package by.iba.uzhyhala.user;

import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoAuthorization extends HttpServlet implements IParseJsonString {

    private static final Logger logger = Logger.getLogger(DoAuthorization.class);
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    private Session session;
    private Gson gson;

    public DoAuthorization() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        if (doLogin(req.getParameter("login_or_email").toLowerCase(), req.getParameter("password").toLowerCase())) {
            resp.sendRedirect("/index");
        }
        //TODO: else block
    }

    public boolean doLogin(String loginOrEmail, String password) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(loginOrEmail);
        if (matcher.find()) {
            return isPasswordValid(loginOrEmail, password, "email");
        } else {
            return isPasswordValid(loginOrEmail, password, "login");
        }
    }

    private boolean isPasswordValid(String cred, String password, String type) {
        Query query = session.createQuery("SELECT a.password FROM " + VariablesUtil.ENTITY_AUTH_INFO + " a WHERE " + type + " = :cred");
        query.setParameter("cred", cred);

        boolean flag = false;
        if (!(query.list().isEmpty()) && (password.equals(query.list().get(0).toString())))
            flag = true;

        return flag;
    }

    @Override
    public String prepareInputString(String login, String password, String email) {
        return null;
    }

    @Override
    public List<?> handleInputString(String args) {
        return null;
    }
}
