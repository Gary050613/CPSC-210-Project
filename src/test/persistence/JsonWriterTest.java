package persistence;

import model.*;
import model.Class;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Adapted from JsonSerializationDemo
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            DataBase db = new DataBase();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDataBase() {
        try {
            DataBase db = new DataBase();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDataBase.json");
            writer.open();
            writer.write(db);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDataBase.json");
            List<Class> classes = reader.readClasses();
            assertEquals(0, classes.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralDataBase() {
        try {
            DataBase db = new DataBase();
            db.createClass("CPSC 110", "Gregor");
            db.createClass("CPSC 121", "Karina");
            Class cpsc110 = db.getClasses().get(0);
            Class cpsc121 = db.getClasses().get(1);
            Assignment ass1 = new Assignment("Racket","Use DrR",
                    LocalDateTime.parse("2000-10-10T10:10"), 50, cpsc110);
            Assignment ass2 = new Assignment("Python","Use Py",
                    LocalDateTime.parse("2010-10-10T10:10"), 100, cpsc110);
            Assignment ass3 = new Assignment("PrairieLearn","Use PL",
                    LocalDateTime.parse("2020-10-10T10:10"), 10, cpsc121);
            cpsc110.addAssignment(ass1);
            cpsc110.addAssignment(ass2);
            cpsc121.addAssignment(ass3);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDataBase.json");
            writer.open();
            writer.write(db);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralDataBase.json");
            List<Class> classes = reader.readClasses();
            assertEquals(2, classes.size());
            List<Assignment> expected110 = new ArrayList<>();
            List<Assignment> expected121 = new ArrayList<>();
            expected110.add(ass1);
            expected110.add(ass2);
            expected121.add(ass3);

            checkClassAssignments(expected110, cpsc110);
            checkClassAssignments(expected121, cpsc121);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}