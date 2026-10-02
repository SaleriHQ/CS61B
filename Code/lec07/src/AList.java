
public class AList {
    private int[] data;
    private int size;

    public AList() {
        data = new int[100];
        size = 0;
    }

    public void resize() {
        int[] t = new int[size * 2];
        System.arraycopy(data, 0, t, 0, size);
        data = t;
    }

    public void addLast(int x) {
        if (size == data.length)
            resize();
        data[size++] = x;
    }

    public int getLast() {
        return data[size - 1];
    }

    public int get(int i) {
        return data[i];
    }

    public int size() {
        return size;
    }

    public int removeLast() {
        return data[--size];
    }
}
