package Model;

import java.util.ArrayList;
import java.util.List;

public class TrainingCamp extends Event{

    public int numberOfParticipants;
    List<Student> students = new ArrayList<>();
    public TrainingCamp(String startDate, String endDate, double price, String country, String city,String address, int numberOfParticipants,List<Student> students) {
        super(startDate, endDate, price, country, city,address);
        this.numberOfParticipants = numberOfParticipants;
        this.students=students;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
