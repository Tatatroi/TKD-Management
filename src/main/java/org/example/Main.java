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
    public static void parentChild(Session session, Student student, Parent parent, IRepo<Parent> parentRepo, IRepo<Student> studentRepo, IRepo<Session> sessionRepo) throws SQLException {
        //System.out.println(student);
        session.getSessionStudents().add(student.getId());
        try {
            sessionRepo.update(session);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        parent.getChildren().add(student.getId());
        student.setParent(parent.getId());

        try {
            if(parentRepo.get(parent.getId()) == null) {
                parentRepo.add(parent);
            }
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        try {
            studentRepo.add(student);
            studentRepo.update(student);
            parentRepo.update(parent);
        }
        catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * The main method that initializes the application, starts the console interface and creates the in-memory repos and populates them with some intial values.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) throws SQLException, DatabaseException {

//        DatabaseRepo<Student> studentRepo = new DatabaseStudent("jdbc:sqlserver://localhost:1433;database=TKD-Management;integratedSecurity=true;trustServerCertificate=true;");
//        DatabaseRepo<Parent> parentRepo = new DatabaseParent("jdbc:sqlserver://localhost:1433;database=TKD-Management;integratedSecurity=true;trustServerCertificate=true;");
//        DatabaseRepo<Session> sessionRepo = new DatabaseSession("jdbc:sqlserver://localhost:1433;database=TKD-Management;integratedSecurity=true;trustServerCertificate=true;");
//        DatabaseRepo<Contest> contestRepo = new DatabaseContest("jdbc:sqlserver://localhost:1433;database=TKD-Management;integratedSecurity=true;trustServerCertificate=true;");
//        DatabaseRepo<Trainer> trainerRepo = new DatabaseTrainer("jdbc:sqlserver://localhost:1433;database=TKD-Management;integratedSecurity=true;trustServerCertificate=true;");
//        DatabaseRepo<BeltExam> beltExamRepo = new DatabaseBeltExam("jdbc:sqlserver://localhost:1433;database=TKD-Management;integratedSecurity=true;trustServerCertificate=true;");
//        DatabaseRepo<TrainingCamp> trainingCampRepo = new DatabaseTrainingCamp("jdbc:sqlserver://localhost:1433;database=TKD-Management;integratedSecurity=true;trustServerCertificate=true;");
        InFileRepo<Student> studentRepo = new InFileRepo<>("C:\\Users\\raulm\\Documents\\Sem 3\\TKD-Management\\src\\main\\java\\org\\example\\Data\\students.csv",Student::fromCSV);
        InFileRepo<Parent> parentRepo = new InFileRepo<>("C:\\Users\\raulm\\Documents\\Sem 3\\TKD-Management\\src\\main\\java\\org\\example\\Data\\parents.csv",Parent::fromCSV);
        InFileRepo<Session> sessionRepo = new InFileRepo<>("C:\\Users\\raulm\\Documents\\Sem 3\\TKD-Management\\src\\main\\java\\org\\example\\Data\\sessions.csv",Session::fromCSV);
        InFileRepo<Trainer> trainerRepo = new InFileRepo<>("C:\\Users\\raulm\\Documents\\Sem 3\\TKD-Management\\src\\main\\java\\org\\example\\Data\\trainers.csv",Trainer::fromCSV);
        InFileRepo<Contest> contestRepo = new InFileRepo<>("C:\\Users\\raulm\\Documents\\Sem 3\\TKD-Management\\src\\main\\java\\org\\example\\Data\\contests.csv",Contest::fromCSV);
        InFileRepo<TrainingCamp> trainingCampRepo = new InFileRepo<>("C:\\Users\\raulm\\Documents\\Sem 3\\TKD-Management\\src\\main\\java\\org\\example\\Data\\trainingCamps.csv",TrainingCamp::fromCSV);
        InFileRepo<BeltExam> beltExamRepo = new InFileRepo<>("C:\\Users\\raulm\\Documents\\Sem 3\\TKD-Management\\src\\main\\java\\org\\example\\Data\\beltExams.csv",BeltExam::fromCSV);

        Trainer t1 = new Trainer(1,"Mitroi","Stefan","srefanmitroi@gmail.com","Calea Floresti nr 58B",2004,"0761969675","black");
        trainerRepo.add(t1);
//
        Trainer t2 = new Trainer(2, "Popescu", "Andrei", "andrei.popescu@gmail.com", "Strada Mihai Viteazu nr 15", 1990, "0755123456", "red");
        trainerRepo.add(t2);
//
        Trainer t3 = new Trainer(3, "Ionescu", "Maria", "maria.ionescu@yahoo.com", "Bulevardul Eroilor nr 45", 1985, "0725987654", "blue");
        trainerRepo.add(t3);
//
        Trainer t4 = new Trainer(4, "Vasilescu", "Radu", "radu.vasilescu@outlook.com", "Strada Zorilor nr 23", 1992, "0744123456", "green");
        trainerRepo.add(t4);
//        InMemoryRepo<Trainer> trainerInMemoryRepo = new InMemoryRepo<>("trainers.json", new TypeReference<List<Trainer>>() {});
        //trainerInMemoryRepo.add(t4);
//       trainerInMemoryRepo.remove(4);
//        List<Trainer> allTrainers = trainerRepo.getAll();
//        trainerInMemoryRepo.get(4);
//        System.out.println("All trainers: " + allTrainers);

//        Session session1 = new Session(1,DifficultyLevel.beginner,23,t1.getId(),50);
//        sessionRepo.add(session1);
//
//        Session session2 = new Session(2, DifficultyLevel.intermediary, 20, t2.getId(), 75);
//        sessionRepo.add(session2);
//
//        Session session3 = new Session(3, DifficultyLevel.advanced, 15, t3.getId(), 100);
//       sessionRepo.add(session3);
//
//
//        Parent parent1 = new Parent(1,"Muresan","Victor","muresanVictor@gmail.com","Calea Baciului nr 5",1980,"0783243165");
//        Student student1 = new Student(1,"Muresan","Alex","alex@gmail.com","Calea Baciului nr 5",2010,"0754635543","white",session1.getId());
////        studentRepo.remove(student1.getId());
//        student1.getSessionDateList().add(new SessionDate("monday","2024-11-11",session1.getId(),true));
//        student1.getSessionDateList().add(new SessionDate("wednesday","2024-11-13",session1.getId(),true));
//        student1.getSessionDateList().add(new SessionDate("friday","2024-11-15",session1.getId(), true));
//       parentChild(session1,student1,parent1,parentRepo,studentRepo,sessionRepo);
////       System.out.println(studentRepo.getAll());
//
//        // Părinți adiționali
//        Parent parent2 = new Parent(2, "Pop", "Cristina", "cristina.pop@gmail.com", "Strada Republicii nr 12", 1982, "0755555555");
//        Parent parent3 = new Parent(3, "Dobre", "Ion", "ion.dobre@yahoo.com", "Strada Zorilor nr 14", 1985, "0744123123");
//        Parent parent4 = new Parent(4, "Matei", "Ioana", "ioana.matei@outlook.com", "Strada Garii nr 6", 1979, "0724123987");
//        Parent parent5 = new Parent(5, "Ionescu", "Daniel", "daniel.ionescu@gmail.com", "Strada Ciresilor nr 10", 1983, "0762123890");
//        Parent parent6 = new Parent(6, "Radu", "Marius", "marius.radu@gmail.com", "Strada Independentei nr 20", 1988, "0767891234");
//
//// Prima pereche de frați
//        Student student2 = new Student(2, "Pop", "Ana", "ana.pop@gmail.com", "Strada Republicii nr 12", 2012, "0751234567", "yellow", session2.getId());
//        Student student3 = new Student(3, "Pop", "Mihai", "mihai.pop@gmail.com", "Strada Republicii nr 12", 2014, "0757654321", "yellow", session2.getId());
//        student2.getSessionDateList().add(new SessionDate("friday","2024-11-15",session2.getId(), true));
//        student2.getSessionDateList().add(new SessionDate("monday","2024-11-18",session2.getId(),true));
//        student3.getSessionDateList().add(new SessionDate("friday","2024-11-15",session2.getId(),true));
//        parentChild(session2, student2, parent2, parentRepo, studentRepo, sessionRepo);
//        parentChild(session2, student3, parent2, parentRepo, studentRepo, sessionRepo);
//
//// A doua pereche de frați
//        Student student4 = new Student(4, "Dobre", "Florin", "florin.dobre@yahoo.com", "Strada Zorilor nr 14", 2011, "0744112233", "orange", session3.getId());
//        Student student5 = new Student(5, "Dobre", "Ioana", "ioana.dobre@yahoo.com", "Strada Zorilor nr 14", 2013, "0744556677", "orange", session3.getId());
//        student4.getSessionDateList().add(new SessionDate("friday","2024-10-15",session2.getId(),true));
//        student4.getSessionDateList().add(new SessionDate("monday","2024-11-18",session2.getId(),true));
//        student5.getSessionDateList().add(new SessionDate("friday","2024-11-15",session2.getId(),true));
//        student5.getSessionDateList().add(new SessionDate("wednesday","2024-11-20",session2.getId(),false));
//        parentChild(session3, student4, parent3, parentRepo, studentRepo, sessionRepo);
//        parentChild(session3, student5, parent3, parentRepo, studentRepo, sessionRepo);
//
//// Studenți unici
//        Student student6 = new Student(6, "Matei", "Vlad", "vlad.matei@yahoo.com", "Strada Garii nr 6", 2012, "0721122334", "green", session1.getId());
//        Student student7 = new Student(7, "Ionescu", "Irina", "irina.ionescu@gmail.com", "Strada Ciresilor nr 10", 2010, "0765123789", "blue", session2.getId());
//        Student student8 = new Student(8, "Radu", "Andrei", "andrei.radu@gmail.com", "Strada Independentei nr 20", 2011, "0767894561", "green", session3.getId());
//        student6.getSessionDateList().add(new SessionDate("friday","2024-10-15",session1.getId(),true));
//        student6.getSessionDateList().add(new SessionDate("monday","2024-10-18",session1.getId(),false));
//        student6.getSessionDateList().add(new SessionDate("monday","2024-11-18",session1.getId(),true));
//        student7.getSessionDateList().add(new SessionDate("friday","2024-11-14",session2.getId(),true));
//        student8.getSessionDateList().add(new SessionDate("wednesday","2024-11-20",session3.getId(),false));
//        parentChild(session1, student6, parent4, parentRepo, studentRepo, sessionRepo);
//        parentChild(session2, student7, parent5, parentRepo, studentRepo, sessionRepo);
//        parentChild(session3, student8, parent6, parentRepo, studentRepo, sessionRepo);
//
//// Studenți fără părinți
//        Student student9 = new Student(9, "Vasilescu", "Elena", "elena.vasilescu@gmail.com", "Strada Libertatii nr 5", 2013, "0734123890", "green", session1.getId());
//        Student student10 = new Student(10, "Marin", "Paul", "paul.marin@gmail.com", "Strada Primaverii nr 9", 2012, "0712123890", "white", session2.getId());
//        Student student11 = new Student(11, "Popa", "Diana", "diana.popa@yahoo.com", "Strada Viitorului nr 2", 2011, "0785123890", "blue", session3.getId());
//        student9.getSessionDateList().add(new SessionDate("friday","2024-10-15",session1.getId(),true));
//        student9.getSessionDateList().add(new SessionDate("monday","2024-10-18",session1.getId(),false));
//        student10.getSessionDateList().add(new SessionDate("monday","2024-10-18",session2.getId(),true));
//        student11.getSessionDateList().add(new SessionDate("friday","2024-11-15",session3.getId(),true));
//        student11.getSessionDateList().add(new SessionDate("wednesday","2024-11-20",session3.getId(),false));
//        parentChild(session1, student9, parent4, parentRepo, studentRepo, sessionRepo);
//        parentChild(session2, student10, parent5, parentRepo, studentRepo, sessionRepo);
//        parentChild(session3, student11, parent6, parentRepo, studentRepo, sessionRepo);
//
//
//// Actualizare sesiunii pentru studenții fără părinți
//        sessionRepo.update(session1);
//        sessionRepo.update(session2);
//        sessionRepo.update(session3);

//        Contest contest1 = new Contest(101, "2024-11-28", "2024-11-30", 300, "Romania", "Cluj-Napoca", "Campionatul International", "Sala Sporturilor");
//        contestRepo.add(contest1);
//
//        Contest contest2 = new Contest(102, "2024-12-05", "2024-12-07", 250, "Romania", "Bucuresti", "Campionatul National", "Sala Polivalenta");
//        contestRepo.add(contest2);
//
//        Contest contest3 = new Contest(103, "2025-01-12", "2025-01-15", 350, "Romania", "Timisoara", "Open-ul Romaniei", "Sala Olimpia");
//        contestRepo.add(contest3);
//
//        Contest contest4 = new Contest(104, "2025-02-20", "2025-02-22", 400, "Romania", "Iasi", "Cupa Moldovei", "Sala Sporturilor");
//        contestRepo.add(contest4);
//
//        TrainingCamp trainingCamp1 = new TrainingCamp(11, "2025-06-15", "2025-06-20", 500, "Romania", "Brasov", "Complexul Olimpic", 25);
//        trainingCampRepo.add(trainingCamp1);
//
//        TrainingCamp trainingCamp2 = new TrainingCamp(12, "2025-07-01", "2025-07-07", 650, "Romania", "Sibiu", "Sala Transilvania", 30);
//        trainingCampRepo.add(trainingCamp2);
//
//        TrainingCamp trainingCamp3 = new TrainingCamp(13, "2025-08-10", "2025-08-15", 550, "Romania", "Constanta", "Sala Sporturilor", 20);
//        trainingCampRepo.add(trainingCamp3);
//
//        BeltExam beltExam1 = new BeltExam(1, "2025-05-05", "2025-05-06", 150, "Romania", "Cluj-Napoca", "Sala Sporturilor", "yellow");
//        beltExamRepo.add(beltExam1);
//
//        BeltExam beltExam2 = new BeltExam(2, "2025-06-10", "2025-06-11", 200, "Romania", "Bucuresti", "Sala Polivalenta", "green");
//        beltExamRepo.add(beltExam2);
//
//        BeltExam beltExam3 = new BeltExam(3, "2025-07-15", "2025-07-16", 250, "Romania", "Timisoara", "Centrul Sportiv", "blue");
//        beltExamRepo.add(beltExam3);
//

        
//        try {
//            DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=TKD-Management;integratedSecurity=true;trustServerCertificate=true;");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        DatabaseRepo<Student> students2 = new DatabaseStudent("jdbc:sqlserver://localhost:1433;database=TKD-Management;integratedSecurity=true;trustServerCertificate=true;");
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