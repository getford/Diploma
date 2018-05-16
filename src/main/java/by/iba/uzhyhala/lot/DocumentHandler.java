package by.iba.uzhyhala.lot;

import by.iba.uzhyhala.entity.LotEntity;
import by.iba.uzhyhala.lot.to.BetHistoryTO;
import by.iba.uzhyhala.util.CommonUtil;
import by.iba.uzhyhala.util.MailUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static by.iba.uzhyhala.util.VariablesUtil.*;


@WebServlet(urlPatterns = "/generatehistorybets")
public class DocumentHandler extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(DocumentHandler.class);
    private static final String BET_HISTORY = "Bet history";
    private static final String RESIRECT_URL_PATH = "/pages/lot.jsp?uuid=";
    private static final long serialVersionUID = 1016213373588804043L;
    private ByteArrayOutputStream byteArrayOutputStreamPDF = new ByteArrayOutputStream();
    private String documentPasscode;
    private String urlLot;
    private String fileName = "File_";
    private byte[] bytesExcel = null;
    private static String timeNow = String.valueOf(new SimpleDateFormat(PATTERN_TIME).format(new Date()));
    private static String subject = "Корреспонденция по лоту ";


    @SuppressFBWarnings("HRS_REQUEST_PARAMETER_TO_HTTP_HEADER")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String uuidLot = req.getParameter("uuid_lot");
        String redirectUrl = RESIRECT_URL_PATH + uuidLot;
        fileName = "Bet_history_" + timeNow.replaceAll(":", ".") + "_" + uuidLot;

        try {
            switch (req.getParameter("send-mail")) {
                case "true":
                    if (req.getParameter("type").equals(PDF)) {
                        fileName += PDF_EXTENSION;
                        generateDocHistoryBetPDF(uuidLot, req, resp, true);
                    } else if (req.getParameter("type").equals(EXCEL)) {
                        fileName += EXCEL_EXTENSION_XLSX;
                        generateExcelDocHistoryBet(req, uuidLot, EXCEL_EXTENSION_XLSX, true);
                    }
                    LOGGER.info("Document " + fileName + " create/send successfully");
                    resp.sendRedirect(redirectUrl);
                    break;
                case "false":
                    if (req.getParameter("type").equals(PDF)) {
                        fileName += PDF_EXTENSION;
                        generateDocHistoryBetPDF(uuidLot, req, resp, false);
                    } else if (req.getParameter("type").equals(EXCEL)) {
                        fileName += EXCEL_EXTENSION_XLSX;
                        generateExcelDocHistoryBet(req, uuidLot, EXCEL_EXTENSION_XLSX, false);
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
                new MailUtil().sendErrorMail(Arrays.toString(ex.getStackTrace()));
                LOGGER.error(ex.getLocalizedMessage());
            }
        }

    }

    public void generateDocHistoryBetPDF(String uuidLot, HttpServletRequest req, HttpServletResponse resp, boolean isSendMail) {
        try {
            Document document = new Document(PageSize.A4);
            URL url = new URL(req.getRequestURL().toString());

            urlLot = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + RESIRECT_URL_PATH + uuidLot;
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
            if (!StringUtils.isBlank(req.getParameter(PARAMETER_API_KEY_NAME)) && !isSendMail) {
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
                    PDF_OWNER_PASSCODE.getBytes(StandardCharsets.UTF_8),
                    PdfWriter.ALLOW_COPY,
                    PdfWriter.ENCRYPTION_AES_128
            );

            document.open();

            document.addAuthor("Auction Diploma");
            document.addTitle(BET_HISTORY);

            document.add(new Paragraph("Auction Diploma"));
            document.add(new Paragraph(BET_HISTORY));
            document.add(new Paragraph(String.valueOf(new SimpleDateFormat(PATTERN_FULL_DATE_TIME).format(new Date()))));

            BarcodeQRCode barcodeQRCode = new BarcodeQRCode(getLotUrl(), 1000, 1000, null);
            Image codeQrImage = barcodeQRCode.getImage();
            codeQrImage.setAbsolutePosition(469, 729);
            codeQrImage.scaleAbsolute(100, 100);
            document.add(codeQrImage);

            document.add(new Paragraph("\n---------------------------------------------------------------" +
                    "-------------------------------------------------------------------"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Rate: " + CommonUtil.getRate(uuidLot, LOT)));
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
                        fileName, PDF_EXTENSION)
                );

                String body = "<br/>" + timeNow +
                        "<br/>Добрый день, найдите прикрепленные файлы в письме." +
                        "<br/>Адрес лота: <b>" + urlLot + "</b>" +
                        "<br/>Пароль для открытия файла: <b>" + documentPasscode + "</b>" +
                        "<br/><br/>С уважением";

                // TODO: send document
                mailUtil.sendSimpleHtmlMail("", body, subject + uuidLot);
            } else {
                LOGGER.info("Send document not required");
            }

            LOGGER.info("PDF document successfully generated");
            LOGGER.info("Document name\t" + fileName);
            LOGGER.info("Password\t" + getDocumentPasscode());
        } catch (IOException | DocumentException e) {
            new MailUtil().sendErrorMail(Arrays.toString(e.getStackTrace()));
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    public void generateExcelDocHistoryBet(HttpServletRequest req, String uuidLot, String extension, boolean isSendMail) {
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
                    CommonUtil.createExcelFile(dateList, columnList, BET_HISTORY),
                    fileName, extension));

            // TODO: send document
            String body = "<br/>" + timeNow +
                    "<br/>Добрый день, найдите прикрепленные файлы в письме." +
                    "<br/>Адрес лота: <b>" + urlLot + "</b>" +
                    "<br/><br/>С уважением";

            mailUtil.sendSimpleHtmlMail("", body, subject + uuidLot);
        } else {
            try {
                URL url = new URL(req.getRequestURL().toString());
                urlLot = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + RESIRECT_URL_PATH + uuidLot;
            } catch (MalformedURLException e) {
                new MailUtil().sendErrorMail(Arrays.toString(e.getStackTrace()));
                LOGGER.error(e.getLocalizedMessage());
            }
            File file = new File(String.valueOf(CommonUtil.prepareFileForAttach(
                    CommonUtil.createExcelFile(dateList, columnList, BET_HISTORY),
                    fileName, extension)));

            try (FileInputStream fis = new FileInputStream(file)) {
                bytesExcel = new byte[(int) file.length()];
                fis.read(bytesExcel);
            } catch (IOException e) {
                new MailUtil().sendErrorMail(Arrays.toString(e.getStackTrace()));
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

        String subject = "Корреспоненция по всем лотам";
        String body = "<br/>" + timeNow +
                "<br/>Добрый день, найдите прикрепленные файлы в письме." +
                "<br/><br/>С уважением";

        // TODO: send document
        mailUtil.sendSimpleHtmlMail("", body, subject);
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
