package by.iba.uzhyhala.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address", schema = "public", catalog = "auction")
public class AddressEntity implements Serializable {
    private static final long serialVersionUID = 1;

    private int id;
    private String uuidUser;
    private String country;
    private String city;
    private String street;
    private Integer house;
    private Integer zip;

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
    @Column(name = "country", nullable = true, length = -1)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "city", nullable = true, length = -1)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "street", nullable = true, length = -1)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "house", nullable = true)
    public Integer getHouse() {
        return house;
    }

    public void setHouse(Integer house) {
        this.house = house;
    }

    @Basic
    @Column(name = "zip", nullable = true)
    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }
}
