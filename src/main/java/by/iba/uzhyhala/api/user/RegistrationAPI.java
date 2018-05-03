package by.iba.uzhyhala.api.user;

import by.iba.uzhyhala.api.to.UserRegTOAPI;
import by.iba.uzhyhala.user.Registration;
import by.iba.uzhyhala.util.VariablesUtil;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/api/user/reg")
public class RegistrationAPI extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegistrationAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOGGER.info(getClass().getName() + "\t" + "doPost Method");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String responseMessage = "";
        UserRegTOAPI userRegTOAPI = handleResponse(req.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
        LOGGER.info(getClass().getName() + "\t" + "userRegTOAPI: " + userRegTOAPI.toString());
        if (validateRequest(userRegTOAPI)) {
            if (Pattern.compile(VariablesUtil.REGEXP_EMAIL,
                    Pattern.CASE_INSENSITIVE).matcher(userRegTOAPI.getEmail()).find()) {
                boolean isUserReg = new Registration().doRegistration(
                        userRegTOAPI.getLogin(),
                        userRegTOAPI.getPasscode(),
                        userRegTOAPI.getEmail());
                LOGGER.info(getClass().getName() + "\t" + "isUserReg: " + isUserReg);
                if (isUserReg)
                    responseMessage = "{\"message\":\"Success\"}";
                else
                    responseMessage = "{\"message\":\"Failed\"}";
            } else
                responseMessage = "{\"message\":\"Email isnt correct\"}";
        } else
            responseMessage = "{\"message\":\"There is one or more field(s) is empty\"}";
        LOGGER.info(getClass().getName() + "\t" + "responseMessage: " + responseMessage);
        resp.getWriter().write(responseMessage);
    }

    private boolean validateRequest(UserRegTOAPI to) {
        LOGGER.info(getClass().getName() + "\t" + "validateRequest Method");
        return !StringUtils.isBlank(to.getLogin()) &&
                !StringUtils.isBlank(to.getEmail()) &&
                !StringUtils.isBlank(to.getPasscode());
    }

    private UserRegTOAPI handleResponse(String args) {
        return new Gson().fromJson(args, UserRegTOAPI.class);
    }
}
