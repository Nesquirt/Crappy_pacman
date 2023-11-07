public class Pellet {
    private int x;
    private int y;
    private int value;
    private boolean isSpecial; // Indica se il pellet è speciale

    public Pellet(int x, int y, int value, boolean isSpecial) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.isSpecial = isSpecial;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public boolean isSpecial() {
        return isSpecial;
    }
}

