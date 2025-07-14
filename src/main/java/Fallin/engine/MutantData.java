package Fallin.engine;

import java.io.Serializable;

public class MutantData implements Serializable {
    private static final long serialVersionUID = 1L;
    public int x, y;

    public MutantData(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
