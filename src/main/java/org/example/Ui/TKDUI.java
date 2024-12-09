package org.example.Ui;

import org.example.Controller.TKDController;
import org.example.Exceptions.ValidationException;
import org.example.Model.*;

import java.io.IOException;
import java.util.Scanner;

/**
 * The main UI class that provides a command line interface for interacting with tha TKD management system
 */
public class TKDUI {
    private final TKDController tkdController;
    private final Scanner scanner;

    /**
     * Constructs a new UI with tha given TKD controller
     * @param tkdController the controller that contains the bussines logic for the TKD management system
     */
    public TKDUI(TKDController tkdController) {
        this.tkdController = tkdController;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the UI application displaing a menu and handiling user input
     */
    public void start() {
        boolean continueLoop = true;

        while (continueLoop) {
            printMenu();
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "0" -> continueLoop = false;
                    case "1" -> startStudent();
                    case "2" -> startTrainer();
                    case "3" -> startSession();
                    case "4" -> startContest();
                    case "5" -> startTrainingCamp();
                    case "6" -> startBeltExam();
                    case "7" -> generateBill();
                    case "8" -> startParent();
                    case "9" -> combinationsOfEvents();
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    /**
     * display an interactive menu
     */
    private void printMenu() {
        System.out.println("\n==== TKD Management Console ====");
        System.out.println("1 - Students");
        System.out.println("2 - Trainers");
        System.out.println("3 - Sessions");
        System.out.println("4 - Contests");
        System.out.println("5 - Training camps");
        System.out.println("6 - Belt exams");
        System.out.println("7 - Bill");
        System.out.println("8 - Parents");
        System.out.println("9 - Event combinations");
        System.out.println("0 - Exit");
        System.out.print("Select an option: ");
    }

    /**
     * display all combinations that a parent can afford with a specific amount of money
     */
    private void combinationsOfEvents() throws ValidationException {
        System.out.println("Enter the amount of money: ");
        int amountOfMoney;
        try {
            amountOfMoney = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("amountOfMoney can't be empty");
        }

        System.out.println("Here is a list of events (contests and/or training camps) within the budget range you specified:");
        tkdController.eventsThatdontExceedAmountOfMoney(amountOfMoney);

    }

    /**
     * menu for a student
     */
    private void startStudent(){
        boolean continueLoop = true;

        while (continueLoop) {
            printStudent();
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "0" -> continueLoop = false;
                    case "1" -> addStudent();
                    case "2" -> deleteStudent();
                    case "3" -> viewStudents();
                    case "4" -> changeStudentSession();
                    case "5" -> viewAttendances();
                    case "6" -> sortStudentsByAttend();
                    case "7" -> filterStudentsByBeltLevel();
                    case "8" -> sortStudentsAlphabetical();
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    /**
     * menu for a parent
     */
    private void startParent(){
        boolean continueLoop = true;
        while (continueLoop){
            printParent();
            String option = scanner.nextLine();
            try {
                switch (option) {
                    case "0" -> continueLoop = false;
                    case "1" -> addParent();
                    case "2" -> deleteParent();
                    case "3" -> viewParents();
                    case "4" -> filterParentsByNumberOfChildren();
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    /**
     * menu for a trainer
     */
    private void startTrainer(){
        boolean continueLoop = true;

        while (continueLoop) {
            printTrainer();
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "0" -> continueLoop = false;
                    case "1" -> addTrainer();
                    case "2" -> deleteTrainer();
                    case "3" -> viewTrainers();
                    case "4" -> assignSessionToTrainer();
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    /**
     * menu for a session
     */
    private void startSession(){
        boolean continueLoop = true;

        while (continueLoop) {
            printSession();
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "0" -> continueLoop = false;
                    case "1" -> addSession();
                    case "2" -> deleteSession();
                    case "3" -> changeStudentSession();
                    case "4" -> assignSessionToTrainer();
                    case "5" -> addAttendance();
                    case "6" -> sortSessionByNumberOfParticipants();
                    case "7" -> getDateWithMostStudentsForSession();
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    /**
     * menu for a contest
     */
    private void startContest(){
        boolean continueLoop = true;

        while (continueLoop) {
            printContest();
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "0" -> continueLoop = false;
                    case "1" -> addContest();
                    case "2" -> deleteContest();
                    case "3" -> viewContests();
                    case "4" -> addStudentToContest();
                    case "5" -> sortContestsBuStartDate();
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    /**
     * menu for belt exam
     */
    private void startBeltExam(){
        boolean continueLoop = true;

        while (continueLoop) {
            printBeltExam();
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "0" -> continueLoop = false;
                    case "1" -> addBeltExam();
                    case "2" -> deleteBeltExam();
                    case "3" -> viewBeltExams();
                    case "4" -> addStudentToBeltExam();
                    case "5" -> addResultToBeltExam();
                    case "6" -> sortBeltExamnsByDates();
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    /**
     * menu for a Training camp
     */
    private void startTrainingCamp(){
        boolean continueLoop = true;

        while (continueLoop) {
            printTrainingCamp();
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "0" -> continueLoop = false;
                    case "1" -> addTrainingCamp();
                    case "2" -> deleteTrainingCamp();
                    case "3" -> viewTrainingCamp();
                    case "4" -> addStudentToTrainingCamp();
                    case "5" -> sortedCampsByDates();
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    /**
     * CRUD for parents menu
     */
    private void printParent(){
        System.out.println("\n==== TKD Management Console ====");
        System.out.println("1 - Add Parent");
        System.out.println("2 - Delete Parent");
        System.out.println("3 - View Parents");
        System.out.println("4 - Filter Parents by number of children");
        System.out.println("0 - Exit");
        System.out.print("Select an option: ");
    }

    /**
     * CRUD for Students menu
     */
    private void printStudent(){
        System.out.println("\n==== TKD Management Console ====");
        System.out.println("1 - Add Student");
        System.out.println("2 - Delete Student");
        System.out.println("3 - View Students");
        System.out.println("4 - Change Student Session");
        System.out.println("5 - View Student attendances");
        System.out.println("6 - View sorted students by attendances");
        System.out.println("7 - View students filtered by belt level");
        System.out.println("8 - Sort students ordered alphabetical");
        System.out.println("0 - Exit");
        System.out.print("Select an option: ");
    }

    /**
     * CRUD for Trainers menu
     */
    private void printTrainer(){
        System.out.println("\n==== TKD Management Console ====");
        System.out.println("1 - Add Trainer");
        System.out.println("2 - Delete Trainer");
        System.out.println("3 - View Trainers");
        System.out.println("4 - Assign Session to Trainer");
        System.out.println("0 - Exit");
        System.out.print("Select an option: ");
    }

    /**
     * CRUD for Sessions menu
     */
    private void printSession(){
        System.out.println("\n==== TKD Management Console ====");
        System.out.println("1 - Add Session");
        System.out.println("2 - Delete Session");
        System.out.println("3 - Change Student Session");
        System.out.println("4 - Assign Session to Trainer");
        System.out.println("5 - Add attendance");
        System.out.println("6 - Sort session by number of participants");
        System.out.println("7 - Get date with the most students that attended a given session");
        System.out.println("0 - Exit");
        System.out.print("Select an option: ");
    }

    /**
     * CRUD for Contests menu
     */
    private void printContest(){
        System.out.println("\n==== TKD Management Console ====");
        System.out.println("1 - Add Contest");
        System.out.println("2 - Delete Contest");
        System.out.println("3 - View all Contests");
        System.out.println("4 - Add student to contest");
        System.out.println("5 - View sorted contests by start date");
        System.out.println("0 - Exit");
        System.out.print("Select an option: ");
    }

    /**
     * CRUD for TrainingCamps menu
     */
    private void printTrainingCamp(){
        System.out.println("\n==== TKD Management Console ====");
        System.out.println("1 - Add Training camp");
        System.out.println("2 - Delete training camp");
        System.out.println("3 - View all training camps");
        System.out.println("4 - Add student to training camp");
        System.out.println("5 - Sorted Camps by starting dates");
        System.out.println("0 - Exit");
        System.out.print("Select an option: ");
    }

    /**
     * CRUD for BeltExams menu
     */
    private void printBeltExam(){
        System.out.println("\n==== TKD Management Console ====");
        System.out.println("1 - Add Belt Exam");
        System.out.println("2 - Delete Belt Exam");
        System.out.println("3 - View all Belt Exams");
        System.out.println("4 - Add student to Belt Exam");
        System.out.println("5 - Add result to Belt Exam");
        System.out.println("6 - Sort Belt Examines by starting dates");
        System.out.println("0 - Exit");
        System.out.print("Select an option: ");
    }

    // Methods for each option

    /**
     * request all information that a student need.  If it catches an exception it calls the function again.
     */
    private void addStudent() throws ValidationException, IOException {
        System.out.print("Enter student ID: ");
        int idStudent;
        try {
            idStudent = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("Student ID can't be empty");
        }
        if (idStudent == 0 || idStudent < 0) {
            throw new ValidationException("student ID cannot be null or negative");
        }

        System.out.print("Enter  student first name: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            throw new ValidationException("student first name cannot be null or negative");
        }


        System.out.print("Enter  student last name: ");
        String lastName = scanner.nextLine();
        if (lastName.isEmpty()) {
            throw new ValidationException("student last name cannot be null or negative");
        }

        System.out.print("Enter  student email: ");
        String email = scanner.nextLine();
        if (email.isEmpty()) {
            throw new ValidationException("student email cannot be null or negative");
        }


        System.out.print("Enter  student address: ");
        String address = scanner.nextLine();
        if (address.isEmpty()) {
            throw new ValidationException("student address cannot be null or negative");
        }


        System.out.print("Enter student year of birth: ");
        int dateOfBirth;
        try {
            dateOfBirth = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("dateOfBirth can't be empty");
        }

        if (dateOfBirth == 0 || dateOfBirth < 0) {
            throw new ValidationException("student year of birth cannot be null or negative");
        }

        System.out.print("Enter  student telephone number: ");
        String telNumber = scanner.nextLine();
        if (telNumber.isEmpty()) {
            throw new ValidationException("student telephone number cannot be null or negative");
        }


        System.out.print("Enter  student belt level: ");
        String beltLevel = scanner.nextLine();
        if (beltLevel.isEmpty()) {
            throw new ValidationException("student belt level cannot be null or negative");
        }


        System.out.print("Enter session ID: ");
        int sessionId;
        try {
            sessionId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("sessionId can't be empty");
        }

        if (sessionId == 0 || sessionId < 0) {
            throw new ValidationException("session ID cannot be null or negative");
        }

        Session session = null;
        session = tkdController.getSessionById(sessionId);
        System.out.print("Do you want to add a parent to the student? (yes/no) -> if no a person to contact will be necesary: ");
        String parentDecision = scanner.nextLine().trim().toLowerCase();
        Student student = new Student(idStudent, name, lastName, email, address, dateOfBirth, telNumber, beltLevel, session.getId());
        tkdController.addObject(student);
//        Parent parent = null;
//        if (parentDecision.equals("yes")) {
//            parent = addParent();
//        }else if(parentDecision.equals("no")) {
//            System.out.println("You have to enter a person to contact in emergency case, it ca be the same person: ");
//            parent = addParent();
//        }
//        System.out.println("caca");
//        Parent p1 = parent;
        System.out.println("Every student needs a parent or a contact person:");
        Parent parent = addParent();
//        tkdController.addObject(parent);


        //call addStudentToParent
        tkdController.addStudentToParent(student, parent);

        // call addStudentToSession function
        tkdController.addStudentToSession(sessionId, idStudent);

        //tkdController.addStudent(student);

        System.out.println("Student added successfully.");
    }

    /**
     * request all information that a parent need
     * @return a Parent object
     */
    private Parent addParent() throws ValidationException {
        System.out.print("Enter parent ID: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("id can't be empty");
        }

        if(id == 0 || id < 0){
            throw new ValidationException("parent ID cannot be null or negative");
        }

        System.out.print("Enter  parent first name: ");
        String name = scanner.nextLine();
        if(name.isEmpty()){
            throw new ValidationException("parent first name cannot be null or negative");}

        System.out.print("Enter  parent last name: ");
        String lastName = scanner.nextLine();
        if(lastName.isEmpty()){
            throw new ValidationException("parent last name cannot be null or negative");}

        System.out.print("Enter  parent email: ");
        String email = scanner.nextLine();
        if(email.isEmpty()){
            throw new ValidationException("parent email cannot be null or negative");}

        System.out.print("Enter  parent address: ");
        String address = scanner.nextLine();
        if(address.isEmpty()){
            throw new ValidationException("parent address cannot be null or negative");}

        System.out.print("Enter parent year of birth: ");
        int dateOfBirth;
        try {
            dateOfBirth = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("dateOfBirth can't be empty");
        }

        if(dateOfBirth == 0 || dateOfBirth < 0){
            throw new ValidationException("parent year of birth cannot be null or negative");
        }

        System.out.print("Enter  parent number: ");
        String number = scanner.nextLine();
        if(number.isEmpty()){
            throw new ValidationException("parent number cannot be null or negative");}

        Parent newParent = new Parent(id, name, lastName, email, address, dateOfBirth,number);
//        tkdController.addObject(newParent);
        return newParent;
    }

    /**
     * request all information that a session needs.  If it catches an exception it calls the function again.
     */
    private void addSession() throws ValidationException {
        System.out.print("Enter Session ID: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("id can't be empty");
        }

        if(id == 0 || id < 0){
            throw new ValidationException("Session ID cannot be null or negative");
        }

        System.out.print("Enter Difficulty Level (beginner, intermediary, advanced): ");
        DifficultyLevel difficultyLevel;
        try {
            difficultyLevel = DifficultyLevel.valueOf(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid difficulty level. Please enter beginner, intermediary, advanced.");
            difficultyLevel = DifficultyLevel.valueOf(scanner.nextLine());
        }

        System.out.print("Enter Maximum Number of Participants: ");
        int maximumParticipants;
        try {
            maximumParticipants = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("maximumParticipants can't be empty");
        }

        if(maximumParticipants == 0 || maximumParticipants < 0){
            throw new ValidationException("Maximum Number of Participants cannot be null or negative");
        }

        System.out.print("Enter Trainer ID for this Session: ");
        int trainerId;
        try {
            trainerId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("trainerId can't be empty");
        }

        if(trainerId == 0 || trainerId < 0){
            throw new ValidationException("Trainer ID for this Session cannot be null or negative");
        }

        Trainer trainer = null;
        try {
            trainer = tkdController.getTrainerById(trainerId);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            addSession();
        }
//        if (trainer == null) {
//            System.out.println("Trainer not found. Please check the trainer ID.");
//            return;
//        }

        System.out.print("Enter Price Per Session: ");
        double pricePerSession = Double.parseDouble(scanner.nextLine());

        // Crearea obiectului Session
        Session session = new Session(id, difficultyLevel, maximumParticipants, trainer.getId(), pricePerSession);
        try {
            tkdController.addObject(session);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            addSession();
        }
        System.out.println("Session added successfully.");
    }

    /**
     * request all information that a trainer need.  If it catches an exception it calls the function again.
     */
    private void addTrainer() throws ValidationException {
        System.out.print("Enter Trainer ID: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("id can't be empty");
        }

        if(id == 0 || id < 0){
            throw new ValidationException("Trainer ID cannot be null or negative");
        }

        System.out.print("Enter  Trainer First Name: ");
        String name = scanner.nextLine();
        if(name.isEmpty()){
            throw new ValidationException("Trainer First Name cannot be null or negative");}

        System.out.print("Enter  Trainer Last Name: ");
        String lastName = scanner.nextLine();
        if(lastName.isEmpty()){
            throw new ValidationException("Trainer Last Name cannot be null or negative");}

        System.out.print("Enter  Trainer Email: ");
        String email = scanner.nextLine();
        if(email.isEmpty()){
            throw new ValidationException("Trainer Email cannot be null or negative");}

        System.out.print("Enter  Trainer Address: ");
        String address = scanner.nextLine();
        if(address.isEmpty()){
            throw new ValidationException("Trainer Address cannot be null or negative");}

        System.out.print("Enter Trainer Year of Birth: ");
        int dateOfBirth;
        try {
            dateOfBirth = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("dateOfBirth can't be empty");
        }

        if(dateOfBirth == 0 || dateOfBirth < 0){
            throw new ValidationException("Trainer Year of Birth cannot be null or negative");
        }

        System.out.print("Enter  Trainer Telephone Number: ");
        String number = scanner.nextLine();
        if(number.isEmpty()){
            throw new ValidationException("Trainer Telephone Number cannot be null or negative");}

        System.out.print("Enter  Trainer Belt Level: ");
        String beltLevel = scanner.nextLine();
        if(beltLevel.isEmpty()){
            throw new ValidationException("Trainer Belt Level cannot be null or negative");}

        Trainer newTrainer = new Trainer(id, name, lastName, email, address, dateOfBirth, number, beltLevel);

        try {
            tkdController.addObject(newTrainer);
        } catch (IOException e) {
            System.out.println("Error: "+ e.getMessage());
            addTrainer();
        }
        System.out.println("Trainer added successfully.");
    }
    /**
     * request all information that a contest need. If it catches an exception it calls the function again.
     */
    private void addContest() throws ValidationException {
        System.out.println("Enter Contest Id: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("id can't be empty");
        }


        System.out.print("Enter   start date: ");
        String startdate = scanner.nextLine();
        if(startdate.isEmpty()){
            throw new ValidationException(" start date cannot be null or negative");}

        System.out.print("Enter   end date: ");
        String enddate = scanner.nextLine();
        if(enddate.isEmpty()){
            throw new ValidationException(" end date cannot be null or negative");}

        System.out.println("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter   country: ");
        String country = scanner.nextLine();
        if(country.isEmpty()){
            throw new ValidationException(" country cannot be null or negative");}

        System.out.print("Enter   city: ");
        String city = scanner.nextLine();
        if(city.isEmpty()){
            throw new ValidationException(" city cannot be null or negative");}
        System.out.print("Enter   address: ");
        String address = scanner.nextLine();

        if(address.isEmpty()){
            throw new ValidationException("address cannot be null or negative");}

        System.out.print("Enter   name: ");
        String name = scanner.nextLine();
        if(name.isEmpty()){
            throw new ValidationException(" name cannot be null or negative");}

        Contest contest = new Contest(id,startdate,enddate,price, country, city, name,address);
        try {
            tkdController.addObject(contest);
        } catch (IOException e) {
            System.out.println("Error: "+ e.getMessage());
            addContest();
        }
        System.out.println("Contest added successfully.");
    }

    /**
     * request all information that a training camp need. If it catches an exception it calls the function again.
     */
    private void addTrainingCamp() throws ValidationException {
        System.out.println("Enter Training camp Id: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("id can't be empty");
        }


        System.out.print("Enter   start date: ");
        String startdate = scanner.nextLine();
        if(startdate.isEmpty()){
            throw new ValidationException(" start date cannot be null or negative");}


        System.out.print("Enter   end date: ");
        String enddate = scanner.nextLine();

        if(enddate.isEmpty()){
            throw new ValidationException("end date cannot be null or negative");}

        System.out.println("Enter price date: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter   country: ");
        String country = scanner.nextLine();
        if(country.isEmpty()){
            throw new ValidationException(" country cannot be null or negative");}


        System.out.print("Enter   city: ");
        String city = scanner.nextLine();
        if(city.isEmpty()){
            throw new ValidationException(" city cannot be null or negative");}


        System.out.print("Enter   address: ");
        String address = scanner.nextLine();
        if(address.isEmpty()){
            throw new ValidationException(" address cannot be null or negative");}

        System.out.println("Enter max number of students: ");
        int numberOfParticipants;
        try {
            numberOfParticipants = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("numberOfParticipants can't be empty");
        }

        TrainingCamp trainingCamp = new TrainingCamp(id,startdate,enddate,price, country, city, address, numberOfParticipants);
        try {
            tkdController.addObject(trainingCamp);
        } catch (IOException e) {
            System.out.println("Error: "+ e.getMessage());
            addTrainingCamp();
        }
        System.out.println("Training camp added successfully.");
    }

    /**
     * request all information that a BeltExam need. If it catches an exception it calls the function again.
     */
    private void addBeltExam() throws ValidationException {
        System.out.println("Enter Belt Exam Id: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("id can't be empty");
        }


        System.out.print("Enter   start date: ");
        String startdate = scanner.nextLine();
        if(startdate.isEmpty()){
            throw new ValidationException(" start date cannot be null or negative");}

        System.out.print("Enter   end date: ");
        String enddate = scanner.nextLine();
        if(enddate.isEmpty()){
            throw new ValidationException(" end date cannot be null or negative");}


        System.out.println("Enter price date: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter   country: ");
        String country = scanner.nextLine();
        if(country.isEmpty()){
            throw new ValidationException(" country cannot be null or negative");}

        System.out.print("Enter   city: ");
        String city = scanner.nextLine();
        if(city.isEmpty()){
            throw new ValidationException(" city cannot be null or negative");}


        System.out.print("Enter   address: ");
        String address = scanner.nextLine();
        if(address.isEmpty()){
            throw new ValidationException(" address cannot be null or negative");}


        System.out.print("Enter   belt color: ");
        String beltColor = scanner.nextLine();
        if(beltColor.isEmpty()){
            throw new ValidationException(" belt color cannot be null or negative");}

        BeltExam beltExam = new BeltExam(id,startdate,enddate,price, country, city, address, beltColor);
        try {
            tkdController.addObject(beltExam);
        } catch (IOException e) {
            System.out.println("Error: "+ e.getMessage());
            addBeltExam();
        }
        System.out.println("Belt Exam added successfully.");
    }

    /**
     * request all information that a belt exam need. If it catches an exception it calls the function again.
     *
     */
    private void addStudentToBeltExam() throws ValidationException {
        System.out.println("Enter Belt Exam Id: ");
        int idBeltExam;
        try {
            idBeltExam = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("idBeltExam can't be empty");
        }


        System.out.println("Enter Student Id: ");
        int studentId;
        try {
            studentId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("studentId can't be empty");
        }


        try {
            tkdController.addStudentToBeltExam(idBeltExam,studentId);
        } catch (IOException e) {
            System.out.println("Error: "+ e.getMessage());
            addStudentToBeltExam();
        }
        System.out.println("Student added successfully.");
    }

    /**
     * add a student to contest based on their ID s. If it catches an exception it calls the function again.
     */
    private void addStudentToContest() throws ValidationException {
        System.out.println("Enter Contest Id: ");
        int idContest;
        try {
            idContest = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("idContest can't be empty");
        }


        System.out.println("Enter Student Id: ");
        int studentId;
        try {
            studentId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("studentId can't be empty");
        }


        try {
            tkdController.addStudentToContest(studentId,idContest);
        } catch (IOException e) {
            System.out.println("Error: "+ e.getMessage());
            addStudentToContest();
        }
        System.out.println("Student added successfully.");
    }

    /**
     * add a student to a trianing camp based on their ID s. If it catches an exception it calls the function again.
     */
    private void addStudentToTrainingCamp() throws ValidationException {
        System.out.println("Enter Student Id: ");
        int idStudent;
        try {
            idStudent = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("idStudent can't be empty");
        }


        System.out.println("Enter Training Camp Id: ");
        int idTrainingCamp;
        try {
            idTrainingCamp = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValidationException("idTrainingCamp can't be empty");
        }


        try {
            tkdController.addStudentToTrainingCamp(idStudent,idTrainingCamp);
        } catch (IOException e) {
            System.out.println("Error: "+ e.getMessage());
            addStudentToTrainingCamp();
        }
        System.out.println("Student added successfully.");
    }


//    private void promoteBeltExam() throws IOException {
//        System.out.println("Enter Belt Exam Id: ");
//        int idBeltExam = Integer.parseInt(scanner.nextLine());
//
//        tkdController.changeBeltLevel(idBeltExam);
//        System.out.println("Belt Exams Results changed successfully.");
//    }

    /**
     * deletes a student base on ID. If it catches an exception it calls the function again.
     */
    private void deleteStudent() {
        int id = readStudentId();
        try {
            tkdController.deleteStudent(id);
        }
        catch (IOException e){
            System.out.println("Error: "+ e.getMessage());
            deleteStudent();
        }
        System.out.println("Student deleted successfully.");
    }

    /**
     * deletes a belt exam base on ID. If it catches an exception it calls the function again.
     */
    private void deleteBeltExam() {
        int id = readBeltExamId();
        try{
            tkdController.deleteBeltExam(id);
        }
        catch (IOException e){
            System.out.println("Error: "+ e.getMessage());
            deleteBeltExam();
        }
        System.out.println("Belt Exam deleted successfully.");
    }

    /**
     * deletes a training camp base on ID. If it catches an exception it calls the function again.
     */
    private void deleteTrainingCamp() {
        int id = readTrainingCampId();
        try {
            tkdController.deleteTrainingCamp(id);
        }
        catch (IOException e){
            System.out.println("Error: "+ e.getMessage());
            deleteTrainingCamp();
        }
        System.out.println("Training camp deleted successfully.");
    }

    /**
     * deletes a parent base on ID. If it catches an exception it calls the function again.
     */
    private void deleteParent(){
        int id = readParentId();
        try {
            tkdController.deleteParent(id);
        }
        catch (IOException e){
            System.out.println("Error: "+ e.getMessage());
            deleteParent();
        }
        System.out.println("Parent deleted successfully.");
    }

    /**
     * deletes a trainer base on ID. If it catches an exception it calls the function again.
     */
    private void deleteTrainer() {
        int id = readTrainerId();
        try {
            tkdController.deleteTrainer(id);
        }
        catch (IOException e){
            System.out.println("Error: "+ e.getMessage());
            deleteTrainer();
        }
        System.out.println("Trainer deleted successfully.");
    }

    /**
     * deletes a session base on ID. If it catches an exception it calls the function again.
     */
    private void deleteSession() {
        int id = readSessionId();
        try {
            tkdController.deleteSession(id);
        }
        catch (IOException e){
            System.out.println("Error: "+ e.getMessage());
            deleteSession();
        }
        System.out.println("Session deleted successfully.");
    }

    /**
     * deletes a contest base on ID. If it catches an exception it calls the function again.
     */
    private void deleteContest() {
        int id = readContestId();
        try {
            tkdController.deleteContest(id);
        }
        catch (IOException e){
            System.out.println("Error: "+ e.getMessage());
            deleteContest();
        }
        System.out.println("Contest deleted successfully.");
    }

    /**
     * displays a list of trainers
     */
    private void viewTrainers() {
        System.out.println("=== List of Trainers ===");
        tkdController.viewTrainers();
    }

    /**
     * displays a list of parents
     */
    private void viewParents(){
        System.out.println("=== List of Parents ===");
        tkdController.viewParents();
    }

    /**
     * displays a list of contests
     */
    private void viewContests(){
        System.out.println("=== List of Contests ===");
        tkdController.viewContests();
    }

    /**
     * displays a list of training camps
     */
    private void viewTrainingCamp(){
        System.out.println("=== List of Training camps ===");
        tkdController.viewTrainingCamps();
    }

    /**
     * displays a list of students
     */
    private void viewStudents() {
        System.out.println("=== List of Students ===");
        tkdController.viewStudents();
    }

    /**
     * displays a list of belt exams
     */
    private void viewBeltExams(){
        System.out.println("=== List of Belt Exams ===");
        tkdController.viewBeltExams();
    }

    /**
     * displays a list of attendances for a student. If it catches an exception it calls the function again.
     */
    private void viewAttendances(){
        try {
            int studentId = readStudentId();
            System.out.println("=== List of attendances ===");
            tkdController.numberOfAttendances(studentId);
        }
        catch (IOException e){
            System.out.println("Error: "+ e.getMessage());
            viewAttendances();
        }
    }



    /**
     * Changes the session of a trainer, by reading the new session id and the trainer id from the console, and displaying a successful message.
     * If it catches an exception it calls the function again.
     */
    private void assignSessionToTrainer() throws IOException {
        try {
            int sessionId = readSessionId();
            int trainerId = readTrainerId();
            tkdController.assignSessionToTrainer(sessionId, trainerId);
            System.out.println("Session assigned to trainer successfully.");
        }
        catch (IOException e){
            System.out.println("Error: "+ e.getMessage());
            assignSessionToTrainer();
        }
    }
    /**
     * Adds an attendance to a student, by reading the session id, the student id, if he attended or not, the weekday and the date from the console.
     * If it catches an exception it calls the function again.
     */
    private void addAttendance() throws ValidationException {
        int sessionId = readSessionId();
        int studentId = readStudentId();

        System.out.print("Enter attendance(true or false): ");
        boolean attendance = Boolean.parseBoolean(scanner.nextLine());

        System.out.print("Enter  week day: ");
        String weekday = scanner.nextLine();
        if(weekday.isEmpty()){
            throw new ValidationException("week day cannot be null or negative");}

        System.out.print("Enter  date: ");
        String date = scanner.nextLine();
        if(date.isEmpty()){
            throw new ValidationException("date cannot be null or negative");}
        try {
            tkdController.addAttendance(studentId,sessionId, attendance, weekday, date);
        } catch (IOException e) {
            System.out.println("Error: "+ e.getMessage());
            addAttendance();
        }
    }

    /**
     * Changes the session of a student, by reading the new session id and the student id from the console, and displaying a successful message.
     * If it catches an exception it calls the function again.
     */
    private void changeStudentSession() {
        try {
            int studentId = readStudentId();
            int sessionId = readSessionId();
            tkdController.changeStudentSession(studentId, sessionId);
            System.out.println("Student session changed successfully.");
        }
        catch (IOException e){
            System.out.println("Error: "+ e.getMessage());
            changeStudentSession();
        }
    }

    /**
     * Adds result for a student from the belt exam, by reading the student id, belt exam id and the result from the console and displaying it.
     * If it catches an exception it calls the function again.
     */
    private void addResultToBeltExam(){
        try {
            int studentId = readStudentId();
            int beltExamId = readBeltExamId();
            System.out.println("Result(true/false): ");
            boolean promoted = Boolean.parseBoolean(scanner.nextLine());
            tkdController.addResultToBeltExam(studentId, beltExamId, promoted);
        }
        catch (IOException e){
            System.out.println("Error: "+ e.getMessage());
            addResultToBeltExam();
        }
    }

    /**
     * Generates a bill for a parent and displays it in the console. If it catches an exception it calls the function again.
     */
    private void generateBill() throws ValidationException {
        int parentId = readParentId();

        System.out.print("Enter   the month for the invoice: ");
        String month = scanner.nextLine();
        if(month.isEmpty()){
            throw new ValidationException(" the month for the invoice cannot be null or negative");}

        try {
            tkdController.generateInvoice(parentId, month);
        }
        catch (IOException e){
            System.out.println("Error: "+ e.getMessage());
            generateBill();
        }
    }

    /**
     * Reads an id for a student from the console.
     * @return The id of the student.
     */
    private int readStudentId() {
        System.out.print("Enter student ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Reads an id for a parent from the console.
     * @return The id of the parent.
     */
    private int readParentId(){
        System.out.print("Enter parent ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Reads an id for a trainer from the console.
     * @return The id of the trainer.
     */
    private int readTrainerId() {
        System.out.print("Enter trainer ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Reads an id for a session from the console.
     * @return The id of the session.
     */
    private int readSessionId() {
        System.out.print("Enter session ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Reads an id for a contest from the console.
     * @return The id of the contest.
     */
    private int readContestId() {
        System.out.print("Enter contest ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Reads an id for a training camp from the console.
     * @return The id of the training camp.
     */
    private int readTrainingCampId() {
        System.out.print("Enter training camp ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Reads an id for a belt exam from the console.
     * @return The id of the belt exam.
     */
    private int readBeltExamId() {
        System.out.print("Enter belt exam ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * call the sortedContest function from Controller that prints out the contests sorted by their start date
     */
    private void sortContestsBuStartDate(){
        tkdController.sortedContests();
    }

    /**
     * call the sortedStudentsByAttend function from Controller that prints out the students sorted by their number of attendances
     */
    private void sortStudentsByAttend(){
        tkdController.sortedStudentsByAttend();
    }

    /**
     * call the filter students function from Controller that prints out the students filtered by a belt level read from the console
     */
    private void filterStudentsByBeltLevel() throws ValidationException {
        System.out.print("Enter   the belt level for filtering: ");
        String beltLevel = scanner.nextLine();
        if(beltLevel.isEmpty()){
            throw new ValidationException(" the belt level for filtering cannot be null or negative");}

        tkdController.filteredStudentsByBeltLevel(beltLevel);
    }

    /**
     * call the filter parents function from Controller that prints out the parents filtered by a number of children read from the console.
     * If it catches an exception it calls the function again.
     */
    private void filterParentsByNumberOfChildren(){
        System.out.println("Enter the number of children for filtering: ");
        Integer numberOfChildren = scanner.nextInt();

        try{
            tkdController.filterParentsByNumberOfChildren(numberOfChildren);
        }
        catch (IOException e){
            System.out.println("Error: "+ e.getMessage());
            filterParentsByNumberOfChildren();
        }
    }

    /**
     * calls the sortStudentsAlphabetical function in the controller
     */
    private void sortStudentsAlphabetical(){
        tkdController.sortStudentsAlphabetical();
    }

    /**
     * calls the sortSessionByNumberOfParticipants function in the controller
     */
    private void sortSessionByNumberOfParticipants(){
        tkdController.sortSessionByNumberOfParticipants();
    }

    /**
     * calls the sortedCampsByDates function from controller
     */
    private void sortedCampsByDates(){
        tkdController.sortedCampsByDates();
    }

    /**
     * calls the sortBeltExamsByDates function from controller
     */
    private void sortBeltExamnsByDates(){
        tkdController.sortBeltExamnsByDates();
    }

    /**
     * Reads a session id and gives it as parameter to the getDateWithMostStudentsForSession function from controller.
     * If it catches an exception it calls the function again.
     */
    public void getDateWithMostStudentsForSession(){
        int sessionId = readSessionId();
        try {
            tkdController.getDateWithMostStudentsForSession(sessionId);
        }
        catch (IOException e){
            System.out.println("Error: "+ e.getMessage());
            getDateWithMostStudentsForSession();
        }
    }
}
