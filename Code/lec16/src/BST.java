public class BST<Key extends Comparable<Key>, Value> {
    class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int size;

        public Node(Key k, Value v, int s) {
            key = k;
            val = v;
            size = s;
        }
    }

    private Node root;

    public BST() {
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node node, Key k, Value v) {
        if (node == null)
            return new Node(k, v, 1);

        int cmp = k.compareTo(node.key);

        if (cmp < 0)
            node.left = put(node.left, k, v);
        else if (cmp > 0)
            node.right = put(node.right, k, v);
        else
            node.val = v;
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node node, Key key) {
        if (node == null)
            return null;

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            return get(node.left, key);
        } else if (cmp > 0) {
            return get(node.right, key);
        } else {
            return node.val;
        }
    }

    public void remove(Key key) {
        remove(root, key);
    }

    public Node remove(Node node, Key key) {
        if (node == null)
            return null;
        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            node.left = remove(node.left, key);
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null)
                return node.right;
            if (node.right == null)
                return node.left;
            Node tmp = node;
            node = min(tmp.right);
            node.right = remove(tmp.right, node.key);
            node.left = tmp.left;
        }
        return node;
    }

    public Node min(Node node) {
        if (node == null)
            return null;
        if (node.left == null)
            return node;
        return min(node.left);
    }

    public boolean contains(Key key) {
        return contains(root, key);
    }

    private boolean contains(Node node, Key key) {
        if (node == null) {
            return false;
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            return contains(node.left, key);
        } else if (cmp > 0) {
            return contains(node.right, key);
        } else {
            return true;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node n) {
        if (n == null)
            return 0;
        return n.size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

}
