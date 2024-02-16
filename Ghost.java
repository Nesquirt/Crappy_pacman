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
    private Pacman pacman;
    private MazeTemplate mazeTemplate;
    private Timer moveTimer;

    public Ghost(String name, int x, int y, MazeTemplate mazeTemplate, Pacman pacman) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.size = 20;
        this.speed = 2;
        this.pacman = pacman;
        this.mazeTemplate = mazeTemplate;

        moveTimer = new Timer();
        moveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //move();
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

    public void move() {
        int targetX = pacman.getX();
        int targetY = pacman.getY();

        int deltaX = targetX - x;
        int deltaY = targetY - y;

        int moveX = Integer.compare(deltaX, 0) * speed;
        int moveY = Integer.compare(deltaY, 0) * speed;

        if (isValidMove(x + moveX, y)) {
            x += moveX;
        }

        if (isValidMove(x, y + moveY)) {
            y += moveY;
        }
    }

    private boolean isValidMove(int x, int y) {
        int cellX = x / mazeTemplate.CELL;
        int cellY = y / mazeTemplate.CELL;

        if (cellX >= 0 && cellX < mazeTemplate.getColumnCount() && cellY >= 0 && cellY < mazeTemplate.getRowCount()) {
            char cellType = mazeTemplate.getMazeData()[cellY][cellX];
            return cellType == 'o' || cellType == 'd' || cellType == 'p';
        }
        return false;
    }

}
