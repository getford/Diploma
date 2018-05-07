package by.iba.uzhyhala.api.lot;

import by.iba.uzhyhala.lot.DocumentBetHistory;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

@WebServlet(urlPatterns = "/api/document/bet-history")
public class LotBetHistoryDocumentAPI extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LotBetHistoryDocumentAPI.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.info(getClass().getName() + "\t" + "doGet method");
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            if (CommonUtil.isApiKeyValid(req.getParameter(VariablesUtil.PARAMETER_API_KEY_NAME))) {
                LOGGER.info("uuid lot: " + req.getParameter("uuid") +
                        ", api_key: " + req.getParameter(VariablesUtil.PARAMETER_API_KEY_NAME));
                DocumentBetHistory documentBetHistory = new DocumentBetHistory();
                documentBetHistory.generateDocHistoryBet(req.getParameter("uuid"), req, resp, new URL(req.getRequestURL().toString()));

                resp.getWriter().write("{" +
                        "\"status\": " + resp.getStatus() + ",\n" +
                        "\"passcode\":\"" + documentBetHistory.getDocumentPasscode() + "\"," +
                        "\"url\":\"" + documentBetHistory.getLotUrl() + "\"," +
                        "\"document\":\"" + documentBetHistory.getPdfEncode() + "\"}");
            } else
                resp.getWriter().write("{\"exception\":\"API key isnt correct\"}");
        } catch (Exception ex) {
            LOGGER.info(getClass().getName() + "\t" + "{\"exception\":\"" + ex.getLocalizedMessage() + "\"}");
            resp.getWriter().write("{\"exception\":\"" + ex.getLocalizedMessage() + "\"}");
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + Arrays.toString(ex.getStackTrace()));
        }
    }
}
