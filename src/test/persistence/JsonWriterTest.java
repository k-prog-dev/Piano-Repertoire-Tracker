package persistence;

import model.Piece;
import model.RepertoireTracker;
import model.StatusType;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: referenced JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            RepertoireTracker r = new RepertoireTracker();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyRepertoire() {
        try {
            RepertoireTracker r = new RepertoireTracker();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRepertoire.json");
            writer.open();
            writer.write(r);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRepertoire.json");
            r = reader.read();
            assertEquals(0, r.repertoireList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralRepertoire() {
        try {
            RepertoireTracker r = new RepertoireTracker();
            r.addPiecetoRep(new Piece("nocturne", "Chopin", "Romantic", 4, StatusType.LEARNING));
            r.addPiecetoRep(new Piece("Prelude", "Bach", "Baroque", 5, StatusType.MASTERED));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRepertoire.json");
            writer.open();
            writer.write(r);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralRepertoire.json");
            r = reader.read();
            assertEquals(2, r.repertoireList().size());
            checkPiece("nocturne", "Chopin", "Romantic", 4, StatusType.LEARNING, 0, 0, 0, r.repertoireList().get(0));
            checkPiece("Prelude", "Bach", "Baroque", 5, StatusType.MASTERED, 0, 0, 0, r.repertoireList().get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
