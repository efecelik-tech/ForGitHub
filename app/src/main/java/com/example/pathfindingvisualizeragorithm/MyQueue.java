
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
