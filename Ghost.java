import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Ghost {
    private int x;
    private int y;
    private int size;
    private String name;
    private int speed;
    private int direction;
    private Pacman pacman;
    private MazeTemplate mazeTemplate;
    private Random random;
    private int changeDirectionProbability; // Probabilità di cambiare direzione (in percentuale)

    public Ghost(String name, int x, int y, MazeTemplate mazeTemplate, Pacman pacman, int speed) {
        this(name, x, y, mazeTemplate, pacman, speed, 0); // Imposta la probabilità di cambiare direzione a 0% di default
    }

    public Ghost(String name, int x, int y, MazeTemplate mazeTemplate, Pacman pacman, int speed, int changeDirectionProbability) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.size = 20;
        this.speed = speed;
        this.pacman = pacman;
        this.direction = 0; // Inizia muovendosi verso l'alto
        this.mazeTemplate = mazeTemplate;
        this.random = new Random();
        this.changeDirectionProbability = changeDirectionProbability;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void move() {
        if (pacman.isMoving()) {
            moveTowardsPacman();
            randomlyChangeDirection();
        } else {
            // Se Pac-Man è fermo, continua nella direzione attuale
            continueInCurrentDirection();
        }
    }

    private void moveTowardsPacman() {
        int targetX = pacman.getX();
        int targetY = pacman.getY();

        int deltaX = targetX - getX();
        int deltaY = targetY - getY();

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            moveHorizontally(deltaX);
        } else {
            moveVertically(deltaY);
        }
    }

    private void moveHorizontally(int deltaX) {
        int step = Integer.compare(deltaX, 0);
        int nextX = getX() + step * mazeTemplate.CELL;

        if (isValidMove(nextX, getY())) {
            setX(nextX);
        }
    }

    private void moveVertically(int deltaY) {
        int step = Integer.compare(deltaY, 0);
        int nextY = getY() + step * mazeTemplate.CELL;

        if (isValidMove(getX(), nextY)) {
            setY(nextY);
        }
    }

    private void continueInCurrentDirection() {
        if (direction == 0) {
            moveVertically(-1); // Sposta verso l'alto
        } else if (direction == 1) {
            moveVertically(1); // Sposta verso il basso
        } else if (direction == 2) {
            moveHorizontally(-1); // Sposta verso sinistra
        } else if (direction == 3) {
            moveHorizontally(1); // Sposta verso destra
        }
    }

    private void randomlyChangeDirection() {
        // Aggiungi una probabilità del 5% di cambiare direzione ad ogni passo
        if (random.nextDouble() < changeDirectionProbability / 100.0) {
            direction = random.nextInt(4);
        }
    }

    private boolean isValidMove(int x, int y) {
        int cellX = x / mazeTemplate.CELL;
        int cellY = y / mazeTemplate.CELL;

        if (cellX >= 0 && cellX < mazeTemplate.getColumnCount() && cellY >= 0 && cellY < mazeTemplate.getRowCount()) {
            char cellType = mazeTemplate.getMazeData()[cellY][cellX];
            return cellType != 'x' && cellType != 'v' && cellType != 'h' && cellType != '1' && cellType != '2' && cellType != '3' && cellType != '4';
        }

        return false;
    }
}
