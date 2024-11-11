package Model;

import java.util.ArrayList;
import java.util.List;

public class Contest extends Event{

    public String name;
    List<Student> students = new ArrayList<>();

    public Contest(String startDate, String endDate, double price, String country, String city, String name, String address) {
        super(startDate, endDate, price, country, city, address);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
