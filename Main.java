import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crea un'istanza di MazeTemplate e carica il livello da un file
            MazeTemplate mazeTemplate = new MazeTemplate("level.txt");

            // Crea un'istanza di Pacman
            Pacman pacman = new Pacman(50, 50, mazeTemplate, 21); // Passa la dimensione di Pac-Man

            // Crea un'istanza di Input per gestire l'input
            Input input = new Input();

            // Crea una lista di fantasmi
            List<Ghost> ghosts = new ArrayList<>();
            ghosts.add(new Ghost("Inky", 100, 100, mazeTemplate, pacman, 5,0));
            ghosts.add(new Ghost("Blinky", 225, 225, mazeTemplate, pacman, 5,0));
            ghosts.add(new Ghost("Pinky", 310, 320, mazeTemplate, pacman, 5,0));
            ghosts.add(new Ghost("Clyde", 400, 400, mazeTemplate, pacman, 5,0));

            // Crea un'istanza di Gui e aggiungi il labirinto, Pac-Man, la dimensione di Pac-Man, l'input e la lista di fantasmi
            Gui gui = new Gui(pacman, mazeTemplate, 21, input, ghosts);

            // Crea una finestra JFrame per visualizzare il gioco
            JFrame frame = new JFrame("Pac-Man Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(gui);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);

            // Assicurati che il pannello abbia il focus per gestire l'input
            gui.requestFocus();
        });
    }
}
