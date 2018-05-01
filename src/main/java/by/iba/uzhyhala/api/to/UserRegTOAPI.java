package by.iba.uzhyhala.api.to;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRegTOAPI {

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String passcode;

    public UserRegTOAPI(String login, String email, String passcode) {
        this.login = login;
        this.email = email;
        this.passcode = passcode;
    }

    public UserRegTOAPI() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    @Override
    public String toString() {
        return "UserRegTOAPI{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", passcode='" + passcode + '\'' +
                '}';
    }
}
