package ui;

import model.DataBase;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import model.*;
import model.Class;

import static javax.swing.SwingConstants.*;

public class SwingUI {
    JFrame loginUI;
    JFrame teacherPortal;
    protected static final int UI_WIDTH = 1000;
    protected static final int UI_HEIGHT = 700;
    protected static final int BUTTON_WIDTH = 100;
    protected static final int BUTTON_HEIGHT = 50;
    protected static final int SELECTION_WIDTH = 200;
    protected static final int TEXT_FIELD_WIDTH = 200;
    protected static final int TEXT_FIELD_HEIGHT = 50;
    protected static final int TEXT_AREA_HEIGHT = 100;
    protected static final int SELECTOR_HEIGHT = 50;
    protected static final int SELECTOR_WIDTH = 200;
    protected static final int TEXT_HEIGHT = 20;
    protected static final int BUTTON_GAP = 10;
    protected static final int GAP = 20;

    protected DataBase db;
    protected static final String JSON_STORE = "./data/database.json";
    protected JsonWriter jsonWriter;
    protected JsonReader jsonReader;

    protected boolean loggedIn;
    protected Teacher curTeacher;
    protected Class curClass;
    protected Assignment curAss;

    private JTextField userName;
    private JPasswordField password;
    private JLabel alert;


    public SwingUI() {
        db = new DataBase();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        db.createClass("CPSC 110", "Gregor");
        db.createClass("CPSC 121", "Karina");
        db.createTeacher("Teacher", "pw");
        db.createStudent("Student", "pw");
        db.createStudent("Tom", "pw");
        db.createTA("TA", "pw");
        displayLogin();
//        displayTeacherPortal();
    }

    // TODO: Documentation
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void displayLogin() {
        loginUI = new JFrame();
        JLabel userString = new JLabel("Username");
        userString.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                (UI_HEIGHT - TEXT_FIELD_HEIGHT) / 3 - TEXT_HEIGHT,
                TEXT_FIELD_WIDTH, TEXT_HEIGHT);
        loginUI.add(userString);

        userName = new JTextField();
        userName.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2, (UI_HEIGHT - TEXT_FIELD_HEIGHT) / 3,
                TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        loginUI.add(userName);

