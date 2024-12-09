package org.example.Service;

import org.example.Exceptions.DatabaseException;
import org.example.Model.*;
import org.example.Repository.DatabaseRepo;
import org.example.Repository.InMemoryRepo;
import org.example.Repository.InMemoryRepo;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A service class that provides the business logic for the TKD-Management system.
 */
public class TKD_Service {
    private DatabaseRepo<Student> students;

    private DatabaseRepo<Trainer> trainers;

    private DatabaseRepo<Parent> parents;

    private DatabaseRepo<Session> sessions;

    private DatabaseRepo<Contest> contests;

    private DatabaseRepo<TrainingCamp> trainingCamps;

    private DatabaseRepo<BeltExam> beltExams;

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
    public TKD_Service(DatabaseRepo<Student> students, DatabaseRepo<Trainer> trainers, DatabaseRepo<Parent> parent, DatabaseRepo<Session> sessions, DatabaseRepo<Contest> contests, DatabaseRepo<TrainingCamp> trainingCamps, DatabaseRepo<BeltExam> beltExams) {
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
     * @throws IOException  If no trainer or session was found.
     */
    public void assignGroupToTrainer(int trainerId, int sessionId) throws IOException {
//        try {
//            if(trainers.getAll().stream().noneMatch(tr -> tr.getId() == trainerId)){
//                throw new IOException("Invalid trainer ID");
//            }
//        } catch (DatabaseException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            if(sessions.getAll().stream().noneMatch(ss->ss.getId()== sessionId)){
//                throw new IOException("Invalid session ID");
//            }
//        } catch (DatabaseException e) {
//            throw new RuntimeException(e);
//        }
        Trainer tr = null; 
try {
    tr = trainers.get(trainerId);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        Session ss = null; 
try {
    ss = sessions.get(sessionId);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        ss.trainer=tr.getId();
    try {
    sessions.update(ss);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}

    }

    /**
     * Change the session of a student and remove him from the previous one.
     * @param studentId     The unique identifier of a student.
     * @param sessionId     The unique identifier of a session.
     * @throws IOException  If no student or session was found.
     */
    public void changeStudentGroup(int studentId,int sessionId) throws IOException {
//        if(students.getAll().stream().noneMatch(st -> st.getId() == studentId)){
//            throw new IOException("Invalid student ID");
//        }
//        if(sessions.getAll().stream().noneMatch(ss->ss.getId()== sessionId)){
//            throw new IOException("Invalid session ID");
//        }
        Student st = null; 
try {
    st = students.get(studentId);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        Session new_ss = null; 
try {
    new_ss = sessions.get(sessionId);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        Session old_ss = null;
        try {
            old_ss = sessions.get(st.getSession());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        old_ss.getSessionStudents().remove(st.getId());
        new_ss.getSessionStudents().add(st.getId());
        st.setSession(new_ss.id);
        try {
    students.update(st);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        try {
    sessions.update(new_ss);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        try {
    sessions.update(old_ss);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
    }

    /**
     * Changes the belt color of a student if he passed the exam.
     * @param beltExamID  The unique identifier of a belt exam.
     */
    public void changeBeltlevel(Integer beltExamID){
        try {
            for(int stId: beltExams.get(beltExamID).getListOfResults().keySet()){
                if(beltExams.get(beltExamID).getListOfResults().get(stId)==1){
                    students.get(stId).setBeltLevel(beltExams.get(beltExamID).getBeltColor());
                    students.update(students.get(stId));
                }
            }
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Counts the number of attendances and absences of a student.
     * @param studentId         The unique identifier of a student.
     * @return                  Map containing the number of attendances and absences of a student.
     * @throws IOException      If no student was found.
     */
    public Map<String,Integer> numberOfAttendencesAndAbsences(int studentId) throws IOException {
//        if(students.getAll().stream().noneMatch(st -> st.getId() == studentId)){
//            throw new IOException("Invalid student ID");
//        }
        Student st= null;
        try {
            st = students.get(studentId);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
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
     * @param eventPairs            a list with information that holds all Contest/TrainingCamp and their prices
     * @param remainingAmount       the amount of money after one Contest/TrainingCamp has been chosen
     * @param start                 starting point where the function start to search
     * @param currentCombination    the combination on every search
     * @param results               this is a parameter where all combinations will be found
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
     * @param amountOfMoney     the range that it's forbidden to be exceeded
     * @return                  a list of lists with all possible combinations
     */
    public List<List<Integer>> eventsThatdontExceedAmountOfMoney(double amountOfMoney) throws DatabaseException{
        try{
            List<Map.Entry<Integer, Double>> eventPairs = new ArrayList<>();

            for (int i = 0; i < contests.getAll().size(); i++) {
                Contest ct = contests.getAll().get(i);
                eventPairs.add(new AbstractMap.SimpleEntry<>(ct.getId(), ct.price));
            }
            for (int i = 0; i < trainingCamps.getAll().size(); i++) {
                TrainingCamp ct = trainingCamps.getAll().get(i);
                eventPairs.add(new AbstractMap.SimpleEntry<>(ct.getId(), ct.price));
            }

            List<List<Integer>> results = new ArrayList<>();
            findCombinations(eventPairs, amountOfMoney, 0, new ArrayList<>(), results);
            return results;

        }catch (DatabaseException e){
            throw new RuntimeException();
        }

    }

    /**
     * Adds a student to a belt exam.
     * @param idStudent     The unique identifier of a student.
     * @param idBeltExam    The unique identifier of a belt exam.
     * @throws IOException  If no student or belt exam was found.
     */
    public void addStudentToBeltExam(int idStudent, int idBeltExam) throws IOException {
//        if(students.getAll().stream().noneMatch(st -> st.getId() == idStudent)){
//            throw new IOException("Invalid student ID");
//        }
//        if(beltExams.getAll().stream().noneMatch(bt -> bt.getId() == idBeltExam)){
//            throw new IOException("Invalid belt exam ID");
//        }
        Student s = null; 
try {
    s = students.get(idStudent);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        BeltExam belt = null; 
try {
    belt = beltExams.get(idBeltExam);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        belt.getListOfResults().put(s.getId(),-1);
        try {
    beltExams.update(belt);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
    }

    /**
     * Updates the result of a belt exam of a student and if promoted changes the belt color.
     * @param idStudent     The unique identifier of a student.
     * @param idBeltExam    The unique identifier of a belt exam.
     * @param promoted      The result of the exam, passed(true) or failed(false).
     * @throws IOException  If no student or belt exam was found.
     */
    public void addResultBeltExam(int idStudent, int idBeltExam, boolean promoted) throws IOException {
        try {
    if(students.getAll().stream().noneMatch(st -> st.getId() == idStudent)){
            throw new IOException("Invalid student ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        try {
    if(beltExams.getAll().stream().noneMatch(bt -> bt.getId() == idBeltExam)){
            throw new IOException("Invalid belt exam ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        Student s = null; 
try {
    s = students.get(idStudent);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        BeltExam belt = null; 
try {
    belt = beltExams.get(idBeltExam);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        if(promoted){
            belt.getListOfResults().put(s.getId(),1); // promoted
            changeBeltlevel(idBeltExam);
        }
        else{
            belt.getListOfResults().put(s.getId(),0); // failed
        }
        try {
    beltExams.update(belt);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
    }

    /**
     * Adds an attendance/absence to the list of session dates from the student.
     * @param studentId     The unique identifier of a student.
     * @param sessionId     The unique identifier of a session.
     * @param attendance    The attendance of a student which can be true(was present) or false(wasn't present).
     * @param weekday       The day of the week of the session.
     * @param date          The exact date the session took place.
     * @throws IOException  If no student or session was found.
     */
    public void addAttendance(int studentId,int sessionId,boolean attendance,String weekday,String date) throws IOException {
        try {
    if(students.getAll().stream().noneMatch(st -> st.getId() == studentId)){
            throw new IOException("Invalid student ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        try {
    if(sessions.getAll().stream().noneMatch(ss -> ss.getId() == sessionId)){
            throw new IOException("Invalid session ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        Student s = null; 
try {
    s = students.get(studentId);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        SessionDate sessionDate = new SessionDate(weekday,date,sessionId,attendance);
        s.getSessionDateList().add(sessionDate);
        try {
    students.update(s);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
    }

    /**
     * Adds a student to a contest.
     * @param studentId     The unique identifier of a student.
     * @param contestId     The unique identifier of a contest.
     * @throws IOException  If no student or contest was found.
     */
    public void addStudentToContest(int studentId,int contestId) throws IOException {
        try {
    if(students.getAll().stream().noneMatch(st -> st.getId() == studentId)){
            throw new IOException("Invalid student ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        try {
    if(contests.getAll().stream().noneMatch(ct -> ct.getId() == contestId)){
            throw new IOException("Invalid contest ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        Student st = null; 
try {
    st = students.get(studentId);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        Contest ct = null; 
try {
    ct = contests.get(contestId);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        ct.getStudents().add(st.getId());
        st.getContestList().add(ct.getId());
        try {
    contests.update(ct);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        try {
    students.update(st);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
    }

    /**
     * Adds a student to a training camp.
     * @param studentId         The unique identifier of a student.
     * @param trainingCampId    The unique identifier of a training camp.
     * @throws IOException      If no student or training camp was found.
     */
    public void addStudentToTraining(int studentId,int trainingCampId) throws IOException {
        try {
    if(students.getAll().stream().noneMatch(st -> st.getId() == studentId)){
            throw new IOException("Invalid student ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        try {
    if(trainingCamps.getAll().stream().noneMatch(tc -> tc.getId() == trainingCampId)){
            throw new IOException("Invalid training camp ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        Student st = null; 
try {
    st = students.get(studentId);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        TrainingCamp tc = null; 
try {
    tc = trainingCamps.get(trainingCampId);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        tc.getStudents().add(st.getId());
        st.getTrainingCampList().add(tc.getId());
        try {
    trainingCamps.update(tc);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        try {
    students.update(st);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
    }

    /**
     * Adds a student to a parent, by searching the for the parent by email to see if it needs to be added to the repo.
     * @param student   The student object that needs to be added to the parent.
     * @param parent    The parent object that needs to be updated/ created.
     */
    public void addStudentToParent(Student student, Parent parent){
        if(findParent(parent.getEmail())){
            Parent updateParent = null;
            try {
                updateParent = parents.getAll().stream().filter(pt -> Objects.equals(pt.getEmail(),parent.getEmail())).findFirst().orElse(null);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
            updateParent.getChildren().add(student.getId());
            try {
                parents.update(updateParent);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
            student.setParent(updateParent.getId());
        }
        else {
            try {
                parents.add(parent);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
            parent.getChildren().add(student.getId());
            try {
                parents.update(parent);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
            student.setParent(parent.getId());
        }
    }

    /**
     * Searches in the parents repo for a parent by email and return true, if he exists.
     * @param email     the unique email of a Parent
     * @return          true/false if parent found
     */
    public boolean findParent(String email){
        try {
            return parents.getAll().stream().anyMatch(pt -> Objects.equals(pt.getEmail(), email));
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * this function adds an object in their repo based on their type
     * @param                o represent the given object
     * @throws IOException   If object id already exists.
     */
    public void addObject(Object o) throws IOException {
        if(o instanceof Student){
            try {
                students.add((Student) o);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }

        }
        else if(o instanceof Trainer){
//            if(trainers.getAll().stream().anyMatch(tr->tr.getId()==((Trainer) o).getId())){
//                throw new IOException("Trainer ID already exists");
//            }
            try {
                trainers.add((Trainer) o);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
        }
        else if(o instanceof Parent){
//            if(parents.getAll().stream().anyMatch(pt->pt.getId()==((Parent) o).getId())){
//                throw new IOException("Parent ID already exists");
//            }
            try {
                parents.add((Parent) o);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
        }
        else if(o instanceof Session){
//            if(sessions.getAll().stream().anyMatch(ss->ss.getId()==((Session) o).getId())){
//                throw new IOException("Session ID already exists");
//            }
            try {
                sessions.add((Session) o);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
        }
        else if(o instanceof BeltExam){
//            if(beltExams.getAll().stream().anyMatch(bx->bx.getId()==((BeltExam) o).getId())){
//                throw new IOException("Belt exam ID already exists");
//            }
            try {
                beltExams.add((BeltExam) o);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
        }
        else if(o instanceof Contest){
//            if(contests.getAll().stream().anyMatch(ct->ct.getId()==((Contest) o).getId())){
//                throw new RuntimeException();
//            }
            try {
                contests.add((Contest) o);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
        }
        else if(o instanceof TrainingCamp){
//            if(trainingCamps.getAll().stream().anyMatch(tc->tc.getId()==((TrainingCamp) o).getId())){
//                throw new IOException("Training camp ID already exists");
//            }
            try {
                trainingCamps.add((TrainingCamp) o);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * make an Invoice for every parent based on the month
     * @param parentID          the ID of the parent
     * @param month             the month they want an invoice
     * @return                  a string that holds information an invoice need to have (for every child they have)
     * @throws IOException      If no parent was found.
     */

    public String generateInvoice(Integer parentID,String month) throws IOException {
        try {
    if(parents.getAll().stream().noneMatch(pt -> pt.getId() == parentID)){
            throw new IOException("Invalid parent ID");
        }

        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }        Parent parent = null;
        try {
            parent = parents.get(parentID);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        String invoice="Invoice for the month " + month + "\nParent name: " + parent.getLastName() + " " + parent.getName() + "\n";
        double total = 0;
        for(int studentId: parent.getChildren()){
            Student student = null; 
            try {
                student = students.get(studentId);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }            int presences=0;
            double individualTotal = 0;
            for(SessionDate sd: student.getSessionDateList()){
                if(sd.isAttended() && sd.getDate().substring(5, 7).equals(month)){
                    presences++;
                }
            }
            try {
                individualTotal += presences*(sessions.get(student.getSession())).getPricePerSession();
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
            total+= individualTotal;
            invoice += "Student name: " + student.getLastName() + " " + student.getName() + "\n Total for student: " + individualTotal + "\n";
        }
        invoice += "Total: " + total +"\n";
        return invoice;
    }

    /**
     * it deletes a student based on their ID
     * @param studentID         the id of the student
     * @throws IOException      If no student was found.
     */
    public void removeStudent(Integer studentID) throws IOException {
        try {
    if(students.getAll().stream().noneMatch(st -> st.getId() == studentID)){
            throw new IOException("Invalid student ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        Parent parent = null;
        try {
            parent = parents.get(students.get(studentID).getParent());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        if(parent.getChildren().size()>1){
            parent.getChildren().remove(studentID);
            try {
    parents.update(parent);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        }
        else{
            try {
                parents.remove(parent.getId());
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
        }
        Session session = null;
        try {
            session = sessions.get(students.get(studentID).getSession());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        session.getSessionStudents().remove( studentID);
        try {
    sessions.update(session);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        try {
            students.remove(studentID);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * it deletes a trainer based on their ID
     * @param trainerID         the id of the trainer
     * @throws IOException      If no trainer was found.
     */
    public void removeTrainer(Integer trainerID) throws IOException {
        try {
    if(trainers.getAll().stream().noneMatch(t -> t.getId() == trainerID)){
            throw new IOException("Invalid trainer ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        try {
            trainers.remove(trainerID);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * it deletes a parent based on their ID
     * @param parentID          the id of the Parent
     * @throws IOException      If no parent was found.
     */
    public void removeParent(Integer parentID) throws IOException {
        try {
    if(parents.getAll().stream().noneMatch(pt -> pt.getId() == parentID)){
            throw new IOException("Invalid parent ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        try {
            List<Integer> children = parents.get(parentID).getChildren();
            for(int i=0; i<children.size(); i++){
                students.remove(children.get(i));
            }
            parents.remove(parentID);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * it deletes a session based on their ID
     * @param sessionID         the id of the session
     * @throws IOException      If no session was found.
     */
    public void removeSession(Integer sessionID) throws IOException {
        try {
        if(sessions.getAll().stream().noneMatch(ss -> ss.getId() == sessionID)){
                throw new IOException("Invalid session ID");
            }

        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        try {
            for (int i = 0; i < sessions.get(sessionID).getSessionStudents().size(); i++) {
                Student student = students.get(sessions.get(sessionID).getSessionStudents().get(i));
                student.setSession(0);
                students.update(student);
            }
        }
        catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        try {
            sessions.remove(sessionID);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * it deletes a BeltExam based on their ID
     * @param beltExamID        the id of the BeltExam
     * @throws IOException      If no belt exam was found.
     */
    public void removeBeltExam(Integer beltExamID) throws IOException {
        try {
    if(beltExams.getAll().stream().noneMatch(bt -> bt.getId() == beltExamID)){
            throw new IOException("Invalid belt exam ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        try {
            beltExams.remove(beltExamID);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * it deletes a Contest based on their ID
     * @param contestID         the id of the contest
     * @throws IOException      If no contest was found.
     */
    public void removeContest(Integer contestID) throws IOException {
        try {
    if(contests.getAll().stream().noneMatch(ct -> ct.getId() == contestID)){
            throw new IOException("Invalid contest ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        try {
            contests.remove(contestID);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * it deletes a training camp based on their ID
     * @param trainingCampID    the id of the trainingCamp
     * @throws IOException      If no training camp was found.
     */
    public void removeTrainingCamp(Integer trainingCampID) throws IOException {
        try {
    if(trainingCamps.getAll().stream().noneMatch(tc -> tc.getId() == trainingCampID)){
            throw new IOException("Invalid training camp ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        try {
            trainingCamps.remove(trainingCampID);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * assign a student to a session
     * @param idSession the id of the session
     * @param studentID the id of the Student
     */
    public void addStudentToSession(Integer idSession, Integer studentID){

        Session ss = null; 
try {
    ss = sessions.get(idSession);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        Student st = null; 
try {
    st = students.get(studentID);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        ss.getSessionStudents().add(st.getId());
        try {
    sessions.update(ss);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}

    }

    /**
     * get a Trainer based on their id
     * @param trainerId         the trainer id
     * @return                  object of type Trainer
     * @throws IOException      If no trainer was found.
     */
    public Trainer getTrainerById(int trainerId) throws IOException {
        try {
    if(trainers.getAll().stream().noneMatch(st -> st.getId() == trainerId)){
            throw new IOException("Invalid trainer ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        try {
            return trainers.get(trainerId);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get a Contest based on their id
     * @param       contestID the contest id
     * @return      an object of type Contest
     */
    public Contest getContestById(int contestID){
        try {
            return contests.get(contestID);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get a Training Camp based on their id
     * @param idTrainingCamp    the Training Camp id
     * @return                  an object of type Training Camp
     */
    public TrainingCamp getTrainingCampByIs(int idTrainingCamp){
        try {
            return trainingCamps.get(idTrainingCamp);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get a session based on their id
     * @param sessionId         the session id
     * @return                  an object of type Session
     * @throws IOException      If no session was found.
     */
    public Session getSessionById(int sessionId) throws IOException {
        try {
    if(sessions.getAll().stream().noneMatch(st -> st.getId() == sessionId)){
            throw new IOException("Invalid session ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}
        try {
            return sessions.get(sessionId);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /** display all Students
     *
     * @return a String that holds all students
     */
    public String viewAllStudents(){
        StringBuilder allStudents= new StringBuilder();
        try {
            for(Session s: sessions.getAll()){
                for(int stId: s.getSessionStudents()){
                    allStudents.append(students.get(stId).toString2());
                }
                allStudents.append('\n');
            }
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        return allStudents.toString();
    }

    /**
     * display all Trainers
     * @return a String that holds all trainers
     */
    public String viewAllTrainers(){
        StringBuilder allTrainers= new StringBuilder();
        try {
            for(Trainer t: trainers.getAll()){
                allTrainers.append(t.toString2()).append('\n');
            }
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
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

        try {
            for (Parent p : parents.getAll()) {
                allParents.append(ANSI_RED).append("Parent").append(ANSI_RESET)
                        .append(" with id: ").append(p.getId())
                        .append(", name ").append(p.getName()).append(" ").append(p.getLastName())
                        .append(" has childrens: ");
                for (int sId : p.getChildren()) {
                    Student s = null;
    try {
        s = students.get(sId);
    } catch (DatabaseException e) {
        throw new RuntimeException(e);
    }                allParents.append("\n")
                            .append(ANSI_GREEN).append("Student").append(ANSI_RESET)
                            .append(" with id: ").append(s.getId())
                            .append(" ").append(s.getLastName()).append(" ").append(s.getName());
                }
                allParents.append("\n");
            }
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
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

        try {
            for (Contest c : contests.getAll()) {
                allContests.append(c.toString2()).append('\n');

                for (int sId : c.getStudents()) {
                    allContests.append(students.get(sId).toString3()).append('\n');
                }

                allContests.append('\n');
            }
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        return allContests.toString();
    }

    /**
     * display all Training Camps
     * @return a String that holds all Training Camps
     */
    public String viewTrainingCamps(){
        StringBuilder allTrainingCamps = new StringBuilder();
        try {
            for(TrainingCamp t: trainingCamps.getAll()){
                allTrainingCamps.append(t.toString2()).append('\n');
                for(int sId: t.getStudents()){
                    allTrainingCamps.append(students.get(sId).toString2()).append('\n');
                }
                allTrainingCamps.append('\n');
            }
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        return allTrainingCamps.toString();
    }

    /**
     * display all BeltExams
     * @return a string that holds all BeltExams
     */
    public String viewBeltExams() {
        StringBuilder allBeltExams = new StringBuilder();
        try {
            for (BeltExam b : beltExams.getAll()) {
                allBeltExams.append(b.toString2()).append('\n');
                for (int sId : b.getListOfResults().keySet()) {
                    allBeltExams.append(students.get(sId).toString2()).append('\n');
                }
                allBeltExams.append('\n');
            }
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        return allBeltExams.toString();
    }


    /**
     * Sorts the contests based on their starting date.
     * @return a sorted List of Contests based on ther starting dates
     */
    public List<Contest> sortContestsByDates(){
        List<Contest> sorted = null;
        try {
            sorted = new ArrayList<>(contests.getAll());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
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
        List<BeltExam> sorted = null;
        try {
            sorted = new ArrayList<>(beltExams.getAll());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
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
        List<TrainingCamp> sorted = null;
        try {
            sorted = new ArrayList<>(trainingCamps.getAll());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
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
        List<Session> sorted = null;
        try {
            sorted = new ArrayList<>(sessions.getAll());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        sorted.sort(Comparator.comparingInt(c -> c.getSessionStudents().size()));
        return sorted;
    }

    /**
     * Sorts the students based on their name.
     * @return a list of students ordered alphabetical
     */
    public List<Student> sortStudentsAlphabetical(){
        List<Student> sorted = null;
        try {
            sorted = new ArrayList<>(students.getAll());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        sorted.sort(Comparator.comparing(s -> s.name));
        return sorted;
    }

    /**
     * Sorts the students based on their number of attendances.
     * @return a list of Students ordered by number of Attendances
     */
    public List<Student> sortStudentsByNumberOfAttendences(){
        List<Student> sorted = null;
        try {
            sorted = new ArrayList<>(students.getAll());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        sorted.sort((s1, s2) -> {
            try {
                return Integer.compare(numberOfAttendencesAndAbsences(s1.getId()).get("Attendences"), numberOfAttendencesAndAbsences(s2.getId()).get("Attendences"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return sorted;
    }

    /**
     * Filters the students based on a belt level
     * @param   beltLevel The belt level upon which the filter will apply
     * @return  List of all students having the belt level specified
     */
    public List<Student> filterStudentsByBelt(String beltLevel){
        List<Student> filtered = null;
        try {
            filtered = new ArrayList<>(students.getAll());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        filtered = filtered.stream().filter((s1)->s1.getBeltLevel().equals(beltLevel)).toList();
        return filtered;
    }

    /**
     * Filters parents based of their number of children
     * @param noOfChildren      number of children we want to filter
     * @return                  a list of parents that have noOfChildren as number of children
     * @throws IOException      If noOfChildren is negative.
     */
    public List<Parent> filterParentsNumberOfChildren(int noOfChildren) throws IOException {
        if(noOfChildren<0){
            throw new IOException("Children number can't be negative");
        }
        try {
            return parents.getAll().stream()  // Obținem stream-ul de la lista de părinți
                    .filter(p -> p.getChildren().size() == noOfChildren)  // Filtrăm părinții care au exact numărul de copii dorit
                    .collect(Collectors.toList());  // Colectăm rezultatele într-o listă
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets for a given session the date with the highest attendance and number of participants. It searches through each student's
     * session date list to find the most attended date for all students.
     * @param sessionId         The unique identifier of the session.
     * @return                  A simple entry containing the most attended date and the number of students.
     * @throws IOException      If no session was found.
     */
    public AbstractMap.SimpleEntry<String, Double> getMostProfitableDateForSession(int sessionId) throws IOException {
        try {
    if(sessions.getAll().stream().noneMatch(ss -> ss.getId() == sessionId)){
            throw new IOException("Invalid session ID");
        }

} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        Session session = null; 
try {
    session = sessions.get(sessionId);
} catch (DatabaseException e) {
    throw new RuntimeException(e);
}        Map<String,Double> freqWeekdays = new HashMap<>();
        try {
            for(Student st: students.getAll()){
                if(st.session == sessionId){
                    for(SessionDate sd: st.getSessionDateList()) {
                        if(sd.isAttended()) {
                            if (freqWeekdays.containsKey(sd.getDate())){
                                freqWeekdays.replace(sd.getDate(),freqWeekdays.get(sd.getDate())+sessions.get(sessionId).getPricePerSession());
                            }
                            else{
                                freqWeekdays.put(sd.getDate(),sessions.get(sessionId).getPricePerSession());
                            }
                        }
                    }
                }
            }
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        double max = 0;
        String date = "";
        for(String d: freqWeekdays.keySet()){
            if(freqWeekdays.get(d)>max){
                max = freqWeekdays.get(d);
                date = d;
            }
        }
        return new AbstractMap.SimpleEntry<String,Double>(date,max);
    }

}

