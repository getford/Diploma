package by.iba.uzhyhala.util;

import by.iba.uzhyhala.lot.to.BetBulkTO;
import by.iba.uzhyhala.lot.to.BetHistoryTO;
import by.iba.uzhyhala.lot.to.BetTO;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class CommonUtil {
    private static final Logger LOGGER = Logger.getLogger(CommonUtil.class);

    public static String loginOrEmail(String loginOrEmail) {
        String result = Pattern.compile(VariablesUtil.REGEXP_EMAIL,
                Pattern.CASE_INSENSITIVE).matcher(loginOrEmail).find() ? "email" : "login";
        LOGGER.debug(CommonUtil.class.getName() + " loginOrEmail return: " + result);
        return result;
    }

    public static String getUUIDUserByLoginEmail(Session session, String loginOrEmail, String type) {
        String result = session.createQuery("SELECT a.uuid FROM " + VariablesUtil.ENTITY_AUTH_INFO
                + " a WHERE " + type + " = :cred").setParameter("cred", loginOrEmail).list().get(0).toString();
        LOGGER.debug(CommonUtil.class.getName() + " getUUIDUserByLoginEmail return: " + result);
        return result;
    }

    public static String getUUIDUserByUUIDLot(Session session, String uuidLot) {
        String result = session.createSQLQuery("SELECT uuid_user_seller FROM lot WHERE uuid = '" + uuidLot + "'").list().get(0).toString();
        LOGGER.debug(CommonUtil.class.getName() + " getUUIDUserByUUIDLot return: " + result);
        return result;
    }

    public static String getUserFirstLastNameByUUID(Session session, String uuid) {
        Object[] object = session.createSQLQuery("SELECT first_name, last_name FROM personal_information WHERE uuid_user = '" + uuid + "'").list().toArray();
        String result = String.valueOf(((Object[]) object[0])[0]) + " " + String.valueOf(((Object[]) object[0])[1]);
        LOGGER.debug(CommonUtil.class.getName() + " getUUIDUserByLoginEmail return: " + result);
        return result;
    }

    public static String getCategoryById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            String s = session.createQuery("SELECT c.category_name FROM " + VariablesUtil.ENTITY_CATEGORY + " c WHERE id = :id")
                    .setParameter("id", id).getResultList().get(0).toString();
            return s;
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public static String getJsonBetBulk(Session session, String uuid) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            return String.valueOf(session.createQuery("SELECT b.bulk FROM " + VariablesUtil.ENTITY_BET + " b WHERE uuid = :uuid")
                    .setParameter("uuid", uuid).list().get(0));

        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin("\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return null;
        }
    }

    public static List<BetHistoryTO> getHistoryBets(String uuidLot) {
        LOGGER.info("getHistoryBets method");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            BetBulkTO betBulkTO = new Gson().fromJson(CommonUtil.getJsonBetBulk(session, uuidLot), BetBulkTO.class);
            List<BetTO> betTOList = new ArrayList<>(betBulkTO.getBets());

            List<BetHistoryTO> betHistoryTO = new ArrayList<>();
            for (BetTO bet : betTOList) {
                BetHistoryTO to = new BetHistoryTO();
                to.setUserName(CommonUtil.getUserFirstLastNameByUUID(session, bet.getUuidUser()));
                to.setBet(bet.getBet());
                to.setDate(bet.getDate());
                to.setTime(bet.getTime());

                betHistoryTO.add(to);
            }
            return betHistoryTO;
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return null;
        }
    }

    public static String getLotDateEnd(String start, String plusSec) {
        String dateNow = new SimpleDateFormat(VariablesUtil.PATTERN_DATE_REVERSE).format(new Date().getTime());
        LocalDateTime localDateTime = LocalDateTime.parse(dateNow + "T" + start);
        return String.valueOf(localDateTime.plusSeconds(Long.parseLong(plusSec)).toLocalTime());
    }
}
