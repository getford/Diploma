package by.iba.uzhyhala.user;

import java.util.List;

public interface IParseJsonString {
    String prepareInputString(String login, String password);
    List<?> handleInputString(String args);
}
