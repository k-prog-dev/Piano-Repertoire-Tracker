package persistence;

import model.RepertoireTracker;
import model.StatusType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

// CITATION: referenced JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            RepertoireTracker r = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyRepertoire() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRepertoire.json");
        try {
            RepertoireTracker r = reader.read();
            assertEquals(0, r.getTotalNumOfPieces());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralRepertoire() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRepertoire.json");
        try {
            RepertoireTracker r = reader.read();
            assertEquals(2, r.repertoireList().size());
            checkPiece("clair de lune", "Debussy", "classical", 5, StatusType.LEARNING, 0, 100, 85,
                    r.repertoireList().get(0));
            checkPiece("andaluza", "Granados", "classical", 6, StatusType.MASTERED, 4, 0, 0, r.repertoireList().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
