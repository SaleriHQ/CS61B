import org.junit.Test;

import static org.junit.Assert.*;

public class ExUnionFindTest {
    @Test
    public void initializationCreatesSingletons() {
        ExUnionFind uf = new ExUnionFind(5);
        assertEquals(5, uf.count());
        for (int i = 0; i < 5; i++) {
            assertEquals(i, uf.find(i));
            assertEquals(i, uf.parentOf(i));
            assertEquals(1, uf.sizeOf(i));
        }
    }

    @Test
    public void unionUsesSizeAndIgnoresRepeatedUnion() {
        ExUnionFind uf = new ExUnionFind(8);
        uf.union(0, 1);
        uf.union(2, 3);
        uf.union(0, 2);
        uf.union(4, 5);
        uf.union(6, 7);
        uf.union(4, 6);
        uf.union(0, 4);

        assertEquals(1, uf.count());
        assertEquals(8, uf.sizeOf(7));
        int root = uf.find(0);
        for (int i = 1; i < 8; i++) {
            assertEquals(root, uf.find(i));
        }

        uf.union(3, 7);
        assertEquals("重复 union 不应改变集合数量", 1, uf.count());
        assertEquals("重复 union 不应把集合大小翻倍", 8, uf.sizeOf(3));
    }

    @Test
    public void findFullyCompressesThePath() {
        ExUnionFind uf = new ExUnionFind(8);
        uf.union(0, 1);
        uf.union(2, 3);
        uf.union(0, 2);
        uf.union(4, 5);
        uf.union(6, 7);
        uf.union(4, 6);
        uf.union(0, 4);

        int root = uf.find(7);
        assertEquals(root, uf.parentOf(7));
        assertEquals(root, uf.parentOf(6));
        assertEquals(root, uf.parentOf(4));
    }

    @Test
    public void invalidIndicesAreRejected() {
        ExUnionFind uf = new ExUnionFind(3);
        assertThrows(IllegalArgumentException.class, () -> uf.find(-1));
        assertThrows(IllegalArgumentException.class, () -> uf.find(3));
        assertThrows(IllegalArgumentException.class, () -> new ExUnionFind(-1));
    }
}
