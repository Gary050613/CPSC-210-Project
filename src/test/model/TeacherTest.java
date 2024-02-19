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

public class TeacherTest {
    Teacher teacher;
    Student stud1;
    Student stud2;
    Class clas;

    @BeforeEach
    void setup() {
        teacher = new Teacher("", "");
        stud1 = new Student("", "");
        stud2 = new Student("", "");
        clas = new Class("", "");
    }

    @Test
    void testAddStudent() {
        try {
            teacher.addStudent(clas, stud1);
        } catch (AlreadyInClass e) {
            fail();
        }
        assertTrue(clas.getListOfStudents().contains(stud1));
    }

    @Test
    void testAddStudentAlreadyInClass() {
        try {
            teacher.addStudent(clas, stud1);
            teacher.addStudent(clas, stud1);
            fail();
        } catch (AlreadyInClass e) {

        }
    }

    @Test
    void testCreateAssignment() {
        try {
            clas.getListOfAssignments().get(0);
        } catch(IndexOutOfBoundsException e) {

        }
        teacher.createAssignment(clas, "", "", null, 0);
        assertTrue(null != clas.getListOfAssignments().get(0));
    }

    @Test
    void testViewStudents() {
        try {
            clas.addStudent(stud1);
            clas.addStudent(stud2);
        } catch (AlreadyInClass e) {
            fail();
        }
        List<Student> expected = new ArrayList<>();
        expected.add(stud1);
        expected.add(stud2);
        assertEquals(expected, teacher.viewStudents(clas));
    }

    @Test
    void testViewSubmission() {
        try {
            teacher.addStudent(clas, stud1);
        } catch (AlreadyInClass e) {
            fail();
        }
        teacher.createAssignment(clas, "", "", LocalDateTime.now().plusSeconds(1), 0);
        Assignment ass = clas.getListOfAssignments().get(0);
        try {
            stud1.submitAssignment(ass, "Blah");
        } catch (DueDatePast e) {
            fail();
        }
        try {
            assertEquals(ass.getSubmission(stud1), teacher.viewSubmission(ass, stud1));
        } catch (NoSubmission e) {
            fail();
        }
    }
}
