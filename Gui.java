// Classe Gui
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
    private int timeElapsed;
    private ImageIcon heartIcon;
    private int heartSize;

    public Gui(Pacman pacman, MazeTemplate mazeTemplate, double pacManSize, Input input) {
        this.pacman = pacman;
        this.mazeTemplate = mazeTemplate;
        this.pacManSize = pacManSize;
        this.keyStates = new boolean[4];
        this.pacManIcons = new HashMap<>();
        this.input = input;
        this.timeElapsed = 0;

        setPreferredSize(new Dimension(920, 460));
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
        heartIcon = new ImageIcon("images/heart.png");
        heartSize = 30;

        Timer timeTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeElapsed++;
                repaint();
            }
        });
        timeTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pacman.handleInput(input.getKeyStates());
        pacman.move();
        repaint();

        if (pacman.isGameOver()) {
            System.out.println("Hai vinto!");
            // Puoi fare ulteriori azioni qui in caso di vittoria
            // Ad esempio, visualizzare un messaggio di vittoria o eseguire altre operazioni
        }
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
            int pacManSizeHalf = (int) Math.round(pacManSize) / 2;

            int pacManXCentered = pacManX - pacManSizeHalf;
            int pacManYCentered = pacManY - pacManSizeHalf;

            if (pacManDirection == 2 || pacManDirection == 3) {
                int cellY = pacManY / mazeTemplate.CELL;
                pacManYCentered = cellY * mazeTemplate.CELL + mazeTemplate.CELL / 2 - pacManSizeHalf;
            }

            if (pacManDirection == 0 || pacManDirection == 1) {
                int cellX = pacManX / mazeTemplate.CELL;
                pacManXCentered = cellX * mazeTemplate.CELL + mazeTemplate.CELL / 2 - pacManSizeHalf;
            }

            g.drawImage(pacManImage, pacManXCentered, pacManYCentered, (int) Math.round(pacManSize), (int) Math.round(pacManSize), this);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        DecimalFormat df = new DecimalFormat("00000");
        String scoreText = "Punteggio: " + df.format(pacman.getScore());
        g.drawString(scoreText, 20, getHeight() - 20);
        String timeText = "Tempo: " + timeElapsed + " s";
        g.drawString(timeText, 190, getHeight() - 20);
        for (int i = 0; i < pacman.getLives(); i++) {
            g.drawImage(heartIcon.getImage(), 320 + i * (heartSize + 5), getHeight() - 40, heartSize, heartSize, this);
        }
    }
}

