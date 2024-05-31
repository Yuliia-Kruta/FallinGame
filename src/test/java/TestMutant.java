import Fallin.engine.Cell;
import Fallin.engine.Mutant;
import Fallin.engine.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestMutant {

    @Test
    public void testMutantConstructor() {
        Mutant mutant = new Mutant(1, 2);
        assertEquals(1, mutant.getX());
        assertEquals(2, mutant.getY());
    }

    @Test
    public void testHandleInteraction() {
        Player player = new Player(0, 0);
        player.setLife(10);
        Cell[][] map = new Cell[10][10];
        Mutant mutant = new Mutant(1, 1);
        List<Mutant> mutants = new ArrayList<>();
        mutants.add(mutant);

        map[1][1] = mutant;
        mutant.handleInteraction(player, mutants, map, 1, 1);
        assertEquals(6, player.getLife());
        assertNull(map[1][1]);
        assertFalse(mutants.contains(mutant));
    }

    @Test
    public void testGetSymbol() {
        Mutant mutant = new Mutant(0, 0);
        assertEquals("X", mutant.getSymbol());
    }
}
