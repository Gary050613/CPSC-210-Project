package model;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

// A User who may be (Student, TA, or Teacher)
public class User {
    // delete or rename this class!
    private String userName;
    private String password;
    private List<Class> listOfClasses;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        listOfClasses = new ArrayList<>();
    }

    // EFFECT: Checks whether the entered password is correct
    public boolean login(String enteredPassword) {
        return enteredPassword.equals(this.password);
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
}
