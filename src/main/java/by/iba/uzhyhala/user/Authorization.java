package by.iba.uzhyhala.user;

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
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static by.iba.uzhyhala.util.CommonUtil.loginOrEmail;
import static by.iba.uzhyhala.util.VariablesUtil.*;
import static java.lang.String.valueOf;
import static org.apache.commons.codec.digest.DigestUtils.sha512Hex;

@WebServlet(urlPatterns = "/auth")
public class Authorization extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(Authorization.class);
    private static final String REDIRECT_INDEX_PAGE = "/pages/index.jsp";
    private static final String REDIRECT_AUTH_PAGE = "/pages/auth.jsp";
    private static final long serialVersionUID = 3172204188621956218L;

    private String type;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String loginOrEmail = req.getParameter("login_or_email");
            type = loginOrEmail(loginOrEmail).toLowerCase();
            if (isPasswordValid(session, loginOrEmail, req.getParameter(PASSCODE))) {
                Object[] obj = session.createSQLQuery("select uuid, role from auth_info where "
                        + type + " = '" + loginOrEmail + "'").list().toArray();
                setAuthCookie(((Object[]) obj[0])[0].toString(), valueOf(((Object[]) obj[0])[1]), resp);
                resp.sendRedirect(REDIRECT_INDEX_PAGE);
            } else {
                resp.sendRedirect(REDIRECT_AUTH_PAGE);
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
        }
    }

    private boolean isPasswordValid(Session session, String cred, String password) {
        Query query = session.createQuery("SELECT a.password FROM " + ENTITY_AUTH_INFO + " a WHERE " +
                type + " = :cred").setParameter("cred", cred);
        return !query.list().isEmpty() && sha512Hex(password + HASH_SALT).equals(valueOf(query.list().get(0)));
    }

    private void setAuthCookie(String uuid, String role, HttpServletResponse resp) {
        String token = Jwts.builder()
                .setSubject("AuthToken")
                .claim("uuid", uuid)
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS512,
                        COOKIE_KEY.getBytes(StandardCharsets.UTF_8)
                )
                .compact();
        LOGGER.info("token create successfully");

        Cookie cookie = new Cookie(COOKIE_AUTH_NAME, token);
        cookie.setMaxAge(-1); //  the cookie will persist until browser shutdown
        resp.addCookie(cookie);
    }
}
