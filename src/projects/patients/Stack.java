class Stack<TreeNode> {

    private int stackPointer;
    private final int STACK_SIZE = 20;
    private Object[] theStack = null;

    // lets construct the stack

    public Stack(int STACK_SIZE) {
        // Stack<TreeNode> treeNodeStack = new Stack<TreeNode>(STACK_SIZE);
        theStack = new Object[STACK_SIZE];
        stackPointer = 0;
    }

    public void push(Object newItem) {
        if (stackPointer == STACK_SIZE) {
            throw new IllegalStateException("the stack is full");
        } else {

            theStack[stackPointer] = newItem;
            stackPointer++;
        }
    }

    @SuppressWarnings("unchecked")
    public TreeNode pop() {
        TreeNode object = null;
        if (stackPointer > 0) {
            object = (TreeNode) theStack[stackPointer - 1];
            stackPointer--;
        } else {
            return null;
        }
        return object;
    }

    public void empty() {
        while (stackPointer != 0) {
            pop();
        }
    }

    public Object peek() {
        if (stackPointer == 0) {
            System.out.println("the stack is empty");
        } else {
            return theStack[stackPointer - 1];
        }
        return null;

    }


    public int getStackPointer() {
        return stackPointer;
    }

}