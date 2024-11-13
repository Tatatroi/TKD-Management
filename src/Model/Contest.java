package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a contest in the TKD management system
 */
public class Contest extends Event{

    public String name;
    List<Student> students = new ArrayList<>();

    /**
     *
     * @param id            The unique identifier of the event.
     * @param startDate     The start date of the event.
     * @param endDate       The end date of the event.
     * @param price         The price of the event.
     * @param country       The country where the event takes place.
     * @param city          The city where the event takes place.
     * @param name          The name of the contest.
     * @param address       The address where the event takes place.
     */
    public Contest(int id,String startDate, String endDate, double price, String country, String city, String name, String address) {
        super(id,startDate, endDate, price, country, city, address);
        this.name = name;
    }

    /**
     * Gets the name of the contest.
     * @return The name of the contest.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the contest.
     * @param name  The name of the contest to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of students registered to the contest.
     * @return The list of students registered to the contest.
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Sets the list of students registered to the contest.
     * @param students  The list of students registered to the contest to set.
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }


    /**
     * Return as a string parameters that I need: name,price
     * @return
     */
    @Override
    public String toString() {
        return "Contest{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
