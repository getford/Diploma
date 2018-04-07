package by.iba.uzhyhala.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "feedback", schema = "public", catalog = "auction")
public class FeedbackEntity {
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
    @Column(name = "id_user", nullable = true, updatable = false, insertable = false)
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
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackEntity that = (FeedbackEntity) o;

        if (id != that.id) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (idUser != null ? !idUser.equals(that.idUser) : that.idUser != null) return false;
        if (feedbackText != null ? !feedbackText.equals(that.feedbackText) : that.feedbackText != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (idUser != null ? idUser.hashCode() : 0);
        result = 31 * result + (feedbackText != null ? feedbackText.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    public AuthInfoEntity getAuthInfoByIdUser() {
        return authInfoByIdUser;
    }

    public void setAuthInfoByIdUser(AuthInfoEntity authInfoByIdUser) {
        this.authInfoByIdUser = authInfoByIdUser;
    }
}
