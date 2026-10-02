public class DLList<T> {
    public class IntNode {
        public IntNode prev;
        public T item;
        public IntNode next;

        public IntNode(T i, IntNode p, IntNode n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private int size;
    private IntNode sentinel;
    private IntNode last;

    public DLList(T item) {
        IntNode p = new IntNode(null, null, null);
        p.prev = p;
        sentinel = p;
        last = p;
        size = 0;
    }

    public static void main(String[] args) {
        DLList<String> ds = new DLList<String>("Hello World");
    }
}
