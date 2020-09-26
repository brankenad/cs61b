import edu.princeton.cs.algs4.Queue;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class TestSortAlgs {
    private static Random rand = new Random(10);

    @Test
    public void testQuickSort() {
        Queue<Integer> items = new Queue<>();
        for (int i = 0; i < 100; i += 1) {
            items.enqueue(rand.nextInt(500));
        }

        Queue<Integer> sortedItems = QuickSort.quickSort(items);
        assertTrue(isSorted(sortedItems));
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> items = new Queue<>();
        for (int i = 0; i < 100; i += 1) {
            items.enqueue(rand.nextInt(500));
        }

        Queue<Integer> sortedItems = MergeSort.mergeSort(items);
        assertTrue(isSorted(sortedItems));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
