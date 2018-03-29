package by.iba.uzhyhala.cookie;

import javax.servlet.http.Cookie;

public class CheckCookie {
    public boolean isAdmin(Cookie[] cookies, int position) {
        boolean flag = false;
        for (Cookie cooky : cookies) {
            if (cooky.getName().equals("auth") && position == 0)
                flag = true;
        }
        return flag;
    }

    public boolean isAuthorized(Cookie[] cookies) {
        boolean flag = false;
        for (Cookie cooky : cookies) {
            if (cooky.getName().equals("auth"))
                flag = true;
        }
        return flag;
    }
}