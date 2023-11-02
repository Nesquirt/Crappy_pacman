public class Pacman {
    private int x;
    private int y;
    private int direction;
    private boolean isMoving;
    private int speed;
    private MazeTemplate mazeTemplate; // riferimento a MazeTemplate

    public Pacman(int startX, int startY, MazeTemplate mazeTemplate) {
        x = startX;
        y = startY;
        direction = 3;
        isMoving = false;
        speed = 10;
        this.mazeTemplate = mazeTemplate; // Inizializza il riferimento a MazeTemplate
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
    }

    private boolean isValidMove(int x, int y) {
        int cellX = x / mazeTemplate.CELL;
        int cellY = y / mazeTemplate.CELL;

        if (cellX >= 0 && cellX < mazeTemplate.getColumnCount() && cellY >= 0 && cellY < mazeTemplate.getRowCount()) {
            return mazeTemplate.getMazeData()[cellY][cellX] != 'x';
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

    public void handleInput(Input input) {
        if (input.isUpPressed()) {
            setDirection(0); // Su
            startMoving();
        } else if (input.isDownPressed()) {
            setDirection(1); // Giù
            startMoving();
        } else if (input.isLeftPressed()) {
            setDirection(2); // Sinistra
            startMoving();
        } else if (input.isRightPressed()) {
            setDirection(3); // Destra
            startMoving();
        } else {
            //stopMoving(); // Fermo
        }
    }
}
