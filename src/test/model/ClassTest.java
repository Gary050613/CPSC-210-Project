package model;

import error.AlreadyInClass;
import error.NoSubmission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClassTest {
    Class clas;
    Teacher teacher;
    Student stud;
    TA ta;
    Assignment ass;

    @BeforeEach
    void setup() {
        clas = new Class("CPSC", "Computer");
        teacher = new Teacher("Teach", "teach");
        stud = new Student("Student", "stud");
        ta = new TA("TA", "ta");
        ass = new Assignment("HW1", "hw1", null, 50, clas);
    }

    @Test
    void testAddTeacher() {
        try {
            clas.addTeacher(teacher);
        } catch (AlreadyInClass e) {
            fail();
        }
        assertTrue(clas.getListOfTeachers().contains(teacher));
        assertTrue(teacher.getlistOfClasses().contains(clas));
    }

    @Test
    void testAddTeacherAlreadyInClass() {
        try {
            clas.addTeacher(teacher);
        } catch (AlreadyInClass e) {
            fail();
        }
        try {
            clas.addTeacher(teacher);
            fail();
        } catch (AlreadyInClass e) {

        }
    }

    @Test
    void testAddTA() {
        try {
            clas.addTA(ta);
        } catch (AlreadyInClass e) {
            fail();
        }
        assertTrue(clas.getListOfTAs().contains(ta));
        assertTrue(ta.getlistOfClasses().contains(clas));
    }

    @Test
    void testAddTAAlreadyInClass() {
        try {
            clas.addTA(ta);
        } catch (AlreadyInClass e) {
            fail();
        }
        try {
            clas.addTA(ta);
            fail();
        } catch (AlreadyInClass e) {

        }
    }

    @Test
    void testAddStudent() {
        try {
            clas.addStudent(stud);
        } catch (AlreadyInClass e) {
            fail();
        }
        assertTrue(clas.getListOfStudents().contains(stud));
        assertTrue(stud.getlistOfClasses().contains(clas));
        for (Assignment assignment : clas.getListOfAssignments()) {
            try {
                assignment.getSubmission(stud);
            } catch (NoSubmission e) {
                fail();
            }
        }
    }

    @Test
    void testAddStudentAlreadyInClass() {
        try {
            clas.addStudent(stud);
        } catch (AlreadyInClass e) {
            fail();
        }
        try {
            clas.addStudent(stud);
            fail();
        } catch (AlreadyInClass e) {

        }
    }

    @Test
    void testAddAssignment() {
        clas.addAssignment(ass);
        assertTrue(clas.getListOfAssignments().contains(ass));
    }

    @Test
    void testHashCode() {
        Class clas2 = new Class("CPSC", "Computer");
        assertEquals(clas, clas2);
        assertNotEquals(clas, null);
    }

    @Test
    void testEquals() {
        Class clas2 = new Class("CPSC", "Computer");
        assertTrue(clas.equals(clas2));
        Class clas3 = new Class("CPS", "Computer");
        assertFalse(clas.equals(clas3));
        clas3 = new Class("CPSC", "Compute");
        assertFalse(clas.equals(clas3));
        assertFalse(clas.equals(null));
        assertFalse(clas.equals(new Object()));
    }
}
