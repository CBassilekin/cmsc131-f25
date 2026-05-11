public class HashTable {

    private Prescription[] hashTable;
    private int SIZE = 0;

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
    public int hashing(String key) {

        int hashcode = 0;
        int index = 0;

        // Get the 4 characters references then their 8 lower bytes digits
        // The first reference is the first character of the string
        // The second reference is at 1/4 of the string
        // The third reference is at 1/2 of the string
        // The fourth reference is the last one

        byte byFirst = (byte) key.charAt(0);
        byte byFourth = (byte) key.charAt(key.length() / 4);
        byte byMiddle = (byte) key.charAt(key.length() / 2);
        byte byLast = (byte) key.charAt(key.length() - 1);

        // Clearing the top bit to be positive
        int clearedPositive = byLast & 0x7F;

        // packing all four bits into the long integer to create the hashcode
        hashcode = (clearedPositive & 0xFF) << 24 | (byMiddle & 0xFF) << 16 | (byFourth & 0xFF) << 8
                | (byFirst & 0xFF);

        // using the modular function to find the index in the hash table

        index = Math.abs(hashcode) % hashTable.length;

        return index;

    }

    public boolean storeInteraction(String key, String contra) {

        if (key == null || contra == null) {
            return false;
        }
        Prescription current = new Prescription(key, null, 0, contra);

        // Compute the index position for this interaction
        int index = hashing(key);
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

            // if we are done going down and up the table, we may have exhausted our
            // possibilities
            if (index == startingIndex) {
                return false;
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
        int indexToMatch = hashing(newPrescription);
        int startingIndex = indexToMatch;

        while (hashTable[indexToMatch] != null) {
            // this key may exist in the hashTable, let's find it
            if (hashTable[indexToMatch].getName().equalsIgnoreCase(newPrescription)) {
                return true;// we found it!
            }
            // let's look down the table
            indexToMatch = (indexToMatch + 1) % hashTable.length;
            // getting to the end of the table and starting back from 0.

            if (indexToMatch == startingIndex) // we are back at the start
                break;
        }
        return false;
    }

}
