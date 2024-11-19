package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a session in the TKD management system
 */
public class Session implements HasID{
    public Integer id;
    public DifficultyLevel difficultyLevel;
    public int maximumParticipants;
    public Trainer trainer;
    public double pricePerSession;

    private List<Student> sessionStudents = new ArrayList<>();

    public List<Student> getSessionStudents() {
        return sessionStudents;
    }

    public void setSessionStudents(List<Student> sessionStudents) {
        this.sessionStudents = sessionStudents;
    }

    /**
     * Constructs a new Session with the specified ID, difficulty level, maximum participants, trainer, and price per session.
     * @param id                    The unique identifier of the session.
     * @param difficultyLevel       The difficulty level of the session( beginner, intermediary, advanced).
     * @param maximumParticipants   The max number of students that can be part of a session.
     * @param trainer               The trainer who manages the session and it's students.
     * @param pricePerSession       The price of one session.
     */
    public Session(Integer id , DifficultyLevel difficultyLevel, int maximumParticipants, Trainer trainer, double pricePerSession) {
        this.id=id;
        this.difficultyLevel = difficultyLevel;
        this.maximumParticipants = maximumParticipants;
        this.trainer = trainer;
        this.pricePerSession = pricePerSession;
    }

    /**
     * Gets the id of the session.
     * @return The id of the session.
     */
    @Override
    public Integer getId() {
        return id;
    }


    /**
     * Sets the id of the session.
     * @param id  The id of the session to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the difficulty level of the session.
     * @return The difficulty level of the session.
     */
    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    /**
     * Sets the difficulty level of the session.
     * @param difficultyLevel  The difficulty level of the session to set.
     */
    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    /**
     * Gets the maximum number of participants of the session.
     * @return The maximum number of participants of the session.
     */
    public int getMaximumParticipants() {
        return maximumParticipants;
    }

    /**
     * Sets the maximum number of participants of the session.
     * @param maximumParticipants  The maximum number of participants of the session to set.
     */
    public void setMaximumParticipants(int maximumParticipants) {
        this.maximumParticipants = maximumParticipants;
    }

    /**
     * Gets the trainer of the session.
     * @return The trainer of the session.
     */
    public Trainer getTrainer() {
        return trainer;
    }

    /**
     * Sets the trainer of the session.
     * @param trainer  The trainer of the session to set.
     */
    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    /**
     * Gets the price per session.
     * @return The price per session.
     */
    public double getPricePerSession() {
        return pricePerSession;
    }

    /**
     * Sets the price per session.
     * @param pricePerSession  The price per session to set.
     */
    public void setPricePerSession(double pricePerSession) {
        this.pricePerSession = pricePerSession;
    }

    @Override
    public String toString() {
        // Coduri ANSI pentru culori
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";

        return ANSI_CYAN + "📅 Session Details:" + ANSI_RESET + "\n" +
                ANSI_YELLOW + "  ID: " + ANSI_RESET + id + "\n" +
                ANSI_GREEN + "  Difficulty Level: " + ANSI_RESET + difficultyLevel + "\n" +
                ANSI_GREEN + "  Max Participants: " + ANSI_RESET + maximumParticipants + "\n" +
                ANSI_GREEN + "  Trainer: " + ANSI_RESET + trainer + "\n" +
                ANSI_GREEN + "  Price per Session: " + ANSI_RESET + pricePerSession + " lei/h\n" +
                ANSI_RED + "  Students in Session: " + ANSI_RESET + sessionStudents;
    }

}
