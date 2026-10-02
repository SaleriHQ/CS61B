import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BSTTest {
    @Test
    public void putGetAndSizeWork() {
        BST<Integer, String> bst = new BST<>();

        assertTrue(bst.isEmpty());
        assertEquals(0, bst.size());

        bst.put(5, "five");
        bst.put(3, "three");
        bst.put(7, "seven");

        assertFalse(bst.isEmpty());
        assertEquals(3, bst.size());
        assertEquals("three", bst.get(3));
        assertTrue(bst.contains(7));
        assertFalse(bst.contains(9));
        assertNull(bst.get(9));
    }

    @Test
    public void puttingExistingKeyUpdatesValueWithoutChangingSize() {
        BST<Integer, String> bst = new BST<>();
        bst.put(5, "five");
        bst.put(5, "FIVE");

        assertEquals(1, bst.size());
        assertEquals("FIVE", bst.get(5));
    }
}
