package by.iba.uzhyhala.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static by.iba.uzhyhala.util.VariablesUtil.*;

public class CookieUtil {
    private static final Logger LOGGER = Logger.getLogger(CookieUtil.class);

    private String token = null;
    private boolean isFindCookie = false;

    public CookieUtil(HttpServletRequest req) {
        if (req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals(COOKIE_AUTH_NAME)) {
                    token = cookie.getValue();
                    isFindCookie = true;
                    LOGGER.info("Constructor, token: " + token);
                }
            }
        } else {
            isFindCookie = false;
            LOGGER.error("Cookie with name " + COOKIE_AUTH_NAME + " not found");
        }
    }

    public boolean isAdmin(Cookie[] cookies) throws UnsupportedEncodingException {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(COOKIE_AUTH_NAME)
                    && getRoleFromToken().equalsIgnoreCase(ROLE_ADMIN))
                return true;
        }
        return false;
    }

    public static String getUserLoginFromCookie(HttpServletRequest request) {
        LOGGER.info("getUserLoginFromCookie");
        try {
            CookieUtil cookieUtil = new CookieUtil(request);
            if (cookieUtil.isFindCookie()) {
                return CommonUtil.getUserLoginByUUID(cookieUtil.getUserUuidFromToken());
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("\n" + Arrays.toString(ex.getStackTrace()));
            return "";
        }
        return "";
    }

    public String getUserUuidFromToken() throws IOException {
        if (!StringUtils.isBlank(token)) {
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(COOKIE_KEY.getBytes("UTF-8"))
                    .parseClaimsJws(token);
            return String.valueOf(jws.getBody().get("uuid"));
        } else {
            LOGGER.info("Cookie with name " + COOKIE_AUTH_NAME + " not found");
            return null;
        }
    }

    private String getRoleFromToken() throws UnsupportedEncodingException {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(COOKIE_KEY.getBytes("UTF-8"))
                .parseClaimsJws(token);
        return String.valueOf(jws.getBody().get("role"));
    }

    public boolean isFindCookie() {
        return isFindCookie;
    }
}