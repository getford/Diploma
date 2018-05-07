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
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private String documentPasscode;
    private String urlLot;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            URL url = new URL(req.getRequestURL().toString());
            generateDocHistoryBet(req.getParameter("uuid_lot"), req, resp, url);
        } catch (IOException | DocumentException e) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n\n\n" + Arrays.toString(e.getStackTrace()));
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    public void generateDocHistoryBet(String uuidLot, HttpServletRequest req, HttpServletResponse resp, URL url) throws DocumentException, IOException {

        Document document = new Document(PageSize.A4);
        String timeNow = String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_TIME).format(new Date()));
        String fileName = "Bet_history_" + timeNow.replaceAll(":", ".") + "_" + uuidLot + ".pdf";

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
            pdfWriter = PdfWriter.getInstance(document, byteArrayOutputStream);
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
//        document.close();
        LOGGER.info("PDF document successfully generated");
        LOGGER.info("Document name\t" + fileName);
        LOGGER.info("Password\t" + getDocumentPasscode());
    }

    public String getPdfEncode() {
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    public String getDocumentPasscode() {
        return documentPasscode;
    }

    public String getLotUrl(){
        return urlLot;
    }
}
