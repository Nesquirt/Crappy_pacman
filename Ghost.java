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
        // Inizializza la posizione del fantasma all'interno del recinto
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
        // Restituisce una direzione casuale (0-3) per il movimento del fantasma
        return new Random().nextInt(4);
    }

    public void move() {
        if (playerMoving && !input.isPlayerMoving()) {
            // Se il giocatore ha smesso di muoversi, ferma il fantasma
            playerMoving = false;
            return;
        }

        // Logica per il movimento del fantasma
        // Implementa la tua logica qui basandoti sulla direzione corrente e le mosse disponibili

        // Esempio: Muovi in una direzione casuale
        if (Math.random() < 0.1) {
            direction = getRandomDirection();
        }

        // Calcola la prossima posizione basata sulla direzione corrente e sulla velocità
        int nextX = x + ((direction % 2 == 0) ? speed * (direction - 1) : 0);
        int nextY = y + ((direction % 2 == 1) ? speed * (direction - 2) : 0);

        // Verifica se la prossima posizione è all'interno dei limiti del labirinto e non entra in collisione con le barriere del labirinto
        if (isWithinMazeBounds(nextX, nextY) && !collidesWithMazeBarrier(nextX, nextY)) {
            // Aggiorna la posizione del fantasma
            x = nextX;
            y = nextY;
        } else {
            // Cambia direzione se colpisce un muro
            direction = getRandomDirection();
        }
    }

    private boolean isWithinMazeBounds(int x, int y) {
        // Implementa la logica per verificare se (x, y) è all'interno dei limiti del labirinto
        return x >= 0 && x < mazeTemplate.getColumnCount() * mazeTemplate.CELL &&
                y >= 0 && y < mazeTemplate.getRowCount() * mazeTemplate.CELL;
    }

    private boolean collidesWithMazeBarrier(int x, int y) {
        // Implementa la logica per verificare se la posizione (x, y) entra in collisione con una barriera del labirinto
        int cellX = x / mazeTemplate.CELL;
        int cellY = y / mazeTemplate.CELL;
        char cellType = mazeTemplate.getMazeData()[cellY][cellX];
        return cellType == 'h' || cellType == 'v' || cellType == '1' || cellType == '2' || cellType == '3' || cellType == '4';
    }

    // Altri metodi e getter/setter se necessari

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
}
