/**
 * A 级第 4 题：实现带完整路径压缩的加权 Quick Union。
 *
 * <p>题目：在不查看教材实现的情况下，完成一个支持 union、find、connected、
 * sizeOf 和 count 的并查集。union 必须按集合大小合并；find 必须执行完整路径压缩，
 * 即查找路径上的每个节点在调用结束后都直接指向根节点。</p>
 *
 * <p>本实现使用两个数组，与课程习题所链接的 API 表示方式一致：</p>
 * <ul>
 *     <li>{@code parent[p]} 表示 p 的父节点；根节点的父节点是它自己。</li>
 *     <li>{@code size[r]} 仅当 r 是根节点时才有意义。</li>
 * </ul>
 *
 * <p>要求：</p>
 * <ol>
 *     <li>构造函数创建 n 个互不连通的单元素集合。</li>
 *     <li>重复合并已经连通的元素时，集合数量和集合大小不能改变。</li>
 *     <li>索引越界时抛出 {@link IllegalArgumentException}。</li>
 *     <li>不要修改公开方法签名。</li>
 * </ol>
 */
public class ExUnionFind {
    private final int[] parent;
    private final int[] size;
    private int count;

    public ExUnionFind(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n 不能为负数");
        }
        parent = new int[n];
        size = new int[n];
        count = n;

        // TODO：初始化 parent 和 size，使每个元素最初都单独构成一个集合。
    }

    /** 返回 p 的根节点，并完全压缩查找路径上的所有节点。 */
    public int find(int p) {
        validate(p);
        // TODO：找到根节点，压缩整条路径，然后返回根节点。
        return p;
    }

    /** 连接 p 和 q 所在集合，并把较小的树挂到较大的树下面。 */
    public void union(int p, int q) {
        // TODO：找到两个根；若已连接则不操作；否则按集合大小合并。
    }

    public boolean connected(int p, int q) {
        // TODO：两个元素连通，当且仅当它们的根节点相同。
        return false;
    }

    public int sizeOf(int p) {
        // TODO：返回存储在 p 的根节点处的集合大小。
        return 0;
    }

    public int count() {
        return count;
    }

    /** 仅供练习测试观察内部结构使用的包级方法。 */
    int parentOf(int p) {
        validate(p);
        return parent[p];
    }

    private void validate(int p) {
        if (p < 0 || p >= parent.length) {
            throw new IllegalArgumentException("索引越界：" + p);
        }
    }
}
