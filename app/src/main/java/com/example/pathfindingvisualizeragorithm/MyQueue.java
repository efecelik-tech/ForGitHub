/*package com.example.pathfindingvisualizeragorithm;
//FIFO yapısı hazırlicam.
public class MyQueue {//kuruğu yönetecek ana sınıf
    private class QNode{//kuyruğun düğümünü tutan sınıf
        Node data;//içinde bizim harita karemiz var
        QNode next;//bir sonraki halka
        public QNode(Node data){//constructor
            this.data=data;
            this.next=null;
        }

    }
    private QNode front;//Kuruğun başı esasen kuruk için çıkış kapsı
    private  QNode rear;//Kuruğun sonu esasen kuruk için giriş kapsı
private int size;//kaç eleman olduğunu tutuyoruz(sırada kaç kişi var)

    public MyQueue(){//constructor
        this.front=null;
        this.rear=null;
        this.size=0;
    }
//ENQUEUE kuruğun arkasına ekleme demektir ve bunun kod olucak bu kısım
    public void enqueue(Node data){
        QNode newNode=new QNode(data);
        if (rear==null) {
            front = rear = newNode;
        }else{
            //sona ekleyip kuruğu uzatıyorum
            rear.next=newNode;
            rear=newNode;
        }
        size++;
    }
//DEQUEUE kuyruğun başından çıkar ve ver
    public Node dequeue() {
        if (front == null) {
            return null;
        }
        Node temp = front.data;//en öndekini al
        front = front.next;//sırayı kaydır
        if (front == null) {
            rear = null;
        }
        size--;
        return temp;
    }
    public boolean isEmpty(){//kuyruk boş mu kontrolü
        return front==null;

    }
}

 */
package com.example.pathfindingvisualizeragorithm;

public class MyQueue {

    // Sadece kuyruk içinde kullanılan düğüm sınıfı
    private class QNode {
        Node data;
        QNode next;
        public QNode(Node data) {
            this.data = data;
            this.next = null;
        }
    }

    private QNode front; // Kuyruk başı (Çıkış)
    private QNode rear;  // Kuyruk sonu (Giriş)
    private int size;

    public MyQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    // Kuyruğun sonuna ekle
    public void enqueue(Node data) {
        QNode newNode = new QNode(data);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    // Kuyruğun başından çıkar
    public Node dequeue() {
        if (front == null) return null;
        Node temp = front.data;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return temp;
    }

    public boolean isEmpty() {
        return front ==null;
    }
}