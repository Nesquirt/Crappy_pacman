import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MazeTemplate {
    final int CELL = 20;
    private char[][] mazeData;

    public MazeTemplate(String fileName) {
        readMazeDataFromFile(fileName);
    }
//lettura maze da file
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

    public void drawMaze(Graphics g) {
        for (int row = 0; row < getRowCount(); row++) {
            for (int col = 0; col < getColumnCount(); col++) {
                char cellType = mazeData[row][col];
                drawCell(g, row, col, cellType);
            }
        }
    }

    private void drawCell(Graphics g, int row, int col, char cellType) {
        int x = col * CELL;
        int y = row * CELL;

        switch (cellType) {
            case 'e': // uscita recinto
                g.setColor(Color.WHITE);
                g.fillRect(x, y + CELL / 2 - 10, CELL, 3);
                break;

            case 'h': // linee orizzontali
                g.setColor(Color.BLUE);
                g.fillRect(x, y + CELL / 2 - 1, CELL, 3);
                break;

            case 'v': // vlinee verticali
                g.setColor(Color.BLUE);
                g.fillRect(x + CELL / 2 - 1, y, 3, CELL);
                break;

            case '1': // angolo alto-dx
                drawCorner(g, x - CELL / 2, y + CELL / 2);
                break;

            case '2': // angolo alto-sx
                drawCorner(g, x + CELL / 2, y + CELL / 2);
                break;

            case '3': // angolo basso-dx
                drawCorner(g, x - CELL / 2, y - CELL / 2);
                break;

            case '4': // angolo basso-sx
                drawCorner(g, x + CELL / 2, y - CELL / 2);
                break;

            case 'o':
                break; // cella vuota

            case 'd': // cella con pellet
                g.setColor(Color.WHITE);
                g.fillRect(x + CELL / 2 - 1, y + CELL / 2 - 1, 3, 3);
                break;

            case 'p': // cella con pellet gigante
                g.setColor(Color.PINK);
                g.fillOval(x + CELL / 2 - 7, y + CELL / 2 - 7, 13, 13);
                break;

            case 'x': // cella vuota non percorribile
            case 'g': // recinto fantasmi
            default:
                break;
        }
    }

    // Modifica il metodo drawCorner
    private void drawCorner(Graphics g, int xBase, int yBase) {   //TODO: FIXARE VISUALIZZAZIONE ANGOLI
        int halfCell = CELL / 2;
        int x = xBase + halfCell;
        int y = yBase + halfCell;

        g.setColor(Color.BLUE);
        g.fillOval(x - 3, y - 3, 6, 6);
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
