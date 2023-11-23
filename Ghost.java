import java.util.Random;

public class Ghost {
    private String name;
    private int x;
    private int y;
    private MazeTemplate mazeTemplate;
    private Pacman pacman;
    private int speed;
    private int direction;
    private boolean playerMoving;
    private Input input;
    private GhostPattern ghostPattern;

    public Ghost(String name, MazeTemplate mazeTemplate, Pacman pacman, int speed, Input input) {
        this.name = name;
        this.mazeTemplate = mazeTemplate;
        this.pacman = pacman;
        this.speed = speed;
        this.direction = getRandomDirection();
        this.playerMoving = false;
        this.input = input;
        initializePosition();
        this.ghostPattern = new GhostPattern(this, pacman, mazeTemplate);
    }

    private void initializePosition() {
        char[][] mazeData = mazeTemplate.getMazeData();
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(mazeTemplate.getRowCount());
            col = random.nextInt(mazeTemplate.getColumnCount());
        } while (mazeData[row][col] != 'g');

        x = col * mazeTemplate.CELL + mazeTemplate.CELL / 2;
        y = row * mazeTemplate.CELL + mazeTemplate.CELL / 2;
    }

    private int getRandomDirection() {
        return new Random().nextInt(4);
    }

    public void move() {
        if (playerMoving && !input.isPlayerMoving()) {
            playerMoving = false;
            return;
        }

        ghostPattern.updateDirection();

        // Calcola la direzione verso cui muoversi in base alla posizione di Pacman
        int targetDirection = calculateTargetDirection();

        // Aggiorna la direzione solo se non è contraria a quella corrente
        if (isOppositeDirection(targetDirection)) {
            targetDirection = getRandomDirection();
        }

        direction = targetDirection;

        int nextX = x + ((direction % 2 == 0) ? speed * (direction - 1) : 0);
        int nextY = y + ((direction % 2 == 1) ? speed * (direction - 2) : 0);

        if (isWithinMazeBounds(nextX, nextY) && !collidesWithMazeBarrier(nextX, nextY)) {
            x = nextX;
            y = nextY;
        } else {
            direction = getRandomDirection();
        }
    }

    private boolean isOppositeDirection(int targetDirection) {
        // Verifica se la direzione target è opposta a quella corrente
        return (direction + 2) % 4 == targetDirection;
    }

    private int calculateTargetDirection() {
        int pacmanX = pacman.getX();
        int pacmanY = pacman.getY();

        int dx = pacmanX - x;
        int dy = pacmanY - y;

        if (Math.abs(dx) > Math.abs(dy)) {
            return (dx > 0) ? 3 : 2; // Right o Left
        } else {
            return (dy > 0) ? 1 : 0; // Down o Up
        }
    }

    private boolean isWithinMazeBounds(int x, int y) {
        return x >= 0 && x < mazeTemplate.getColumnCount() * mazeTemplate.CELL &&
                y >= 0 && y < mazeTemplate.getRowCount() * mazeTemplate.CELL;
    }

    private boolean collidesWithMazeBarrier(int x, int y) {
        int cellX = x / mazeTemplate.CELL;
        int cellY = y / mazeTemplate.CELL;
        char cellType = mazeTemplate.getMazeData()[cellY][cellX];
        return cellType == 'h' || cellType == 'v' || cellType == '1' || cellType == '2' || cellType == '3' || cellType == '4';
    }

    public String getName() {
        return name;
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

    public int getSize() {
        return 20; // Sostituisci con la dimensione effettiva del fantasma
    }

    public boolean isPlayerMoving() {
        return playerMoving;
    }

    public void setPlayerMoving(boolean playerMoving) {
        this.playerMoving = playerMoving;
    }

    public void setDirection(int newDirection) {
        this.direction = newDirection;
    }

    public Input getInput() {
        return input;
    }
}
