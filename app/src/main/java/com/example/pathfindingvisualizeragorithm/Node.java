
package com.example.pathfindingvisualizeragorithm;

public class Node implements Comparable<Node> {//Ne zaman iki Node nesnesini karşılaştırman gerekirse, o kurala bakacaksın dedik bunla
    public int x, y;

    // --- GÖRSEL DURUMLAR ---
    public boolean isWall;    // Engel (Siyah)
    public boolean isVisited; // Algoritma taradı (Sarı)
    public boolean isPath;    // En kısa yol (Mavi)

    // --- ALGORİTMA VERİLERİ ---
    public Node parent;       // Yolu geriye takip etmek için
    public double gCost;      // Başlangıca olan uzaklık
    public double hCost;      // Hedefe olan kuş uçuşu uzaklık (Heuristic)
    public double fCost;      // Toplam maliyet (g + h)

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.isWall = false;
        reset(); // İlk değerleri ata
    }

    // Algoritma tekrar çalışmadan önce temizlik (Duvarlar ve konumlar korunur)
    public void reset() {
        this.isVisited = false;
        this.isPath = false;
        this.parent = null;
        this.gCost = Double.MAX_VALUE;//sonsuz çünkü ilk başlarken sonsuz alıyoruz ki daha kısa bir yol olduğunda onu eklesin
        this.fCost = Double.MAX_VALUE;
        this.hCost = 0;//
    }

    public void calculateFCost() {

        this.fCost = this.gCost + this.hCost;
    }

    // Min-Heap sıralaması için (A* ve Dijkstra kullanır)
    @Override
    public int compareTo(Node other) {
        if (this.fCost < other.fCost) return -1;
        if (this.fCost > other.fCost) return 1;
        return 0;
    }
}
