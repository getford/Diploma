package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.entity.BetEntity;
import by.iba.uzhyhala.entity.LotEntity;
import by.iba.uzhyhala.util.*;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
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
    private String uuidAddLot;

    public LotHandler() {
    }

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            this.uuidUser = new CookieUtil(req).getUserUuidFromToken();
            this.idUser = CommonUtil.getIdUserByUUID(session, uuidUser);
            boolean isLotAdd = addLot(session, req.getParameter("name_lot"), req.getParameter("info_lot"), req.getParameter("cost"),
                    req.getParameter("blitz"), req.getParameter("step"), req.getParameter("date_start"),
                    req.getParameter("date_end"), req.getParameter("time_start"), req.getParameter("time_end"), 1);
            if (isLotAdd)
                resp.sendRedirect("/pages/lot.jsp?uuid=" + uuidAddLot);
            else {
                PrintWriter printWriter = resp.getWriter();
                printWriter.println("Add lot have some error");
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    // TODO: id category
    private boolean addLot(Session session, String name, String info, String cost, String blitz, String step, String dateStart,
                           String dateEnd, String timeStart, String timeEnd, int idCat) {
        logger.debug(getClass().getName() + " addLot");

        String dateNow = new SimpleDateFormat(VariablesUtil.PATTERN_DATE).format(new Date().getTime());
        this.uuidAddLot = UUID.randomUUID().toString();
        try {
            LotEntity lotEntity = new LotEntity();
            lotEntity.setUuid(uuidAddLot);
            lotEntity.setIdUserSeller(idUser);
            lotEntity.setName(name);
            lotEntity.setInformation(info);
            lotEntity.setCost(cost);
            lotEntity.setBlitzCost(blitz);
            lotEntity.setStepCost(step);
            lotEntity.setDateAdd(dateNow);
            lotEntity.setDateStart(new SimpleDateFormat(VariablesUtil.PATTERN_DATE).format(
                    new SimpleDateFormat(VariablesUtil.PATTERN_DATE_REVERSE).parse(dateStart)));
            lotEntity.setDateEnd(new SimpleDateFormat(VariablesUtil.PATTERN_DATE).format(
                    new SimpleDateFormat(VariablesUtil.PATTERN_DATE_REVERSE).parse(dateEnd)));
            lotEntity.setTimeStart(timeStart);
            lotEntity.setTimeEnd(timeEnd);
            lotEntity.setIdCategory(idCat);
            if (String.valueOf(dateNow).equals(lotEntity.getDateStart()))
                lotEntity.setStatus(VariablesUtil.STATUS_LOT_ACTIVE);
            else
                lotEntity.setStatus(VariablesUtil.STATUS_LOT_WAIT);

            BetEntity betEntity = new BetEntity();
            betEntity.setUuid(uuidAddLot);
            betEntity.setBulk(prepareBetBulk(uuidUser, uuidAddLot, lotEntity.getStatus(), cost, blitz, step));

            session.save(lotEntity);
            session.save(betEntity);
            session.getTransaction().commit();
            session.clear();
            return true;
        } catch (Exception e) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n\n\n" + Arrays.toString(e.getStackTrace()));
            logger.error(e.getLocalizedMessage());
            return false;
        }
    }

    private String prepareBetBulk(String uuidUser, String uuidLot, String status, String cost, String blitz, String step) {
        return "{\n" +
                "  \"uuid_lot\": \"" + uuidLot + "\",\n" +
                "  \"uuid_seller\": \"" + uuidUser + "\",\n" +
                "  \"uuid_client\": \"\",\n" +
                "  \"status\": \"" + status + "\",\n" +
                "  \"blitz_cost\": \"" + Integer.parseInt(blitz) + "\",\n" +
                "  \"step\": \"" + Integer.parseInt(step) + "\",\n" +
                "  \"bets\": [\n" +
                "    {\n" +
                "      \"uuid_user\": \"" + uuidUser + "\",\n" +
                "      \"uuid_bet\": \"" + UUID.randomUUID().toString() + "\",\n" +
                "      \"bet\": 0,\n" +
                "      \"old_cost\": " + Integer.parseInt(cost) + ",\n" +
                "      \"new_cost\": " + Integer.parseInt(cost) + ",\n" +
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
