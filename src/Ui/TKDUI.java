package Ui;

import Controller.TKDController;
import Model.*;
import Service.TKD_Service;

import java.io.IOException;
import java.util.Scanner;

public class TKDUI {
    private final TKDController tkdController;
    private final Scanner scanner;

    public TKDUI(TKDController tkdController) {
        this.tkdController = tkdController;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean continueLoop = true;

        while (continueLoop) {
            printMenu();
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "0" -> continueLoop = false;
                    case "1" -> addStudent();
                    case "2" -> deleteStudent();
                    case "3" -> viewStudents();
                    case "4" -> addTrainer();
                    case "5" -> deleteTrainer();
                    //case "6" -> viewTrainers();
                    case "7" -> addSession();
                    case "8" -> deleteSession();
                    case "9" -> assignSessionToTrainer();
                    case "10" -> changeStudentSession();
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\n==== TKD Management Console ====");
        System.out.println("1 - Add Student");
        System.out.println("2 - Delete Student");
        System.out.println("3 - View Students");
        System.out.println("4 - Add Trainer");
        System.out.println("5 - Delete Trainer");
        System.out.println("6 - View Trainers");
        System.out.println("7 - Add Session");
        System.out.println("8 - Delete Session");
        System.out.println("9 - Assign Session to Trainer");
        System.out.println("10 - Change Student Session");
        System.out.println("0 - Exit");
        System.out.print("Select an option: ");
    }

    // Methods for each option
    private void addStudent() throws IOException {
        System.out.print("Enter student ID: ");
        int idStudent = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter student first name: ");
        String name = scanner.nextLine();

        System.out.print("Enter student last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter student email: ");
        String email = scanner.nextLine();

        System.out.print("Enter student address: ");
        String address = scanner.nextLine();

        System.out.print("Enter student year of birth: ");
        int dateOfBirth = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter student telephone number: ");
        String telNumber = scanner.nextLine();

        System.out.print("Enter student belt level: ");
        String beltLevel = scanner.nextLine();

        System.out.print("Enter session ID: ");
        int sessionId = Integer.parseInt(scanner.nextLine());
        Session session = tkdController.getSessionById(sessionId);

        System.out.print("Do you want to add a parent to the student? (yes/no) -> if no a person to contact will be necesary: ");
        String parentDecision = scanner.nextLine().trim().toLowerCase();

        Parent parent = null;
        if (parentDecision.equals("yes")) {
            parent = addParent();
        }else if(parentDecision.equals("no")) {
            System.out.println("You have to enter a person to contact in emergency case, it ca be the same person: ");
            parent = addParent();
        }

        tkdController.addObject(parent);

        Student student = new Student(idStudent, name, lastName, email, address, dateOfBirth, telNumber, beltLevel, session);

        tkdController.addObject(student);

        //call addStudentToParent
        tkdController.addStudentToParent(student, parent);

        // call addStudentToSession function
        tkdController.addStudentToSession(sessionId,idStudent);

        //tkdController.addStudent(student);

        System.out.println("Student added successfully.");
    }

    private Parent addParent() throws IOException {
        System.out.print("Enter parent ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter parent first name: ");
        String name = scanner.nextLine();

        System.out.print("Enter parent last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter parent email: ");
        String email = scanner.nextLine();

        System.out.print("Enter parent address: ");
        String address = scanner.nextLine();

        System.out.print("Enter parent year of birth: ");
        int dateOfBirth = Integer.parseInt(scanner.nextLine());

        Parent newParent = new Parent(id, name, lastName, email, address, dateOfBirth);
        tkdController.addObject(newParent);
        return newParent;
    }

    private void addSession() throws IOException {
        System.out.print("Enter Session ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Difficulty Level (EASY, MEDIUM, HARD): ");
        DifficultyLevel difficultyLevel;
        try {
            difficultyLevel = DifficultyLevel.valueOf(scanner.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid difficulty level. Please enter EASY, MEDIUM, or HARD.");
            return;
        }

        System.out.print("Enter Maximum Number of Participants: ");
        int maximumParticipants = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Trainer ID for this Session: ");
        int trainerId = Integer.parseInt(scanner.nextLine());

        Trainer trainer = tkdController.getTrainerById(trainerId);
        if (trainer == null) {
            System.out.println("Trainer not found. Please check the trainer ID.");
            return;
        }

        System.out.print("Enter Price Per Session: ");
        double pricePerSession = Double.parseDouble(scanner.nextLine());

        // Crearea obiectului Session
        Session session = new Session(id, difficultyLevel, maximumParticipants, trainer, pricePerSession);
        tkdController.addObject(session);
        System.out.println("Session added successfully.");
    }

    private void addTrainer() throws IOException{
        System.out.print("Enter Trainer ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Trainer First Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Trainer Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter Trainer Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Trainer Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter Trainer Year of Birth: ");
        int dateOfBirth = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Trainer Telephone Number: ");
        String number = scanner.nextLine();

        System.out.print("Enter Trainer Belt Level: ");
        String beltLevel = scanner.nextLine();

        Trainer newTrainer = new Trainer(id, name, lastName, email, address, dateOfBirth, number, beltLevel);
        tkdController.addObject(newTrainer);
        System.out.println("Trainer added successfully.");
    }



    private void deleteStudent() {
        int id = readStudentId();
        tkdController.deleteStudent(id);
        System.out.println("Student deleted successfully.");
    }

    private void viewStudents() {
        System.out.println("=== List of Students ===");
        tkdController.viewStudents();
    }



    private void deleteTrainer() {
        int id = readTrainerId();
        tkdController.deleteTrainer(id);
        System.out.println("Trainer deleted successfully.");
    }

//    private void viewTrainers() {
//        System.out.println("=== List of Trainers ===");
//        for (Trainer trainer : tkdController.viewTrainers()) {
//            System.out.println(trainer);
//        }
//    }


    private void deleteSession() {
        int id = readSessionId();
        tkdController.deleteSession(id);
        System.out.println("Session deleted successfully.");
    }

    private void assignSessionToTrainer() {
        int sessionId = readSessionId();
        int trainerId = readTrainerId();
        tkdController.assignSessionToTrainer(sessionId, trainerId);
        System.out.println("Session assigned to trainer successfully.");
    }

    private void changeStudentSession() {
        int studentId = readStudentId();
        int sessionId = readSessionId();
        tkdController.changeStudentSession(studentId, sessionId);
        System.out.println("Student session changed successfully.");
    }

    // Helper methods to read IDs from the user
    private int readStudentId() {
        System.out.print("Enter student ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private int readTrainerId() {
        System.out.print("Enter trainer ID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private int readSessionId() {
        System.out.print("Enter session ID: ");
        return Integer.parseInt(scanner.nextLine());
    }
}
