package deque;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * CS61B Spring 2021 Project 1 的公开接口行为测试。
 * 依据：https://sp21.datastructur.es/materials/proj/proj1/proj1
 * 章节：The Deque API、2. Array Deque、Deque Interface。
 * <p>
 * 只使用规范中的无参构造器和公开 API；不读写私有字段、不调用 resize，
 * 不假定指针布局、增长倍数、自定义构造器或 null 元素的行为。
 * 性能、初始底层容量 8、内存使用率要求仍需另外审查，功能测试不能证明它们。
 * iterator 的删除、耗尽后 next、遍历中修改队列的行为不在本测试约定内。
 */
public class ArrayDequeTest {
    private static void assertSequence(ArrayDeque<?> deque, List<?> expected) {
        assertEquals("size", expected.size(), deque.size());
        assertEquals("isEmpty", expected.isEmpty(), deque.isEmpty());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals("get(" + i + ")", expected.get(i), deque.get(i));
        }
        assertEquals("get 不应修改 size", expected.size(), deque.size());
    }

    @SafeVarargs
    private static <T> void assertItems(ArrayDeque<T> deque, T... expected) {
        List<T> list = new ArrayList<>();
        for (T item : expected) {
            list.add(item);
        }
        assertSequence(deque, list);
    }

    private static ArrayDeque<String> words(String... values) {
        ArrayDeque<String> deque = new ArrayDeque<>();
        for (String value : values) {
            deque.addLast(value);
        }
        return deque;
    }

    @Test
    public void constructorCreatesEmptyDeque() {
        assertItems(new ArrayDeque<Integer>());
    }

    @Test
    public void addFirstUpdatesSizeAndOrder() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1);
        assertItems(deque, 1);
        deque.addFirst(2);
        assertItems(deque, 2, 1);
        deque.addFirst(3);
        assertItems(deque, 3, 2, 1);
    }

    @Test
    public void addLastUpdatesSizeAndOrder() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        assertItems(deque, 1);
        deque.addLast(2);
        assertItems(deque, 1, 2);
        deque.addLast(3);
        assertItems(deque, 1, 2, 3);
    }

    @Test
    public void mixedAddsPreserveLogicalOrder() {
        ArrayDeque<String> deque = words("B");
        deque.addFirst("A");
        deque.addLast("C");
        deque.addFirst("Z");
        assertItems(deque, "Z", "A", "B", "C");
    }

    @Test
    public void emptyRemovalsReturnNullWithoutChangingSize() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            assertNull(deque.removeFirst());
            assertNull(deque.removeLast());
            assertItems(deque);
        }
    }

    @Test
    public void removeFirstReturnsAndRemovesFront() {
        ArrayDeque<String> deque = words("A", "B", "C");
        assertEquals("A", deque.removeFirst());
        assertItems(deque, "B", "C");
        assertEquals("B", deque.removeFirst());
        assertItems(deque, "C");
        assertEquals("C", deque.removeFirst());
        assertItems(deque);
        assertNull(deque.removeFirst());
    }

    @Test
    public void removeLastReturnsAndRemovesBack() {
        ArrayDeque<String> deque = words("A", "B", "C");
        assertEquals("C", deque.removeLast());
        assertItems(deque, "A", "B");
        assertEquals("B", deque.removeLast());
        assertItems(deque, "A");
        assertEquals("A", deque.removeLast());
        assertItems(deque);
        assertNull(deque.removeLast());
    }

    @Test
    public void allSingleElementAddRemoveCombinations() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        for (int i = 0; i < 80; i++) {
            String item = "item-" + i;
            boolean addAtFront = i % 2 == 0;
            boolean removeAtFront = i % 4 < 2;
            if (addAtFront) {
                deque.addFirst(item);
            } else {
                deque.addLast(item);
            }
            assertEquals(1, deque.size());
            assertFalse(deque.isEmpty());
            String removed = removeAtFront ? deque.removeFirst() : deque.removeLast();
            assertEquals("round=" + i + ", add=" + (addAtFront ? "first" : "last")
                    + ", remove=" + (removeAtFront ? "first" : "last"), item, removed);
            assertItems(deque);
        }
    }

    @Test
    public void mixedRemovalsPreserveMiddle() {
        ArrayDeque<String> deque = words("B", "C");
        deque.addFirst("A");
        deque.addLast("D");
        assertEquals("A", deque.removeFirst());
        assertEquals("D", deque.removeLast());
        assertItems(deque, "B", "C");
    }

    @Test
    public void getUsesIndexFromFrontAndDoesNotMutate() {
        ArrayDeque<String> deque = words("B", "C");
        deque.addFirst("A");
        for (int i = 0; i < 5; i++) {
            assertItems(deque, "A", "B", "C");
        }
        assertEquals("A", deque.removeFirst());
        assertEquals("C", deque.removeLast());
        assertEquals("B", deque.removeFirst());
        assertItems(deque);
    }

    @Test
    public void invalidGetOnEmptyReturnsNull() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        for (int index : new int[] { -1, 0, 1, Integer.MIN_VALUE, Integer.MAX_VALUE }) {
            assertNull("index=" + index, deque.get(index));
        }
        assertItems(deque);
    }

    @Test
    public void invalidGetOnNonemptyReturnsNullWithoutMutation() {
        ArrayDeque<String> deque = words("A", "B");
        for (int index : new int[] { -1, 2, 100, Integer.MIN_VALUE, Integer.MAX_VALUE }) {
            assertNull("index=" + index, deque.get(index));
        }
        assertItems(deque, "A", "B");
    }

    @Test
    public void repeatedForwardQueueOperations() {
        slidingWindow(true);
    }

    @Test
    public void repeatedReverseQueueOperations() {
        slidingWindow(false);
    }

    private static void slidingWindow(boolean forward) {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        java.util.ArrayDeque<Integer> expected = new java.util.ArrayDeque<>();
        for (int i = 0; i < 40; i++) {
            deque.addLast(i);
            expected.addLast(i);
        }
        for (int i = 40; i < 2040; i++) {
            if (forward) {
                assertEquals("step=" + i, expected.pollFirst(), deque.removeFirst());
                expected.addLast(i);
                deque.addLast(i);
            } else {
                assertEquals("step=" + i, expected.pollLast(), deque.removeLast());
                expected.addFirst(i);
                deque.addFirst(i);
            }
            assertSequence(deque, new ArrayList<>(expected));
        }
    }

    @Test
    public void largeAddLastAndDrainFirst() {
        largeDrain(false);
    }

    @Test
    public void largeAddFirstAndDrainLast() {
        largeDrain(true);
    }

    private static void largeDrain(boolean front) {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int round = 0; round < 3; round++) {
            for (int i = 0; i < 10000; i++) {
                if (front) {
                    deque.addFirst(i);
                } else {
                    deque.addLast(i);
                }
                assertEquals(i + 1, deque.size());
            }
            for (int i = 0; i < 10000; i++) {
                assertEquals(Integer.valueOf(front ? 9999 - i : i), deque.get(i));
            }
            for (int i = 0; i < 10000; i++) {
                assertEquals(Integer.valueOf(i), front ? deque.removeLast() : deque.removeFirst());
                assertEquals(9999 - i, deque.size());
            }
            assertItems(deque);
        }
    }

    @Test
    public void supportsGenericTypesAndIndependentInstances() {
        ArrayDeque<Double> numbers = new ArrayDeque<>();
        ArrayDeque<String> strings = new ArrayDeque<>();
        ArrayDeque<Boolean> flags = new ArrayDeque<>();
        numbers.addFirst(3.5);
        strings.addLast("text");
        flags.addFirst(true);
        assertItems(numbers, 3.5);
        assertItems(strings, "text");
        assertItems(flags, true);
        strings.addFirst("more");
        assertItems(numbers, 3.5);
        assertItems(flags, true);
    }

    @Test
    public void duplicatesRemainSeparateElements() {
        ArrayDeque<String> deque = words("same", "same", "other");
        assertEquals("same", deque.removeFirst());
        assertItems(deque, "same", "other");
        assertEquals("other", deque.removeLast());
        assertItems(deque, "same");
    }

    private static String printed(ArrayDeque<?> deque) throws Exception {
        PrintStream original = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (PrintStream capture = new PrintStream(bytes, true, "UTF-8")) {
            System.setOut(capture);
            deque.printDeque();
        } finally {
            System.setOut(original);
        }
        return bytes.toString("UTF-8").replace("\r\n", "\n");
    }

    private static void assertPrinted(String expected, ArrayDeque<?> deque) throws Exception {
        String actual = printed(deque);
        assertTrue("题目要求最后打印换行", actual.endsWith("\n"));
        String line = actual.substring(0, actual.length() - 1);
        // 允许最后一个元素后的空格，但不能用 trim 掩盖缺少换行或额外行。
        assertEquals(expected, line.replaceFirst(" +$", ""));
    }

    @Test
    public void printEmptyIncludesNewline() throws Exception {
        ArrayDeque<String> deque = new ArrayDeque<>();
        assertPrinted("", deque);
        assertItems(deque);
    }

    @Test
    public void printSingleElementIncludesNewline() throws Exception {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(42);
        assertPrinted("42", deque);
        assertItems(deque, 42);
    }

    @Test
    public void printLogicalOrderWithoutMutation() throws Exception {
        ArrayDeque<String> deque = words("B", "C");
        deque.addFirst("A");
        assertPrinted("A B C", deque);
        assertPrinted("A B C", deque);
        assertItems(deque, "A", "B", "C");
        assertEquals("A", deque.removeFirst());
        assertEquals("C", deque.removeLast());
        assertItems(deque, "B");
    }

    private static Iterable<?> iterable(Object deque) {
        // 经 Object 检查，让尚未实现 Iterable 的作业报告断言失败而非阻止整套编译。
        assertTrue("题目要求 ArrayDeque implements Iterable<T>", deque instanceof Iterable<?>);
        return (Iterable<?>) deque;
    }

    @Test
    public void emptyIteratorHasNoElements() {
        Iterator<?> iterator = iterable(new ArrayDeque<String>()).iterator();
        assertNotNull(iterator);
        assertFalse(iterator.hasNext());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void iteratorTraversesInOrderWithoutMutation() {
        ArrayDeque<String> deque = words("B", "C");
        deque.addFirst("A");
        Iterator<?> iterator = iterable(deque).iterator();
        for (String expected : new String[] { "A", "B", "C" }) {
            assertTrue(iterator.hasNext());
            assertTrue("hasNext 不应推进迭代器", iterator.hasNext());
            assertEquals(expected, iterator.next());
        }
        assertFalse(iterator.hasNext());
        assertItems(deque, "A", "B", "C");
    }

    @Test
    public void iteratorsHaveIndependentPositions() {
        ArrayDeque<String> deque = words("A", "B");
        Iterator<?> first = iterable(deque).iterator();
        Iterator<?> second = iterable(deque).iterator();
        assertEquals("A", first.next());
        assertEquals("B", first.next());
        assertEquals("A", second.next());
        assertFalse(first.hasNext());
        assertTrue(second.hasNext());
        assertEquals("B", second.next());
        assertFalse(second.hasNext());
    }

    @Test
    public void enhancedForAfterLargeMixedAdds() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            if (i % 2 == 0) {
                deque.addFirst(i);
                expected.add(0, i);
            } else {
                deque.addLast(i);
                expected.add(i);
            }
        }
        List<Object> actual = new ArrayList<>();
        for (Object item : iterable(deque)) {
            assertTrue("迭代器不应产生多余元素", actual.size() < expected.size());
            actual.add(item);
        }
        assertEquals(expected, actual);
    }

    @Test
    public void equalsIsReflexiveAndRejectsNonDeque() {
        ArrayDeque<String> deque = words("A");
        assertTrue(deque.equals(deque));
        assertFalse(deque.equals(null));
        assertFalse(deque.equals("A"));
        assertFalse(deque.equals(Arrays.asList("A")));
    }

    @Test
    public void equalsComparesValuesNotReferences() {
        ArrayDeque<String> first = words(new String("A"), new String("B"));
        ArrayDeque<String> second = words(new String("A"), new String("B"));
        ArrayDeque<String> third = words(new String("A"), new String("B"));
        assertTrue(first.equals(second));
        assertTrue(second.equals(first));
        assertTrue(second.equals(third));
        assertTrue(first.equals(third));
        assertItems(first, "A", "B");
        assertItems(second, "A", "B");
    }

    @Test
    public void emptyDequesAreEqual() {
        assertTrue(new ArrayDeque<String>().equals(new ArrayDeque<Integer>()));
    }

    @Test
    public void equalsDistinguishesOrderLengthAndContents() {
        ArrayDeque<String> deque = words("A", "B");
        assertFalse(deque.equals(words("B", "A")));
        assertFalse(deque.equals(words("A")));
        assertFalse(deque.equals(words("A", "B", "C")));
        assertFalse(deque.equals(words("A", "C")));
        assertFalse(deque.equals(new ArrayDeque<String>()));
    }

    @Test
    public void equalsIgnoresInsertionHistory() {
        ArrayDeque<String> first = words("A", "B", "C");
        ArrayDeque<String> second = new ArrayDeque<>();
        second.addFirst("C");
        second.addFirst("B");
        second.addFirst("A");
        assertTrue(first.equals(second));
        assertTrue(second.equals(first));
    }

    @Test
    public void equalsAcceptsOtherDequeImplementation() throws Exception {
        // 使用独立的 Deque 测试对象，避免 LinkedListDeque 的错误干扰本类测试。
        Class<?> contract = projectDequeInterface();
        List<Object> contents = new ArrayList<>();
        contents.add(new String("A"));
        contents.add(new String("B"));
        Object other = Proxy.newProxyInstance(contract.getClassLoader(),
                new Class<?>[] { contract, Iterable.class }, (proxy, method, args) -> {
                    switch (method.getName()) {
                        case "size":
                            return contents.size();
                        case "isEmpty":
                            return contents.isEmpty();
                        case "get":
                            int index = (Integer) args[0];
                            return index < 0 || index >= contents.size() ? null : contents.get(index);
                        case "iterator":
                            return contents.iterator();
                        case "addFirst":
                            contents.add(0, args[0]);
                            return null;
                        case "addLast":
                            contents.add(args[0]);
                            return null;
                        case "removeFirst":
                            return contents.isEmpty() ? null : contents.remove(0);
                        case "removeLast":
                            return contents.isEmpty() ? null : contents.remove(contents.size() - 1);
                        case "printDeque":
                            for (int i = 0; i < contents.size(); i++) {
                                if (i > 0) {
                                    System.out.print(" ");
                                }
                                System.out.print(contents.get(i));
                            }
                            System.out.println();
                            return null;
                        case "equals":
                            Object candidate = args[0];
                            if (!contract.isInstance(candidate)
                                    || !Integer.valueOf(contents.size()).equals(
                                            contract.getMethod("size").invoke(candidate))) {
                                return false;
                            }
                            for (int i = 0; i < contents.size(); i++) {
                                if (!contents.get(i).equals(
                                        contract.getMethod("get", int.class).invoke(candidate, i))) {
                                    return false;
                                }
                            }
                            return true;
                        case "hashCode":
                            return contents.hashCode();
                        case "toString":
                            return contents.toString();
                        default:
                            throw new AssertionError("未定义的测试对象操作：" + method.getName());
                    }
                });
        ArrayDeque<String> array = words(new String("A"), new String("B"));
        assertTrue("equals 应接受相同内容的另一种 Deque 实现", array.equals(other));
        contents.set(1, "C");
        assertFalse("同长度、不同内容不相等", array.equals(other));
        contents.set(1, "B");
        contents.add("C");
        assertFalse("不同长度不相等", array.equals(other));
    }

    private static Class<?> projectDequeInterface() throws Exception {
        Class<?> contract;
        try {
            contract = Class.forName("deque.Deque");
        } catch (ClassNotFoundException error) {
            throw new AssertionError("题目要求创建 deque.Deque 接口", error);
        }
        assertTrue("Deque 应是接口", contract.isInterface());
        return contract;
    }

    @Test
    public void implementsProjectDequeInterface() throws Exception {
        Class<?> contract = projectDequeInterface();
        assertTrue("ArrayDeque 应实现 Deque", contract.isAssignableFrom(ArrayDeque.class));
    }

    @Test
    public void seededRandomOperationsMatchReference() {
        for (long seed : new long[] { 61L, 2026L, 12345L }) {
            Random random = new Random(seed);
            ArrayDeque<Integer> actual = new ArrayDeque<>();
            java.util.ArrayDeque<Integer> expected = new java.util.ArrayDeque<>();
            for (int step = 0; step < 5000; step++) {
                int operation = random.nextInt(8);
                int value = random.nextInt(100);
                String context = "seed=" + seed + ", step=" + step + ", op=" + operation;
                try {
                    switch (operation) {
                        case 0:
                        case 1:
                            expected.addFirst(value);
                            actual.addFirst(value);
                            break;
                        case 2:
                        case 3:
                            expected.addLast(value);
                            actual.addLast(value);
                            break;
                        case 4:
                            assertEquals(expected.pollFirst(), actual.removeFirst());
                            break;
                        case 5:
                            assertEquals(expected.pollLast(), actual.removeLast());
                            break;
                        case 6:
                            assertNull(actual.get(-1));
                            assertNull(actual.get(expected.size()));
                            break;
                        default:
                            assertEquals(expected.isEmpty(), actual.isEmpty());
                    }
                    assertSequence(actual, new ArrayList<>(expected));
                } catch (AssertionError | RuntimeException error) {
                    throw new AssertionError(context, error);
                }
            }
            while (!expected.isEmpty()) {
                assertEquals(expected.pollFirst(), actual.removeFirst());
            }
            assertItems(actual);
        }
    }
}
