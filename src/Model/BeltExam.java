package Model;

import java.util.List;

public class BeltExam extends Event{

    public List<Student> listOfContenders;
    public String BeltColor;


    public BeltExam(String startDate, String endDate, double price, String country, String city, String address, List<Student> listOfContenders, String BeltColor) {
        super(startDate, endDate, price, country, city, address);
        this.listOfContenders = listOfContenders;
        this.BeltColor = BeltColor;
    }
}
