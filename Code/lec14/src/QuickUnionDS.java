public class QuickUnionDS implements DisjointSets {

    private int[] id;

    public QuickUnionDS(int N) {
        id = new int[N];
        for (int i = 0; i < N; ++i)
            id[i] = i;
    }

    public int find(int p) {
        while (id[p] >= 0)
            p = id[p];
        return p;
    }

    @Override
    public void connect(int p, int q) {
        id[find(p)] = find(q);
    }

    @Override
    public boolean isConnect(int p, int q) {
        return find(p) == find(q);
    }
}
