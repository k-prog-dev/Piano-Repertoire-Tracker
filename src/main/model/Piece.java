package model;

import org.json.JSONObject;

import persistence.Writable;

// creates a piece with a name, composer, genre, and number of pages
public class Piece implements Writable {
    private String name;
    private String composer;
    private String genre;
    private int numPages;
    private StatusType status;
    private int rating;
    private int practiceStreak;
    private int totalHoursPracticed;

    // constructs new piece with a name, composer, genre, number of pages, and
    // status
    public Piece(String nameOfPiece, String composer, String genre, int numPages, StatusType status) {
        this.name = nameOfPiece;
        this.composer = composer;
        this.genre = genre;
        this.numPages = numPages;
        this.status = status;
        this.rating = 0;
        this.practiceStreak = 0;
        this.totalHoursPracticed = 0;
    }

    // getters
    public String getPieceName() {
        return name;
    }

    public String getComposer() {
        return composer;
    }

    public String getGenre() {
        return genre;
    }

    public int getNumPages() {
        return numPages;
    }

    public StatusType getStatus() {
        return status;
    }

    public int getTotalHoursPracticed() {
        return totalHoursPracticed;
    }

    public int getPracticeStreak() {
        return practiceStreak;
    }

    public int getRating() {
        return rating;
    }

    // MODIFIES: this
    // EFFECTS: sets the total number of hours practiced
    public void addTotalHoursPracticed(int practiced) {
        totalHoursPracticed += practiced;
    }

    // MODIFIES: this
    // EFFECTS: incraments the practice streak by one to represent one day
    public void incramentPracticeStreak() {
        practiceStreak++;
    }

    // EFFECTS: sets rating to the rating given by user
    public void setRating(int rating) {
        if (this.status.equals(StatusType.MASTERED)) {
            this.rating = rating;
        }
    }

    public void setStatus(StatusType status) {
        this.status = status;
        EventLog.getInstance().logEvent(new Event("A piece has been updated to the " + status + " status"));
    }

    public void setPracticeStreak(int streak) {
        this.practiceStreak = streak;
    }

    public void setTotalHoursPracticed(int hours) {
        this.totalHoursPracticed = hours;
    }

    // REQUIRES: the status has to be in learning, with practiceStreak and days to
    // be 0
    // EFFECTS: initializes new practice streak and 0
    // number of days practiced
    public void startPracticing() {
        if (this.status.equals(StatusType.LEARNING) && this.practiceStreak == 0 && this.totalHoursPracticed == 0) {
            this.practiceStreak = 0;
            this.totalHoursPracticed = 0;
        }
    }

    // EFFECTS: shows the piece in a consise format
    public String showPiece() {
        return name + " by " + composer + " composed in the " + genre + " era ";
    }

    // EFFECTS: represents the data for toJson
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("composer", composer);
        json.put("genre", genre);
        json.put("numPages", numPages);
        json.put("status", status);
        json.put("practice streak", practiceStreak);
        json.put("total hours practiced", totalHoursPracticed);
        json.put("rating", rating);
        return json;
    }
}