package threads;

import java.util.*;

/**
 * this class provide a simple implementation of blocking queue implemented without concurrent lib
 * class has restricted functionality, dedicated to solve special task
 *
 * @param <T>
 */


public class MyBlockingQueue<T> {


    /**
     * this inner class is an element of list that the queue is based on
     *
     * @param <T>
     */
    static class Node<T> {
        private T data;
        Node<T> nextNode;

        Node(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    /**
     * initial capacity of the queue
     */
    private final int capacity;

    /**
     * number of elements containing in the queue
     */
    private volatile Integer counter = 0;
    /**
     * the head node of list
     */
    private volatile Node<T> head;
    /**
     * the last node of list
     */
    private volatile Node<T> tail;





    public MyBlockingQueue(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
            this.head = new Node(null);
            this.tail = this.head;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * synchronized getter
     * @return current number of elements containing in the queue
     */
    public synchronized int size() {
        return counter;
    }
    /**
     * synchronized method
     * @return true if queue is empty, false if not
     */
    public synchronized boolean isEmpty() {
            return counter == 0;
    }

    /**
     * inner function to insert node in base list
     * @param node
     */
    private void enqueue(Node<T> node) {
        tail.nextNode = node;
        tail = node;
    }

    /**
     * synchronized method adds new node with given data to base list
     * @param data
     * @return true if success false, if queue is full
     */
    public boolean add(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        Node<T> newNode = new Node<>(data);
        synchronized (counter) {
            if (counter < capacity) {
                if (counter == 0) {
                    head = newNode;
                    tail = head;
                    counter++;
                    return true;
                }
                enqueue(newNode);
                counter++;
                return true;
            }
            return false;
        }
    }

    /**
     * synchronized method delete and return the head of list
     * @return the head node
     */
    public T remove() {
        synchronized (counter) {
            if (counter == 0) {
                throw new NoSuchElementException();
            }
            if (head != null) {
                T result = head.getData();
                head = head.nextNode;
                counter--;
                return result;
            }
            return null;
        }
    }

    /**
     * method clears the base list
     */
    public synchronized void clear() {
        tail = null;
        head = null;
        counter = 0;
    }

}
