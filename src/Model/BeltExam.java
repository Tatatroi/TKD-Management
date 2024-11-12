package Model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a belt exam in the TKD management system
 */
public class BeltExam extends Event{

    public Map< Student,Integer> listOfResults = new HashMap<>();// 1: passed, 0: failed, -1: absent
    public String BeltColor;

    /**
     *
     * @param id            The unique identifier of the event.
     * @param startDate     The start date of the event.
     * @param endDate       The end date of the event.
     * @param price         The price of the event.
     * @param country       The country where the event takes place.
     * @param city          The city where the event takes place.
     * @param address       The address where the event takes place.
     * @param BeltColor     The belt color for which the exam is taken.
     */
    public BeltExam(int id,String startDate, String endDate, double price, String country, String city, String address, String BeltColor) {
        super(id,startDate, endDate, price, country, city, address);
        this.BeltColor = BeltColor;
    }

    /**
     * Gets the list of results from the exam.
     * @return The list of results from the exam as a map of student and int.
     * The int can be -1( did not participate/exam hasn't taken place yet), 0( failed ) and 1( passed ).
     */
    public Map<Student, Integer> getListOfResults() {
        return listOfResults;
    }

    /**
     * Sets the list of results from the exam.
     * @param listOfResults  The list of results from the exam to set as a map of student and int.
     * The int can be -1( did not participate/exam hasn't taken place yet), 0( failed ) and 1( passed ).
     */
    public void setListOfResults(Map<Student, Integer> listOfResults) {
        this.listOfResults = listOfResults;
    }

    /**
     * Gets the belt color from the exam.
     * @return The belt color from the exam.
     */
    public String getBeltColor() {
        return BeltColor;
    }

    /**
     * Sets the belt color from the exam.
     * @param beltColor  The belt color from the exam to set.
     */
    public void setBeltColor(String beltColor) {
        BeltColor = beltColor;
    }

}
