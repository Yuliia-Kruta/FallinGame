package Fallin.engine;

import javafx.scene.layout.StackPane;

import java.awt.*;
import java.util.List;

public class Cell extends StackPane {
    private int x;
    private int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void handleInteraction(Player player, List<Mutant> mutants, Cell[][] map, int x, int y) {
        map[x][y] = null;
    }

    public String getSymbol(){
        return null;
    }

    public String getCellStyle(){
        return null;
    }

}
