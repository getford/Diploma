package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.lot.to.BetBulkTO;
import by.iba.uzhyhala.lot.to.BetTO;
import by.iba.uzhyhala.util.*;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(urlPatterns = "/bethandler")
public class BetHandler extends HttpServlet implements Serializable {
    private static final Logger logger = Logger.getLogger(BetHandler.class);

    private Session session = null;
    private String uuidLot;
    private String uuidUser;
    private String errorMessage;
    private Gson gson = new Gson();

    public BetHandler() {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.uuidUser = new CookieUtil(req).getUserUuidFromToken();
        this.uuidLot = req.getParameter("uuid_lot");
        try {
            String timeNow = String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_TIME).format(new Date().getTime()));
            doBet(prepareDoBet(Integer.parseInt(req.getParameter("cost")), timeNow), timeNow);
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());
        }
    }

    private String prepareDoBet(int bet, String timeNow) {
        BetBulkTO betBulkTO = gson.fromJson(getJsonBulk(), BetBulkTO.class);
        List<BetTO> betTOList = new ArrayList<>(betBulkTO.getBets());
        BetTO betTO = new BetTO();

        int size = betBulkTO.getBets().size() - 1;
        if (betBulkTO.getStatus().equals(VariablesUtil.STATUS_LOT_ACTIVE)) {
            if (bet < betBulkTO.getBlitzCost() && bet >= betBulkTO.getStep()) {
                betTO.setUuidBet(UUID.randomUUID().toString());
                betTO.setBet(bet);
                betTO.setDate(String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_DATE).format(new Date().getTime())));
                betTO.setOldCost(betBulkTO.getBets().get(size).getNewCost());
                betTO.setNewCost(betBulkTO.getBets().get(size).getNewCost() + bet);
                betTO.setUuidUser(uuidUser);
                betTO.setTime(timeNow);

                betTOList.add(betTO);
                betBulkTO.setBets(betTOList);
            } else {
                betBulkTO.setUuidClient(uuidUser);
                betBulkTO.setStatus(VariablesUtil.STATUS_LOT_SALES);

                betTO.setUuidBet(UUID.randomUUID().toString());
                betTO.setBet(bet);
                betTO.setDate(String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_DATE).format(new Date().getTime())));
                betTO.setOldCost(betBulkTO.getBets().get(size).getNewCost());
                betTO.setNewCost(betBulkTO.getBets().get(size).getNewCost() + bet);
                betTO.setUuidUser(uuidUser);
                betTO.setTime(timeNow);

                betTOList.add(betTO);
                betBulkTO.setBets(betTOList);
            }
        }

        return gson.toJson(betBulkTO);
    }

    private void doBet(String jsonBulk, String time) {
        try {
            session.createQuery("UPDATE " + VariablesUtil.ENTITY_BET + " SET bulk = :newBulk WHERE uuid = :uuid")
                    .setParameter("newBulk", jsonBulk)
                    .setParameter("uuid", uuidLot)
                    .executeUpdate();

            session.createQuery("UPDATE " + VariablesUtil.ENTITY_LOT + " SET time_end = :dateEnd WHERE uuid = :uuid")
                    .setParameter("dateEnd", CommonUtil.getPrepareDateEnd(time, VariablesUtil.LOT_TIME_AFTER_BET_SEC))
                    .setParameter("uuid", uuidLot)
                    .executeUpdate();
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getLocalizedMessage());
            // return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    private String getJsonBulk() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            return String.valueOf(session.createQuery("SELECT b.bulk FROM " + VariablesUtil.ENTITY_BET + " b WHERE uuid = :uuid")
                    .setParameter("uuid", uuidLot).list().get(0));

        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + Arrays.toString(ex.getStackTrace()));
            logger.error(ex.getStackTrace());
            return null;
        }
    }
}
