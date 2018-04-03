package by.iba.uzhyhala.user;

import by.iba.uzhyhala.entity.AuthInfoEntity;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.RegexpUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import by.iba.uzhyhala.util.VerifyRecaptchaUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/auth")
public class Authorization extends HttpServlet implements IParseJsonString {

    private static final Logger logger = Logger.getLogger(Authorization.class);

    private Session session;
    private String type;

    public Authorization() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (isLoginOrEmail(req.getParameter("login_or_email").toLowerCase(), req.getParameter("password").toLowerCase())) {
            Object[] obj = getUserUuidAndRole(req.getParameter("login_or_email").toLowerCase());
            // get reCAPTCHA request param
            String gRecaptchaResponse = req.getParameter("g-recaptcha-response");
            System.out.println(gRecaptchaResponse);
            boolean verify = VerifyRecaptchaUtil.verify(gRecaptchaResponse);

            logger.debug("verify > " + verify);

            setAuthCookie(((Object[]) obj[0])[0].toString(), ((Object[]) obj[0])[1].toString(), resp);
            // resp.sendRedirect("/pages/index.jsp");

            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/pages/index.jsp");
            PrintWriter out = resp.getWriter();
            if (verify) {
                out.println("<font color=red>Either user name or password is wrong.</font>");
            } else {
                out.println("<font color=red>You missed the Captcha.</font>");
            }
            rd.include(req, resp);

            session.close();
        } else {
            resp.sendRedirect("/pages/auth.jsp");
            session.close();
        }
    }

    private boolean isLoginOrEmail(String loginOrEmail, String password) {
        Matcher matcher = Pattern.compile(RegexpUtil.EMAIL_REGEXP, Pattern.CASE_INSENSITIVE).matcher(loginOrEmail);
        if (matcher.find()) {
            type = "email";
            return isPasswordValid(loginOrEmail, password);
        } else {
            type = "login";
            return isPasswordValid(loginOrEmail, password);
        }
    }

    private Object[] getUserUuidAndRole(String loginOrEmail) {
        try {
            Query query = session.createQuery("SELECT a.uuid, a.id_role FROM " + VariablesUtil.ENTITY_AUTH_INFO + " a WHERE " + type + " = :cred");
            query.setParameter("cred", loginOrEmail);

            logger.error(query.list().toArray());

            return query.list().toArray();
        }catch (Exception ex){
            logger.error(ex.getLocalizedMessage());
            return null;
        }
    }

    private boolean isPasswordValid(String cred, String password) {
        Query query = session.createQuery("SELECT a.password FROM " + VariablesUtil.ENTITY_AUTH_INFO + " a WHERE " + type + " = :cred");
        query.setParameter("cred", cred);
        return !(query.list().isEmpty()) && (password.equals(query.list().get(0).toString()));
    }

    private void setAuthCookie(String uuid, String role, HttpServletResponse resp) {
        try {
            String token = Jwts.builder()
                    .setSubject("AuthToken")
                    //.setExpiration(new Date(1300819380))
                    .claim("uuid", uuid)
                    .claim("role", role)
                    .signWith(SignatureAlgorithm.HS512,
                            VariablesUtil.COOKIE_KEY.getBytes("UTF-8")
                    )
                    .compact();
            logger.info("token create successfully");

            Cookie cookie = new Cookie("auth", token);
            cookie.setMaxAge(-1); //  the cookie will persist until browser shutdown
            resp.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String prepareInputString(String login, String password, String emil) {
        return null;
    }

    @Override
    public List<AuthInfoEntity> handleInputString(String args) {
        return null;
    }
}
