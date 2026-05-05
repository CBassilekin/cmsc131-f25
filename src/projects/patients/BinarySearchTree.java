class BinarySearchTree {

    private TreeNode theRoot;// first root of the tree structure
    private Stack<TreeNode> iterationStack = new Stack<TreeNode>(100);
    private int count; // to keep track of the number of nodes in the tree.

    /**
     * this inner class represents a node in the binary search tree, which contains
     * an IdentifiedObject
     * as data and references to the left and right child nodes
     * the constructor initializes the data and sets the left and right child
     * references to null
     */
    class TreeNode {
        TreeNode(IdentifiedObject data) {
            this.data = data;
            left = null;
            right = null;
        }

        IdentifiedObject data;
        TreeNode left, right;
    }

    /**
     * This constructor initializes an empty binary search tree by setting the root
     * to null.
     */
    public BinarySearchTree() {
        theRoot = null;
    }

    public int getSize() {

        return count;
    }

    /**
     * this method is used to add a new IdentifiedObject to the binary search tree
     * according to the binary search tree property based on the identity of the
     * object and the identities of the existing objects in the tree
     * 
     * @param obj the IdentifiedObject to add to the binary search tree
     * 
     */
    public void add(IdentifiedObject obj) {

        if (obj == null) {
            return;
        }

        // check if the object already exists in the tree to avoid duplicates
        if (find(obj.getIdentity()) != null) {
            return;
        }

        // add the new object to the tree by creating a new TreeNode and adding it to
        // the correct position in the tree based on the binary search tree property
        theRoot = addNode(theRoot, new TreeNode(obj)); // addNode returns the resulting tree after adding the new node
        count++;
    }

    /**
     * this method is used to find the IdentifiedObject with the specified identity
     * in the binary search tree
     * 
     * @param id the identity to search for in the binary search tree
     * @return the IdentifiedObject with the specified identity if it exists in the
     *         binary search tree, or null if no such object exists
     */
    public IdentifiedObject find(Identity id) {
        TreeNode node = findNode(theRoot, id);
        if (node != null)
            return node.data;
        else
            return null;
    }

    /**
     * this method is used to add a new TreeNode to the binary search tree rooted at
     * root according to the binary search tree property based on the identity of
     * the data in the new node and the data in the existing nodes in the tree
     * 
     * @param root    the root of the subtree to which to add the new node
     * @param newNode the new TreeNode to add to the binary search tree
     * @return the resulting tree after adding the new node, which is the same as
     *         the input root if the root is not null, or the new node if the input
     *         root is null
     * 
     */
    private TreeNode addNode(TreeNode root, TreeNode newNode) {
        if (root == null) {
            return newNode;
        }
        if (newNode.data.getIdentity().isLessThan(root.data.getIdentity())) {
            root.left = addNode(root.left, newNode);
        } else {
            root.right = addNode(root.right, newNode);
        }
        return root;
    }

    /**
     * this method is used to find the TreeNode with the specified identity in the
     * binary search tree
     * 
     * @param root the root of the subtree to search for the node with the specified
     *             identity
     * @param id   the identity to search for in the subtree rooted at root
     * @return the TreeNode with the specified identity if it exists in the subtree
     *         rooted at root, or null if no such node exists
     */
    private TreeNode findNode(TreeNode root, Identity id) {
        if (root == null) {
            return null;
        }
        if (id.match(root.data.getIdentity())) {
            return root;
        }
        if (id.isLessThan(root.data.getIdentity())) {
            return findNode(root.left, id);
        } else {
            return findNode(root.right, id);
        }

    }

    /**
     * this method is used to find the leftmost node starting from a given node and
     * push all the nodes along the path to the stack
     * 
     * @param startingNode the node from which to start finding the leftmost node
     * @param stack        the stack to which to push the nodes along the path to
     *                     the leftmost node
     */
    public void findLeftMost(TreeNode startingNode, Stack<TreeNode> stack) {
        while (startingNode != null) {
            stack.push(startingNode);
            startingNode = startingNode.left;
        }
    }

    /**
     * this method is used to initialize the iteration process of the BST by
     * emptying the stack and
     * pushing the leftmost nodes starting from the root to the stack
     * it is called before starting the iteration process to ensure that the stack
     * is in the correct state
     */
    public void init() {
        iterationStack.empty();
        findLeftMost(theRoot, iterationStack);

    }

    /**
     * this method is used to return the next TreeNode in the iteration process of
     * the BST
     * 
     * @return the next TreeNode in the iteration process of the BST, or null if
     *         there are no more nodes to visit
     */
    public TreeNode next() {
        if (iterationStack.peek() == null) {
            return null;
        }
        TreeNode visited = iterationStack.pop();
        TreeNode nonvisitedTreeNode = visited.right;
        if (nonvisitedTreeNode != null) {
            findLeftMost(nonvisitedTreeNode, iterationStack);
        }

        return visited;
    }

    /**
     * this method is used to check if there are more nodes to visit in the
     * iteration
     * process of the BST
     * 
     * @return true if there are more nodes to visit in the iteration process of the
     *         BST, false otherwise
     * 
     */
    public boolean hasNext() {
        return (iterationStack.getStackPointer() > 0);
    }

    /**
     * this method is used to return the next IdentifiedObject in the iteration
     * process of the BST
     * 
     * @return the next IdentifiedObject in the iteration process of the BST, or
     *         null if there are no more nodes to visit
     * 
     */

    public IdentifiedObject nextData() {
        TreeNode nextNode = next();
        if (nextNode != null) {
            return nextNode.data;
        }
        return null;
    }
}
