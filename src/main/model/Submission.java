package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Submission {
    private Student byStudent;
    private Assignment assignment;
    private int mark;
    private LocalDateTime submittedTime;
    private LocalDateTime markedTime;
    private String content;

    // Constructor
    public Submission(Student byStudent, Assignment assignment) {
        this.byStudent = byStudent;
        this.assignment = assignment;
        this.mark = -1;
        this.content = null;
    }

    // REQUIRES: submittedTime <= assignment.dueDate (Checked in Assignment.studentSubmit())
    // MODIFIES: this
    // EFFECTS: Submit the assignment for the student
    public void submit(String submittedContent, LocalDateTime time) {
        this.content = submittedContent;
        submittedTime = time;
    }

    // REQUIRES: assignedMark <= assignment.getAvailableMark() (Checked in Assignment.mark())
    // MODIFIES: this
    // EFFECTS: Mark this submission with assignedMark and save marked time
    public void mark(int assignedMark) {
        this.mark = assignedMark;
        this.markedTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        Submission that = (Submission) o;
        return mark == that.mark
                && Objects.equals(byStudent, that.byStudent)
                && Objects.equals(assignment, that.assignment)
                && Objects.equals(submittedTime, that.submittedTime)
                && Objects.equals(markedTime, that.markedTime)
                && Objects.equals(content, that.content);
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

    public String getContent() {
        return content;
    }
}
