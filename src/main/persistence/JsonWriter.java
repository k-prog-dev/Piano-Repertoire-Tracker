package persistence;

import model.Event;
import model.EventLog;
import model.RepertoireTracker;

import java.io.*;

import org.json.JSONObject;

// Represents a writer that writes JSON representation of repertoire to file
// CITATION: referenced JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)
public class JsonWriter {
    private PrintWriter writer;
    private static final int TAB = 4;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of r to file
    public void write(RepertoireTracker r) {
        JSONObject json = r.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        EventLog.getInstance().logEvent(new Event("Pieces have been saved to the repertoire"));
        writer.print(json);
    }
}