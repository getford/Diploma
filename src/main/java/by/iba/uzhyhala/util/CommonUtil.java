package by.iba.uzhyhala.util;

import by.iba.uzhyhala.entity.AuthInfoEntity;
import by.iba.uzhyhala.lot.to.BetBulkTO;
import by.iba.uzhyhala.lot.to.BetHistoryTO;
import by.iba.uzhyhala.lot.to.BetTO;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

public class CommonUtil {
    private static final Logger LOGGER = Logger.getLogger(CommonUtil.class);

    public static String loginOrEmail(String loginOrEmail) {
        String result = Pattern.compile(VariablesUtil.REGEXP_EMAIL,
                Pattern.CASE_INSENSITIVE).matcher(loginOrEmail).find() ? "email" : "login";
        LOGGER.debug(CommonUtil.class.getName() + " loginOrEmail return: " + result);
        return result;
    }

    public static String getUUIDUserByLoginEmail(Session session, String loginOrEmail, String type) {
        String result = session.createQuery("SELECT a.uuid FROM " + VariablesUtil.ENTITY_AUTH_INFO
                + " a WHERE " + type + " = :cred").setParameter("cred", loginOrEmail).list().get(0).toString();
        LOGGER.debug(CommonUtil.class.getName() + " getUUIDUserByLoginEmail return: " + result);
        return result;
    }

    public static String getUserLoginByUUID(String uuid) {
        LOGGER.info("getUserLoginByUUID method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            String result = session.createQuery("SELECT a.login FROM " + VariablesUtil.ENTITY_AUTH_INFO
                    + " a WHERE uuid = :uuid").setParameter("uuid", uuid).list().get(0).toString();
            LOGGER.debug(CommonUtil.class.getName() + " getUserEmailByUUID return: " + result);
            return result;
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("CommonUtil class, Method: getUserLoginByUUID\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return null;
        }
    }

    public static String getUserEmailByUUID(String uuid) {
        LOGGER.info("getUserEmailByUUID method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            String result = session.createQuery("SELECT a.email FROM " + VariablesUtil.ENTITY_AUTH_INFO
                    + " a WHERE uuid = :uuid").setParameter("uuid", uuid).list().get(0).toString();
            LOGGER.debug(CommonUtil.class.getName() + " getUserEmailByUUID return: " + result);
            return result;
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("CommonUtil class, Method: getUserEmailByUUID\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return null;
        }
    }

    public static String getUUIDUserByUUIDLot(Session session, String uuidLot) {
        String result = session.createSQLQuery("SELECT uuid_user_seller FROM lot WHERE uuid = '" + uuidLot + "'").list().get(0).toString();
        LOGGER.debug(CommonUtil.class.getName() + " getUUIDUserByUUIDLot return: " + result);
        return result;
    }

    public static String getUserFirstLastNameByUUID(Session session, String uuid) {
        Object[] object = session.createSQLQuery("SELECT first_name, last_name FROM personal_information WHERE uuid_user = '" + uuid + "'").list().toArray();
        String result = String.valueOf(((Object[]) object[0])[0]) + " " + String.valueOf(((Object[]) object[0])[1]);
        LOGGER.debug(CommonUtil.class.getName() + " getUUIDUserByLoginEmail return: " + result);
        return result;
    }

