import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GhostPattern {
    private static final Logger logger = Logger.getLogger(GhostPattern.class.getName());

    private Ghost ghost;
    private Pacman pacman;
    private MazeTemplate mazeTemplate;
    private AStarPathfinding pathfinding;

    public GhostPattern(Ghost ghost, Pacman pacman, MazeTemplate mazeTemplate) {
        this.ghost = ghost;
        this.pacman = pacman;
        this.mazeTemplate = mazeTemplate;
        this.pathfinding = new AStarPathfinding(mazeTemplate);
    }

    public void update() {
        int targetDirection = calculateTargetDirection();
        ghost.setDirection(targetDirection);
        // Non chiamare ghost.move() qui
    }

    private int calculateTargetDirection() {
        int ghostX = ghost.getX() / mazeTemplate.CELL;
        int ghostY = ghost.getY() / mazeTemplate.CELL;

        int pacmanX = pacman.getX() / mazeTemplate.CELL;
        int pacmanY = pacman.getY() / mazeTemplate.CELL;

        List<Node> path = pathfinding.findPath(ghostX, ghostY, pacmanX, pacmanY);

        if (path != null && path.size() > 1) {
            Node nextNode = path.get(1);
            int direction = getDirectionFromPath(ghostX, ghostY, nextNode.getX(), nextNode.getY());
            logger.log(Level.INFO, "Direzione calcolata usando A*: " + direction);
            return direction;
        } else {
            int direction = simpleChaseLogic(ghostX, ghostY, pacmanX, pacmanY);
            logger.log(Level.INFO, "Torna all'inseguimento classico: " + direction);
            return direction;
        }
    }

    private int simpleChaseLogic(int ghostX, int ghostY, int pacmanX, int pacmanY) {
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
        int dx = endX - startX;
        int dy = endY - startY;

        if (Math.abs(dx) > Math.abs(dy)) {
            return (dx > 0) ? 3 : 2; // Right o Left
        } else {
            return (dy > 0) ? 1 : 0; // Down o Up
        }
    }
}
