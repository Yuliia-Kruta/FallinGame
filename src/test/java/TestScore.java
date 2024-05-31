import Fallin.engine.Score;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestScore {

    @Test
    public void testScoreInitialization() {
        LocalDate date = LocalDate.of(2024, 5, 30);
        Score score = new Score(100, date);
        assertEquals(100, score.getScore());
        assertEquals(date, score.getDate());
    }

    @Test
    public void testCompareTo() {
        LocalDate date = LocalDate.of(2024, 5, 30);
        Score score1 = new Score(100, date);
        Score score2 = new Score(200, date);

        assertTrue(score1.compareTo(score2) > 0);
        assertTrue(score2.compareTo(score1) < 0);
    }

    @Test
    public void testToString() {
        LocalDate date = LocalDate.of(2024, 5, 30);
        Score score = new Score(100, date);
        assertEquals("100 2024-05-30", score.toString());
    }
}
