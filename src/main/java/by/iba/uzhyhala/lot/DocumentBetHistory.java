package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.lot.to.BetHistoryTO;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@WebServlet(urlPatterns = "/generatehistorybets")
public class DocumentBetHistory extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(DocumentBetHistory.class);
    private ByteArrayOutputStream byteArrayOutputStreamPDF = new ByteArrayOutputStream();
    private ByteArrayOutputStream byteArrayOutputStreamExcel = new ByteArrayOutputStream();
    private String documentPasscode;
    private String urlLot;
    private static String fileName = "Bet History";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String timeNow = String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_TIME).format(new Date()));
        fileName = "Bet_history_" + timeNow.replaceAll(":", ".") + "_" + req.getParameter("uuid_lot") + ".pdf";
        generateDocHistoryBetPDF(req.getParameter("uuid_lot"), req, resp);

    }

    public void generateDocHistoryBetPDF(String uuidLot, HttpServletRequest req, HttpServletResponse resp) {
        try {
            Document document = new Document(PageSize.A4);
            URL url = new URL(req.getRequestURL().toString());

            urlLot = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/pages/lot.jsp?uuid=" + uuidLot;
            documentPasscode = String.valueOf(UUID.randomUUID()).substring(0, 8);

            PdfPTable table = new PdfPTable(new float[]{20, 10, 10, 10});
            table.setTotalWidth(PageSize.A4.getWidth() - 10);
            table.setLockedWidth(true);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);

            table.addCell("First/Last name");
            table.addCell("Bet");
            table.addCell("Date");
            table.addCell("Time");
            table.setHeaderRows(1);

            PdfPCell[] cells = table.getRow(0).getCells();
            for (PdfPCell cell : cells) {
                cell.setBackgroundColor(BaseColor.WHITE);
            }

            List<BetHistoryTO> list = CommonUtil.getHistoryBets(uuidLot);

            assert list != null;
            for (int i = 1; i < list.size(); i++) {
                table.addCell(list.get(i).getUserName());
                table.addCell(String.valueOf(list.get(i).getBet()));
                table.addCell(list.get(i).getDate());
                table.addCell(list.get(i).getTime());
            }

            PdfWriter pdfWriter = null;

            // check api call
            if (!StringUtils.isBlank(req.getParameter(VariablesUtil.PARAMETER_API_KEY_NAME))) {
                resp.setContentType("application/json");
                pdfWriter = PdfWriter.getInstance(document, byteArrayOutputStreamPDF);
            } else {
                resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                resp.setContentType("application/pdf;charset=UTF-8");
                pdfWriter = PdfWriter.getInstance(document, resp.getOutputStream());
            }

            pdfWriter.setEncryption(
                    getDocumentPasscode().getBytes(StandardCharsets.UTF_8),
                    VariablesUtil.PDF_OWNER_PASSCODE.getBytes(StandardCharsets.UTF_8),
                    PdfWriter.ALLOW_COPY,
                    PdfWriter.ENCRYPTION_AES_128);

            document.open();

            document.addAuthor("Auction Diploma");
            document.addTitle("Bet history");

            document.add(new Paragraph("Auction Diploma"));
            document.add(new Paragraph("Bet history"));
            document.add(new Paragraph(String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_FULL_DATE_TIME).format(new Date()))));

            BarcodeQRCode barcodeQRCode = new BarcodeQRCode(getLotUrl(), 1000, 1000, null);
            Image codeQrImage = barcodeQRCode.getImage();
            codeQrImage.setAbsolutePosition(469, 729);
            codeQrImage.scaleAbsolute(100, 100);
            document.add(codeQrImage);

            document.add(new Paragraph("\n---------------------------------------------------------------" +
                    "-------------------------------------------------------------------"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(table);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("---------------------------------------------------------------" +
                    "-------------------------------------------------------------------"));
            document.add(new Paragraph("UUID lot: " + uuidLot));
            document.add(new Paragraph("URL lot: " + getLotUrl()));

            document.close();
            LOGGER.info("PDF document successfully generated");
            LOGGER.info("Document name\t" + fileName);
            LOGGER.info("Password\t" + getDocumentPasscode());
        } catch (IOException | DocumentException e) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n\n\n" + Arrays.toString(e.getStackTrace()));
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    public void generateDocHistoryBetExcel(String uuidLot, String extension) {
        List<Map<String, String>> dateList = new ArrayList<>();
        List<BetHistoryTO> list = CommonUtil.getHistoryBets(uuidLot);

        List<String> columnList = new ArrayList<>();
        columnList.add("Name");
        columnList.add("Bet");
        columnList.add("Date");
        columnList.add("Time");

        assert list != null;
        for (BetHistoryTO betHistoryTO : list) {
            Map<String, String> map = new HashMap<>();
            map.put(columnList.get(0), betHistoryTO.getUserName());
            map.put(columnList.get(1), String.valueOf(betHistoryTO.getBet()));
            map.put(columnList.get(2), betHistoryTO.getDate());
            map.put(columnList.get(3), betHistoryTO.getTime());
            dateList.add(map);
        }

        MailUtil mailUtil = new MailUtil();
        mailUtil.addAttachment(CommonUtil.prepareExcelFileForAttach(
                CommonUtil.createExcelFile(dateList, columnList, "Bet history"),
                fileName, extension));
        // TODO: send document
        mailUtil.sendErrorMailForAdmin("");

    }

    public String getPdfEncode() {
        return Base64.getEncoder().encodeToString(byteArrayOutputStreamPDF.toByteArray());
    }

    public String getExcelEncode() {
        return Base64.getEncoder().encodeToString(byteArrayOutputStreamExcel.toByteArray());
    }

    public String getDocumentPasscode() {
        return documentPasscode;
    }

    public String getLotUrl() {
        return urlLot;
    }
}
