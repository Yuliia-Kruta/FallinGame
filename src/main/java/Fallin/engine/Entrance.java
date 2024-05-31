package Fallin.engine;

import java.util.List;

public class Entrance extends Cell{


    public Entrance(int x, int y) {
        super(x, y);
        setStyle("-fx-background-color:green;");
    }

    @Override
    public void handleInteraction(Player player, List<Mutant> mutants, Cell[][] map, int x, int y) {
    }
    @Override
    public String getSymbol() {
        return "S";
    }

    @Override
    public String getCellStyle() {
        return "-fx-background-image: url(\"start_icon.png\"); -fx-background-size: cover;";
    }
}
