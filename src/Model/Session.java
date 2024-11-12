package Model;

import java.util.ArrayList;
import java.util.List;

public class Session implements HasID{
    public Integer id;
    public DifficultyLevel difficultyLevel;
    public int maximumParticipants;
    public Trainer trainer;
    public double pricePerSession;

    private List<Student> sessionStudents;

    public List<Student> getSessionStudents() {
        return sessionStudents;
    }

    public void setSessionStudents(List<Student> sessionStudents) {
        this.sessionStudents = sessionStudents;
    }

    public Session(Integer id , DifficultyLevel difficultyLevel, int maximumParticipants, Trainer trainer, double pricePerSession) {
        this.id=id;
        this.difficultyLevel = difficultyLevel;
        this.maximumParticipants = maximumParticipants;
        this.trainer = trainer;
        this.pricePerSession = pricePerSession;
        this.sessionStudents=new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getMaximumParticipants() {
        return maximumParticipants;
    }

    public void setMaximumParticipants(int maximumParticipants) {
        this.maximumParticipants = maximumParticipants;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public double getPricePerSession() {
        return pricePerSession;
    }

    public void setPricePerSession(double pricePerSession) {
        this.pricePerSession = pricePerSession;
    }
}
