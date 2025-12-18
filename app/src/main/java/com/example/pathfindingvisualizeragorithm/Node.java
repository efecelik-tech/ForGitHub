/*
package com.example.pathfindingvisualizeragorithm;
//Node sınıfı:Haritadaki her bir kareyi temsil ediyor.
public class Node implements Comparable<Node> {//javanın comparable özelliğini ekliyoruz ki min-heap bu kareleri sırayla dizebilsin.
    public int x;//nodenin konumu
    public int y;
    public boolean isWall;//engel mi değil mi?
    public boolean isVisited;//daha önce geldik mi?
    public Node parent;//Yol takibi için geldiği node deydi kimdi ?
    public double gCost;//Başlangıçtan buraya gelmenin maaliyeti(djkstra ve a* için)
    public double hCost;//hedefe kuş uçuşu tahmini mesafe(sadece a*)
    public double fCost;//g+h total maaliyet
    public Node(int x,int y){//constructor
        this.x=x;
        this.y=y;
        this.isWall=false;//başlangıçta engel yok diye başlıyoruz ki kendimiz koyalım onları
        this.isVisited=false;
        this.parent=null;
        //Maliyetleri sonsuz başlatıyoruz ki daha kısa yol bulunca güncellenebilsin
        this.gCost=Double.MAX_VALUE;
        this.fCost=Double.MAX_VALUE;
        this.hCost=0;
    }
    //A* algoritması için f değerini hesaplayan yardımcı metot
    public void calculateFcost(){

        this.fCost=this.gCost+this.hCost;
    }
    @Override//cpmperable interfacedeki compareTo metodunu ezdik
    public int compareTo(Node other){
       if(this.fCost<other.fCost){//daha öncelikli
           return -1;
       }
       else if(this.fCost>other.fCost){//diğeri daha öncelikli
           return 1;
       }
       return 0;//eşit
    }
}
*/
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