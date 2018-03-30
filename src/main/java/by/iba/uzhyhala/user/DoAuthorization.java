package by.iba.uzhyhala.user;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DoAuthorization extends HttpServlet implements IParseJsonString {

    private static final Logger logger = Logger.getLogger(DoAuthorization.class);

    private void doLogin(){

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        doLogin();
    }

    @Override
    public String prepareInputString(String login, String password) {
        return null;
    }

    @Override
    public List<?> handleInputString(String args) {
        return null;
    }
}
