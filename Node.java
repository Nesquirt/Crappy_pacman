import java.util.Objects;

public class Node {
    private int x;
    private int y;
    private double gScore; // Costo effettivo dal punto di partenza al nodo corrente
    private double hScore; // Distanza euristica dal nodo corrente al punto di destinazione
    private double fScore; // Somma di gScore e hScore

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.gScore = 0;
        this.hScore = 0;
        this.fScore = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getGScore() {
        return gScore;
    }

    public void setGScore(double gScore) {
        this.gScore = gScore;
    }

    public double getHScore() {
        return hScore;
    }

    public void setHScore(double hScore) {
        this.hScore = hScore;
    }

    public double getFScore() {
        return fScore;
    }

    public void setFScore(double fScore) {
        this.fScore = fScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
