import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByN0 = new OffByN();
    static CharacterComparator offByN = new OffByN(3);

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("", offByN0));
        assertTrue(palindrome.isPalindrome(" ", offByN0));
        assertTrue(palindrome.isPalindrome("",offByN));
        assertTrue(palindrome.isPalindrome(" ", offByN));

        assertTrue(palindrome.isPalindrome("racecar") == palindrome.isPalindrome("racecar", offByN0));
        assertTrue(palindrome.isPalindrome("noon") == palindrome.isPalindrome("noon", offByN0));

        assertTrue(palindrome.isPalindrome("sloop", offByN));
        assertTrue(palindrome.isPalindrome("whet", offByN));


    }


}
