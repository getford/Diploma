package by.iba.uzhyhala.to;

public class UserTO extends PersonalInformationTO {
    private String login;
    private String password;
    private String email;
    private String uuid;
    private String role;

    public UserTO(String firstName, String lastName, String email, String phone, String country, String street, String house, String zip, String login, String password, String email1, String uuid, String role) {
        super(firstName, lastName, email, phone, country, street, house, zip);
        this.login = login;
        this.password = password;
        this.email = email1;
        this.uuid = uuid;
        this.role = role;
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

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserTO{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", uuid='" + uuid + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
