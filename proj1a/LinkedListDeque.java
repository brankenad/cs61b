/**
 *Creat a Generic Linked List Deque 
 */

public class LinkedListDeque<T> {
	private  class LinkedListNode {
		private LinkedListNode prev,
							   next;
		private T items;

		private LinkedListNode(LinkedListNode p, T x, LinkedListNode n) {
			prev = p;
			next = n;
			items = x;
		}
	}

	private int size;
	private LinkedListNode sentienel;

	public LinkedListDeque() {
		sentienel = new LinkedListNode(null, null, null);
		sentienel.prev = sentienel;
		sentienel.next = sentienel;
		size = 0;
	}
 
	public LinkedListDeque(T x) {
		sentienel = new LinkedListNode(null, null, null);
		sentienel.prev = new LinkedListNode(sentienel, x, sentienel);
		sentienel.next = sentienel.prev;
		size = 1;
	}

	public LinkedListDeque(LinkedListDeque other) {
		sentienel = new LinkedListNode(null, null, null);
		sentienel.prev = sentienel;
		sentienel.next = sentienel;
		size = 0;
		for (int i = 0; i < other.size(); i = i + 1) {
			addLast((T) other.get(i));
		}
	}

	/**
	 * Return the number of items in the deque.
	 */

	public int size() {
		return size;
	}

	/**
	 * Return true if deque is empty, false otherwise.
	 */

	public boolean isEmpty() {
		return size == 0;
	}

	/**	
	 * Get the item at the given index, where 0 is the front, 1 is the next item, and so forth.
	 * If no such item exists, returns null.
	 */

	public T get(int index) {
		LinkedListNode track = sentienel;
		for (int i = 0; i < index; i = i + 1) {
			track = track.next;
		}

		return track.next.items;
	}

	/**
	 * Help method of function getRecursive
	 */

	private T getRecursiveHelp(int index, LinkedListNode m) {
		if (index == 0) {
			return m.next.items;
		}

		return getRecursiveHelp(index - 1, m.next);
	}

    /**
     * Same as get, but uses recursion
     */

	public T getRecursive(int index) {
		return getRecursiveHelp(index, sentienel);
	}

	/**
	 * Print the items in the deque from first to last, separated by a space.
	 * Once all the items have been printed, print out a new line.
	 */

	public void printDeque() {
		LinkedListNode track = sentienel;
		for (int i = 0; i < size; i = i + 1) {
			System.out.print(track.next.items + " ");
		}

		System.out.print("\n");
	}

	/**
	 * Add an item of type T to the front of the deque.
	 */

	public void addFirst(T item) {
		sentienel.next = new LinkedListNode(sentienel, item, sentienel.next);
		sentienel.next.next.prev = sentienel.next;
		size = size + 1;
	}

	/**
	 * Remove and returns the item at the front of the deque.
	 * If no such item exists, returns null.
	 */

	public T removeFirst() {
		if (!isEmpty()) {
			size = size - 1;
		}
		T rm = sentienel.next.items;
		sentienel.next = sentienel.next.next;
		sentienel.next.prev = sentienel;
		return rm;
	}

	/**
	 * Add an item of type T to the back of the deque.
	 */

	public void addLast(T item) {
		sentienel.prev = new LinkedListNode(sentienel.prev, item, sentienel);
		sentienel.prev.prev.next = sentienel.prev;
		size = size + 1;
	}

	/**
	 * Remove and returns the item at the front of the deque.
	 * If no such item exists, returns null.
	 */

	public T removeLast() {
		if (!isEmpty()) {
			size = size - 1;
		}
		T rm = sentienel.prev.items;
		sentienel.prev = sentienel.prev.prev;
		sentienel.prev.next = sentienel;
		return rm;
	}
}