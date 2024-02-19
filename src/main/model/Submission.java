package model;

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

    // REQUIRES: submittedTime <= assignment.dueDate (Checked in Assignment.studentSubmit())
    // MODIFIES: this
    // EFFECTS: Submit the assignment for the student
    public void submit(String submittedContent, LocalDateTime time) {
        this.submission = submittedContent;
        submittedTime = time;
    }

    // REQUIRES: assignedMark <= assignment.getAvailableMark() (Checked in Assignment.mark())
    // MODIFIES: this
    // EFFECTS: Mark this submission with assignedMark and save marked time
    public void mark(int assignedMark) {
        this.mark = assignedMark;
        this.markedTime = LocalDateTime.now();
    }

    // Getters & Setters (No setters for Student & Assignment)
    public LocalDateTime getSubmittedTime() {
        return submittedTime;
    }

    public LocalDateTime getMarkedTime() {
        return markedTime;
    }

    public Student getByStudent() {
        return byStudent;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public int getMark() {
        return mark;
    }

    public String getSubmission() {
        return submission;
    }
}
