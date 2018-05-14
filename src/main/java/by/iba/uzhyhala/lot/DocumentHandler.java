package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.entity.LotEntity;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@WebServlet(urlPatterns = "/generatehistorybets")
public class DocumentHandler extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(DocumentHandler.class);
    private ByteArrayOutputStream byteArrayOutputStreamPDF = new ByteArrayOutputStream();
    private String documentPasscode;
    private String urlLot;
    private static String fileName = "File_";
    private byte[] bytesExcel = null;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String timeNow = String.valueOf(new SimpleDateFormat(VariablesUtil.PATTERN_TIME).format(new Date()));
        String uuidLot = req.getParameter("uuid_lot");
        String redirectUrl = "/pages/lot.jsp?uuid=" + uuidLot;
        fileName = "Bet_history_" + timeNow.replaceAll(":", ".") + "_" + uuidLot;

        try {
            switch (req.getParameter("send-mail")) {
                case "true":
                    if (req.getParameter("type").equals(VariablesUtil.PDF)) {
                        fileName += VariablesUtil.PDF_EXTENSION;
                        generateDocHistoryBetPDF(uuidLot, req, resp, true);
                    } else if (req.getParameter("type").equals(VariablesUtil.EXCEL)) {
                        fileName += VariablesUtil.EXCEL_EXTENSION_XLSX;
                        generateExcelDocHistoryBet(req, uuidLot, VariablesUtil.EXCEL_EXTENSION_XLSX, true);
                    }
                    LOGGER.info("Document " + fileName + " create/send successfully");
                    resp.sendRedirect(redirectUrl);
                    break;
                case "false":
                    if (req.getParameter("type").equals(VariablesUtil.PDF)) {
                        fileName += VariablesUtil.PDF_EXTENSION;
                        generateDocHistoryBetPDF(uuidLot, req, resp, false);
                    } else if (req.getParameter("type").equals(VariablesUtil.EXCEL)) {
                        fileName += VariablesUtil.EXCEL_EXTENSION_XLSX;
                        generateExcelDocHistoryBet(req, uuidLot, VariablesUtil.EXCEL_EXTENSION_XLSX, false);
                    }
                    LOGGER.info("Document " + fileName + " create/send successfully");
                    resp.sendRedirect(redirectUrl);
                    break;
                default:
                    LOGGER.error("Error: document " + fileName + " don't create/send successfully");
                    break;
            }
        } catch (Exception e) {
            try {
                LOGGER.error(e.getLocalizedMessage());
                resp.sendRedirect(redirectUrl);
            } catch (IOException ex) {
                new MailUtil().sendErrorMail(getClass().getName() + "\n\n\n" + Arrays.toString(ex.getStackTrace()));
                LOGGER.error(ex.getLocalizedMessage());
            }
        }

    }

    public void generateDocHistoryBetPDF(String uuidLot, HttpServletRequest req, HttpServletResponse resp, boolean isSendMail) {
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
            if (!StringUtils.isBlank(req.getParameter(VariablesUtil.PARAMETER_API_KEY_NAME)) && !isSendMail) {
                resp.setContentType("application/json");
                pdfWriter = PdfWriter.getInstance(document, byteArrayOutputStreamPDF);

            } else {
                if (!isSendMail) {
                    resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                    resp.setContentType("application/pdf;charset=UTF-8");
                    pdfWriter = PdfWriter.getInstance(document, resp.getOutputStream());
                } else {
                    pdfWriter = PdfWriter.getInstance(document, byteArrayOutputStreamPDF);
                }
            }

            // passcode for document
            pdfWriter.setEncryption(
                    getDocumentPasscode().getBytes(StandardCharsets.UTF_8),
                    VariablesUtil.PDF_OWNER_PASSCODE.getBytes(StandardCharsets.UTF_8),
                    PdfWriter.ALLOW_COPY,
                    PdfWriter.ENCRYPTION_AES_128
            );

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

            if (isSendMail) {
                MailUtil mailUtil = new MailUtil();
                mailUtil.addAttachment(CommonUtil.prepareFileForAttach(byteArrayOutputStreamPDF,
                        fileName, VariablesUtil.PDF_EXTENSION)
                );
                // TODO: send document
                mailUtil.sendPdfFileWithPasscode("", documentPasscode, urlLot);
            } else {
                LOGGER.info("Send document not required");
            }

            LOGGER.info("PDF document successfully generated");
            LOGGER.info("Document name\t" + fileName);
            LOGGER.info("Password\t" + getDocumentPasscode());
        } catch (IOException | DocumentException e) {
            new MailUtil().sendErrorMail(getClass().getName() + "\n\n\n" + Arrays.toString(e.getStackTrace()));
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    public void generateExcelDocHistoryBet(HttpServletRequest req, String uuidLot, String extension, boolean isSendMail) {
        String sheetName = "Bet history";
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

        if (isSendMail) {
            MailUtil mailUtil = new MailUtil();
            mailUtil.addAttachment(CommonUtil.prepareFileForAttach(
                    CommonUtil.createExcelFile(dateList, columnList, sheetName),
                    fileName, extension));
            // TODO: send document
            mailUtil.sendSimpleEmailWithAttachment("");
        } else {
            try {
                URL url = new URL(req.getRequestURL().toString());
                urlLot = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/pages/lot.jsp?uuid=" + uuidLot;

                File file = new File(String.valueOf(CommonUtil.prepareFileForAttach(
                        CommonUtil.createExcelFile(dateList, columnList, sheetName),
                        fileName, extension)));

                FileInputStream fis = new FileInputStream(file);
                bytesExcel = new byte[(int) file.length()];
                fis.read(bytesExcel);
            } catch (IOException e) {
                new MailUtil().sendErrorMail(getClass().getName() + "\n\n\n" + Arrays.toString(e.getStackTrace()));
                LOGGER.error(e.getLocalizedMessage());
            }
        }
    }

    public void generateExcelDocLots(String query, String extension) {
        List<Map<String, String>> dateList = new ArrayList<>();
        List<LotEntity> lotEntityList = new LotHandler().getLots(query);

        List<String> columnList = new ArrayList<>();
        columnList.add("uuid");
        columnList.add("uuid seller");
        columnList.add("uuid client");
        columnList.add("name");
        columnList.add("information");
        columnList.add("cost");
        columnList.add("blitz cost");
        columnList.add("step cost");
        columnList.add("date add");
        columnList.add("date start");
        columnList.add("date end");
        columnList.add("time start");
        columnList.add("time end");
        columnList.add("category");
        columnList.add("status");

        for (LotEntity lotEntity : lotEntityList) {
            Map<String, String> map = new HashMap<>();
            map.put(columnList.get(0), lotEntity.getUuid());
            map.put(columnList.get(1), lotEntity.getUuidUserSeller());
            map.put(columnList.get(2), lotEntity.getUuidUserClient());
            map.put(columnList.get(3), lotEntity.getName());
            map.put(columnList.get(4), lotEntity.getInformation());
            map.put(columnList.get(5), lotEntity.getCost());
            map.put(columnList.get(6), lotEntity.getBlitzCost());
            map.put(columnList.get(7), lotEntity.getStepCost());
            map.put(columnList.get(8), lotEntity.getDateAdd());
            map.put(columnList.get(9), lotEntity.getDateStart());
            map.put(columnList.get(10), lotEntity.getDateEnd());
            map.put(columnList.get(11), lotEntity.getTimeStart());
            map.put(columnList.get(12), lotEntity.getTimeEnd());
            map.put(columnList.get(13), String.valueOf(lotEntity.getIdCategory()));
            map.put(columnList.get(14), lotEntity.getStatus());
            dateList.add(map);
        }

        MailUtil mailUtil = new MailUtil();
        mailUtil.addAttachment(CommonUtil.prepareFileForAttach(
                CommonUtil.createExcelFile(dateList, columnList, "All lots"),
                fileName, extension));
        // TODO: send document
        mailUtil.sendSimpleEmailWithAttachment("");
    }

    public String getPdfBetEncode() {
        return Base64.getEncoder().encodeToString(byteArrayOutputStreamPDF.toByteArray());
    }

    public String getExcelBetEncode() {
        return Base64.getEncoder().encodeToString(bytesExcel);
    }

    public String getDocumentPasscode() {
        return documentPasscode;
    }

    public String getLotUrl() {
        return urlLot;
    }
}
