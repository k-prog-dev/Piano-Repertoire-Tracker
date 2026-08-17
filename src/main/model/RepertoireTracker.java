package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// creates a Repertoire Tracker to hold all the pieces in one list, 
// using an arraylist of pieces
public class RepertoireTracker implements Writable {
    private ArrayList<Piece> repertoire;

    // Constructor initializing a repertoireTracker with no pieces added
    public RepertoireTracker() {
        repertoire = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds piece into the repertoire, return true if successful
    // return false if not
    public boolean addPiecetoRep(Piece piece) {
        if (!repertoire.contains(piece)) {
            repertoire.add(piece);
            EventLog.getInstance().logEvent(new Event(piece.getPieceName() + " has been added to the repertoire"));
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: removes piece by its name from repertoire
    public boolean removePiece(String name) {
        for (Piece p : repertoire) {
            if (p.getPieceName().equals(name)) {
                repertoire.remove(p);
                EventLog.getInstance().logEvent(new Event(name + " has been removed from the repertoire"));
                return true;
            }
        }
        return false;
    }

    // EFFECTS: gets the list of all pieces in the repertoire
    public ArrayList<Piece> repertoireList() {
        EventLog.getInstance().logEvent(new Event("Viewed all pieces in the repertoire"));
        return repertoire;
    }

    // EFFECTS: gets total number of pieces in repertoire
    public int getTotalNumOfPieces() {
        return repertoire.size();

    }

    // EFFECTS: find piece by the given name
    // returns null if none found
    public Piece findPiece(String name) {
        for (Piece p : repertoire) {
            if (p.getPieceName().equals(name)) {
                return p;
            }
        }
        return null;

    }

    // EFFECTS: gets list of pieces from each status repertoire
    public ArrayList<Piece> getPiecesByStatus(StatusType status) {
        ArrayList<Piece> pieceByStatus = new ArrayList<Piece>();
        for (Piece p : repertoire) {
            if (p.getStatus().equals(status)) {
                pieceByStatus.add(p);
            }
        }
        EventLog.getInstance().logEvent(new Event("Pieces from " + status + " have been retrieved."));
        return pieceByStatus;
    }

    // EFFECTS: gets total number of pieces in each status repertoire
    public int getTotalNumOfPiecesByStatus(StatusType status) {
        int totalNum = 0;
        for (Piece p : repertoire) {
            if (p.getStatus().equals(status)) {
                totalNum++;
            }
        }
        return totalNum++;
    }

    // EFFECTS: gets list of pieces from each composer
    public List<Piece> getPiecesByComposer(String name) {
        ArrayList<Piece> pieceByComposer = new ArrayList<Piece>();
        for (Piece p : repertoire) {
            if (p.getComposer().equals(name)) {
                pieceByComposer.add(p);
            }
        }
        return pieceByComposer;
    }

    // EFFECTS: gets list of pieces that are less than numPages provided
    public List<Piece> getPiecesByTotalNumOfPages(int totalNum) {
        ArrayList<Piece> pieceByPages = new ArrayList<Piece>();
        for (Piece p : repertoire) {
            if (p.getNumPages() <= totalNum) {
                pieceByPages.add(p);
            }
        }
        return pieceByPages;
    }

    // EFFECTS: gets number of pieces from each composer
    public int getTotalNumOfPiecesByComposer(String composer) {
        int totalPiecesByComposer = 0;
        for (Piece p : repertoire) {
            if (p.getComposer().equals(composer)) {
                totalPiecesByComposer++;
            }
        }
        return totalPiecesByComposer;
    }

    // EFFECTS: gets pieces in genre
    public List<Piece> getPiecesInGenre(String genre) {
        ArrayList<Piece> pieceInGenre = new ArrayList<Piece>();
        for (Piece p : repertoire) {
            if (p.getGenre().equals(genre)) {
                pieceInGenre.add(p);
            }
        }
        return pieceInGenre;
    }

    // EFFECTS: gets total number of pieces from each genre
    public int getTotalNumofPiecesInGenre(String genre) {
        int totalPiecesFromGenre = 0;
        for (Piece p : repertoire) {
            if (p.getGenre().equals(genre)) {
                totalPiecesFromGenre++;
            }
        }
        return totalPiecesFromGenre;
    }

    // EFFECTS: changes status and carries out necessary actions;
    // if status updates to LEARNING, carry out the method startPracticing
    // automatically,
    // if status updates to MASTERED, initialize a rating of 0, which can be
    // changed by user later.
    public void updateStatus(Piece piece, StatusType status) {
        piece.setStatus(status);
        if (status.equals(StatusType.LEARNING)) {
            piece.startPracticing();
        }
        if (status.equals(StatusType.MASTERED)) {
            piece.setRating(0);
        }
    }

    // EFFECTS: returns status of the piece depending on the piece
    public StatusType getPieceStatus(Piece piece) {
        return piece.getStatus();
    }

    // EFFECTS: sends data to the Json file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("repertoire", repertoireToJson());
        return json;
    }

    // EFFECTS: puts the piece in the repertoire in Json
    public JSONArray repertoireToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Piece p : repertoire) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}