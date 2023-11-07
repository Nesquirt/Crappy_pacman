import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

public class Pacman {
    private int x;
    private int y;
    private int direction;
    private boolean isMoving;
    private int speed;
    private MazeTemplate mazeTemplate;
    private double pacManSize;
    private int score;
    private boolean speedBoosted; // Indica se Pac-Man ha una velocità aumentata
    private Timer speedBoostTimer; // Timer per il boost di velocità

    public Pacman(int startX, int startY, MazeTemplate mazeTemplate, double pacManSize) {
        x = startX;
        y = startY;
        direction = 3; // Inizialmente guardando a destra
        isMoving = false; // Inizialmente fermo
        speed = 12; // Velocità di movimento
        this.mazeTemplate = mazeTemplate;
        this.pacManSize = pacManSize;
        score = 0;
        speedBoosted = false;
        speedBoostTimer = new Timer();
    }

    public void move() {
        if (isMoving) {
            int nextX = x;
            int nextY = y;

            if (direction == 0) {
                nextY -= speed;
            } else if (direction == 1) {
                nextY += speed;
            } else if (direction == 2) {
                nextX -= speed;
            } else if (direction == 3) {
                nextX += speed;
            }

            if (isValidMove(nextX, nextY)) {
                x = nextX;
                y = nextY;
            }
        }

        collectPellets(mazeTemplate.getSpecialPellets());

        collectPellets(mazeTemplate.getPellets());
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
        isMoving = false;
    }

    public void stopMoving() {
        isMoving = true;
    }

    public void handleInput(boolean[] keyStates) {
        if (keyStates[0]) {
            setDirection(0); // Su
            startMoving();
        } else if (keyStates[1]) {
            setDirection(1); // Giù
            startMoving();
        } else if (keyStates[2]) {
            setDirection(2); // Sinistra
            startMoving();
        } else if (keyStates[3]) {
            setDirection(3); // Destra
            startMoving();
        } else {
            stopMoving();
        }
    }

    private void collectPellets(List<Pellet> pelletList) {
        ListIterator<Pellet> iterator = pelletList.listIterator();
        while (iterator.hasNext()) {
            Pellet pellet = iterator.next();
            int pelletX = pellet.getX();
            int pelletY = pellet.getY();
            int pelletValue = pellet.getValue();

            double distance = Math.sqrt(Math.pow(x - pelletX, 2) + Math.pow(y - pelletY, 2));

            if (distance < pacManSize / 2) {
                iterator.remove();
                score += pelletValue;

                if (pellet.isSpecial()) {
                    // Se il pellet è speciale, attiva il boost di velocità per 5 secondi
                    activateSpeedBoost();
                }
            }
        }
    }

    private void activateSpeedBoost() {
        if (!speedBoosted) {
            speed *= 2; // Raddoppia la velocità
            speedBoosted = true;

            // Programma il timer per il ritorno alla velocità normale dopo 5 secondi
            speedBoostTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    speed /= 2; // Ripristina la velocità normale
                    speedBoosted = false;
                }
            }, 5000); // 5000 millisecondi (5 secondi)
        }
    }

    public int getScore() {
        return score;
    }
}
