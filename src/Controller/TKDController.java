package Controller;

import Model.*;
import Service.TKD_Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * A controller class that handles the business logic for the TKD-Management system.
 */
public class TKDController {
    private final TKD_Service tkdService;

    /**
     * Constructs a new UniversityController with the given UniversityService.
     *
     * @param tkdService The service that provides the business logic for the TKD-Management system.
     */
    public TKDController(TKD_Service tkdService) {
        this.tkdService = tkdService;
    }

    /**
     * Adds an object to repo.
     * @param obj           The object to be added.
     * @throws IOException  If object already exists.
     */
    public void addObject(Object obj) throws IOException {
        tkdService.addObject(obj);
        if(obj instanceof Student){
            System.out.println("Student" + ((Student) obj).getName() + " added with ID " + ((Student) obj).getId());
        }
        else if(obj instanceof Trainer){
            System.out.println("Trainer" + ((Trainer) obj).getName() + " added with ID " + ((Trainer) obj).getId());
        }
        else if(obj instanceof Parent){
            System.out.println("Parent" + ((Parent) obj).getName() + " added with ID " + ((Parent) obj).getId());
        }
        else if(obj instanceof Session){
            System.out.println("Session with ID " + ((Session) obj).id + " added with ID " + ((Session) obj).getId());
        }
        else if(obj instanceof BeltExam){
            System.out.println("Belt Exam with ID " + ((BeltExam) obj).getId() + " added");
        }
        else if(obj instanceof Contest){
            System.out.println("Contest with ID " + ((Contest) obj).getId() + " added");
        }
        else if(obj instanceof TrainingCamp){
            System.out.println("Training camp with ID " + ((TrainingCamp) obj).getId() + " added");
        }
    }

    /**
     * Deletes a student from the repo.
     * @param idStudent The unique identifier of the student.
     */
    public void deleteStudent(Integer idStudent){
        tkdService.removeStudent(idStudent);
        System.out.println("Student with ID " + idStudent + " deleted");
    }

    /**
     * Deletes a trainer from the repo.
     * @param idTrainer The unique identifier of the trainer.
     */
    public void deleteTrainer(Integer idTrainer){
        tkdService.removeTrainer(idTrainer);
        System.out.println("Trainer with ID " + idTrainer + " deleted");
    }

    /**
     * Deletes a parent from the repo.
     * @param idParent The unique identifier of the parent.
     */
    public void deleteParent(Integer idParent){
        tkdService.removeParent(idParent);
        System.out.println("Parent with ID " + idParent + " deleted");
    }

    /**
     * Deletes a session from the repo.
     * @param idSession The unique identifier of the session.
     */
    public void deleteSession(Integer idSession){
        tkdService.removeSession(idSession);
        System.out.println("Session with ID " + idSession + " deleted");
    }

    /**
     * Deletes a belt exam from the repo.
     * @param idBeltExam The unique identifier of the belt exam.
     */
    public void deleteBeltExam(Integer idBeltExam){
        tkdService.removeBeltExam(idBeltExam);
        System.out.println("Belt Exam with ID " + idBeltExam + " deleted");
    }

    /**
     * Deletes a contest from the repo.
     * @param idContest The unique identifier of the contest.
     */
    public void deleteContest(Integer idContest){
        tkdService.removeContest(idContest);
        System.out.println("Contest with ID " + idContest + " deleted");
    }

    /**
     * Deletes a training camp from the repo.
     * @param idTrainingCamp The unique identifier of the training camp.
     */
    public void deleteTrainingCamp(Integer idTrainingCamp){
        tkdService.removeTrainingCamp(idTrainingCamp);
        System.out.println("Training camp with ID " + idTrainingCamp + " deleted");
    }

    /**
     * Changes the session of the trainer
     * @param idTrainer The unique identifier of the trainer.
     * @param idSession The unique identifier of the session.
     */
    public void assignSessionToTrainer(Integer idSession, Integer idTrainer){
        tkdService.assignGroupToTrainer(idTrainer, idSession);
        System.out.println("Trainer with ID " + idTrainer + " assigned to session with ID " + idSession);
    }

    /**
     * Changes the session of the student.
     * @param idStudent The unique identifier of the student.
     * @param idSession The unique identifier of the session.
     */
    public void changeStudentSession(Integer idStudent, Integer idSession){
        tkdService.changeStudentGroup(idStudent, idSession);
        System.out.println("Student with ID " + idStudent + " changed to session with ID " + idSession);
    }

//    public void changeBeltLevel(Integer idBeltLevel){
//        tkdService.changeBeltlevel(idBeltLevel);
//        System.out.println("Belt Level with ID " + idBeltLevel + " changed");
//    }

