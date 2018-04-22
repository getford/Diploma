package by.iba.uzhyhala.admin;

import by.iba.uzhyhala.entity.LotEntity;
import by.iba.uzhyhala.lot.LotHandler;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = "/documenthandler")
public class DocumentHandler extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DocumentHandler.class);

    private byte[] base64document;
    private List<LotEntity> list = new LotHandler().getLots(VariablesUtil.QUERY_SELECT_ALL_LOT);
    private String userName;
    private String tableHead;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        int i = 100000000;
    }

    public DocumentHandler() {
        // TODO: get user name
        this.userName = "getford";
        // TODO: get parameter to get rows
        this.tableHead = "All lots";
    }

    public void generatePDF() {
        Document document = new Document(PageSize.A4);
        // TODO: generate password for doc
        String dateNow = String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_DATE_WITH_DOT).format(new Date()));
        String timeNow = String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_TIME).format(new Date()));
        String filePath = "documents/" + dateNow + "_" + timeNow + "_" + tableHead + ".pdf";
        String documentPassword = String.valueOf(UUID.randomUUID()).substring(0, 8);

        try {
            PdfPTable table = new PdfPTable(new float[]{20, 7, 4, 7, 7, 4});
            table.setTotalWidth(PageSize.A4.getWidth() - 10);
            table.setLockedWidth(true);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);

            table.addCell("uuid");
            table.addCell("Name");
            table.addCell("Cost");
            table.addCell("Date add");
            table.addCell("Date end");
            table.addCell("Status");
            table.setHeaderRows(1);

            PdfPCell[] cells = table.getRow(0).getCells();
            for (PdfPCell cell : cells) {
                cell.setBackgroundColor(BaseColor.WHITE);
            }
            for (int i = 1; i < list.size(); i++) {
                table.addCell(list.get(i).getUuid());
                table.addCell(list.get(i).getName());
                table.addCell(list.get(i).getCost());
                table.addCell(list.get(i).getDateAdd());
                table.addCell(list.get(i).getDateEnd());
                table.addCell(list.get(i).getStatus());
            }

            PdfWriter pdfWriter = PdfWriter.getInstance(document,
                    new FileOutputStream(filePath));
           /* pdfWriter.setEncryption(
                    documentPassword.getBytes(Charset.forName("UTF-8")),
                    VariablesUtil.PDF_OWNER_PASSCODE.getBytes(Charset.forName("UTF-8")),
                    PdfWriter.ALLOW_COPY,
                    PdfWriter.ENCRYPTION_AES_128);*/

            document.open();
            document.add(new Paragraph("Auction Diploma"));
            document.add(new Paragraph(String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_FULL_DATE_TIME).format(new Date()))));

            BarcodeQRCode barcodeQRCode = new BarcodeQRCode(filePath, 1000, 1000, null);
            Image codeQrImage = barcodeQRCode.getImage();
            codeQrImage.setAlignment(Image.ALIGN_RIGHT);
            codeQrImage.setAbsolutePosition(469, 729);
            codeQrImage.scaleAbsolute(100, 100);
            document.add(codeQrImage);

            document.add(new Paragraph("---------------------------------------------------------------" +
                    "-------------------------------------------------------------------"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph(tableHead));
            document.add(new Paragraph("\n"));
            document.add(table);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("---------------------------------------------------------------" +
                    "-------------------------------------------------------------------"));
            document.add(new Paragraph("Created by: " + userName));

            document.close();

            logger.info("PDF document successfully generated");
            logger.info("Password\t" + documentPassword);
        } catch (DocumentException | FileNotFoundException e) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n\n\n" + Arrays.toString(e.getStackTrace()));
            logger.error(e.getLocalizedMessage());
        }
    }

    public byte[] getBase64document() {
        return base64document;
    }
}
