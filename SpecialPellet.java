import java.awt.*;

public class SpecialPellet extends Pellet {
    private int duration; // Durata della modalità super dopo aver mangiato il pellet speciale

    public SpecialPellet(int x, int y, int value, int duration) {
        super(x, y, value, true); // Chiama il costruttore della classe madre (Pellet)
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public void draw(Graphics g) {
        // Disegna il pellet speciale sulla grafica
        g.setColor(Color.RED);
        g.fillOval(getX(), getY(), 10, 10);
    }
}
