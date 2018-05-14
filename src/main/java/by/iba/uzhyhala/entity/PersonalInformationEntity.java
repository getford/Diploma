package by.iba.uzhyhala.entity;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "personal_information", schema = "public", catalog = "auction")
public class PersonalInformationEntity implements Serializable {

    private static final long serialVersionUID = -1178846911523194106L;
    private int id;
    private String uuidUser;
    private String firstName;
    private String lastName;
    private Date bday;

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
    @Column(name = "uuid_user", nullable = true, length = -1)
    public String getUuidUser() {
        return uuidUser;
    }

    public void setUuidUser(String uuidUser) {
        this.uuidUser = uuidUser;
    }

    @Basic
    @Column(name = "first_name", nullable = true, length = -1)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = -1)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @SuppressFBWarnings("EI_EXPOSE_REP")
    @Basic
    @Column(name = "bday", nullable = true)
    public Date getBday() {
        // return new Date(bday.getTime());
        return bday;
    }

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public void setBday(Date bday) {
        // this.bday = new Date(bday.getTime());
        this.bday = bday;
    }
}
