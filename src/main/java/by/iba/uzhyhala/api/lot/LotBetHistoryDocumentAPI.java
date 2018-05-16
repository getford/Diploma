package by.iba.uzhyhala.api.lot;

import by.iba.uzhyhala.lot.DocumentHandler;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.MailUtil;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static by.iba.uzhyhala.util.VariablesUtil.*;


@WebServlet(urlPatterns = "/api/document/bet-history")
public class LotBetHistoryDocumentAPI extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LotBetHistoryDocumentAPI.class);
    private static final long serialVersionUID = 2780709724219293504L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.info("doGet method");
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            if (CommonUtil.isApiKeyValid(req.getParameter(PARAMETER_API_KEY_NAME))) {
                LOGGER.info("uuid lot: " + req.getParameter("uuid") +
                        ", api_key: " + req.getParameter(PARAMETER_API_KEY_NAME));
                String type = req.getParameter("type");
                switch (type) {
                    case PDF:
                        resp.getWriter().write(documentPDF(req, resp));
                        break;
                    case EXCEL:
                        resp.getWriter().write(documentExcel(req, resp));
                        break;
                    default:
                        resp.getWriter().write(documentPdfAndExcel(req, resp));
                        break;
                }
            } else
                resp.getWriter().write("{\"exception\":\"API key isnt correct\"}");
        } catch (Exception ex) {
            try {
                resp.getWriter().write("{\"exception\":\"" + ex.getLocalizedMessage() + "\"}");
            } catch (Exception e) {
                LOGGER.error(e.getLocalizedMessage());
                new MailUtil().sendErrorMail(Arrays.toString(e.getStackTrace()));
            }
            LOGGER.info("{\"exception\":\"" + ex.getLocalizedMessage() + "\"}");
            new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
        }
    }

    private String documentPDF(HttpServletRequest req, HttpServletResponse resp) {
        DocumentHandler documentHandler = new DocumentHandler();
        documentHandler.generateDocHistoryBetPDF(req.getParameter("uuid"), req, resp, false);

        return "{" +
                "\"status\": " + resp.getStatus() + ",\n" +
                "\"url\":\"" + documentHandler.getLotUrl() + "\"," +
                "\"type\": \"" + PDF + "\",\n" +
                "\"passcode\":\"" + documentHandler.getDocumentPasscode() + "\"," +
                "\"document\":\"" + documentHandler.getPdfBetEncode() + "\"}";
    }

    private String documentExcel(HttpServletRequest req, HttpServletResponse resp) {
        DocumentHandler documentHandler = new DocumentHandler();
        documentHandler.generateExcelDocHistoryBet(req, req.getParameter("uuid"), EXCEL_EXTENSION_XLSX, false);

        return "{" +
                "\"status\": " + resp.getStatus() + ",\n" +
                "\"url\":\"" + documentHandler.getLotUrl() + "\"," +
                "\"type\": \"" + EXCEL + "\"," +
                "\"document\":\"" + documentHandler.getExcelBetEncode() + "\"}";
    }

    private String documentPdfAndExcel(HttpServletRequest req, HttpServletResponse resp) {
        DocumentHandler documentHandler = new DocumentHandler();
        documentHandler.generateDocHistoryBetPDF(req.getParameter("uuid"), req, resp, false);
        documentHandler.generateExcelDocHistoryBet(req, req.getParameter("uuid"), EXCEL_EXTENSION_XLSX, false);

        return "{" +
                "\"status\": " + resp.getStatus() + ",\n" +
                "\"url\":\"" + documentHandler.getLotUrl() + "\"," +
                "\"type_" + PDF + "\": \"" + PDF + "\",\n" +
                "\"passcode_" + PDF + "\":\"" + documentHandler.getDocumentPasscode() + "\"," +
                "\"document_" + PDF + "\":\"" + documentHandler.getPdfBetEncode() + "\"," +
                "\"type_" + EXCEL + "\": \"" + EXCEL + "\"," +
                "\"document_" + EXCEL + "\":\"" + documentHandler.getExcelBetEncode() + "\"}";
    }
}
