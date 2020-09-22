package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.*;


public class ArrayHeapMinPQTest {
    @Test
    public void sanitygeneric() {
        try {
            ArrayHeapMinPQ<String> aHMinPQ1 = new ArrayHeapMinPQ<>();
            ArrayHeapMinPQ<Integer> aHMinPQ2 = new ArrayHeapMinPQ<>();
            ArrayHeapMinPQ<Character> aHMinPQ3 = new ArrayHeapMinPQ<>();
            ArrayHeapMinPQ<Double> aHMinPQ4 = new ArrayHeapMinPQ<>();
        } catch(Exception e) {
            fail();
        }
    }

    @Test
    public void sanityContain() {
        ArrayHeapMinPQ<String> aHMinPQ = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100; i += 1) {
            aHMinPQ.add("ahdakash" + i, i);
        }
        for (int i = 0; i < 120; i += 1) {
            if (i < 100) {
                assertTrue(aHMinPQ.contains("ahdakash" + i));
            } else {
                assertFalse(aHMinPQ.contains("ahdakash" + i));
            }
        }
    }

    @Test
    public void sanityAdd() {
        ArrayHeapMinPQ<String> aHMinPQ = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10; i += 1) {
            aHMinPQ.add("kahfa" + i, i);
        }
        assertEquals(10, aHMinPQ.size());
    }

    @Test
    public void sanityGetSmallest() {
        ArrayHeapMinPQ<String> aHMinPQ = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10; i += 1) {
            aHMinPQ.add("akhdfa" + i, i);
        }
        assertEquals("akhdfa" + 0,aHMinPQ.getSmallest());
        aHMinPQ.add("akfasajasdf", 0);
        assertEquals("akhdfa" + 0,aHMinPQ.getSmallest());
        for (int i = 10 ; i < 20; i += 1) {
            aHMinPQ.add("aksflfalahfasl" + i, -1 * i);
            assertEquals("aksflfalahfasl" + i, aHMinPQ.getSmallest());
        }

    }

    @Test
    public void sanityRemoveSmallest() {
        ArrayHeapMinPQ<String> aHMinPQ1 = new ArrayHeapMinPQ<>();
        Set<String> actualremoveItem1 = new HashSet<>();
        Set<String> expectremoveItem1 = new HashSet<>();
        ArrayHeapMinPQ<String> aHMinPQ2 = new ArrayHeapMinPQ<>();
        Set<String> actualremoveItem2 = new HashSet<>();
        Set<String> expectremoveItem2 = new HashSet<>();
        for (int i = 0; i < 100; i += 1) {
            aHMinPQ1.add("akhlaasjdga" + i, i);
            aHMinPQ2.add("akfdlalajd" + i, 99 - i);
        }
        for (int i = 0; i < StdRandom.uniform(0, 100); i += 1) {
            actualremoveItem1.add(aHMinPQ1.removeSmallest());
            expectremoveItem1.add("akhlaasjdga" + i);
            actualremoveItem2.add(aHMinPQ2.removeSmallest());
            expectremoveItem2.add("akfdlalajd" + (99 - i));
        }
        assertEquals(expectremoveItem1,actualremoveItem1);
        assertEquals(expectremoveItem2,actualremoveItem2);
    }

    @Test
    public void sanitySize() {
        ArrayHeapMinPQ<String> aHMinPQ = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 14; i += 1) {
            aHMinPQ.add("akhdfa" + i, i);
        }
        assertEquals(14, aHMinPQ.size());
        for (int i = 0; i < 12; i += 1) {
            aHMinPQ.removeSmallest();
        }
        assertEquals(2, aHMinPQ.size());
    }

    @Test
    public void sanitychangePriority() {
        ArrayHeapMinPQ<String> aHMinPQ1 = new ArrayHeapMinPQ<>();
        ArrayHeapMinPQ<String> aHMinPQ2 = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100; i += 1) {
            aHMinPQ1.add("akdfgalfha" + i, i);
            aHMinPQ2.add("akdfgalfha" + i, 99 - i);
        }
        assertNotEquals(aHMinPQ1, aHMinPQ2);
        for (int i = 0; i < 100; i += 1) {
            aHMinPQ1.changePriority("akdfgalfha" + i, 99 - i);
        }
        for (int i = 0; i < StdRandom.uniform(10, 100); i += 1) {
            assertEquals(aHMinPQ2.removeSmallest(), aHMinPQ1.removeSmallest());
        }
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    public void sanityAddException() {
        ArrayHeapMinPQ<String> aHMinPQ1 = new ArrayHeapMinPQ<>();
        aHMinPQ1.add("ABC", 0.0);
        thrown.expect(IllegalArgumentException.class);
        aHMinPQ1.add("ABC", 1.0);
        aHMinPQ1.removeSmallest();
        thrown.expect(NoSuchElementException.class);
        aHMinPQ1.getSmallest();
        thrown.expect(NoSuchElementException.class);
        aHMinPQ1.removeSmallest();
        thrown.expect(NoSuchElementException.class);
        aHMinPQ1.changePriority("ABC", 10);
        aHMinPQ1.add("kahdaasdh", 100);
        thrown.expect(NoSuchElementException.class);
        aHMinPQ1.changePriority("ABC", 10);
    }

}

