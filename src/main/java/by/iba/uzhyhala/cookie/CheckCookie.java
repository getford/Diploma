package by.iba.uzhyhala.cookie;

import by.iba.uzhyhala.util.VariablesUtil;

import javax.servlet.http.Cookie;

public class CheckCookie {
    public boolean isAdmin(Cookie[] cookies, String role) {
        for (Cookie cooky : cookies) {
            if (cooky.getName().equals("auction_auth") && role.equals(VariablesUtil.ROLE_ADMIN))
                return true;
        }
        return false;
    }

    public boolean isAuthorized(Cookie[] cookies) {
        for (Cookie cooky : cookies) {
            if (cooky.getName().equals("auction_auth"))
                return true;
        }
        return false;
    }
}