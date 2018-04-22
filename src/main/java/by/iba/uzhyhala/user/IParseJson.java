package by.iba.uzhyhala.user;

import by.iba.uzhyhala.entity.AuthInfoEntity;

import java.util.List;

public interface IParseJson {
    String prepareInputString(String login, String password, String emil);

    List<AuthInfoEntity> handleInputString(String args);
}