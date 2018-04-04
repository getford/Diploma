package by.iba.uzhyhala.util;

public class CommonUtil {
    public static String nameRoleByID(int id) {
        return (id == 1) ? VariablesUtil.ROLE_ADMIN_NAME : VariablesUtil.ROLE_USER_NAME;
    }

    public static int idRoleByName(String name) {
        return (name.equalsIgnoreCase("admin")) ? VariablesUtil.ROLE_ADMIN_ID : VariablesUtil.ROLE_USER_ID;
    }
}
