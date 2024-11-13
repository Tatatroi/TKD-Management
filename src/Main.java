//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import Ui.TKDUI;
import Controller.TKDController;
import Service.TKD_Service;
import Model.*;
import Repo.*;

import static Model.DifficultyLevel.beginner;

public class Main {

    public static void parentChild(Session session, Student student, Parent parent, InMemoryRepo<Parent> parentRepo, InMemoryRepo<Student> studentRepo, InMemoryRepo<Session> sessionRepo){
        session.getSessionStudents().add(student);
        sessionRepo.update(session);
        parent.getChildren().add(student);
        student.setParent(parent);
        parentRepo.add(parent);
        studentRepo.add(student);
    }



    public static void main(String[] args) {

        InMemoryRepo<Student> studentRepo = new InMemoryRepo<>();
        InMemoryRepo<Parent> parentRepo = new InMemoryRepo<>();
        InMemoryRepo<Session> sessionRepo = new InMemoryRepo<>();
        InMemoryRepo<Contest> contestRepo = new InMemoryRepo<>();
        InMemoryRepo<Trainer> trainerRepo = new InMemoryRepo<>();
        InMemoryRepo<BeltExam> beltExamRepo = new InMemoryRepo<>();
        InMemoryRepo<TrainingCamp> trainingCampRepo = new InMemoryRepo<>();


        Trainer t1 = new Trainer(1,"Mitroi","Stefan","srefanmitroi@gmail.com","Calea Floresti nr 58B",2004,"0761969675","black");
        trainerRepo.add(t1);

        Trainer t2 = new Trainer(2, "Popescu", "Andrei", "andrei.popescu@gmail.com", "Strada Mihai Viteazu nr 15", 1990, "0755123456", "red");
        trainerRepo.add(t2);

        Trainer t3 = new Trainer(3, "Ionescu", "Maria", "maria.ionescu@yahoo.com", "Bulevardul Eroilor nr 45", 1985, "0725987654", "blue");
        trainerRepo.add(t3);

        Trainer t4 = new Trainer(4, "Vasilescu", "Radu", "radu.vasilescu@outlook.com", "Strada Zorilor nr 23", 1992, "0744123456", "green");
        trainerRepo.add(t4);


        Session session1 = new Session(1,DifficultyLevel.beginner,23,t1,50);
        sessionRepo.add(session1);

        Session session2 = new Session(2, DifficultyLevel.intermediary, 20, t2, 75);
        sessionRepo.add(session2);

        Session session3 = new Session(3, DifficultyLevel.advanced, 15, t3, 100);
        sessionRepo.add(session3);


        Parent parent1 = new Parent(1,"Muresan","Victor","muresanVictor@gmail.com","Calea Baciului nr 5",1980,"0783243165");
        Student student1 = new Student(1,"Muresan","Alex","alex@gmail.com","Calea Baciului nr 5",2010,"0754635543","white",session1);
        parentChild(session1,student1,parent1,parentRepo,studentRepo,sessionRepo);

        // Părinți adiționali
        Parent parent2 = new Parent(2, "Pop", "Cristina", "cristina.pop@gmail.com", "Strada Republicii nr 12", 1982, "0755555555");
        Parent parent3 = new Parent(3, "Dobre", "Ion", "ion.dobre@yahoo.com", "Strada Zorilor nr 14", 1985, "0744123123");
        Parent parent4 = new Parent(4, "Matei", "Ioana", "ioana.matei@outlook.com", "Strada Garii nr 6", 1979, "0724123987");
        Parent parent5 = new Parent(5, "Ionescu", "Daniel", "daniel.ionescu@gmail.com", "Strada Ciresilor nr 10", 1983, "0762123890");
        Parent parent6 = new Parent(6, "Radu", "Marius", "marius.radu@gmail.com", "Strada Independentei nr 20", 1988, "0767891234");

// Prima pereche de frați
        Student student2 = new Student(2, "Pop", "Ana", "ana.pop@gmail.com", "Strada Republicii nr 12", 2012, "0751234567", "yellow", session2);
        Student student3 = new Student(3, "Pop", "Mihai", "mihai.pop@gmail.com", "Strada Republicii nr 12", 2014, "0757654321", "yellow", session2);
        parentChild(session2, student2, parent2, parentRepo, studentRepo, sessionRepo);
        parentChild(session2, student3, parent2, parentRepo, studentRepo, sessionRepo);

// A doua pereche de frați
        Student student4 = new Student(4, "Dobre", "Florin", "florin.dobre@yahoo.com", "Strada Zorilor nr 14", 2011, "0744112233", "orange", session3);
        Student student5 = new Student(5, "Dobre", "Ioana", "ioana.dobre@yahoo.com", "Strada Zorilor nr 14", 2013, "0744556677", "orange", session3);
        parentChild(session3, student4, parent3, parentRepo, studentRepo, sessionRepo);
        parentChild(session3, student5, parent3, parentRepo, studentRepo, sessionRepo);

// Studenți unici
        Student student6 = new Student(6, "Matei", "Vlad", "vlad.matei@yahoo.com", "Strada Garii nr 6", 2012, "0721122334", "green", session1);
        Student student7 = new Student(7, "Ionescu", "Irina", "irina.ionescu@gmail.com", "Strada Ciresilor nr 10", 2010, "0765123789", "blue", session2);
        Student student8 = new Student(8, "Radu", "Andrei", "andrei.radu@gmail.com", "Strada Independentei nr 20", 2011, "0767894561", "green", session3);
        parentChild(session1, student6, parent4, parentRepo, studentRepo, sessionRepo);
        parentChild(session2, student7, parent5, parentRepo, studentRepo, sessionRepo);
        parentChild(session3, student8, parent6, parentRepo, studentRepo, sessionRepo);

// Studenți fără părinți
        Student student9 = new Student(9, "Vasilescu", "Elena", "elena.vasilescu@gmail.com", "Strada Libertatii nr 5", 2013, "0734123890", "green", session1);
        Student student10 = new Student(10, "Marin", "Paul", "paul.marin@gmail.com", "Strada Primaverii nr 9", 2012, "0712123890", "white", session2);
        Student student11 = new Student(11, "Popa", "Diana", "diana.popa@yahoo.com", "Strada Viitorului nr 2", 2011, "0785123890", "blue", session3);
        parentChild(session1, student9, parent4, parentRepo, studentRepo, sessionRepo);
        parentChild(session2, student10, parent5, parentRepo, studentRepo, sessionRepo);
        parentChild(session3, student11, parent6, parentRepo, studentRepo, sessionRepo);
//        session1.getSessionStudents().add(student9);
//        session2.getSessionStudents().add(student10);
//        session3.getSessionStudents().add(student11);

// Actualizare sesiunii pentru studenții fără părinți
        sessionRepo.update(session1);
        sessionRepo.update(session2);
        sessionRepo.update(session3);

        Contest contest1 = new Contest(101,"28.11.2024","30.11.2024",300,"Romania","Cluj-Napoca","Campionatu International","Sala Sporturilor");
        contestRepo.add(contest1);

        Contest contest2 = new Contest(102, "05.12.2024", "07.12.2024", 250, "Romania", "Bucuresti", "Campionatul National", "Sala Polivalenta");
        contestRepo.add(contest2);

        Contest contest3 = new Contest(103, "12.01.2025", "15.01.2025", 350, "Romania", "Timisoara", "Open-ul Romaniei", "Sala Olimpia");
        contestRepo.add(contest3);

        Contest contest4 = new Contest(104, "20.02.2025", "22.02.2025", 400, "Romania", "Iasi", "Cupa Moldovei", "Sala Sporturilor");
        contestRepo.add(contest4);


        TrainingCamp trainingCamp1 = new TrainingCamp(11, "15.06.2025", "20.06.2025", 500, "Romania", "Brasov", "Complexul Olimpic", 25);
        trainingCampRepo.add(trainingCamp1);

        TrainingCamp trainingCamp2 = new TrainingCamp(12, "01.07.2025", "07.07.2025", 650, "Romania", "Sibiu", "Sala Transilvania", 30);
        trainingCampRepo.add(trainingCamp2);

        TrainingCamp trainingCamp3 = new TrainingCamp(13, "10.08.2025", "15.08.2025", 550, "Romania", "Constanta", "Sala Sporturilor", 20);
        trainingCampRepo.add(trainingCamp3);

        BeltExam beltExam1 = new BeltExam(1, "05.05.2025", "06.05.2025", 150, "Romania", "Cluj-Napoca", "Sala Sporturilor", "yellow");
        beltExamRepo.add(beltExam1);

        BeltExam beltExam2 = new BeltExam(2, "10.06.2025", "11.06.2025", 200, "Romania", "Bucuresti", "Sala Polivalenta", "green");
        beltExamRepo.add(beltExam2);

        BeltExam beltExam3 = new BeltExam(3, "15.07.2025", "16.07.2025", 250, "Romania", "Timisoara", "Centrul Sportiv", "blue");
        beltExamRepo.add(beltExam3);




        TKD_Service tkdService = new TKD_Service(studentRepo,trainerRepo,parentRepo,sessionRepo,contestRepo,trainingCampRepo,beltExamRepo);
        TKDController tkdController = new TKDController(tkdService);
        TKDUI newUi = new TKDUI(tkdController);

        newUi.start();



    }
}