package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static model.StatusType.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RepertoireTrackerTest {
    private Piece piece1;
    private Piece piece2;
    private Piece piece3;
    private RepertoireTracker testRepertoire;

    @BeforeEach
    void runBefore() {
        piece1 = new Piece("Rêverie", "Debussy", "Late-Romantic", 8, NOT_STARTED);
        piece2 = new Piece("Nocturne op 9 no 2", "Chopin", "Romantic", 3, LEARNING);
        piece3 = new Piece("Interstellar Main Theme", "Zimmer", "Contemporary", 6, MASTERED);
        testRepertoire = new RepertoireTracker();
    }

    @Test
    void testConstructor() {
        assertTrue(testRepertoire.repertoireList().isEmpty());
        assertEquals(0, testRepertoire.getTotalNumOfPieces());
        assertEquals(0, testRepertoire.repertoireList().size());
    }

    @Test
    void testFindPiece() {
        assertTrue(testRepertoire.addPiecetoRep(piece1));
        assertTrue(testRepertoire.addPiecetoRep(piece2));
        assertTrue(testRepertoire.addPiecetoRep(piece3));

        assertEquals(piece1, testRepertoire.findPiece("Rêverie"));
        assertEquals(piece3, testRepertoire.findPiece("Interstellar Main Theme"));
        assertEquals(null, testRepertoire.findPiece("Waltz in A-minor"));
    }

    @Test
    void testAddOnePieceToRep() {
        assertTrue(testRepertoire.repertoireList().isEmpty());
        assertEquals(0, testRepertoire.getTotalNumOfPieces());

        assertTrue(testRepertoire.addPiecetoRep(piece1));

        assertEquals(1, testRepertoire.getTotalNumOfPieces());
        assertEquals(1, testRepertoire.repertoireList().size());
        assertEquals(piece1, testRepertoire.repertoireList().get(0));

    }

    @Test
    void testAddMultiplePiecesToRep() {
        assertTrue(testRepertoire.repertoireList().isEmpty());
        assertEquals(0, testRepertoire.getTotalNumOfPieces());

        assertTrue(testRepertoire.addPiecetoRep(piece1));
        assertTrue(testRepertoire.addPiecetoRep(piece2));
        assertFalse(testRepertoire.addPiecetoRep(piece2));
        assertTrue(testRepertoire.addPiecetoRep(piece3));

        assertEquals(3, testRepertoire.getTotalNumOfPieces());
        assertEquals(piece1, testRepertoire.repertoireList().get(0));
        assertEquals(piece2, testRepertoire.repertoireList().get(1));
        assertEquals(piece3, testRepertoire.repertoireList().get(2));
    }

    @Test
    void testRemoveMultiplePiecesFromRep() {
        assertTrue(testRepertoire.addPiecetoRep(piece1));
        assertTrue(testRepertoire.addPiecetoRep(piece2));
        assertFalse(testRepertoire.addPiecetoRep(piece2));
        assertTrue(testRepertoire.addPiecetoRep(piece3));

        assertEquals(3, testRepertoire.getTotalNumOfPieces());
        assertEquals(piece1, testRepertoire.repertoireList().get(0));
        assertEquals(piece2, testRepertoire.repertoireList().get(1));
        assertEquals(piece3, testRepertoire.repertoireList().get(2));

        assertTrue(testRepertoire.removePiece(piece2.getPieceName()));
        assertFalse(testRepertoire.removePiece(piece2.getPieceName()));

        assertEquals(2, testRepertoire.getTotalNumOfPieces());
        assertEquals(piece1, testRepertoire.repertoireList().get(0));
        assertEquals(piece3, testRepertoire.repertoireList().get(1));
    }

    @Test
    void testGetPiecesByStatus() {
        assertTrue(testRepertoire.addPiecetoRep(piece1));
        assertTrue(testRepertoire.addPiecetoRep(piece2));
        assertTrue(testRepertoire.addPiecetoRep(piece3));

        assertEquals(3, testRepertoire.getTotalNumOfPieces());
        assertEquals(piece1, testRepertoire.getPiecesByStatus(NOT_STARTED).get(0));
        assertEquals(1, testRepertoire.getTotalNumOfPiecesByStatus(NOT_STARTED));

        Piece piece4 = new Piece("Rondo", "Kuhlau", "Classical", 5, LEARNING);
        assertTrue(testRepertoire.addPiecetoRep(piece4));

        assertEquals(2, testRepertoire.getTotalNumOfPiecesByStatus(LEARNING));
        assertEquals(piece2, testRepertoire.getPiecesByStatus(LEARNING).get(0));
        assertEquals(piece4, testRepertoire.getPiecesByStatus(LEARNING).get(1));

        assertEquals(1, testRepertoire.getTotalNumOfPiecesByStatus(MASTERED));
        assertEquals(piece3, testRepertoire.getPiecesByStatus(MASTERED).get(0));
    }

    @Test
    void testGetPiecesByComposer() {
        assertTrue(testRepertoire.addPiecetoRep(piece1));
        assertTrue(testRepertoire.addPiecetoRep(piece2));
        assertTrue(testRepertoire.addPiecetoRep(piece3));
        Piece piece4 = new Piece("Fantasie-Impromptu", "Chopin", "Classical", 8, MASTERED);
        assertTrue(testRepertoire.addPiecetoRep(piece4));

        assertEquals(4, testRepertoire.getTotalNumOfPieces());
        assertEquals(2, testRepertoire.getTotalNumOfPiecesByComposer("Chopin"));
        assertEquals(piece2, testRepertoire.getPiecesByComposer("Chopin").get(0));
        assertEquals(piece4, testRepertoire.getPiecesByComposer("Chopin").get(1));

        assertEquals(1, testRepertoire.getTotalNumOfPiecesByComposer("Debussy"));
        assertEquals(piece1, testRepertoire.getPiecesByComposer("Debussy").get(0));

        assertEquals(1, testRepertoire.getTotalNumOfPiecesByComposer("Zimmer"));
        assertEquals(piece3, testRepertoire.getPiecesByComposer("Zimmer").get(0));
    }

    @Test
    void testGetPiecesByGenre() {
        assertTrue(testRepertoire.addPiecetoRep(piece1));
        assertTrue(testRepertoire.addPiecetoRep(piece2));
        Piece piece4 = new Piece("Fantasie-Impromptu", "Chopin", "Classical", 8, MASTERED);
        assertTrue(testRepertoire.addPiecetoRep(piece4));
        Piece piece5 = new Piece("Rondo", "Kuhlau", "Classical", 5, LEARNING);
        assertTrue(testRepertoire.addPiecetoRep(piece5));

        assertEquals(4, testRepertoire.getTotalNumOfPieces());

        assertEquals(2, testRepertoire.getTotalNumofPiecesInGenre("Classical"));
        assertEquals(piece4, testRepertoire.getPiecesInGenre("Classical").get(0));
        assertEquals(piece5, testRepertoire.getPiecesInGenre("Classical").get(1));

        assertEquals(1, testRepertoire.getTotalNumofPiecesInGenre("Late-Romantic"));
        assertEquals(piece1, testRepertoire.getPiecesInGenre("Late-Romantic").get(0));

        assertEquals(1, testRepertoire.getTotalNumofPiecesInGenre("Romantic"));
        assertEquals(piece2, testRepertoire.getPiecesInGenre("Romantic").get(0));

    }

    @Test
    void testGetPiecesByNumOfPages() {
        assertTrue(testRepertoire.addPiecetoRep(piece1));
        assertTrue(testRepertoire.addPiecetoRep(piece2));
        assertEquals(2, testRepertoire.getTotalNumOfPieces());
        assertEquals(piece1, testRepertoire.getPiecesByTotalNumOfPages(8).get(0));
        assertEquals(piece2, testRepertoire.getPiecesByTotalNumOfPages(8).get(1));
        assertEquals(2, testRepertoire.getPiecesByTotalNumOfPages(8).size());

        assertTrue(testRepertoire.getPiecesByTotalNumOfPages(1).isEmpty());

    }

    @Test
    void testUpdateStatus() {
        assertTrue(testRepertoire.addPiecetoRep(piece1));
        assertTrue(testRepertoire.addPiecetoRep(piece2));

        assertEquals(StatusType.NOT_STARTED, testRepertoire.getPieceStatus(piece1));
        assertEquals(1, testRepertoire.getTotalNumOfPiecesByStatus(NOT_STARTED));

        testRepertoire.updateStatus(piece1, LEARNING);
        assertEquals(StatusType.LEARNING, testRepertoire.getPieceStatus(piece1));
        assertEquals(0, testRepertoire.getTotalNumOfPiecesByStatus(StatusType.NOT_STARTED));
        assertEquals(2, testRepertoire.getTotalNumOfPiecesByStatus(StatusType.LEARNING));
    }

    @Test
    void testUpdateStatusMastered() {
        assertTrue(testRepertoire.addPiecetoRep(piece1));
        assertTrue(testRepertoire.addPiecetoRep(piece2));

        testRepertoire.updateStatus(piece1, MASTERED);
        testRepertoire.updateStatus(piece2, MASTERED);

        assertEquals(StatusType.MASTERED, testRepertoire.getPieceStatus(piece1));
        assertEquals(StatusType.MASTERED, testRepertoire.getPieceStatus(piece2));
        assertEquals(0, testRepertoire.findPiece("Rêverie").getRating());
        assertEquals(0, testRepertoire.getTotalNumOfPiecesByStatus(StatusType.NOT_STARTED));
        assertEquals(0, testRepertoire.getTotalNumOfPiecesByStatus(StatusType.LEARNING));
        assertEquals(2, testRepertoire.getTotalNumOfPiecesByStatus(StatusType.MASTERED));
    }
}
