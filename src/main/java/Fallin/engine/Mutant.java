package Fallin.engine;

import java.io.Serializable;
import java.util.List;

public class Mutant extends Cell implements Serializable {

    private static final long serialVersionUID = 1L;

    public Mutant(int x, int y) {
        super(x, y);
        setStyle("-fx-background-color:purple;");
    }

    @Override
    public void handleInteraction(Player player, List<Mutant> mutants, Cell[][] map, int x, int y) {
        player.setLife(player.getLife() - 4);
        super.handleInteraction(player, mutants, map, x, y);
    }

    @Override
    public String getSymbol() {
        return "X";
    }

    @Override
    public String getCellStyle() {
        return "-fx-background-image: url(\"mutant_icon.png\"); -fx-background-size: cover;";
    }

}
