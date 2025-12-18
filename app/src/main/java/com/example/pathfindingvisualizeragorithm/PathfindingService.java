package com.example.pathfindingvisualizeragorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PathfindingService {

    public enum AlgorithmType { DIJKSTRA, ASTAR_MANHATTAN, ASTAR_EUCLIDEAN }

    public static class AlgorithmResult {
        public List<Node> path;
        public long durationNs;
        public int visitedNodeCount;
        public double pathCost;
    }

    public AlgorithmResult runPathfinding(Node[][] grid, Node startNode, Node endNode, AlgorithmType type) {
        AlgorithmResult result = new AlgorithmResult();
        long startTime = System.nanoTime();

        CustomMinHeap openSet = new CustomMinHeap(grid.length * grid[0].length);
        Set<Node> closedSet = new HashSet<>();

        startNode.gCost = 0;
        startNode.hCost = calculateHeuristic(startNode, endNode, type);
        startNode.calculateFCost();
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.extractMin();
            result.visitedNodeCount++;

            current.isVisited = true; // Ziyaret edildi (SARI)

            if (current == endNode) {
                long endTime = System.nanoTime();
                result.durationNs = endTime - startTime;
                result.path = reconstructPath(current);
                result.pathCost = current.gCost;
                return result;
            }

            closedSet.add(current);

            for (Node neighbor : getNeighbors(grid, current)) {
                if (neighbor.isWall || closedSet.contains(neighbor)) continue;

                double newGCost = current.gCost + 1;

                if (newGCost < neighbor.gCost) {
                    neighbor.gCost = newGCost;
                    neighbor.hCost = calculateHeuristic(neighbor, endNode, type);
                    neighbor.calculateFCost();
                    neighbor.parent = current;

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    } else {
                        openSet.updateNode(neighbor);
                    }
                }
            }
        }
        return result;
    }

    private double calculateHeuristic(Node node, Node target, AlgorithmType type) {
        if (type == AlgorithmType.DIJKSTRA) return 0;
        if (type == AlgorithmType.ASTAR_EUCLIDEAN)
            return Math.sqrt(Math.pow(node.x - target.x, 2) + Math.pow(node.y - target.y, 2));
        return Math.abs(node.x - target.x) + Math.abs(node.y - target.y);
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

    private List<Node> getNeighbors(Node[][] grid, Node node) {
        List<Node> neighbors = new ArrayList<>();
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] d : dirs) {
            int nx = node.x + d[0];
            int ny = node.y + d[1];
            if (nx >= 0 && nx < grid[0].length && ny >= 0 && ny < grid.length) {
                neighbors.add(grid[ny][nx]);
            }
        }
        return neighbors;
}
}