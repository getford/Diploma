package by.iba.uzhyhala.api.lot;

import by.iba.uzhyhala.api.to.LotTOAPI;
import by.iba.uzhyhala.lot.LotHandler;
import by.iba.uzhyhala.util.MailUtil;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/api/lot/add")
public class LotHandlerAPI extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LotHandlerAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LotHandler lotHandler = new LotHandler();
        String body = null;
        String responseMessage = "";
        boolean isLotAdd;

        body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        logger.debug(getClass().getName() + body);
        LotTOAPI lotTOAPI = handleResponse(body);
        try {
            if (validateRequest(lotTOAPI)) {
                isLotAdd = lotHandler.addLot(
                        lotTOAPI.getUuidUserSeller(),
                        lotTOAPI.getName(),
                        lotTOAPI.getInformation(),
                        lotTOAPI.getCost(),
                        lotTOAPI.getBlitzCost(),
                        lotTOAPI.getStepCost(),
                        lotTOAPI.getDateStart(),
                        lotTOAPI.getTimeStart(),
                        lotTOAPI.getIdCategory());

                if (isLotAdd)
                    responseMessage = "{\"message\":\"Success\"}";
                else
                    responseMessage = "{\"message\":\"Failed\"}";
            } else
                responseMessage = "{\"message\":\"There is one or more field(s) is empty\"}";
        } catch (Exception ex) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n" + body + "\n\n\n" + Arrays.toString(ex.getStackTrace()));
            responseMessage = "{\"message\":\"There is one or more field(s) is empty\",\"exception\":\"" + ex.getLocalizedMessage() + "\"}";
            logger.error(ex.getStackTrace());
        } finally {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(responseMessage);
        }
    }

    private boolean validateRequest(LotTOAPI to) {
        return !StringUtils.isBlank(to.getUuidUserSeller()) &&
                !StringUtils.isBlank(to.getName()) &&
                !StringUtils.isBlank(to.getInformation()) &&
                !StringUtils.isBlank(to.getCost()) &&
                !StringUtils.isBlank(to.getBlitzCost()) &&
                !StringUtils.isBlank(to.getStepCost()) &&
                !StringUtils.isBlank(to.getDateStart()) &&
                !StringUtils.isBlank(to.getTimeStart()) &&
                !(to.getIdCategory() == null);
    }

    private LotTOAPI handleResponse(String args) {
        return new Gson().fromJson(args, LotTOAPI.class);
    }
}