    /**
     * Counts the number of attendances and absences of a student.
     * @param idStudent The unique identifier of the student.
     */
    public void numberOfAttendances(Integer idStudent){
        Map<String,Integer> attendencesAndAbsences = tkdService.numberOfAttendencesAndAbsences(idStudent);
        System.out.println("Student with id " + idStudent + " has " + attendencesAndAbsences.get("Attendences") + " attendances "+
                attendencesAndAbsences.get("Absences") + " absences");
    }

    /**
     * Adds a student to a belt exam.
     * @param idBeltExam    The unique identifier of the belt exam.
     * @param idStudent     The unique identifier of the student.
     */
    public void addStudentToBeltExam(Integer idBeltExam, Integer idStudent){
        tkdService.addStudentToBeltExam(idStudent, idBeltExam);
        System.out.println("Student with id " + idStudent + " added to Belt Exam with ID " + idBeltExam);
    }

    /**
     * Adds a result to a belt exam.
     * @param idBeltExam    The unique identifier of the belt exam.
     * @param idStudent     The unique identifier of the student.
     * @param result        The result which is true(passed) or false(failed).
     */
    public void addResultToBeltExam(Integer idBeltExam, Integer idStudent, Boolean result){
        tkdService.addResultBeltExam(idStudent, idBeltExam, result);
        if(result) {
            System.out.println("Student with id " + idStudent + " has promoted Belt Exam with ID " + idBeltExam);
        }else{
            System.out.println("Student with id " + idStudent + " has not promoted Belt Exam with ID " + idBeltExam);
        }
    }

    /**
     * Adds an attendance to a student.
     * @param idStudent     The unique identifier of the student.
     * @param idSession     The unique identifier of the belt exam.
     * @param attendance    The attendance which can be true(present) or false(absent).
     * @param weekDay       The day of the week of the attendance.
     * @param date          The date of the session.
     */
    public void addAttendance(Integer idStudent,Integer idSession, Boolean attendance, String weekDay, String date){
        tkdService.addAttendance(idStudent, idSession, attendance, weekDay, date);
        if(attendance) {
            System.out.println("Student with id " + idStudent + " has attended the session with ID " + idSession + " on " + weekDay +" from "+date);
        }
        else{
            System.out.println("Student with id " + idStudent + " has not attended the session with ID " + idSession + " on " + weekDay +" from "+date);
        }
    }

    /**
     * Adds a student to a contest.
     * @param idStudent     The unique identifier of the student.
     * @param idContest    The unique identifier of the contest.
     */
    public void addStudentToContest(Integer idStudent, Integer idContest){
        tkdService.addStudentToContest(idStudent, idContest);
        System.out.println("Student with id " + idStudent + " has been added to contest with id " + idContest);
    }

    /**
     * Adds a student to a training camp.
     * @param idStudent         The unique identifier of the student.
     * @param idTrainingCamp    The unique identifier of the training camp.
     */
    public void addStudentToTrainingCamp(Integer idStudent, Integer idTrainingCamp){
        tkdService.addStudentToTraining(idStudent, idTrainingCamp);
        System.out.println("Student with id " + idStudent + " has been added to training camp with id " + idTrainingCamp);
    }

    /**
     * Displays all students from the session.
     */
    public void viewStudents(){
        System.out.println("Here are all students with id, name and level: \n" + tkdService.viewAllStudents());
//        System.out.println();
    }

    /**
     * Displays all trainers from the session.
     */
    public void viewTrainers(){
        System.out.println("Here are all trainers with id, name, belt color and level: ");
        System.out.println(tkdService.viewAllTrainers());
    }

    /**
     * Displays all contests.
     */
    public void viewContests(){
        System.out.println("Here are all contests with id, name, start date, end date, price and list of students: ");
        System.out.println(tkdService.viewAllContests());
    }

    /**
     * Displays all training camps.
     */
    public void viewTrainingCamps(){
        System.out.println("Here are all training camps with id, start date, end date, price, max number of participants and list of students: ");
        System.out.println(tkdService.viewTrainingCamps());
    }

    /**
     * Displays all belt exams.
     */
    public void viewBeltExams(){
        System.out.println("Here are all belt exams with id, start date, end date, price, belt color and list of students: ");
        System.out.println(tkdService.viewBeltExams());
    }

    /**
     * Displays all parents.
     */
    public void viewParents(){
        System.out.println("Here are parents with id, name and their children: ");
        System.out.println(tkdService.viewAllParents());
    }

    /**
     * Adds a student to a parent.
     * @param student   The student to be added.
     * @param parent    The parent to be added.
     */
    public void addStudentToParent(Student student, Parent parent){
        tkdService.addStudentToParent(student,parent);
    }

    /**
     * Generates an invoice for a month for a parent for all its children.
     * @param parentID  The unique identifier of a parent.
     * @param month     The month for the invoice.
     */
    public void generateInvoice(Integer parentID,String month){
        System.out.println(tkdService.generateInvoice(parentID,month));
    }

