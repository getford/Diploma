package by.iba.uzhyhala.util;

public final class VariablesUtil {
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
    static final String EMAIL_SUPPORT_PASSWORD = "diploma1234auction";
    static final String EMAIL_HOST = "smtp.gmail.com";
    static final String EMAIL_PORT = "587";
    static final String EMAIL_TEST = "o5m2bxu1.1dp@20email.eu";

    // Date format pattern's
    public static final String PATTERN_DATE = "dd-MM-yyyy";
    public static final String PATTERN_DATE_REVERSE = "yyyy-MM-dd";
    public static final String PATTERN_DATE_DOC = "dd.MM.yyyy";
    public static final String PATTERN_TIME = "HH:mm:ss:SSS";
    public static final String PATTERN_TIME_DOC = "HH:mm:ss";
    public static final String PATTERN_DATE_TIME = "dd/MM/yyyy, HH:mm:ss:SSS";

    // Cookie creds
    public static final String COOKIE_KEY = "a7fca695-ae2b-4aea-bc17-e62106e14c57";
    public static final String COOKIE_AUTH_NAME = "auction_auth";

    public static final String MESSAGE_ERROR_SERVER = "Please, contact with administrator. " +
            "Email: auction.diploma@gmail.com";

    // Regexp
    public static final String REGEXP_EMAIL = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    public static final String REGEXP_PASSWORD = "";

    // Query
    public static final String QUERY_CHART_DATA_ADD_DATE_LOT = "SELECT date_add, count(date_add) FROM lot " +
            "GROUP BY date_add ORDER BY date_add ASC";
    public static final String QUERY_CHART_DATA_START_DATE_LOT = "SELECT date_start, count(date_start) FROM lot " +
            "GROUP BY date_start ORDER BY date_start ASC";
    public static final String QUERY_CHART_DATA_END_DATE_LOT = "SELECT date_end, count(date_end) FROM lot " +
            "GROUP BY date_end ORDER BY date_end ASC";

}
