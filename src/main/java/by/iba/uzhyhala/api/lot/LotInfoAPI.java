package by.iba.uzhyhala.api.lot;

import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.MailUtil;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static by.iba.uzhyhala.util.VariablesUtil.PARAMETER_API_KEY_NAME;


@WebServlet(urlPatterns = "/api/lot/info")
public class LotInfoAPI extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LotInfoAPI.class);
    private static final long serialVersionUID = -5847046564494053976L;

    @SuppressFBWarnings("XSS_REQUEST_PARAMETER_TO_SERVLET_WRITER")
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String message;
        String uuidLot = req.getParameter("uuid");
        try {
            if (CommonUtil.isApiKeyValid(req.getParameter(PARAMETER_API_KEY_NAME))) {
                LOGGER.info("uuid lot: " + uuidLot + ", api_key: " + req.getParameter(PARAMETER_API_KEY_NAME));
                String bulk = CommonUtil.getJsonBetBulk(uuidLot);
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