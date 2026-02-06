import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }
    @Test
    @DisplayName("Test basic addFirst and addLast")
    public void addFirstAndAddLastTestBasic() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        ad1.addFirst(10);
        assertThat(ad1.toList()).containsExactly(10).inOrder();

        ad1.addFirst(20);
        assertThat(ad1.toList()).containsExactly(20, 10).inOrder();

        ad1.addLast(30);
        assertThat(ad1.toList()).containsExactly(20, 10, 30).inOrder();

        ad1.addLast(40);
        assertThat(ad1.toList()).containsExactly(20, 10, 30, 40).inOrder();
    }

    @Test
    @DisplayName("Test isEmpty method")
    public void isEmptyTest() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();

        // New deque should be empty
        assertThat(ad1.isEmpty()).isTrue();

        // After adding an element, should not be empty
        ad1.addFirst("test");
        assertThat(ad1.isEmpty()).isFalse();

        // After removing, should be empty again
        ad1.removeFirst();
        assertThat(ad1.isEmpty()).isTrue();

        // Test with addLast
        ad1.addLast("another");
        assertThat(ad1.isEmpty()).isFalse();
        ad1.removeLast();
        assertThat(ad1.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Test size method")
    public void sizeTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        // Initial size should be 0
        assertThat(ad1.size()).isEqualTo(0);

        // Test size with addFirst
        ad1.addFirst(1);
        assertThat(ad1.size()).isEqualTo(1);

        ad1.addFirst(2);
        assertThat(ad1.size()).isEqualTo(2);

        // Test size with addLast
        ad1.addLast(3);
        assertThat(ad1.size()).isEqualTo(3);

        // Test size with removals
        ad1.removeFirst();
        assertThat(ad1.size()).isEqualTo(2);

        ad1.removeLast();
        assertThat(ad1.size()).isEqualTo(1);

        ad1.removeFirst();
        assertThat(ad1.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Test removeFirst method")
    public void removeFirstTest() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();

        // Removing from empty deque should return null
        assertThat(ad1.removeFirst()).isNull();

        // Test with single element
        ad1.addFirst("only");
        assertThat(ad1.removeFirst()).isEqualTo("only");
        assertThat(ad1.isEmpty()).isTrue();

        // Test with multiple elements added with addFirst
        ad1.addFirst("c");
        ad1.addFirst("b");
        ad1.addFirst("a");

        assertThat(ad1.removeFirst()).isEqualTo("a");
        assertThat(ad1.removeFirst()).isEqualTo("b");
        assertThat(ad1.removeFirst()).isEqualTo("c");
        assertThat(ad1.isEmpty()).isTrue();

        // Test with mixed adds
        ad1.addLast("x");
        ad1.addFirst("y");
        ad1.addLast("z");
        // Expected: [y, x, z]
        assertThat(ad1.removeFirst()).isEqualTo("y");
        assertThat(ad1.removeFirst()).isEqualTo("x");
        assertThat(ad1.removeFirst()).isEqualTo("z");
    }

    @Test
    @DisplayName("Test removeLast method")
    public void removeLastTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        // Removing from empty deque should return null
        assertThat(ad1.removeLast()).isNull();

        // Test with single element
        ad1.addFirst(42);
        assertThat(ad1.removeLast()).isEqualTo(42);
        assertThat(ad1.isEmpty()).isTrue();

        // Test with multiple elements
        ad1.addLast(1);
        ad1.addLast(2);
        ad1.addLast(3);
        // Expected: [1, 2, 3]

        assertThat(ad1.removeLast()).isEqualTo(3);
        assertThat(ad1.removeLast()).isEqualTo(2);
        assertThat(ad1.removeLast()).isEqualTo(1);
        assertThat(ad1.isEmpty()).isTrue();

        // Test with mixed adds
        ad1.addFirst(10);
        ad1.addLast(20);
        ad1.addFirst(30);
        // Expected: [30, 10, 20]
        assertThat(ad1.removeLast()).isEqualTo(20);
        assertThat(ad1.removeLast()).isEqualTo(10);
        assertThat(ad1.removeLast()).isEqualTo(30);
    }

    @Test
    @DisplayName("Test get method with index")
    public void getTest() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();

        // Getting from empty deque should return null (based on your LinkedList implementation)
        // Note: Your ArrayDeque get() has a bug - it doesn't handle empty case properly

        // Setup a deque with elements
        ad1.addLast("a");
        ad1.addLast("b");
        ad1.addLast("c");
        ad1.addLast("d");
        ad1.addLast("e");

        // Test valid indices
        assertThat(ad1.get(0)).isEqualTo("a");
        assertThat(ad1.get(1)).isEqualTo("b");
        assertThat(ad1.get(2)).isEqualTo("c");
        assertThat(ad1.get(3)).isEqualTo("d");
        assertThat(ad1.get(4)).isEqualTo("e");

        // Test after some removals
        ad1.removeFirst(); // removes "a"
        // Now: [b, c, d, e]
        assertThat(ad1.get(0)).isEqualTo("b");
        assertThat(ad1.get(1)).isEqualTo("c");
        assertThat(ad1.get(2)).isEqualTo("d");
        assertThat(ad1.get(3)).isEqualTo("e");

        ad1.removeLast(); // removes "e"
        // Now: [b, c, d]
        assertThat(ad1.get(0)).isEqualTo("b");
        assertThat(ad1.get(1)).isEqualTo("c");
        assertThat(ad1.get(2)).isEqualTo("d");
    }

    @Test
    @DisplayName("Test circular array behavior with wrap-around")
    public void circularBehaviorTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        // Fill the initial array (capacity is 8)
        for (int i = 0; i < 7; i++) {
            ad1.addLast(i);
        }
        // Array: [0, 1, 2, 3, 4, 5, 6, _, front=0, last=6, size=7

        // Remove some from front to create space at beginning
        ad1.removeFirst(); // removes 0
        ad1.removeFirst(); // removes 1
        ad1.removeFirst(); // removes 2
        // Array: [_, _, _, 3, 4, 5, 6, _, front=3, last=6, size=4

        // Add more elements that should wrap around
        ad1.addLast(7);
        ad1.addLast(8);
        // Array: [8, _, _, 3, 4, 5, 6, 7, front=3, last=0, size=6

        // Verify the list
        assertThat(ad1.toList()).containsExactly(3, 4, 5, 6, 7, 8).inOrder();

        // Continue adding to trigger resize
        ad1.addLast(9);
        ad1.addLast(10);
        // This should trigger resize (size=8, capacity=8, adding 9th element)

        // Verify after resize
        assertThat(ad1.size()).isEqualTo(8);
        // Should contain: 3, 4, 5, 6, 7, 8, 9, 10
        for (int i = 0; i < 8; i++) {
            assertThat(ad1.get(i)).isEqualTo(i + 3);
        }
    }

    @Test
    @DisplayName("Test resize functionality")
    public void resizeTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        // Initial capacity is 8
        // Add 9 elements to trigger resize
        for (int i = 0; i < 9; i++) {
            ad1.addLast(i);
        }

        // Size should be 9
        assertThat(ad1.size()).isEqualTo(9);

        // Verify all elements are there
        for (int i = 0; i < 9; i++) {
            assertThat(ad1.get(i)).isEqualTo(i);
        }

        // Remove some, add more to test multiple resizes
        for (int i = 0; i < 5; i++) {
            ad1.removeFirst();
        }
        // Now: [5, 6, 7, 8], size=4

        // Add many more to trigger another resize
        for (int i = 9; i < 20; i++) {
            ad1.addLast(i);
        }

        // Size should be 15 (4 + 11)
        assertThat(ad1.size()).isEqualTo(15);

        // Verify all elements
        List<Integer> expected = new java.util.ArrayList<>();
        for (int i = 5; i < 20; i++) {
            expected.add(i);
        }
        assertThat(ad1.toList()).containsExactlyElementsIn(expected).inOrder();
    }

    @Test
    @DisplayName("Test toList method")
    public void toListTest() {
        Deque61B<Character> ad1 = new ArrayDeque61B<>();

        // Empty deque
        assertThat(ad1.toList()).isEmpty();

        // Single element
        ad1.addFirst('A');
        assertThat(ad1.toList()).containsExactly('A').inOrder();
        ad1.removeFirst();

        // Multiple elements with addFirst
        ad1.addFirst('C');
        ad1.addFirst('B');
        ad1.addFirst('A');
        assertThat(ad1.toList()).containsExactly('A', 'B', 'C').inOrder();

        // Clear and test with addLast
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeFirst();

        ad1.addLast('X');
        ad1.addLast('Y');
        ad1.addLast('Z');
        assertThat(ad1.toList()).containsExactly('X', 'Y', 'Z').inOrder();

        // Mixed operations
        ad1.addFirst('W');
        ad1.addLast('A');
        // Expected: [W, X, Y, Z, A]
        assertThat(ad1.toList()).containsExactly('W', 'X', 'Y', 'Z', 'A').inOrder();
    }

    @Test
    @DisplayName("Test mixed operations comprehensively")
    public void mixedOperationsTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        // Start with some elements
        ad1.addLast(1);
        ad1.addFirst(0);
        ad1.addLast(2);
        ad1.addLast(3);
        ad1.addFirst(-1);
        // Expected: [-1, 0, 1, 2, 3]

        assertThat(ad1.size()).isEqualTo(5);
        assertThat(ad1.isEmpty()).isFalse();

        // Test get
        assertThat(ad1.get(0)).isEqualTo(-1);
        assertThat(ad1.get(2)).isEqualTo(1);
        assertThat(ad1.get(4)).isEqualTo(3);

        // Test removals
        assertThat(ad1.removeFirst()).isEqualTo(-1);
        assertThat(ad1.removeLast()).isEqualTo(3);
        // Now: [0, 1, 2]

        assertThat(ad1.size()).isEqualTo(3);
        assertThat(ad1.toList()).containsExactly(0, 1, 2).inOrder();

        // Add more
        ad1.addFirst(-2);
        ad1.addLast(4);
        ad1.addLast(5);
        // Now: [-2, 0, 1, 2, 4, 5]

        // Test all methods together
        assertThat(ad1.size()).isEqualTo(6);
        assertThat(ad1.isEmpty()).isFalse();
        assertThat(ad1.get(0)).isEqualTo(-2);
        assertThat(ad1.get(5)).isEqualTo(5);

        // Remove all
        for (int i = 0; i < 6; i++) {
            ad1.removeFirst();
        }

        assertThat(ad1.isEmpty()).isTrue();
        assertThat(ad1.size()).isEqualTo(0);
        assertThat(ad1.removeFirst()).isNull();
        assertThat(ad1.removeLast()).isNull();
    }

    @Test
    @DisplayName("Test edge cases with wrap-around and full array")
    public void edgeCasesTest() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();

        // Test 1: Add until full, then remove all
        for (int i = 0; i < 8; i++) {
            ad1.addLast("item" + i);
        }
        // Array is full, size=8

        assertThat(ad1.size()).isEqualTo(8);
        assertThat(ad1.isEmpty()).isFalse();

        // Remove all
        for (int i = 0; i < 8; i++) {
            assertThat(ad1.removeFirst()).isEqualTo("item" + i);
        }

        assertThat(ad1.isEmpty()).isTrue();

        // Test 2: Create wrap-around scenario
        for (int i = 0; i < 4; i++) {
            ad1.addLast("A" + i);
        }
        // Remove 2 from front
        ad1.removeFirst();
        ad1.removeFirst();
        // Add to create wrap
        ad1.addLast("B1");
        ad1.addLast("B2");
        ad1.addLast("B3");
        ad1.addLast("B4");
        // Should trigger resize

        // Verify
        assertThat(ad1.size()).isEqualTo(6);
        assertThat(ad1.toList()).containsExactly("A2", "A3", "B1", "B2", "B3", "B4").inOrder();
    }
}
