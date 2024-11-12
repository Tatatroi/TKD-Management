package Service;

import Model.*;
import Repo.InMemoryRepo;

import java.io.IOException;
import java.util.*;

/**
 * A service class that provides the business logic for the university system.
 */
public class TKD_Service {
    private InMemoryRepo<Student> students;

    private InMemoryRepo<Trainer> trainers;

    private InMemoryRepo<Parent> parents;

    private InMemoryRepo<Session> sessions;

    private InMemoryRepo<Contest> contests;

    private InMemoryRepo<TrainingCamp> trainingCamps;

    private InMemoryRepo<BeltExam> beltExams;

    /**
     * Constructs a new TKD_Service with the given repositories.
     * @param students          The repository for students.
     * @param trainers          The repository for trainers.
     * @param parent            The repository for parents.
     * @param sessions          The repository for sessions.
     * @param contests          The repository for contests.
     * @param trainingCamps     The repository for training camps.
     * @param beltExams         The repository for belt exams.
     */
    public TKD_Service(InMemoryRepo<Student> students, InMemoryRepo<Trainer> trainers, InMemoryRepo<Parent> parent, InMemoryRepo<Session> sessions, InMemoryRepo<Contest> contests, InMemoryRepo<TrainingCamp> trainingCamps, InMemoryRepo<BeltExam> beltExams) {
        this.students = students;
        this.trainers = trainers;
        this.parents = parent;
        this.sessions = sessions;
        this.contests = contests;
        this.trainingCamps = trainingCamps;
        this.beltExams = beltExams;
    }

    /**
     * Change the trainer of a session.
     * @param trainerId     The unique identifier of a trainer.
     * @param sessionId     The unique identifier of a session.
     */
    public void assignGroupToTrainer(int trainerId, int sessionId){
        Trainer tr = trainers.get(trainerId);
        Session ss = sessions.get(sessionId);
        ss.trainer=tr;
        sessions.update(ss);
    }

    /**
     * Change the session of a student and remove him from the previous one.
     * @param studentId     The unique identifier of a student.
     * @param sessionId     The unique identifier of a session.
     */
    public void changeStudentGroup(int studentId,int sessionId){
        Student st = students.get(studentId);
        Session new_ss = sessions.get(sessionId);
        Session old_ss = sessions.get(st.getSession().getId());

        old_ss.getSessionStudents().remove(st);
        new_ss.getSessionStudents().add(st);
        st.setSession(new_ss);
        students.update(st);
        sessions.update(new_ss);
        sessions.update(old_ss);
    }

    /**
     * Changes the belt color of a student if he passed the exam.
     * @param beltExamID    The unique identifier of a belt exam.
     */
    public void changeBeltlevel(Integer beltExamID){
        for(Student st: beltExams.get(beltExamID).getListOfResults().keySet()){
            if(beltExams.get(beltExamID).getListOfResults().get(st)==1){
                st.setBeltLevel(beltExams.get(beltExamID).getBeltColor());
                students.update(st);
            }
        }
    }

    /**
     * Counts the number of attendances and absences of a student.
     * @param studentId The unique identifier of a student.
     * @return Map containing the number of attendances and absences of a student.
     */
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

    /**
     * Adds a student to a belt exam.
     * @param idStudent     The unique identifier of a student.
     * @param idBeltExam    The unique identifier of a belt exam.
     */
    public void addStudentToBeltExam(int idStudent, int idBeltExam){
        Student s = students.get(idStudent);
        BeltExam belt = beltExams.get(idBeltExam);
        belt.getListOfResults().put(s,-1);
        beltExams.update(belt);
    }

    // atribuire rezultat examen de centura

    /**
     * Updates the result of a belt exam of a student and if promoted changes the belt color.
     * @param idStudent     The unique identifier of a student.
     * @param idBeltExam    The unique identifier of a belt exam.
     * @param promoted      The result of the exam, passed(true) or failed(false).
     */
    public void addResultBeltExam(int idStudent, int idBeltExam, boolean promoted){
        Student s = students.get(idStudent);
        BeltExam belt = beltExams.get(idBeltExam);
        if(promoted){
            belt.getListOfResults().put(s,1); // promoted
            changeBeltlevel(idBeltExam);
        }
        else{
            belt.getListOfResults().put(s,0); // failed
        }
        beltExams.update(belt);
    }

