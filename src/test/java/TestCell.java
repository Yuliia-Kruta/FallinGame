import Fallin.engine.Cell;
import Fallin.engine.Player;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestCell {

    @Test
    public void testCellConstructor() {
        Cell cell = new Cell(1, 2);
        assertEquals(1, cell.getX());
        assertEquals(2, cell.getY());
    }

    @Test
    public void testHandleInteraction() {
        Player player = new Player(0, 0);
        Cell[][] map = new Cell[10][10];
        Cell cell = new Cell(1, 1);

        map[1][1] = cell;
        cell.handleInteraction(player, null, map, 1, 1);
        assertNull(map[1][1]);
    }

    @Test
    public void testGetSymbol() {
        Cell cell = new Cell(0, 0);
        assertEquals("_", cell.getSymbol());
    }
}
