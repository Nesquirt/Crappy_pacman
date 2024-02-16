import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class Menu extends JFrame {

    public static scoreboard Scoreboard_frame;
    public static Main main_frame;
    public Menu() {
        super("Pacman");
        setSize(920, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Creazione del pannello principale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // Aggiunta del logo di Pacman al centro
        ImageIcon pacmanIcon = new ImageIcon("images/menu_logo.png");
        JLabel pacmanLabel = new JLabel(pacmanIcon);
        mainPanel.add(pacmanLabel, BorderLayout.CENTER);

        // Creazione del pannello per i pulsanti
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50)); // Allineamento centrale, spazio orizzontale  verticale
        buttonPanel.setBackground(Color.BLACK);

        // Caricamento delle immagini per i pulsanti
        ImageIcon startImageIcon = new ImageIcon("images/start.png");
        ImageIcon leaderboardImageIcon = new ImageIcon("images/leaderboard.png");
        ImageIcon quitImageIcon = new ImageIcon("images/quit.png");

        // Pulsante Start
        JButton startButton = new JButton();
        startButton.setIcon(startImageIcon);
        startButton.setPreferredSize(new Dimension(200, 60));
        startButton.setFocusPainted(true);
        buttonPanel.add(startButton);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Main.main(new String[]{});
                setVisible(false);
            }
        });

        // Pulsante Leaderboard
        Scoreboard_frame = new scoreboard(this);

        JButton leaderboardButton = new JButton();
        leaderboardButton.setIcon(leaderboardImageIcon);
        leaderboardButton.setPreferredSize(new Dimension(200, 60));
        leaderboardButton.setFocusPainted(true);
        buttonPanel.add(leaderboardButton);
        leaderboardButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                Scoreboard_frame.setVisible(true);

            }

        });

        // Pulsante Quit
        JButton quitButton = new JButton();
        quitButton.setIcon(quitImageIcon);
        quitButton.setPreferredSize(new Dimension(200, 60));
        quitButton.setFocusPainted(true);
        buttonPanel.add(quitButton);

        quitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }

        });

        // Aggiunta del pannello dei pulsanti al pannello principale
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Aggiunta del pannello principale alla finestra
        add(mainPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Menu::new);
    }

    }
