import java.util.*;

public class AStarPathfinding {
    private MazeTemplate mazeTemplate;
    private Map<Node, Double> gScore;
    private Map<Node, Double> fScore;

    public AStarPathfinding(MazeTemplate mazeTemplate) {
        this.mazeTemplate = mazeTemplate;
        this.gScore = new HashMap<>();
        this.fScore = new HashMap<>();
    }

    public List<Node> findPath(int startX, int startY, int endX, int endY) {
        Node startNode = new Node(startX, startY);
        Node goalNode = new Node(endX, endY);

        // Inizializza gScore e fScore per il nodo di partenza
        gScore.put(startNode, 0.0);
        fScore.put(startNode, calculateHeuristic(startX, startY, endX, endY));

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        openSet.add(startNode);

        Map<Node, Node> cameFrom = new HashMap<>();

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.equals(goalNode)) {
                return reconstructPath(cameFrom, current);
            }

            for (Node neighbor : getNeighbors(current)) {
                double tentativeGScore = gScore.get(current) + calculateDistance(current, neighbor);

                if (!gScore.containsKey(neighbor) || tentativeGScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + calculateHeuristic(neighbor.getX(), neighbor.getY(), endX, endY));

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        // Se si arriva qui, non c'è un percorso
        return Collections.emptyList(); // Aggiunto per gestire il caso senza percorso
    }

    private List<Node> reconstructPath(Map<Node, Node> cameFrom, Node current) {
        List<Node> path = new ArrayList<>();
        path.add(current);

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }

        Collections.reverse(path);
        return path;
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();

        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int newX = node.getX() + dx[i];
            int newY = node.getY() + dy[i];

            if (isWithinMazeBounds(newX, newY) && mazeTemplate.getMazeData()[newY][newX] != 'x') {
                neighbors.add(new Node(newX, newY));
            }
        }

        return neighbors;
    }

    private double calculateDistance(Node node1, Node node2) {
        int dx = node1.getX() - node2.getX();
        int dy = node1.getY() - node2.getY();
        return Math.sqrt(dx * dx + dy * dy); // Distanza euclidea
    }

    private double calculateHeuristic(int currentX, int currentY, int goalX, int goalY) {
        int dx = goalX - currentX;
        int dy = goalY - currentY;
        return Math.sqrt(dx * dx + dy * dy); // Distanza euclidea
    }

    private boolean isWithinMazeBounds(int x, int y) {
        return x >= 0 && x < mazeTemplate.getColumnCount() && y >= 0 && y < mazeTemplate.getRowCount();
    }
}
