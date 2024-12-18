package org.example.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a training camp in the TKD management system
 */
public class TrainingCamp extends Event{

    public int numberOfParticipants;
    List<Integer> students = new ArrayList<>();

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

    public TrainingCamp(){}
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
    public List<Integer> getStudents() {
        return students;
    }

    /**
     * Sets the list of students from the camp.
     * @param students  The list of students from the camp to set.
     */
    public void setStudents(List<Integer> students) {
        this.students = students;
    }


    @Override
    public String toString() {
        return "TrainingCamp{" +
                "numberOfParticipants=" + numberOfParticipants +
                ", students=" + students +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", price=" + price +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    /**
     * Return as a strinf parameters that I need: address, price
     * @return
     */

    public String toString2() {
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";

        return ANSI_BLUE + "🏕️ Training Camp Details:" + ANSI_RESET + "\n" +
                ANSI_YELLOW + "  Starting date: " + ANSI_RESET + getStartDate() + "\n" +
                ANSI_YELLOW + "  Address: " + ANSI_RESET + address + "\n" +
                ANSI_YELLOW + "  Price: " + ANSI_RED + price + " lei" + ANSI_RESET;
    }


}
