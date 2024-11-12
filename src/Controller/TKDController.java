package Controller;

import Model.*;
import Service.TKD_Service;

import java.io.IOException;
import java.util.Map;

public class TKDController {
    private final TKD_Service tkdService;

    public TKDController(TKD_Service tkdService) {
        this.tkdService = tkdService;
    }

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


    public void deleteStudent(Integer idStudent){
        tkdService.removeStudent(idStudent);
        System.out.println("Student with ID " + idStudent + " deleted");
    }

    public void deleteTrainer(Integer idTrainer){
        tkdService.removeTrainer(idTrainer);
        System.out.println("Trainer with ID " + idTrainer + " deleted");
    }

    public void deleteParent(Integer idParent){
        tkdService.removeParent(idParent);
        System.out.println("Parent with ID " + idParent + " deleted");
    }

    public void deleteSession(Integer idSession){
        tkdService.removeSession(idSession);
        System.out.println("Session with ID " + idSession + " deleted");
    }

    public void deleteBeltExam(Integer idBeltExam){
        tkdService.removeBeltExam(idBeltExam);
        System.out.println("Belt Exam with ID " + idBeltExam + " deleted");
    }

    public void deleteContest(Integer idContest){
        tkdService.removeContest(idContest);
        System.out.println("Contest with ID " + idContest + " deleted");
    }

    public void deleteTrainingCamp(Integer idTrainingCamp){
        tkdService.removeTrainingCamp(idTrainingCamp);
        System.out.println("Training camp with ID " + idTrainingCamp + " deleted");
    }

    public void assignSessionToTrainer(Integer idSession, Integer idTrainer){
        tkdService.assignGroupToTrainer(idTrainer, idSession);
        System.out.println("Trainer with ID " + idTrainer + " assigned to session with ID " + idSession);
    }

    public void changeStudentSession(Integer idStudent, Integer idSession){
        tkdService.changeStudentGroup(idStudent, idSession);
        System.out.println("Student with ID " + idStudent + " changed to session with ID " + idSession);
    }

    public void changeBeltLevel(Integer idBeltLevel){
        tkdService.changeBeltlevel(idBeltLevel);
        System.out.println("Belt Level with ID " + idBeltLevel + " changed");
    }

    public void numberOfAttendances(Integer idStudent){
        Map<String,Integer> attendencesAndAbsences = tkdService.numberOfAttendencesAndAbsences(idStudent);
        System.out.println("Student with id " + idStudent + " has " + attendencesAndAbsences.get("Attendences") + " attendances "+
                attendencesAndAbsences.get("Absences") + " absences");
    }

    public void addStudentToBeltExam(Integer idBeltExam, Integer idStudent){
        tkdService.addStudentToBeltExam(idStudent, idBeltExam);
        System.out.println("Student with id " + idStudent + " added to Belt Exam with ID " + idBeltExam);
    }

    public void addResultToBeltExam(Integer idBeltExam, Integer idStudent, Boolean result){
        tkdService.addResultBeltExam(idStudent, idBeltExam, result);
        if(result) {
            System.out.println("Student with id " + idStudent + " has promoted Belt Exam with ID " + idBeltExam);
        }else{
            System.out.println("Student with id " + idStudent + " has not promoted Belt Exam with ID " + idBeltExam);
        }
    }

    public void addAttendance(Integer idStudent,Integer idSession, Boolean attendance, String weekDay, String date){
        tkdService.addAttendance(idStudent, idSession, attendance, weekDay, date);
        if(attendance) {
            System.out.println("Student with id " + idStudent + " has attended the session with ID " + idSession + " on " + weekDay +" from "+date);
        }
        else{
            System.out.println("Student with id " + idStudent + " has not attended the session with ID " + idSession + " on " + weekDay +" from "+date);
        }
    }

    public void addStudentToContest(Integer idStudent, Integer idContest){
        tkdService.addStudentToContest(idStudent, idContest);
        System.out.println("Student with id " + idStudent + " has been added to contest with id " + idContest);
    }

    public void addStudentToTrainingCamp(Integer idStudent, Integer idTrainingCamp){
        tkdService.addStudentToTraining(idStudent, idTrainingCamp);
        System.out.println("Student with id " + idStudent + " has been added to training camp with id " + idTrainingCamp);
    }

    public void viewStudents(){
        System.out.println("Here are all students with id, name and level: \n" + tkdService.viewAllStudents());
//        System.out.println();
    }

    public void viewTrainers(){
        System.out.println("Here are all trainers with id, name, belt color and level: ");
        System.out.println(tkdService.viewAllTrainers());
    }

    public void viewContests(){
        System.out.println("Here are all contests with id, name, start date, end date, price and list of students: ");
        System.out.println(tkdService.viewAllContests());
    }

    public void viewTrainingCamps(){
        System.out.println("Here are all training camps with id, start date, end date, price, max number of participants and list of students: ");
        System.out.println(tkdService.viewTrainingCamps());
    }

    public void viewBeltExams(){
        System.out.println("Here are all belt exams with id, start date, end date, price, belt color and list of students: ");
        System.out.println(tkdService.viewBeltExams());
    }

    public void addStudentToParent(Student student, Parent parent){
        tkdService.addStudentToParent(student,parent);
    }

    public void generateInvoice(Integer parentID,String month){
        System.out.println(tkdService.generateInvoice(parentID,month));
    }

    public void addStudentToSession(Integer idSession, Integer idStudent){
        tkdService.addStudentToSession(idSession, idStudent);
        System.out.println("Student with id " + idStudent + " has been added to session with id " + idSession);
    }

    public Trainer getTrainerById(Integer idTrainer){
        return tkdService.getTrainerById(idTrainer);
    }

    public Session getSessionById(Integer idSession){
        return tkdService.getSessionById(idSession);
    }



}
