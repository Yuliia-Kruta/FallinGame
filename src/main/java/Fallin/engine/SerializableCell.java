package Fallin.engine;

import java.io.Serializable;

public class SerializableCell implements Serializable {
    private static final long serialVersionUID = 1L;

    private int x;
    private int y;
    private String type;  // e.g., "Treasure", "Trap", "MedicalUnit"

    public SerializableCell(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public String getType() { return type; }
}
