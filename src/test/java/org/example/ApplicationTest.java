package org.example;

import org.example.Model.*;
import org.example.Repository.InMemoryRepo;
import org.junit.jupiter.api.Test;
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
}
