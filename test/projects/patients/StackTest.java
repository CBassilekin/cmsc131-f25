import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class StackTest {
    private Stack<String> stack;
    private final int SIZE = 20;

    /**
     * Test verifies that the constructor initializes the stack correctly
     */
    @Before
    public void setUp() {
        stack = new Stack<String>(SIZE);
    }

    /**
     * Test verifies that the constructor initializes the stack correctly
     */
    @Test
    public void testConstructorIsValid() {
        assertNotNull(stack);
        // Use the method from your Stack class, not a local variable
        assertEquals(0, stack.getStackPointer());
    }

    /**
     * Test verifies that push() adds items to the stack and updates the stack pointer correctly
     */
    @Test
    public void testPushWorksCorrectly() {
        stack.push("First");
        assertEquals(1, stack.getStackPointer());

        stack.push("Second");
        assertEquals(2, stack.getStackPointer());

        stack.push("Third");
        assertEquals(3, stack.getStackPointer());
    }

    /**
     * Test verifies that push() throws an exception when trying to push onto a full stack
     */
    @Test
    public void testPushWithFullStack() {
        // Fill the stack to its limit (20)
        for (int i = 1; i <= 20; i++) {
            stack.push("Item " + i);
        }

        // Test for overflow using JUnit 4 try-catch
        try {
            stack.push("Twenty-one");
            fail("Should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            assertEquals("the stack is full", e.getMessage());
        }
    }

    /**
     * Test verifies that pop() removes items from the stack in LIFO order and updates the stack pointer correctly
     */
    @Test
    public void testPopEmpty() {
        assertNull(stack.pop());
    }

    /**
    * Test verifies that pop() removes items from the stack in LIFO order and updates the stack pointer correctly
    */
    @Test
    public void testPopNotEmptyUntilEmpty() {
        stack.push("First");
        stack.push("Second");
        stack.push("Third");

        // LIFO order: Last In, First Out
        assertEquals("Third", stack.pop());
        assertEquals(2, stack.getStackPointer());

        assertEquals("Second", stack.pop());
        assertEquals(1, stack.getStackPointer());

        assertEquals("First", stack.pop());
        assertEquals(0, stack.getStackPointer());

        assertNull(stack.pop());
    }

    /**
     * Test verifies that empty() clears the stack and resets the stack pointer
     */
    @Test
    public void testEmpty() {
        stack.push("A");
        stack.push("B");
        stack.empty();
        assertEquals(0, stack.getStackPointer());
        assertNull(stack.pop());
    }

    /**
     * Test verifies that peek() returns the top item without modifying the stack
     */
    @Test
    public void testPeekReturnsTopItem() {
        stack.push("First");
        stack.push("Second");

        // Peek should see the top but not remove it
        assertEquals("Second", stack.peek());
        assertEquals(2, stack.getStackPointer());
        assertEquals("Second", stack.peek());
    }

    /**
     * Test verifies that peek() returns null when the stack is empty
     */
    @Test
    public void testPeekEmptyStack() {
        assertNull(stack.peek());
    }

    /**
     * Test verifies that peek() does not modify the stack
     */
    @Test
    public void testPeekDoesNotModifyStack() {
        stack.push("First");
        stack.push("Second");
        stack.push("Third");
        // peek should return top item without modifying the stack
        assertEquals("Third", stack.peek());
        // stack should still have the same top item after peek
        assertEquals("Third", stack.peek());

    }

}