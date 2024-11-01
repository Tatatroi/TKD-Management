package Service;

import Model.*;
import Repo.InMemoryRepo;

import java.util.HashMap;
import java.util.Map;

public class TKD_Service {
    private InMemoryRepo<Student> students;

    private InMemoryRepo<Trainer> trainers;

    private InMemoryRepo<Parent> parent;

    private InMemoryRepo<Session> sessions;

    public TKD_Service(InMemoryRepo<Student> students, InMemoryRepo<Trainer> trainers, InMemoryRepo<Parent> parent, InMemoryRepo<Session> sessions) {
        this.students = students;
        this.trainers = trainers;
        this.parent = parent;
        this.sessions = sessions;
    }
    public void assignGroupToTrainer(int trainerId, int sessionId){
        Trainer tr = trainers.get(trainerId);
        Session ss = sessions.get(sessionId);
        ss.trainer=tr;
        sessions.update(ss);
    }

    public void changeStudentGroup(int studentId,int sessionId){
        Student st = students.get(studentId);
        Session ss = sessions.get(sessionId);
        ss.getSessionStudents().add(st);
        st.setSession(ss);
    }

    public void changeBeltlevel(BeltExam beltExam){
        for(Student st: beltExam.getListOfResults().keySet()){
            if(beltExam.getListOfResults().get(st)==1){
                st.setBeltLevel(beltExam.getBeltColor());
            }
        }
    }

    public Map<String,Integer> numberOfAttendencesAndAbsences(int studentId){
        Student st=students.get(studentId);
        Map<String,Integer> attendencesAbsences= new HashMap<>();
        attendencesAbsences.put("Attendemces",0);
        attendencesAbsences.put("Absences",0);
        for(SessionDate sd: st.getSessionDateList().keySet()){
            if(st.getSessionDateList().get(sd)){
                int count=attendencesAbsences.get("Attendences");
                attendencesAbsences.put("Attendences",count+1);
            }
            else{
                int count=attendencesAbsences.get("Absences");
                attendencesAbsences.put("Absences",count+1);
            }
        }
        return attendencesAbsences;
    }

    /*
    1: atribuirea unei grupe la un trainer
    (trainer, session)
    2: schimbarea unui copil de la o grupa la alta
    3: atribuirea unei centuri
    4: numararea prezentelor si absentelor unui copil
     */
}
