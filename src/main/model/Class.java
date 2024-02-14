package model;

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
}
