import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestIntList {
    /**
     * Test double used to observe explicit size() calls. Returning a stored
     * value avoids counting the recursive calls inside IntList.size().
     */
    private static class SizeCountingIntList extends IntList {
        private static int sizeCalls;
        private static boolean counting;
        private final int reportedSize;

        SizeCountingIntList(int first, IntList rest, int reportedSize) {
            super(first, rest);
            this.reportedSize = reportedSize;
        }

        @Override
        public int size() {
            if (counting) {
                sizeCalls += 1;
                return reportedSize;
            }
            return super.size();
        }

        static void resetSizeCalls() {
            sizeCalls = 0;
            counting = true;
        }

        static void stopCountingSizeCalls() {
            counting = false;
        }
    }

    @Test
    public void testAddSquaresExistingList() {
        IntList list = new IntList(1, new IntList(2, null));

        list.add(5);

        assertListEquals(new int[] { 1, 1, 2, 4, 5 }, list);
    }

    @Test
    public void testAddTwice() {
        IntList list = new IntList(1, new IntList(2, null));

        list.add(5);
        list.add(7);

        assertListEquals(
                new int[] { 1, 1, 1, 1, 2, 4, 4, 16, 5, 25, 7 },
                list);
    }

    @Test
    public void testAddToSingleElementList() {
        IntList list = new IntList(3, null);

        list.add(4);

        assertListEquals(new int[] { 3, 9, 4 }, list);
    }

    @Test
    public void testAddWithZeroAndNegativeValues() {
        IntList list = new IntList(-2, new IntList(0, null));

        list.add(-3);

        assertListEquals(new int[] { -2, 4, 0, 0, -3 }, list);
    }

    @Test
    public void testAddCallsSizeAtMostOnce() {
        IntList tail = new SizeCountingIntList(2, null, 1);
        IntList list = new SizeCountingIntList(1, tail, 2);
        SizeCountingIntList.resetSizeCalls();

        list.add(5);

        SizeCountingIntList.stopCountingSizeCalls();
        assertTrue("add may call size() at most once, but called it "
                + SizeCountingIntList.sizeCalls + " times.",
                SizeCountingIntList.sizeCalls <= 1);
        assertListEquals(new int[] { 1, 1, 2, 4, 5 }, list);
    }

    @Test
    public void testAddAdjacent() {
        IntList l = new IntList(1, new IntList(1, new IntList(2, new IntList(3, null))));
        IntList r1 = new IntList(2, new IntList(2, new IntList(3, null)));
        IntList r2 = new IntList(4, new IntList(3, null));

        l.addAdjacent();
        assertEquals(r1.size(), l.size());
        for (int i = 0; i < r1.size(); i++) {
            assertEquals(r1.get(i), l.get(i));
        }

        l.addAdjacent();
        assertEquals(r2.size(), l.size());
        for (int i = 0; i < r2.size(); i++) {
            assertEquals(r2.get(i), l.get(i));
        }
    }

    /** Checks both values and length. */
    private static void assertListEquals(int[] expected, IntList actual) {
        for (int i = 0; i < expected.length; i += 1) {
            assertEquals("Unexpected value at index " + i,
                    expected[i], actual.get(i));
        }
        assertEquals("Unexpected list length", expected.length, actual.size());
    }

}
