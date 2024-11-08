package Service;

import Model.*;
import Repo.InMemoryRepo;

import java.io.IOException;
import java.util.*;

public class TKD_Service {
    private InMemoryRepo<Student> students;

    private InMemoryRepo<Trainer> trainers;

    private InMemoryRepo<Parent> parents;

    private InMemoryRepo<Session> sessions;

    private InMemoryRepo<Contest> contests;

    private InMemoryRepo<TrainingCamp> trainingCamps;

    private InMemoryRepo<BeltExam> beltExams;

    public TKD_Service(InMemoryRepo<Student> students, InMemoryRepo<Trainer> trainers, InMemoryRepo<Parent> parent, InMemoryRepo<Session> sessions, InMemoryRepo<Contest> contests, InMemoryRepo<TrainingCamp> trainingCamps) {
        this.students = students;
        this.trainers = trainers;
        this.parents = parent;
        this.sessions = sessions;
        this.contests = contests;
        this.trainingCamps = trainingCamps;
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
        students.update(st);
        sessions.update(ss);
    }

    public void changeBeltlevel(BeltExam beltExam){
        for(Student st: beltExam.getListOfResults().keySet()){
            if(beltExam.getListOfResults().get(st)==1){
                st.setBeltLevel(beltExam.getBeltColor());
                students.update(st);
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

    private void findCombinations(List<Map.Entry<Integer, Double>> eventPairs, double remainingAmount,int start, List<Integer> currentCombination, List<List<Integer>> results) {
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

    public void addAttendance(int studentId,int sessionId,boolean attendance,String weekday,String date){
        Student s=students.get(studentId);
        Session ss = sessions.get(sessionId);
        SessionDate sessionDate = new SessionDate(weekday,date,ss);
        s.getSessionDateList().put(sessionDate,attendance);
        students.update(s);
    }

    public void addStudentToContest(int studentId,int contestId){
        Student st = students.get(studentId);
        Contest ct = contests.get(contestId);
        st.getContestList().add(ct);
        students.update(st);
    }

    public void addStudentToTraining(int studentId,int trainingCampId){
        Student st = students.get(studentId);
        TrainingCamp tc = trainingCamps.get(trainingCampId);
        st.getTrainingCampList().add(tc);
        students.update(st);
    }

    public void addObject(Object o) throws IOException {
        if(o instanceof Student){
            if(students.getAll().stream().anyMatch(st->st.getId()==((Student) o).getId())){
                throw new RuntimeException();
            }
            students.add((Student) o);

        }
        else if(o instanceof Trainer){
            if(trainers.getAll().stream().anyMatch(tr->tr.getId()==((Trainer) o).getId())){
                throw new RuntimeException();
            }
            trainers.add((Trainer) o);
        }
        else if(o instanceof Parent){
            if(parents.getAll().stream().anyMatch(pt->pt.getId()==((Parent) o).getId())){
                throw new RuntimeException();
            }
            parents.add((Parent) o);
        }
        else if(o instanceof Session){
            if(sessions.getAll().stream().anyMatch(ss->ss.getId()==((Session) o).getId())){
                throw new RuntimeException();
            }
            sessions.add((Session) o);
        }
        else if(o instanceof BeltExam){
            if(beltExams.getAll().stream().anyMatch(bx->bx.getId()==((BeltExam) o).getId())){
                throw new RuntimeException();
            }
            beltExams.add((BeltExam) o);
        }
        else if(o instanceof Contest){
            if(contests.getAll().stream().anyMatch(ct->ct.getId()==((Contest) o).getId())){
                throw new RuntimeException();
            }
            contests.add((Contest) o);
        }
        else if(o instanceof TrainingCamp){
            if(trainingCamps.getAll().stream().anyMatch(tc->tc.getId()==((TrainingCamp) o).getId())){
                throw new RuntimeException();
            }
            trainingCamps.add((TrainingCamp) o);
        }
    }


    public void removeStudent(Integer studentID){
        students.remove(studentID);
    }
    public void removeTrainer(Integer trainerID){
        trainers.remove(trainerID);
    }
    public void removeParent(Integer parentID){
        parents.remove(parentID);
    }
    public void removeSession(Integer sessionID){
        sessions.remove(sessionID);
    }
    public void removeBeltExam(Integer beltExamID){
        beltExams.remove(beltExamID);
    }
    public void removeContest(Integer contestID){
        contests.remove(contestID);
    }
    public void removeTrainingCamp(Integer trainingCampID){
        trainingCamps.remove(trainingCampID);
    }


}
