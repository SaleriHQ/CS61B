public class UnionFindDS implements DisjointSets {

    int[] id;

    public UnionFindDS(int N) {
        id = new int[N];
        for (int i = 0; i < N; ++i)
            id[i] = -1;
    }

    public int find(int p) {
        if (id[p] < 0)
            return p;
        id[p] = find(id[p]);
        return id[p];
    }

    @Override
    public void connect(int p, int q) {
        int p_id = find(p);
        int q_id = find(q);
        if (p_id == q_id)
            return;
        if (id[p_id] > id[q_id]) {
            id[p_id] += id[q_id];
            id[q_id] = p_id;
        } else {
            id[q_id] += id[p_id];
            id[p_id] = q_id;
        }
    }

    @Override
    public boolean isConnect(int p, int q) {
        return find(p) == find(q);
    }

    public int sizeOf(int p) {
        return Math.abs(find(p));
    }

}
