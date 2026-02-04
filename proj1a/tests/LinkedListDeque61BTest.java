import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    @DisplayName("Test isEmpty method")
    public void isEmptyTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        // New deque should be empty
        assertThat(lld1.isEmpty()).isTrue();

        // After adding an element, it should not be empty
        lld1.addFirst("test");
        assertThat(lld1.isEmpty()).isFalse();

        // After removing the element, it should be empty again
        lld1.removeFirst();
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Test size method")
    public void sizeTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        // Initial size should be 0
        assertThat(lld1.size()).isEqualTo(0);

        // Size should increase with addFirst
        lld1.addFirst(1);
        assertThat(lld1.size()).isEqualTo(1);

        lld1.addFirst(2);
        assertThat(lld1.size()).isEqualTo(2);

        // Size should decrease with removeFirst
        lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(1);

        lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(0);

        // Test with addLast
        lld1.addLast(10);
        lld1.addLast(20);
        assertThat(lld1.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test removeFirst method")
    public void removeFirstTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        // Removing from empty deque should return null
        assertThat(lld1.removeFirst()).isNull();

        // Test with single element
        lld1.addFirst("only");
        assertThat(lld1.removeFirst()).isEqualTo("only");
        assertThat(lld1.isEmpty()).isTrue();

        // Test with multiple elements
        lld1.addFirst("c");
        lld1.addFirst("b");
        lld1.addFirst("a");

        // Should remove in FIFO order for addFirst (last added is first removed)
        assertThat(lld1.removeFirst()).isEqualTo("a");
        assertThat(lld1.removeFirst()).isEqualTo("b");
        assertThat(lld1.removeFirst()).isEqualTo("c");
        assertThat(lld1.isEmpty()).isTrue();

        // Test with addLast and removeFirst (queue behavior)
        lld1.addLast("x");
        lld1.addLast("y");
        lld1.addLast("z");
        assertThat(lld1.removeFirst()).isEqualTo("x");
        assertThat(lld1.removeFirst()).isEqualTo("y");
        assertThat(lld1.removeFirst()).isEqualTo("z");
    }

    @Test
    @DisplayName("Test removeLast method")
    public void removeLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        // Removing from empty deque should return null
        assertThat(lld1.removeLast()).isNull();

        // Test with single element
        lld1.addFirst(42);
        assertThat(lld1.removeLast()).isEqualTo(42);
        assertThat(lld1.isEmpty()).isTrue();

        // Test with multiple elements added with addFirst
        lld1.addFirst(3);
        lld1.addFirst(2);
        lld1.addFirst(1);

        // Should remove in LIFO order for addFirst
        assertThat(lld1.removeLast()).isEqualTo(3);
        assertThat(lld1.removeLast()).isEqualTo(2);
        assertThat(lld1.removeLast()).isEqualTo(1);
        assertThat(lld1.isEmpty()).isTrue();

        // Test with addLast and removeLast (stack behavior)
        lld1.addLast(100);
        lld1.addLast(200);
        lld1.addLast(300);
        assertThat(lld1.removeLast()).isEqualTo(300);
        assertThat(lld1.removeLast()).isEqualTo(200);
        assertThat(lld1.removeLast()).isEqualTo(100);
    }

    @Test
    @DisplayName("Test get method with index")
    public void getTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        // Getting from empty deque should return null
        assertThat(lld1.get(0)).isNull();
        assertThat(lld1.get(5)).isNull();
        assertThat(lld1.get(-1)).isNull();

        // Setup a deque with elements
        lld1.addLast("a");
        lld1.addLast("b");
        lld1.addLast("c");
        lld1.addLast("d");

        // Test valid indices
        assertThat(lld1.get(0)).isEqualTo("a");
        assertThat(lld1.get(1)).isEqualTo("b");
        assertThat(lld1.get(2)).isEqualTo("c");
        assertThat(lld1.get(3)).isEqualTo("d");

        // Test invalid indices
        assertThat(lld1.get(4)).isNull();  // out of bounds
        assertThat(lld1.get(-1)).isNull(); // negative index
        assertThat(lld1.get(100)).isNull(); // far out of bounds

        // Test after removals
        lld1.removeFirst();  // removes "a"
        assertThat(lld1.get(0)).isEqualTo("b");
        assertThat(lld1.get(1)).isEqualTo("c");
        assertThat(lld1.get(2)).isEqualTo("d");
    }

    @Test
    @DisplayName("Test getRecursive method with index")
    public void getRecursiveTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        // Getting from empty deque should return null
        assertThat(lld1.getRecursive(0)).isNull();
        assertThat(lld1.getRecursive(5)).isNull();
        assertThat(lld1.getRecursive(-1)).isNull();

        // Setup a deque with elements
        for (int i = 0; i < 5; i++) {
            lld1.addLast(i * 10);  // [0, 10, 20, 30, 40]
        }

        // Test valid indices
        assertThat(lld1.getRecursive(0)).isEqualTo(0);
        assertThat(lld1.getRecursive(1)).isEqualTo(10);
        assertThat(lld1.getRecursive(2)).isEqualTo(20);
        assertThat(lld1.getRecursive(3)).isEqualTo(30);
        assertThat(lld1.getRecursive(4)).isEqualTo(40);

        // Test invalid indices
        assertThat(lld1.getRecursive(5)).isNull();  // out of bounds
        assertThat(lld1.getRecursive(-1)).isNull(); // negative index
    }

    @Test
    @DisplayName("Compare get and getRecursive methods")
    public void getAndGetRecursiveComparisonTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        // Add some elements in mixed order
        lld1.addFirst("first");
        lld1.addLast("second");
        lld1.addFirst("newfirst");
        lld1.addLast("third");

        // get and getRecursive should return same results
        assertThat(lld1.get(0)).isEqualTo(lld1.getRecursive(0));
        assertThat(lld1.get(1)).isEqualTo(lld1.getRecursive(1));
        assertThat(lld1.get(2)).isEqualTo(lld1.getRecursive(2));
        assertThat(lld1.get(3)).isEqualTo(lld1.getRecursive(3));

        // Both should handle edge cases the same way
        assertThat(lld1.get(4)).isEqualTo(lld1.getRecursive(4));  // both null
        assertThat(lld1.get(-1)).isEqualTo(lld1.getRecursive(-1)); // both null
    }

    @Test
    @DisplayName("Test comprehensive mixed operations")
    public void mixedOperationsTest() {
        Deque61B<Character> lld1 = new LinkedListDeque61B<>();

        // Start empty
        assertThat(lld1.isEmpty()).isTrue();
        assertThat(lld1.size()).isEqualTo(0);

        // Add elements
        lld1.addFirst('B');
        lld1.addFirst('A');
        lld1.addLast('C');
        lld1.addLast('D');

        // Verify state: [A, B, C, D]
        assertThat(lld1.isEmpty()).isFalse();
        assertThat(lld1.size()).isEqualTo(4);
        assertThat(lld1.get(0)).isEqualTo('A');
        assertThat(lld1.get(3)).isEqualTo('D');

        // Remove from both ends
        assertThat(lld1.removeFirst()).isEqualTo('A');
        assertThat(lld1.removeLast()).isEqualTo('D');

        // Verify new state: [B, C]
        assertThat(lld1.size()).isEqualTo(2);
        assertThat(lld1.get(0)).isEqualTo('B');
        assertThat(lld1.get(1)).isEqualTo('C');
        assertThat(lld1.getRecursive(0)).isEqualTo('B');
        assertThat(lld1.getRecursive(1)).isEqualTo('C');

        // More operations
        lld1.addFirst('X');
        lld1.addLast('Y');
        // State: [X, B, C, Y]
        assertThat(lld1.size()).isEqualTo(4);
        assertThat(lld1.removeFirst()).isEqualTo('X');
        assertThat(lld1.removeLast()).isEqualTo('Y');
        assertThat(lld1.removeFirst()).isEqualTo('B');
        assertThat(lld1.removeLast()).isEqualTo('C');

        // Should be empty now
        assertThat(lld1.isEmpty()).isTrue();
        assertThat(lld1.size()).isEqualTo(0);
        assertThat(lld1.removeFirst()).isNull();
        assertThat(lld1.removeLast()).isNull();
    }

    @Test
    @DisplayName("Test edge cases with single element")
    public void singleElementEdgeCasesTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        // Add single element
        lld1.addFirst(999);

        // Test all methods with single element
        assertThat(lld1.isEmpty()).isFalse();
        assertThat(lld1.size()).isEqualTo(1);
        assertThat(lld1.get(0)).isEqualTo(999);
        assertThat(lld1.getRecursive(0)).isEqualTo(999);
        assertThat(lld1.get(1)).isNull();  // out of bounds

        // Remove should work and result in empty deque
        assertThat(lld1.removeFirst()).isEqualTo(999);
        assertThat(lld1.isEmpty()).isTrue();
        assertThat(lld1.size()).isEqualTo(0);

        // Add again and remove from other end
        lld1.addLast(888);
        assertThat(lld1.removeLast()).isEqualTo(888);
        assertThat(lld1.isEmpty()).isTrue();
    }
}