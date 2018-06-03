package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.lot.to.BetBulkTO;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import static by.iba.uzhyhala.util.CommonUtil.*;
import static by.iba.uzhyhala.util.VariablesUtil.*;
import static java.lang.String.valueOf;

@WebServlet(urlPatterns = "/status")
public class LotStatus extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LotStatus.class);
    private static final long serialVersionUID = -363251488504441394L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        boolean isChangeStatus;
        if (Objects.requireNonNull(getHistoryBets(req.getParameter("uuid"))).size() > 1) {
            isChangeStatus = isUpdateLotStatus(STATUS_LOT_SALES, req.getParameter("uuid"), req);
            new DocumentHandler().generateConfirmationLotSalesPDF(req.getParameter("uuid"), req, resp);
        } else {
            isChangeStatus = isUpdateLotStatus(STATUS_LOT_CLOSE, req.getParameter("uuid"), req);
        }
        LOGGER.info("isChangeStatus: " + isChangeStatus);
    }

    boolean isUpdateLotStatus(String status, String uuidLot, HttpServletRequest request) {
        LOGGER.info("isUpdateLotStatus method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            BetBulkTO betBulkTO = new Gson().fromJson(getJsonBetBulk(uuidLot), BetBulkTO.class);
            betBulkTO.setStatus(status);

            session.beginTransaction();

            session.createQuery("UPDATE " + ENTITY_LOT + " SET status = :status WHERE uuid = :uuid")
                    .setParameter("status", status)
                    .setParameter("uuid", uuidLot)
                    .executeUpdate();
            session.createQuery("UPDATE " + ENTITY_LOT + " SET date_end = :dateEnd WHERE uuid = :uuid")
                    .setParameter("dateEnd", new SimpleDateFormat(PATTERN_DATE).format(new Date().getTime()))
                    .setParameter("uuid", uuidLot)
                    .executeUpdate();
            session.createQuery("UPDATE " + ENTITY_LOT + " SET uuid_user_client = :uuidUserClient WHERE uuid = :uuid")
                    .setParameter("uuidUserClient", betBulkTO.getUuidClient())
                    .setParameter("uuid", uuidLot)
                    .executeUpdate();
            session.createQuery("UPDATE " + ENTITY_BET + " SET bulk = :newBulk WHERE uuid = :uuid")
                    .setParameter("newBulk", new Gson().toJson(betBulkTO))
                    .setParameter("uuid", uuidLot)
                    .executeUpdate();

            URL url = new URL(valueOf(request.getRequestURL()));
            String subject = "Статус лота был успешно изменен";
            String body = "<br/> " + new SimpleDateFormat(PATTERN_FULL_DATE_TIME).format(new Date().getTime()) + "<br/>" +
                    "<p>Здравствуйте,</p>" +
                    "<p>Уведомляем вас о том, что статус вашего лота, был успешно изменен</p>" +
                    "<p>" +
                    "<b>Новый статус: </b>" + translateLotStatus(status) + "" +
                    "<br/><b>Уникальный идентификатор лота: </b>" + uuidLot + "</p>" +
                    "<p>You profile: <a href=\"" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort()
                    + "/pages/lot.jsp?uuid=" + uuidLot + "\">" + url.getProtocol() + "://" + url.getHost() + ":"
                    + url.getPort() + "/pages/lot.jsp?uuid="
                    + uuidLot + "</a></p>";

            new MailUtil().sendSimpleHtmlMail(getUserEmailByUUID(
                    getUUIDUserByUUIDLot(uuidLot)), body, subject);
            return true;
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("Method: isUpdateLotStatus\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return false;
        }
    }
}
