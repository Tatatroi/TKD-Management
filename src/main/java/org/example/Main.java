package org.example;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import org.example.Exceptions.DatabaseException;
import org.example.Ui.TKDUI;
import org.example.Controller.TKDController;
import org.example.Service.TKD_Service;
import org.example.Model.*;
import org.example.Repository.*;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    /**
     * Adds a student to a parent, session and adds the objects to the repos.
     * @param session       The session, where the student is added.
     * @param student       The session, where the student is added.
     * @param parent        The parent, where the student is added.
     * @param parentRepo    The parent repo, where the parent is added.
     * @param studentRepo   The student repo, where the parent is added.
     * @param sessionRepo   The session repo, where the parent is added.
     */
    public static void parentChild(Session session, Student student, Parent parent, DatabaseRepo<Parent> parentRepo, DatabaseRepo<Student> studentRepo, DatabaseRepo<Session> sessionRepo) throws SQLException, DatabaseException {
        //System.out.println(student);
        session.getSessionStudents().add(student.getId());
        sessionRepo.update(session);
        parent.getChildren().add(student.getId());
        student.setParent(parent.getId());

        if(parentRepo.get(parent.getId()) == null) {
            parentRepo.add(parent);
        }
        studentRepo.add(student);
        studentRepo.update(student);
        parentRepo.update(parent);
    }


    /**
     * The main method that initializes the application, starts the console interface and creates the in-memory repos and populates them with some intial values.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) throws SQLException {

        DatabaseRepo<Student> studentRepo = new DatabaseStudent("jdbc:sqlserver://localhost:1433;database=TKD Management;integratedSecurity=true;trustServerCertificate=true;");
        DatabaseRepo<Parent> parentRepo = new DatabaseParent("jdbc:sqlserver://localhost:1433;database=TKD Management;integratedSecurity=true;trustServerCertificate=true;");
        DatabaseRepo<Session> sessionRepo = new DatabaseSession("jdbc:sqlserver://localhost:1433;database=TKD Management;integratedSecurity=true;trustServerCertificate=true;");
        DatabaseRepo<Contest> contestRepo = new DatabaseContest("jdbc:sqlserver://localhost:1433;database=TKD Management;integratedSecurity=true;trustServerCertificate=true;");
        DatabaseRepo<Trainer> trainerRepo = new DatabaseTrainer("jdbc:sqlserver://localhost:1433;database=TKD Management;integratedSecurity=true;trustServerCertificate=true;");
        DatabaseRepo<BeltExam> beltExamRepo = new DatabaseBeltExam("jdbc:sqlserver://localhost:1433;database=TKD Management;integratedSecurity=true;trustServerCertificate=true;");
        DatabaseRepo<TrainingCamp> trainingCampRepo = new DatabaseTrainingCamp("jdbc:sqlserver://localhost:1433;database=TKD Management;integratedSecurity=true;trustServerCertificate=true;");



        
        
//        try {
//            DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=TKD Management;integratedSecurity=true;trustServerCertificate=true;");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        DatabaseRepo<Student> students2 = new DatabaseStudent("jdbc:sqlserver://localhost:1433;database=TKD Management;integratedSecurity=true;trustServerCertificate=true;");
//        students2.add(student1);
//        students2.update(student1);
//        System.out.println(students2.getAll());
        TKD_Service tkdService = new TKD_Service(studentRepo,trainerRepo,parentRepo,sessionRepo,contestRepo,trainingCampRepo,beltExamRepo);
        TKDController tkdController = new TKDController(tkdService);
        TKDUI newUi = new TKDUI(tkdController);
//        System.out.println(tkdService.filterStudentsByBelt("white"));
//        System.out.println(parentRepo.getAll());
//        System.out.println(studentRepo.get(1));
        newUi.start();


    }
}