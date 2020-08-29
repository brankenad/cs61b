import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testOffByOne() {
        boolean input1 = offByOne.equalChars('a', 'b');
        assertTrue(input1);
        assertTrue(offByOne.equalChars('a', 'b') == offByOne.equalChars('b', 'a'));

        boolean input2 = offByOne.equalChars('a', 'B');
        assertFalse(input2);
        assertTrue(offByOne.equalChars('a', 'B') == offByOne.equalChars('a', 'B'));

        boolean input3 = offByOne.equalChars('&', '%');
        assertTrue(input3);
        assertTrue(offByOne.equalChars('&', '%') == offByOne.equalChars('%', '&'));
    }
}
// Uncomment this class once you've created your CharacterComparator interface and OffByOne class.