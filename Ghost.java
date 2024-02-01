import javax.swing.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
    private Timer moveTimer;
    private long lastMoveTime = System.currentTimeMillis();
    private int millisecondsPerMove = 16; // Regola questo valore per cambiare la velocità

    public Ghost(String name, int x, int y, MazeTemplate mazeTemplate, Pacman pacman, int speed, int changeDirectionProbability) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.size = 20;
        this.speed = 2;
        this.pacman = pacman;
        this.direction = 0; // Inizia muovendosi verso l'alto
        this.mazeTemplate = mazeTemplate;
        this.random = new Random();
        this.changeDirectionProbability = changeDirectionProbability;

        moveTimer = new Timer();
        moveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                move();
            }
        }, 0, 16);  // Esegui il metodo move() ogni 100 millisecondi (puoi regolare l'intervallo)
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

    public void stopMoving() {
        moveTimer.cancel();
    }

    public void move() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastMoveTime;

        if (elapsedTime >= millisecondsPerMove) {
            moveTowardsPacman();

            // Se il fantasma è bloccato, ruota nella prima direzione utile
            if (!isValidMove(calculateNextX(), calculateNextY())) {
                blockedInABlock();
            }

            lastMoveTime = currentTime;
        }
    }

    private int calculateNextX() {
        int step = Integer.compare(direction % 2, 0);
        return getX() + step;
    }

    private int calculateNextY() {
        int step = Integer.compare(direction % 2, 1);
        return getY() + step;
    }

    private void randomlyMove() {
        // Scegli una direzione casuale
        int randomDirection = random.nextInt(4);

        // Sposta il fantasma nella direzione casuale
        if (randomDirection == 0) {
            moveVertically(-1); // Sposta verso l'alto
        } else if (randomDirection == 1) {
            moveVertically(1); // Sposta verso il basso
        } else if (randomDirection == 2) {
            moveHorizontally(-1); // Sposta verso sinistra
        } else if (randomDirection == 3) {
            moveHorizontally(1); // Sposta verso destra
        }
    }

    private void moveTowardsPacman() {
        int targetX = pacman.getX(); // + ((int) pacman.pacManSize / 2);
        int targetY = pacman.getY(); // + ((int) pacman.pacManSize / 2);

        int deltaX = targetX - getX();
        int deltaY = targetY - getY();

        // Calcola la direzione più breve per raggiungere Pac-Man
        int newDirection = calculateShortestDirection(deltaX, deltaY);

        // Sposta il fantasma nella nuova direzione
        moveInDirection(newDirection);
    }

    private void moveInDirection(int newDirection) {
        if (newDirection == 0) {
            moveVertically(-1); // Sposta verso l'alto
        } else if (newDirection == 1) {
            moveVertically(1); // Sposta verso il basso
        } else if (newDirection == 2) {
            moveHorizontally(-1); // Sposta verso sinistra
        } else if (newDirection == 3) {
            moveHorizontally(1); // Sposta verso destra
        }
    }

    private int calculateShortestDirection(int deltaX, int deltaY) {
        // Calcola la direzione più breve per raggiungere Pac-Man
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            return (deltaX > 0) ? 3 : 2; // Sposta orizzontalmente
        } else {
            return (deltaY > 0) ? 1 : 0; // Sposta verticalmente
        }
    }

    private void moveHorizontally(int deltaX) {
        int step = Integer.compare(deltaX, 0);
        int nextX = getX() + step; // * mazeTemplate.CELL;

        int nextY = getY();

        // Se la prossima posizione è valida in termini orizzontali, aggiorna la posizione
        if (isValidMove(nextX, nextY)) {
            setX(nextX);
        }
    }

    private void moveVertically(int deltaY) {
        int step = Integer.compare(deltaY, 0);
        int nextY = getY() + step; // * mazeTemplate.CELL;

        int nextX = getX();

        // Se la prossima posizione è valida in termini verticali, aggiorna la posizione
        if (isValidMove(nextX, nextY)) {
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

    private void blockedInABlock() {
        // Cerca una direzione libera
        for (int newDirection = 0; newDirection < 4; newDirection++) {
            int nextX = calculateNextXInDirection(newDirection);
            int nextY = calculateNextYInDirection(newDirection);

            // Se la prossima posizione è valida, ruota il fantasma in quella direzione
            if (isValidMove(nextX, nextY)) {
                direction = newDirection;
                break;
            }
        }
    }

    private int calculateNextXInDirection(int newDirection) {
        int step = Integer.compare(newDirection % 2, 0);
        return getX() + step;
    }

    private int calculateNextYInDirection(int newDirection) {
        int step = Integer.compare(newDirection % 2, 1);
        return getY() + step;
    }

    private void randomlyChangeDirection() {
        // Aggiungi una probabilità del 5% di cambiare direzione ad ogni passo
        if (random.nextDouble(100) < changeDirectionProbability / 100.0) {
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
