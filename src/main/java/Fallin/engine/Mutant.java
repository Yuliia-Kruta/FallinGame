package Fallin.engine;

import java.util.List;

public class Mutant extends Cell{

    public Mutant(int x, int y) {
        super(x, y);
        setStyle("-fx-background-color:purple;");
    }

    @Override
    public void handleInteraction(Player player, List<Mutant> mutants, Cell[][] map, int x, int y) {
        player.setLife(player.getLife() - 4);
        mutants.remove(this);
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
