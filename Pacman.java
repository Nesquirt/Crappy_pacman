import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ListIterator;

public class Pacman {
    private int x;
    private int y;
    private int direction;
    private boolean isMoving;
    private int speed;
    private MazeTemplate mazeTemplate;
    private double pacManSize;
    private int score;
    private double speedMultiplier = 0.3; // Moltiplicatore di velocità iniziale
    private int lives;
    private static final int MAX_LIVES = 3;
    private boolean isGameOver;
    private boolean isGameWon;
    private boolean isSuperMode;
    private Timer superModeTimer;

    public Pacman(int startX, int startY, MazeTemplate mazeTemplate, double pacManSize) {
        x = startX;
        y = startY;
        direction = 3; // Inizialmente guardando a destra
        isMoving = false; // Inizialmente fermo
        speed = 2; // Velocità di movimento
        this.mazeTemplate = mazeTemplate; // Inizializza il riferimento a MazeTemplate
        this.pacManSize = pacManSize; // Inizializza la dimensione di Pac-Man come double
        score = 0; // Inizializza il punteggio a 0
        lives = MAX_LIVES; // Inizializza le vite al massimo
        isGameOver = false; // Inizializza il flag del game over a false
        isGameWon = false; // Inizializza il flag della vittoria a false
        isSuperMode = false; // Inizializza il flag della modalità super a false

        superModeTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetSuperMode();
            }
        });
        superModeTimer.setRepeats(false);
    }

    public void move() {
        if (isMoving) {
            int nextX = x;
            int nextY = y;

            if (direction == 0) {
                nextY -= speed * speedMultiplier;
            } else if (direction == 1) {
                nextY += speed * speedMultiplier;
            } else if (direction == 2) {
                nextX -= speed * speedMultiplier;
            } else if (direction == 3) {
                nextX += speed * speedMultiplier;
            }

            if (isValidMove(nextX, nextY)) {
                x = nextX;
                y = nextY;
            }

            // Verifica la raccolta di pellet speciali solo se non è in modalità super
            if (!isSuperMode) {
                collectPellets(mazeTemplate.getSpecialPellets());
            }

            // Verifica la raccolta di pellet normali
            collectPellets(mazeTemplate.getPellets());

            // Controlla se tutti i pellet sono stati mangiati
            if (mazeTemplate.getPellets().isEmpty() && mazeTemplate.getSpecialPellets().isEmpty()) {
                if (lives > 0) {
                    // Ripristina la posizione iniziale
                    resetPosition();
                } else {
                    // Game over
                    isGameOver = true;
                }
            }

            // Controlla se hai vinto
            if (mazeTemplate.getPellets().isEmpty() && mazeTemplate.getSpecialPellets().isEmpty()) {
                isGameWon = true;
            }
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

    public void setDirection(int newDirection) {
        if (newDirection >= 0 && newDirection <= 3) {
            direction = newDirection;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirection() {
        return direction;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void startMoving() {
        isMoving = true;
    }

    public void stopMoving() {
        isMoving = false;
    }

    public void handleInput(boolean[] keyStates) {
        if (keyStates[0] && canMoveUp()) {
            setDirection(0); // Su
            startMoving();
        } else if (keyStates[1] && canMoveDown()) {
            setDirection(1); // Giù
            startMoving();
        } else if (keyStates[2] && canMoveLeft()) {
            setDirection(2); // Sinistra
            startMoving();
        } else if (keyStates[3] && canMoveRight()) {
            setDirection(3); // Destra
            startMoving();
        } else {
            stopMoving();
        }
    }

    private boolean canMoveUp() {
        int cellX = x / mazeTemplate.CELL;
        int cellY = y / mazeTemplate.CELL;

        if (cellY > 0) {
            char cellType = mazeTemplate.getMazeData()[cellY - 1][cellX];
            return cellType != 'x' && cellType != 'v';
        }

        return false;
    }

    private boolean canMoveDown() {
        int cellX = x / mazeTemplate.CELL;
        int cellY = y / mazeTemplate.CELL;

        if (cellY < mazeTemplate.getRowCount() - 1) {
            char cellType = mazeTemplate.getMazeData()[cellY + 1][cellX];
            return cellType != 'x' && cellType != 'v';
        }

        return false;
    }

    private boolean canMoveLeft() {
        int cellX = x / mazeTemplate.CELL;
        int cellY = y / mazeTemplate.CELL;

        if (cellX > 0) {
            char cellType = mazeTemplate.getMazeData()[cellY][cellX - 1];
            return cellType != 'x' && cellType != 'h';
        }

        return false;
    }

    private boolean canMoveRight() {
        int cellX = x / mazeTemplate.CELL;
        int cellY = y / mazeTemplate.CELL;

        if (cellX < mazeTemplate.getColumnCount() - 1) {
            char cellType = mazeTemplate.getMazeData()[cellY][cellX + 1];
            return cellType != 'x' && cellType != 'h';
        }

        return false;
    }

    private void collectPellets(List<Pellet> pellets) {
        ListIterator<Pellet> iterator = pellets.listIterator();
        while (iterator.hasNext()) {
            Pellet pellet = iterator.next();
            if (pellet.intersects(x, y, pacManSize)) {
                iterator.remove();
                score += pellet.getScore();
                if (pellet instanceof SpecialPellet) {
                    activateSuperMode();
                }
            }
        }
    }

    private void activateSuperMode() {
        if (!isSuperMode) {
            isSuperMode = true;
            superModeTimer.setInitialDelay(((SpecialPellet) mazeTemplate.getSpecialPellets().get(0)).getDuration());
            superModeTimer.start();
        } else {
            superModeTimer.restart();
        }
    }

    private void resetSuperMode() {
        isSuperMode = false;
        superModeTimer.stop();
    }

    public boolean isSuperModeActive() {
        return isSuperMode;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isGameWon() {
        return isGameWon;
    }

    public double getPacManSize() {
        return pacManSize;
    }

    public void resetPosition() {
        x = mazeTemplate.getStartingX();
        y = mazeTemplate.getStartingY();
        direction = 3; // Guardando a destra
        stopMoving();
        mazeTemplate.resetPellets();
        mazeTemplate.resetSpecialPellets();
        resetSuperMode();
        lives--;
        isGameOver = false;
        isGameWon = false;
    }
}
