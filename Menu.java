import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class Menu extends JFrame {

    public static scoreboard Scoreboard_frame;
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
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50)); // Allineamento centrale, spazio orizzontale di 50 pixel e spazio verticale di 20 pixel
        buttonPanel.setBackground(Color.BLACK);

        // Caricamento delle immagini per i pulsanti
        ImageIcon startImageIcon = new ImageIcon("images/start.png");
        ImageIcon leaderboardImageIcon = new ImageIcon("images/leaderboard.png");
        ImageIcon quitImageIcon = new ImageIcon("images/quit.png");

        // Pulsante Start
        JButton startButton = new JButton();
        startButton.setIcon(startImageIcon);
        startButton.setPreferredSize(new Dimension(200, 60));
        //startButton.setBorder(new RoundBorder(10));
        startButton.setFocusPainted(true);
        buttonPanel.add(startButton);

        // Pulsante Leaderboard
        Scoreboard_frame = new scoreboard(this);

        JButton leaderboardButton = new JButton();
        leaderboardButton.setIcon(leaderboardImageIcon);
        leaderboardButton.setPreferredSize(new Dimension(200, 60));
        //leaderboardButton.setBorder(new RoundBorder(10));
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
        //quitButton.setBorder(new RoundBorder(10));
        quitButton.setFocusPainted(true);
        buttonPanel.add(quitButton);

        // Aggiunta del pannello dei pulsanti al pannello principale
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Aggiunta del pannello principale alla finestra
        add(mainPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Menu::new);
    }

    // Classe per definire un bordo arrotondato
    static class RoundBorder extends AbstractBorder {
        private final int radius;

        RoundBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(c.getBackground());
            g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2d.dispose();
        }
    }
}
