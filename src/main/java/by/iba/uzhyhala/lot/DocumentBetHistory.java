package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.lot.to.BetHistoryTO;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.HibernateUtil;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = "/generatehistorybets")
public class DocumentBetHistory extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(DocumentBetHistory.class);
    private URL url;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            url = new URL(req.getRequestURL().toString());
            if (generateDocHistoryBet(req.getParameter("uuid_lot"), resp)) {
                resp.sendRedirect("/pages/lot.jsp?uuid=" + req.getParameter("uuid_lot"));
            } else {
                resp.sendRedirect("/pages/lot.jsp?uuid=" + req.getParameter("uuid_lot"));
            }
        } catch (IOException e) {
            LOGGER.error(e.getStackTrace());
        }
    }

    private boolean generateDocHistoryBet(String uuidLot, HttpServletResponse resp) throws IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Document document = new Document(PageSize.A4);
        resp.setHeader("Content-Disposition",
                "attachment;filename=Bet_history_" + uuidLot + ".pdf");
        resp.setContentType("application/pdf;charset=UTF-8");
        String documentPassword = String.valueOf(UUID.randomUUID()).substring(0, 8);
        String toEncode = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/pages/lot.jsp?uuid=" + uuidLot;

        try {
            PdfPTable table = new PdfPTable(new float[]{20, 10, 10, 10});
            table.setTotalWidth(PageSize.A4.getWidth() - 10);
            table.setLockedWidth(true);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);

            table.addCell("User name");
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

            PdfWriter pdfWriter = PdfWriter.getInstance(document, resp.getOutputStream());
            pdfWriter.setEncryption(
                    documentPassword.getBytes(Charset.forName("UTF-8")),
                    VariablesUtil.PDF_OWNER_PASSWORD.getBytes(Charset.forName("UTF-8")),
                    PdfWriter.ALLOW_COPY,
                    PdfWriter.ENCRYPTION_AES_128);

            document.open();
            document.add(new Paragraph("Auction Diploma"));
            document.add(new Paragraph("Bet history"));
            document.add(new Paragraph(String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_DATE_TIME).format(new Date()))));

            BarcodeQRCode barcodeQRCode = new BarcodeQRCode(toEncode, 1000, 1000, null);
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
            //   document.add(new Paragraph("Created by: " + CommonUtil.getUserFirstLastNameByUUID(session, uuidLot)));
            document.add(new Paragraph("UUID Lot: " + uuidLot));
            document.close();
            LOGGER.info("PDF document successfully generated");
            LOGGER.info("Password\t" + documentPassword);
            return true;
        } catch (DocumentException | FileNotFoundException e) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n\n\n" + Arrays.toString(e.getStackTrace()));
            LOGGER.error(e.getLocalizedMessage());
            return false;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }
}
