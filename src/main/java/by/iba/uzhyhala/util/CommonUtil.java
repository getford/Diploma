package by.iba.uzhyhala.util;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.regex.Pattern;

public class CommonUtil {
    private static final Logger logger = Logger.getLogger(CommonUtil.class);

    public static String nameRoleByID(int id) {
        return (id == 1) ? VariablesUtil.ROLE_ADMIN_NAME : VariablesUtil.ROLE_USER_NAME;
    }

    public static int idRoleByName(String name) {
        return (name.equalsIgnoreCase("admin")) ? VariablesUtil.ROLE_ADMIN_ID : VariablesUtil.ROLE_USER_ID;
    }

    public static String loginOrEmail(String loginOrEmail) {
        String result = Pattern.compile(VariablesUtil.REGEXP_EMAIL,
                Pattern.CASE_INSENSITIVE).matcher(loginOrEmail).find() ? "email" : "login";
        logger.debug(CommonUtil.class.getName() + " loginOrEmail return: " + result);
        return result;
    }

    public static int getIdUserByLoginEmail(Session session, String loginOrEmail, String type) {
        return (int) session.createQuery("SELECT a.id FROM " + VariablesUtil.ENTITY_AUTH_INFO + " a WHERE "
                + type + " = '" + loginOrEmail + "'").list().get(0);
    }

    public static String getUUIDUserByLoginEmail(Session session, String loginOrEmail, String type) {
        return session.createQuery("SELECT a.uuid FROM " + VariablesUtil.ENTITY_AUTH_INFO
                + " a WHERE " + type + " = :cred").setParameter("cred", loginOrEmail).list().get(0).toString();

    }
}
