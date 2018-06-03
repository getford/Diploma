package by.iba.uzhyhala.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class VariablesUtil {
    private VariablesUtil() {
    }

    static final String HIBERNATE_CONFIG = "hibernate.cfg.xml";

    // Entity
    public static final String ENTITY_ADDRESS = "AddressEntity";
    public static final String ENTITY_AUTH_INFO = "AuthInfoEntity";
    public static final String ENTITY_BET = "BetEntity";
    public static final String ENTITY_CATEGORY = "CategoryEntity";
    public static final String ENTITY_FEEDBACK = "FeedbackEntity";
    public static final String ENTITY_LOT = "LotEntity";
    public static final String ENTITY_PERSONAL_INFORMATION = "PersonalInformationEntity";

    // User role
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "user";

    //
    public static final String EMAIL = "email";
    public static final String LOGIN = "login";
    public static final String PASSCODE = "password";

    // Lot status
    public static final String STATUS_LOT_ACTIVE = "active";
    public static final String STATUS_LOT_SALES = "sales";
    public static final String STATUS_LOT_WAIT = "wait";
    public static final String STATUS_LOT_CLOSE = "close";

    // russian lot status
    public static final String STATUS_RUS_LOT_ACTIVE = "Доступен для ставок";
    public static final String STATUS_RUS_LOT_SALES = "Продано";
    public static final String STATUS_RUS_LOT_WAIT = "В ожидании";
    public static final String STATUS_RUS_LOT_CLOSE = "Закрыт";

    // Email
    public static final String EMAIL_SUPPORT = "auction.diploma@gmail.com";
    static final String EMAIL_SUPPORT_PASSCODE = "diploma1234auction";
    static final String EMAIL_HOST = "smtp.gmail.com";
    static final String EMAIL_PORT = "587";
    static final String EMAIL_TEST = "o5m2bxu1.1dp@20email.eu";
    static final String EMAIL_CONTENT_TYPE_HTML = "text/html; charset=utf-8";
    static final String EMAIL_CONTENT_TYPE_PLAIN = "text/plain; charset=utf-8";
    static final String EMAIL_TITLE_PART = "[AUCTION DIPLOMA] ";

    // Date format pattern's
    public static final String PATTERN_DATE = "dd-MM-yyyy";
    public static final String PATTERN_DATE_REVERSE = "yyyy-MM-dd";
    public static final String PATTERN_DATE_WITH_DOT = "dd.MM.yyyy";
    public static final String PATTERN_TIME_WITH_MILLISECONDS = "HH:mm:ss.SSS";
    public static final String PATTERN_TIME = "HH:mm:ss";
    public static final String PATTERN_FULL_DATE_TIME_WITH_MILLISECONDS = "dd/MM/yyyy, HH:mm:ss.SSS";
    public static final String PATTERN_FULL_DATE_TIME = "dd/MM/yyyy, HH:mm:ss";
    public static final String PATTERN_FULL_REVERSE_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    // Cookie creds
    public static final String COOKIE_KEY = "a7fca695-ae2b-4aea-bc17-e62106e14c57";
    public static final String COOKIE_AUTH_NAME = "auction_auth";

    public static final String LOT_TIME_SEC = "600";
    public static final String LOT_TIME_AFTER_BET_SEC = "10";

    public static final String MESSAGE_ERROR_SERVER = "Please, contact with administrator. " +
            "Email: auction.diploma@gmail.com";

    // Regexp
    public static final String REGEXP_EMAIL = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    public static final String REGEXP_DELETE_NUMBERS = "^([0-9]+)";

    // Files
    public static final String PDF_OWNER_PASSCODE = "9cb6baf0";
    public static final String PDF_EXTENSION = ".pdf";
    public static final String EXCEL_EXTENSION_XLSX = ".xlsx";
    public static final String EXCEL_EXTENSION_XLS = ".xls";
    public static final String PDF = "pdf";
    public static final String EXCEL = "excel";

    // Query charts
    public static final String QUERY_CHART_DATA_ADD_DATE_LOT = "SELECT date_add, count(date_add) FROM lot " +
            "GROUP BY date_add ORDER BY date_add ASC";
    public static final String QUERY_CHART_DATA_START_DATE_LOT = "SELECT date_start, count(date_start) FROM lot " +
            "GROUP BY date_start ORDER BY date_start ASC";
    public static final String QUERY_CHART_DATA_END_DATE_LOT = "SELECT date_end, count(date_end) FROM lot " +
            "GROUP BY date_end ORDER BY date_end ASC";
    public static final String QUERY_CHART_DATE_CREATE_USER = "SELECT create_date, count(create_date) FROM auth_info " +
            "GROUP BY create_date ORDER BY create_date ASC";

    // Query lot
    public static final String QUERY_SELECT_ALL_LOT = "SELECT l FROM " + ENTITY_LOT + " l ORDER BY date_add ASC";

    // Query count
    public static final String QUERY_COUNT_ADD_LOT_TODAY = "SELECT count(uuid) FROM lot WHERE date_add = '"
            + new SimpleDateFormat(PATTERN_DATE).format(new Date().getTime()) + "'";
    public static final String QUERY_COUNT_END_LOT_TODAY = "SELECT count(uuid) FROM lot WHERE date_end = '"
            + new SimpleDateFormat(PATTERN_DATE).format(new Date().getTime()) + "'";
    public static final String QUERY_COUNT_START_LOT_TODAY = "SELECT count(uuid) FROM lot WHERE date_start = '"
            + new SimpleDateFormat(PATTERN_DATE).format(new Date().getTime()) + "'";

    private static final String PREPARE_QUERY_COUNT_ALL = "SELECT count(uuid) FROM lot WHERE status =";
    public static final String QUERY_COUNT_ALL_LOT = "SELECT count(uuid) FROM lot";
    public static final String QUERY_COUNT_ALL_LOT_ACTIVE = PREPARE_QUERY_COUNT_ALL + "'" + STATUS_LOT_ACTIVE + "'";
    public static final String QUERY_COUNT_ALL_LOT_SALES = PREPARE_QUERY_COUNT_ALL + "'" + STATUS_LOT_SALES + "'";
    public static final String QUERY_COUNT_ALL_LOT_WAIT = PREPARE_QUERY_COUNT_ALL + "'" + STATUS_LOT_WAIT + "'";
    public static final String QUERY_COUNT_ALL_LOT_CLOSE = PREPARE_QUERY_COUNT_ALL + "'" + STATUS_LOT_CLOSE + "'";
    public static final String QUERY_COUNT_ALL_USERS = "SELECT count(uuid) FROM auth_info";

    public static final String QUERY_COUNT_USERS_USE_API = "SELECT count(uuid) FROM auth_info WHERE api_key != 'null'";
    public static final String QUERY_COUNT_USERS_ADD_TODAY = "SELECT count(uuid) FROM auth_info WHERE create_date = '"
            + new SimpleDateFormat(PATTERN_DATE_REVERSE).format(new Date().getTime()) + "'";

    // api
    public static final String PARAMETER_API_KEY_NAME = "api_key";

    // test
    public static final String TEST_API_KEY_NAME = "D2B331E0831A4C5683E17FDA0394723C";
    public static final String TEST_URL = "http://localhost:8080/";

    // rate
    public static final String RATE_PLUS = "plus";
    public static final String RATE_MINUS = "minus";
    public static final String LOT = "lot";
    public static final String USER = "user";

    // upload
    public static final String FOLDER_UPLOAD_IMAGES = "AUCTION DIPLOMA UPLOAD IMAGES";

    // hash
    public static final String HASH_SALT = "LA";

    // others variables
    public static final String DOUBLE_ZERO = "00";
}
