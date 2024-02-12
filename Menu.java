import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

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
        ImageIcon pacmanIcon = new ImageIcon("images/menu_logo.png"); // Assicurati che il percorso sia corretto
        JLabel pacmanLabel = new JLabel(pacmanIcon);
        mainPanel.add(pacmanLabel, BorderLayout.CENTER);

        // Creazione del pannello per i pulsanti
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50)); // Allineamento centrale, spazio orizzontale di 50 pixel e spazio verticale di 20 pixel
        buttonPanel.setBackground(Color.BLACK);

        // Pulsante Start
        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(200, 60));
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        buttonPanel.add(startButton);

        // Pulsante Leaderboard
        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.setPreferredSize(new Dimension(200, 60));
        leaderboardButton.setFont(new Font("Arial", Font.BOLD, 20));
        buttonPanel.add(leaderboardButton);

        // Pulsante Quit
        JButton quitButton = new JButton("Quit");
        quitButton.setPreferredSize(new Dimension(200, 60));
        quitButton.setFont(new Font("Arial", Font.BOLD, 20));
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
}
