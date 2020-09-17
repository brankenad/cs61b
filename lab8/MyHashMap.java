import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

public class MyHashMap<K, V> implements Map61B<K, V>{
	private class Entry {
		public K key;
		public V value;

		public Entry(K k, V v) {
			key = k;
			value = v;
		}
	}

	private ArrayList<ArrayList<Entry>> buckets;
	private HashSet<K> keySet;
	private double loadFactor;
	private int numOfBuckets;

	public MyHashMap() {
		this(16, 0.75);
	}

	public MyHashMap(int initialSize) {
		this(initialSize, 0.75);
	}

	public MyHashMap(int initialSize, double loadFactor) {
		buckets = new ArrayList<>();
		keySet = new HashSet<>();
		this.loadFactor = loadFactor;
		numOfBuckets = initialSize;
		for (int i = 0; i < numOfBuckets; i += 1) {
			buckets.add(new ArrayList<>());
		}
	}

	/** Removes all of the mappings from this map. */
	@Override
	public void clear() {
		keySet = new HashSet<>();
		buckets = new ArrayList<>();
		numOfBuckets = 0;
	}

	/** Returns true if this map contains a mapping for the specified key. */
	@Override
	public boolean containsKey(K key) {
		return keySet.contains(key);
	}

	private int hashCodeMapToIndex(K key, int i) {
		return (key.hashCode() & 0x7FFFFFFF) % i;
	}

	/**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
	@Override
	public V get(K key) {
		if (containsKey(key)) {
			int index = hashCodeMapToIndex(key, numOfBuckets);
			for (Entry entry : buckets.get(index)) {
				if(entry.key.equals(key)) {
					return entry.value;
				}
			}
		}
		return null;
	}
	/** Returns the number of key-value mappings in this map. */
	@Override
    public int size() {
    	return keySet.size();
    }

    private void resizing(int capacity) {
		ArrayList<ArrayList<Entry>> newbuckets = new ArrayList<>();
		for (int i = 0; i < capacity; i += 1) {
			newbuckets.add(new ArrayList<>());
		}
		for (K k : this) {
			int index = hashCodeMapToIndex(k, capacity);
			newbuckets.get(index).add(new Entry(k, get(k)));
		}
		this.buckets = newbuckets;
		this.numOfBuckets *= 2;
	}
    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
    	int index = hashCodeMapToIndex(key, numOfBuckets);
    	if (containsKey(key)) {
    		for (Entry entry : buckets.get(index)) {
    			if (entry.key.equals(key)) {
    				entry.value = value;
    				break;
				}
			}
		} else {
    		buckets.get(index).add(new Entry(key, value));
    		keySet.add(key);
		}
    	if ((double) keySet.size() / (double) numOfBuckets >= loadFactor) {
    		resizing(numOfBuckets * 2);
		}
	}
    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
    	return keySet;
    }

    @Override
    public V remove(K key) {
    	throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
    	throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
    	return keySet.iterator();
    }
}