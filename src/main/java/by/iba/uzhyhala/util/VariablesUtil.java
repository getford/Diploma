package by.iba.uzhyhala.util;

public final class VariablesUtil {
    private VariablesUtil() {
    }

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

    // Lot status
    public static final String STATUS_LOT_ACTIVE = "active";
    public static final String STATUS_LOT_SALES = "sales";
    public static final String STATUS_LOT_WAIT = "wait";
    public static final String STATUS_LOT_CLOSE = "close";

    // Email cred
    static final String EMAIL_SUPPORT = "auction.diploma@gmail.com";
    static final String EMAIL_SUPPORT_PASSCODE = "diploma1234auction";
    static final String EMAIL_HOST = "smtp.gmail.com";
    static final String EMAIL_PORT = "587";
    static final String EMAIL_TEST = "o5m2bxu1.1dp@20email.eu";

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

    // PDF
    public static final String PDF_OWNER_PASSCODE = "9cb6baf0";

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
    public static final String QUERY_SELECT_USER_LOT = "SELECT l FROM " + ENTITY_LOT + " l ORDER BY date_add ASC";

}
