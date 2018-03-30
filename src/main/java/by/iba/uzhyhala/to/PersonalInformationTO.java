package by.iba.uzhyhala.to;

public class PersonalInformationTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String country;
    private String street;
    private String house;
    private String zip;

    PersonalInformationTO(String firstName, String lastName, String email, String phone, String country, String street, String house, String zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.street = street;
        this.house = house;
        this.zip = zip;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHous() {
        return house;
    }

    public void setHous(String house) {
        this.house = house;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "PersonalInformationTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", country='" + country + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
