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

    @Override
    public String toString() {
        return "Trainer{" +
                "name='" + name + '\'' +
                ", LastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", number='" + number + '\'' +
                ", beltLevel='" + beltLevel + '\'' +
                '}';
    }
}
