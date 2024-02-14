package model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

public class Assignment {
    private String name;
    private String description;
    private LocalDateTime dueDate;
    private int availableMark;
    private Class assignedClass;

    private HashMap<Student, Integer> studentMarks;
    private HashMap<Student, String> submissions;

    public Assignment(String name, String description, LocalDateTime dueDate, int availableMark, Class assignedClass) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.availableMark = availableMark;
        this.assignedClass = assignedClass;
        this.studentMarks = new HashMap<>();
        this.submissions = new HashMap<>();
    }

    // REQUIRES: stud a part of the assignedClass, submitted before dueDate
    // MODIFIES: this
    // EFFECTS: Add the student submission to submissions
    public void studentSubmit(Student stud, String submission) {
        // TODO: Add error detection for the two REQUIRES
        this.submissions.put(stud, submission);
    }

    // REQUIRES: stud has a submission & mark <= availableMark
    // MODIFIES: this
    // EFFECTS: Marks the assignment for the student
    public void mark(Student stud, int mark) {
        // TODO: Check role eligibility to mark
        this.studentMarks.put(stud, (Integer) mark);
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public int getAvailableMark() {
        return availableMark;
    }

    public void setAvailableMark(int availableMark) {
        this.availableMark = availableMark;
    }

    public Class getAssignedClass() {
        return assignedClass;
    }

    public void setAssignedClass(Class assignedClass) {
        this.assignedClass = assignedClass;
    }
}
