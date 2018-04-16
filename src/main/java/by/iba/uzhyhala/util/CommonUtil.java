package by.iba.uzhyhala.util;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
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

    public static int getIdUserByUUID(Session session, String uuid) {
        int result = (int) session.createQuery("SELECT a.id FROM " + VariablesUtil.ENTITY_AUTH_INFO + " a WHERE" +
                " uuid = :uuid").setParameter("uuid", uuid).list().get(0);
        logger.debug(CommonUtil.class.getName() + " getIdUserByLoginEmail return: " + result);
        return result;
    }

    public static String getUUIDUserByLoginEmail(Session session, String loginOrEmail, String type) {
        String result = session.createQuery("SELECT a.uuid FROM " + VariablesUtil.ENTITY_AUTH_INFO
                + " a WHERE " + type + " = :cred").setParameter("cred", loginOrEmail).list().get(0).toString();
        logger.debug(CommonUtil.class.getName() + " getUUIDUserByLoginEmail return: " + result);
        return result;
    }

/*    public static String getUserFirstLastNameByUUID(Session session, String uuid) {
        String result = session.createQuery("SELECT a. FROM " + VariablesUtil.ENTITY_AUTH_INFO
                + " a WHERE " + type + " = :cred").setParameter("cred", loginOrEmail).list().get(0).toString();
        logger.debug(CommonUtil.class.getName() + " getUUIDUserByLoginEmail return: " + result);
        return result;
    }*/

    public static String getCategoryById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            String s = session.createQuery("SELECT c.category_name FROM " + VariablesUtil.ENTITY_CATEGORY + " c WHERE id = :id")
                    .setParameter("id", id).getResultList().get(0).toString();
            return s;
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public static String getLotDateEnd(String start, String plusSec) {
        String dateNow = new SimpleDateFormat(VariablesUtil.PATTERN_DATE_REVERSE).format(new Date().getTime());
        LocalDateTime localDateTime = LocalDateTime.parse(dateNow + "T" + start);
        return String.valueOf(localDateTime.plusSeconds(Long.parseLong(plusSec)).toLocalTime());
    }
}
