import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class HashTableTest {

    private HashTable hashTable;

    @Before
    public void setUp() {
        hashTable = new HashTable(10);
    }

    /**
     * Test checks if the hash table is properly initialized and not null after
     * construction.
     */
    @Test
    public void testConstructor() {
        assertNotNull(hashTable);
    }

    /**
     * Test checks if the hashing method returns -1 when given a null key,
     * indicating invalid input.
     */
    @Test
    public void testHashingWithNullKey() {
        int index = hashTable.hashing(null, true);
        assertEquals(-1, index);
    }

    /**
     * Test checks if the hashing method works correctly with eight bytes.
     */
    @Test
    public void testHashingwithEightBytes() {
        int index1 = hashTable.hashing("Aspirine", true);
        int index2 = hashTable.hashing("Ibuprofen", true);
        int index3 = hashTable.hashing("Paracetamol", true);

        assertTrue(index1 >= 0 && index1 < 10);
        assertTrue(index2 >= 0 && index2 < 10);
        assertTrue(index3 >= 0 && index3 < 10);
    }

    /** Test checks if the hashing method works correctly with four bytes. */
    @Test
    public void testHashingwithFourBytes() {
        int index1 = hashTable.hashing("Aspirine", false);
        int index2 = hashTable.hashing("Ibuprofen", false);
        int index3 = hashTable.hashing("Paracetamol", false);

        assertTrue(index1 >= 0 && index1 < 10);
        assertTrue(index2 >= 0 && index2 < 10);
        assertTrue(index3 >= 0 && index3 < 10);
    }

    /**
     * Test checks if the storeInteraction method throws an IllegalArgumentException
     * when given null values for the key or contra parameters, ensuring that the
     * method properly validates its inputs.
     */
    @Test
    public void testStoreInteractionthrowsException() {
        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    hashTable.storeInteraction(null, "Ibuprofen", true);
                });
        assertEquals("Your entries are invalid. Try again!", e.getMessage());

        e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    hashTable.storeInteraction("advil", null, true);
                });
        assertEquals("Your entries are invalid. Try again!", e.getMessage());

    }

    /**
     * Test checks if the storeInteraction method successfully stores interactions
     * and
     */
    @Test
    public void testStoreInteractionReturnsTrue() {
        assertTrue(hashTable.storeInteraction("Aspirin", "Ibuprofen", true));

        // Trying to store the same interaction again should return true as it is
        // already stored
        assertTrue(hashTable.storeInteraction("Aspirin", "Ibuprofen", true));

        // Now storing a different interaction should also return true
        assertTrue(hashTable.storeInteraction("Aspirin", "Paracetamol", true));

    }

    /**
     * Test checks if the storeInteraction method returns false when the hash table
     * is
     * full, ensuring that the method correctly handles cases where there is no
     * available space to store new interactions due to collisions and linear
     * probing.
     */
    @Test
    public void testStoreInteractionReturnsFalseWhenTableIsFull() {
        // Fill the hash table with interactions to create collisions
        for (int i = 0; i < 10; i++) {
            assertTrue(hashTable.storeInteraction("Drug" + i, "Contra" + i, true));
        }
        assertFalse(hashTable.storeInteraction("ExtraDrug", "ExtraContra", true)); // This should return false as
                                                                                   // the table is full
    }

    /**
     * Test checks if the find method works correctly with fewer prescriptions.
     */
    @Test
    public void testFindWithFewerPrescriptions() {
        hashTable.storeInteraction("Aspirin", "Ibuprofen", false);
        hashTable.storeInteraction("Ibuprofen", "Aspirin", false);

        assertTrue(hashTable.find("Aspirin"));
        assertTrue(hashTable.find("Ibuprofen"));

        // Searching for a drug that is not stored should return false
        assertFalse(hashTable.find("Amoxicillin"));
    }

    /** */
    @Test
    public void testFindWithMorePrescriptions() {
        HashTable largeTable = new HashTable(100); // Create a larger hash table to accommodate more interactions
        for (int i = 0; i < 100; i++) {
            largeTable.storeInteraction("Aspirin", "Contra" + i, true);

        }
        // track the number of collisions that occurred during the storage of
        // interactions
        int collisionsAtStorage = largeTable.getCollisionCountDuringHashing();
        assertTrue(collisionsAtStorage > 0);

        // We expect 4950 collisions since the first entry will not cause a collision
        assertEquals(4950, collisionsAtStorage); // We expect 495 collisions since the first

        assertTrue(largeTable.find("Aspirin"));
        // we expect to find the drug right away without any collision since it is the
        // first entry
        int collisionsAtSearch = largeTable.getCollisionBeforeFinding();
        assertEquals(0, collisionsAtSearch);

        // Searching for a drug that is not stored should return false
        assertFalse(largeTable.find("Contra0"));

        // The collision count should be greater than 0 due to the collisions created
        assertTrue(largeTable.getCollisionCountDuringHashing() > 0);

    }

    /**
     * Test checks if the find method correctly identifies a drug in an empty hash
     * table,
     * ensuring that the method returns false when there are no interactions stored.
     */
    @Test
    public void testFindWithNoPrescriptions() {
        // Searching for any drug in an empty table should return false
        assertFalse(hashTable.find("Aspirin"));
        assertFalse(hashTable.find("Ibuprofen"));
    }

    /**
     * Test checks if the find method correctly identifies a drug without any
     * contraindications, ensuring that the method can successfully retrieve stored
     * interactions even when there are no contraindications associated with the
     * drug.
     */
    @Test
    public void testFindWhenPatientHasNoContraindications() {
        hashTable.storeInteraction("Aspirin", "Ibuprofen", true);
        // Searching for a drug that is stored but has no contraindications should
        // return true
        assertFalse(hashTable.find("Advil"));
    }

    /**
     * Test checks if the getCollisionCountDuringHashing method accurately counts
     * the
     * number of collisions that occur when using the eight-byte hashing method.
     */
    @Test
    public void testGetCollisionCountWhileHashingWithEightBytes() {
        // Fill the hash table with interactions to create collisions
        for (int i = 0; i < 10; i++) {
            hashTable.storeInteraction("Aspirin", "Contra" + i, true);

        }
        int collisions = hashTable.getCollisionCountDuringHashing();
        // The collision count should be greater than 0 due to the collisions created
        assertTrue(hashTable.getCollisionCountDuringHashing() > 0);
        assertEquals(45, hashTable.getCollisionCountDuringHashing()); // We expect 9 collisions since the first entry
                                                                      // will not cause a
                                                                      // collision
    }

    /**
     * Test checks if the getCollisionCountDuringHashing method
     * accurately counts the number of collisions that occur
     */
    @Test
    public void testGetCollisionCountWhileHashingWithFourBytes() {
        // Fill the hash table with interactions to create collisions
        for (int i = 0; i < 10; i++) {
            hashTable.storeInteraction("Aspirin", "Contra" + i, false);

        }
        int collisions = hashTable.getCollisionCountDuringHashing();

        // The collision count should be greater than 0 due to the collisions created
        assertTrue(hashTable.getCollisionCountDuringHashing() > 0);
        assertEquals(45, hashTable.getCollisionCountDuringHashing()); // We expect 45 collisions since the first entry
                                                                      // will not cause
                                                                      // a
                                                                      // collision
    }

    /**
     * Test checks if the find method correctly identifies a drug without any
     * collisions
     * when the first entry is the one being searched for, even in a larger table
     * with
     * many interactions that cause collisions.
     */
    @Test
    public void testGetCollisionBeforeFinding() {
        HashTable largeTable = new HashTable(100); // Create a larger hash table to accommodate more interactions
        for (int i = 0; i < 100; i++) {
            largeTable.storeInteraction("Aspirin", "Contra" + i, true);

        }
        // track the number of collisions that occurred during the storage of
        // interactions
        int collisionsAtStorage = largeTable.getCollisionCountDuringHashing();
        assertTrue(collisionsAtStorage > 0);

        // We expect 4950 collisions since the first entry will not cause a collision
        assertEquals(4950, collisionsAtStorage); // We expect 495 collisions since the first

        assertTrue(largeTable.find("Aspirin"));
        // we expect to find the drug right away without any collision since it is the
        // first entry
        int collisionsAtSearch = largeTable.getCollisionBeforeFinding();
        assertEquals(0, collisionsAtSearch);
    }

    /**
     * Test checks if the find method correctly identifies a drug without any
     * collisions
     * when the first entry is the one being searched for, even in a larger table
     * with
     */
    @Test
    public void testGetCollisionBeforeFindingWithLargePrimeTable() {
        // same as above test but with a larger prime table to reduce collisions at
        // storage
        HashTable largeTable = new HashTable(1009); // Create a larger hash table with a prime number size to reduce
                                                    // collisions
        for (int i = 0; i < 100; i++) {
            largeTable.storeInteraction("Aspirin", "Contra" + i, true);

        }
        // track the number of collisions that occurred during the storage of
        // interactions
        int collisionsAtStorage = largeTable.getCollisionCountDuringHashing();
        assertTrue(collisionsAtStorage > 0);
        // We expect 4950 collisions since the first entry will not cause a collision
        assertEquals(4950, collisionsAtStorage); // We expect 4950 collisions same as above

        assertTrue(largeTable.find("Aspirin"));
        // we expect to find the drug right away without any collision since it is the
        // first entry
        int collisionsAtSearch = largeTable.getCollisionBeforeFinding();
        assertEquals(0, collisionsAtSearch);

    }
}
