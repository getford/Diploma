package by.iba.uzhyhala.api;

import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

import static by.iba.uzhyhala.util.VariablesUtil.*;

@WebServlet(urlPatterns = "/del")
public class DeleteHandlerAPI extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(DeleteHandlerAPI.class);
    private static final long serialVersionUID = -2744742351941480652L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            String parameter = Pattern.compile("=").split(req.getQueryString())[0];
            String uuid = Pattern.compile("=").split(req.getQueryString())[1];
            LOGGER.info("parameter: " + parameter);
            LOGGER.info("uuid: " + uuid);
            if ("uuid-user".equals(parameter)) {
                deleteUser(session, uuid, resp);
            } else if ("uuid-lot".equals(parameter)) {
                deleteLot(session, uuid, resp);
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
        }
    }

    private void deleteUser(Session session, String uuid, HttpServletResponse resp) throws IOException {
        session.createQuery("DELETE FROM " + ENTITY_ADDRESS + " WHERE uuid_user = :uuid")
                .setParameter("uuid", uuid)
                .executeUpdate();
        session.createQuery("DELETE FROM " + ENTITY_PERSONAL_INFORMATION + " WHERE uuid_user = :uuid")
                .setParameter("uuid", uuid)
                .executeUpdate();
        session.createQuery("DELETE FROM " + ENTITY_AUTH_INFO + " WHERE uuid = :uuid")
                .setParameter("uuid", uuid)
                .executeUpdate();
        resp.getWriter().write("{\"message\":\"Success\"}");
        resp.sendRedirect("/pages/manageusers.jsp");
    }

    private void deleteLot(Session session, String uuidLot, HttpServletResponse resp) throws IOException {
        session.createQuery("DELETE FROM " + ENTITY_BET + " WHERE uuid = :uuid")
                .setParameter("uuid", uuidLot)
                .executeUpdate();
        session.createQuery("DELETE FROM " + ENTITY_LOT + " WHERE uuid = :uuid")
                .setParameter("uuid", uuidLot)
                .executeUpdate();
        resp.getWriter().write("{\"message\":\"Success\"}");
    }
}
