package model;

import error.DueDatePast;

import java.time.LocalDateTime;

public class Submission {
    private Student byStudent;
    private Assignment assignment;
    private int mark;
    private LocalDateTime submittedTime;
    private LocalDateTime markedTime;
    private String submission;

    // Constructor
    public Submission(Student byStudent, Assignment assignment) {
        this.byStudent = byStudent;
        this.assignment = assignment;
        this.mark = -1;
        this.submission = null;
    }

    // REQUIRES: submittedTime <= assignment.dueDate
    // MODIFIES: this
    // EFFECTS: Submit the assignment for the student, or throw DueDatePast error
    public void submit(String submittedContent) throws DueDatePast {
        if (LocalDateTime.now().isBefore(assignment.getDueDate())) {
            this.submission = submittedContent;
            submittedTime = LocalDateTime.now();
        } else {
            throw new DueDatePast();
        }
    }

    public void mark(int assignedMark) {
        this.mark = assignedMark;
        this.markedTime = LocalDateTime.now();
    }

    // Getters & Setters (No setters for Student & Assignment)
    public Student getByStudent() {
        return byStudent;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getMark() {
        return mark;
    }
}
