class BinarySearchTree {

    private TreeNode theRoot;// first root of the tree structure

    // Defining the TreeNode class to be used in the BinarySearchTree class
    // TreeNode class represents a node in the binary search tree,
    // containing an IdentifiedObject and references to left and right child nodes.
    private class TreeNode {
        TreeNode(IdentifiedObject data) {
            this.data = data;
            left = null;
            right = null;
        }

        IdentifiedObject data;
        TreeNode left, right;
    }

    // Building the constructor for the BinarySearchTree Class
    public BinarySearchTree() {
        theRoot = null;
    }

    // Method to add an IdentifiedObject to the binary search tree .
    public void add(IdentifiedObject obj) {

        if (obj == null) {
            return;
        }
        theRoot = addNode(theRoot, new TreeNode(obj)); // addNode returns the resulting tree after adding the new node
    }

    // Find and return the IdentifiedObject with the specified identity in the
    // binary search tree. If no such object exists, return null.
    public IdentifiedObject find(Identity id) {
        TreeNode node = findNode(theRoot, id);
        if (node != null)
            return node.data;
        else
            return null;
    }

    // returns Resulting Tree
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

    // Find the node with the given identity in the binary search tree, starting
    // from the specified root node.
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
}
