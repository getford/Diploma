package by.iba.uzhyhala.api.lot;

import by.iba.uzhyhala.api.to.LotTOAPI;
import by.iba.uzhyhala.lot.LotHandler;
import by.iba.uzhyhala.util.CommonUtil;
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

import static by.iba.uzhyhala.util.VariablesUtil.PARAMETER_API_KEY_NAME;


@WebServlet(urlPatterns = "/api/lot/add")
public class LotAddAPI extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LotAddAPI.class);
    private static final long serialVersionUID = 7419178022015023781L;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.info("doPost Method");
        LotHandler lotHandler = new LotHandler();
        String responseMessage = "";
        String body = "";
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            LOGGER.debug(body);
            LotTOAPI lotTOAPI = handleResponse(body);

            if (CommonUtil.isApiKeyValid(req.getParameter(PARAMETER_API_KEY_NAME))) {
                if (validateRequest(lotTOAPI)) {
                    boolean isLotAdd = lotHandler.addLot(
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
            } else
                responseMessage = "{\"exception\":\"Api key isnt valid\"}";
            resp.getWriter().write(responseMessage);
        } catch (Exception ex) {
            try {
                resp.getWriter().write("{\"exception\":\"" + ex.getLocalizedMessage() + "\"}");
            } catch (Exception e) {
                LOGGER.error(e.getLocalizedMessage());
                new MailUtil().sendErrorMail(Arrays.toString(e.getStackTrace()));
            }
            new MailUtil().sendErrorMail(body + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
        }
    }

    private boolean validateRequest(LotTOAPI to) {
        LOGGER.info("validateRequest Method");
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