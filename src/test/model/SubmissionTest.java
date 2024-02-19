package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SubmissionTest {
    Submission sub1;
    Submission sub2;
    Student stud1;
    Student stud2;
    Assignment ass;
    Class clas;

    @BeforeEach
    void setup() {
        clas = new Class("", "");
        ass = new Assignment("", "", null, 0, clas);
        stud1 = new Student("", "");
        stud2 = new Student("", "");
        sub1 = new Submission(stud1, ass);
        sub2 = new Submission(stud2, ass);
    }

    @Test
    void testConstructor() {
        assertEquals(stud1, sub1.getByStudent());
        assertEquals(ass, sub1.getAssignment());
    }

    @Test
    void testSubmit() {
        LocalDateTime dateTime = LocalDateTime.now();
        sub1.submit("1+1=2", dateTime);
        assertEquals("1+1=2", sub1.getContent());
        assertEquals(dateTime, sub1.getSubmittedTime());
    }

    @Test
    void testMark() {
        LocalDateTime dateTime = LocalDateTime.now();
        sub1.mark(10);
        assertEquals(10, sub1.getMark());
        assertTrue(dateTime.isBefore(sub1.getMarkedTime()));
    }

    @Test
    void testNotEquals() {
        assertFalse(sub1.equals(sub2));
    }
}
