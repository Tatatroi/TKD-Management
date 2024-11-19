package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a student in the TKD management system
 */
public class Student extends Person{
    public List<Contest> contestList = new ArrayList<>();
    public List<TrainingCamp> trainingCampList = new ArrayList<>();

//    private List<Session> assignedGroups=new ArrayList<>();
    public Session session;
    public HashMap<SessionDate,Boolean> sessionDateList = new HashMap<>();
    public Parent parent;

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
    public Student(Integer id, String name, String lastName, String email, String address, int dateOfBirth, String number, String beltLevel, Session session) {
        super(id,name, lastName, email, address, dateOfBirth, number, beltLevel);
        this.session=session;
    }

    /**
     * Gets the list of contests where the student participated.
     * @return The list of contests.
     */
    public List<Contest> getContestList() {
        return contestList;
    }

    /**
     * Sets the list of contests where the student participated.
     * @param contestList The list of contests to set.
     */
    public void setContestList(List<Contest> contestList) {
        this.contestList = contestList;
    }

    /**
     * Gets the list of trainingcamps where the student participated.
     * @return The list of trainingcamps.
     */
    public List<TrainingCamp> getTrainingCampList() {
        return trainingCampList;
    }

    /**
     * Sets the list of trainingcamps where the student participated.
     * @param trainingCampList The list of trainingcamps to set.
     */
    public void setTrainingCampList(List<TrainingCamp> trainingCampList) {
        this.trainingCampList = trainingCampList;
    }

    /**
     * Gets the session to which the student belongs.
     * @return The session of the student.
     */
    public Session getSession() {
        return session;
    }

    /**
     * Sets the session to which the student belongs.
     * @param session The session of the student to set.
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * Gets the map of sessiondates and bools to see where the student was absent and presesnt.
     * @return A map of sessiondates and bools.
     */
    public HashMap<SessionDate, Boolean> getSessionDateList() {
        return sessionDateList;
    }

    /**
     * Sets the map of sessiondates and bools to see where the student was absent and presesnt.
     * @param sessionDateList The sessionDate mao of the student to set.
     */
    public void setSessionDateList(HashMap<SessionDate, Boolean> sessionDateList) {
        this.sessionDateList = sessionDateList;
    }

    /**
     * Gets the parent of the student.
     * @return The parent of the student.
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * Sets the parent of the student.
     * @param parent The parent to set.
     */
    public void setParent(Parent parent) {
        this.parent = parent;
    }


    /**
     * prints out a student
     */
    @Override
    public String toString() {
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
                ANSI_GREEN + "  Belt Level: " + ANSI_RESET + beltLevel + "\n" +
                ANSI_YELLOW + "  Contests: " + ANSI_RESET + contestList + "\n" +
                ANSI_YELLOW + "  Training Camps: " + ANSI_RESET + trainingCampList + "\n" +
                ANSI_YELLOW + "  Current Session: " + ANSI_RESET + session + "\n" +
                ANSI_YELLOW + "  Session Dates: " + ANSI_RESET + sessionDateList + "\n" +
                ANSI_RED + "  Parent: " + ANSI_RESET + parent;
    }

}
