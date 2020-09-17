import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTrieSet implements TrieSet61B{
	
	private static class Node {
		private boolean isKey;
		private Map<Character, Node> link;

		public Node(boolean isKey) {
			this.isKey = isKey;
			link = new HashMap<>();
		}
	}


	private Node root;

	public MyTrieSet() {
		root = new Node(false);
	}

	/** Clears all items out of Trie */
    @Override
    public void clear() {
    	root = null;
    }

    public boolean containsHelp(Node root, String key) {
    	char c = key.charAt(0);
    	int length = key.length();
		if (!root.link.containsKey(c)) {
			return false;
		}
		if (length == 1) {
			return root.link.get(c).isKey;
		}

    	return containsHelp(root.link.get(c), key.substring(1));
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    @Override
    public boolean contains(String key) {
    	if (root == null) {
    		return false;
		}
        if (key == null || key.length() < 1) {
		    throw new IllegalArgumentException();
		}
    	return containsHelp(root, key);
    }

    /** Inserts string KEY into Trie */
    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
		    throw new IllegalArgumentException();
		}
		Node track = root;
		for (int i = 0; i < key.length(); i += 1) {
			char c = key.charAt(i);
			if (!track.link.containsKey(c)) {
				track.link.put(c, new Node(false));
			}
			track = track.link.get(c);
		}
		track.isKey = true;
    }

    public Node find(String string) {
    	Node track = root;
    	for (int i = 0; i < string.length(); i += 1) {
    		if (!track.link.containsKey(string.charAt(i))) {
    			return null;
    		}
    		track = track.link.get(string.charAt(i));
    	}
    	return track;
    }

    public void findAllKey(String string, Node node, List<String> list) {
    	if (node == null) {
    		return;
    	}
    	if (node.isKey) {
    		list.add(string);
    	}
    	for (Character c : node.link.keySet()) {
    		findAllKey(string + c, node.link.get(c), list);
    	}
    }
    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix) {
    	List<String> result = new ArrayList<>();
    	Node track = find(prefix);
    	if (track == null) {
    		return null;
    	}
    	findAllKey(prefix, track, result);
    	return result;
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
    	throw new UnsupportedOperationException();
    }

}