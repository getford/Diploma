package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.entity.LotEntity;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = "/lotcontrol")
public class LotControl extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LotHandler.class);
    private static final String ZERO_TIME = "00:00:00";

    private Session session;
    private String uuidLot;

    public LotControl(String uuidLot) {
        try {
            this.uuidLot = uuidLot;
      /*    session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            LOGGER.debug(getClass().getName() + " constructor");
            this.type = CommonUtil.loginOrEmail(loginOrEmail);
            this.idUser = CommonUtil.getIdUserByLoginEmail(session, loginOrEmail, type);*/
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + Arrays.toString(ex.getStackTrace()));
        } /*finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }*/
    }

    public List<LotEntity> getLotInfoByUuid() {
        LOGGER.debug(getClass().getName() + " getLotInfoByUuid");
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("SELECT l FROM " + VariablesUtil.ENTITY_LOT + " l WHERE uuid = :uuid", LotEntity.class)
                    .setParameter("uuid", uuidLot).getResultList();
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public String returnEndTime() {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            String timeEnd = String.valueOf(session.createSQLQuery("SELECT time_end FROM lot WHERE uuid = '" + uuidLot + "'").getResultList().get(0));

            DateFormat dateFormat = new SimpleDateFormat(VariablesUtil.PATTERN_TIME);
            Date reference = dateFormat.parse(ZERO_TIME);
            Date date = dateFormat.parse(timeEnd);
            LOGGER.debug("TIME END: " + date);
            long secondsEnd = (date.getTime() - reference.getTime()) / 1000L;

            reference = dateFormat.parse(ZERO_TIME);
            date = dateFormat.parse(String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_TIME_WITH_MILLISECONDS).format(new Date().getTime())));
            LOGGER.debug("TIME NOW: " + date);
            long secondsNow = (date.getTime() - reference.getTime()) / 1000L;

            long diff = secondsEnd - secondsNow;

            if (diff < 0)
                return "CLOSED";
            else {
                // TODO: show time on page format MM:ss
                LocalTime timeOfDay = LocalTime.ofSecondOfDay(diff);
                String time = String.valueOf(timeOfDay);
                LOGGER.debug("DIFF TIME: " + time);
                return String.valueOf(diff);
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }
}
