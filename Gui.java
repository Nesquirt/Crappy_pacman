import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gui extends JPanel implements ActionListener {
    private Pacman pacman;
    private MazeTemplate mazeTemplate;
    private double pacManSize;
    private boolean[] keyStates;
    private Map<Integer, ImageIcon> pacManIcons;
    private Map<String, ImageIcon> ghostIcons;
    private Input input;
    private int timeElapsed;
    private ImageIcon heartIcon;
    private int heartSize;
    private boolean gameWon;
    private List<Ghost> ghosts;

    public Gui(Pacman pacman, MazeTemplate mazeTemplate, double pacManSize, Input input, List<Ghost> ghosts) {
        this.pacman = pacman;
        this.mazeTemplate = mazeTemplate;
        this.pacManSize = pacManSize;
        this.keyStates = new boolean[4];
        this.pacManIcons = loadPacManIcons();
        this.ghostIcons = loadGhostIcons();
        this.input = input;
        this.timeElapsed = 0;
        this.gameWon = false;
        this.ghosts = ghosts;

        setPreferredSize(new Dimension(920, 460));
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyRelease(e);
            }
        });

        Timer timer = new Timer(17, this);
        timer.start();

        Timer timeTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeElapsed++;
                repaint();
            }
        });
        timeTimer.start();
    }

    private Map<Integer, ImageIcon> loadPacManIcons() {
        Map<Integer, ImageIcon> icons = new HashMap<>();
        icons.put(0, new ImageIcon("images/up.gif"));
        icons.put(1, new ImageIcon("images/down.gif"));
        icons.put(2, new ImageIcon("images/left.gif"));
        icons.put(3, new ImageIcon("images/right.gif"));
        return icons;
    }

    private Map<String, ImageIcon> loadGhostIcons() {
        Map<String, ImageIcon> icons = new HashMap<>();
        icons.put("Inky", new ImageIcon("images/inky.gif"));
        icons.put("Blinky", new ImageIcon("images/blinky.gif"));
        icons.put("Pinky", new ImageIcon("images/pinky.gif"));
        icons.put("Clyde", new ImageIcon("images/clyde.gif"));
        return icons;
    }

    private void handleKeyPress(KeyEvent e) {
        if (gameWon) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                gameWon = false;
                pacman.resetPosition();
                setPlayerMoving();
                repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_Q) {
                System.exit(0);
            }
        } else {
            input.keyPressed(e);
            setPlayerMoving();
        }
    }

    private void setPlayerMoving() {
        for (Ghost ghost : ghosts) {
            ghost.setPlayerMoving(true);
        }
    }

    private void handleKeyRelease(KeyEvent e) {
        if (!gameWon) {
            input.keyReleased(e);
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

        for (Ghost ghost : ghosts) {
            String ghostName = ghost.getName();
            ImageIcon ghostIcon = ghostIcons.get(ghostName);
            if (ghostIcon != null) {
                Image ghostImage = ghostIcon.getImage();
                int ghostX = (int) Math.round(ghost.getX());
                int ghostY = (int) Math.round(ghost.getY());
                int ghostSize = (int) Math.round(ghost.getSize());

                g.drawImage(ghostImage, ghostX, ghostY, ghostSize, ghostSize, this);
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        DecimalFormat df = new DecimalFormat("00000");
        String scoreText = "Punteggio: " + df.format(pacman.getScore());
        g.drawString(scoreText, 20, getHeight() - 20);
        String timeText = "Tempo: " + timeElapsed + " s";
        g.drawString(timeText, 190, getHeight() - 20);

        if (gameWon) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            String winMessage = "Hai vinto! Premi R per riprovare, Q per uscire.";
            int messageWidth = g.getFontMetrics().stringWidth(winMessage);
            int messageX = getWidth() / 2 - messageWidth / 2;
            int messageY = getHeight() / 2;
            g.drawString(winMessage, messageX, messageY);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameWon) {
            pacman.handleInput(input.getKeyStates());
            pacman.move();

            for (Ghost ghost : ghosts) {
                //if(ghost.name == "Inky")
                //ghost.target.X = pacman.getX();
                //ghost.target.Y = pacman.getY();
                //else if(ghost.name == "Pinky")
                //  {if(pacman.isFacing(Right)
                //      ghost.target.X = pacman.getX() + 3
                //      ghost.target.Y = pacman.getY()
                ghost.move();
            }

            repaint();

            if (pacman.isGameOver()) {
                System.out.println("Hai perso!");
            } else if (pacman.isGameWon()) {
                gameWon = true;
            }
        }
    }
}
