package by.iba.uzhyhala.admin;

import by.iba.uzhyhala.entity.LotEntity;
import by.iba.uzhyhala.lot.LotHandler;
import by.iba.uzhyhala.util.MailUtil;
import by.iba.uzhyhala.util.VariablesUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DocumentHandler {
    private static final Logger logger = Logger.getLogger(DocumentHandler.class);

    private List<LotEntity> list = new LotHandler().getAllLots();
    private String userName;
    private String tableHead;

    public DocumentHandler() {
        // TODO: get user name
        this.userName = "getford";
        // TODO: get parameter to get rows
        this.tableHead = "All lots";
    }

    public void generatePDF() {
        Document document = new Document();

        String dateNow = String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_DATE_DOC).format(new Date()));
        String timeNow = String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_TIME_DOC).format(new Date()));

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

            PdfWriter.getInstance(document,
                    new FileOutputStream("documents/" + dateNow + "_" + timeNow + "_" + tableHead + ".pdf"));

            document.open();
            document.add(new Paragraph("Auction Diploma"));
            document.add(new Paragraph(String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_DATE_TIME).format(new Date()))));
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
        } catch (DocumentException | FileNotFoundException e) {
            new MailUtil().sendErrorMailForAdmin(getClass().getName() + "\n\n\n" + Arrays.toString(e.getStackTrace()));
            logger.error(e.getLocalizedMessage());
        }
    }
}
