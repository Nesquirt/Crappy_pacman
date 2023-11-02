import javax.swing.*;
import java.awt.*;

public class Maze extends JPanel {
    public Maze() {
        setPreferredSize(new Dimension(800, 800)); // Imposta le dimensioni del labirinto
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Disegna una zona vuota nera
        //g.setColor(Color.BLACK);
        //g.fillRect(0, 0, getWidth(), getHeight());
    }
}
