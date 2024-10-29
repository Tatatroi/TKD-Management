package Model;

import java.util.HashMap;
import java.util.List;

public class Student extends Person{
    public List<Contest> contestList;
    public List<TrainingCamp> trainingCampList;
    public HashMap<SessionDate,Boolean> sessionDateList;
    public Parent parent;
    public Student(String name, String lastName, String email, String address, int dateOfBirth, String number, String beltLevel,Parent parent) {
        super(name, lastName, email, address, dateOfBirth, number, beltLevel);
        this.parent=parent;
    }

}
