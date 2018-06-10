package by.iba.uzhyhala.admin;

import by.iba.uzhyhala.lot.LotStatus;
import by.iba.uzhyhala.util.MailUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static by.iba.uzhyhala.util.HibernateUtil.getSessionFactory;
import static by.iba.uzhyhala.util.VariablesUtil.*;

@WebServlet(urlPatterns = "/change")
public class ChangeHandler extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LotStatus.class);
    private static final long serialVersionUID = 7888456218037755091L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();

            if (req.getParameter("type").equals(USER)) {

                session.createQuery("UPDATE " + ENTITY_AUTH_INFO +
                        " SET login = :login " +
                        "WHERE uuid = :uuid")
                        .setParameter("login", req.getParameter("login"))
                        .setParameter("uuid", req.getParameter("uuid"))
                        .executeUpdate();

                session.createQuery("UPDATE " + ENTITY_AUTH_INFO +
                        " SET email = :email " +
                        "WHERE uuid = :uuid")
                        .setParameter("email", req.getParameter("email"))
                        .setParameter("uuid", req.getParameter("uuid"))
                        .executeUpdate();

                session.createQuery("UPDATE " + ENTITY_AUTH_INFO +
                        " SET role = :role " +
                        "WHERE uuid = :uuid")
                        .setParameter("role", req.getParameter("role"))
                        .setParameter("uuid", req.getParameter("uuid"))
                        .executeUpdate();
            } else if (req.getParameter("type").equals(LOT)) {
            }
            resp.sendRedirect("/pages/manageusers.jsp");
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("Method: isUpdateLotStatus\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
        }
    }
}
