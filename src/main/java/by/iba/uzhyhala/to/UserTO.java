package by.iba.uzhyhala.to;

public class UserTO extends PersonalInformationTO {
    private String login;
    private String password;
    private String uuid;

    public UserTO(String firstName, String lastName, String email, String phone, String country, String street, String house, String zip) {
        super(firstName, lastName, email, phone, country, street, house, zip);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "UserTO{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
