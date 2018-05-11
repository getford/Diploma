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
}
