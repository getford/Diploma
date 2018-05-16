package by.iba.uzhyhala.user;

import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.iba.uzhyhala.util.VariablesUtil.COOKIE_AUTH_NAME;

@WebServlet(urlPatterns = "/logout")
public class Logout extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Logout.class);
    private static final long serialVersionUID = 1296230515763603800L;

//    public Logout(HttpServletRequest req, HttpServletResponse resp) {
//        try {
//            doPost(req, resp);
//        } catch (IOException e) {
//            LOGGER.error(Arrays.toString(e.getStackTrace()));
//        }
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.info("doPost method");
        Cookie cookie = new Cookie(COOKIE_AUTH_NAME, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");

        resp.addCookie(cookie);
        resp.sendRedirect("/pages/index.jsp");
    }
}
