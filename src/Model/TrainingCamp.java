package Model;

import java.util.List;

public class TrainingCamp extends Event{

    public int numberOfParticipants;
    List<Student> students;
    public TrainingCamp(String startDate, String endDate, double price, String country, String city,String address, int numberOfParticipants,List<Student> students) {
        super(startDate, endDate, price, country, city,address);
        this.numberOfParticipants = numberOfParticipants;
        this.students=students;
    }
}
