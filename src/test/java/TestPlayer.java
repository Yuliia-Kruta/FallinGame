import Fallin.engine.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class TestPlayer {
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player(9, 0);
    }

    @Test
    public void testInitialState() {
        assertEquals(9, player.getX());
        assertEquals(0, player.getY());
        assertEquals(10, player.getLife());
        assertEquals(0, player.getTreasures());
        assertEquals(0, player.getSteps());
    }


    @Test
    public void testIncrementTreasures() {
        Player player = new Player(0, 0);
        player.incrementTreasures();
        assertEquals(1, player.getTreasures());
    }

    @Test
    public void testIncrementSteps() {
        Player player = new Player(0, 0);
        player.incrementSteps();
        assertEquals(1, player.getSteps());
    }
}
