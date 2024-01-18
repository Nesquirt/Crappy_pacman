import java.awt.*;

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

    public Rectangle getBounds() {
        // I limiti del pellet sono rappresentati da un rettangolo
        return new Rectangle(x, y, isSpecial ? 10 : 5, isSpecial ? 10 : 5);
    }

    public boolean intersects(int otherX, int otherY, double otherSize) {
        Rectangle pelletBounds = getBounds();
        Rectangle otherBounds = new Rectangle(otherX, otherY, (int) otherSize, (int) otherSize);
        return pelletBounds.intersects(otherBounds);
    }

    public void draw(Graphics g) {
        // Disegna il pellet sulla grafica
        if (isSpecial) {
            g.setColor(Color.YELLOW);
            g.fillOval(x, y, 10, 10);
        } else {
            g.setColor(Color.WHITE);
            g.fillOval(x, y, 5, 5);
        }
    }

    public int getScore() {
        // Restituisce il punteggio del pellet
        return value;
    }
}
