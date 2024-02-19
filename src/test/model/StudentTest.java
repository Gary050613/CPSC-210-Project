package model;

import error.AlreadyInClass;
import error.DueDatePast;
import error.NoSubmission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    Student stud;
    Class class1;
    Class class2;
    Assignment ass1;
    Assignment ass2;
    Assignment ass3;

    @BeforeEach
    void setup() {
        stud = new Student("Student", "stud");
        class1 = new Class("CPSC1", "cs");
        class2 = new Class("CPSC2", "cs");
        ass1 = new Assignment("", "", null, 0, class1);
        ass2 = new Assignment("", "", null, 0, class1);
        ass3 = new Assignment("", "", null, 0, class2);
        class1.addAssignment(ass1);
        class1.addAssignment(ass2);
        class2.addAssignment(ass3);
        try {
            class1.addStudent(stud);
        } catch (AlreadyInClass e) {

        }
    }

    @Test
    void testGetAllAssignments() {
        List<Assignment> expected = new ArrayList<>();
        expected.add(ass1);
        expected.add(ass2);
        assertEquals(expected, stud.getAllAssignments());
    }

    @Test
    void testGetAllAssignmentsMultipleClasses() {
        try {
            class2.addStudent(stud);
        } catch (AlreadyInClass e) {
            fail();
        }
        List<Assignment> expected = new ArrayList<>();
        expected.add(ass1);
        expected.add(ass2);
        expected.add(ass3);
        assertEquals(expected, stud.getAllAssignments());
    }

    @Test
    void testSubmitAssignment() {
        ass1.setDueDate(LocalDateTime.now().plusSeconds(1));
        try {
            stud.submitAssignment(ass1, "Blah");
        } catch (DueDatePast e) {
            fail();
        }
        try {
            assertEquals("Blah", ass1.getSubmission(stud).getContent());
        } catch (NoSubmission e) {
            fail();
        }
    }

    @Test
    void testSubmitAssignmentDueDatePast() {
        ass1.setDueDate(LocalDateTime.now().minusSeconds(1));
        try {
            stud.submitAssignment(ass1, "Blah");
            fail();
        } catch (DueDatePast e) {

        }
    }
}
