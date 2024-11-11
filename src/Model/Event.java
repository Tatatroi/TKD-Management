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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public Integer getId() {return id;}
}
