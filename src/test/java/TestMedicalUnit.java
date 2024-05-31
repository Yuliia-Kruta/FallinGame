import Fallin.engine.Cell;
import Fallin.engine.MedicalUnit;
import Fallin.engine.Player;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestMedicalUnit {

    @Test
    public void testMedicalUnitConstructor() {
        MedicalUnit medicalUnit = new MedicalUnit(1, 2);
        assertEquals(1, medicalUnit.getX());
        assertEquals(2, medicalUnit.getY());
    }

    @Test
    public void testHandleInteraction() {
        Player player = new Player(0, 0);
        player.setLife(7);
        Cell[][] map = new Cell[10][10];
        MedicalUnit medicalUnit = new MedicalUnit(1, 1);

        map[1][1] = medicalUnit;
        medicalUnit.handleInteraction(player, null, map, 1, 1);
        assertEquals(10, player.getLife());
        assertNull(map[1][1]);
    }

    @Test
    public void testGetSymbol() {
        MedicalUnit medicalUnit = new MedicalUnit(0, 0);
        assertEquals("M", medicalUnit.getSymbol());
    }
}
