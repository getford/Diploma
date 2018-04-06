package by.iba.uzhyhala.user;

import by.iba.uzhyhala.entity.AuthInfoEntity;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
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
import java.util.List;

@WebServlet(urlPatterns = "/auth")
public class Authorization extends HttpServlet implements IParseJsonString {

    private static final Logger logger = Logger.getLogger(Authorization.class);
    private static final String REDIRECT_INDEX_PAGE = "/pages/index.jsp";
    private static final String REDIRECT_AUTH_PAGE = "/pages/auth.jsp";

    private Session session;
    private String type;
    private String role;

    public Authorization() {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.type = CommonUtil.loginOrEmail(req.getParameter("login_or_email")).toLowerCase();
        if (isPasswordValid(req.getParameter("login_or_email"), req.getParameter("password"))) {
            Object[] obj = getUserUuidAndRole(req.getParameter("login_or_email").toLowerCase());
            assert obj != null;
            setAuthCookie(((Object[]) obj[0])[0].toString(), CommonUtil.getNameRoleByID(
                    Integer.parseInt(String.valueOf(((Object[]) obj[0])[1]))), resp);
            resp.sendRedirect(REDIRECT_INDEX_PAGE);
        } else {
            resp.sendRedirect(REDIRECT_AUTH_PAGE);
        }
    }

    private Object[] getUserUuidAndRole(String loginOrEmail) {
        try {
            return session.createSQLQuery("select uuid, id_role from auth_info where " + type + " = '" +
                    loginOrEmail + "'").list().toArray();
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getLocalizedMessage());
            return null;
        }
    }

    private boolean isPasswordValid(String cred, String password) {
        Query query = session.createQuery("SELECT a.password FROM " + VariablesUtil.ENTITY_AUTH_INFO + " a WHERE " +
                type + " = :cred").setParameter("cred", cred);
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
            logger.info(getClass().getName() + " token create successfully");

            Cookie cookie = new Cookie(VariablesUtil.COOKIE_AUTH_NAME, token);
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
