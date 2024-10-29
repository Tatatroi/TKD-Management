package Model;

public class Session {
    public DifficultyLevel difficultyLevel;
    public int maximumParticipants;
    public Trainer trainer;
    public double pricePerSession;

    public Session(DifficultyLevel difficultyLevel, int maximumParticipants, Trainer trainer, double pricePerSession) {
        this.difficultyLevel = difficultyLevel;
        this.maximumParticipants = maximumParticipants;
        this.trainer = trainer;
        this.pricePerSession = pricePerSession;
    }
}
