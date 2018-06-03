package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.lot.to.BetBulkTO;
import by.iba.uzhyhala.lot.to.BetTO;
import by.iba.uzhyhala.util.CookieUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import static by.iba.uzhyhala.util.CommonUtil.getJsonBetBulk;
import static by.iba.uzhyhala.util.CommonUtil.getLotDateEnd;
import static by.iba.uzhyhala.util.VariablesUtil.*;
import static java.lang.String.valueOf;


@WebServlet(urlPatterns = "/bethandler")
public class BetHandler extends HttpServlet implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(BetHandler.class);
    private static final long serialVersionUID = 835606950246195611L;

    private String uuidLot;
    private String uuidUser;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            this.uuidUser = new CookieUtil(req).getUserUuidFromToken();
            this.uuidLot = req.getParameter("uuid_lot");
            String timeNow = valueOf(new SimpleDateFormat(PATTERN_TIME).format(new Date().getTime()));
            doBet(prepareDoBet(Integer.parseInt(req.getParameter("cost")), timeNow, req), timeNow);

            resp.sendRedirect("/pages/lot.jsp?uuid=" + uuidLot);
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
        }
    }

    private String prepareDoBet(int bet, String timeNow, HttpServletRequest request) {
        LOGGER.info("PrepareDoBet method");
        BetBulkTO betBulkTO = new Gson().fromJson(getJsonBetBulk(uuidLot), BetBulkTO.class);
        List<BetTO> betTOList = new ArrayList<>(betBulkTO.getBets());
        BetTO betTO = new BetTO();

        int size = betBulkTO.getBets().size() - 1;
        if (betBulkTO.getStatus().equals(STATUS_LOT_ACTIVE) && bet >= betBulkTO.getStep()) {
            if (bet < betBulkTO.getBlitzCost()) {
                betBulkTO.setUuidClient(uuidUser);
                betTO.setUuidBet(UUID.randomUUID().toString());
                betTO.setBet(bet);
                betTO.setDate(valueOf(new SimpleDateFormat(PATTERN_DATE).format(new Date().getTime())));
                betTO.setOldCost(betBulkTO.getBets().get(size).getNewCost());
                betTO.setNewCost(betBulkTO.getBets().get(size).getNewCost() + bet);
                betTO.setUuidUser(uuidUser);
                betTO.setTime(timeNow);

                betTOList.add(betTO);
                betBulkTO.setBets(betTOList);
            } else {
                betBulkTO.setUuidClient(uuidUser);
                betBulkTO.setStatus(STATUS_LOT_SALES);
                new LotStatus().isUpdateLotStatus(STATUS_LOT_SALES, uuidLot, request);

                betTO.setUuidBet(UUID.randomUUID().toString());
                betTO.setBet(bet);
                betTO.setDate(valueOf(new SimpleDateFormat(PATTERN_DATE).format(new Date().getTime())));
                betTO.setOldCost(betBulkTO.getBets().get(size).getNewCost());
                betTO.setNewCost(betBulkTO.getBets().get(size).getNewCost() + bet);
                betTO.setUuidUser(uuidUser);
                betTO.setTime(timeNow);

                betTOList.add(betTO);
                betBulkTO.setBets(betTOList);
            }
        }

        return new Gson().toJson(betBulkTO);
    }

    private void doBet(String jsonBulk, String time) {
        LOGGER.info("doBet method");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("UPDATE " + ENTITY_BET + " SET bulk = :newBulk WHERE uuid = :uuid")
                    .setParameter("newBulk", jsonBulk)
                    .setParameter("uuid", uuidLot)
                    .executeUpdate();

            session.createQuery("UPDATE " + ENTITY_LOT + " SET time_end = :timeEnd WHERE uuid = :uuid")
                    .setParameter("timeEnd", getLotDateEnd(time, LOT_TIME_AFTER_BET_SEC))
                    .setParameter("uuid", uuidLot)
                    .executeUpdate();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
        }
    }
}
