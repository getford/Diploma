package by.iba.uzhyhala.api;

import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/del")
public class DeleteHandlerAPI extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(DeleteHandlerAPI.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IllegalArgumentException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            String parameter = Pattern.compile("=").split(req.getQueryString())[0];
            String uuid = Pattern.compile("=").split(req.getQueryString())[1];
            LOGGER.info(getClass().getName() + "\t" + "parameter: " + parameter);
            LOGGER.info(getClass().getName() + "\t" + "uuid: " + uuid);
            switch (parameter) {
                case "uuid-user":
                    session.createQuery("DELETE FROM " + VariablesUtil.ENTITY_ADDRESS + " WHERE uuid_user = :uuid")
                            .setParameter("uuid", uuid)
                            .executeUpdate();

                    session.createQuery("DELETE FROM " + VariablesUtil.ENTITY_PERSONAL_INFORMATION + " WHERE uuid_user = :uuid")
                            .setParameter("uuid", uuid)
                            .executeUpdate();

                    session.createQuery("DELETE FROM " + VariablesUtil.ENTITY_AUTH_INFO + " WHERE uuid = :uuid")
                            .setParameter("uuid", uuid)
                            .executeUpdate();
                    resp.getWriter().write("{\"message\":\"Success\"}");
                    resp.sendRedirect("/pages/manageusers.jsp");
                    break;
                case "uuid-lot":

                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            throw new IllegalArgumentException("Cannot get parameter from URL, pls contact with administrator");
        }
    }

    private void deleteLot(Session session, String uuidLot, HttpServletResponse resp) throws IOException {


        session.createQuery("DELETE FROM " + VariablesUtil.ENTITY_BET + " WHERE uuid = :uuid")
                .setParameter("uuid", uuidLot)
                .executeUpdate();

        session.createQuery("DELETE FROM " + VariablesUtil.ENTITY_LOT + " WHERE uuid = :uuid")
                .setParameter("uuid", uuidLot)
                .executeUpdate();
        resp.getWriter().write("{\"message\":\"Success\"}");
    }
}
