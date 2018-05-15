package by.iba.uzhyhala.util;

import by.iba.uzhyhala.entity.AuthInfoEntity;
import by.iba.uzhyhala.lot.to.BetBulkTO;
import by.iba.uzhyhala.lot.to.BetHistoryTO;
import by.iba.uzhyhala.lot.to.BetTO;
import com.google.gson.Gson;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

import static by.iba.uzhyhala.util.VariablesUtil.*;

public class CommonUtil {
    private static final Logger LOGGER = Logger.getLogger(CommonUtil.class);

    public static String loginOrEmail(String loginOrEmail) {
        String result = Pattern.compile(REGEXP_EMAIL,
                Pattern.CASE_INSENSITIVE).matcher(loginOrEmail).find() ? "email" : "login";
        LOGGER.debug(CommonUtil.class.getName() + " loginOrEmail return: " + result);
        return result;
    }

    public static String getUUIDUserByLoginEmail(Session session, String loginOrEmail, String type) {
        String result = session.createQuery("SELECT a.uuid FROM " + ENTITY_AUTH_INFO
                + " a WHERE " + type + " = :cred").setParameter("cred", loginOrEmail).list().get(0).toString();
        LOGGER.debug(CommonUtil.class.getName() + " getUUIDUserByLoginEmail return: " + result);
        return result;
    }

    public static String getUserLoginByUUID(String uuid) {
        LOGGER.info("getUserLoginByUUID method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            String result = session.createQuery("SELECT a.login FROM " + ENTITY_AUTH_INFO
                    + " a WHERE uuid = :uuid").setParameter("uuid", uuid).list().get(0).toString();
            LOGGER.debug(CommonUtil.class.getName() + " getUserEmailByUUID return: " + result);
            return result;
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

            String result = session.createQuery("SELECT a.email FROM " + ENTITY_AUTH_INFO
                    + " a WHERE uuid = :uuid").setParameter("uuid", uuid).list().get(0).toString();
            LOGGER.debug(CommonUtil.class.getName() + " getUserEmailByUUID return: " + result);
            return result;
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("Method: getUserEmailByUUID\n" + Arrays.toString(ex.getStackTrace()));
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
            String s = session.createQuery("SELECT c.category_name FROM " + ENTITY_CATEGORY + " c WHERE id = :id")
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
            return session.createQuery("SELECT a FROM " + ENTITY_AUTH_INFO + " a").list();
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("getAllUser\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getStackTrace());
            return new ArrayList<>();
        }
    }

    public static String getJsonBetBulk(String uuid) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return String.valueOf(session.createQuery("SELECT b.bulk FROM " + ENTITY_BET + " b WHERE uuid = :uuid")
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

    public static int getCurrentCost(List<BetHistoryTO> betHistoryTO) {
        int current = 0;
        for (BetHistoryTO to : betHistoryTO)
            current += to.getBet();
        return current;
    }

    public static String getLotDateEnd(String start, String plusSec) {
        String dateNow = new SimpleDateFormat(PATTERN_DATE_REVERSE).format(new Date().getTime());
        LocalDateTime localDateTime = LocalDateTime.parse(dateNow + "T" + start);
        return String.valueOf(localDateTime.plusSeconds(Long.parseLong(plusSec)).toLocalTime() + ":00");
    }

    public static boolean isUserHaveApiKey(String uuid) {
        LOGGER.info("isUserHaveApiKey method");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            int length = session
                    .createSQLQuery("SELECT api_key FROM auth_info WHERE uuid = :uuid")
                    .setParameter("uuid", uuid)
                    .getResultList().get(0).toString().length();

            return length == TEST_API_KEY_NAME.length();
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
                    .list().size() > 0;
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("Method: isApiKeyValid\n" + Arrays.toString(ex.getStackTrace()));
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
            if (extension.equals(EXCEL_EXTENSION_XLSX) || extension.equals(EXCEL_EXTENSION_XLS)) {
                LOGGER.info("prepareFileForAttach\textension: " + extension);
                Workbook workbook = (Workbook) o;
                workbook.write(byteArrayOutputStream);
            }
            if (extension.equals(PDF_EXTENSION)) {
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
                return "Статус не определены, обратитесь в поддержку" + EMAIL_SUPPORT;
        }
    }

    public static int getRate(String uuid, String type) {
        LOGGER.info("getRate method");
        int currentRate = 0;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            switch (type) {
                case LOT:
                    currentRate = Integer.parseInt(String.valueOf(session
                            .createQuery("SELECT a.rate FROM " + ENTITY_LOT + " a WHERE uuid = :uuid")
                            .setParameter("uuid", uuid)
                            .list().get(0)));
                    break;
                case USER:
                    currentRate = Integer.parseInt(String.valueOf(session
                            .createQuery("SELECT a.rate FROM " + ENTITY_AUTH_INFO + " a WHERE uuid = :uuid")
                            .setParameter("uuid", uuid)
                            .list().get(0)));
                    break;
                default:
                    break;
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
        switch (goal) {
            case RATE_PLUS:
                rate += 1;
                break;
            case RATE_MINUS:
                rate -= 1;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            switch (type) {
                case LOT:
                    session.createQuery("UPDATE " + ENTITY_LOT + " SET rate = :rate WHERE uuid = :uuid")
                            .setParameter("rate", rate)
                            .setParameter("uuid", uuid)
                            .executeUpdate();
                    break;
                case USER:
                    session.createQuery("UPDATE " + ENTITY_AUTH_INFO + " SET rate = :rate WHERE uuid = :uuid")
                            .setParameter("rate", rate)
                            .setParameter("uuid", uuid)
                            .executeUpdate();
                    break;
            }
            LOGGER.info("Rate " + type + " " + uuid + " " + goal + " was successfully updated");
        } catch (Exception ex) {
            new MailUtil().sendErrorMail("Method: changeRate\n" + Arrays.toString(ex.getStackTrace()));
            LOGGER.error(ex.getLocalizedMessage());
        }
    }
}
