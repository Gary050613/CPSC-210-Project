package model;

import error.MarkOverflow;
import error.NoSubmission;

public class TA extends User {
    // Constructor
    public TA(String userName, String password) {
        super(userName, password);
    }

    // REQUIRES: listOfClasses.contains(ass.getAssignedClass()) (Achieved through restriction of TA's UI)
    // MODIFIES: ass.getSubmission(stud)
    // EFFECTS: Marks Student's Assignment with a mark
    public void mark(Assignment ass, Student stud, int mark) throws NoSubmission, MarkOverflow {
        ass.mark(stud, mark);
    }
}
