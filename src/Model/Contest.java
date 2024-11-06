package Model;

import java.util.List;

public class Contest extends Event{

    public String name;
    List<Student> students;

    public Contest(String startDate, String endDate, double price, String country, String city, String name, String address,List<Student> students) {
        super(startDate, endDate, price, country, city, address);
        this.name = name;
        this.students=students;
    }

}
