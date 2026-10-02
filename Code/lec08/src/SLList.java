import java.util.NoSuchElementException;

public class SLList<Item> implements List61B<Item> {
    class Node {
        public Item item;
        public Node next;

        public Node() {
            item = null;
            next = null;
        }

        public Node(Item i) {
            item = i;
            next = null;
        }

        public Node(Item i, Node n) {
            item = i;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    public SLList() {
        sentinel = new Node();
        size = 0;
    }

    public SLList(Item i) {
        sentinel = new Node(null, new Node(i));
        size = 1;
    }

    @Override
    public void addFirst(Item x) {
        insert(x, 0);
    }

    @Override
    public void addLast(Item y) {
        insert(y, size);
    }

    @Override
    public Item getFirst() {
        if (size == 0)
            throw new NoSuchElementException("No such Item");
        return get(0);
    }

    @Override
    public Item getLast() {
        return get(size - 1);
    }

    @Override
    public Item removeLast() {
        if (size == 0)
            throw new NoSuchElementException("No such Item");
        Node p = sentinel;
        while (p.next.next != null)
            p = p.next;
        Item res = p.next.item;
        p.next = null;
        size--;
        return res;
    }

    @Override
    public Item get(int i) {
        if (size == 0)
            throw new NoSuchElementException("No such Item");
        Node p = sentinel.next;
        for (int j = 0; j < i; ++j)
            p = p.next;
        return p.item;
    }

    @Override
    public void insert(Item x, int position) {
        if (position > size)
            throw new NoSuchElementException();
        Node p = sentinel;
        for (int i = 0; i < position; ++i)
            p = p.next;
        p.next = new Node(x, p.next);
        size++;
    }

    @Override
    public int size() {
        return size;
    }
}
