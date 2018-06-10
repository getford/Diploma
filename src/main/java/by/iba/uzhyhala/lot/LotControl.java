package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.entity.LotEntity;
import by.iba.uzhyhala.util.MailUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static by.iba.uzhyhala.util.HibernateUtil.getSessionFactory;
import static by.iba.uzhyhala.util.VariablesUtil.*;
import static java.lang.String.valueOf;

@WebServlet(urlPatterns = "/lotcontrol")
public class LotControl extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LotHandler.class);
    private static final String ZERO_TIME = "00:00:00";
    private static final long serialVersionUID = 4795526489089370744L;

    private String uuidLot;

    public LotControl(String uuidLot) {
        this.uuidLot = uuidLot;
    }

    public List<LotEntity> getLotInfoByUuid() {
        LOGGER.debug(" getLotInfoByUuid");
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery("SELECT l FROM " + ENTITY_LOT + " l WHERE uuid = :uuid", LotEntity.class)
                    .setParameter("uuid", uuidLot).getResultList();
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return new ArrayList<>();
        }
    }

    public static List<LotEntity> getAllLots() {
        LOGGER.debug(" getLotInfoByUuid");
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery("SELECT l FROM " + ENTITY_LOT + " l", LotEntity.class).getResultList();
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
            return new ArrayList<>();
        }
    }

    public String returnEndTime() {
        try (Session session = getSessionFactory().openSession()) {
            String timeEnd = valueOf(session.createSQLQuery("SELECT time_end FROM lot WHERE uuid = '" + uuidLot + "'").getResultList().get(0));

            DateFormat dateFormat = new SimpleDateFormat(PATTERN_TIME);
            Date reference = dateFormat.parse(ZERO_TIME);
            Date date = dateFormat.parse(timeEnd);
            LOGGER.debug("TIME END: " + date);
            long secondsEnd = (date.getTime() - reference.getTime()) / 1000L;

            reference = dateFormat.parse(ZERO_TIME);
            date = dateFormat.parse(valueOf(new SimpleDateFormat(PATTERN_TIME_WITH_MILLISECONDS).format(new Date().getTime())));
            LOGGER.debug("TIME NOW: " + date);
            long secondsNow = (date.getTime() - reference.getTime()) / 1000L;

            long diff = secondsEnd - secondsNow;

            if (diff < 0)
                return STATUS_LOT_CLOSE;
            else {
                LocalTime timeOfDay = LocalTime.ofSecondOfDay(diff);
                String time = valueOf(timeOfDay);
                LOGGER.debug("DIFF TIME: " + time);
                return valueOf(diff);
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
        }
        return null;
    }
}
