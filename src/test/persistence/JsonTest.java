package persistence;

import model.Piece;
import model.StatusType;

import static org.junit.jupiter.api.Assertions.assertEquals;

// CITATION: referenced JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)
public class JsonTest {
    protected void checkPiece(String name, String composer, String genre, int numPages, StatusType status, int rating,
            int practiceStreak, int totalHoursPracticed, Piece piece) {
        assertEquals(name, piece.getPieceName());
        assertEquals(composer, piece.getComposer());
        assertEquals(genre, piece.getGenre());
        assertEquals(numPages, piece.getNumPages());
        assertEquals(status, piece.getStatus());
        assertEquals(rating, piece.getRating());
        assertEquals(practiceStreak, piece.getPracticeStreak());
        assertEquals(totalHoursPracticed, piece.getTotalHoursPracticed());
    }

}
