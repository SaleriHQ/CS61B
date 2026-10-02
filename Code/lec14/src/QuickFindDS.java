public class QuickFindDS implements DisjointSets {

    private int[] id;

    public QuickFindDS(int N) {
        id = new int[N];
        for (int i = 0; i < N; ++i)
            id[i] = i;
    }

    /**
     * 将p 和 q的id设置为相同的
     */
    @Override
    public void connect(int p, int q) {
        int p_id = id[p];
        int q_id = id[q];
        for (int i = 0; i < id.length; ++i)
            if (id[i] == p_id)
                id[i] = q_id;
    }

    @Override
    public boolean isConnect(int p, int q) {
        return id[p] == id[q];
    }
}
