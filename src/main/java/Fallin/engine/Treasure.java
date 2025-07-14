package Fallin.engine;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

public class Treasure extends Cell implements Serializable {

    private static final long serialVersionUID = 1L;

    public Treasure(int x, int y) {
        super(x, y);
        setStyle("-fx-background-color:gold;");
    }

    @Override
    public void handleInteraction(Player player, List<Mutant> mutants, Cell[][] map, int x, int y) {
        player.incrementTreasures();
        super.handleInteraction(player, mutants, map, x, y);
    }
    @Override
    public String getSymbol() {
        return "G";
    }

    @Override
    public String getCellStyle() {
        return "-fx-background-image: url(\"treasure_icon.png\"); -fx-background-size: cover;";
    }
}