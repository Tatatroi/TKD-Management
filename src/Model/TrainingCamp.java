package Model;

public class TrainingCamp extends Event{

    public int numberOfParticipants;
    public TrainingCamp(String startDate, String endDate, double price, String country, String city,String address, int numberOfParticipants) {
        super(startDate, endDate, price, country, city,address);
        this.numberOfParticipants = numberOfParticipants;
    }
}
