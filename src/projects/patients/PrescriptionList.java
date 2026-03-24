
public class PrescriptionList {

    private ListRecord head;
    private int iterator;
    ListRecord nextRecord = null;

    public PrescriptionList() {
        head = null;
    }

    private class ListRecord {
        public Prescription data;
        public ListRecord next;

        public ListRecord(Prescription pr) {
            data = pr;
            next = null;
        }
    }

    // Adds a new prescription to the end of the list
    public boolean add(Prescription pr) {
        ListRecord pNew = new ListRecord(pr); // new record to be added to the list

        // 1. List is empty
        if (head == null) {
            head = pNew;

        }
        // 2. PNew should be the new head
        else if (comesAfter(pNew.data, head.data)) {
            pNew.next = head;
            head = pNew;
        }
        // 3. We traverse to find the insertion point
        else {
            // identifying pBefore and pAfter
            ListRecord pBefore = head;
            ListRecord pAfter = head.next;
            while ((pAfter != null) && (!comesAfter(pNew.data, pAfter.data))) {
                pBefore = pAfter;
                pAfter = pAfter.next;
            }
            // insert the new record to the list
            // Now pNew correctly sits BETWEEN pBefore and pAfter
            pNew.next = pAfter;
            pBefore.next = pNew;
        }

        return true;

    }

    public int iteratorIndex() {
        // we will return the current index of the record being iterated on.
        return iterator;
    }

    public void init() {
        // we reset the iteration to the beginning of the list
        iterator = 0;
        nextRecord = head;

    }

    /**
     * this method iterates over the list of records,
     * and return the prescriotion of a visited recorded
     * 
     * @return currentPrescription - the exact prescription at a soecific location
     *         in the list
     */
    public Prescription next() {
        ListRecord visitedRecord = null;
        Prescription currentPrescription = null;

        while (nextRecord != null) {
            visitedRecord = nextRecord;
            nextRecord = nextRecord.next;
            iterator++;
            currentPrescription = visitedRecord.data;
            return currentPrescription;
        } // when iteration ends, the iteration reset and returns null;
        init();
        return null;

    }

    public PrescriptionList findPatientList(Patient pat, Patient[] listArray) {
        ListRecord assignRecord = head;
        /*
         * Name Name = (assignRecord.data.getPatientFromPrescription().getName());
         * 
         * 
         * Name patName = pat.getIdentity().getName();
         */PrescriptionList assigned = new PrescriptionList();
        // traverse through all the record, we could also use the iterator here
        while (assignRecord != null) {

            if (assignRecord.data.getPatientID(assignRecord.data).match(pat.getIdentity())) {

                assigned.add(assignRecord.data);
            }

            assignRecord = assignRecord.next;

        }
        return assigned;
    }

    public static boolean comesAfter(Prescription newRecord, Prescription current) {
        // where records have distincts prescription date
        if (!newRecord.getDate().equals(current.getDate())) {
            return (newRecord.getDate().after(current.getDate()));

            // if records have the same prescription date
        } else {
            // let's organize them by medication name.
            int comp = newRecord.getName().compareTo(current.getName());
            return (comp < 0);
        }

    }

    /**
     * this method counts the prescriptions in a given list.
     * 
     * @return localCounter - the total number of prescriptions in a list.
     */
    public int getCount() {
        int localCounter = 0;
        while (nextRecord != null) {
            localCounter++;
            nextRecord = nextRecord.next;

        } // when iteration ends, the iteration reset and returns null;
        init();
        return localCounter;
    }

}
