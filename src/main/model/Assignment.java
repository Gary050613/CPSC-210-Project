package model;

import error.DueDatePast;
import error.MarkOverflow;
import error.NoSubmission;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

// Represents an assignment for a class
public class Assignment implements Writable {
    private String name;
    private String description;
    private LocalDateTime dueDate;
    private int availableMark;
    private Class assignedClass;

    private HashMap<Student, Submission> submissions;

    public Assignment(String name, String description, LocalDateTime dueDate, int availableMark, Class assignedClass) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.availableMark = availableMark;
        this.assignedClass = assignedClass;
        this.submissions = new HashMap<>();
        for (Student stud : assignedClass.getListOfStudents()) {
            submissions.put(stud, new Submission(stud, this));
        }
    }

    // REQUIRES: stud a part of the assignedClass (Assumed by the ability to access this method)
    // MODIFIES: this
    // EFFECTS: Add the student submission to submissions, throws DueDatePast exception if past dueDate
    public void studentSubmit(Student stud, String submission) throws DueDatePast {
        LocalDateTime snapshot = LocalDateTime.now();
        if (snapshot.isAfter(dueDate)) {
            throw new DueDatePast();
        } else {
            this.submissions.get(stud).submit(submission, snapshot);
        }
    }

    // MODIFIES: this
    // EFFECTS: Marks the assignment for the student
    public void mark(Student stud, int mark) throws NoSubmission, MarkOverflow {
        if (submissions.get(stud).getContent() == null) {
            throw new NoSubmission();
        } else if (mark > availableMark) {
            throw new MarkOverflow();
        } else {
            this.submissions.get(stud).mark(mark);
        }
    }

    // REQUIRES: stud not already assigned
    // MODIFIES: this
    // EFFECTS: Add the student to this assignment
    public void addStudent(Student stud) {
        submissions.put(stud, new Submission(stud, this));
    }

    // EFFECTS: Return the stud's submission
    public Submission getSubmission(Student stud) throws NoSubmission {
        if (submissions.get(stud).getContent() == null) {
            throw new NoSubmission();
        } else {
            return submissions.get(stud);
        }
    }

    public void selfDelete() {
        this.assignedClass.getListOfAssignments().remove(this);
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("desc", description);
        json.put("due", dueDate);
        json.put("mark", availableMark);
        json.put("class", assignedClass.getCourseName());
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Assignment that = (Assignment) o;
        return availableMark == that.availableMark && Objects.equals(name, that.name)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, dueDate, availableMark, assignedClass);
    }
}
