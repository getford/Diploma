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

    public String getEmail() {
        return email;
    }

    public String getPasscode() {
        return passcode;
    }

}
