package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataBaseTest {
    DataBase db;

    @BeforeEach
    void setup() {
        db = new DataBase();
    }

    @Test
    void testCreateClass() {
        db.createClass("CPSC", "CS");
        assertEquals("CPSC", db.getClasses().get(0).getCourseName());
        assertEquals("CS", db.getClasses().get(0).getCourseDescription());
    }

    @Test
    void testCreateTeacher() {
        db.createTeacher("Teacher", "teach");
        assertEquals("Teacher", db.getTeachers().get(0).getUserName());
        assertTrue(db.getTeachers().get(0).login("teach"));
    }

    @Test
    void testCreateTA() {
        db.createTA("TA", "ta");
        assertEquals("TA", db.getTAs().get(0).getUserName());
        assertTrue(db.getTAs().get(0).login("ta"));
    }

    @Test
    void testCreateStudent() {
        db.createStudent("Student", "study");
        assertEquals("Student", db.getStudents().get(0).getUserName());
        assertTrue(db.getStudents().get(0).login("study"));
    }

    @Test
    void testSetClasses() {
        List<Class> classes = new ArrayList<>();
        classes.add(new Class("CPSC 110", "Gregor"));
        classes.add(new Class("CPSC 121", "Karina"));
        db.setClasses(classes);
        assertEquals(classes, db.getClasses());
    }
}
