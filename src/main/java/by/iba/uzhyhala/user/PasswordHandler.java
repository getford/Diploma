package by.iba.uzhyhala.user;

import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import static by.iba.uzhyhala.util.CommonUtil.*;
import static by.iba.uzhyhala.util.VariablesUtil.ENTITY_AUTH_INFO;
import static by.iba.uzhyhala.util.VariablesUtil.HASH_SALT;
import static java.lang.String.valueOf;
import static org.apache.commons.codec.digest.DigestUtils.sha512Hex;

@WebServlet(urlPatterns = "/forgetpassword")
public class PasswordHandler extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(PasswordHandler.class);
    private static final long serialVersionUID = -6493429003403048237L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            String isForgetPassword = req.getParameter("fp");
            String uuid = req.getParameter("uuid");
            if ("true".equals(isForgetPassword)) {  // change forget password
                LOGGER.info("isForgetPassword -> " + isForgetPassword);
                if (req.getParameter("new_password").equals(req.getParameter("new_password_"))) {
                    String hashPassword = sha512Hex(req.getParameter("new_password") + HASH_SALT);
                    session.createSQLQuery("UPDATE auth_info SET " +
                            "password = '" + hashPassword + "' WHERE uuid ='" + uuid + "'").executeUpdate();
                    resp.sendRedirect("/pages/auth.jsp");
                }
            } else if ("false".equals(isForgetPassword)) { // change user password
                LOGGER.info("isForgetPassword -> " + isForgetPassword);
                String oldPassword = session.createQuery("SELECT a.password FROM " + ENTITY_AUTH_INFO + " a WHERE uuid = :uuid")
                        .setParameter("uuid", uuid).list().get(0).toString();

                if (oldPassword.equals(sha512Hex(req.getParameter("old_password") + HASH_SALT))) {
                    if (req.getParameter("new_password").equals(req.getParameter("new_password_"))) {
                        String hashPassword = sha512Hex(req.getParameter("new_password") + HASH_SALT);
                        session.createSQLQuery("UPDATE auth_info SET " +
                                "password = '" + hashPassword + "' WHERE uuid ='" + uuid + "'").executeUpdate();
                        LOGGER.info("Password change successfully for user " + uuid);

                        URL url = new URL(valueOf(req.getRequestURL()));
                        String body =
                                "<br/>Добрый день, ваг пароль был изменен." +
                                        "<br/><b>Если это были не вы</b>" +
                                        "<p>Перейдите по ссылке для изменения: <a href=\"" + url.getProtocol() + "://" + url.getHost() +
                                        ":" + url.getPort() + "/pages/password.jsp?uuid=" + uuid +
                                        "&fp=true\">CLICK HERE FOR CHANGE PASSWORD</a></p>" +
                                        "<br/><br/>С уважением";
                        new MailUtil().sendSimpleHtmlMail(getUserEmailByUUID(getUUIDUserByUUIDLot(uuid)), body, "Пароль был изменен");
                        resp.sendRedirect("/pages/profile.jsp?user=" + CommonUtil.getUserLoginByUUID(uuid));
                    }
                }
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("Method: getUserLoginByUUID\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            resp.sendRedirect("/pages/index.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String le = req.getParameter("login_email");
        new MailUtil().sendForgetPasscodeMail(getUUIDUserByLoginEmail(le, loginOrEmail(le)), req);
        resp.sendRedirect("/pages/auth.jsp");
    }
}
