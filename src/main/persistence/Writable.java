package persistence;

import org.json.JSONObject;

// interface that runs the toJson method
// CITATION: referenced JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
