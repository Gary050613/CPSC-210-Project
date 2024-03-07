package model;

import error.DueDatePast;

import java.util.ArrayList;
import java.util.List;

// Represents a student account
public class Student extends User {
    public Student(String userName, String password) {
        super(userName, password);
    }

    // EFFECTS: Returns all assignments of all the student's classes
    public List<Assignment> getAllAssignments() {
        List<Assignment> allAssignments = new ArrayList<>();
        for (Class clas : getlistOfClasses()) {
            allAssignments.addAll(clas.getListOfAssignments());
        }
        return allAssignments;
    }

    // MODIFIES: Assignment's Submission for current student
    // EFFECTS: Submit the content to the Assignment, or throws DueDatePast error if dueDate is past
    public void submitAssignment(Assignment ass, String submission) throws DueDatePast {
        ass.studentSubmit(this, submission);
    }
}
