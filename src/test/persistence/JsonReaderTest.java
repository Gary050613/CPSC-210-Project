package persistence;

import model.*;
import model.Class;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            List<Class> classes = reader.readClasses();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDataBase() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDataBase.json");
        try {
            List<Class> classes = reader.readClasses();
            assertEquals(0, classes.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDataBase() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDataBase.json");
        try {
            List<Class> classes = reader.readClasses();
            assertEquals(2, classes.size());
            Class cpsc110 = new Class("CPSC 110", "Gregor");
            Class cpsc121 = new Class("CPSC 121", "Karina");
            Assignment ass1 = new Assignment("Racket","Use DrR",
                    LocalDateTime.parse("2000-10-10T10:10"), 50, cpsc110);
            Assignment ass2 = new Assignment("Python","Use Py",
                    LocalDateTime.parse("2010-10-10T10:10"), 100, cpsc110);
            Assignment ass3 = new Assignment("PrairieLearn","Use PL",
                    LocalDateTime.parse("2020-10-10T10:10"), 10, cpsc121);
            List<Assignment> expected110 = new ArrayList<>();
            List<Assignment> expected121 = new ArrayList<>();
            expected110.add(ass1);
            expected110.add(ass2);
            expected121.add(ass3);
            checkClassAssignments(expected110, classes.get(0));
            checkClassAssignments(expected121, classes.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}