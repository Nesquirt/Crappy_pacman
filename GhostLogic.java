import java.util.Random;

public class GhostLogic {
    private Pacman pacman;
    private Random random;

    public GhostLogic(Pacman pacman) {
        this.pacman = pacman;
        this.random = new Random();
    }

    public void moveTowardsPacman(Ghost ghost) {
        int targetX = pacman.getX() + ((int) pacman.getPacManSize() / 2);
        int targetY = pacman.getY() + ((int) pacman.getPacManSize() / 2);

        int deltaX = targetX - ghost.getX();
        int deltaY = targetY - ghost.getY();

        // Calcola la direzione più breve per raggiungere Pac-Man
        int newDirection = calculateShortestDirection(deltaX, deltaY);

        // Sposta il fantasma nella nuova direzione
        ghost.moveInDirection(newDirection);
    }

    public void moveTowardsTarget(Ghost ghost, int targetX, int targetY) {
        int deltaX = targetX - ghost.getX();
        int deltaY = targetY - ghost.getY();

        // Calcola la direzione più breve per raggiungere il target
        int newDirection = calculateShortestDirection(deltaX, deltaY);

        // Sposta il fantasma nella nuova direzione
        ghost.moveInDirection(newDirection);
    }

    public void moveTowardsCorner(Ghost ghost, int cornerX, int cornerY) {
        int deltaX = cornerX - ghost.getX();
        int deltaY = cornerY - ghost.getY();

        // Calcola la direzione più breve per raggiungere l'angolo
        int newDirection = calculateShortestDirection(deltaX, deltaY);

        // Sposta il fantasma nella nuova direzione
        ghost.moveInDirection(newDirection);
    }

    public void randomlyChangeDirection(Ghost ghost) {
        // Aggiungi una probabilità del 5% di cambiare direzione ad ogni passo
        if (ghost.getRandom().nextDouble(100) < ghost.getChangeDirectionProbability() / 100.0) {
            ghost.setDirection(ghost.getRandom().nextInt(4));
        }
    }

    public int calculateShortestDirection(int deltaX, int deltaY) {
        // Calcola la direzione più breve
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            return (deltaX > 0) ? 3 : 2; // Sposta orizzontalmente
        } else {
            return (deltaY > 0) ? 1 : 0; // Sposta verticalmente
        }
    }
}
