package by.iba.uzhyhala.cookie;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ParseCookie {
    private static final Logger log = Logger.getLogger(ParseCookie.class);
    private String token = null;

    public ParseCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("auth")) {
                    token = c.getValue();
                    log.info("token: " + token);
                }
            }
        }
    }

    public int getUserIdFromToken() throws IOException {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey("zhigalo".getBytes("UTF-8"))
                .parseClaimsJws(token);

        return Integer.parseInt(String.valueOf(jws.getBody().get("id")));
    }

    public int getPositionIdFromToken() throws UnsupportedEncodingException {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey("zhigalo".getBytes("UTF-8"))
                .parseClaimsJws(token);

        return Integer.parseInt(String.valueOf(jws.getBody().get("position")));
    }

    public String getLoginFromToken() throws UnsupportedEncodingException {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey("zhigalo".getBytes("UTF-8"))
                .parseClaimsJws(token);

        return String.valueOf(jws.getBody().get("login"));
    }
}