package by.iba.uzhyhala.entity;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "auth_info", schema = "public", catalog = "auction")
public class AuthInfoEntity {
    private int id;
    private String login;
    private String password;
    private String email;
    private String uuid;
    private String role;
    private Date createDate;
    private String apiKey;
    private int rate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "login", nullable = false, length = -1)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password", nullable = false, length = -1)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email", nullable = false, length = -1)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "uuid", nullable = false, length = -1)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "role", nullable = false, length = 9)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @SuppressFBWarnings("EI_EXPOSE_REP")
    @Basic
    @Column(name = "create_date", nullable = true)
    public Date getCreateDate() {
        return createDate;
    }

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "api_key", nullable = true, length = 50)
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Basic
    @Column(name = "rate", nullable = false)
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

}
