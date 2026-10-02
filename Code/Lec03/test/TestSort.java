
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestSort {

    // public static void testSort() {
    // String[] input = { "i", "hava", "an", "egg" };
    // String[] output = { "an", "egg", "hava", "i" };
    //
    // Sort.sort(input);
    //
    // for (int i = 0; i < input.length; ++i) {
    // if (!input[i].equals(output[i])) {
    // System.out.println(
    // "Missmatch in position " + i + ", output is: " + output + ", but got: " +
    // input[i]);
    // break;
    // }
    // }
    // }

    @Test
    public void testSort() {
        String[] input = { "i", "hava", "an", "egg" };
        String[] output = { "an", "egg", "hava", "i" };

        Sort.sort(input, 0);

        for (String s : input) {
            System.out.println(s + " ");
        }

        assertArrayEquals(output, input);
    }

    @Test
    public void testFindSamllest() {
        String[] input = { "i", "hava", "an", "egg" };
        int output = 2;

        int res = Sort.findSmallLest(input, 0);

        assertEquals(res, output);
    }
}
