package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.entity.BetEntity;
import by.iba.uzhyhala.entity.LotEntity;
import by.iba.uzhyhala.util.CookieUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static by.iba.uzhyhala.util.CommonUtil.*;
import static by.iba.uzhyhala.util.VariablesUtil.*;
import static java.lang.String.valueOf;

@WebServlet(urlPatterns = "/lothandler")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 100,
        maxFileSize = 1024 * 1024 * 100,
        maxRequestSize = 1024 * 1024 * 100)
public class LotHandler extends HttpServlet implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(LotHandler.class);
    private static final long serialVersionUID = 6295721900470243790L;
    private static final String NEW_LINE = "\",\n";

    private String uuidUser;
    private String uuidAddLot;

    public LotHandler() {
    }

    LotHandler(String loginOrEmail) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            LOGGER.debug(" constructor");
            this.uuidUser = getUUIDUserByLoginEmail(loginOrEmail, loginOrEmail(loginOrEmail));
        } catch (Exception e) {
            new MailUtil().sendErrorMail(Arrays.toString(e.getStackTrace()));
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            this.uuidUser = new CookieUtil(req).getUserUuidFromToken();
            boolean isLotAdd = addLot(
                    uuidUser,
                    req.getParameter("name_lot").trim(),
                    req.getParameter("info_lot").trim(),
                    req.getParameter("cost").trim(),
                    req.getParameter("blitz").trim(),
                    req.getParameter("step").trim(),
                    req.getParameter("date_start").trim(),
                    req.getParameter("time_start").trim(),
                    Integer.parseInt(req.getParameter("id_category")),
                    saveUploadFile(req).trim()
            );
            if (isLotAdd)
                resp.sendRedirect("/pages/lot.jsp?uuid=" + uuidAddLot);
            else {
                PrintWriter printWriter = resp.getWriter();
                printWriter.println("Add lot have some error");
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
        }
    }

    public boolean addLot(String uuidUserSeller, String name, String info, String cost, String blitz,
                          String step, String dateStart, String timeStart, int idCat, String fileName) throws ParseException {
        LOGGER.debug("addLot");

        String dateNow = new SimpleDateFormat(PATTERN_DATE).format(new Date().getTime());
        String dateStartParsed = new SimpleDateFormat(PATTERN_DATE).format(
                new SimpleDateFormat(PATTERN_DATE_REVERSE).parse(dateStart));
        this.uuidAddLot = UUID.randomUUID().toString();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            LotEntity lotEntity = new LotEntity();
            lotEntity.setUuid(uuidAddLot);
            lotEntity.setUuidUserSeller(uuidUserSeller);
            lotEntity.setName(name);
            lotEntity.setInformation(info);
            lotEntity.setCost(cost);
            lotEntity.setBlitzCost(blitz);
            lotEntity.setStepCost(step);
            lotEntity.setDateAdd(dateNow);
            lotEntity.setDateStart(dateStartParsed);
            lotEntity.setTimeStart(timeStart + ":00");
            lotEntity.setTimeEnd(getLotDateEnd(timeStart + ":00", LOT_TIME_SEC));
            lotEntity.setIdCategory(idCat);
            lotEntity.setImagesName(fileName);

            if (valueOf(dateNow).equals(lotEntity.getDateStart()))
                lotEntity.setStatus(STATUS_LOT_ACTIVE);
            else
                lotEntity.setStatus(STATUS_LOT_WAIT);

            BetEntity betEntity = new BetEntity();
            betEntity.setUuid(uuidAddLot);
            betEntity.setBulk(prepareBetBulk(uuidUserSeller, uuidAddLot, lotEntity.getStatus(), cost, blitz, step));

            session.save(lotEntity);
            session.save(betEntity);
            session.getTransaction().commit();
            session.clear();
            return true;
        } catch (Exception e) {
            new MailUtil().sendErrorMail(Arrays.toString(e.getStackTrace()));
            LOGGER.error(e.getLocalizedMessage());
            return false;
        }
    }

    private String prepareBetBulk(String uuidUser, String uuidLot, String status, String cost, String blitz, String step) {
        return "{\n" +
                "  \"uuid_lot\": \"" + uuidLot.trim() + NEW_LINE +
                "  \"uuid_seller\": \"" + uuidUser.trim() + NEW_LINE +
                "  \"uuid_client\": \"\",\n" +
                "  \"status\": \"" + status.trim() + NEW_LINE +
                "  \"blitz_cost\": \"" + Integer.parseInt(blitz.trim()) + NEW_LINE +
                "  \"step\": \"" + Integer.parseInt(step.trim()) + NEW_LINE +
                "  \"bets\": [\n" +
                "    {\n" +
                "      \"uuid_user\": \"" + uuidUser.trim() + NEW_LINE +
                "      \"uuid_bet\": \"" + UUID.randomUUID().toString() + NEW_LINE +
                "      \"bet\": 0,\n" +
                "      \"old_cost\": " + Integer.parseInt(cost.trim()) + ",\n" +
                "      \"new_cost\": " + Integer.parseInt(cost.trim()) + ",\n" +
                "      \"date\": \"" + new SimpleDateFormat(PATTERN_DATE).format(new Date().getTime()).trim() + NEW_LINE +
                "      \"time\": \"" + new SimpleDateFormat(PATTERN_TIME_WITH_MILLISECONDS).format(new Date().getTime()).trim() + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}
