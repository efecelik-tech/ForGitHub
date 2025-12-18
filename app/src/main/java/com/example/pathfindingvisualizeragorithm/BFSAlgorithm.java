/*package com.example.pathfindingvisualizeragorithm;
import java.util.ArrayList;
import java.util.List;

public class BFSAlgorithm {
    //Rapor için istatistikler
    public int nodesVisited=0;
    public long executionTime=0;
    //çözücü metot
    public List<Node> solve(Node[][] grid,Node startNode,Node endNode,int rows,int cols) {
        long startTime = System.nanoTime();
        nodesVisited = 0;
        //kendi kuruğumuzu kullanıcaz
        MyQueue queue = new MyQueue();


        // Başlangıcı ekle ve işaretle
        queue.enqueue(startNode);
        startNode.isVisited = true;
        startNode.parent = null; // Başlangıcın atası yoktur

        // 2. DÖNGÜ
        while (!queue.isEmpty()) {
            // Sıradakini al
            Node current = queue.dequeue();
            nodesVisited++;

            // Hedef bulundu mu?
            if (current == endNode) {
                executionTime = System.nanoTime() - startTime; // Süre bitti
                return reconstructPath(endNode); // Yolu çiz
            }
            // 3. Komşuları gez
            List<Node> neighbors = getNeighbors(grid, current, rows, cols);
            for (Node neighbor : neighbors) {
                // Ziyaret edilmemişse ve duvar değilse
                if (!neighbor.isVisited && !neighbor.isWall) {
                    neighbor.isVisited = true;
                    neighbor.parent = current; // Yolu hatırlamak için not al
                    queue.enqueue(neighbor);   // Sıraya ekle
                }
            }
        }
        return new ArrayList<>();
    }
    private List<Node> getNeighbors(Node[][] grid, Node node, int rows, int cols) {
        List<Node> neighbors = new ArrayList<>();
        // Yukarı, Aşağı, Sol, Sağ
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newRow = node.y + dir[0];
            int newCol = node.x + dir[1];

            // Harita sınırları içinde mi?
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                neighbors.add(grid[newRow][newCol]);
            }
        }
        return neighbors;
    }
    // YARDIMCI METOT: Yolu tersten takip et
    private List<Node> reconstructPath(Node current) {
        List<Node> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        return path;
    }
}

 */
package com.example.pathfindingvisualizeragorithm;

import java.util.ArrayList;
import java.util.List;

public class BFSAlgorithm {

    public int nodesVisited = 0;
    public long executionTime = 0;

    public List<Node> solve(Node[][] grid, Node startNode, Node endNode) {
        long startTime = System.nanoTime();
        nodesVisited = 0;

        MyQueue queue = new MyQueue();

        queue.enqueue(startNode);
        startNode.isVisited = true;
        startNode.parent = null;

        while (!queue.isEmpty()) {
            Node current = queue.dequeue();
            nodesVisited++;

            // Hedef bulundu mu?
            if (current == endNode) {
                executionTime = System.nanoTime() - startTime;
                return reconstructPath(endNode);
            }

            // Komşuları gez
            for (Node neighbor : getNeighbors(grid, current)) {
                if (!neighbor.isVisited && !neighbor.isWall) {
                    neighbor.isVisited = true; // Ziyaret edildi (SARI)
                    neighbor.parent = current; // Yolu kaydet
                    queue.enqueue(neighbor);
                }
            }
        }
        return new ArrayList<>(); // Yol yok
    }

    private List<Node> getNeighbors(Node[][] grid, Node node) {
        List<Node> neighbors = new ArrayList<>();
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newRow = node.y + dir[0];
            int newCol = node.x + dir[1];

            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                neighbors.add(grid[newRow][newCol]);
            }
        }
        return neighbors;
    }

    private List<Node> reconstructPath(Node current) {
        List<Node> path = new ArrayList<>();
        while (current != null) {
            current.isPath = true; // Yolun parçası (MAVİ)
            path.add(0, current);
            current = current.parent;
        }
        return path;
    }
}