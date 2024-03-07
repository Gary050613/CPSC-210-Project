package model;

import error.AlreadyInClass;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Represents an actual class. Contains Teachers, TA's, and students. Contains assignment information.
public class Class implements Writable {
    // Course Properties
    private String courseName;
    private String courseDescription;

    // Users
    private List<Teacher> listOfTeachers;
    private List<TA> listOfTAs;
    private List<Student> listOfStudents;

    // Items
    private List<Assignment> listOfAssignments;

    // Constructor
    public Class(String courseName, String courseDescription) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        listOfTeachers = new ArrayList<>();
        listOfTAs = new ArrayList<>();
        listOfStudents = new ArrayList<>();
        listOfAssignments = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Add a new teacher to this class
    public void addTeacher(Teacher teacher) throws AlreadyInClass {
        if (listOfTeachers.contains(teacher)) {
            throw new AlreadyInClass();
        } else {
            listOfTeachers.add(teacher);
            teacher.addClass(this);
        }
    }

    // MODIFIES: this
    // EFFECTS: Add a new TA to this class
    public void addTA(TA ta) throws AlreadyInClass {
        if (listOfTAs.contains(ta)) {
            throw new AlreadyInClass();
        } else {
            listOfTAs.add(ta);
            ta.addClass(this);
        }
    }

    // MODIFIES: this
    // EFFECTS: Add a new Student to this class, assign all current assignments to the student
    public void addStudent(Student stud) throws AlreadyInClass {
        if (listOfStudents.contains(stud)) {
            throw new AlreadyInClass();
        } else {
            listOfStudents.add(stud);
            stud.addClass(this);
            for (Assignment ass : listOfAssignments) {
                ass.addStudent(stud);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Add a new assignment and add to listOfAssignments
    public void addAssignment(Assignment ass) {
        listOfAssignments.add(ass);
    }

    // Getters & Setters
    public String getCourseName() {
        return courseName;
    }

//    public void setCourseName(String newCourseName) {
//        courseName = newCourseName;
//    }

    public String getCourseDescription() {
        return courseDescription;
    }

//    public void setCourseDescription(String newDescription) {
//        courseDescription = newDescription;
//    }

    public List<Student> getListOfStudents() {
        return listOfStudents;
    }

    public List<Assignment> getListOfAssignments() {
        return listOfAssignments;
    }

    public List<Teacher> getListOfTeachers() {
        return listOfTeachers;
    }

    public List<TA> getListOfTAs() {
        return listOfTAs;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", courseName);
        json.put("desc", courseDescription);
        json.put("assignments", assignmentsToJson());
        return json;
    }

    // EFFECTS: Returns all assignments of this class as a JSONArray
    private JSONArray assignmentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Assignment ass : listOfAssignments) {
            jsonArray.put(ass.toJson());
        }
        return jsonArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Class aclass = (Class) o;
        return Objects.equals(courseName, aclass.courseName)
                && Objects.equals(courseDescription, aclass.courseDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseName, courseDescription, listOfTeachers, listOfTAs, listOfStudents,
                listOfAssignments);
    }
}
