package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a training camp in the TKD management system
 */
public class TrainingCamp extends Event{

    public int numberOfParticipants;
    List<Student> students = new ArrayList<>();

    /**
     * Constructs a new Training camp with the specified ID, start date, end date, price, country, city, address and the max number of participants..
     * @param id            The unique identifier of the event.
     * @param startDate     The start date of the event.
     * @param endDate       The end date of the event.
     * @param price         The price of the event.
     * @param country       The country where the event takes place.
     * @param city          The city where the event takes place.
     * @param address       The address where the event takes place.
     * @param numberOfParticipants The max number of participants that can go to the camp.
     */
    public TrainingCamp(int id,String startDate, String endDate, double price, String country, String city,String address, int numberOfParticipants) {
        super(id,startDate, endDate, price, country, city,address);
        this.numberOfParticipants = numberOfParticipants;
    }

    /**
     * Gets the max number of participants of the camp.
     * @return The max number of participants of the camp.
     */
    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    /**
     * Sets the max number of participants of the camp.
     * @param numberOfParticipants  The max number of participants of the camp to set.
     */
    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    /**
     * Gets the list of students from the camp.
     * @return The list of students from the camp.
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Sets the list of students from the camp.
     * @param students  The list of students from the camp to set.
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }


    /**
     * Return as a strinf parameters that I need: address, price
     * @return
     */
    @Override
    public String toString() {
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";

        return "TrainingCamp{" +
                "address='" + address + '\'' +
                ", price=" + ANSI_RED + price + ANSI_RESET +
                '}';
    }

}
