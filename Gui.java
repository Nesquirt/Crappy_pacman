import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Gui extends JPanel implements ActionListener {
    private Pacman pacman;
    private MazeTemplate mazeTemplate;
    private double pacManSize;
    private boolean[] keyStates;
    private Map<Integer, ImageIcon> pacManIcons;
    private Input input;

    public Gui(Pacman pacman, MazeTemplate mazeTemplate, double pacManSize, Input input) {
        this.pacman = pacman;
        this.mazeTemplate = mazeTemplate;
        this.pacManSize = pacManSize;
        this.keyStates = new boolean[4];
        this.pacManIcons = new HashMap<>();
        this.input = input;

        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                input.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                input.keyReleased(e);
            }
        });

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
        pacman.handleInput(input.getKeyStates());
        pacman.move();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        mazeTemplate.drawMaze(g);

        int pacManX = (int) Math.round(pacman.getX());
        int pacManY = (int) Math.round(pacman.getY());
        int pacManDirection = pacman.getDirection();

        ImageIcon pacManIcon = pacManIcons.get(pacManDirection);
        if (pacManIcon != null) {
            Image pacManImage = pacManIcon.getImage();
            g.drawImage(pacManImage, pacManX, pacManY, (int) Math.round(pacManSize), (int) Math.round(pacManSize), this);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        DecimalFormat df = new DecimalFormat("00000");
        String scoreText = "Punteggio: " + df.format(pacman.getScore());
        g.drawString(scoreText, 20, getHeight() - 20);
    }
}
