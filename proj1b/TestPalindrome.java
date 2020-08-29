import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        boolean input1 = palindrome.isPalindrome("");
        assertTrue(input1);

        boolean input2 = palindrome.isPalindrome(" ");
        assertTrue(input2);

        boolean input3 = palindrome.isPalindrome("racecar");
        assertTrue(input3);

        boolean input4 = palindrome.isPalindrome("noon");
        assertTrue(input4);

        boolean input5 = palindrome.isPalindrome("123421");
        assertFalse(input5);

        boolean input6 = palindrome.isPalindrome("hrose");
        assertFalse(input6);
    }

    @Test
    public void testNewIsPalidrome() {
        boolean input1 = palindrome.isPalindrome("", offByOne);
        assertTrue(input1);

        boolean input2 = palindrome.isPalindrome(" ", offByOne);
        assertTrue(input2);

        boolean input3 = palindrome.isPalindrome("noon", offByOne);
        assertFalse(input3);

        boolean input4 = palindrome.isPalindrome("racecar", offByOne);
        assertFalse(input4);

        boolean input5 = palindrome.isPalindrome("flake", offByOne);
        assertTrue(input5);

        boolean input6 = palindrome.isPalindrome("ac&%&%db", offByOne);
        assertTrue(input6);

        boolean input7 = palindrome.isPalindrome("0457631", offByOne);
        assertTrue(input7);
    }
}
// Uncomment this class once you've created your Palindrome class.