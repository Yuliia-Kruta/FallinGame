import Fallin.engine.Cell;
import Fallin.engine.Player;
import Fallin.engine.Trap;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestTrap {

    @Test
    public void testTrapConstructor() {
        Trap trap = new Trap(1, 2);
        assertEquals(1, trap.getX());
        assertEquals(2, trap.getY());
    }

    @Test
    public void testHandleInteraction() {
        Player player = new Player(0, 0);
        player.setLife(10);
        Cell[][] map = new Cell[10][10];
        Trap trap = new Trap(1, 1);

        map[1][1] = trap;
        trap.handleInteraction(player, null, map, 1, 1);
        assertEquals(8, player.getLife());
        assertNull(map[1][1]);
    }

    @Test
    public void testGetSymbol() {
        Trap trap = new Trap(0, 0);
        assertEquals("T", trap.getSymbol());
    }
}
