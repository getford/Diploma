package by.iba.uzhyhala.user;

import javax.servlet.http.HttpServlet;
import java.util.List;

public class DoAuthorization extends HttpServlet implements IParseJsonString {

    public void doLogin(){

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
