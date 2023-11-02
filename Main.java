import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crea un'istanza di MazeTemplate e carica il livello da un file
            MazeTemplate mazeTemplate = new MazeTemplate("level.txt");

            // Crea un'istanza di Pacman
            Pacman pacman = new Pacman(50, 50, mazeTemplate);


            // Crea un'istanza di Input
            Input input = new Input();


            // Crea un'istanza di Gui e aggiungi il labirinto, Pac-Man,l'input e aggiungi la possibilità di cambiare la dimensione di pacman
            Gui gui = new Gui(pacman, input, mazeTemplate, 20);

            // Crea una finestra JFrame per visualizzare il gioco
            JFrame frame = new JFrame("Pac-Man Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(gui);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Assicurati che il pannello abbia il focus per gestire l'input
            gui.requestFocus();

            // Crea un timer per aggiornare il gioco e il pannello Gui
            Timer timer = new Timer(100, e -> {
                // Chiamare il metodo handleInput di Pacman per gestire l'input da tastiera
                pacman.handleInput(input);

                // Altri aggiornamenti del gioco
                pacman.move();

                // Aggiorna l'interfaccia utente con le animazioni e il punteggio
                gui.repaint();
            });
            timer.start();
        });
    }
}

