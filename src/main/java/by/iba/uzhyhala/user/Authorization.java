package by.iba.uzhyhala.user;

import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static by.iba.uzhyhala.util.VariablesUtil.*;

@WebServlet(urlPatterns = "/auth")
public class Authorization extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(Authorization.class);
    private static final String REDIRECT_INDEX_PAGE = "/pages/index.jsp";
    private static final String REDIRECT_AUTH_PAGE = "/pages/auth.jsp";
    private static final long serialVersionUID = 3172204188621956218L;

    private String type;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String loginOrEmail = req.getParameter("login_or_email");
            type = CommonUtil.loginOrEmail(loginOrEmail).toLowerCase();
            if (isPasswordValid(loginOrEmail, req.getParameter("password"))) {
                Object[] obj = getUserUuidAndRole(loginOrEmail.toLowerCase());
                setAuthCookie(((Object[]) obj[0])[0].toString(), String.valueOf(((Object[]) obj[0])[1]), resp);
                resp.sendRedirect(REDIRECT_INDEX_PAGE);
            } else {
                resp.sendRedirect(REDIRECT_AUTH_PAGE);
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
        }
    }

    private Object[] getUserUuidAndRole(String loginOrEmail) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createSQLQuery("select uuid, role from auth_info where " + type + " = '" +
                    loginOrEmail + "'").list().toArray();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return new Object[0];
        }
    }

    private boolean isPasswordValid(String cred, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("SELECT a.password FROM " + ENTITY_AUTH_INFO + " a WHERE " +
                    type + " = :cred").setParameter("cred", cred);
            return !(query.list().isEmpty()) && (password.equals(query.list().get(0).toString()));
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return false;
        }
    }

    private void setAuthCookie(String uuid, String role, HttpServletResponse resp) {
        try {
            String token = Jwts.builder()
                    .setSubject("AuthToken")
                    .claim("uuid", uuid)
                    .claim("role", role)
                    .signWith(SignatureAlgorithm.HS512,
                            COOKIE_KEY.getBytes("UTF-8")
                    )
                    .compact();
            LOGGER.info(" token create successfully");

            Cookie cookie = new Cookie(COOKIE_AUTH_NAME, token);
            cookie.setMaxAge(-1); //  the cookie will persist until browser shutdown
            resp.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            new MailUtil().sendErrorMail(Arrays.toString(e.getStackTrace()));
            LOGGER.error(e.getMessage());
        }
    }
}
