package Model;

public abstract class Person {
    public String name;
    public String LastName;
    public String email;
    public String address;
    public int dateOfBirth;
    public String number;
    public String beltLevel;

    public Person(String name, String lastName, String email, String address, int dateOfBirth, String number, String beltLevel) {
        this.name = name;
        LastName = lastName;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.number = number;
        this.beltLevel = beltLevel;
    }



    public Person(String name, String lastName, String email, String address, int dateOfBirth) {
        this.name = name;
        LastName = lastName;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }
}
