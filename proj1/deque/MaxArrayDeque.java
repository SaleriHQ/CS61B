package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        comparator = c;
    }

    public T max() {
        return max(comparator);
    }

    public T max(Comparator<T> c) {
        if (isEmpty())
            return null;

        T res = get(0);
        for (int i = 1; i < size(); ++i) {
            T p = get(i);
            if (comparator.compare(p, res) > 0) {
                res = p;
            }
        }
        return res;
    }
}
