package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubmissionTest {
    Submission sub;

    @BeforeEach
    void setup() {
        sub = new Submission(null, null);
    }

    @Test
    void testSubmit() {
        LocalDateTime dateTime = LocalDateTime.now();
        sub.submit("1+1=2", dateTime);
        assertEquals("1+1=2", sub.getContent());
        assertEquals(dateTime, sub.getSubmittedTime());
    }

    @Test
    void testMark() {
        LocalDateTime dateTime = LocalDateTime.now();
        sub.mark(10);
        assertEquals(10, sub.getMark());
        assertTrue(dateTime.isBefore(sub.getMarkedTime()));
    }
}
