public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordDeque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    /**
     * Return true if the given word is a palindrome, false otherwise.
     */

    private boolean isPalindromeHelp(Deque<Character> wordToDeque) {
        if (wordToDeque.size() <= 1) {
            return true;
        }
        return wordToDeque.removeFirst() == wordToDeque.removeLast() && isPalindromeHelp(wordToDeque);
    }

    public boolean isPalindrome(String word) {
        return isPalindromeHelp(wordToDeque(word));
    }

    private boolean isPalindromeHelp(Deque<Character> wordToDeque, CharacterComparator cc) {
        if (wordToDeque.size() <= 1) {
            return true;
        }
        return cc.equalChars(wordToDeque.removeFirst(), wordToDeque.removeLast()) && isPalindromeHelp(wordToDeque, cc);
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        return isPalindromeHelp(wordToDeque(word), cc);
    }
}