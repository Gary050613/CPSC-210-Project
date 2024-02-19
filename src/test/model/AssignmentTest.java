package model;

import error.AlreadyInClass;
import error.DueDatePast;
import error.MarkOverflow;
import error.NoSubmission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AssignmentTest {
    Assignment ass;
    Student stud;
    Class clas;

    @BeforeEach
    void setup() {
        stud = new Student("Student", "password");
        clas = new Class("CPSC", "Yay");
        try {
            clas.addStudent(stud);
        } catch (AlreadyInClass e) {}
        LocalDateTime due = LocalDateTime.now().plusMinutes(1);
        ass = new Assignment("HW1", "hw1", due, 50, clas);
    }

    @Test
    void testStudentSubmit() {
        ass.setDueDate(LocalDateTime.now().plusSeconds(1));
        try {
            ass.studentSubmit(stud, "Blah");
        } catch (DueDatePast e) {
            fail();
        }
        try {
            assertEquals(ass.getSubmission(stud).getContent(), "Blah");
        } catch (NoSubmission e) {
            fail();
        }
    }

    @Test
    void testStudentSubmitPastDue() {
        ass.setDueDate(LocalDateTime.now().minusSeconds(1));
        try {
            ass.studentSubmit(stud, "Blah");
            fail();
        } catch (DueDatePast e) {

        }
    }

    @Test
    void testMark() {
        try {
            ass.studentSubmit(stud, "Blah");
        } catch (DueDatePast e) {
            fail();
        }
        try {
            ass.mark(stud, 50);
        } catch (NoSubmission e) {
            fail();
        } catch (MarkOverflow e) {
            fail();
        }
        try {
            assertEquals(50, ass.getSubmission(stud).getMark());
        } catch (NoSubmission e) {
            fail();
        }
    }

    @Test
    void testMarkOverFlow() {
        try {
            ass.studentSubmit(stud, "Blah");
        } catch (DueDatePast e) {
            fail();
        }
        try {
            ass.mark(stud, 51);
            fail();
        } catch (NoSubmission e) {
            fail();
        } catch (MarkOverflow e) {

        }
    }

    @Test
    void testMarkNoSubmission() {
        try {
            ass.mark(stud, 50);
            fail();
        } catch (NoSubmission e) {

        } catch (MarkOverflow e) {
            fail();
        }
    }

    @Test
    void testAddStudent() {
        Student newStud = new Student("Stu", "123");
        ass.addStudent(newStud);
        Submission check = new Submission(newStud, ass);
        try {
            assertEquals(check, ass.getSubmission(newStud));
        } catch (NoSubmission e) {
            fail();
        }
    }

    @Test
    void testGetSubmission() {
        try {
            Submission check = new Submission(stud, ass);
            assertEquals(check, ass.getSubmission(stud));
        } catch (NoSubmission e) {
            fail();
        }
    }
}
