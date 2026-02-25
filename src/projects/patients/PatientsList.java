package projects.hospital;

public class PatientsList {

    private Patients[] patientsList;
    private final int MAX_PATIENTS = 1000;
    private int nextAvailIdx = 0;
    private int numPatients = 0;

    public PatientsList() {

        patientsList = new Patient[MAX_PATIENTS];
    }

    /**
     * the method adds a new patient to the list
     * the array is supposed empty at first.
     * the method checks if the patient is null or already exists in the list, if so
     * it returns false.
     * if the patient is added successfully to the list, it returns true.
     * 
     */
    public boolean add(Patient pat) {

        // handle the cases when the patient is null or already exists in the list
        // this operation is not allowed and should return false
        if (pat == null || patientExists(pat.getId())) {
            return false;
        }

        // a corner case exists when the list is full and needs to be resized
        if (nextAvailIdx > patientsList.length) {
            resizeArray();
        }

        // handle the case when the patient is added successfully to the list
        // this operation should return true
        // patientsList[nextAvailIdx++] = pat;
        return add_ordered(pat);
    }

    /**
     * the method checks if a patient with the given identity already exists in the
     * 
     * @param id the patient identity to check for existence in the list
     * @return true if the patient exists in the list, false otherwise
     */
    public boolean patientExists(PatientIdentity id) {
        for (int i = 0; i < patientsList.length; i++) {
            if (patientsList != null && patientsList[i].getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * the method resizes the array of patients when it is full,
     * it creates a new array with a larger size and copies the existing patients
     * to the new array, then it replaces the old array with the new one.
     * 
     */
    public void resizeArray() {
        // we will add 200 more slots to the array when it is full, this is an arbitrary
        // number
        int newSize = patientsList.length + 200;
        Patient[] newArray = new Patient[newSize];
        for (int i = 0; i < patientsList.length; i++) {
            newArray[i] = patientsList[i];
        }
        patientsList = newArray;
    }

    /**
     * the method returns the number of patients in the list
     * 
     * @return the number of patients in the list
     */
    public int getNumPatients() {
        if (patientsList != null) {
            for (int i = 0; i < patientsList.length; i++) {
                if (patientsList[i] != null) {
                    numPatients++;
                }
            }
        }
        return numPatients;
    }

    /**
     * the method finds a patient in the list based on the given patient identity
     * 
     * @param id the patient identity to search for in the list
     * @return the patient object if found, null otherwise
     * 
     */
    public Patient find(PatientIdentity id) {
        for (int i = 0; i < patientsList.length; i++) {
            if (patientsList != null && patientsList[i].getId().equals(id)) {
                return patientsList[i];
            }
        }
        return null;
    }

    public boolean add_ordered(Patient pat) {
        if (pat == null || patientExists(pat.getId())) {
            return false;
        }

        if (nextAvailIdx > patientsList.length) {
            resizeArray();
        }

        int i = 0;
        while (i < nextAvailIdx && patientsList[i].getId().isLessThan(pat.getId())) {
            i++;
        }

        for (int j = nextAvailIdx; j > i; j--) {
            patientsList[j] = patientsList[j - 1];
        }
        patientsList[i] = pat;
        nextAvailIdx++;
        return true;
    }
}