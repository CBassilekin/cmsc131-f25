public class Contraindications {

    private String medName;
    private Patient pat;
    private HashTable contraTable = new HashTable(1009);
    private int count = 0;// number of interactions without the symmetrical pairs.

    private PrescriptionList allContra;
    private PrescriptionList myPrescriptions;

    /**
     * Defining the contraindications object using
     * the medecine name which is to be prescribed
     * and the list of its related contraindications
     * to better informed the prescriber and avoid an adverse interaction.
     * 
     * @param medecineString    - name of the prescription
     * @param contraindications - list of all nominated contraindications
     */
    public Contraindications(Patient pat) {

        if (pat != null) {
            this.pat = pat;

        } else {
            throw new IllegalArgumentException("The Patient's information is required.");
        }
    }

    /**
     * This method populates the hashtable symmetrically
     * using a patient's exisiting prescriptions
     * 
     * @param pr     - list of patient current prescriptions
     * @param paList - patients list is needed to read the prescriptions
     *               file
     * @param contra - the contraindications file is needed to read the
     *               interactions data and populate the hash table
     * @return true if at least one contraindication is found and loaded, false
     *         otherwise.
     */
    public boolean loadHashTable(PatientsList paList, String contra, String pr) {
        int count = 0;// count the interactions
        String lastMedAdded = null;
        boolean foundAtLeastOne = false; // this stay true unless
        // a match is found in the contraindications list

        if (paList == null || contra == null || pr == null) {
            throw new IllegalArgumentException("The database value does not exist.");
        }
        // this will load all the interactions as prescriptions
        // in a list to be iterated on later for matching with the patient's
        // prescriptions.

        PrescriptionList allContra = new PrescriptionList();
        boolean success = allContra.readPrescriptions(contra, paList);

        // Match the patients current prescriptions with the running
        // list of prescriptions to retrieve the valid ones to add to the hash table.
        PrescriptionList all = new PrescriptionList();
        success = all.readPrescriptions(pr, paList);

        PrescriptionList myPrescriptions = new PrescriptionList();
        myPrescriptions = myPrescriptions.findPatientList(pat, all);
        myPrescriptions.init();
        Prescription current;

        while ((current = myPrescriptions.next()) != null) {

            // start iterating on the global interactions list
            allContra.init();
            Prescription interaction;

            while ((interaction = allContra.next()) != null) {

                // let's map the data from a prescription' arguments
                String medA = interaction.getName();
                String medB = interaction.getPrescriber();
                if (current.getName().equalsIgnoreCase(medA)) {
                    contraTable.storeInteraction(medA, medB);
                    // storing its symmetrical interaction at once
                    contraTable.storeInteraction(medB, medA);

                    lastMedAdded = medA;
                    foundAtLeastOne = true;
                    count += 2; // Ignore Symmetrical pairs adjustment.

                } else if (current.getName().equalsIgnoreCase(medB)) {
                    contraTable.storeInteraction(medB, medA);
                    // storing its symmetrical interaction at once
                    contraTable.storeInteraction(medA, medB);

                    lastMedAdded = medB;
                    foundAtLeastOne = true;
                    count += 2;// Ignore Symmetrical pairs adjustment.

                }
            }
        }
        medName = lastMedAdded;
        this.count = count; // Ignore Symmetrical pairs adjustment.
        return foundAtLeastOne;

    }

    public boolean isContraIndicated(Prescription pr) {
        return contraTable.find(pr.getName());

    }

    /**
     * this methods return the medication name being entered has
     * one having contraindications with others
     */
    public String getMedName() {
        if (medName == null) {
            throw new IllegalStateException("No medication name has been added to the contraindications list.");
        }
        return medName.toLowerCase();
    }

    public HashTable getPatientAllInteractions() {
        return contraTable;
    }

    public int getCount() {
        return count;

    }

}
