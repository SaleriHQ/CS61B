/**
 * A 级第 3(d) 题：使用并查集实现 SimpleOomage 集合。
 *
 * <p>题目：假设元素的 hashCode 是完美哈希值，不同元素的哈希值互不相同，
 * 且一定在 0 到 140607 之间。请只使用一个 {@link ExUnionFind} 和一个额外的
 * 哨兵位置实现集合：</p>
 * <ul>
 *     <li>{@link #add(Object)} 把元素加入集合。</li>
 *     <li>{@link #contains(Object)} 判断集合中是否存在该元素。</li>
 * </ul>
 *
 * <p>提示：把已经加入集合的哈希值与同一个哨兵节点连接起来。</p>
 */
public class ExSimpleOomageSet<T> {
    private static final int MAX_HASH = 140_607;
    private static final int SENTINEL = MAX_HASH + 1;
    private final ExUnionFind unionFind = new ExUnionFind(SENTINEL + 1);

    public void add(T item) {
        // TODO：把元素的完美哈希值加入集合。
    }

    public boolean contains(T item) {
        // TODO：判断元素的完美哈希值是否已经加入集合。
        return false;
    }

    private int checkedHash(T item) {
        if (item == null) {
            throw new IllegalArgumentException("元素不能为 null");
        }
        int hash = item.hashCode();
        if (hash < 0 || hash > MAX_HASH) {
            throw new IllegalArgumentException("哈希值必须位于 [0, 140607]");
        }
        return hash;
    }
}
