package by.iba.uzhyhala.util;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.regex.Pattern;

public class CommonUtil {
    private static final Logger logger = Logger.getLogger(CommonUtil.class);

    public static String loginOrEmail(String loginOrEmail) {
        String result = Pattern.compile(VariablesUtil.REGEXP_EMAIL,
                Pattern.CASE_INSENSITIVE).matcher(loginOrEmail).find() ? "email" : "login";
        logger.debug(CommonUtil.class.getName() + " loginOrEmail return: " + result);
        return result;
    }

    public static int getIdUserByLoginEmail(Session session, String loginOrEmail, String type) {
        int result = (int) session.createQuery("SELECT a.id FROM " + VariablesUtil.ENTITY_AUTH_INFO + " a WHERE "
                + type + " = '" + loginOrEmail + "'").list().get(0);
        logger.debug(CommonUtil.class.getName() + " getIdUserByLoginEmail return: " + result);
        return result;
    }

    public static String getUUIDUserByLoginEmail(Session session, String loginOrEmail, String type) {
        String result = session.createQuery("SELECT a.uuid FROM " + VariablesUtil.ENTITY_AUTH_INFO
                + " a WHERE " + type + " = :cred").setParameter("cred", loginOrEmail).list().get(0).toString();
        logger.debug(CommonUtil.class.getName() + " getUUIDUserByLoginEmail return: " + result);
        return result;
    }
}
