package org.example;

import org.example.Controller.TKDController;
import org.example.Exceptions.BusinessLogicException;
import org.example.Exceptions.DatabaseException;
import org.example.Exceptions.EntityNotFoundException;
import org.example.Model.*;
import org.example.Repository.InMemoryRepo;
import org.example.Service.TKD_Service;
import org.example.Ui.TKDUI;
import org.junit.jupiter.api.Test;

import java.security.Provider;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
public class ApplicationTest {


    InMemoryRepo<Student> studentsRepo = new InMemoryRepo<>();
    InMemoryRepo<Parent> parentsRepo = new InMemoryRepo<>();
    InMemoryRepo<Trainer> trainersRepo = new InMemoryRepo<>();
    InMemoryRepo<Session> sessionsRepo = new InMemoryRepo<>();
    InMemoryRepo<Contest> contestsRepo = new InMemoryRepo<>();
    InMemoryRepo<TrainingCamp> trainingCampsRepo = new InMemoryRepo<>();
    InMemoryRepo<BeltExam> beltExamsRepo = new InMemoryRepo<>();
    Trainer t1 = new Trainer(1,"Mitroi","Stefan","srefanmitroi@gmail.com","Calea Floresti nr 58B",2004,"0761969675","black");
    Session session1 = new Session(1,DifficultyLevel.beginner,23,t1.getId(),50);
    Parent parent1 = new Parent(1,"Muresan","Victor","muresanVictor@gmail.com","Calea Baciului nr 5",1980,"0783243165");
    Student student1 = new Student(1,"Muresan","Alex","alex@gmail.com","Calea Baciului nr 5",2010,"0754635543","white",session1.getId());
    Contest contest1 = new Contest(101, "2024-11-28", "2024-11-30", 300, "Romania", "Cluj-Napoca", "Campionatul International", "Sala Sporturilor");
    TrainingCamp trainingCamp1 = new TrainingCamp(11, "2025-06-15", "2025-06-20", 500, "Romania", "Brasov", "Complexul Olimpic", 25);
    BeltExam beltExam1 = new BeltExam(1, "2025-05-05", "2025-05-06", 150, "Romania", "Cluj-Napoca", "Sala Sporturilor", "yellow");

    TKD_Service tkdService = new TKD_Service(studentsRepo, trainersRepo, parentsRepo,sessionsRepo,contestsRepo,trainingCampsRepo,beltExamsRepo);
    TKDController tkdController = new TKDController(tkdService);
    TKDUI newUi = new TKDUI(tkdController);

    @Test
    void TestCRUDStudent(){
        studentsRepo.add(student1);
        assertEquals(student1, studentsRepo.get(student1.getId()));
        student1.setDateOfBirth(2010);
        studentsRepo.update(student1);
        assertEquals(2010, studentsRepo.get(parent1.getId()).getDateOfBirth());
        studentsRepo.remove(student1.getId());
        assertNull(studentsRepo.get(student1.getId()));
    }
    @Test
    void TestCRUDParent(){
        parentsRepo.add(parent1);
        assertEquals(parent1, parentsRepo.get(parent1.getId()));
        parent1.setLastName("Andrei");
        parentsRepo.update(parent1);
        assertEquals("Andrei", parentsRepo.get(parent1.getId()).getLastName());
        parentsRepo.remove(parent1.getId());
        assertNull(parentsRepo.get(parent1.getId()));
    }

    @Test
    void TestCRUDTrainer() {
        trainersRepo.add(t1);
        assertEquals(t1, trainersRepo.get(t1.getId()));
        t1.setName("Mihai");
        trainersRepo.update(t1);
        assertEquals("Mihai", trainersRepo.get(t1.getId()).getName());
        trainersRepo.remove(t1.getId());
        assertNull(trainersRepo.get(t1.getId()));
    }

    @Test
    void TestCRUDSession() {
        sessionsRepo.add(session1);
        assertEquals(session1, sessionsRepo.get(session1.getId()));
        session1.setDifficultyLevel(DifficultyLevel.beginner);
        sessionsRepo.update(session1);
        assertEquals(DifficultyLevel.beginner, sessionsRepo.get(session1.getId()).getDifficultyLevel());
        sessionsRepo.remove(session1.getId());
        assertNull(sessionsRepo.get(session1.getId()));
    }

