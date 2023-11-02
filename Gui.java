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
    private Map<Integer, ImageIcon> pacManIcons; // Mappa delle animazioni di Pac-Man basate sulla direzione
    private MazeTemplate mazeTemplate; // Aggiungiamo un oggetto MazeTemplate

    public Gui(Pacman pacman, Input input, MazeTemplate mazeTemplate) {
        this.pacman = pacman;
        this.input = input;
        this.mazeTemplate = mazeTemplate; // Inizializziamo il template del labirinto
        score = 0;

        // Imposta il pannello e altri dettagli UI
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        addKeyListener(input);
        Timer timer = new Timer(100, this);
        timer.start();

        // Carica le animazioni di Pac-Man da file GIF
        pacManIcons = new HashMap<>();
        pacManIcons.put(0, new ImageIcon("images/up.gif")); // Su
        pacManIcons.put(1, new ImageIcon("images/down.gif")); // Giù
        pacManIcons.put(2, new ImageIcon("images/left.gif")); // Sinistra
        pacManIcons.put(3, new ImageIcon("images/right.gif")); // Destra
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Chiamare il metodo handleInput di Pacman per gestire l'input da tastiera
        pacman.handleInput(input);

        // Altri aggiornamenti del gioco
        pacman.move();

        // Aggiorna l'interfaccia utente con le animazioni e il punteggio
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Disegna uno sfondo nero
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Disegna il labirinto utilizzando il template
        mazeTemplate.drawMaze(g);

        // Disegna il punteggio

        //Pac-Man utilizzando l'animazione GIF basata sulla direzione
        int pacManX = pacman.getX();
        int pacManY = pacman.getY();
        int pacManDirection = pacman.getDirection();

        ImageIcon pacManIcon = pacManIcons.get(pacManDirection);
        if (pacManIcon != null) {
            Image pacManImage = pacManIcon.getImage();

            // Disegna l'animazione di Pac-Man
            g.drawImage(pacManImage, pacManX, pacManY, this);
        }
    }
}


