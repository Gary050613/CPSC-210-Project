package model;

import error.AlreadyInClass;
import error.NoSubmission;

import java.time.LocalDateTime;
import java.util.List;

public class Teacher extends User {
    public Teacher(String userName, String password) {
        super(userName, password);
    }

    // MODIFIES: class
    // EFFECTS: Add the student to the class
    public void addStudent(Class clas, Student stud) throws AlreadyInClass {
        clas.addStudent(stud);
    }

    // MODIFIES: class
    // EFFECTS: Create a new Assignment for Class
    public void createAssignment(Class clas, String assName, String assDesc, LocalDateTime dueDate, int availMark) {
        clas.addAssignment(new Assignment(assName, assDesc, dueDate, availMark, clas));
    }

    // EFFECTS: Returns the students for the Class
    public List<Student> viewStudents(Class clas) {
        return clas.getListOfStudents();
    }

    // EFFECTS: Returns a specific student's submission for the assignment
    public Submission viewSubmission(Assignment ass, Student stud) throws NoSubmission {
        return ass.getSubmission(stud);
    }
}
