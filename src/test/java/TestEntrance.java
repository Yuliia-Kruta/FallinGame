import Fallin.engine.Entrance;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class TestEntrance {

    @Test
    public void testEntranceConstructor() {
        Entrance entrance = new Entrance(9, 0);
        assertEquals(9, entrance.getX());
        assertEquals(0, entrance.getY());
    }

    @Test
    public void testGetSymbol() {
        Entrance entrance = new Entrance(9, 0);
        assertEquals("S", entrance.getSymbol());
    }
}