    @Test
    void TestCRUDContest() {
        contestsRepo.add(contest1);
        assertEquals(contest1, contestsRepo.get(contest1.getId()));
        contest1.setName("Mondial");
        contestsRepo.update(contest1);
        assertEquals("Mondial", contestsRepo.get(contest1.getId()).getName());
        contestsRepo.remove(contest1.getId());
        assertNull(contestsRepo.get(contest1.getId()));
    }

    @Test
    void TestCRUDTrainingCamp() {
        trainingCampsRepo.add(trainingCamp1);
        assertEquals(trainingCamp1, trainingCampsRepo.get(trainingCamp1.getId()));
        trainingCamp1.setPrice(400.50);
        trainingCampsRepo.update(trainingCamp1);
        assertEquals(400.50, trainingCampsRepo.get(trainingCamp1.getId()).getPrice());
        trainingCampsRepo.remove(trainingCamp1.getId());
        assertNull(trainingCampsRepo.get(trainingCamp1.getId()));
    }

    @Test
    void TestCRUDBeltExamns() {
        beltExamsRepo.add(beltExam1);
        assertEquals(beltExam1, beltExamsRepo.get(beltExam1.getId()));
        beltExam1.setBeltColor("red");
        beltExamsRepo.update(beltExam1);
        assertEquals("red",beltExamsRepo.get(beltExam1.getId()).getBeltColor());
        beltExamsRepo.remove(beltExam1.getId());
        assertNull(beltExamsRepo.get(beltExam1.getId()));
    }

    @Test
    void testEventsThatDontExceedAmountOfMoneyValidData() throws EntityNotFoundException, DatabaseException, BusinessLogicException {
        // Mock pentru contests
        Contest contest1 = new Contest(1,"12.12.2024","12.12.2025",200,"Romania","Cluj-Napoca", "Campionat European", "Sala Sporturilor Horia Demian");
        Contest contest2 = new Contest(2,"13.12.2024","13.12.2025",300,"Romania","Cluj-Napoca", "Campionat European", "Sala Sporturilor Horia Demian");

        contestsRepo.add(contest1);
        contestsRepo.add(contest2);

        List<List<Integer>> results = new ArrayList<>();

        results = tkdService.eventsThatdontExceedAmountOfMoney(500);

        assertEquals(Arrays.asList(Arrays.asList(1,2)), results);

    }

    @Test
    void testEventsThatDontExceedAmountOfMoneyNoValidData() throws EntityNotFoundException, DatabaseException, BusinessLogicException {
        // Mock pentru contests
        Contest contest1 = new Contest(1,"12.12.2024","12.12.2025",200,"Romania","Cluj-Napoca", "Campionat European", "Sala Sporturilor Horia Demian");
        Contest contest2 = new Contest(2,"13.12.2024","13.12.2025",300,"Romania","Cluj-Napoca", "Campionat European", "Sala Sporturilor Horia Demian");

        contestsRepo.add(contest1);
        contestsRepo.add(contest2);

        List<List<Integer>> results = new ArrayList<>();

        Exception exp = assertThrows(BusinessLogicException.class, () -> {
            tkdService.eventsThatdontExceedAmountOfMoney(100); // Pragul de 100 este mai mic decât prețul oricărui concurs
        });

        // Verificarea tipului excepției
        assertTrue(exp instanceof BusinessLogicException);

    }

    @Test
    void testInvoiceValidData() throws EntityNotFoundException, DatabaseException, BusinessLogicException {
        parent1.setChildren(Collections.singletonList(student1.getId()));

        parentsRepo.add(parent1);

        student1.setParent(parent1.getId());

        studentsRepo.add(student1);

        sessionsRepo.add(session1);

        tkdService.addAttendance(student1.getId(), session1.getId(), true, "Monday", "2024/12/01");

        String invoice = "Invoice for the month " + "12" + "\nParent name: " + parent1.getLastName() + " " + parent1.getName() + "\n";
        invoice += "Student name: " + student1.getLastName() + " " + student1.getName() + "\n Total for student: " + session1.getPricePerSession() + "\n";
        invoice += "Total: " + session1.getPricePerSession() +"\n";

        String invoice2 = tkdService.generateInvoice(parent1.getId(),"12");

        assertEquals(invoice, invoice2);

    }

