package model;

import error.AlreadyInClass;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

// A User who may be (Student, TA, or Teacher)
public class User {
    private String userName;
    private String password;
    private List<Class> listOfClasses;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        listOfClasses = new ArrayList<>();
    }

    // EFFECTS: Checks whether the entered password is correct
    public boolean login(String enteredPassword) {
        return enteredPassword.equals(this.password);
    }

    // REQUIRES: Class not already inside listOfClasses
    // MODIFIES: this
    // EFFECTS: Add Class to the User
    public void addClass(Class clas) throws AlreadyInClass {
        if (listOfClasses.contains(clas)) {
            throw new AlreadyInClass();
        } else {
            listOfClasses.add(clas);
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String newUserName) {
        this.userName = newUserName;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public List<Class> getlistOfClasses() {
        return listOfClasses;
    }
}
