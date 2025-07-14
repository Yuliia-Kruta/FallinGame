package Fallin.engine;

import java.io.Serializable;
import java.util.List;

public class MedicalUnit extends Cell implements Serializable {

    private static final long serialVersionUID = 1L;

    public MedicalUnit(int x, int y) {
        super(x, y);
        setStyle("-fx-background-color:blue;");
    }

    @Override
    public void handleInteraction(Player player, List<Mutant> mutants, Cell[][] map, int x, int y) {
        player.setLife(Math.min(player.getLife()+3, 10));
        super.handleInteraction(player, mutants, map, x, y);
    }
    @Override
    public String getSymbol() {
        return "M";
    }

    @Override
    public String getCellStyle() {
        return "-fx-background-image: url(\"medicalUnit_icon.png\"); -fx-background-size: cover;";
    }
}
