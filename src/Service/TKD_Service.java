package Service;

import Model.*;
import Repo.InMemoryRepo;

import java.util.*;

public class TKD_Service {
    private InMemoryRepo<Student> students;

    private InMemoryRepo<Trainer> trainers;

    private InMemoryRepo<Parent> parent;

    private InMemoryRepo<Session> sessions;

    private InMemoryRepo<Contest> contests;

    private InMemoryRepo<TrainingCamp> trainingCamps;

    private InMemoryRepo<BeltExam> beltExams;

    public TKD_Service(InMemoryRepo<Student> students, InMemoryRepo<Trainer> trainers, InMemoryRepo<Parent> parent, InMemoryRepo<Session> sessions, InMemoryRepo<Contest> contests, InMemoryRepo<TrainingCamp> trainingCamps) {
        this.students = students;
        this.trainers = trainers;
        this.parent = parent;
        this.sessions = sessions;
        this.contests = contests;
        this.trainingCamps = trainingCamps;
    }

    /* old constructor
    public TKD_Service(InMemoryRepo<Student> students, InMemoryRepo<Trainer> trainers, InMemoryRepo<Parent> parent, InMemoryRepo<Session> sessions) {
        this.students = students;
        this.trainers = trainers;
        this.parent = parent;
        this.sessions = sessions;
    }
    */

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
        attendencesAbsences.put("Attendences",0);
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

    public void newContest(Contest newContest){
        contests.add(newContest);
    }

    private void findCombinations(List<Map.Entry<Integer, Double>> eventPairs, double remainingAmount,
                                  int start, List<Integer> currentCombination, List<List<Integer>> results) {
        if (remainingAmount == 0) {
            results.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int i = start; i < eventPairs.size(); i++) {
            Map.Entry<Integer, Double> event = eventPairs.get(i);
            if (event.getValue() > remainingAmount) continue;

            currentCombination.add(event.getKey());
            findCombinations(eventPairs, remainingAmount - event.getValue(), i, currentCombination, results);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    public void eventsThatdontExceedAmountOfMoney(double amountOfMoney){
        List<Map.Entry<Integer,Double>> eventPairs = new ArrayList<>();

        for(int i = 0; i<contests.getAll().size(); i++){
            Contest ct = contests.getAll().get(i);
            eventPairs.add(new AbstractMap.SimpleEntry<>(ct.getId(),ct.price)) ;
        }
        for(int i = 0; i<trainingCamps.getAll().size(); i++){
            TrainingCamp ct = trainingCamps.getAll().get(i);
            eventPairs.add(new AbstractMap.SimpleEntry<>(ct.getId(),ct.price)) ;
        }

        List<List<Integer>> results = new ArrayList<>();
        findCombinations(eventPairs, amountOfMoney, 0, new ArrayList<>(), results);

        // Afișăm rezultatele --> Atentie trebuie modificate
        System.out.println("Events combinations that don t exceed the sum : " + amountOfMoney);
        for (List<Integer> combination : results) {
            System.out.println("ID-urile evenimentelor: " + combination);
        }

    }

    // atribuire copil la examen de centura

    public void addStudentToBeltExam(int idStudent, int idBeltExam){
        Student s = students.get(idStudent);
        BeltExam belt = beltExams.get(idBeltExam);
        belt.getListOfResults().put(s,-1);
        beltExams.update(belt);
    }

    // atribuire rezultat examen de centura

    public void addResultBeltExam(int idStudent, int idBeltExam, boolean promoted){
        Student s = students.get(idStudent);
        BeltExam belt = beltExams.get(idBeltExam);
        if(promoted){
            belt.getListOfResults().put(s,1); // promoted
        }
        else{
            belt.getListOfResults().put(s,0); // failed
        }
        beltExams.update(belt);
    }

    public void addStudent(Student newStudent){
        students.add(newStudent);
    }

    public void addTrainer(Trainer newTrainer){
        trainers.add(newTrainer);
    }

    public void addParent(Parent newParent){
        parent.add(newParent);
    }

    public void addSession(Session newSession){
        sessions.add(newSession);
    }

    public void addBeltExam(BeltExam newBeltExam){
        beltExams.add(newBeltExam);
    }

    public void addContest(Contest newContest){
        contests.add(newContest);
    }

    public void addTrainingCamp(TrainingCamp newCamp){
        trainingCamps.add(newCamp);
    }
}
