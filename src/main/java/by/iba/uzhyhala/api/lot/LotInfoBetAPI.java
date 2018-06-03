package by.iba.uzhyhala.api.lot;

import by.iba.uzhyhala.util.MailUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static by.iba.uzhyhala.util.CommonUtil.getJsonBetBulk;
import static by.iba.uzhyhala.util.CommonUtil.isApiKeyValid;
import static by.iba.uzhyhala.util.VariablesUtil.PARAMETER_API_KEY_NAME;

@WebServlet(urlPatterns = "/api/lot/infobet")
public class LotInfoBetAPI extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LotInfoBetAPI.class);
    private static final long serialVersionUID = -5847046564494053976L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String message;
        String uuidLot = req.getParameter("uuid");
        try {
            if (isApiKeyValid(req.getParameter(PARAMETER_API_KEY_NAME))) {
                LOGGER.info("uuid lot: " + uuidLot + ", api_key: " + req.getParameter(PARAMETER_API_KEY_NAME));
                String bulk = getJsonBetBulk(uuidLot);
                if (!StringUtils.isBlank(bulk))
                    message = bulk;
                else
                    message = "{\"uuid_lot\": \"" + uuidLot + "\",\"exception\": \"Info not found, please check uuid lot\"}";
            } else {
                message = "{\"exception\":\"Api key isnt valid\"}";
            }
            resp.getWriter().write(message);
        } catch (Exception ex) {
            try {
                resp.getWriter().write("{\"exception\":\"" + ex.getLocalizedMessage() + "\"}");
            } catch (Exception e) {
                LOGGER.error(e.getLocalizedMessage());
                new MailUtil().sendErrorMail(Arrays.toString(e.getStackTrace()));
            }
        }
    }
}