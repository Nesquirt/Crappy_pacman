import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Gui extends JPanel implements ActionListener {
    private Pacman pacman;
    private Input input;
    private int score;
    private Map<Integer, ImageIcon> pacManIcons;
    private MazeTemplate mazeTemplate;
    private double pacManSize; // Cambiato il tipo di pacManSize a double

    public Gui(Pacman pacman, Input input, MazeTemplate mazeTemplate, double pacManSize) {
        this.pacman = pacman;
        this.input = input;
        this.mazeTemplate = mazeTemplate;
        this.pacManSize = pacManSize; // Inizializza la dimensione di Pac-Man come double
        score = 0;

        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        addKeyListener(input);
        Timer timer = new Timer(100, this);
        timer.start();

        pacManIcons = new HashMap<>();
        pacManIcons.put(0, new ImageIcon("images/up.gif"));
        pacManIcons.put(1, new ImageIcon("images/down.gif"));
        pacManIcons.put(2, new ImageIcon("images/left.gif"));
        pacManIcons.put(3, new ImageIcon("images/right.gif"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pacman.handleInput(input);
        pacman.move();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        mazeTemplate.drawMaze(g);

        int pacManX = (int) Math.round(pacman.getX()); // Arrotonda la posizione x
        int pacManY = (int) Math.round(pacman.getY()); // Arrotonda la posizione y
        int pacManDirection = pacman.getDirection();

        ImageIcon pacManIcon = pacManIcons.get(pacManDirection);
        if (pacManIcon != null) {
            Image pacManImage = pacManIcon.getImage();

            // Disegna Pac-Man con la dimensione specificata
            g.drawImage(pacManImage, pacManX, pacManY, (int) Math.round(pacManSize), (int) Math.round(pacManSize), this);
        }
    }
}


