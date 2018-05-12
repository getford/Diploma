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
    private static final Logger LOGGER = Logger.getLogger(LotHandler.class);
    private static final long serialVersionUID = 1;

    private Session session;
    private String type;
    private String uuidUser;
    private String uuidAddLot;

    public LotHandler() {
    }

    public LotHandler(String loginOrEmail) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            LOGGER.debug(getClass().getName() + " constructor");
            this.type = CommonUtil.loginOrEmail(loginOrEmail);
            this.uuidUser = CommonUtil.getUUIDUserByLoginEmail(session, loginOrEmail, type);
        } catch (Exception e) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n\n\n" + Arrays.toString(e.getStackTrace()));
            LOGGER.error(e.getLocalizedMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String timeStart = req.getParameter("time_start");
        try {
            this.uuidUser = new CookieUtil(req).getUserUuidFromToken();
            boolean isLotAdd = addLot(
                    uuidUser,
                    req.getParameter("name_lot"),
                    req.getParameter("info_lot"),
                    req.getParameter("cost"),
                    req.getParameter("blitz"),
                    req.getParameter("step"),
                    req.getParameter("date_start"),
                    timeStart,
                    1);
            if (isLotAdd)
                resp.sendRedirect("/pages/lot.jsp?uuid=" + uuidAddLot);
            else {
                PrintWriter printWriter = resp.getWriter();
                printWriter.println("Add lot have some error");
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
        }
    }

    // TODO: id category
    public boolean addLot(String uuidUserSeller, String name, String info, String cost, String blitz, String step, String dateStart, String timeStart, int idCat) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        LOGGER.debug(getClass().getName() + " addLot");

        String dateNow = new SimpleDateFormat(VariablesUtil.PATTERN_DATE).format(new Date().getTime());
        this.uuidAddLot = UUID.randomUUID().toString();
        try {
            LotEntity lotEntity = new LotEntity();
            lotEntity.setUuid(uuidAddLot);
            lotEntity.setUuidUserSeller(uuidUserSeller);
            lotEntity.setName(name);
            lotEntity.setInformation(info);
            lotEntity.setCost(cost);
            lotEntity.setBlitzCost(blitz);
            lotEntity.setStepCost(step);
            lotEntity.setDateAdd(dateNow);
            lotEntity.setDateStart(new SimpleDateFormat(VariablesUtil.PATTERN_DATE).format(
                    new SimpleDateFormat(VariablesUtil.PATTERN_DATE_REVERSE).parse(dateStart)));
            /*lotEntity.setDateEnd(new SimpleDateFormat(VariablesUtil.PATTERN_DATE).format(
                    new SimpleDateFormat(VariablesUtil.PATTERN_DATE_REVERSE).parse(dateEnd)));*/
            lotEntity.setTimeStart(timeStart + ":00");
            // lotEntity.setTimeEnd(timeEnd);
            lotEntity.setTimeEnd(CommonUtil.getLotDateEnd(timeStart + ":00", VariablesUtil.LOT_TIME_SEC));
            lotEntity.setIdCategory(idCat);
            if (String.valueOf(dateNow).equals(lotEntity.getDateStart()))
                lotEntity.setStatus(VariablesUtil.STATUS_LOT_ACTIVE);
            else
                lotEntity.setStatus(VariablesUtil.STATUS_LOT_WAIT);

            BetEntity betEntity = new BetEntity();
            betEntity.setUuid(uuidAddLot);
            betEntity.setBulk(prepareBetBulk(uuidUserSeller, uuidAddLot, lotEntity.getStatus(), cost, blitz, step));

            session.save(lotEntity);
            session.save(betEntity);
            session.getTransaction().commit();
            session.clear();
            return true;
        } catch (Exception e) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n\n\n" + Arrays.toString(e.getStackTrace()));
            LOGGER.error(e.getLocalizedMessage());
            return false;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
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
                "      \"time\": \"" + String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_TIME_WITH_MILLISECONDS).format(new Date().getTime())) + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    public void deleteLot(String uuid) {
        LOGGER.debug(getClass().getName() + " deleteLot");

    }

    public List<LotEntity> getLots(String query) {
        LOGGER.debug(getClass().getName() + " showLots");
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery(query).getResultList();
        } catch (Exception e) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n\n\n" + Arrays.toString(e.getStackTrace()));
            LOGGER.error(e.getLocalizedMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }

    public List<LotEntity> getUserLot() {
        LOGGER.debug(getClass().getName() + " getUserLot");
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("SELECT l FROM " + VariablesUtil.ENTITY_LOT + " l WHERE uuidUserSeller = :uuid", LotEntity.class)
                    .setParameter("uuid", uuidUser).getResultList();
        } catch (Exception e) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n\n\n" + Arrays.toString(e.getStackTrace()));
            LOGGER.error(e.getLocalizedMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }
}
