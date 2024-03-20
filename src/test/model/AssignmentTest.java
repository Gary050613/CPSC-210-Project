package model;

import error.AlreadyInClass;
import error.DueDatePast;
import error.MarkOverflow;
import error.NoSubmission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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
    void testConstructor() {
        assertEquals(ass.getName(), "HW1");
        assertEquals(ass.getDescription(), "hw1");

        assertEquals(ass.getAvailableMark(), 50);
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
        try {
            ass.getSubmission(newStud);
            fail();
        } catch (NoSubmission e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testGetSubmission() {
        try {
            ass.getSubmission(stud);
            fail();
        } catch (NoSubmission e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testHashCode() {
        Assignment ass1 = new Assignment("ass1", "lol", LocalDateTime.parse("2020-10-10T10:10"),
                50, clas);
        Assignment ass2 = new Assignment("ass1", "lol", LocalDateTime.parse("2020-10-10T10:10"),
                50, clas);
        Assignment ass3 = new Assignment("ass2", "lol", LocalDateTime.parse("2020-10-10T10:10"),
                50, clas);
        assertEquals(ass1.hashCode(), ass2.hashCode());
        assertNotEquals(ass1.hashCode(), ass3.hashCode());
    }

    @Test
    void testEquals() {
        Assignment ass1 = new Assignment("ass1", "lol", LocalDateTime.parse("2020-10-10T10:10"),
                50, clas);
        Assignment ass2 = new Assignment("ass1", "lol", LocalDateTime.parse("2020-10-10T10:10"),
                50, clas);
        assertTrue(ass1.equals(ass2));
        ass2 = new Assignment("ass2", "lol", LocalDateTime.parse("2020-10-10T10:10"),
                50, clas);
        assertFalse(ass1.equals(ass2));
        ass2 = new Assignment("ass1", "nah", LocalDateTime.parse("2020-10-10T10:10"),
                50, clas);
        assertFalse(ass1.equals(ass2));
        ass2 = new Assignment("ass1", "lol", LocalDateTime.parse("2020-10-10T10:10"),
                25, clas);
        assertFalse(ass1.equals(ass2));
        assertFalse(ass1.equals(null));
        assertFalse(ass1.equals(new Object()));
    }

    @Test
    void testSelfDelete() {
        ass.selfDelete();
        assertFalse(clas.getListOfAssignments().contains(ass));
    }

    @Test
    void testSelfDeleteAlreadyDeleted() {
        ass.selfDelete();
        try {
            ass.selfDelete();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testSetters() {
        ass.setName("NEW");
        assertEquals("NEW", ass.getName());
        ass.setDescription("YEAH");
        assertEquals("YEAH", ass.getDescription());
        LocalDateTime dueDate = LocalDateTime.parse("2000-10-10T10:00");
        ass.setDueDate(dueDate);
        assertEquals(dueDate, ass.getDueDate());
        ass.setAvailableMark(10);
        assertEquals(10, ass.getAvailableMark());
    }
}
