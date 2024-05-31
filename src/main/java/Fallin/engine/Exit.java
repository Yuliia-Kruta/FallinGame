package Fallin.engine;

import java.util.List;

public class Exit extends Cell{

    public Exit(int x, int y) {
        super(x, y);
        setStyle("-fx-background-color:red;");
    }

    @Override
    public void handleInteraction(Player player, List<Mutant> mutants, Cell[][] map, int x, int y) {
    }
    @Override
    public String getSymbol() {
        return "E";
    }

    @Override
    public String getCellStyle() {
        return "-fx-background-image: url(\"finish_icon.png\"); -fx-background-size: cover;";
    }
}