    public static String getCategoryById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String s = session.createQuery("SELECT c.category_name FROM " + VariablesUtil.ENTITY_CATEGORY + " c WHERE id = :id")
                    .setParameter("id", id).getResultList().get(0).toString();
            return s;
        } catch (Exception ex) {
            LOGGER.error(ex.getLocalizedMessage());
        }
        return null;
    }

    public static List<AuthInfoEntity> getAllUser() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT a FROM " + VariablesUtil.ENTITY_AUTH_INFO + " a").list();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("getAllUser\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return new ArrayList<>();
        }
    }

    public static String getJsonBetBulk(String uuid) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return String.valueOf(session.createQuery("SELECT b.bulk FROM " + VariablesUtil.ENTITY_BET + " b WHERE uuid = :uuid")
                    .setParameter("uuid", uuid).list().get(0));
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
            return null;
        }
    }

    public static String getLotDateEnd(String start, String plusSec) {
        String dateNow = new SimpleDateFormat(VariablesUtil.PATTERN_DATE_REVERSE).format(new Date().getTime());
        LocalDateTime localDateTime = LocalDateTime.parse(dateNow + "T" + start);
        return String.valueOf(localDateTime.plusSeconds(Long.parseLong(plusSec)).toLocalTime() + ":00");
    }

    public static boolean isUpdateLotStatus(String status, String uuid, HttpServletRequest request) {
        LOGGER.info("isUpdateLotStatus method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            BetBulkTO betBulkTO = new Gson().fromJson(CommonUtil.getJsonBetBulk(uuid), BetBulkTO.class);
            betBulkTO.setStatus(status);

            session.beginTransaction();

            session.createQuery("UPDATE " + VariablesUtil.ENTITY_LOT + " SET status = :status WHERE uuid = :uuid")
                    .setParameter("status", status)
                    .setParameter("uuid", uuid)
                    .executeUpdate();
            session.createQuery("UPDATE " + VariablesUtil.ENTITY_BET + " SET bulk = :newBulk WHERE uuid = :uuid")
                    .setParameter("newBulk", new Gson().toJson(betBulkTO))
                    .setParameter("uuid", uuid)
                    .executeUpdate();

            URL url = new URL(String.valueOf(request.getRequestURL()));
            String subject = "Статус лота был успешно изменен";
            String body = "<br/> " + new SimpleDateFormat(VariablesUtil.PATTERN_FULL_DATE_TIME).format(new Date().getTime()) + "<br/>" +
                    "<p>Здравствуйте,</p>" +
                    "<p>Уведомляем вас о том, что статус вашего лота, был успешно изменен</p>" +
                    "<p>" +
                    "<b>Новый статус: </b>" + translateLotStatus(status) + "" +
                    "<br/><b>Уникальный идентификатор лота: </b>" + uuid + "</p>" +
                    "<p>You profile: <a href=\"" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/pages/lot.jsp?uuid=" + uuid + "\">" +
                    "" + url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/pages/lot.jsp?uuid=" + uuid + "</a></p>";

            new MailUtil().sendMail(getUserEmailByUUID(getUUIDUserByUUIDLot(session, uuid)), body, subject);
            return true;
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("CommonUtil class, Method: isUpdateLotStatus\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return false;
        }
    }

    public static boolean isUserHaveApiKey(String uuid) {
        LOGGER.info("isUserHaveApiKey method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            int length = session
                    .createSQLQuery("SELECT api_key FROM auth_info WHERE uuid = :uuid")
                    .setParameter("uuid", uuid)
                    .getResultList().get(0).toString().length();

            return length == VariablesUtil.TEST_API_KEY_NAME.length();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("CommonUtil class, Method: isUserHaveApiKey\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return false;
        }
    }

    public static boolean isApiKeyValid(String key) {
        LOGGER.info("isApiKeyValid method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            return session
                    .createQuery("SELECT a.uuid FROM " + VariablesUtil.ENTITY_AUTH_INFO + " a WHERE api_key = :key")
                    .setParameter("key", key)
                    .list().size() > 0;
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("CommonUtil class, Method: isApiKeyValid\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
            return false;
        }
    }

    public static File prepareFileForAttach(Object o, String fileName, String extension) {
        try {
            File tempFile = File.createTempFile(fileName, extension);

            LOGGER.info("file name: " + tempFile.getName());
            LOGGER.info("file path: " + tempFile.getAbsolutePath());

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (extension.equals(VariablesUtil.EXCEL_EXTENSION_XLSX) || extension.equals(VariablesUtil.EXCEL_EXTENSION_XLS)) {
                LOGGER.info("prepareFileForAttach\textension: " + extension);
                Workbook workbook = (Workbook) o;
                workbook.write(byteArrayOutputStream);
            }
            if (extension.equals(VariablesUtil.PDF_EXTENSION)) {
                byteArrayOutputStream = (ByteArrayOutputStream) o;
            }

            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.close();

            return tempFile;
        } catch (IOException ex) {
            new MailUtil().sendErrorMail("\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return null;
        }
    }

    public static Workbook createExcelFile(List<Map<String, String>> dataList, List<String> columnList, String sheetName) {
        LOGGER.info("createExcelFile method");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        Row rowHeader = sheet.createRow(0);

        for (int i = 0; i < columnList.size(); i++) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellValue(String.valueOf(columnList.get(i)));
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

    public static boolean isApiCall(HttpServletRequest request) {
        LOGGER.info("isApiCall method");
        return !StringUtils.isBlank(request.getParameter(VariablesUtil.PARAMETER_API_KEY_NAME));
    }

    public static String translateLotStatus(String englishStatus) {
        LOGGER.info("translateLotStatus\t" + englishStatus);
        switch (englishStatus) {
            case VariablesUtil.STATUS_LOT_ACTIVE:
                return VariablesUtil.STATUS_RUS_LOT_ACTIVE;
            case VariablesUtil.STATUS_LOT_SALES:
                return VariablesUtil.STATUS_RUS_LOT_SALES;
            case VariablesUtil.STATUS_LOT_WAIT:
                return VariablesUtil.STATUS_RUS_LOT_WAIT;
            case VariablesUtil.STATUS_LOT_CLOSE:
                return VariablesUtil.STATUS_RUS_LOT_CLOSE;
            default:
                return "Неопределено, обратитесь в поддержку" + VariablesUtil.EMAIL_SUPPORT;
        }
    }
}
