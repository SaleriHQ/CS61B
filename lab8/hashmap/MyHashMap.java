package hashmap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A hash table-backed Map implementation. Provides amortized constant time
 * access to elements via get(), remove(), and put() in the best case.
 *
 * Assumes null keys will never be inserted, and does not resize down upon
 * remove().
 * 
 * @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private double maxLoad;
    private int size;

    /** Constructors */
    public MyHashMap() {
        buckets = createTable(16);
        maxLoad = 0.75;
        size = 0;
    }

    public MyHashMap(int initialSize) {
        buckets = createTable(16);
        maxLoad = 0.75;
        size = 0;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad     maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        this.maxLoad = maxLoad;
        size = 0;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<Node>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return (Collection<Node>[]) new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    private int tableIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    private void resize(int newCapacity) {
        Collection<Node>[] new_buckets = createTable(newCapacity);
        for (Collection<Node> bucket : buckets) {
            if (bucket == null)
                continue;

            for (Node node : bucket) {
                int index = Math.floorMod(node.key.hashCode(), newCapacity);
                if (new_buckets[index] == null)
                    new_buckets[index] = createBucket();

                new_buckets[index].add(node);
            }
        }
        buckets = new_buckets;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V remove(K key) {
        Collection<Node> bucket = buckets[tableIndex(key)];
        if (bucket == null)
            return null;

        Iterator<Node> iter = bucket.iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node.key.equals(key)) {
                iter.remove();
                return node.value;
            }
        }

        return null;
    }

    @Override
    public V remove(K key, V val) {
        Collection<Node> bucket = buckets[tableIndex(key)];
        if (bucket == null)
            return null;

        Iterator<Node> iter = bucket.iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node.key.equals(key) && node.value.equals(val)) {
                iter.remove();
                return node.value;
            }
        }

        return null;
    }

    @Override
    public void put(K key, V val) {
        int index = tableIndex(key);
        if (buckets[index] == null)
            buckets[index] = createBucket();

        Collection<Node> bucket = buckets[index];

        for (Node node : bucket) {
            if (node.key.equals(key)) {
                node.value = val;
                return;
            }
        }

        bucket.add(createNode(key, val));

        size++;
        if ((double) size / buckets.length > maxLoad) {
            resize(buckets.length * 2);
        }
    }

    @Override
    public boolean containsKey(K key) {
        Collection<Node> bucket = buckets[tableIndex(key)];
        if (bucket == null)
            return false;

        Iterator<Node> iter = bucket.iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node.key.equals(key))
                return true;
        }

        return false;
    }

    @Override
    public V get(K key) {
        Collection<Node> bucket = buckets[tableIndex(key)];
        if (bucket == null)
            return null;

        Iterator<Node> iter = bucket.iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node.key.equals(key))
                return node.value;
        }

        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int bucketIndex = 0;
            private Iterator<Node> bucketIterator;

            @Override
            public boolean hasNext() {
                while (bucketIterator == null || !bucketIterator.hasNext()) {
                    if (bucketIndex >= buckets.length)
                        return false;

                    Collection<Node> bucket = buckets[bucketIndex++];
                    if (bucket != null)
                        bucketIterator = bucket.iterator();
                }
                return true;
            }

            @Override
            public K next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return bucketIterator.next().key;
            }
        };
    }

    @Override
    public void clear() {
        buckets = createTable(16);
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();

        for (Collection<Node> bucket : buckets) {
            if (bucket == null)
                continue;

            for (Node node : bucket)
                keys.add(node.key);
        }

        return keys;
    }

}
