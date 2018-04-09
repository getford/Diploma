package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.entity.BetEntity;
import by.iba.uzhyhala.entity.LotEntity;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = "/lothandler")
public class LotHandler extends HttpServlet implements Serializable {
    private static final Logger logger = Logger.getLogger(LotHandler.class);

    private Session session;
    private String type;
    private int idUser;
    private String uuidUser;

    public LotHandler(String loginOrEmail) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            logger.debug(getClass().getName() + " constructor");
            this.type = CommonUtil.loginOrEmail(loginOrEmail);
            this.idUser = CommonUtil.getIdUserByLoginEmail(session, loginOrEmail, type);
            this.uuidUser = CommonUtil.getUUIDUserByLoginEmail(session, loginOrEmail, type);
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + Arrays.toString(ex.getStackTrace()));
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public LotHandler() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        this.uuidUser = req.getParameter("uuid_user");  //hidden
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            this.idUser = CommonUtil.getIdUserByUUID(session, uuidUser);
            addLot(session, req.getParameter("name_lot"), req.getParameter("info_lot"), req.getParameter("cost"),
                    req.getParameter("blitz"), req.getParameter("step"), req.getParameter("date_start"),
                    req.getParameter("date_end"), req.getParameter("time_start"), req.getParameter("time_end"), 1);

        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void addLot(Session session, String name, String info, String cost, String blitz, String step, String dateStart,
                       String dateEnd, String timeStart, String timeEnd, int idCat) {
        logger.debug(getClass().getName() + " addLot");

        String dateNow = new SimpleDateFormat(VariablesUtil.PATTERN_DATE).format(new Date().getTime());
        String uuidNewLot = UUID.randomUUID().toString();
        try {
            LotEntity lotEntity = new LotEntity();
            lotEntity.setUuid(uuidNewLot);
            lotEntity.setIdUserSeller(idUser);
            lotEntity.setName(name);
            lotEntity.setInformation(info);
            lotEntity.setCost(cost);
            lotEntity.setBlitzCost(blitz);
            lotEntity.setStepCost(step);
            lotEntity.setDateAdd(dateNow);
            lotEntity.setDateStart(dateStart);
            lotEntity.setDateEnd(dateEnd);
            lotEntity.setTimeStart(timeStart);
            lotEntity.setTimeEnd(timeEnd);
            lotEntity.setIdCategory(idCat);
            if (String.valueOf(dateNow).equals(dateStart))
                lotEntity.setStatus(VariablesUtil.STATUS_LOT_ACTIVE);
            else
                lotEntity.setStatus(VariablesUtil.STATUS_LOT_WAIT);

            BetEntity betEntity = new BetEntity();
            betEntity.setUuid(uuidNewLot);
            betEntity.setBulk(prepareBetBulk(uuidUser, uuidNewLot, lotEntity.getStatus(), cost, blitz));

            session.save(lotEntity);
            session.save(betEntity);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    private String prepareBetBulk(String uuidUser, String uuidLot, String status, String cost, String blitz) {
        return "{\n" +
                "  \"uuid_lot\": \"" + uuidLot + "\",\n" +
                "  \"uuid_seller\": \"" + uuidUser + "\",\n" +
                "  \"uuid_client\": \"\",\n" +
                "  \"status\": \"" + status + "\",\n" +
                "  \"bets\": [\n" +
                "    {\n" +
                "      \"uuid_user\": \"" + uuidUser + "\",\n" +
                "      \"uuid_bet\": \"" + UUID.randomUUID().toString() + "\",\n" +
                "      \"bet\": 0,\n" +
                "      \"blitz_cost\":" + Integer.parseInt(blitz) + ",\n" +
                "      \"old_cost\": " + Integer.parseInt(cost) + ",\n" +
                "      \"new_cost\":" + Integer.parseInt(cost) + ",\n" +
                "      \"date\": \"" + String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_DATE).format(new Date().getTime())) + "\",\n" +
                "      \"time\": \"" + String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_TIME).format(new Date().getTime())) + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    public void deleteLot(String uuid) {
        logger.debug(getClass().getName() + " deleteLot");

    }

    public List<LotEntity> getAllLots() {
        logger.debug(getClass().getName() + " showLots");
        return session.createQuery("SELECT l FROM " + VariablesUtil.ENTITY_LOT + " l").list();
    }

    public List<LotEntity> getUserLot() {
        logger.debug(getClass().getName() + " getUserLot");
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            return session.createQuery("SELECT l FROM " + VariablesUtil.ENTITY_LOT + " l WHERE id_user_seller = :id", LotEntity.class)
                    .setParameter("id", idUser).getResultList();
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }
}
