import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MazeTemplate {
    final int CELL = 20;
    private char[][] mazeData;
    private List<Pellet> pellets; // Lista di tutti i pellet nel labirinto
    private List<Pellet> specialPellets; // Lista di tutti i pellet speciali nel labirinto

    //-----------------------Lettura Labirinto da File-------------------------------------------//

    public MazeTemplate(String fileName) {
        readMazeDataFromFile(fileName);
        initializePellets();
    }

    private void readMazeDataFromFile(String fileName) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mazeData = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            mazeData[i] = lines.get(i).toCharArray();
        }
    }

    //--------------------------------------------------------------------------------//

    private void initializePellets() {  //Inizializzazione pellet e special pellets, riferiti al mazetemplate da file rappresentati da "d" e "p"
        pellets = new ArrayList<>();
        specialPellets = new ArrayList<>();

        for (int row = 0; row < getRowCount(); row++) {
            for (int col = 0; col < getColumnCount(); col++) {
                char cellType = mazeData[row][col];
                if (cellType == 'd') {
                    pellets.add(new Pellet(col * CELL + CELL / 2, row * CELL + CELL / 2, 10, false));
                } else if (cellType == 'p') {
                    specialPellets.add(new Pellet(col * CELL + CELL / 2, row * CELL + CELL / 2, 100, true));
                }
            }
        }
    }

    public void drawMaze(Graphics g) {
        for (int row = 0; row < getRowCount(); row++) {
            for (int col = 0; col < getColumnCount(); col++) {
                char cellType = mazeData[row][col];
                drawCell(g, row, col, cellType);
                //g.drawRect(row*CELL, col*CELL, CELL, CELL);
            }
        }

        // Disegna i pellet rimanenti
        drawPellets(g, pellets);
        drawPellets(g, specialPellets);
    }

    public List<Pellet> getSpecialPellets() {
        return specialPellets;
    }

    public List<Pellet> getPellets() {
        return pellets;
    }

    private void drawPellets(Graphics g, List<Pellet> pelletList) {
        ListIterator<Pellet> iterator = pelletList.listIterator();
        while (iterator.hasNext()) {
            Pellet pellet = iterator.next();
            int pelletX = pellet.getX();
            int pelletY = pellet.getY();
            int pelletValue = pellet.getValue();
            boolean isSpecial = pellet.isSpecial();

            g.setColor(isSpecial ? Color.PINK : Color.WHITE);
            if (isSpecial) {
                g.fillOval(pelletX - 7, pelletY - 7, 13, 13);
            } else {
                g.fillRect(pelletX - 1, pelletY - 1, 3, 3);
            }
        }
    }
//----------------------Assegnazione lettere del mazetemplate al relativo utilizzo--------------------//
    private void drawCell(Graphics g, int row, int col, char cellType) {
        int x = col * CELL;
        int y = row * CELL;

        switch (cellType) {
            case 'e': // Uscita del recinto
                g.setColor(Color.WHITE);
                g.fillRect(x, y + CELL / 2 - 10, CELL, 3);
                break;

            case 'h': // Linee orizzontali
                g.setColor(Color.BLUE);
                g.fillRect(x, y + CELL / 2 - 1, CELL, 3);
                break;

            case 'v': // Linee verticali
                g.setColor(Color.BLUE);
                g.fillRect(x + CELL / 2 - 1, y, 3, CELL);
                break;

            case '1': // Angolo alto-dx
            case '5':
                drawCorner(g, x - CELL / 2, y + CELL / 2);
                break;

            case '2': // Angolo alto-sx
            case '6':
                drawCorner(g, x + CELL / 2, y + CELL / 2);
                break;

            case '3': // Angolo basso-dx
            case '7':
                drawCorner(g, x - CELL / 2, y - CELL / 2);
                break;

            case '4': // Angolo basso-sx
            case '8':
                drawCorner(g, x + CELL / 2, y - CELL / 2);
                break;

            case 'o':
                break; // Cella vuota

            case 'x':// Cella vuota non percorribile
                //g.drawRect(x, y, CELL, CELL);
            case 'g': // Recinto fantasmi
            default:
                break;
        }
    }

    //----------------------Assegnazione lettere del mazetemplate al relativo utilizzo--------------------//

    private void drawCorner(Graphics g, int xBase, int yBase) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle oldClip = g.getClipBounds();

        g2.setClip(xBase, yBase, CELL, CELL);
        g2.setColor(Color.BLUE);

        Shape oval = new Ellipse2D.Double(xBase, yBase, CELL, CELL);

        g2.setStroke(new BasicStroke(3));
        g2.draw(oval);
        g2.setClip(oldClip);
    }

    public char[][] getMazeData() {
        return mazeData;
    }

    public int getRowCount() {
        return mazeData.length;
    }

    public int getColumnCount() {
        return mazeData[0].length;
    }
}
