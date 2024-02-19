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

    @BeforeEach
    void setup() {
        stud1 = new Student("", "");
        stud2 = new Student("", "");
        sub1 = new Submission(stud1, null);
        sub2 = new Submission(stud2, null);
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
