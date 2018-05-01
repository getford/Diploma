package by.iba.uzhyhala.api.user;

import by.iba.uzhyhala.api.to.UserRegTOAPI;
import by.iba.uzhyhala.user.Registration;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/api/user/reg")
public class RegistrationAPI extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegistrationAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String responseMessage = "";
        UserRegTOAPI userRegTOAPI = handleResponse(req.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
        if (validateRequest(userRegTOAPI)) {
            boolean isUserReg = new Registration().doRegistration(userRegTOAPI.getLogin(), userRegTOAPI.getPasscode(), userRegTOAPI.getEmail());
            if (isUserReg)
                responseMessage = "{\"message\":\"Success\"}";
            else
                responseMessage = "{\"message\":\"Failed\"}";
        } else
            responseMessage = "{\"message\":\"There is one or more field(s) is empty\"}";
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