    /**
     * Adds a student to a session.
     * @param idStudent   The unique identifier of a student.
     * @param idSession   The unique identifier of a session.
     */
    public void addStudentToSession(Integer idSession, Integer idStudent){
        tkdService.addStudentToSession(idSession, idStudent);
        System.out.println("Student with id " + idStudent + " has been added to session with id " + idSession);
    }

    /**
     * Gets a trainer by id.
     * @param idTrainer The unique identifier of a trainer.
     * @return  The trainer.
     */
    public Trainer getTrainerById(Integer idTrainer){
        return tkdService.getTrainerById(idTrainer);
    }

    /**
     * Gets a trainer by id.
     * @param idSession The unique identifier of a session.
     * @return  The session.
     */
    public Session getSessionById(Integer idSession){
        return tkdService.getSessionById(idSession);
    }

    /**
     * Shows every combination of events that a student can attend to for a given amount of money.
     * @param amountOfMoney The maximum value of a combination.
     */
    public void eventsThatdontExceedAmountOfMoney(double amountOfMoney){
        List<List<Integer>> results = tkdService.eventsThatdontExceedAmountOfMoney(amountOfMoney);
        System.out.println("Events combinations that don t exceed the sum : " + amountOfMoney);
        for (List<Integer> combination : results) {
            System.out.println("ID-urile evenimentelor: ");
            for(Integer c : combination) {
                if(c > 100 && c < 200){ // id for Contests
                    Contest cnt = tkdService.getContestById(c);
                    System.out.println(cnt.toString2());
                }
                else if(c > 10 && c < 100){
                    TrainingCamp tc = tkdService.getTrainingCampByIs(c);
                    System.out.println(tc.toString2());
                }
            }
            System.out.println('\n');
        }
    }


    /**
     * prints a sorted list of contests based on their start date
     */
    public void sortedContests(){
        for(Contest c1: tkdService.sortContestsByDates()){
            System.out.println(c1.toString2());
            System.out.println('\n');
        }
    }

    /**
     * prints a sorted list of trainingCamps based on their starting date
     */
    public void sortedCampsByDates(){
        for(TrainingCamp tc: tkdService.sortTrainingCampsByDates()){
            System.out.println(tc.toString2());
            System.out.println('\n');
        }
    }

    /**
     * prints a sorted list of BeltExamns based on their starting dates
     */
    public void sortBeltExamnsByDates(){
        for(BeltExam bt: tkdService.sortBeltExamnsByDates()){
            System.out.println(bt.toString2());
            System.out.println('\n');
        }
    }

    /**
     * prints a sorted list of students based on their number of attendances
     */
    public void sortedStudentsByAttend(){
        for(Student s1 : tkdService.sortStudentsByNumberOfAttendences()){
            System.out.println(s1.toString2());
            System.out.println('\n');
        }
    }

    /**
     * prints a filtered list containing just students with the specified belt level
     * @param beltLevel The belt level that is used for filtering the students
     */
    public void filteredStudentsByBeltLevel(String beltLevel){
        for(Student s1 : tkdService.filterStudentsByBelt(beltLevel)){
            System.out.println(s1.toString2());
            System.out.println('\n');
        }
    }


    /**
     * prints a filtred list of parents that have a specific number of children
     * @param noOfChildren number of children
     */
    public void filterParentsByNumberOfChildren(Integer noOfChildren){
        if(tkdService.filterParentsNumberOfChildren(noOfChildren).size() == 0){
            System.out.println("No parent with " + noOfChildren + " children");
        }
        for(Parent p1: tkdService.filterParentsNumberOfChildren(noOfChildren)){
            System.out.println(p1.toString2());
            System.out.println('\n');
        }
    }

    /**
     * prints a list of students ordered alphabetical
     */
    public void sortStudentsAlphabetical(){
        List<Student> students = tkdService.sortStudentsAlphabetical();
        for(Student s : students){
            System.out.println(s.toString2());
        }
    }

    /**
     * prints a list of sessions ordered by number of participants
     */
    public void sortSessionByNumberOfParticipants(){
        List<Session> sessions = tkdService.sortSessionByNumberOfParticipants();
        for(Session s : sessions){
            System.out.println(s.toString2());
        }
    }

    /**
     * Gets for a given session the date with the biggest profit made and the amount made and prints the outputs.
     * @param sessionId     The unique identifier of the session.
     */
    public void getDateWithMostStudentsForSession(int sessionId){
        String date = tkdService.getMostProfitableDateForSession(sessionId).getKey();
        double max = tkdService.getMostProfitableDateForSession(sessionId).getValue();
        System.out.println("The date with most students at the session with id " + sessionId + " is " + date + " and made " + max + " RON.");
    }

}
