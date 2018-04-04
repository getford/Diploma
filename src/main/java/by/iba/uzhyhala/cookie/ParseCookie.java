package by.iba.uzhyhala.cookie;

import by.iba.uzhyhala.util.VariablesUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ParseCookie {
    private static final Logger logger = Logger.getLogger(ParseCookie.class);
    private String token = null;

    public ParseCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("auction_auth")) {
                    token = c.getValue();
                    logger.info("token: " + token);
                }
            }
        }
    }

    public int getUserUuidFromToken() throws IOException {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(VariablesUtil.COOKIE_KEY.getBytes("UTF-8"))
                .parseClaimsJws(token);

        return Integer.parseInt(String.valueOf(jws.getBody().get("uuid")));
    }

    public int getRoleFromToken() throws UnsupportedEncodingException {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(VariablesUtil.COOKIE_KEY.getBytes("UTF-8"))
                .parseClaimsJws(token);

        return Integer.parseInt(String.valueOf(jws.getBody().get("role")));
    }
}