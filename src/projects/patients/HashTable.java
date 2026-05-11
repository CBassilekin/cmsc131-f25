public class HashTable {

    private Prescription[] hashTable;
    private int SIZE = 0;
    private int collisionCount = 0;
    private int collisionBeforeFinding = 0;

    public HashTable(int size) {

        if (size != 0) {
            this.SIZE = size;
            hashTable = new Prescription[SIZE];

        }
    }

    /**
     * This method create a hashcode from a string
     * 
     * @param keys - medications that should not be prescribed together
     * @return the index where that combination " key" can be found
     *         in our hash table
     */
    public int hashing(String key, boolean useFullEightBytes) {

        if (key == null) {
            return -1; // invalid input
        }

        int index = 0; // this will be the index in the hash table where we will store the interaction
        int len = key.length(); // the length of the string key, we will use it to determine the positions of
                                // the characters we will use to create the hashcode

        // Define 8 positions with the flexibility to use only 4 for testing purposes
        // The first reference is the first character of the string
        // The second reference is at 1/8 of the string
        // The third reference is at 1/4 of the string
        // The fourth reference is at 3/8 of the string
        // The fifth reference is at 1/2 of the string
        // The sixth reference is at 5/8 of the string
        // The seventh reference is at 3/4 of the string
        // The eighth reference is the last character of the string

        int hashcode = 0;

        if (useFullEightBytes) {
            // OPTION: 8 Characters (spread across the whole string)
            for (int i = 0; i < 8; i++) {
                int pos = (i * (len - 1)) / 7; // Evenly spaces 8 points from start to end
                long b = (long) key.charAt(pos) & 0xFF;
                hashcode |= (b << (8 * (7 - i)));
            }
        } else {
            // OPTION: 4 Characters (The "Classic" 4-point check)
            int[] positions = { 0, len / 4, len / 2, len - 1 };
            for (int i = 0; i < 4; i++) {
                long b = (long) key.charAt(positions[i]) & 0xFF;
                // We shift them into the 4 most significant byte slots
                // to keep the hash "heavy"
                hashcode |= (b << (24 - (i * 8)));
            }
        }

        // using the modular function to find the index in the hash table

        index = (int) Math.abs(hashcode) % hashTable.length;
        return index;

    }

    public boolean storeInteraction(String key, String contra, boolean useFullEightBytes) {

        if (key == null || contra == null) {
            throw new IllegalArgumentException("Your entries are invalid. Try again!");
        }
        Prescription current = new Prescription(key, null, 0, contra);

        // Compute the index position for this interaction
        int index = hashing(key, false);
        int startingIndex = index;

        // let's handle the case of a collision using linear probing

        while (hashTable[index] != null) {

            // let's handle the duplicate case where interaction already exists in the table
            if (hashTable[index].getName().equalsIgnoreCase(key)
                    && hashTable[index].getPrescriber().equalsIgnoreCase(contra))
                return true; // already stored , no need to repeat operation

            // let's handle the case of a collision using linear probing
            // we may store the interaction below similar ones
            index = (index + 1) % hashTable.length;
            collisionCount++;

            // if we are done going down and up the table, we may have exhausted our
            // possibilities
            if (index == startingIndex) {
                return false; // table full, we can't store this interaction
            }

        }

        // we got a suitable null spot
        hashTable[index] = current;
        return true;

    }

    /**
     * This method would find a particular contraindication using
     * an index
     * In this sceanrio, a new prescription (key) needs be prescribed to
     * a patient
     * we will lookup the contradiction table to find out if it is listed there as
     * a contradiction for a prescription he already take in this way
     * Precription (contra, null, 0, oldPrescription)
     */

    public boolean find(String newPrescription) {
        if (newPrescription == null) {
            return false;
        }

        // let's check the corresponding index
        int indexToMatch = hashing(newPrescription, false);
        int startingIndex = indexToMatch;

        while (hashTable[indexToMatch] != null) {
            // this key may exist in the hashTable, let's find it
            if (hashTable[indexToMatch].getName().equalsIgnoreCase(newPrescription)) {
                return true;// we found it!
            }
            // let's look down the table
            indexToMatch = (indexToMatch + 1) % hashTable.length;
            collisionBeforeFinding++;

            // getting to the end of the table and starting back from 0.
            if (indexToMatch == startingIndex) // we are back at the start
                break;
        }
        return false;
    }

    public int getCollisionCountDuringHashing() {
        return collisionCount;
    }

    public int getCollisionBeforeFinding() {
        return collisionBeforeFinding;

    }
}
