package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static model.StatusType.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PieceTest {
    private Piece testPiece;

    @BeforeEach
    void runBefore() {
        testPiece = new Piece("Rêverie", "Debussy", "Late-Romantic", 8, NOT_STARTED);
    }

    @Test
    void testConstructor() {
        assertEquals("Rêverie", testPiece.getPieceName());
        assertEquals("Debussy", testPiece.getComposer());
        assertEquals("Late-Romantic", testPiece.getGenre());
        assertEquals(8, testPiece.getNumPages());
        assertEquals(NOT_STARTED, testPiece.getStatus());
        assertEquals(0, testPiece.getPracticeStreak());
        assertEquals(0, testPiece.getTotalHoursPracticed());
        String expected = testPiece.getPieceName() + " by " + testPiece.getComposer() + " composed in the "
                + testPiece.getGenre() + " era ";
        assertEquals(expected, testPiece.showPiece());

    }

    @Test
    void testSetRating() {
        testPiece.setRating(4);
        assertEquals(0, testPiece.getRating()); // should be 0 since it is not in the MASTERED repertoire
    }

    @Test
    void testSetStatus() {
        testPiece.setStatus(NOT_STARTED);
        assertEquals(NOT_STARTED, testPiece.getStatus());
        testPiece.setStatus(MASTERED);
        assertEquals(MASTERED, testPiece.getStatus());
        testPiece.setStatus(NOT_STARTED);
        testPiece.setStatus(LEARNING);
        assertEquals(LEARNING, testPiece.getStatus());
        assertEquals(0, testPiece.getPracticeStreak());
        assertEquals(0, testPiece.getTotalHoursPracticed());
        testPiece.setPracticeStreak(1);
        testPiece.setTotalHoursPracticed(40);
        assertEquals(40, testPiece.getTotalHoursPracticed());
        testPiece.setStatus(MASTERED);
        assertEquals(40, testPiece.getTotalHoursPracticed());
        assertEquals(1, testPiece.getPracticeStreak());
    }

    @Test
    void testStartPracticing() {
        testPiece.setStatus(LEARNING);
        testPiece.setPracticeStreak(0);
        testPiece.setTotalHoursPracticed(0);
        testPiece.startPracticing();
        assertEquals(0, testPiece.getPracticeStreak());
        assertEquals(0, testPiece.getTotalHoursPracticed());
        testPiece.setPracticeStreak(1);
        testPiece.setTotalHoursPracticed(40);
        testPiece.setStatus(MASTERED);
        testPiece.startPracticing();
        assertEquals(1, testPiece.getPracticeStreak());
        assertEquals(40, testPiece.getTotalHoursPracticed());

    }

    @Test
    void testStartPracticingWithDifferentStatus() {
        testPiece.setStatus(LEARNING);
        testPiece.setPracticeStreak(0);
        testPiece.setTotalHoursPracticed(0);
        testPiece.startPracticing();
        assertEquals(0, testPiece.getPracticeStreak());
        assertEquals(0, testPiece.getTotalHoursPracticed());
        testPiece.setPracticeStreak(0);
        testPiece.setTotalHoursPracticed(100);
        testPiece.setStatus(MASTERED);
        testPiece.startPracticing();
        assertEquals(0, testPiece.getPracticeStreak());
        assertEquals(100, testPiece.getTotalHoursPracticed());
    }

    @Test
    void testStartPracticeWithStreak() {
        testPiece.setStatus(LEARNING);
        testPiece.setPracticeStreak(10);
        testPiece.setTotalHoursPracticed(0);
        testPiece.startPracticing();
        assertEquals(10, testPiece.getPracticeStreak());
        assertEquals(0, testPiece.getTotalHoursPracticed());
    }

    @Test
    void testStartPracticeWithPractice() {
        testPiece.setStatus(LEARNING);
        testPiece.setPracticeStreak(0);
        testPiece.setTotalHoursPracticed(0);
        testPiece.startPracticing();
        assertEquals(0, testPiece.getPracticeStreak());
        assertEquals(0, testPiece.getTotalHoursPracticed());
        testPiece.setPracticeStreak(0);
        testPiece.setTotalHoursPracticed(100);
        testPiece.startPracticing();
        assertEquals(0, testPiece.getPracticeStreak());
        assertEquals(100, testPiece.getTotalHoursPracticed());
    }

    @Test
    void testAddsTotalHoursPracticed() {
        assertEquals(0, testPiece.getTotalHoursPracticed());
        testPiece.addTotalHoursPracticed(10);
        assertEquals(10, testPiece.getTotalHoursPracticed());
        testPiece.addTotalHoursPracticed(5);
        assertEquals(15, testPiece.getTotalHoursPracticed());
        testPiece.addTotalHoursPracticed(5);
        testPiece.addTotalHoursPracticed(10);
        assertEquals(30, testPiece.getTotalHoursPracticed());
    }

    @Test
    void testIncramentPracticeStreak() {
        assertEquals(0, testPiece.getPracticeStreak());
        testPiece.incramentPracticeStreak();
        assertEquals(1, testPiece.getPracticeStreak());
        testPiece.incramentPracticeStreak();
        testPiece.incramentPracticeStreak();
        assertEquals(3, testPiece.getPracticeStreak());
    }
}
