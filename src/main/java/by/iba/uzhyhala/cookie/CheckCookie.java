package by.iba.uzhyhala.cookie;

import by.iba.uzhyhala.util.VariablesUtil;

import javax.servlet.http.Cookie;

public class CheckCookie {
    public boolean isAdmin(Cookie[] cookies, String role) {
        boolean flag = false;
        for (Cookie cooky : cookies) {
            if (cooky.getName().equals("auction_auth") && role.equals(VariablesUtil.ROLE_ADMIN))
                flag = true;
        }
        return flag;
    }

    public boolean isAuthorized(Cookie[] cookies) {
        boolean flag = false;
        for (Cookie cooky : cookies) {
            if (cooky.getName().equals("auction_auth"))
                flag = true;
        }
        return flag;
    }
}