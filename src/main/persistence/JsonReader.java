package persistence;

import model.Assignment;
import model.DataBase;
import model.Class;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads database from JSON data stored in file
// Model adapted from JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads database from file and returns Classes information;
    // throws IOException if an error occurs reading data from file
    public List<Class> readClasses() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDataBase(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses database from JSON object and returns classes info
    private List<Class> parseDataBase(JSONObject jsonObject) {
        List<Class> classes = new ArrayList<>();
        JSONArray listOfClasses = jsonObject.getJSONArray("classes");
        addClasses(classes, listOfClasses);
        return classes;
    }

    // MODIFIES: classes
    // EFFECTS: parses classes from JSON object and adds them to list of classes
    private void addClasses(List<Class> classes, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject classObj = (JSONObject) json;
            addClass(classes, classObj);
        }
    }

    // MODIFIES: classes
    // EFFECTS: adds class to classes
    private void addClass(List<Class> classes, JSONObject jsonObject) {
        classes.add(parseClass(jsonObject));
    }

    // EFFECTS: parses class information and return a class object
    private Class parseClass(JSONObject jsonObject) {
        String courseName = jsonObject.getString("name");
        String courseDescription = jsonObject.getString("desc");
        Class clas = new Class(courseName, courseDescription);
        JSONArray jsonArray = jsonObject.getJSONArray("assignments");
        addAssignments(clas, jsonArray);
        return clas;
    }

    // MODIFIES: clas
    // EFFECTS: adds assignments to a class
    private void addAssignments(Class clas, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject assObj = (JSONObject) json;
            addAssignment(clas, assObj);
        }
    }

    // MODIFIES: clas
    // EFFECTS: adds a assignment to a class
    private void addAssignment(Class clas, JSONObject jsonObject) {
        clas.addAssignment(parseAssignment(jsonObject, clas));
    }

    // EFFECTS: parses an assignment and return it as an assignment object
    private Assignment parseAssignment(JSONObject jsonObject, Class clas) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("desc");
        LocalDateTime dueDate = LocalDateTime.parse(jsonObject.getString("due"));
        int availableMark = jsonObject.getInt("mark");
        Assignment ass = new Assignment(name, description, dueDate, availableMark, clas);
        return ass;
    }
}