        JLabel passwordString = new JLabel("Password");
        passwordString.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                (UI_HEIGHT - TEXT_FIELD_HEIGHT) / 3 + TEXT_FIELD_HEIGHT,
                TEXT_FIELD_WIDTH, TEXT_HEIGHT);
        loginUI.add(passwordString);

        password = new JPasswordField();
        password.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                (UI_HEIGHT - TEXT_FIELD_HEIGHT) / 3 + TEXT_FIELD_HEIGHT + GAP,
                TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        loginUI.add(password);

        JButton login = new JButton("Login");
        login.setBounds((UI_WIDTH - BUTTON_WIDTH) / 2, (UI_HEIGHT - BUTTON_HEIGHT) / 3 * 2,
                BUTTON_WIDTH, BUTTON_HEIGHT);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userName.getText();
                String pw = password.getText();
                List<Teacher> teacherList = db.getTeachers();
                for (Teacher t : teacherList) {
                    if (t.getUserName().equals(user) && t.login(pw)) {
                        loggedIn = true;
                        curTeacher = t;
                        alert.setText("Correct!");
                        loginUI.dispose();
                        displayTeacherPortal();
                        return;
                    }
                }
                alert.setText("Username or password incorrect!");
            }
        });
        loginUI.add(login);

        alert = new JLabel();
        alert.setBounds(0, (UI_HEIGHT - BUTTON_HEIGHT) / 3 * 2 + BUTTON_HEIGHT,
                UI_WIDTH, TEXT_HEIGHT);
        alert.setHorizontalAlignment(CENTER);
        loginUI.add(alert);

        loginUI.setSize(UI_WIDTH,UI_HEIGHT);
        loginUI.setLayout(null);
        loginUI.setVisible(true);
    }

    // TODO: Documentation
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void displayTeacherPortal() {
        teacherPortal = new JFrame();

        JButton create = new JButton("Create Assignment");
        create.setBounds((UI_WIDTH - SELECTION_WIDTH) / 2, (UI_HEIGHT - BUTTON_HEIGHT) / 4,
                SELECTION_WIDTH, BUTTON_HEIGHT);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySelectClass(0);
                teacherPortal.dispose();
            }
        });
        teacherPortal.add(create);

        JButton view = new JButton("View Assignment");
        view.setBounds((UI_WIDTH - SELECTION_WIDTH) / 2, (UI_HEIGHT - BUTTON_HEIGHT) / 4 + BUTTON_HEIGHT + BUTTON_GAP,
                SELECTION_WIDTH, BUTTON_HEIGHT);
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySelectClass(1);
                teacherPortal.dispose();
            }
        });
        teacherPortal.add(view);

        JButton change = new JButton("Change Assignment");
        change.setBounds((UI_WIDTH - SELECTION_WIDTH) / 2,
                (UI_HEIGHT - BUTTON_HEIGHT) / 4 + 2 * (BUTTON_HEIGHT + BUTTON_GAP),
                SELECTION_WIDTH, BUTTON_HEIGHT);
        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teacherPortal.dispose();
                displaySelectClass(2);
            }
        });
        teacherPortal.add(change);

        JButton delete = new JButton("Delete Assignment");
        delete.setBounds((UI_WIDTH - SELECTION_WIDTH) / 2,
                (UI_HEIGHT - BUTTON_HEIGHT) / 4 + 3 * (BUTTON_HEIGHT + BUTTON_GAP),
                SELECTION_WIDTH, BUTTON_HEIGHT);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teacherPortal.dispose();
                displaySelectClass(3);
            }
        });
        teacherPortal.add(delete);

        JButton save = new JButton("Save Assignment");
        save.setBounds((UI_WIDTH - SELECTION_WIDTH) / 2,
                (UI_HEIGHT - BUTTON_HEIGHT) / 4 + 4 * (BUTTON_HEIGHT + BUTTON_GAP),
                SELECTION_WIDTH, BUTTON_HEIGHT);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teacherPortal.dispose();
                saveAssignments();
            }
        });
        teacherPortal.add(save);

        JButton load = new JButton("Load Assignment");
        load.setBounds((UI_WIDTH - SELECTION_WIDTH) / 2,
                (UI_HEIGHT - BUTTON_HEIGHT) / 4 + 5 * (BUTTON_HEIGHT + BUTTON_GAP),
                SELECTION_WIDTH, BUTTON_HEIGHT);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teacherPortal.dispose();
                loadAssignments();
            }
        });
        teacherPortal.add(load);

        teacherPortal.setSize(UI_WIDTH, UI_HEIGHT);
        teacherPortal.setLayout(null);
        teacherPortal.setVisible(true);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void displayCreateAssignment() {
        JFrame createAss = new JFrame();
        int curUIPos = UI_HEIGHT / 4;

        JLabel nameString = new JLabel("Assignment Name");
        nameString.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_HEIGHT);
        createAss.add(nameString);
        curUIPos += TEXT_HEIGHT;

        JTextField assName = new JTextField();
        assName.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        createAss.add(assName);
        curUIPos += TEXT_FIELD_HEIGHT;

        JLabel descString = new JLabel("Assignment Description");
        descString.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_HEIGHT);
        createAss.add(descString);
        curUIPos += TEXT_HEIGHT;

        JTextArea assDesc = new JTextArea();
        assDesc.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_AREA_HEIGHT);
        createAss.add(assDesc);
        curUIPos += TEXT_AREA_HEIGHT;

        JLabel dateString = new JLabel("Due Date (yyyy-MM-dd HH:mm)");
        dateString.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_HEIGHT);
        createAss.add(dateString);
        curUIPos += TEXT_HEIGHT;

        JTextField assDate = new JTextField();
        assDate.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        createAss.add(assDate);
        curUIPos += TEXT_FIELD_HEIGHT;

        JLabel markString = new JLabel("Available Marks");
        markString.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_HEIGHT);
        createAss.add(markString);
        curUIPos += TEXT_HEIGHT;

        JTextField assMark = new JTextField();
        assMark.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        createAss.add(assMark);
        curUIPos += TEXT_FIELD_HEIGHT;

        JButton create = new JButton("Create");
        create.setBounds((UI_WIDTH - BUTTON_WIDTH) / 2,
                curUIPos,
                BUTTON_WIDTH, BUTTON_HEIGHT);

        createAss.add(create);

        createAss.setSize(UI_WIDTH, UI_HEIGHT);
        createAss.setLayout(null);
        createAss.setVisible(true);

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String assNameString = assName.getText();
                String assDescString = assDesc.getText();
                String assDateString = assDate.getText();
                String assMarkString = assMark.getText();
                int availMark = Integer.valueOf(assMarkString);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dueDate = null;
                try {
                    dueDate = LocalDateTime.parse(assDateString, formatter);
                } catch (DateTimeParseException ex) {
                    System.out.println("Incorrect Date Format");
                }
                curTeacher.createAssignment(curClass, assNameString, assDescString, dueDate, availMark);
                createAss.dispose();
                displayTeacherPortal();
            }
        });
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void displayViewAssignment() {
        JFrame displayAss = new JFrame();
        int curUIPos = UI_HEIGHT / 3;

        JLabel nameString = new JLabel("Name: " + curAss.getName());
        nameString.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_HEIGHT);
        displayAss.add(nameString);
        curUIPos += TEXT_HEIGHT;

        JLabel descString = new JLabel("Description: " + curAss.getDescription());
        descString.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_HEIGHT);
        displayAss.add(descString);
        curUIPos += TEXT_HEIGHT;

        JLabel dateString = new JLabel("Due Date: " + curAss.getDueDate().toString());
        dateString.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_HEIGHT);
        displayAss.add(dateString);
        curUIPos += TEXT_HEIGHT;

        JLabel markString = new JLabel("Marks: " + curAss.getAvailableMark());
        markString.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_HEIGHT);
        displayAss.add(markString);
        curUIPos += TEXT_HEIGHT;

        JButton create = new JButton("Back");
        create.setBounds((UI_WIDTH - BUTTON_WIDTH) / 2,
                curUIPos,
                BUTTON_WIDTH, BUTTON_HEIGHT);

        displayAss.add(create);

        displayAss.setSize(UI_WIDTH, UI_HEIGHT);
        displayAss.setLayout(null);
        displayAss.setVisible(true);

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAss.dispose();
                displayTeacherPortal();
            }
        });
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void displayChangeAssignment() {
        JFrame changeAss = new JFrame();
        int curUIPos = UI_HEIGHT / 4;

        JLabel nameString = new JLabel("Assignment Name");
        nameString.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_HEIGHT);
        changeAss.add(nameString);
        curUIPos += TEXT_HEIGHT;

        JTextField assName = new JTextField(curAss.getName());
        assName.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        changeAss.add(assName);
        curUIPos += TEXT_FIELD_HEIGHT;

        JLabel descString = new JLabel("Assignment Description");
        descString.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_HEIGHT);
        changeAss.add(descString);
        curUIPos += TEXT_HEIGHT;

        JTextArea assDesc = new JTextArea(curAss.getDescription());
        assDesc.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_AREA_HEIGHT);
        changeAss.add(assDesc);
        curUIPos += TEXT_AREA_HEIGHT;

        JLabel dateString = new JLabel("Due Date (yyyy-MM-dd HH:mm)");
        dateString.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_HEIGHT);
        changeAss.add(dateString);
        curUIPos += TEXT_HEIGHT;

        JTextField assDate = new JTextField(curAss.getDueDate().toString());
        assDate.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        changeAss.add(assDate);
        curUIPos += TEXT_FIELD_HEIGHT;

        JLabel markString = new JLabel("Available Marks");
        markString.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_HEIGHT);
        changeAss.add(markString);
        curUIPos += TEXT_HEIGHT;

        JTextField assMark = new JTextField(Integer.toString(curAss.getAvailableMark()));
        assMark.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        changeAss.add(assMark);
        curUIPos += TEXT_FIELD_HEIGHT;

        JButton change = new JButton("Change");
        change.setBounds((UI_WIDTH - BUTTON_WIDTH) / 2,
                curUIPos,
                BUTTON_WIDTH, BUTTON_HEIGHT);

        changeAss.add(change);

        changeAss.setSize(UI_WIDTH, UI_HEIGHT);
        changeAss.setLayout(null);
        changeAss.setVisible(true);

        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String assNameString = assName.getText();
                String assDescString = assDesc.getText();
                String assDateString = assDate.getText();
                String assMarkString = assMark.getText();
                int availMark = Integer.valueOf(assMarkString);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dueDate = null;
                try {
                    dueDate = LocalDateTime.parse(assDateString, formatter);
                } catch (DateTimeParseException ex) {
                    System.out.println("Incorrect Date Format");
                }
                curAss.selfDelete();
                curTeacher.createAssignment(curClass, assNameString, assDescString, dueDate, availMark);
                changeAss.dispose();
                displayTeacherPortal();
            }
        });
    }

    private void displayDeleteAssignment() {
        JFrame deleteAss = new JFrame();
        int curUIPos = UI_HEIGHT / 3;

        JLabel confirmation = new JLabel("Are you sure?");
        confirmation.setBounds((UI_WIDTH - TEXT_FIELD_WIDTH) / 2,
                curUIPos,
                TEXT_FIELD_WIDTH, TEXT_HEIGHT);
        deleteAss.add(confirmation);
        curUIPos += TEXT_HEIGHT;

        JButton delete = new JButton("Delete");
        delete.setBounds((UI_WIDTH - BUTTON_WIDTH) / 2,
                curUIPos,
                BUTTON_WIDTH, BUTTON_HEIGHT);

        deleteAss.add(delete);

        deleteAss.setSize(UI_WIDTH, UI_HEIGHT);
        deleteAss.setLayout(null);
        deleteAss.setVisible(true);

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curAss.selfDelete();
                deleteAss.dispose();
                displayTeacherPortal();
            }
        });
    }

    private void saveAssignments() {
        try {
            jsonWriter.open();
            jsonWriter.write(db);
            jsonWriter.close();
            System.out.println("Saved current assignment info to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file at " + JSON_STORE);
        }
        displayTeacherPortal();
    }

    private void loadAssignments() {
        try {
            List<Class> listOfClasses = jsonReader.readClasses();
            db.setClasses(listOfClasses);
            System.out.println("Loaded all assignment data from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        displayTeacherPortal();
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void displaySelectAssignment(int action) {
        JFrame selectAssignment = new JFrame();

        String[] selections = new String[curClass.getListOfAssignments().size()];
        for (int i = 0;i < curClass.getListOfAssignments().size();i++) {
            selections[i] = curClass.getListOfAssignments().get(i).getName();
        }
        JComboBox indexSelector = new JComboBox(selections);
        indexSelector.setBounds((UI_WIDTH - SELECTOR_WIDTH) / 2, UI_HEIGHT / 3,
                SELECTOR_WIDTH, SELECTOR_HEIGHT);
        selectAssignment.add(indexSelector);

        JButton confirm = new JButton("Confirm");
        confirm.setBounds((UI_WIDTH - BUTTON_WIDTH) / 2, (UI_HEIGHT - BUTTON_HEIGHT) / 3 * 2,
                BUTTON_WIDTH, BUTTON_HEIGHT);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indexSelector.getSelectedIndex() == -1) {
                    selectAssignment.dispose();
                    displayTeacherPortal();
                }
                curAss = curClass.getListOfAssignments().get(indexSelector.getSelectedIndex());
                selectAssignment.dispose();
                switch (action) {
                    case 1:
                        displayViewAssignment();
                        break;
                    case 2:
                        displayChangeAssignment();
                        break;
                    case 3:
                        displayDeleteAssignment();
                        break;
                }
            }
        });
        selectAssignment.add(confirm);

        selectAssignment.setSize(UI_WIDTH, UI_HEIGHT);
        selectAssignment.setLayout(null);
        selectAssignment.setVisible(true);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void displaySelectClass(int action) {
        JFrame selectClass = new JFrame();

        String[] selections = new String[db.getClasses().size()];
        for (int i = 0;i < db.getClasses().size();i++) {
            selections[i] = db.getClasses().get(i).getCourseName();
        }
        JComboBox indexSelector = new JComboBox(selections);
        indexSelector.setBounds((UI_WIDTH - SELECTOR_WIDTH) / 2, UI_HEIGHT / 3,
                SELECTOR_WIDTH, SELECTOR_HEIGHT);
        selectClass.add(indexSelector);

        JButton confirm = new JButton("Confirm");
        confirm.setBounds((UI_WIDTH - BUTTON_WIDTH) / 2, (UI_HEIGHT - BUTTON_HEIGHT) / 3 * 2,
                BUTTON_WIDTH, BUTTON_HEIGHT);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curClass = db.getClasses().get(indexSelector.getSelectedIndex());
                selectClass.dispose();
                switch (action) {
                    case 0:
                        displayCreateAssignment();
                        break;
                    case 1:
                        displaySelectAssignment(1);
                        break;
                    case 2:
                        displaySelectAssignment(2);
                        break;
                    case 3:
                        displaySelectAssignment(3);
                        break;
                }
            }
        });
        selectClass.add(confirm);

        selectClass.setSize(UI_WIDTH, UI_HEIGHT);
        selectClass.setLayout(null);
        selectClass.setVisible(true);
    }
}
