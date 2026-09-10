package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node() {
            item = null;
            prev = null;
            next = null;
        }

        public Node(T i) {
            item = i;
            prev = null;
            next = null;
        }

        public Node(T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public LinkedListDeque() {
        Node h = new Node();
        Node t = new Node(null, h, h);
        h.next = t;
        h.prev = t;

        head = h;
        tail = t;
        size = 0;
    }

    public LinkedListDeque(T i) {
        this();
        addLast(i);
    }

    public void addFirst(T item) {
        head.next = new Node(item, head, head.next);
        head.next.next.prev = head.next;
        size++;
    }

    public void addLast(T item) {
        tail.prev = new Node(item, tail.prev, tail);
        tail.prev.prev.next = tail.prev;
        size++;
    }

    public T removeFirst() {
        if (isEmpty())
            return null;
        Node oldNode = head.next;
        head.next = oldNode.next;
        oldNode.next.prev = head;
        size--;
        return oldNode.item;
    }

    public T removeLast() {
        if (isEmpty())
            return null;
        Node oldNode = tail.prev;
        tail.prev = oldNode.prev;
        oldNode.prev.next = tail;
        size--;
        return oldNode.item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node p = head.next;
        while (p != tail) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    public T get(int index) {
        if (index >= size() || isEmpty())
            return null;

        Node p = head.next;
        for (int i = 0; i < index; ++i)
            p = p.next;

        return p.item;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node p = head.next;

            @Override
            public boolean hasNext() {
                return p != tail;
            }

            @Override
            public T next() {
                p = p.next;
                return p.prev.item;
            }
        };
    }

}
