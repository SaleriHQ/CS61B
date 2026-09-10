package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.google.common.base.Objects;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] data;
    // head 是队首前一格，tail
    // 是队尾后一格；存满时它们不一定指向空位https://sp21.datastructur.es/materials/proj/proj1/proj1#project-tasks。
    // 通过 size == 0 / size == data.length 区分空与满。
    private int head;
    private int tail;
    private int size;

    public ArrayDeque() {
        this(8);
    }

    public ArrayDeque(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        data = (T[]) new Object[length];
        head = length - 1;
        tail = 0;
        size = 0;
    }

    public ArrayDeque(T i) {
        this(8);
        addLast(i);
    }

    public void resize(boolean isBig) {
        T[] t;
        if (isBig)
            t = (T[]) new Object[data.length * 2];
        else
            t = (T[]) new Object[data.length / 2];
        int first = (head + 1) % data.length;
        int firstPart = Math.min(size, data.length - first);
        System.arraycopy(data, first, t, 0, firstPart);
        System.arraycopy(data, 0, t, firstPart, size - firstPart);
        data = t;
        head = data.length - 1;
        tail = size;
    }

    public void addFirst(T item) {
        if (size == data.length) {
            resize(true);
        }
        data[head] = item;
        head = (head - 1 + data.length) % data.length;
        size++;
    }

    public void addLast(T item) {
        if (size == data.length) {
            resize(true);
        }
        data[tail] = item;
        tail = (tail + 1 + data.length) % data.length;
        size++;
    }

    public boolean isZero() {
        return head == tail && size == 0;
    }

    // 1. 判断合理
    // 2. 删除元素
    // 3. 结算指针
    public T removeFirst() {
        if (size == 0)
            return null;
        head = (head + 1 + data.length) % data.length;
        T res = data[head];
        size -= 1;
        if (isZero()) {
        }
        if (size < data.length / 2)
            resize(false);
        return res;
    }

    public T removeLast() {
        if (size == 0)
            return null;
        tail = (tail - 1 + data.length) % data.length;
        T res = data[tail];
        size -= 1;
        if (size < data.length / 2)
            resize(false);
        return res;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (size == 0) {
            System.out.println();
            return;
        }
        for (int i = 0; i < size; ++i)
            System.out.print(get(i) + " ");
        System.out.println();
    }

    public T get(int index) {
        if (index < 0 || index >= size)
            return null;
        return data[(head + index + 1 + data.length) % data.length];
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int next_index;

            @Override
            public boolean hasNext() {
                return next_index < size;
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException("End");
                return get(next_index++);
            }

        };
    }

    public boolean equals(Object o) {
        if (!(o instanceof Deque<?>))
            return false;
        Deque<?> otehr = (Deque<?>) o;
        if (size != otehr.size())
            return false;
        Iterator<T> p_iter = this.iterator();
        Iterator<?> o_iter = otehr.iterator();
        while (p_iter.hasNext())
            if (!Objects.equal(p_iter.next(), o_iter.next()))
                return false;
        return true;
    }
}
