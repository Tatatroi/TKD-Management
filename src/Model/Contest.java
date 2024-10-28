package Model;

public class Contest extends Event{

    public String name;


    public Contest(String startDate, String endDate, double price, String country, String city, String name, String address) {
        super(startDate, endDate, price, country, city, address);
        this.name = name;
    }

}