    @Test
    void testInvoiceNoValidData() throws EntityNotFoundException, DatabaseException, BusinessLogicException {
        parent1.setChildren(Collections.singletonList(student1.getId()));

        parentsRepo.add(parent1);

        student1.setParent(parent1.getId());

        studentsRepo.add(student1);

        sessionsRepo.add(session1);

        tkdService.addAttendance(student1.getId(), session1.getId(), true, "Monday", "2024/12/01");

        String invoice = "Invoice for the month " + "12" + "\nParent name: " + parent1.getLastName() + " " + parent1.getName() + "\n";
        invoice += "Student name: " + student1.getLastName() + " " + student1.getName() + "\n Total for student: " + 0 + "\n";
        invoice += "Total: " + session1.getPricePerSession() +"\n";

        String invoice2 = tkdService.generateInvoice(parent1.getId(),"12");

        Exception exp = assertThrows(BusinessLogicException.class, () -> {
            tkdService.eventsThatdontExceedAmountOfMoney(100); // Pragul de 100 este mai mic decât prețul oricărui concurs
        });

        assertTrue(exp instanceof BusinessLogicException);

    }

    @Test
    void testGetMostProfitableDateForSessionValidData() throws EntityNotFoundException, DatabaseException, BusinessLogicException {
        // Pregătire date mock
        Student student1 = new Student(2,"Mitroi","Stefan","srefanmitroi@gmial.com","Calea Baciului nr 34", 2004, "074635428","black", session1.getId());
        Student student2 = new Student(3, "Popescu", "Ioana", "ioanapopescu@gmail.com", "Strada Republicii nr. 12", 2006, "0723456789", "red", session1.getId());
        Student student3 = new Student(4, "Ionescu", "Radu", "raduionescu@yahoo.com", "Bulevardul Muncii nr. 56", 2005, "0765432109", "blue", session1.getId());

        sessionsRepo.add(session1);
        studentsRepo.add(student1);
        studentsRepo.add(student2);
        studentsRepo.add(student3);

        student1.getSessionDateList().add(new SessionDate("Monday", "2024-12-01", session1.getId(), true));
        student1.getSessionDateList().add(new SessionDate("Wednesday", "2024-12-06", session1.getId(), true));
        student1.getSessionDateList().add(new SessionDate("Friday", "2024-12-08", session1.getId(), false));

        AbstractMap.SimpleEntry<String, Double> result = tkdService.getMostProfitableDateForSession(1);

        assertEquals("2024-12-01", result.getKey());
        assertEquals(100.0, result.getValue(), 0.01);
    }

    @Test
    void testGetMostProfitableDateForSessionNoValidData() {
        // Pregătire date mock
        Student student1 = new Student(2, "Mitroi", "Stefan", "srefanmitroi@gmial.com", "Calea Baciului nr 34", 2004, "074635428", "black", session1.getId());
        Student student2 = new Student(3, "Popescu", "Ioana", "ioanapopescu@gmail.com", "Strada Republicii nr. 12", 2006, "0723456789", "red", session1.getId());
        Student student3 = new Student(4, "Ionescu", "Radu", "raduionescu@yahoo.com", "Bulevardul Muncii nr. 56", 2005, "0765432109", "blue", session1.getId());

        sessionsRepo.add(session1);
        studentsRepo.add(student1);
        studentsRepo.add(student2);
        studentsRepo.add(student3);

        // Adăugăm date invalide (de exemplu, nicio sesiune nu este marcată ca participată)
        student1.getSessionDateList().add(new SessionDate("Monday", "2024-12-01", session1.getId(), false));
        student2.getSessionDateList().add(new SessionDate("Wednesday", "2024-12-06", session1.getId(), false));
        student3.getSessionDateList().add(new SessionDate("Friday", "2024-12-08", session1.getId(), false));

        // Verificăm dacă metoda aruncă BusinessLogicException
        assertThrows(BusinessLogicException.class, () -> {
            tkdService.getMostProfitableDateForSession(1);
        });
    }






}
