package persistence;

import java.time.LocalDateTime;
import java.util.List;

import model.Assignment;
import model.Class;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {
//    protected void checkAssignment(String name, String desc, LocalDateTime dueDate, int mark, Class clas,
//                                   Assignment ass) {
//        assertEquals(name, ass.getName());
//        assertEquals(desc, ass.getDescription());
//        assertEquals(dueDate, ass.getDueDate());
//        assertEquals(mark, ass.getAvailableMark());
//        assertEquals(clas, ass.getAssignedClass());
//    }

    protected void checkClassAssignments(List<Assignment> assignmentList, Class clas) {
        for (Assignment ass : assignmentList) {
            assertTrue(clas.getListOfAssignments().contains(ass));
        }
    }
}
