import Fallin.engine.Exit;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class TestExit {

    @Test
    public void testExitConstructor() {
        Exit exit = new Exit(0, 9);
        assertEquals(0, exit.getX());
        assertEquals(9, exit.getY());
    }

    @Test
    public void testGetSymbol() {
        Exit exit = new Exit(0, 9);
        assertEquals("E", exit.getSymbol());
    }
}
