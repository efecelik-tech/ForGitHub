# Pathfinding Algorithm Visualizer

A Java-based Android application developed to visualize and analyze classical
pathfinding algorithms in terms of performance, memory usage, and behavior.

This project was developed as a final project for the **Algorithms (CS 202)** course.

---

##  Project Aim

The aim of this project is to visualize and compare classical pathfinding algorithms
on a grid-based environment by implementing them from scratch and analyzing their
performance empirically.

The project focuses on:
- Understanding algorithmic behavior step-by-step
- Measuring execution time and visited node count
- Comparing theoretical and practical performance
- Observing memory efficiency through visualization

---

##  Implemented Algorithms

- **Breadth-First Search (BFS)**
  - Guarantees shortest path in unweighted graphs
  - Uses a custom FIFO queue implementation

- **Dijkstra’s Algorithm**
  - Finds shortest paths in weighted graphs
  - Implemented with a custom Min-Heap supporting efficient priority updates

- **A\* (A-Star) Algorithm**
  - Informed search algorithm using heuristic guidance
  - Uses Manhattan Distance as an admissible heuristic
  - Achieves significantly better performance on large grids

All algorithms are implemented **from scratch** without using Java’s built-in
pathfinding or priority queue libraries.

---

## 🛠 Technologies Used

- Java
- Android Studio
- Android Canvas (Custom View Rendering)
- Custom Data Structures (Queue, Min-Heap)

---

##  Project Structure

- `Node.java` – Grid cell representation (coordinates, cost values, state)
- `BFSAlgorithm.java` – BFS implementation
- `DijkstraAlgorithm.java` – Dijkstra implementation with custom Min-Heap
- `AStarAlgorithm.java` – A* implementation with heuristic guidance
- `MyQueue.java` – Custom FIFO queue implementation
- `CustomMinHeap.java` – Binary Min-Heap with efficient decrease-key operation
- `PathfindingView.java` – Custom View for grid rendering and visualization

---

##  Team & Task Distribution

**Efe Çelik**
- BFS algorithm implementation
- Dijkstra algorithm implementation
- Custom Queue and Min-Heap data structures
- Grid representation and node tracking
- Performance measurement logic (time & visited nodes)

**Salih Emir Akıcıoğlu**
- A* algorithm implementation
- Heuristic function design
- DFS implementation
- UI interaction and control logic

---

## 📊 Performance Metrics

Algorithms are evaluated using the following metrics:
- **Execution Time (ms)** – measured via `System.nanoTime()`
- **Visited Node Count** – number of processed nodes
- **Path Length** – number of steps in the shortest path

Tests were conducted under identical conditions for fairness.

---

##  Experimental Setup

- Grid sizes ranging from **10×10 to 80×80**
- Multiple scenarios:
  - Open field
  - Random obstacles (~20%)
  - Maze structures
  - Trap scenarios
- Tested on both Android Emulator and physical devices

---

##  Key Observations

- BFS and Dijkstra scale poorly on large grids due to exhaustive exploration
- A* dramatically reduces both execution time and memory usage
- All algorithms consistently find the optimal path
- A* achieves up to **5–10× faster performance** on large grids

---

## ▶ How to Run

1. Open the project in **Android Studio**
2. Build and run on an emulator or physical Android device
3. Select an algorithm and interactively visualize the pathfinding process

---

##  Notes

- All algorithms and data structures are implemented manually for educational purposes
- No external pathfinding or graph libraries are used
- This project is intended for academic analysis and learning

---

##  References

- T. H. Cormen et al., *Introduction to Algorithms*, MIT Press
- P. E. Hart et al., “A Formal Basis for the Heuristic Determination of Minimum Cost Paths”
- Android Developers Documentation – Canvas & Custom Views
