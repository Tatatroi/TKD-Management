package org.example;

import org.example.Controller.TKDController;
import org.example.Model.*;
import org.example.Repository.InMemoryRepo;
import org.example.Service.TKD_Service;
import org.example.Ui.TKDUI;
import org.junit.jupiter.api.Test;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    TKD_Service tkdService = new TKD_Service(studentsRepo,trainersRepo,parentsRepo,sessionsRepo,contestsRepo,trainingCampsRepo,beltExamsRepo);
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
    void testEventsThatDontExceedAmountOfMoney_ValidData() {
        // Mock pentru contests
        Contest contest1 = new Contest(1,"12.12.2024","12.12.2025",200,"Romania","Cluj-Napoca", "Campionat European", "Sala Sporturilor Horia Demian");
        Contest contest2 = new Contest(1,"13.12.2024","13.12.2025",300,"Romania","Cluj-Napoca", "Campionat European", "Sala Sporturilor Horia Demian");

        contestsRepo.add(contest1);
        contestsRepo.add(contest2);

        List<List<Integer>> results = new ArrayList<>();

        assertEquals(Arrays.asList(Arrays.asList(200,300), results);


    }


}
