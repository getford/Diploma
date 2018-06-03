package by.iba.uzhyhala.util;

import by.iba.uzhyhala.entity.AuthInfoEntity;
import by.iba.uzhyhala.entity.CategoryEntity;
import by.iba.uzhyhala.entity.LotEntity;
import by.iba.uzhyhala.lot.to.BetBulkTO;
import by.iba.uzhyhala.lot.to.BetHistoryTO;
import by.iba.uzhyhala.lot.to.BetTO;
import com.google.gson.Gson;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

import static by.iba.uzhyhala.util.VariablesUtil.*;
import static java.lang.String.valueOf;

public class CommonUtil {
    private static final Logger LOGGER = Logger.getLogger(CommonUtil.class);

    private CommonUtil() {
    }

    public static String loginOrEmail(String loginOrEmail) {
        LOGGER.info("loginOrEmail method");
        return Pattern.compile(REGEXP_EMAIL,
                Pattern.CASE_INSENSITIVE).matcher(loginOrEmail).find() ? EMAIL : LOGIN;
    }

    public static String getUUIDUserByLoginEmail(String loginOrEmail, String type) {
        LOGGER.info("getUUIDUserByLoginEmail method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session
                    .createQuery("SELECT a.uuid FROM " + ENTITY_AUTH_INFO + " a WHERE " + type + " = :cred")
                    .setParameter("cred", loginOrEmail)
                    .list()
                    .get(0)
                    .toString();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("Method: getUserLoginByUUID\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return null;
        }
    }

    public static String getUserLoginByUUID(String uuid) {
        LOGGER.info("getUserLoginByUUID method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session
                    .createQuery("SELECT a.login FROM " + ENTITY_AUTH_INFO + " a WHERE uuid = :uuid")
                    .setParameter("uuid", uuid)
                    .list()
                    .get(0)
                    .toString();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("Method: getUserLoginByUUID\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return null;
        }
    }

    public static String getUserEmailByUUID(String uuid) {
        LOGGER.info("getUserEmailByUUID method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT a.email FROM " + ENTITY_AUTH_INFO + " a WHERE uuid = :uuid")
                    .setParameter("uuid", uuid)
                    .list()
                    .get(0)
                    .toString();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("Method: getUserEmailByUUID\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return null;
        }
    }

    public static String getUUIDUserByUUIDLot(String uuidLot) {
        LOGGER.info("getUUIDUserByUUIDLot method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session
                    .createSQLQuery("SELECT uuid_user_seller FROM lot WHERE uuid = '" + uuidLot + "'")
                    .list()
                    .get(0)
                    .toString();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("Method: getUserEmailByUUID\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return null;
        }
    }

    public static String getUserFirstLastNameByUUID(Session session, String uuid) {
        LOGGER.info("getUserFirstLastNameByUUID method");
        Object[] object = session
                .createSQLQuery("SELECT first_name, last_name FROM personal_information WHERE uuid_user = '" + uuid + "'")
                .list()
                .toArray();
        String result = valueOf(((Object[]) object[0])[0]) + " " + ((Object[]) object[0])[1];
        LOGGER.debug("return: " + result);
        return result;
    }

    public static String getCategoryById(int id) {
        LOGGER.info("getCategoryById method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT c.category_name FROM " + ENTITY_CATEGORY + " c WHERE id = :id")
                    .setParameter("id", id)
                    .getResultList()
                    .get(0)
                    .toString();
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
        }
        return null;
    }

    public static List<AuthInfoEntity> getAllUser() {
        LOGGER.info("getAllUser method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT a FROM " + ENTITY_AUTH_INFO + " a").list();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("getAllUser\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return new ArrayList<>();
        }
    }

    public static String getJsonBetBulk(String uuid) {
        LOGGER.info("getJsonBetBulk method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return valueOf(session.createQuery("SELECT b.bulk FROM " + ENTITY_BET + " b WHERE uuid = :uuid")
                    .setParameter("uuid", uuid)
                    .list()
                    .get(0)
            );
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return null;
        }
    }

    public static List<BetHistoryTO> getHistoryBets(String uuidLot) {
        LOGGER.info("getHistoryBets method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            BetBulkTO betBulkTO = new Gson().fromJson(CommonUtil.getJsonBetBulk(uuidLot), BetBulkTO.class);
            List<BetTO> betTOList = new ArrayList<>(betBulkTO.getBets());

            List<BetHistoryTO> betHistoryTO = new ArrayList<>();
            for (BetTO bet : betTOList) {
                BetHistoryTO to = new BetHistoryTO();
                to.setUserName(CommonUtil.getUserFirstLastNameByUUID(session, bet.getUuidUser()));
                to.setBet(bet.getBet());
                to.setDate(bet.getDate());
                to.setTime(bet.getTime());

                betHistoryTO.add(to);
            }
            return betHistoryTO;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return new ArrayList<>();
        }
    }

    public static int getCurrentCost(List<BetHistoryTO> betHistoryTO) {
        int current = 0;
        for (BetHistoryTO to : betHistoryTO)
            current += to.getBet();
        return current;
    }

    public static String getLotDateEnd(String start, String plusSec) {
        LOGGER.info("getLotDateEnd method");
        String dateNow = new SimpleDateFormat(PATTERN_DATE_REVERSE).format(new Date().getTime());
        LocalDateTime localDateTime = LocalDateTime.parse(dateNow + "T" + start);
        return valueOf(localDateTime.plusSeconds(Long.parseLong(plusSec)).toLocalTime() + ":" + DOUBLE_ZERO);
    }

    public static boolean isUserHaveApiKey(String uuid) {
        LOGGER.info("isUserHaveApiKey method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session
                    .createSQLQuery("SELECT api_key FROM auth_info WHERE uuid = :uuid")
                    .setParameter("uuid", uuid)
                    .getResultList()
                    .get(0)
                    .toString()
                    .length() == TEST_API_KEY_NAME.length();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("Method: isUserHaveApiKey\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return false;
        }
    }

    public static boolean isApiKeyValid(String key) {
        LOGGER.info("isApiKeyValid method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session
                    .createQuery("SELECT a.uuid FROM " + ENTITY_AUTH_INFO + " a WHERE api_key = :key")
                    .setParameter("key", key)
                    .list()
                    .size() > 0;
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("Method: isApiKeyValid\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return false;
        }
    }

    public static File prepareFileForAttach(Object o, String fileName, String extension) {
        LOGGER.info("prepareFileForAttach method");
        try {
            File tempFile = File.createTempFile(fileName, extension);

            LOGGER.info("file name: " + tempFile.getName());
            LOGGER.info("file path: " + tempFile.getAbsolutePath());

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (extension.equals(EXCEL_EXTENSION_XLSX) || extension.equals(EXCEL_EXTENSION_XLS)) {
                LOGGER.info("prepareFileForAttach extension: " + extension);
                Workbook workbook = (Workbook) o;
                workbook.write(byteArrayOutputStream);
            }

            if (extension.equals(PDF_EXTENSION))
                byteArrayOutputStream = (ByteArrayOutputStream) o;

            try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
                fileOutputStream.write(byteArrayOutputStream.toByteArray());
            }
            return tempFile;
        } catch (IOException ex) {
            new MailUtil().sendErrorMail("\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return null;
        }
    }

    public static Workbook createExcelFile(List<Map<String, String>> dataList,
                                           List<String> columnList, String sheetName) {
        LOGGER.info("createExcelFile method");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        Row rowHeader = sheet.createRow(0);

        for (int i = 0; i < columnList.size(); i++) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellValue(valueOf(columnList.get(i)));
        }

        int rowNumber = 1;
        for (Map<String, String> stringMap : dataList) {
            Row row = sheet.createRow(rowNumber++);
            int columnNumber = 0;
            for (String column : columnList) {
                Cell cell = row.createCell(columnNumber++);
                cell.setCellValue(stringMap.get(column));
            }
        }
        return workbook;
    }

    public static String translateLotStatus(String englishStatus) {
        switch (englishStatus) {
            case STATUS_LOT_ACTIVE:
                return STATUS_RUS_LOT_ACTIVE;
            case STATUS_LOT_SALES:
                return STATUS_RUS_LOT_SALES;
            case STATUS_LOT_WAIT:
                return STATUS_RUS_LOT_WAIT;
            case STATUS_LOT_CLOSE:
                return STATUS_RUS_LOT_CLOSE;
            default:
                LOGGER.error("Статус не определен, обратитесь в поддержку: " + EMAIL_SUPPORT);
                return "Статус не определен, обратитесь в поддержку" + EMAIL_SUPPORT;
        }
    }

    public static int getRate(String uuid, String type) {
        LOGGER.info("getRate method");
        int currentRate = 0;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            if (LOT.equals(type)) {
                currentRate = Integer.parseInt(valueOf(session
                        .createQuery("SELECT a.rate FROM " + ENTITY_LOT + " a WHERE uuid = :uuid")
                        .setParameter("uuid", uuid)
                        .list()
                        .get(0))
                );
            } else if (USER.equals(type)) {
                currentRate = Integer.parseInt(valueOf(session
                        .createQuery("SELECT a.rate FROM " + ENTITY_AUTH_INFO + " a WHERE uuid = :uuid")
                        .setParameter("uuid", uuid)
                        .list()
                        .get(0))
                );
            }
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("Method: getRate\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return currentRate;
        }
        return currentRate;
    }

    @SuppressFBWarnings("SF_SWITCH_NO_DEFAULT")
    public static void changeRate(String uuid, String goal, String type) {
        LOGGER.info("changeRate method");
        int rate = getRate(uuid, type);
        if (RATE_PLUS.equals(goal)) {
            rate += 1;
        } else if (RATE_MINUS.equals(goal)) {
            rate -= 1;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            if (LOT.equals(type)) {
                session.createQuery("UPDATE " + ENTITY_LOT + " SET rate = :rate WHERE uuid = :uuid")
                        .setParameter("rate", rate)
                        .setParameter("uuid", uuid)
                        .executeUpdate();

            } else if (USER.equals(type)) {
                session.createQuery("UPDATE " + ENTITY_AUTH_INFO + " SET rate = :rate WHERE uuid = :uuid")
                        .setParameter("rate", rate)
                        .setParameter("uuid", uuid)
                        .executeUpdate();

            }
            LOGGER.info("Rate " + type + " " + uuid + " " + goal + " was successfully updated");
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("Method: changeRate\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
        }
    }

    public static List<LotEntity> getLots(String query) {
        LOGGER.debug("getLots");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(query).getResultList();
        } catch (Exception e) {
            new MailUtil().sendErrorMail(Arrays.toString(e.getStackTrace()));
            LOGGER.error(e.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    public static List<CategoryEntity> getCategories() {
        LOGGER.debug("getCategories");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT c FROM " + ENTITY_CATEGORY + " c").getResultList();
        } catch (Exception e) {
            new MailUtil().sendErrorMail(Arrays.toString(e.getStackTrace()));
            LOGGER.error(e.getLocalizedMessage());
        }
        return new ArrayList<>();
    }

    private static String separateUploadFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename"))
                return token.substring(token.indexOf('=') + 2, token.length() - 1);
        }
        return "";
    }

    public static String saveUploadFile(HttpServletRequest req) throws IOException, ServletException {
        String uploadFilePath = req.getServletContext().getRealPath("") + FOLDER_UPLOAD_IMAGES;

        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        for (Part part : req.getParts()) {
            String fileName = separateUploadFileName(part);
            if (!StringUtils.isBlank(fileName)) {
                String path = uploadFilePath + File.separator + fileName;
                part.write(path);
                return fileName;
            }
        }
        return "";
    }
}
