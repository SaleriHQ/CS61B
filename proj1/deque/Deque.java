package deque;

import java.util.Iterator;

public interface Deque<T> {

    public void addFirst(T item);

    public void addLast(T item);

    public boolean isEmpty();

    public T removeFirst();

    public T removeLast();

    public int size();

    public void printDeque();

    public T get(int index);

    public Iterator<T> iterator();

    public boolean equals(Object o);
}
