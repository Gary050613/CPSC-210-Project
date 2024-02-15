package model;

import error.UserAlreadyExists;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Represents an actual class. Contains Teachers, TA's, and students. Contains assignment information.
public class Class {
    // Course Properties
    private String courseName;
    private String courseDescription;

    // Users
    private List<Teacher> listOfTeachers;
    private List<TA> listOfTAs;
    private List<Student> listOfStudents;

    // Items
    private List<Assignment> listOfAssignments;

    // Constructor
    public Class(String courseName, String courseDescription) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        listOfTeachers = new ArrayList<>();
        listOfTAs = new ArrayList<>();
        listOfStudents = new ArrayList<>();
        listOfAssignments = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Add a new teacher to this class
    public void addTeacher(Teacher teacher) throws UserAlreadyExists {
        if (listOfTeachers.contains(teacher)) {
            throw new UserAlreadyExists();
        } else {
            listOfTeachers.add(teacher);
            // TODO: Add this class to the teacher's class list
        }
    }

    // MODIFIES: this
    // EFFECTS: Add a new TA to this class
    public void addTA(TA ta) throws UserAlreadyExists {
        if (listOfTAs.contains(ta)) {
            throw new UserAlreadyExists();
        } else {
            listOfTAs.add(ta);
            // TODO: Add this class to the TA's class list
        }
    }

    // MODIFIES: this
    // EFFECTS: Add a new Student to this class, assign all current assignments to the student
    public void addStudent(Student stud) throws UserAlreadyExists {
        if (listOfStudents.contains(stud)) {
            throw new UserAlreadyExists();
        } else {
            listOfStudents.add(stud);
            // TODO: Add this class to the student's class list
            // TODO: Assign all current assignments to the student
        }
    }

    // MODIFIES: this
    // EFFECTS: Create a new assignment and add to listOfAssignments
    public void createAssignment(String assName, String assDesc, LocalDateTime dueDate, int availMark) {
        listOfAssignments.add(new Assignment(assName, assDesc, dueDate, availMark, this));
    }

    // Getters & Setters
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String newCourseName) {
        courseName = newCourseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String newDescription) {
        courseDescription = newDescription;
    }

    public List<Student> getListOfStudents() {
        return listOfStudents;
    }

    public List<Assignment> getListOfAssignments() {
        return listOfAssignments;
    }
}
