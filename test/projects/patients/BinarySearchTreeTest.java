import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

public class BinarySearchTreeTest {
    private BinarySearchTree tree;
    private sampleObject obj1, obj2, obj3, obj4;

    // Helper class to create sample objects for testing
    class sampleObject implements IdentifiedObject {
        int id;

        sampleObject(int id) {
            this.id = id;
        }

        //Helper class to create Identity for sampleObject
    class sampleIdentity implements Identity {
            int id;

            sampleIdentity(int id) {
                this.id = id;
            }

            //returns true if this.id is less than other    
            public boolean isLessThan(Identity other) {
                return this.id < ((sampleIdentity) other).id;
            }

            //returns true if this.id matches other.id
            public boolean match(Identity other) {
                return id == ((sampleIdentity) other).id;
            }
        }

//returns the Identity of the sampleObject
        public Identity getIdentity() {
            return new sampleIdentity(this.id);

        }
    }

    // Set up a new BinarySearchTree before each test
    @BeforeEach
    public void setUp() {
        tree = new BinarySearchTree();
    }

    // Test adding and finding objects in the binary search tree
    @Test
    public void testAddAndFind() {
        obj1 = new sampleObject(10);
        obj2 = new sampleObject(5);
        obj3 = new sampleObject(15);
        obj4 = new sampleObject(20);
        tree.add(obj1);
        tree.add(obj2);
        tree.add(obj3);
        tree.add(obj4);
        assertEquals(obj1, tree.find(obj1.getIdentity()));
        assertEquals(obj2, tree.find(obj2.getIdentity()));
        assertEquals(obj3, tree.find(obj3.getIdentity()));
        assertEquals(obj4, tree.find(obj4.getIdentity()));
    }

    // Test finding an object that does not exist in the tree
    @Test
    public void testFindNonExistent() {
        obj1 = new sampleObject(10);
        obj2 = new sampleObject(5);
        tree.add(obj1);
        tree.add(obj2);
        assertEquals(null, tree.find(new sampleObject(15).getIdentity()));
    }

    // Test adding a duplicate object to the tree
    @Test
    public void testAddDuplicate() {
        obj1 = new sampleObject(10);
        tree.add(obj1);
        tree.add(obj1);
        assertEquals(obj1, tree.find(obj1.getIdentity()));
    }

    // Test finding an object in an empty tree
    @Test
    public void testFindEmptyTree() {
        assertEquals(null, tree.find(new sampleObject(10).getIdentity()));
    }

    // Test adding a null object to the tree
    @Test
    public void testAddNull() {
        tree.add(null);
        // Ensure your find method handles null identities safely
        assertEquals(null, tree.find(null));
    }

    // Test finding a null identity in the tree
    @Test
    public void testFindNull() {
        assertEquals(null, tree.find(null));
    }

    // Test adding multiple objects and finding them
    @Test
    public void testAddMultiple() {
        for (int i = 0; i < 100; i++) {
            tree.add(new sampleObject(i));
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(i, ((sampleObject) tree.find(new sampleObject(i).getIdentity())).id);
        }
    }

    // Test finding multiple non-existent objects in the tree
    @Test
    public void testFindNonExistentMultiple() {
        for (int i = 0; i < 100; i++) {
            tree.add(new sampleObject(i));
        }
        for (int i = 100; i < 110; i++) {
            assertEquals(null, tree.find(new sampleObject(i).getIdentity()));
        }
    }
}
