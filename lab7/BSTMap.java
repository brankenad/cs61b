import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
	private Node root;

	public class Node {
		private K key;
		private V value;
		private Node left, right;
		private int size = 0;

		public Node(K k, V v) {
			key = k;
			value = v;
			left = null;
			right = null;
			size = 1;
		}
	}

	@Override
	public void clear() {
		root = null;
	}

	@Override
	public boolean containsKey(K key) {
		return get(key) != null;
	}

	private V getHelp(Node r, K key) {
		if (r == null) {
			return null;
		}
		int com = r.key.compareTo(key);
		if (com == 0) {
			return r.value;
		} else if (com > 0) {
			return getHelp(r.left, key);
		} else {
			return getHelp(r.right, key);
		}
	}

	@Override
	public V get(K key) {
		return getHelp(root, key);
	}

	private int size(Node r) {
		if (r == null) {
			return 0;
		}
		return root.size;
	}
	@Override
	public int size() {
		return size(root);
	}

	private Node putHelp(Node r, K key, V value) {
		if (r == null) {
			return new Node(key, value);
		}
		int com = r.key.compareTo(key);
		if (com == 0) {
			r.value = value;
		} else if (com < 0) {
			r.right = putHelp(r.right, key, value);
		} else if (com > 0) {
			r.left = putHelp(r.left, key, value);
		}
		r.size = size(r.left) + size(r.right) + 1;
		return r;
	}

	@Override
	public void put(K key, V value) {
		root = putHelp(root, key, value);
	}

	@Override
	public Set<K> keySet() {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}


	public void printInOrder() {
		if (root == null) {
			System.out.print("the BST is null.\n");
			return;
		}
		printInOrderHelp(root);
	}

	private void printInOrderHelp(Node r) {
		if (r.left == null && r.right == null) {
			printNode(r);
		} else if (r.right == null) {
			printInOrderHelp(r.left);
			printNode(r);
		} else if (r.left == null) {
			printNode(r);
			printInOrderHelp(r.right);
		} else {
			printInOrderHelp(r.left);
			printNode(r);
			printInOrderHelp(r.right);
		}
	}

	private void printNode(Node r) {
		System.out.println(r.key + ":" + r.value);
	}
}