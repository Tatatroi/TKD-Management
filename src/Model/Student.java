package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Student extends Person{
    public List<Contest> contestList=new ArrayList<>();
    public List<TrainingCamp> trainingCampList= new ArrayList<>();

//    private List<Session> assignedGroups=new ArrayList<>();
    public Session session;
    public HashMap<SessionDate,Boolean> sessionDateList;
    public Parent parent;

    public List<Contest> getContestList() {
        return contestList;
    }

    public void setContestList(List<Contest> contestList) {
        this.contestList = contestList;
    }

    public List<TrainingCamp> getTrainingCampList() {
        return trainingCampList;
    }

    public void setTrainingCampList(List<TrainingCamp> trainingCampList) {
        this.trainingCampList = trainingCampList;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public HashMap<SessionDate, Boolean> getSessionDateList() {
        return sessionDateList;
    }

    public void setSessionDateList(HashMap<SessionDate, Boolean> sessionDateList) {
        this.sessionDateList = sessionDateList;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Student(Integer id, String name, String lastName, String email, String address, int dateOfBirth, String number, String beltLevel, Parent parent, Session session) {
        super(id,name, lastName, email, address, dateOfBirth, number, beltLevel);
        this.parent=parent;
        this.session=session;
        //setId(idFromRepo);
    }

}
