package bearmaps;

import java.util.*;

import static org.junit.Assert.assertNotEquals;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {


	private class PriorityNode implements Comparable<PriorityNode> {
		private T item;
		private double priority;

		public PriorityNode(T i, double p) {
			item = i;
			priority = p;
		}

		@Override
		public int compareTo(PriorityNode o) {
			if (this.priority > o.priority) {
				return 1;
			} else if (this.priority == o.priority) {
				return 0;
			}
			return -1;
		}
	}

	private ArrayList<PriorityNode> items;
	private Map<T, Integer> tMap;

	public ArrayHeapMinPQ() {
		items = new ArrayList<>();
		items.add(0, null);
		tMap = new HashMap<>();
	}

	private void swap(ArrayList<PriorityNode> arrayList, int i, int j) {
		PriorityNode tempNode = arrayList.get(i);
		arrayList.set(i, arrayList.get(j));
		arrayList.set(j, tempNode);
		tMap.replace(arrayList.get(i).item, i);
		tMap.replace(arrayList.get(j).item, j);

	}

	private int parentNodeIndex(int i) {
		return i / 2;
	}

	private void swim(ArrayList<PriorityNode> arrayList, int currIndex) {
		if (currIndex == 1) {
			return;
		}
		int parentNodeIndex = parentNodeIndex(currIndex);
		if (arrayList.get(currIndex).compareTo(arrayList.get(parentNodeIndex)) < 0) {
			swap(arrayList, currIndex, parentNodeIndex);
			swim(arrayList, parentNodeIndex);
		}

	}

	/* Adds an item with the given priority value. Throws an
	 * IllegalArgumentExceptionb if item is already present.
	 * You may assume that item is never null. */
	@Override
	public void add(T item, double priority) {
		if (contains(item)) {
			throw new IllegalArgumentException();
		}
		items.add(new PriorityNode(item, priority));
		tMap.put(item, size());
		swim(items, size());
	}

	/* Returns true if the PQ contains the given item. */
	@Override
	public boolean contains(T item) {
		return tMap.containsKey(item);
	}

	/* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
	@Override
	public T getSmallest() {
		if (size() == 0) {
			throw new NoSuchElementException();
		}
		return items.get(1).item;
	}

	private int leftChildIndex(int i) {
		return i * 2;
	}

	private int rightChildIndex(int i) {
		return i * 2 + 1;
	}

	private void sink(ArrayList<PriorityNode> arrayList, int currIndex) {
		int leftChildIndex = leftChildIndex(currIndex);
		int rightChildIndex = rightChildIndex(currIndex);
		int exchangeIndex;
		if (rightChildIndex <= arrayList.size() - 1 &&
			arrayList.get(leftChildIndex).compareTo(arrayList.get(rightChildIndex)) > 0) {
			exchangeIndex = rightChildIndex;
		} else if (leftChildIndex <= arrayList.size() - 1) {
			exchangeIndex = leftChildIndex;
		} else {
			return;
		}
		if (arrayList.get(currIndex).compareTo(arrayList.get(exchangeIndex)) > 0) {
			swap(arrayList, currIndex, exchangeIndex);
			sink(arrayList,exchangeIndex);
		}
	}

	/* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
	@Override
	public T removeSmallest() {
		if (size() == 0) {
			throw new NoSuchElementException();
		}
		T removeItem = items.get(1).item;
		tMap.remove(removeItem);
		items.set(1, items.get(size()));
		tMap.replace(items.get(1).item, 1);
		items.remove(size());
		sink(items, 1);
		return removeItem;
	}

	/* Returns the number of items in the PQ. */
	@Override
	public int size() {
		return items.size() - 1;
	}

	/* Changes the priority of the given item. Throws NoSuchElementException if the item
	 * doesn't exist. */
	@Override
	public void changePriority(T item, double priority) {
		if (!contains(item)) {
			throw new NoSuchElementException();
		}
		PriorityNode temp = new PriorityNode(item, priority);
		int itemIndex = tMap.get(item);
		int compare = items.get(itemIndex).compareTo(temp);
		items.get(itemIndex).priority = priority;
		if (compare < 0) {
			sink(items, itemIndex);
		} else if (compare > 0) {
			swim(items, itemIndex);
		}
	}

	public static void main(String[] args) {
		ArrayHeapMinPQ<String> aHMinPQ = new ArrayHeapMinPQ<>();
		for (int i = 0; i < 100; i += 1) {
			aHMinPQ.add("akdfgalfha" + i, i);
		}
		for (String key : aHMinPQ.tMap.keySet()) {
			System.out.println(key + ":" + aHMinPQ.tMap.get(key));
		}
		aHMinPQ.changePriority("akdfgalfha" + 0, 99 - 0);
	}
}