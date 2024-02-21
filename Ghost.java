import javax.swing.*;
import java.awt.*;
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
    private int direction;
    public int targetX, targetY; //TEMP
    private Rectangle ghostHitbox;

    public Ghost(String name, int x, int y, MazeTemplate mazeTemplate, Pacman pacman) { //definizione classe ghost
        this.name = name;
        this.x = x;
        this.y = y;
        this.size = 20;
        this.speed = 1;
        this.pacman = pacman;
        this.mazeTemplate = mazeTemplate;
        direction = 0;
        //TEMP
        targetX = 0;
        targetY = 0;
        //
        moveTimer = new Timer();
        moveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //move();
            }
        }, 0, 16);  // Esegui il metodo move() ogni 100 millisecondi (puoi regolare l'intervallo)
        this.ghostHitbox = new Rectangle(x,y,size,size);
    }


    public void updateHitbox()
    {
        ghostHitbox.x = x;
        ghostHitbox.y = y;
    }
    public Rectangle getGhostHitbox()
    {
        return ghostHitbox;
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

    public void move() {                                                                                                //classe principale riferita al solo movimento
        targetX = findTarget()[0];                                                                                      //dei fantasmi.
        targetY = findTarget()[1];

        int[] target = findTarget();
        targetX = target[0];
        targetY = target[1];

       /* target=findTarget()[]{
                if targetX = findTarget()[0];
        }else{
            targetY= findTarget()[1];
        }

        if(direction == y --){
            System.out.println("nuova riga");
        } */

        direction = chooseDirection(targetX, targetY);

        switch(direction)
        {
            case 0:
                y --;
                break;
            case 1:
                y ++;
                break;
            case 2:
                x --;
                break;
            case 3:
                x ++;
                break;
        }
        updateHitbox();
        /*
        if (Math.abs(deltaX) >= Math.abs(deltaY) && isValidMove(x + moveX, y)) { // || x % 20 != 0) {
            x += moveX;
        } else if (Math.abs(deltaX) < Math.abs(deltaY) && isValidMove(x, y + moveY)) { // || y % 20 != 0) {
            y += moveY;
        }
        */

    }

    private boolean isValidMove(int x, int y) {                                                                         //serie di controlli per non far uscire i
        int cellX = x / mazeTemplate.CELL;                                                                              //fantasmi dai bordi del labirinto
        int cellY = y / mazeTemplate.CELL;

        if (cellX >= 0 && cellX < mazeTemplate.getColumnCount() && cellY >= 0 && cellY < mazeTemplate.getRowCount()) {
            char cellType = mazeTemplate.getMazeData()[cellY][cellX];
            return cellType == 'o' || cellType == 'd' || cellType == 'p';
        }
        return false;
    }

    private int validDirections() {                                                                                     //Verifica se è una direzione valida
        int cellX = getX() / mazeTemplate.CELL;                                                                         //muovendosi di 20 px in 20px per rimanere
        int cellY = getY() / mazeTemplate.CELL;                                                                         //centrato nelle celle del labirinto.

        int validDirections = 0;
        if (isValidMove(getX() + 20, getY()))
            validDirections++;
        if (isValidMove(getX() - 20, getY()))
            validDirections++;
        if (isValidMove(getX(), getY() + 20))
            validDirections++;
        if (isValidMove(getX(), getY() - 20))
            validDirections++;
        System.out.println("Valid directions: " + validDirections);
        return validDirections;
    }

    private int[] nextCell() {

        int nextX = getX(), nextY = getY();
        int[] nextCell = new int[2];

        switch (direction) {
            case 0:
                nextY -= 20;
                break;
            case 1:

                nextY += 20;
                break;
            case 2:
                nextX -= 20;
                break;
            case 3:
                nextX += 20;
                break;

        }
        nextCell[0] = nextX;
        nextCell[1] = nextY;
        return nextCell;
    }

    private int chooseDirection(int targetX, int targetY) {
        if(getX() % 20 == 0 && getY() % 20 == 0) {
        //Caso 1: corridoio -> continuare ad andare dritti
        if (validDirections() == 2 && isValidMove(nextCell()[0], nextCell()[1])) {
            //System.out.println("Corridoio");
            return direction;
        }
        //Caso 2: angolo -> selezionare l'altra direzione valida
        if (validDirections() == 2 && !isValidMove(nextCell()[0], nextCell()[1])) {
            //System.out.println("Angolo");
            switch (direction) {
                case 2:
                case 3:
                    if (isValidMove(getX(), getY() - 1))
                        return 0;

                    else return 1;
                case 0:
                case 1:
                    if (isValidMove(getX() - 20, getY()))
                        return 2;

                    else return 3;

            }
        }
        //caso 3/4: intersezione a T/incrocio -> scegli direzione con i delta tra le valide
        if (validDirections() >= 3) {
            //System.out.println("Incrocio");
            int deltaX = targetX - getX();
            int deltaY = targetY - getY();
            if (Math.abs(deltaX) >= Math.abs(deltaY)) {
                if (isValidMove((getX() + (int) (20 * Math.signum(deltaX))), getY())) {
                    if (Math.signum(deltaX) < 0)
                        return 2;
                    else return 3;
                } else if (isValidMove(getX(), getY() + 20))
                    return 1;
                else return 0;
            } else {
                if (isValidMove(getX(), (getY() + (int) (20 * Math.signum(deltaY))))) {
                    if (Math.signum(deltaY) < 0) {
                        return 0;
                    } else return 1;
                } else if (isValidMove(getX() + 20, getY()))
                    return 3;
                else return 2;
            }


        }
        //Caso 5: Vicolo cieco -> scegli l'unica direzione possibile
        if (validDirections() == 1) {
            switch (direction) {
                case 0:
                    return 1;
                case 1:
                    return 0;
                case 2:
                    return 3;
                case 3:
                    return 2;
            }
        }
        //System.out.println("Void");
        }return direction;
    }

    private int[] findTarget()
    {
        int[] targetCell = new int[2];
        switch(name)
        {
            case "Blinky": //Fantasma rosso -> punta dritto a Pacman
                targetCell[0] = pacman.getX();
                targetCell[1] = pacman.getY();

            case "Pinky": //Fantasma rosa -> punta a 3 celle davanti a Pacman

                switch(pacman.getDirection()) {

                    case 0:
                        targetCell[0] = pacman.getX();
                        targetCell[1] = pacman.getY() - 60;
                        break;
                    case 1:
                        targetCell[0] = pacman.getX();
                        targetCell[1] = pacman.getY() + 60;
                        break;
                    case 2:
                        targetCell[0] = pacman.getX() - 60;
                        targetCell[1] = pacman.getY();
                        break;
                    case 3:
                        targetCell[0] = pacman.getX() + 60;
                        targetCell[1] = pacman.getY();
                        break;
                }

                break;

            case "Clyde": //Fantasma arancione -> punta a Pacman, ma si allontana se si avvicina troppo
                if(getX() - pacman.getX() <=80 && getY() - pacman.getY() <=80)
                {
                    targetCell[0] = 420;
                    targetCell[1] = 380;
                }
                else
                {
                    targetCell[0] = pacman.getX();
                    targetCell[1] = pacman.getY();
                }
                break;

            case "Inky": //Fantasma blu -> Stessa logica del fantasma rosa ma punta nel lato opposto
                switch(pacman.getDirection()) {

                    case 0:
                        targetCell[0] = pacman.getX();
                        targetCell[1] = pacman.getY() + 60;                                                             //+60 sarebbe i 20px di movimento moltiplicati
                        break;                                                                                          //per il numero di celle di distanza da pacman.
                    case 1:
                        targetCell[0] = pacman.getX();
                        targetCell[1] = pacman.getY() - 60;
                        break;
                    case 2:
                        targetCell[0] = pacman.getX() + 60;
                        targetCell[1] = pacman.getY();
                        break;
                    case 3:
                        targetCell[0] = pacman.getX() - 60;
                        targetCell[1] = pacman.getY();
                        break;
                }

                break;
            default:
                targetCell[0] = pacman.getX();
                targetCell[1] = pacman.getY();
        }
        if(!isValidMove(targetCell[0], targetCell[1]))
        {
            System.out.println("Target non valido: " + targetCell[0] + ", " + targetCell[1]);                           //se una mossa per qualsiasi motivo non è valida
            targetCell[0] = pacman.getX();                                                                              //torna a rincorrere pacman direttamente,
            targetCell[1] = pacman.getY();                                                                              //prendendo x e y come target principale.
        }
        return targetCell;

    }

}
