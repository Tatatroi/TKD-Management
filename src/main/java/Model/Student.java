package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a student in the TKD management system
 */
public class Student extends Person{
    public List<Integer> contestList = new ArrayList<>();
    public List<Integer> trainingCampList = new ArrayList<>();


    public int session;
    public List<SessionDate> sessionDateList = new ArrayList<>();
    public int parent;

    /**
     * Constructs a new Student with the specified ID, name, last name, email, address, date of birth, number, belt level and session.
     * @param id            The unique identifier of the student.
     * @param name          The name of the student.
     * @param lastName      The last name the student
     * @param email         The email address of the student.
     * @param address       The home address of the student.
     * @param dateOfBirth   The date of birth of the student.
     * @param number        The telephone number of the student.
     * @param beltLevel     The belt level of the student.
     * @param session       The session to which the student belongs.
     */
    public Student(Integer id, String name, String lastName, String email, String address, int dateOfBirth, String number, String beltLevel, int session) {
        super(id,name, lastName, email, address, dateOfBirth, number, beltLevel);
        this.session=session;
    }
    public Student(){}
    /**
     * Gets the list of contests where the student participated.
     * @return The list of contests.
     */
    public List<Integer> getContestList() {
        return contestList;
    }

    /**
     * Sets the list of contests where the student participated.
     * @param contestList The list of contests to set.
     */
    public void setContestList(List<Integer> contestList) {
        this.contestList = contestList;
    }

    /**
     * Gets the list of trainingcamps where the student participated.
     * @return The list of trainingcamps.
     */
    public List<Integer> getTrainingCampList() {
        return trainingCampList;
    }

    /**
     * Sets the list of trainingcamps where the student participated.
     * @param trainingCampList The list of trainingcamps to set.
     */
    public void setTrainingCampList(List<Integer> trainingCampList) {
        this.trainingCampList = trainingCampList;
    }

    /**
     * Gets the session to which the student belongs.
     * @return The session of the student.
     */
    public int getSession() {
        return session;
    }

    /**
     * Sets the session to which the student belongs.
     * @param session The session of the student to set.
     */
    public void setSession(int session) {
        this.session = session;
    }

    /**
     * Gets the map of sessiondates and bools to see where the student was absent and presesnt.
     * @return A map of sessiondates and bools.
     */
    public List<SessionDate> getSessionDateList() {
        return sessionDateList;
    }

    /**
     * Sets the map of sessiondates and bools to see where the student was absent and presesnt.
     * @param sessionDateList The sessionDate mao of the student to set.
     */
    public void setSessionDateList(List<SessionDate> sessionDateList) {
        this.sessionDateList = sessionDateList;
    }

    /**
     * Gets the parent of the student.
     * @return The parent of the student.
     */
    public int getParent() {
        return parent;
    }

    /**
     * Sets the parent of the student.
     * @param parent The parent to set.
     */
    public void setParent(int parent) {
        this.parent = parent;
    }


    @Override
    public String toString() {
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_RESET = "\u001B[0m";

        return ANSI_CYAN + "👤 Student Details:" + ANSI_RESET + "\n" +
                ANSI_BLUE + "  Name: " + ANSI_RESET + name + " " + lastName + "\n" +
                ANSI_GREEN + "  Email: " + ANSI_RESET + email + "\n" +
                ANSI_GREEN + "  Address: " + ANSI_RESET + address + "\n" +
                ANSI_GREEN + "  Date of Birth: " + ANSI_RESET + dateOfBirth + "\n" +
                ANSI_GREEN + "  Phone Number: " + ANSI_RESET + number + "\n" +
                ANSI_GREEN + "  Belt Level: " + ANSI_RESET + beltLevel + "\n";
    }

    /**
     * prints out a student
     */

    public String toString2() {
        // Coduri ANSI pentru culori
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_RESET = "\u001B[0m";

        return ANSI_CYAN + "👤 Student Details:" + ANSI_RESET + "\n" +
                ANSI_BLUE + "  Name: " + ANSI_RESET + name + " " + lastName + "\n" +
                ANSI_GREEN + "  Email: " + ANSI_RESET + email + "\n" +
                ANSI_GREEN + "  Address: " + ANSI_RESET + address + "\n" +
                ANSI_GREEN + "  Date of Birth: " + ANSI_RESET + dateOfBirth + "\n" +
                ANSI_GREEN + "  Phone Number: " + ANSI_RESET + number + "\n" +
                ANSI_GREEN + "  Belt Level: " + ANSI_RESET + beltLevel + "\n";
    }

//    public String toString2(){
//        final String ANSI_CYAN = "\u001B[36m";
//        final String ANSI_GREEN = "\u001B[32m";
//        final String ANSI_RESET = "\u001B[0m";
//        return ANSI_GREEN + "  Id: " + ANSI_RESET + getId() + ANSI_CYAN +"  Name: "  + name + " " + lastName + ANSI_RESET + "\n";
//    }

}
