package by.iba.uzhyhala.to;

public class ConfigTO {

    //data base
    private String url;
    private String login;
    private String passwordDataBase;
    private String driver;

    //mail
    private String host;
    private String port;
    private String from;
    private String password;

    public ConfigTO(String url, String login, String passwordDataBase, String driver, String host, String port, String from, String password) {
        this.url = url;
        this.login = login;
        this.passwordDataBase = passwordDataBase;
        this.driver = driver;
        this.host = host;
        this.port = port;
        this.from = from;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordDataBase() {
        return passwordDataBase;
    }

    public void setPasswordDataBase(String passwordDataBase) {
        this.passwordDataBase = passwordDataBase;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
