package by.iba.uzhyhala.user;

import by.iba.uzhyhala.entity.AuthInfoEntity;
import by.iba.uzhyhala.entity.RoleEntity;
import by.iba.uzhyhala.to.UserTO;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.ReCaptchaUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = "/registration")
public class Registration extends HttpServlet implements IParseJsonString {
    private static final Logger logger = Logger.getLogger(Registration.class);

    private Session session;
    private Gson gson;

    public Registration() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter printWriter = resp.getWriter();
        if (ReCaptchaUtil.verify(req.getParameter("g-recaptcha-response"))) {
            if (doRegistration(req.getParameter("login"), req.getParameter("password"), req.getParameter("email"))) {
                try {
                    new MailUtil().sendMailRegistration(req.getParameter("email"),
                            req.getParameter("login"),
                            req.getParameter("password"), req);
                    resp.sendRedirect("/pages/index.jsp");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            printWriter.println("<font color=red>You missed the Captcha.</font>");
        }
    }

    public boolean doRegistration(String login, String password, String email) {
        logger.debug(this.getClass().getName() + ", method: doRegistration");

        AuthInfoEntity authInfoEntity = gson.fromJson(prepareInputString(login.toLowerCase(), password.toLowerCase(), email.toLowerCase()), AuthInfoEntity.class);
        if (isLoginAndEmailEmpty(authInfoEntity.getLogin().toLowerCase(), authInfoEntity.getEmail().toLowerCase())) {

            authInfoEntity.setLogin(authInfoEntity.getLogin().toLowerCase());
            authInfoEntity.setPassword(authInfoEntity.getPassword());
            authInfoEntity.setEmail(authInfoEntity.getEmail());
            authInfoEntity.setRoleByIdRole((RoleEntity) session.load(RoleEntity.class, VariablesUtil.ROLE_USER_ID));
            authInfoEntity.setUuid(UUID.randomUUID().toString());

            session.save(authInfoEntity);
            session.getTransaction().commit();
            //  session.close();
            return true;
        } else {
            logger.debug("Login isn't empty");
            return false;
        }

    }

    private boolean isLoginAndEmailEmpty(String login, String email) {
        Query query = session.createQuery("SELECT a.login, a.email FROM " +
                VariablesUtil.ENTITY_AUTH_INFO + " a WHERE login = :login AND email = :email");
        query.setParameter("login", login);
        query.setParameter("email", email);
        return query.list().isEmpty();

    }

    @Override
    public String prepareInputString(String login, String password, String email) {
        AuthInfoEntity authInfoEntity = new AuthInfoEntity();
        authInfoEntity.setLogin(login);
        authInfoEntity.setPassword(password);
        authInfoEntity.setEmail(email);
        return gson.toJson(authInfoEntity);
    }

    @Override
    public List<AuthInfoEntity> handleInputString(String json) {
        return gson.fromJson(json, new TypeToken<List<UserTO>>() {
        }.getType());
    }
}