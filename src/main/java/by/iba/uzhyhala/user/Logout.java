package by.iba.uzhyhala.user;

import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(urlPatterns = "/logout")
public class Logout extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Logout.class);

    public Logout(HttpServletRequest req, HttpServletResponse resp) {
        try {
            doPost(req, resp);
        } catch (IOException e) {
            logger.error(getClass().getName() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie cookie = new Cookie(VariablesUtil.COOKIE_AUTH_NAME, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");

        resp.addCookie(cookie);
        resp.sendRedirect("/pages/");
    }
}
