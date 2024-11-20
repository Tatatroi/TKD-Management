package Service;

import Model.*;
import Repository.InMemoryRepo;
import Repository.InMemoryRepo;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A service class that provides the business logic for the TKD-Management system.
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
        ss.trainer=tr.getId();
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
        Session old_ss = sessions.get(st.getSession());

        old_ss.getSessionStudents().remove(st.getId());
        new_ss.getSessionStudents().add(st.getId());
        st.setSession(new_ss.id);
        students.update(st);
        sessions.update(new_ss);
        sessions.update(old_ss);
    }

    /**
     * Changes the belt color of a student if he passed the exam.
     * @param beltExamID  The unique identifier of a belt exam.
     */
    public void changeBeltlevel(Integer beltExamID){
        for(int stId: beltExams.get(beltExamID).getListOfResults().keySet()){
            if(beltExams.get(beltExamID).getListOfResults().get(stId)==1){
                students.get(stId).setBeltLevel(beltExams.get(beltExamID).getBeltColor());
                students.update(students.get(stId));
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
        for(SessionDate sd: st.getSessionDateList()){
            if(sd.isAttended()){
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

    /**
     * this is a backtrcking function that find evry possible combination between Contests and TrainingCamps
     * @param eventPairs a list with information that holds all Contest/TrainingCamp and their prices
     * @param remainingAmount the amount of money after one Contest/TrainingCamp has been chosen
     * @param start starting point where the function start to search
     * @param currentCombination the combination on every search
     * @param results <- this is a parameter where all combinations will be found
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


    /**
     * it takes the amount of money given by controller and returns a list with every combination
     * @param amountOfMoney the range that it's forbidden to be exceeded
     * @return a list of lists with all possible combinations
     */
    public List<List<Integer>> eventsThatdontExceedAmountOfMoney(double amountOfMoney){
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

        return results;

    }

    /**
     * Adds a student to a belt exam.
     * @param idStudent     The unique identifier of a student.
     * @param idBeltExam    The unique identifier of a belt exam.
     */
    public void addStudentToBeltExam(int idStudent, int idBeltExam){
        Student s = students.get(idStudent);
        BeltExam belt = beltExams.get(idBeltExam);
        belt.getListOfResults().put(s.getId(),-1);
        beltExams.update(belt);
    }

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
            belt.getListOfResults().put(s.getId(),1); // promoted
            changeBeltlevel(idBeltExam);
        }
        else{
            belt.getListOfResults().put(s.getId(),0); // failed
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
        SessionDate sessionDate = new SessionDate(weekday,date,sessionId,attendance);
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
        ct.getStudents().add(st.getId());
        st.getContestList().add(ct.getId());
        contests.update(ct);
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
        tc.getStudents().add(st.getId());
        st.getTrainingCampList().add(tc.getId());
        trainingCamps.update(tc);
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
            updateParent.getChildren().add(student.getId());
            parents.update(updateParent);
            student.setParent(updateParent.getId());
        }
        else {
            parent.getChildren().add(student.getId());
            parents.add(parent);
            student.setParent(parent.getId());
        }
    }

    /**
     * Searches in the parents repo for a parent by email and return true, if he exixts.
     * @param email the unique email of a Parent
     * @return true/false if parent found
     */
    public boolean findParent(String email){
        return parents.getAll().stream() .anyMatch(pt -> Objects.equals(pt.getEmail(), email));
    }

    /**
     * this function adds an object in their repo based on their type
     * @param o represent the given object
     * @throws IOException
     */
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


    /**
     * make an Invoice for every parent based on the month
     * @param parentID the ID of the parent
     * @param month the month they want an invoice
     * @return a string that holds information an invoice need to have (for every child they have)
     */

    public String generateInvoice(Integer parentID,String month){
        Parent parent = parents.get(parentID);
        String invoice="Invoice for the month " + month + "\nParent name: " + parent.getLastName() + " " + parent.getName() + "\n";
        double total = 0;
        for(int studentId: parent.getChildren()){
            Student student = students.get(studentId);
            int presences=0;
            double individualTotal = 0;
            for(SessionDate sd: student.getSessionDateList()){
                if(sd.isAttended() && sd.getDate().substring(3, 5).equals(month)){
                    presences++;
                }
            }
            individualTotal += presences*(sessions.get(student.getSession())).getPricePerSession();
            total+= individualTotal;
            invoice += "Student name: " + student.getLastName() + " " + student.getName() + "\n Total for student: " + individualTotal + "\n";
        }
        invoice += "Total: " + total +"\n";
        return invoice;
    }

    /**
     * it deletes a student based on their ID
     * @param studentID the id of the student
     */
    public void removeStudent(Integer studentID){
        Parent parent = parents.get(students.get(studentID).getParent());
        if(parent.getChildren().size()>1){
            parent.getChildren().remove(studentID);
            parents.update(parent);
        }
        else{
            parents.remove(parent.getId());
        }
        Session session = sessions.get(students.get(studentID).getSession());
        session.getSessionStudents().remove( studentID);
        sessions.update(session);
        students.remove(studentID);
    }

    /**
     * it deletes a trainer based on their ID
     * @param trainerID the id of the trainer
     */
    public void removeTrainer(Integer trainerID){
        trainers.remove(trainerID);
    }

    /**
     * it deletes a parent based on their ID
     * @param parentID the id of the Parent
     */
    public void removeParent(Integer parentID){
        parents.remove(parentID);
    }

    /**
     * it deletes a session based on their ID
     * @param sessionID the id of the session
     */
    public void removeSession(Integer sessionID){
        sessions.remove(sessionID);
    }

    /**
     * it deletes a BeltExam based on their ID
     * @param beltExamID the id of the BeltExam
     */
    public void removeBeltExam(Integer beltExamID){
        beltExams.remove(beltExamID);
    }

    /**
     * it deletes a Contest based on their ID
     * @param contestID the id of the contest
     */
    public void removeContest(Integer contestID){
        contests.remove(contestID);
    }

    /**
     * it deletes a training camp based on their ID
     * @param trainingCampID the id of the trainingCamp
     */
    public void removeTrainingCamp(Integer trainingCampID){
        trainingCamps.remove(trainingCampID);
    }


    /**
     * assign a student to a session
     * @param idSession the id of the session
     * @param student the id of the Student
     */
    public void addStudentToSession(Integer idSession, Integer student){

        Session ss = sessions.get(idSession);
        Student st = students.get(student);

        ss.getSessionStudents().add(st.getId());
        sessions.update(ss);

    }

    /**
     * get a Trainer based on their id
     * @param trainerId the trainer id
     * @return object of type Trainer
     */
    public Trainer getTrainerById(int trainerId){
        return trainers.get(trainerId);
    }

    /**
     * get a Contest based on their id
     * @param idContes the contest id
     * @return an object of type Contest
     */
    public Contest getContestById(int idContes){
        return contests.get(idContes);
    }

    /**
     * get a Training Camp based on their id
     * @param idTrainingCamp the Training Camp id
     * @return an object of type Training Camp
     */
    public TrainingCamp getTrainingCampByIs(int idTrainingCamp){
        return trainingCamps.get(idTrainingCamp);
    }

    /**
     * get a session based on their id
     * @param sessionId the session id
     * @return an object of type Session
     */
    public Session getSessionById(int sessionId){
        return sessions.get(sessionId);
    }

    /** display all Students
     *
     * @return a String that holds all students
     */
    public String viewAllStudents(){
        StringBuilder allStudents= new StringBuilder();
        for(Session s: sessions.getAll()){
            for(int stId: s.getSessionStudents()){
                allStudents.append(students.get(stId).toString2());
            }
            allStudents.append('\n');
        }
        return allStudents.toString();
    }

    /**
     * display all Trainers
     * @return a String that holds all trainers
     */
    public String viewAllTrainers(){
        StringBuilder allTrainers= new StringBuilder();
        for(Trainer t: trainers.getAll()){
            allTrainers.append(t.toString2()).append('\n');
        }
        return allTrainers.toString();
    }

    /**
     * dispaly all Parents
     * @return a String that holds all Parents
     */
//    public String viewAllParents(){
//        StringBuilder allParents= new StringBuilder();
//        for(Parent p: parents.getAll()){
//            allParents.append(" Parent with id: ").append(p.getId()).append(", name ").append(p.getName()).append(" ").append(p.getLastName()).append(" has childrens: " );
//            for(Student s: p.getChildren()){
//                allParents.append("\n").append("Student with id: ").append(s.getId()).append(" ").append(s.getLastName()).append(s.getName()).append(" ").append(s.getLastName());
//            }
//            allParents.append("\n");
//        }
//        return allParents.toString();
//    }
    public String viewAllParents() {
        StringBuilder allParents = new StringBuilder();
        // Coduri ANSI pentru culori
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RESET = "\u001B[0m";

        for (Parent p : parents.getAll()) {
            allParents.append(ANSI_RED).append("Parent").append(ANSI_RESET)
                    .append(" with id: ").append(p.getId())
                    .append(", name ").append(p.getName()).append(" ").append(p.getLastName())
                    .append(" has childrens: ");
            for (int sId : p.getChildren()) {
                Student s = students.get(sId);
                allParents.append("\n")
                        .append(ANSI_GREEN).append("Student").append(ANSI_RESET)
                        .append(" with id: ").append(s.getId())
                        .append(" ").append(s.getLastName()).append(" ").append(s.getName());
            }
            allParents.append("\n");
        }
        return allParents.toString();
    }


    /**
     * display all contests
     * @return a string that holds all contests
     */
//    public String viewAllContests(){
//        StringBuilder allContests = new StringBuilder();
//        for(Contest c: contests.getAll()){
//            allContests.append("Contest with id ").append(c.getId()).append(", name ").append(c.getName()).append(", start date ").append(c.startDate).
//                    append(", end date ").append(c.endDate).append(", price ").append(c.price).append(" and students: ");
//            for(Student s: c.getStudents()){
//                allContests.append("   Name ").append(s.getLastName()).append(" ").append(s.getName()).append(" and belt level: ").append(s.getBeltLevel()).append('\n');
//            }
//            allContests.append('\n');
//        }
//        return allContests.toString();
//    }
    public String viewAllContests() {
        StringBuilder allContests = new StringBuilder();

        // Coduri ANSI pentru culori
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_ORANGE = "\u001B[38;5;214m"; // Portocaliu
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_RESET = "\u001B[0m";

        for (Contest c : contests.getAll()) {
            allContests.append(c.toString()).append('\n');

            for (int sId : c.getStudents()) {
                allContests.append(students.get(sId).toString3()).append('\n');
            }

            allContests.append('\n');
        }

        return allContests.toString();
    }

    /**
     * display all Training Camps
     * @return a String that holds all Training Camps
     */
    public String viewTrainingCamps(){
        StringBuilder allTrainingCamps = new StringBuilder();
        for(TrainingCamp t: trainingCamps.getAll()){
            allTrainingCamps.append(t.toString2()).append('\n');
            for(int sId: t.getStudents()){
                allTrainingCamps.append(students.get(sId).toString2()).append('\n');
            }
            allTrainingCamps.append('\n');
        }
        return allTrainingCamps.toString();
    }

    /**
     * display all BeltExams
     * @return a string that holds all BeltExams
     */
    public String viewBeltExams() {
        StringBuilder allBeltExams = new StringBuilder();
        for (BeltExam b : beltExams.getAll()) {
            allBeltExams.append(b.toString2()).append('\n');
            for (int sId : b.getListOfResults().keySet()) {
                allBeltExams.append(students.get(sId).toString2()).append('\n');
            }
            allBeltExams.append('\n');
        }
        return allBeltExams.toString();
    }


    /**
     * Sorts the contests based on their starting date.
     * @return a sorted List of Contests based on ther starting dates
     */
    public List<Contest> sortContestsByDates(){
        List<Contest> sorted = new ArrayList<>(contests.getAll());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        sorted.sort((c1, c2) -> {
            LocalDate date1 = LocalDate.parse(c1.startDate, formatter);
            LocalDate date2 = LocalDate.parse(c2.startDate, formatter);
            return date1.compareTo(date2);
        });
        return sorted;
    }

    /**
     *  Sorts the belt exams based on their starting date.
     *  @return a sorted List of Belt Exams based on their starting dates
     */

    public List<BeltExam> sortBeltExamnsByDates(){
        List<BeltExam> sorted = new ArrayList<>(beltExams.getAll());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        sorted.sort((c1, c2) -> {
            LocalDate date1 = LocalDate.parse(c1.startDate, formatter);
            LocalDate date2 = LocalDate.parse(c2.startDate, formatter);
            return date1.compareTo(date2);
        });
        return sorted;
    }

    /**
     * Sorts the training camps based on their starting dates.
     * @return a sorted list of training Camps based on their starting date
     */
    public List<TrainingCamp> sortTrainingCampsByDates(){
        List<TrainingCamp> sorted = new ArrayList<>(trainingCamps.getAll());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        sorted.sort((c1, c2) -> {
            LocalDate date1 = LocalDate.parse(c1.startDate, formatter);
            LocalDate date2 = LocalDate.parse(c2.startDate, formatter);
            return date1.compareTo(date2);
        });
        return sorted;
    }

    /**
     * Sorts the sessions based on their number of participants.
     * @return a list of Session sorted based on their number of participants
     */
    public List<Session> sortSessionByNumberOfParticipants(){
        List<Session> sorted = new ArrayList<>(sessions.getAll());
        sorted.sort(Comparator.comparingInt(c -> c.getSessionStudents().size()));
        return sorted;
    }

    /**
     * Sorts the students based on their name.
     * @return a list of students ordered alphabetical
     */
    public List<Student> sortStudentsAlphabetical(){
        List<Student> sorted = new ArrayList<>(students.getAll());
        sorted.sort(Comparator.comparing(s -> s.name));
        return sorted;
    }

    /**
     * Sorts the students based on their number of attendances.
     * @return a list of Students ordered by number of Attendances
     */
    public List<Student> sortStudentsByNumberOfAttendences(){
        List<Student> sorted = new ArrayList<>(students.getAll());
        sorted.sort((s1, s2) -> Integer.compare(numberOfAttendencesAndAbsences(s1.getId()).get("Attendences"), numberOfAttendencesAndAbsences(s2.getId()).get("Attendences")));
        return sorted;
    }

    /**
     * Filters the students based on a belt level
     * @param   beltLevel The belt level upon which the filter will apply
     * @return  List of all students having the belt level specified
     */
    public List<Student> filterStudentsByBelt(String beltLevel){
        List<Student> filtered = new ArrayList<>(students.getAll());
        filtered = filtered.stream().filter((s1)->s1.getBeltLevel().equals(beltLevel)).toList();
        return filtered;
    }

    /**
     * Filters parents based of their number of children
     * @param noOfChildren number of children we want to filter
     * @return a list of parents that have noOfChildren as number of children
     */
    public List<Parent> filterParentsNumberOfChildren(int noOfChildren) {
        return parents.getAll().stream()  // Obținem stream-ul de la lista de părinți
                .filter(p -> p.getChildren().size() == noOfChildren)  // Filtrăm părinții care au exact numărul de copii dorit
                .collect(Collectors.toList());  // Colectăm rezultatele într-o listă
    }

    /**
     * Gets for a given session the date with the highest attendance and number of participants. It searches through each student's
     * session date list to find the most attended date for all students.
     * @param sessionId     The unique identifier of the session.
     * @return              A simple entry containing the most attended date and the number of students.
     */
    public AbstractMap.SimpleEntry<String, Integer> getDateWithMostStudentsForSession(int sessionId){
        Session session = sessions.get(sessionId);
        Map<String,Integer> freqWeekdays = new HashMap<>();
        for(Student st: students.getAll()){
            if(st.session == sessionId){
                for(SessionDate sd: st.getSessionDateList()) {
                    if(sd.isAttended()) {
                        if (freqWeekdays.containsKey(sd.getDate())){
                            freqWeekdays.put(sd.getDate(),freqWeekdays.get(sd.getDate())+1);
                        }
                        else{
                            freqWeekdays.put(sd.getDate(),1);
                        }
                    }
                }
            }
        }
        int max = 0;
        String date = "";
        for(String d: freqWeekdays.keySet()){
            if(freqWeekdays.get(d)>max){
                max = freqWeekdays.get(d);
                date = d;
            }
        }
        return new AbstractMap.SimpleEntry<String,Integer>(date,max);
    }

}

