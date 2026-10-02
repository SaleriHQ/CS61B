package bstmap;

import java.nio.channels.UnsupportedAddressTypeException;
import java.util.Set;
import java.util.Iterator;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class Node {
        K key;
        V val;
        Node left, right;
        Integer size;

        Node(K k, V v, Integer s) {
            key = k;
            val = v;
            size = s;
        }
    }

    public Node root;

    BSTMap() {
        root = null;
    }

    /** Removes all of the mappings from this map. */
    public void clear() {
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (key == null)
            return false;

        Node node = root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp == 0)
                return true;
            node = cmp < 0 ? node.left : node.right;
        }
        return false;
    }

    /*
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return get(root, key);
    }

    V get(Node node, K key) {
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

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        if (root == null)
            return 0;
        return root.size;
    }

    int size(Node node) {
        if (node == null)
            return 0;
        return node.size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V val) {
        root = put(root, key, val);
    }

    Node put(Node node, K key, V val) {
        if (node == null)
            return new Node(key, val, 1);
        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            node.left = put(node.left, key, val);
        } else if (cmp > 0) {
            node.right = put(node.right, key, val);
        } else {
            node.val = val;
        }
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    /*
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedAddressTypeException();
    }

    /*
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedAddressTypeException();
    }

    /*
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedAddressTypeException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedAddressTypeException();
    }

    public void printInOrder() {

    }
}
