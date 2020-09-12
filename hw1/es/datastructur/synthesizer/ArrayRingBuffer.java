package es.datastructur.synthesizer;
import java.util.Iterator;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        rb = (T[]) new Object[capacity];
        fillCount = 0;
        first = 0;
        last = 0;
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        if (isFull()) {
            throw new RuntimeException("Ring Buffer overflow");
        }
        rb[last] = x;
        last = (last + 1) % rb.length;
        fillCount += 1;

        return;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        T removeBuffer = rb[first];
        rb[first] = null;
        first = (first + 1 ) % rb.length;
        fillCount -= 1;

        return removeBuffer;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should
        //       change.
        return rb[first];
    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

        private class ArrayRingBufferIterator implements Iterator<T> {
            private int currentIndex;
            private int count;

            public ArrayRingBufferIterator() {
                currentIndex = first;
                count = 0;
            }

            @Override
            public boolean hasNext() {
                return count < fillCount();
            }

            @Override
            public T next() {
                T currentItem = rb[currentIndex];
                currentIndex = (currentIndex + 1) % capacity();
                count += 1;
                return currentItem;
            }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> oth = (ArrayRingBuffer) other;
        if (this.capacity() != oth.capacity()) {
            return false;
        }
        if (this.fillCount() != oth.fillCount()) {
            return false;
        }
        for (T i : this) {
            if (i != oth.iterator().next()) {
                return false;
            }
        }
        return true;
    }

}
    // TODO: Remove all comments that say TODO when you're done.
