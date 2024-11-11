package Model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeltExam extends Event{

    public Map< Student,Integer> listOfResults = new HashMap<>();// 1: passed, 0: failed, -1: absent
    public String BeltColor;


    public Map<Student, Integer> getListOfResults() {
        return listOfResults;
    }

    public void setListOfResults(Map<Student, Integer> listOfResults) {
        this.listOfResults = listOfResults;
    }

    public String getBeltColor() {
        return BeltColor;
    }

    public void setBeltColor(String beltColor) {
        BeltColor = beltColor;
    }

    public BeltExam(String startDate, String endDate, double price, String country, String city, String address, String BeltColor) {
        super(startDate, endDate, price, country, city, address);
        this.BeltColor = BeltColor;
    }
}
