/**
 *Creat a Generic Array Deque, in which array is a cricular. 
 */

public class ArrayDeque<T>{
	private int size;
	private T[] items;
	private int nextFirst,
				nextLast;

	public ArrayDeque() {
		items = (T[]) new Object[8];
		size = 0;
		nextFirst = 0;
		nextLast = 1;
	}

	public ArrayDeque(T item) {
		items = (T[]) new Object[8];
		size = 1;
		items[0] = item;
		nextFirst = 7;
		nextLast = 1;
	}

	public ArrayDeque(ArrayDeque other) {
		items = (T[]) new Object[other.items.length];
		System.arraycopy(other.items, 0, arrDeque, 0, other.items.length);
		size = other.size;
		nextFirst = other.nextFirst;
		nextLast = other.nextLast;
	}

	/**
	 * Return true if deque is empty, false otherwise.
	 */

	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Return true if deque is full, false otherwise.
	 */

	private boolean isFull() {
		return size == items.length;
	}
		 
	/**
	 * Return true if the usage radio of ArrayDeque is less than 0.25.
	 */

	private boolean isInefficient() {
		return (items.length) > 16 && (size / items.length < 0.25);
	}

	/**
	 * Upsize the Array Deque.
	 */

	private void upSizing() {
		reSizing(size * 2);
	} 

	/**
	 * Downsize the Array Deque.
	 */

	private void downSizing() {
		reSizing(size / 2);
	}

	/**
	 *Resize the Array Deque, of which length is capacity.
	 */
	private void reSizing(int capacity) {
		T[] resizeAD = (T[]) new Object[capacity];
		for (int i  = 0; i < size; i = i + 1) {
			resizeAD[i] = get(i);
		}
		items = resizeAD;
		nextFirst = capacity - 1;
		nextLast = size;
	}

	/**
	 *Update index forward.
	 */

	private int forwardUpdate(int index) {
		return (index + 1) % items.length;
	}

	/**
	 *Update index reverse.
	 */

	private int reverseUpdate(int index) {
		return (index - 1 + items.length) % items.length;
	}

	/**
	 * Return the number of items in the deque.
	 */

	public int size() {
		return size;
	}

	/**	
	 * Get the item at the given index, where 0 is the front, 1 is the next item, and so forth.
	 * If no such item exists, returns null.
	 */

	public T get(int index) {
		return items[(forwardUpdate(nextFirst) + index) % arrDeque.length];
	}

	/**
	 * Add an item of type T to the front of the deque.
	 */

	public void addFirst(T item) {
		if (isFull()) {
			upSizing();
		}
		items[nextFirst] = item;
		nextFirst = reverseUpdate(nextFirst);
		size = size + 1;

	}

	/**
	 * Remove and returns the item at the front of the deque.
	 * If no such item exists, returns null.
	 */

	public T removeFirst() {
		T rm = get(0);
		items[forwardUpdate(nextFirst)] = null;
		nextFirst = reverseUpdate(nextFirst);
		if (!isEmpty()) {
			size = size - 1;
		}		
		if (isInefficient()) {
			downSizing();
		}
		return rm;

	}

	/**
	 * Add an item of type T to the back of the deque.
	 */

	public void addLast(T item) {
		if (isFull()) {
			upSizing();
		}
		items[nextLast] = item;
		nextLast = forwardUpdate(nextLast);
		size = size + 1;
	}

	/**
	 * Remove and returns the item at the front of the deque.
	 * If no such item exists, returns null.
	 */

	public T removeLast() {
		T rm = get(size - 1);
		items[reverseUpdate(nextLast)] = null;
		nextLast = forwardUpdate(nextLast);
		if (!isEmpty()) {
			size = size - 1;
		}
		if (isInefficient()) {
			downSizing();
		}
		return rm;

	}

	/**
	 * Print the items in the deque from first to last, separated by a space.
	 * Once all the items have been printed, print out a new line.
	 */
	
	public void printDeque() {
		for (int i = 0; i < size; i = i + 1) {
			System.out.print(get(i) + " ");
		}
		System.out.print("\n");
	}

