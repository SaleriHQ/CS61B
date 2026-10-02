import org.junit.Test;

import static org.junit.Assert.*;

public class ExSimpleOomageSetTest {
    @Test
    public void 可以添加并查询元素() {
        ExSimpleOomageSet<MockOomage> set = new ExSimpleOomageSet<>();
        MockOomage first = new MockOomage(0);
        MockOomage last = new MockOomage(140_607);
        MockOomage absent = new MockOomage(42);

        assertFalse(set.contains(first));
        set.add(first);
        set.add(last);

        assertTrue(set.contains(first));
        assertTrue(set.contains(new MockOomage(140_607)));
        assertFalse(set.contains(absent));
    }

    @Test
    public void 重复添加不改变结果() {
        ExSimpleOomageSet<MockOomage> set = new ExSimpleOomageSet<>();
        MockOomage item = new MockOomage(1234);

        set.add(item);
        set.add(item);

        assertTrue(set.contains(item));
        assertFalse(set.contains(new MockOomage(1235)));
    }

    @Test
    public void 拒绝非法哈希值和空元素() {
        ExSimpleOomageSet<MockOomage> set = new ExSimpleOomageSet<>();

        assertThrows(IllegalArgumentException.class,
                () -> set.add(new MockOomage(-1)));
        assertThrows(IllegalArgumentException.class,
                () -> set.contains(new MockOomage(140_608)));
        assertThrows(IllegalArgumentException.class, () -> set.add(null));
    }

    private static class MockOomage {
        private final int hash;

        MockOomage(int hash) {
            this.hash = hash;
        }

        @Override
        public int hashCode() {
            return hash;
        }
    }
}
