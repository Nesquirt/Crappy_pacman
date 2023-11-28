import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
public class GhostPattern {
    private Ghost ghost;
    private Pacman pacman;
    private MazeTemplate mazeTemplate;

    public GhostPattern(Ghost ghost, Pacman pacman, MazeTemplate mazeTemplate) {
        this.ghost = ghost;
        this.pacman = pacman;
        this.mazeTemplate = mazeTemplate;
    }

    public void update() {
        int targetDirection = calculateTargetDirection();
        ghost.setDirection(targetDirection);
        // Non chiamare ghost.move() qui
    }

    private int calculateTargetDirection() {
        // Implementazione di A* per calcolare la direzione verso cui il fantasma dovrebbe muoversi
        int ghostX = ghost.getX() / mazeTemplate.CELL;
        int ghostY = ghost.getY() / mazeTemplate.CELL;

        int pacmanX = pacman.getX() / mazeTemplate.CELL;
        int pacmanY = pacman.getY() / mazeTemplate.CELL;

        // Ottieni il percorso dalla posizione del fantasma alla posizione del giocatore usando A*
        // La funzione AStarPathfinding restituirà una lista di nodi che rappresenta il percorso
        // La funzione getDirectionFromPath restituirà la direzione da seguire per raggiungere il prossimo nodo

        // Esempio:
        AStarPathfinding pathfinding = new AStarPathfinding(mazeTemplate);
        List<Node> path = pathfinding.findPath(ghostX, ghostY, pacmanX, pacmanY);
        if (path != null && path.size() > 1) {
            Node nextNode = path.get(1); // Prendi il secondo nodo (il primo è la posizione corrente del fantasma)
            return getDirectionFromPath(ghostX, ghostY, nextNode.getX(), nextNode.getY());
        } else {
            // Se il percorso non è disponibile, fallback a una logica di movimento di base
            return simpleChaseLogic();
        }
    }

    private int simpleChaseLogic() {
        // Implementa una logica di inseguimento di base se A* non trova un percorso
        int ghostX = ghost.getX() / mazeTemplate.CELL;
        int ghostY = ghost.getY() / mazeTemplate.CELL;

        int pacmanX = pacman.getX() / mazeTemplate.CELL;
        int pacmanY = pacman.getY() / mazeTemplate.CELL;

        if (ghostX < pacmanX) {
            return 3; // Right
        } else if (ghostX > pacmanX) {
            return 2; // Left
        } else if (ghostY < pacmanY) {
            return 1; // Down
        } else {
            return 0; // Up
        }
    }

    private int getDirectionFromPath(int startX, int startY, int endX, int endY) {
        // Calcola la differenza tra le coordinate X e Y
        int dx = endX - startX;
        int dy = endY - startY;

        // Determina la direzione in base alla differenza
        if (Math.abs(dx) > Math.abs(dy)) {
            return (dx > 0) ? 3 : 2; // Right o Left
        } else {
            return (dy > 0) ? 1 : 0; // Down o Up
        }
    }

}
