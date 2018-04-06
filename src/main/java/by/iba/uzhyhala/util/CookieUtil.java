package by.iba.uzhyhala.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class CookieUtil {
    private static final Logger logger = Logger.getLogger(CookieUtil.class);

    private String token = null;
    private boolean isFindCookie = false;

    public CookieUtil(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(VariablesUtil.COOKIE_AUTH_NAME)) {
                    token = c.getValue();
                    isFindCookie = true;
                    logger.info(getClass().getName() + " constructor, token: " + token);
                }
            }
        } else {
            isFindCookie = false;
        }
    }

    public boolean isAdmin(Cookie[] cookies) throws UnsupportedEncodingException {
        for (Cookie cooky : cookies) {
            if ((cooky.getName().equals(VariablesUtil.COOKIE_AUTH_NAME))
                    && (getRoleFromToken() == VariablesUtil.ROLE_ADMIN_ID))
                return true;
        }
        return false;
    }

    public int getUserUuidFromToken() throws IOException {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(VariablesUtil.COOKIE_KEY.getBytes("UTF-8"))
                .parseClaimsJws(token);

        return Integer.parseInt(String.valueOf(jws.getBody().get("uuid")));
    }

    private int getRoleFromToken() throws UnsupportedEncodingException {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(VariablesUtil.COOKIE_KEY.getBytes("UTF-8"))
                .parseClaimsJws(token);

        return Integer.parseInt(String.valueOf(jws.getBody().get("role")));
    }

    public boolean isFindCookie() {
        return isFindCookie;
    }
}