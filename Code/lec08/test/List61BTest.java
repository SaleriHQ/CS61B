import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

/**
 * List61B 实现类共享的契约测试，不依赖具体实现或内部结构。
 * 具体测试类继承本类并实现 newList，JUnit 会运行继承的全部测试。
 * 每次调用 newList 都应返回一个新的空列表。
 * get 使用从 0 开始的索引，insert 的有效位置为 [0, size]。
 * 当前接口未说明非法索引、空表访问及 null 的契约，因此不规定这些行为。
 */
public abstract class List61BTest {
    protected abstract <T> List61B<T> newList();

    @Test
    public void newListIsEmpty() {
        assertEquals(0, newList().size());
    }

    // 用 removeLast 验证顺序，使插入测试不依赖 get 的正确性。
    @SafeVarargs
    private final <T> void assertContentsByRemoving(List61B<T> list, T... expected) {
        assertEquals(expected.length, list.size());
        for (int i = expected.length - 1; i >= 0; i--) {
            assertEquals(expected[i], list.removeLast());
            assertEquals(i, list.size());
        }
    }

    @Test
    public void addFirstPrepends() {
        List61B<Integer> list = newList();
        list.addFirst(10);
        assertEquals(1, list.size());
        assertEquals(Integer.valueOf(10), list.getFirst());
        assertEquals(Integer.valueOf(10), list.getLast());
        list.addFirst(20);
        list.addFirst(30);
        assertContentsByRemoving(list, 30, 20, 10);
    }

    @Test
    public void addLastAppends() {
        List61B<Integer> list = newList();
        list.addLast(10);
        assertEquals(1, list.size());
        assertEquals(Integer.valueOf(10), list.getFirst());
        assertEquals(Integer.valueOf(10), list.getLast());
        list.addLast(20);
        list.addLast(30);
        assertContentsByRemoving(list, 10, 20, 30);
    }

    @Test
    public void endpointReadsDoNotChangeList() {
        List61B<String> list = newList();
        list.addLast("first");
        list.addLast("middle");
        list.addLast("last");
        for (int i = 0; i < 3; i++) {
            assertEquals("first", list.getFirst());
            assertEquals("last", list.getLast());
            assertEquals(3, list.size());
        }
        assertContentsByRemoving(list, "first", "middle", "last");
    }

    @Test
    public void getReadsSingletonAtZero() {
        List61B<Integer> list = newList();
        list.addLast(42);
        assertEquals(Integer.valueOf(42), list.get(0));
        assertContentsByRemoving(list, 42);
    }

    @Test
    public void getReadsEveryValidIndexWithoutChangingList() {
        List61B<String> list = newList();
        list.addLast("first");
        list.addLast("middle");
        list.addLast("last");
        assertEquals("last", list.get(2));
        assertEquals("first", list.get(0));
        assertEquals("middle", list.get(1));
        assertEquals("middle", list.get(1));
        assertContentsByRemoving(list, "first", "middle", "last");
    }

    @Test
    public void insertIntoEmptyList() {
        List61B<String> list = newList();
        list.insert("only", 0);
        assertEquals("only", list.getFirst());
        assertEquals("only", list.getLast());
        assertContentsByRemoving(list, "only");
    }

    @Test
    public void insertAtFrontShiftsExistingItems() {
        List61B<Integer> list = newList();
        list.addLast(20);
        list.addLast(30);
        list.insert(10, 0);
        assertContentsByRemoving(list, 10, 20, 30);
    }

    @Test
    public void insertInMiddlePreservesSuffix() {
        List61B<Integer> list = newList();
        list.addLast(10);
        list.addLast(30);
        list.addLast(40);
        list.insert(20, 1);
        assertContentsByRemoving(list, 10, 20, 30, 40);
    }

    @Test
    public void insertAtSizeAppends() {
        List61B<Integer> list = newList();
        list.addLast(10);
        list.addLast(20);
        list.insert(30, list.size());
        assertContentsByRemoving(list, 10, 20, 30);
    }

    @Test
    public void removeLastUpdatesTailAndCanEmptyList() {
        List61B<Integer> list = newList();
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        assertEquals(Integer.valueOf(30), list.removeLast());
        assertEquals(2, list.size());
        assertEquals(Integer.valueOf(20), list.getLast());
        assertEquals(Integer.valueOf(10), list.getFirst());
        assertContentsByRemoving(list, 10, 20);
    }

    @Test
    public void emptiedListCanBeReused() {
        List61B<String> list = newList();
        list.addLast("old");
        assertContentsByRemoving(list, "old");
        list.addFirst("new");
        list.addLast("tail");
        list.insert("middle", 1);
        assertContentsByRemoving(list, "new", "middle", "tail");
        list.addLast("again");
        assertContentsByRemoving(list, "again");
    }

    @Test
    public void mixedOperationsPreserveDuplicatesAndSize() {
        List61B<Integer> list = newList();
        list.addLast(2);
        list.addFirst(1);
        list.addLast(2);
        list.insert(3, 2);
        assertEquals(4, list.size());
        assertEquals(Integer.valueOf(2), list.removeLast());
        assertEquals(3, list.size());
        list.addFirst(0);
        assertContentsByRemoving(list, 0, 1, 2, 3);
    }

    @Test
    public void instancesHaveIndependentContents() {
        List61B<String> first = newList();
        List61B<String> second = newList();
        first.addLast("one");
        assertEquals(0, second.size());
        second.addFirst("two");
        assertContentsByRemoving(first, "one");
        assertContentsByRemoving(second, "two");
    }

    private String printedOutput(List61B<?> list) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (PrintStream capture = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            list.print();
        } finally {
            System.setOut(original);
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }

    @Test
    public void printEmptyListOutputsNewline() {
        List61B<String> list = newList();
        assertEquals(System.lineSeparator(), printedOutput(list));
        assertEquals(0, list.size());
    }

    @Test
    public void printOutputsItemsWithSpacesAndNewlineWithoutMutation() {
        List61B<String> list = newList();
        list.addLast("hello");
        list.addLast("world");
        // 尾部空格和换行来自 List61B.print 的默认实现。
        assertEquals("hello world " + System.lineSeparator(), printedOutput(list));
        assertContentsByRemoving(list, "hello", "world");
    }
}
