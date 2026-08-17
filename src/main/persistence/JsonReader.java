package persistence;

import model.Piece;
import model.RepertoireTracker;
import model.StatusType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

// represents a reader that reads repertoireTracker from file
// allows user to create pieces and add pieces to repertoire
// CITATION: referenced JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: returns a list of repertoireTracker parsed from file,
    // throws IOException if an error occurs
    public RepertoireTracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRepertoireTracker(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses RepertoireTracker from JSON object and returns it
    private RepertoireTracker parseRepertoireTracker(JSONObject jsonObject) {
        RepertoireTracker r = new RepertoireTracker();
        addPieces(r, jsonObject);
        return r;
    }

    // MODIFIES: r
    // EFFECTS: parses pieces from JSON object and adds them to r
    private void addPieces(RepertoireTracker r, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("repertoire");
        for (Object json : jsonArray) {
            JSONObject nextPiece = (JSONObject) json;
            addPiece(r, nextPiece);
        }
    }

    // MODIFIES: r
    // EFFECTS: parses piece from JSON object and adds to r
    private void addPiece(RepertoireTracker r, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String composer = jsonObject.getString("composer");
        String genre = jsonObject.getString("genre");
        int numPages = jsonObject.getInt("numPages");
        StatusType status = StatusType.valueOf(jsonObject.getString("status"));
        Piece piece = new Piece(name, composer, genre, numPages, status);
        r.addPiecetoRep(piece);
        if (status.equals(StatusType.LEARNING)) {
            int practiceStreak = jsonObject.getInt("practice streak");
            int totalHoursPracticed = jsonObject.getInt("total hours practiced");
            piece.setPracticeStreak(practiceStreak);
            piece.setTotalHoursPracticed(totalHoursPracticed);
        }
        if (status.equals(StatusType.MASTERED)) {
            int rating = jsonObject.getInt("rating");
            piece.setRating(rating);
        }
        r.addPiecetoRep(piece);
    }
}