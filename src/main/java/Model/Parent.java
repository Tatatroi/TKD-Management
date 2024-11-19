package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a parent in the TKD management system
 */
public class Parent extends Person{
    List<Student> children = new ArrayList<>();

    /**
     * Constructs a new Parent with the specified ID, name, last name, email, address, date of birth and number.
     * @param id            The unique identifier of the person.
     * @param name          The name of the person
     * @param lastName      The last name of the person.
     * @param email         The email of the person.
     * @param address       The address of the person.
     * @param dateOfBirth   The date of birth of the person.
     * @param number        The telephone number of the person.
     */
    public Parent(Integer id,String name, String lastName, String email, String address, int dateOfBirth,String number) {
        super(id,name, lastName, email, address, dateOfBirth, number);
    }
    /**
     * Gets the list of children of the parent.
     * @return The list of children of the parent.
     */
    public List<Student> getChildren() {
        return children;
    }

    /**
     * Sets the list of children of the parent.
     * @param children  The list of children of the parent to set.
     */
    public void setChildren(List<Student> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        // Coduri ANSI pentru culori
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";

        return ANSI_CYAN + "👪 Parent Details:" + ANSI_RESET + "\n" +
                ANSI_YELLOW + "  Name: " + ANSI_RESET + name + " " + lastName + "\n" +
                ANSI_GREEN + "  Email: " + ANSI_RESET + email + "\n" +
                ANSI_GREEN + "  Phone: " + ANSI_RESET + number + "\n" +
                ANSI_GREEN + "  Date of Birth: " + ANSI_RESET + dateOfBirth + "\n" +
                ANSI_GREEN + "  Address: " + ANSI_RESET + address + "\n" +
                ANSI_RED + "  Children: " + ANSI_RESET + children;
    }

}