package by.iba.uzhyhala.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "feedback", schema = "public", catalog = "auction")
public class FeedbackEntity implements Serializable {

    private static final long serialVersionUID = 9167851454506425718L;
    private int id;
    private String uuid;
    private Integer idUser;
    private String feedbackText;
    private Date date;
    private AuthInfoEntity authInfoByIdUser;

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
    @Column(name = "uuid", nullable = true, length = -1)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "id_user", nullable = true)
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Basic
    @Column(name = "feedback_text", nullable = false, length = -1)
    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    @Basic
    @Column(name = "date", nullable = true)
    public Date getDate() {
        return new Date(date.getTime());
    }

    public void setDate(Date date) {
        this.date = new Date(date.getTime());
    }

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", insertable = false, updatable = false)
    public AuthInfoEntity getAuthInfoByIdUser() {
        return authInfoByIdUser;
    }

    public void setAuthInfoByIdUser(AuthInfoEntity authInfoByIdUser) {
        this.authInfoByIdUser = authInfoByIdUser;
    }
}
