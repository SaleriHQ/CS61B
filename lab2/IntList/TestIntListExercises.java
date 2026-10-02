package IntList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TestIntListExercises {

    @Test
    public void testSquareListIterativeEmptyList() {
        assertNull(IntListExercises.squareListInerative(null));
    }

    @Test
    public void testSquareListIterativeSingleElement() {
        IntList input = IntList.of(-5);

        IntList actual = IntListExercises.squareListInerative(input);

        assertListEquals(IntList.of(25), actual);
        assertListEquals(IntList.of(-5), input);
        assertNotSame("The result should be a new list.", input, actual);
    }

    @Test
    public void testSquareListIterativeMixedValues() {
        IntList input = IntList.of(-3, 0, 1, 2, -4);

        IntList actual = IntListExercises.squareListInerative(input);

        assertListEquals(IntList.of(9, 0, 1, 4, 16), actual);
        assertListEquals(IntList.of(-3, 0, 1, 2, -4), input);
        assertNoSharedNodes(input, actual);
    }

    @Test
    public void testSquareListIterativeLargestValuesWithoutOverflow() {
        IntList input = IntList.of(-46340, 46340);

        IntList actual = IntListExercises.squareListInerative(input);

        assertListEquals(IntList.of(2147395600, 2147395600), actual);
        assertListEquals(IntList.of(-46340, 46340), input);
        assertNoSharedNodes(input, actual);
    }

    /** Checks both the values and the length of two lists. */
    private static void assertListEquals(IntList expected, IntList actual) {
        int index = 0;
        while (expected != null && actual != null) {
            assertEquals("Unexpected value at index " + index,
                    expected.first, actual.first);
            expected = expected.rest;
            actual = actual.rest;
            index += 1;
        }
        assertNull("Actual list is longer than expected.", actual);
        assertNull("Actual list is shorter than expected.", expected);
    }

    /** Checks that the non-mutative method creates an entirely new list. */
    private static void assertNoSharedNodes(IntList input, IntList result) {
        while (input != null && result != null) {
            assertNotSame("The result should not reuse input nodes.", input, result);
            input = input.rest;
            result = result.rest;
        }
    }
}
