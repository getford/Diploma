package by.iba.uzhyhala.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "auth_info", schema = "public", catalog = "auction")
public class AuthInfoEntity implements Serializable {
    private int id;
    private String login;
    private String password;
    private String email;
    private String uuid;
    private String role;
    private AddressEntity addressByUuid;
    private Collection<FeedbackEntity> feedbacksById;
    private LotEntity lotByUuid;
    private PersonalInformationEntity personalInformationByUuid;

    @Id
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
    @Column(name = "role", nullable = false, length = -1)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthInfoEntity that = (AuthInfoEntity) o;
        return id == that.id &&
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password) &&
                Objects.equals(email, that.email) &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, login, password, email, uuid, role);
    }

    @ManyToOne
    @JoinColumn(name = "uuid", referencedColumnName = "uuid_user", nullable = false)
    public AddressEntity getAddressByUuid() {
        return addressByUuid;
    }

    public void setAddressByUuid(AddressEntity addressByUuid) {
        this.addressByUuid = addressByUuid;
    }

    @OneToMany(mappedBy = "authInfoByIdUser")
    public Collection<FeedbackEntity> getFeedbacksById() {
        return feedbacksById;
    }

    public void setFeedbacksById(Collection<FeedbackEntity> feedbacksById) {
        this.feedbacksById = feedbacksById;
    }

    @ManyToOne
    @JoinColumn(name = "uuid", referencedColumnName = "uuid_user_seller", nullable = false)
    public LotEntity getLotByUuid() {
        return lotByUuid;
    }

    public void setLotByUuid(LotEntity lotByUuid) {
        this.lotByUuid = lotByUuid;
    }

    @ManyToOne
    @JoinColumn(name = "uuid", referencedColumnName = "uuid_user", nullable = false)
    public PersonalInformationEntity getPersonalInformationByUuid() {
        return personalInformationByUuid;
    }

    public void setPersonalInformationByUuid(PersonalInformationEntity personalInformationByUuid) {
        this.personalInformationByUuid = personalInformationByUuid;
    }
}
