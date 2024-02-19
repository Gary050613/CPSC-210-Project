package model;

import error.AlreadyInClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user;
    Class clas;

    @BeforeEach
    void setup() {
        user = new User("User", "password");
        clas = new Class("CPSC", "Computer");
    }

    @Test
    void testLogin() {
        assertTrue(user.login("password"));
        assertFalse(user.login("Password"));
    }

    @Test
    void testAddClass() {
        List<Class> loC = new ArrayList<>();
        assertEquals(user.getlistOfClasses(), loC);
        try {
            user.addClass(clas);
        } catch (AlreadyInClass e) {
            fail();
        }
        loC.add(clas);
        assertEquals(user.getlistOfClasses(), loC);
        try {
            user.addClass(clas);
            fail();
        } catch (AlreadyInClass e) {

        }
    }
}