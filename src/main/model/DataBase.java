package model;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private List<Teacher> teachers;
    private List<TA> tas;
    private List<Student> students;
    private List<Class> classes;

    public DataBase() {
        teachers = new ArrayList<>();
        tas = new ArrayList<>();
        students = new ArrayList<>();
        classes = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Create a new class
    public void createClass(String courseName, String courseDescription) {
        classes.add(new Class(courseName, courseDescription));
    }

    // REQUIRES: userName not already taken
    // MODIFIES: this
    // EFFECTS: Create a new teacher
    public void createTeacher(String userName, String password) {
        teachers.add(new Teacher(userName, password));
    }

    // REQUIRES: userName not already taken
    // MODIFIES: this
    // EFFECTS: Create a new TA
    public void createTA(String userName, String password) {
        tas.add(new TA(userName, password));
    }

    // REQUIRES: userName not already taken
    // MODIFIES: this
    // EFFECTS: Create a new student
    public void createStudent(String userName, String password) {
        students.add(new Student(userName, password));
    }

    // Setters
    public List<Teacher> getTeachers() {
        return teachers;
    }

    public List<TA> getTAs() {
        return tas;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Class> getClasses() {
        return classes;
    }
}
