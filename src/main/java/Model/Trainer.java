package Model;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a parent in the TKD management system
 */
public class Trainer extends Person{

    /**
     *  Constructs a new Trainer with the specified ID, name, last name, email, address, date of birth, number and belt level.
     * @param id            The unique identifier of the trainer.
     * @param name          The name of the trainer
     * @param lastName      The last name of the trainer.
     * @param email         The email of the trainer.
     * @param address       The address of the trainer.
     * @param dateOfBirth   The date of birth of the trainer.
     * @param number        The telephone number of the trainer.
     * @param beltLevel     The belt level of the trainer.
     */
    public Trainer(Integer id,String name, String lastName, String email, String address, int dateOfBirth, String number, String beltLevel) {
        super(id,name, lastName, email, address, dateOfBirth, number, beltLevel);
    }
    public Trainer(){}

    /**
     * prints out a trainer
     * @return
     */
    @Override
    public String toString() {
        // Coduri ANSI pentru culori
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_RESET = "\u001B[0m";

        return ANSI_CYAN + "💪 Trainer Details:" + ANSI_RESET + "\n" +
                ANSI_BLUE + "  Name: " + ANSI_RESET + name + " " + lastName + "\n" +
                ANSI_GREEN + "  Email: " + ANSI_RESET + email + "\n" +
                ANSI_GREEN + "  Address: " + ANSI_RESET + address + "\n" +
                ANSI_GREEN + "  Date of Birth: " + ANSI_RESET + dateOfBirth + "\n" +
                ANSI_GREEN + "  Phone Number: " + ANSI_RESET + number + "\n" +
                ANSI_YELLOW + "  Belt Level: " + ANSI_RESET + beltLevel;
    }

}
