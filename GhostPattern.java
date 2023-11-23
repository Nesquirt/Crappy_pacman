public class GhostPattern {
    private Ghost ghost;
    private Pacman pacman;
    private MazeTemplate mazeTemplate;

    public GhostPattern(Ghost ghost, Pacman pacman, MazeTemplate mazeTemplate) {
        this.ghost = ghost;
        this.pacman = pacman;
        this.mazeTemplate = mazeTemplate;
    }

    public void updateDirection() {
        // Calcola la direzione per inseguire il giocatore
        int targetDirection = calculateTargetDirection();

        // Imposta la direzione del fantasma
        ghost.setDirection(targetDirection);
    }

    private int calculateTargetDirection() {
        int ghostX = ghost.getX();
        int ghostY = ghost.getY();

        int pacmanX = pacman.getX();
        int pacmanY = pacman.getY();

        // Calcola la differenza tra le posizioni X e Y
        int dx = pacmanX - ghostX;
        int dy = pacmanY - ghostY;

        // Scegli la direzione in base alla differenza
        if (Math.abs(dx) > Math.abs(dy)) {
            // Insegui lungo l'asse X
            return (dx > 0) ? 3 : 2; // Right o Left
        } else {
            // Insegui lungo l'asse Y
            return (dy > 0) ? 1 : 0; // Down o Up
        }
    }
}
