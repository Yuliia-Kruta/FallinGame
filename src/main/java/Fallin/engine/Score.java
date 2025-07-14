package Fallin.engine;

import java.io.Serializable;
import java.time.LocalDate;

public class Score implements Comparable<Score>, Serializable {

    private static final long serialVersionUID = 1L;
    private int score;
    private LocalDate date;

    public Score(int score, LocalDate date) {
        this.score = score;
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public int compareTo(Score other) {
        return other.score - this.score;
    }

    @Override
    public String toString() {
        return score + " " + date;
    }
}
