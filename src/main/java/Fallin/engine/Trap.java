package Fallin.engine;

import java.io.Serializable;
import java.util.List;

public class Trap extends Cell implements Serializable {

    private static final long serialVersionUID = 1L;

    public Trap(int x, int y) {
        super(x, y);
        setStyle("-fx-background-color:gray;");
    }

    @Override
    public void handleInteraction(Player player, List<Mutant> mutants, Cell[][] map, int x, int y) {
        player.setLife(player.getLife()-2);
        super.handleInteraction(player, mutants, map, x, y);
    }
    @Override
    public String getSymbol() {
        return "T";
    }

    @Override
    public String getCellStyle() {
        return "-fx-background-image: url(\"trap_icon.png\"); -fx-background-size: cover;";
    }
}
