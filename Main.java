import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crea un'istanza di MazeTemplate e carica il livello da un file
            MazeTemplate mazeTemplate = new MazeTemplate("level.txt");

            // Crea un'istanza di Pacman
            Pacman pacman = new Pacman(50, 50, mazeTemplate, 20); // Passa la dimensione di Pac-Man

            // Crea un'istanza di Input per gestire l'input
            Input input = new Input();

            // Crea un'istanza di Gui e aggiungi il labirinto, Pac-Man, la dimensione di Pac-Man e l'input
            Gui gui = new Gui(pacman, mazeTemplate, 20, input);

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


//TODO:Vittoria se mangiati tutti i pallet
//TODO:Aggiungere fantasmi con relative animazioni
//TODO:Gestire logica fantasmi(Seguono pacman in diversi modi,scappano)
//TODO:Perdere vite se i fantasmi ti toccano, rinizia il livello con una vita in meno ma lascia i pallets mangiati as they as
//TODO:Schermata di vittoria se si mangiano tutti i pallet, schermata di sconfitta se si perdono tutte le vite
//TODO:fare un menù
//TODO:Fare leaderboard aggiornata in automatico
//TODO:Aggiungere suono
