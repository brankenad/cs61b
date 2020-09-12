package es.datastructur.synthesizer;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void testCapacity() {
        //ArrayRingBuffer arb = new ArrayRingBuffer(10);
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        assertEquals(10, arb.capacity());
        assertEquals(0, arb.fillCount());
        arb.enqueue(0);
        arb.enqueue(1);
        arb.enqueue("a");
        assertEquals(0, arb.peek());
        assertEquals(0, arb.dequeue());
    }

}
