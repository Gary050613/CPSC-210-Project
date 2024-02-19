package ui;

import error.AlreadyInClass;
import error.NoSubmission;
import model.*;
import model.Class;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Canvos {
    private DataBase db;
    private Scanner scan;

    public Canvos() {
        runSystem();
    }

    // MODIFIES: this
    // EFFECTS: Initializes Canvos system
    private void init() {
        db = new DataBase();
        scan = new Scanner(System.in);
        scan.useDelimiter("\n");

        db.createClass("CPSC 110", "Gregor");
        db.createClass("CPSC 121", "Karina");
        db.createTeacher("Teacher", "pw");
        db.createStudent("Student", "pw");
        db.createStudent("Tom", "pw");
        db.createTA("TA", "pw");
    }

    // EFFECTS: Runs the system
    private void runSystem() {
        boolean active = true;
        String command = null;

        init();

        while (active) {
            displayMenu();
            command = scan.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                active = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Convos terminated.");
    }

    // EFFECTS: Displays menu of login options
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tt -> Teacher Login");
        System.out.println("\ts -> Student Login");
        System.out.println("\ta -> TA Login");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: Maps the input commands to the corresponding functions
    private void processCommand(String command) {
        if (command.equals("t")) {
            runTeacher();
        } else if (command.equals("s")) {
//            runStudent();
        } else if (command.equals("a")) {
//            runTA();
        } else {
            System.out.println("Command Invalid.");
        }
    }

    // EFFECTS: Presents the list of classes and provides function to choose
    private Class selectClass() {
        for (int i = 0;i < db.getClasses().size();i++) {
            System.out.print(db.getClasses().get(i).getCourseName());
            System.out.print(" [" + i + "]  ");
        }
        System.out.println();
        System.out.println("Please select the class: (Select out of bound to go back)");
        int classSelection = scan.nextInt();
        if (classSelection < 0 || classSelection >= db.getClasses().size()) {
            return null;
        }
        return db.getClasses().get(classSelection);
    }

    // EFFECTS: Presents the list of students and provides function to choose
    private Student selectStudent() {
        for (int i = 0;i < db.getStudents().size();i++) {
            System.out.print(db.getStudents().get(i).getUserName());
            System.out.print(" [" + i + "]  ");
        }
        System.out.println();
        System.out.println("Please select the student: (Select our of bound to go back)");
        int studentSelection = scan.nextInt();
        if (studentSelection < 0 || studentSelection >= db.getStudents().size()) {
            return null;
        }
        return db.getStudents().get(studentSelection);
    }

    // EFFECTS: Presents the list of assignments for a class and provides function to choose
    private Assignment selectAssignment(Class clas) {
        for (int i = 0;i < clas.getListOfAssignments().size();i++) {
            System.out.print(clas.getListOfAssignments().get(i).getName());
            System.out.print(" [" + i + "]  ");
        }
        System.out.println();
        System.out.println("Please select the assignment: (Select out of bound to go back)");
        int assignmentSelection = scan.nextInt();
        if (assignmentSelection < 0 || assignmentSelection >= clas.getListOfAssignments().size()) {
            return null;
        }
        return clas.getListOfAssignments().get(assignmentSelection);
    }

    // REQUIRES: Student has submitted the assignment
    // EFFECTS: Display the submission information
    private void displaySubmission(Submission submission) {
        System.out.println("Student: " + submission.getByStudent().getUserName());
        System.out.println("Assignment:" + submission.getAssignment().getName());
        System.out.println("Submission:" + submission.getContent());
        if (submission.getMark() == -1) {
            System.out.println("Hasn't been marked yet");
        } else {
            System.out.println("Mark: " + submission.getMark() + "/" + submission.getAssignment().getAvailableMark());
        }
    }

    //TEACHER UI SECTION

    // EFFECTS: Log the teacher in and return the account or return null if failed
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private Teacher runTeacherLogin() {
        Teacher curUser = null;
        String userName = null;
        String password = null;
        List<Teacher> users = db.getTeachers();
        while (curUser == null) {
            System.out.println("Please enter Username: (Or .back)");
            userName = scan.next();
            if (userName.equals(".back")) {
                break;
            }
            for (Teacher user : users) {
                if (user.getUserName().equals(userName)) {
                    curUser = user;
                    break;
                }
            }
            if (curUser == null) {
                System.out.println("Username not found.");
            }
            while (curUser != null) {
                System.out.println("Please enter password: (Or .back)");
                password = scan.next();
                if (password.equals(".back")) {
                    curUser = null;
                    break;
                } else if (curUser.login(password)) {
                    System.out.println("Login successful!");
                    return curUser;
                } else {
                    System.out.println("Password Incorrect!");
                }
            }
        }
        return null;
    }

    // EFFECTS: Runs the Teacher portal
    private void runTeacher() {
        Teacher account = runTeacherLogin();
        if (account == null) {
            return;
        }
        String command;
        while (account != null) {
            displayTeacherMenu();
            command = scan.next();

            if (command.equals("q")) {
                account = null;
            } else {
                processTeacherCommand(command, account);
            }
        }
    }

    // EFFECTS: Displays menu of login options
    private void displayTeacherMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add Student");
        System.out.println("\tc -> Create Assignment");
        System.out.println("\tv -> View Students");
        System.out.println("\ts -> View Submission");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: Maps Teacher commands to corresponding functions
    private void processTeacherCommand(String command, Teacher account) {
        if (command.equals("a")) {
            teacherAddStudent(account);
        } else if (command.equals("c")) {
            teacherCreateAssignment(account);
        } else if (command.equals("v")) {
            teacherViewStudents(account);
        } else if (command.equals("s")) {
            teacherViewSubmissions(account);
        } else {
            System.out.println("Command Invalid.");
        }
    }

    // MODIFIES: Class
    // EFFECTS: Presents options to pick student to add to a class
    private void teacherAddStudent(Teacher account) {
        Class curClass = selectClass();
        if (curClass == null) {
            return;
        }
        Student curStudent = selectStudent();
        if (curStudent == null) {
            return;
        }
        try {
            account.addStudent(curClass, curStudent);
            System.out.println("Student successfully added!");
        } catch (AlreadyInClass e) {
            System.out.println("Student is already in ths class!");
        }
    }

    // MODIFIES: Class
    // EFFECTS: Creates a new assignment for a class
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void teacherCreateAssignment(Teacher account) {
        Class curClass = selectClass();
        if (curClass == null) {
            return;
        }

        System.out.println("Assignment Name:");
        String assName = scan.next();

        System.out.println("Assignment Description:");
        String assDesc = scan.next();

        System.out.println("Due Date (yyyy-MM-dd HH:mm):");
        String dueString = scan.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dueDate;
        try {
            dueDate = LocalDateTime.parse(dueString, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Incorrect Date Format");
            return;
        }

        System.out.println("Available Marks:");
        int availMark = scan.nextInt();

        account.createAssignment(curClass, assName, assDesc, dueDate, availMark);
        System.out.println("Assignment created!");
    }

    // EFFECTS: Prints  a list of students of a class
    private void teacherViewStudents(Teacher account) {
        Class curClass = selectClass();
        if (curClass == null) {
            return;
        }

        List<Student> loS = account.viewStudents(curClass);
        for (Student stud : loS) {
            System.out.println(stud.getUserName() + " ");
        }
        System.out.println();
    }

    // EFFECTS: Prints the submission details of a student's assignment
    private void teacherViewSubmissions(Teacher account) {
        Class curClass = selectClass();
        if (curClass == null) {
            return;
        }

        Assignment curAssignment = selectAssignment(curClass);
        if (curAssignment == null) {
            return;
        }

        Student curStudent = selectStudent();
        if (curStudent == null) {
            return;
        }
        Submission submission;
        try {
            submission = account.viewSubmission(curAssignment, curStudent);
            displaySubmission(submission);
        } catch (NoSubmission e) {
            System.out.println("The student hasn't submitted the assignment!");
        }
    }
}
