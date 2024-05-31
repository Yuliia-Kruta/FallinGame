import Fallin.engine.Cell;
import Fallin.engine.Player;
import Fallin.engine.Treasure;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestTreasure {

    @Test
    public void testTreasureConstructor() {
        Treasure treasure = new Treasure(1, 2);
        assertEquals(1, treasure.getX());
        assertEquals(2, treasure.getY());
    }

    @Test
    public void testHandleInteraction() {
        Player player = new Player(0, 0);
        Cell[][] map = new Cell[10][10];
        Treasure treasure = new Treasure(1, 1);

        map[1][1] = treasure;
        treasure.handleInteraction(player, null, map, 1, 1);
        assertEquals(1, player.getTreasures());
        assertNull(map[1][1]);
    }

    @Test
    public void testGetSymbol() {
        Treasure treasure = new Treasure(0, 0);
        assertEquals("G", treasure.getSymbol());
    }
}
