package Model;

public abstract class Event implements HasID{
    private int id;
    public String startDate;
    public String endDate;
    public double price;
    public String country;
    public String city;
    public String address;


    public Event(String startDate, String endDate, double price, String country, String city, String address) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.country = country;
        this.city = city;
        this.address = address;
    }

    @Override
    public Integer getId() {return id;}
}
