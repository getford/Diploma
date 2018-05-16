package by.iba.uzhyhala.user;

import by.iba.uzhyhala.entity.AddressEntity;
import by.iba.uzhyhala.entity.AuthInfoEntity;
import by.iba.uzhyhala.entity.PersonalInformationEntity;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.ReCaptchaUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static by.iba.uzhyhala.util.VariablesUtil.*;

@WebServlet(urlPatterns = "/registration")
public class Registration extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Registration.class);
    private static final long serialVersionUID = -7067000206036155152L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            PrintWriter printWriter = resp.getWriter();
            String email = req.getParameter("email");
            String login = req.getParameter("login");
            if (ReCaptchaUtil.verify(req.getParameter("g-recaptcha-response"))) {
                if (doRegistration(login, req.getParameter("password"), email)) {
                    URL url = new URL(req.getRequestURL().toString());
                    String body = "<br/> " + new SimpleDateFormat(PATTERN_FULL_DATE_TIME).format(new Date().getTime()) + "<br/>" +
                            "<p>Hello,</p>" +
                            "<p>You will be successfully registered in Auction</p>" +
                            "<p>" +
                            "<b>Your login: </b>" + login + "" +
                            "<br/><b>Your password: </b>" + req.getParameter("password") + "" +
                            "</p>" +
                            "<p>You profile: <a href=\"" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/pages/profile.jsp?login=" + login + "\">" +
                            "" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/pages/profile.jsp?user=" + login + "</a></p>";
                    String subject = "";
                    new MailUtil().sendSimpleHtmlMail(email, body, subject);
                    resp.sendRedirect("/pages/index.jsp");
                }
            } else {
                printWriter.println("<font color=red>You missed the Captcha.</font>");
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
        }
    }

    public boolean doRegistration(String login, String password, String email) {
        LOGGER.debug("method: doRegistration");
        String newUserUUID = UUID.randomUUID().toString();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            AuthInfoEntity authInfoEntity = new Gson().fromJson(prepareInputString(login.toLowerCase(), password.toLowerCase(), email.toLowerCase()), AuthInfoEntity.class);
            if (isLoginAndEmailEmpty(session, authInfoEntity.getLogin().toLowerCase(), authInfoEntity.getEmail().toLowerCase())) {

                authInfoEntity.setLogin(authInfoEntity.getLogin().toLowerCase());
                authInfoEntity.setPassword(authInfoEntity.getPassword());
                authInfoEntity.setEmail(authInfoEntity.getEmail());
                authInfoEntity.setRole(ROLE_USER);
                authInfoEntity.setUuid(newUserUUID);
                authInfoEntity.setCreateDate(java.sql.Date.valueOf(new SimpleDateFormat(PATTERN_DATE_REVERSE).format(new Date().getTime())));

                PersonalInformationEntity personalInformationEntity = new PersonalInformationEntity();
                personalInformationEntity.setUuidUser(newUserUUID);

                AddressEntity addressEntity = new AddressEntity();
                addressEntity.setUuidUser(newUserUUID);

                session.save(authInfoEntity);
                session.save(personalInformationEntity);
                session.save(addressEntity);
                session.getTransaction().commit();
                session.close();
                return true;
            } else {
                LOGGER.debug("Login isn't empty");
                return false;
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return false;
        }
    }

    private boolean isLoginAndEmailEmpty(Session session, String login, String email) {
        return session.createQuery("SELECT a.login, a.email FROM " +
                ENTITY_AUTH_INFO + " a WHERE login = :login AND email = :email")
                .setParameter("login", login)
                .setParameter("email", email)
                .list()
                .isEmpty();
    }

    private String prepareInputString(String login, String password, String email) {
        AuthInfoEntity authInfoEntity = new AuthInfoEntity();
        authInfoEntity.setLogin(login);
        authInfoEntity.setPassword(password);
        authInfoEntity.setEmail(email);
        return new Gson().toJson(authInfoEntity);
    }
}