
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
