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

    public Ghost(String name, MazeTemplate mazeTemplate, Pacman pacman, int speed, Input input) {
        this.name = name;
        this.mazeTemplate = mazeTemplate;
        this.pacman = pacman;
        this.speed = speed;
        this.direction = getRandomDirection();
        this.playerMoving = false;
        this.input = input;
        initializePosition();
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
            // If the player has stopped moving, stop the ghost
            playerMoving = false;
            return;
        }

        // Logic for ghost movement
        // Implement your logic here based on the current direction and available moves

        // Example: Move in a random direction
        if (Math.random() < 0.1) {
            direction = getRandomDirection();
        }

        // Update the ghost's position based on the current direction and speed
        // Implement your logic here

        // Example: Move horizontally
        x += (direction % 2 == 0) ? speed * (direction - 1) : 0;

        // Example: Move vertically
        y += (direction % 2 == 1) ? speed * (direction - 2) : 0;
    }

    // Other methods and getters/setters as needed

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

    public boolean isPlayerMoving() {
        return playerMoving;
    }

    public int getSize(){
        return 20;
    }

    public void setPlayerMoving(boolean playerMoving) {
        this.playerMoving = playerMoving;
    }
}
