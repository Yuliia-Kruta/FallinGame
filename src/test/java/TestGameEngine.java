import Fallin.engine.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGameEngine {

    private GameEngine gameEngine;

    /**
     * Initialises a new GameEngine instance before each test
     */
    @BeforeEach
    public void setUp() {
        gameEngine = new GameEngine(10, 5);
    }

    @Test
    public void testConstructor() {
        assertEquals(10, gameEngine.getSize());
        assertEquals(5, gameEngine.getMutants().size());
    }

    @Test
    public void testGenerateMap() {
        gameEngine.generateMap(10, 5);
        assertNotNull(gameEngine.getMap());
        assertEquals("class Fallin.engine.Entrance", gameEngine.getMap()[gameEngine.getSize() - 1][0].getClass().toString());
        assertEquals("class Fallin.engine.Exit", gameEngine.getMap()[0][gameEngine.getSize() - 1].getClass().toString());
    }

    @Test
    public void testPlaceItems() {
        int placedTreasures = 4;
        int placedTraps = 3;
        int placedMedical = 2;
        gameEngine.placeItems(10, Treasure.class, placedTreasures);
        gameEngine.placeItems(10, Trap.class, placedTraps);
        gameEngine.placeItems(10, MedicalUnit.class, placedMedical);

        int treasureCount = 0;
        int medicalUnitCount = 0;
        int trapCount = 0;
        int mutantCount = 0;

        for (int i = 0; i < gameEngine.getSize(); i++) {
            for (int j = 0; j < gameEngine.getSize(); j++) {
                if (gameEngine.getMap()[i][j] instanceof Treasure) treasureCount++;
                if (gameEngine.getMap()[i][j] instanceof Trap) trapCount++;
                if (gameEngine.getMap()[i][j] instanceof MedicalUnit) medicalUnitCount++;
                if (gameEngine.getMap()[i][j] instanceof Mutant) mutantCount++;
            }
        }

        // 8 treasures, 5 traps adn 2 medical units will be already placed when the map is generated
        assertEquals(placedTreasures+8, treasureCount);
        assertEquals(placedTraps+5, trapCount);
        assertEquals(placedMedical+2, medicalUnitCount);
        assertEquals(gameEngine.getMutants().size(), mutantCount);
    }

    @Test
    public void testIsValidMove() {
        // Test the isValidMove method of GameEngine
        assertTrue(gameEngine.isValidMove(0, 0));
        assertTrue(gameEngine.isValidMove(5, 5));
        assertFalse(gameEngine.isValidMove(0, -1));
        assertFalse(gameEngine.isValidMove(11, 10));
    }

    @Test
    public void testMovePlayer() {
        Player player = gameEngine.getPlayer();
        int initialX = player.getX();
        int initialY = player.getY();

        // Move player up
        gameEngine.movePlayer(2);
        assertEquals(initialX - 1, player.getX());
        assertEquals(initialY, player.getY());

        // Move player down
        gameEngine.movePlayer(4);
        assertEquals(initialX, player.getX());
        assertEquals(initialY, player.getY());

        // Move player right
        gameEngine.movePlayer(3);
        assertEquals(initialX, player.getX());
        assertEquals(initialY+1, player.getY());

        // Move player left
        gameEngine.movePlayer(1);
        assertEquals(initialX, player.getX());
        assertEquals(initialY, player.getY());

        // Test moving out of bounds
        gameEngine.movePlayer(1);
        assertEquals(initialX, player.getX());
        assertEquals(initialY, player.getY());
    }


    @Test
    public void testHandleCell() {
        gameEngine.handleCell(0, 0);
        assertNull(gameEngine.getMap()[0][0]);

        gameEngine.getMap()[0][0] = new Treasure(0, 0);
        gameEngine.handleCell(0, 0);
        assertNull(gameEngine.getMap()[0][0]); // Treasure should be removed after interaction
    }

















}
