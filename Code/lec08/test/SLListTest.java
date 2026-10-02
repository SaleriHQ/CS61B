import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** 继承 List61B 契约测试，并补充 SLList 特有的构造器测试。 */
public class SLListTest extends List61BTest {
    @Override
    protected <T> List61B<T> newList() {
        return new SLList<>();
    }

    @Test
    public void emptyConstructorCreatesEmptyList() {
        SLList<String> list = new SLList<>();
        assertEquals(0, list.size());
    }

    // 与 lec05 和 lab3 的 SLList 一致：参数是首个元素，而不是哨兵值。
    @Test
    public void itemConstructorCreatesSingleton() {
        SLList<String> list = new SLList<>("first");
        assertEquals(1, list.size());
        assertEquals("first", list.getFirst());
        assertEquals("first", list.getLast());
        assertEquals("first", list.removeLast());
        assertEquals(0, list.size());
    }

    @Test
    public void itemConstructorPreservesItemWhenAppending() {
        SLList<String> list = new SLList<>("first");
        list.addLast("second");
        assertEquals(2, list.size());
        assertEquals("first", list.getFirst());
        assertEquals("second", list.removeLast());
        assertEquals("first", list.removeLast());
        assertEquals(0, list.size());
    }
}
