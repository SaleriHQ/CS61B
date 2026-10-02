import java.util.NoSuchElementException;

public class SLList {
    public static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }

    private IntNode sentinel;
    private int size;

    public SLList() {
        sentinel = new IntNode(-1, null);
        size = 0;
    }

    public SLList(int x) {
        sentinel = new IntNode(-1, new IntNode(x, null));
        size = 1;
    }

    public SLList(int[] l) {
        sentinel = new IntNode(-1, null);
        IntNode p = sentinel;
        for (int i : l) {
            p.next = new IntNode(i, null);
            p = p.next;
        }
        size = l.length;
    }

    public void addFirst(int x) {
        sentinel.next = new IntNode(x, sentinel.next);
        size++;
    }

    public int getFirst() {
        if (size == 0)
            throw new NoSuchElementException("List is Empty");
        return sentinel.next.item;
    }

    public void addLast(int x) {
        IntNode p = sentinel;
        while (p.next != null)
            p = p.next;
        p.next = new IntNode(x, null);
        size++;
    }

    public int size() {
        return size;
    }

    public void deleteFirst() {
        if (sentinel.next == null)
            throw new NoSuchElementException("List is Empty");
        sentinel.next = sentinel.next.next;
    }

    public void print() {
        IntNode p = sentinel.next;
        while (p != null) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] a = { 1, 2, 3, 4, 5 };
        SLList l = new SLList(a);
        l.print();
    }
}
