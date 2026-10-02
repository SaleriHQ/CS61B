import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class TestIntList {

    @Test
    public void testSize() {
        IntList l3 = new IntList(3, null);
        IntList l2 = new IntList(2, l3);
        IntList l1 = new IntList(1, l2);
        IntList l0 = new IntList(0, l1);
        System.out.println(l0.size());
        assertEquals(l0.size(), 4);
    }

    @Test
    public void testIterativeSize() {
        IntList l3 = new IntList(3, null);
        IntList l2 = new IntList(2, l3);
        IntList l1 = new IntList(1, l2);
        IntList l0 = new IntList(0, l1);
        System.out.println(l0.iterativeSize());
        assertEquals(l0.iterativeSize(), 4);
    }

    @Test
    public void testGet() {
        IntList l3 = new IntList(3, null);
        IntList l2 = new IntList(2, l3);
        IntList l1 = new IntList(1, l2);
        IntList l0 = new IntList(0, l1);
        System.out.println(l0.get(3));
        assertEquals(l0.get(3), 3);
    }

    @Test
    public void testIncrList() {
        IntList original = new IntList(1,
                new IntList(2,
                        new IntList(3, null)));

        IntList incremented = IntList.incrList(original, 5);

        assertNotSame(original, incremented);
        assertEquals(6, incremented.get(0));
        assertEquals(7, incremented.get(1));
        assertEquals(8, incremented.get(2));

        assertEquals(1, original.get(0));
        assertEquals(2, original.get(1));
        assertEquals(3, original.get(2));
    }

    @Test
    public void testDincrList() {
        IntList original = new IntList(1,
                new IntList(2,
                        new IntList(3, null)));

        IntList incremented = IntList.dincrlist(original, 5);

        assertSame(original, incremented);
        assertEquals(6, original.get(0));
        assertEquals(7, original.get(1));
        assertEquals(8, original.get(2));
    }
}
