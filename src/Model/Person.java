package Model;

public abstract class Person implements HasID{
    private int id;
    public String name;
    public String LastName;
    public String email;
    public String address;
    public int dateOfBirth;
    public String number;
    public String beltLevel;

    public Person(Integer id,String name, String lastName, String email, String address, int dateOfBirth, String number, String beltLevel) {
        this.id=id;
        this.name = name;
        LastName = lastName;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.number = number;
        this.beltLevel = beltLevel;
    }



    public Person(Integer id,String name, String lastName, String email, String address, int dateOfBirth) {
        this.id=id;
        this.name = name;
        LastName = lastName;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }

    public void setId(int id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBeltLevel() {
        return beltLevel;
    }

    public void setBeltLevel(String beltLevel) {
        this.beltLevel = beltLevel;
    }

    @Override
    public Integer getId() {return id;}
}
