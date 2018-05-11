package by.iba.uzhyhala.api.lot;

import by.iba.uzhyhala.lot.DocumentHandler;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
                String type = req.getParameter("type");
                switch (type) {
                    case "pdf":
                        resp.getWriter().write(documentPDF(req, resp));
                        break;
                    case "excel":
                        resp.getWriter().write(documentExcel(req, resp));
                        break;
                    default:
                        break;
                }
            } else
                resp.getWriter().write("{\"exception\":\"API key isnt correct\"}");
        } catch (Exception ex) {
            LOGGER.info(getClass().getName() + "\t" + "{\"exception\":\"" + ex.getLocalizedMessage() + "\"}");
            resp.getWriter().write("{\"exception\":\"" + ex.getLocalizedMessage() + "\"}");
            new MailUtil().sendErrorMail(getClass().getName() + Arrays.toString(ex.getStackTrace()));
        }
    }

    private String documentPDF(HttpServletRequest req, HttpServletResponse resp) {
        DocumentHandler documentHandler = new DocumentHandler();
        documentHandler.generateDocHistoryBetPDF(req.getParameter("uuid"), req, resp);

        return "{" +
                "\"status\": " + resp.getStatus() + ",\n" +
                "\"type\": \"pdf\",\n" +
                "\"passcode\":\"" + documentHandler.getDocumentPasscode() + "\"," +
                "\"url\":\"" + documentHandler.getLotUrl() + "\"," +
                "\"document\":\"" + documentHandler.getPdfBetEncode() + "\"}";
    }

    private String documentExcel(HttpServletRequest req, HttpServletResponse resp) {
        DocumentHandler documentHandler = new DocumentHandler();
        documentHandler.generateExcelDocHistoryBet(req, req.getParameter("uuid"), VariablesUtil.EXCEL_EXTENSION_XLSX, false);

        return "{" +
                "\"status\": " + resp.getStatus() + ",\n" +
                "\"type\": \"excel\"," +
                "\"url\":\"" + documentHandler.getLotUrl() + "\"," +
                "\"document\":\"" + documentHandler.getExcelBetEncode() + "\"}";
    }
}
