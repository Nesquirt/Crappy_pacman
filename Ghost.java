import java.awt.*;
import java.util.Random;

public class Ghost {
    private String name;
    private int x;
    private int y;
    private int speed;
    private MazeTemplate mazeTemplate;
    private Pacman pacman;
    private Random random;

    public Ghost(String name, int startX, int startY, MazeTemplate mazeTemplate, Pacman pacman, int speed) {
        this.name = name;
        this.x = startX;
        this.y = startY;
        this.mazeTemplate = mazeTemplate;
        this.pacman = pacman;
        this.speed = speed;
        this.random = new Random();
    }

    public void move() {
        // Implementa la logica di movimento del fantasma
        // Ad esempio, puoi farli inseguire il giocatore o muoversi in modo casuale

        int nextX = x;
        int nextY = y;

        // Scegli una direzione in base alla posizione del giocatore
        int playerX = pacman.getX();
        int playerY = pacman.getY();

        if (random.nextBoolean()) {
            moveHorizontally(playerX);
        } else {
            moveVertically(playerY);
        }
    }

    private void moveHorizontally(int playerX) {
        // Muovi in direzione orizzontale
        if (playerX < x && isValidMove(x - speed, y)) {
            x -= speed;
        } else if (playerX > x && isValidMove(x + speed, y)) {
            x += speed;
        }
    }

    private void moveVertically(int playerY) {
        // Muovi in direzione verticale
        if (playerY < y && isValidMove(x, y - speed)) {
            y -= speed;
        } else if (playerY > y && isValidMove(x, y + speed)) {
            y += speed;
        }
    }

    private boolean isValidMove(int x, int y) {
        // Verifica se la mossa è valida
        int cellX = x / mazeTemplate.CELL;
        int cellY = y / mazeTemplate.CELL;

        if (cellX >= 0 && cellX < mazeTemplate.getColumnCount() && cellY >= 0 && cellY < mazeTemplate.getRowCount()) {
            char cellType = mazeTemplate.getMazeData()[cellY][cellX];
            return cellType != 'x' && cellType != 'v' && cellType != 'h' && cellType != '1' && cellType != '2' && cellType != '3' && cellType != '4';
        }

        return false;
    }

    public void draw(Graphics g) {
        // Implementa la logica di disegno del fantasma
        g.setColor(Color.RED); // Cambia colore a seconda del fantasma
        g.fillOval(x - 10, y - 10, 20, 20); // Disegna un cerchio per rappresentare il fantasma
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        // Restituisci il nome del fantasma
        return name;
    }

    public int getSize() {
        // Restituisci la dimensione del fantasma
        return 20;
    }
}
