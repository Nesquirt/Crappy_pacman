import java.awt.*;
import java.util.List;
import java.util.ListIterator;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pacman {
    private int x;
    private int y;
    private int direction;
    private boolean isMoving;
    private int speed;
    private MazeTemplate mazeTemplate;
    public int pacManSize;
    private int score;
    private double speedMultiplier = 1.2; // Moltiplicatore di velocità iniziale
    private boolean isGameWon;
    public Rectangle pacmanHitbox;
    private List<Ghost> ghosts;

    public Pacman(int startX, int startY, MazeTemplate mazeTemplate, int pacManSize, List<Ghost> ghosts) {
        x = startX;
        y = startY;
        direction = 3; // Inizialmente guardando a destra
        isMoving = false; // Inizialmente fermo
        speed = 2; // Velocità di movimento
        this.mazeTemplate = mazeTemplate; // Inizializza il riferimento a MazeTemplate
        this.pacManSize = pacManSize; // Inizializza la dimensione di Pac-Man come double
        this.ghosts = ghosts;
        score = 0; // Inizializza il punteggio a 0
        isGameWon = false;
        pacmanHitbox = new Rectangle(x, y, pacManSize, pacManSize);
    }

    public void move()
    {
        //System.out.println(x + " " + y);
        speed = 2;
        speedMultiplier = 1;
        if (isMoving) {
            int nextX = x;
            int nextY = y;


            switch (getDirection()) {
                case 0:
                    nextY -= speed * speedMultiplier;
                    break;
                case 1:
                    nextY += speed * speedMultiplier;
                    break;
                case 2:
                    nextX -= speed * speedMultiplier;
                    break;
                case 3:
                    nextX += speed * speedMultiplier;
                    break;

            }
            if (isValidMove(pacmanHitbox.x, pacmanHitbox.y)) {
                x = nextX;
                y = nextY;
                pacmanHitbox.x = nextX;
                pacmanHitbox.y = nextY;


            }
            else{
                //System.out.println("Trovato un muro");
                stopMoving();
            }

            // Verifica la raccolta di pellet speciali
            collectPellets(mazeTemplate.getSpecialPellets());

            // Verifica la raccolta di pellet normali
            collectPellets(mazeTemplate.getPellets());

            // Controlla se tutti i pellet sono stati mangiati
            if (mazeTemplate.getPellets().isEmpty() && mazeTemplate.getSpecialPellets().isEmpty()) {
                isGameWon= true;
                score += 1500;
                }
            }

            //collisione con fantasma
        for(int i = 0; i<ghosts.size();i++)
        {
            if(pacmanHitbox.intersects(ghosts.get(i).getGhostHitbox()))
            {
                setGameWon(true);
            }
        }
        }


    private boolean isValidMove(int x, int y) {
        int cellX = x / mazeTemplate.CELL;
        int cellY = y / mazeTemplate.CELL;
        char cellType;
        if (cellX >= 0 && cellX < mazeTemplate.getColumnCount() && cellY >= 0 && cellY < mazeTemplate.getRowCount()) {
            //System.out.println(cellType == 'o' || cellType == 'd' || cellType == 'p');
            //System.out.println(cellType);
            //System.out.println(cellType);
            //System.out.println(cellType);
            //System.out.println(cellType);
            System.out.println(cellX + ", " + cellY);
            return switch (direction) {
                case 0 -> {
                    if(y%20==0) {
                        cellType = mazeTemplate.getMazeData()[cellY - 1][cellX];
                        yield cellType == 'o' || cellType == 'd' || cellType == 'p';
                    } else
                        yield true;
                            //|| cellType == '1' || cellType == '2' || cellType == '3' || cellType == '4';
                }
                case 2 -> {
                    if(x%20==0) {
                        cellType = mazeTemplate.getMazeData()[cellY][cellX-1];
                        yield cellType == 'o' || cellType == 'd' || cellType == 'p';
                    } else
                        yield true;
                }
                case 3 -> {
                    cellType = mazeTemplate.getMazeData()[cellY][cellX + 1];
                    yield cellType == 'o' || cellType == 'd' || cellType == 'p';
                            //|| cellType == '1' || cellType == '2' || cellType == '3' || cellType == '4';
                }
                case 1 -> {
                    cellType = mazeTemplate.getMazeData()[cellY + 1][cellX];
                    yield cellType == 'o' || cellType == 'd' || cellType == 'p';
                            //|| cellType == '1' || cellType == '2' || cellType == '3' || cellType == '4';
                }
                default -> false;
            };
        }
        return false;
    }

    public void setDirection(int newDirection) {        //metodo per far muovere pacman di 20px in 20px per rimanere centrato
        if (newDirection >= 0 && newDirection <= 3 && x%20==0 && y%20==0){
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
            //stopMoving();
        }
    }


    private void collectPellets(List<Pellet> pelletList) {
        Rectangle currentPellet;
        ListIterator<Pellet> iterator = pelletList.listIterator();
        while (iterator.hasNext()) {
            Pellet pellet = iterator.next();
            int pelletX = pellet.getX();
            int pelletY = pellet.getY();
            int pelletValue = pellet.getValue();
            currentPellet = new Rectangle(pelletX, pelletY, 3, 3);

            if(pacmanHitbox.intersects(currentPellet))
            {
                iterator.remove();
                score += pelletValue;
                if (pellet.isSpecial()) {
                    // Pac-Man ha mangiato un pellet speciale, aumenta la velocità per 5 secondi
                    speedMultiplier = 2.5; // Moltiplicatore di velocità temporaneo
                    Timer timer = new Timer(5000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Ripristina la velocità normale dopo 5 secondi
                            speedMultiplier = 1.2;
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
            }

            }
        }
    }

    public int getScore() {
        return score;
    }

    public boolean isGameWon() {
        return isGameWon;
    }
    public void setGameWon(boolean newState)
    {
        isGameWon = newState;
    }

    public void resetPosition() {
        x = 40;
        y = 40;
        isMoving = false;
    }
}
