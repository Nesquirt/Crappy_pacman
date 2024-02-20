import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args, scoreboard Scoreboard) {
        SwingUtilities.invokeLater(() -> {
            // Crea un'istanza di MazeTemplate e carica il livello da un file
            MazeTemplate mazeTemplate = new MazeTemplate("level.txt");

            // Crea un'istanza di Input per gestire l'input
            Input input = new Input();

            // Crea una lista di fantasmi
            List<Ghost> ghosts = new ArrayList<>();

            // Crea un'istanza di Pacman
            Pacman pacman = new Pacman(40, 40, mazeTemplate, 20, ghosts); // Passa la dimensione di Pac-Man

            // Inizializza i fantasmi
            ghosts.add(new Ghost("Inky", 280, 180, mazeTemplate, pacman));
            ghosts.add(new Ghost("Blinky", 220, 340, mazeTemplate, pacman));
            ghosts.add(new Ghost("Pinky", 360, 340, mazeTemplate, pacman));
            ghosts.add(new Ghost("Clyde", 420, 380, mazeTemplate, pacman));

            // Crea un'istanza di Gui e aggiungi il labirinto, Pac-Man, la dimensione di Pac-Man, l'input e la lista di fantasmi
            Gui gui = new Gui(pacman, mazeTemplate, pacman.pacManSize, input, ghosts, Scoreboard);

            // Crea una finestra JFrame per visualizzare il gioco
            JFrame frame = new JFrame("Pac-Man Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(gui);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);

            // Per far chiudere il gioco dopo aver inserito un nome, devo
            // passarlo alla classe scoreboard, così può renderla non visibile
            // quando viene chiamata la funzione writeFile()
            scoreboard.gameFrame = frame;

            // Assicurati che il pannello abbia il focus per gestire l'input
            gui.requestFocus();
        });
    }
}

//NOTA: per far chiudere il frame di gioco al termine della partita, ho dovuto
// trovare un modo di collegare il JFrame di gioco (creato e usato esclusivamente in questa classe)
// alla condizione di vittoria (nella classe Gui); per farlo, ho deciso di passarlo alla classe scoreboard,
// che contiene la funzione writeFile() che viene chiamata a fine partita.