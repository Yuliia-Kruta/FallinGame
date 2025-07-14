package Fallin.engine;

import java.io.Serializable;

public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    private int x, y;
    private int treasures;
    private int life;
    private int steps;

    public Player(int x, int y, int treasures, int life, int steps) {
        this.x = x;
        this.y = y;
        this.treasures = treasures;
        this.life = life;
        this.steps = steps;
    }

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.treasures = 0;
        this.life = 10;
        this.steps = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTreasures() {
        return treasures;
    }

    public void incrementTreasures(){
        this.treasures++;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getSteps() {
        return steps;
    }

    public void incrementSteps(){
        this.steps++;
    }
}
