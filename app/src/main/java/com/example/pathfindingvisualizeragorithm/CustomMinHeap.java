package com.example.pathfindingvisualizeragorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CustomMinHeap {
    private Node[] heap;
    private int size;
    private int capacity;
    // O(1) erişim için pozisyon haritası
    private Map<Node, Integer> positionMap;

    public CustomMinHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new Node[capacity];
        this.positionMap = new HashMap<>();
    }

    public boolean isEmpty() {
        return size == 0; }

    public void add(Node node) {
        if (size == capacity) {
            heap = Arrays.copyOf(heap, capacity * 2);
            capacity *= 2;
        }
        heap[size] = node;
        positionMap.put(node, size);
        heapifyUp(size);
        size++;
    }

    public Node extractMin() {
        if (isEmpty()) return null;
        Node min = heap[0];
        Node last = heap[size - 1];
        heap[0] = last;
        positionMap.put(last, 0);
        positionMap.remove(min);
        size--;
        heapifyDown(0);
        return min;
    }

    public boolean contains(Node node) { return positionMap.containsKey(node); }

    public void updateNode(Node node) {
        if (positionMap.containsKey(node)) heapifyUp(positionMap.get(node));
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index].compareTo(heap[parent]) < 0) {
                swap(index, parent);
                index = parent;
            } else break;
        }
    }

    private void heapifyDown(int index) {
        int smallest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        if (left < size && heap[left].compareTo(heap[smallest]) < 0) smallest = left;
        if (right < size && heap[right].compareTo(heap[smallest]) < 0) smallest = right;
        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    private void swap(int i, int j) {
        Node temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        positionMap.put(heap[i], i);
        positionMap.put(heap[j],j);
    }
}