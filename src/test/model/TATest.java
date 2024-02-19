package model;

import error.AlreadyInClass;
import error.DueDatePast;
import error.MarkOverflow;
import error.NoSubmission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.fail;

public class TATest {
    TA ta;
    Student stud;
    Assignment ass;
    Class clas;

    @BeforeEach
    void setup() {
        ta = new TA("TA", "ta");
        stud = new Student("Student", "stud");
        clas = new Class("CPSC", "Yay");
        try {
            clas.addStudent(stud);
        } catch (AlreadyInClass e) {}
        ass = new Assignment("HW1", "hw1", LocalDateTime.now().plusMinutes(1), 50, clas);
    }

    @Test
    void testMark() {
        try {
            stud.submitAssignment(ass, "Blah");
        } catch (DueDatePast e) {
            fail();
        }
        try {
            ta.mark(ass, stud, 50);
        } catch (NoSubmission e) {
            fail();
        } catch (MarkOverflow e) {
            fail();
        }
    }

    @Test
    void testMarkNoSubmission() {
        try {
            ta.mark(ass, stud, 50);
            fail();
        } catch (NoSubmission e) {

        } catch (MarkOverflow e) {
            fail();
        }
    }

    @Test
    void testMarkOverFlow() {
        try {
            stud.submitAssignment(ass, "Blah");
        } catch (DueDatePast e) {
            fail();
        }
        try {
            ta.mark(ass, stud, 51);
            fail();
        } catch (NoSubmission e) {
            fail();
        } catch (MarkOverflow e) {

        }
    }
}