    /**
     * Adds an attendance/absence to the list of session dates from the student.
     * @param studentId     The unique identifier of a student.
     * @param sessionId     The unique identifier of a session.
     * @param attendance    The attendance of a student which can be true(was present) or false(wasn't present).
     * @param weekday       The day of the week of the session.
     * @param date          The exact date the session took place.
     */
    public void addAttendance(int studentId,int sessionId,boolean attendance,String weekday,String date){
        Student s=students.get(studentId);
        Session ss = sessions.get(sessionId);
        SessionDate sessionDate = new SessionDate(weekday,date,ss);
        s.getSessionDateList().put(sessionDate,attendance);
        students.update(s);
    }

    /**
     * Adds a student to a contest.
     * @param studentId     The unique identifier of a student.
     * @param contestId     The unique identifier of a contest.
     */
    public void addStudentToContest(int studentId,int contestId){
        Student st = students.get(studentId);
        Contest ct = contests.get(contestId);
        st.getContestList().add(ct);
        students.update(st);
    }

    /**
     * Adds a student to a training camp.
     * @param studentId         The unique identifier of a student.
     * @param trainingCampId    The unique identifier of a training camp.
     */
    public void addStudentToTraining(int studentId,int trainingCampId){
        Student st = students.get(studentId);
        TrainingCamp tc = trainingCamps.get(trainingCampId);
        st.getTrainingCampList().add(tc);
        students.update(st);
    }

    /**
     * Adds a student to a parent, by searching the for the parent by email to see if it needs to be added to the repo.
     * @param student   The student object that needs to be added to the parent.
     * @param parent    The parent object that needs to be updated/ created.
     */
    public void addStudentToParent(Student student, Parent parent){
        if(findParent(parent.getEmail())){
            Parent updateParent = parents.getAll().stream().filter(pt ->Objects.equals(pt.getEmail(),parent.getEmail())).findFirst().orElse(null);
            updateParent.getChildren().add(student);
            parents.update(updateParent);
            student.setParent(updateParent);
        }
        else {
            parent.getChildren().add(student);
            parents.add(parent);
            student.setParent(parent);
        }
    }

    /**
     * Searches in the parents repo for a parent by email and return true, if he exixts.
     * @param email
     * @return
     */
    public boolean findParent(String email){
        return parents.getAll().stream() .anyMatch(pt -> Objects.equals(pt.getEmail(), email));
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

    public String generateInvoice(Integer parentID,String month){
        Parent parent = parents.get(parentID);
        String invoice="Invoice for the month " + month + "\nParent name: " + parent.getLastName() + " " + parent.getName() + "\n";
        double total = 0;
        for(Student student: parent.getChildren()){
            int presences=0;
            double individualTotal = 0;
            for(SessionDate sd: student.getSessionDateList().keySet()){
                if(student.getSessionDateList().get(sd) && sd.getDate().substring(3,5) == month){
                    presences++;
                }
            }
            individualTotal += presences*student.getSession().getPricePerSession();
            total+= individualTotal;
            invoice += "Student name: " + student.getLastName() + " " + student.getName() + "\n Total for student: " + individualTotal + "\n";
        }
        invoice += "Total: " + total +"\n";
        return invoice;
    }

    public void removeStudent(Integer studentID){
        Parent parent = students.get(studentID).getParent();
        if(parent.getChildren().size()>1){
            parent.getChildren().remove(students.get(studentID));
            parents.update(parent);
        }
        else{
            parents.remove(parent.getId());
        }
        Session session = students.get(studentID).getSession();
        session.getSessionStudents().remove( students.get(studentID));
        sessions.update(session);
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

    public void addStudentToSession(Integer idSession, Integer student){

        Session ss = sessions.get(idSession);
        Student st = students.get(student);

        ss.getSessionStudents().add(st);
        sessions.update(ss);

    }

    public Trainer getTrainerById(int trainerId){
        return trainers.get(trainerId);
    }

    public Session getSessionById(int sessionId){
        return sessions.get(sessionId);
    }

    public String viewAllStudents(){
        String allStudents="";
        for(Session s: sessions.getAll()){
            for(Student st: s.getSessionStudents()){
                allStudents += "Student with id " + st.getId() + " and name " + st.getLastName() + " " + st.getName() + " is at " + s.difficultyLevel + " level";
            }
        }
        return allStudents;
    }
    public String viewAllTrainers(){
        String allTrainers="";
        for(Trainer t: trainers.getAll()){
            allTrainers += "Trainer with id " + t.getId() + " and name " + t.getLastName() + " " + t.getName() + " has belt color " + t.getBeltLevel();
        }
        return allTrainers;
    }



}
