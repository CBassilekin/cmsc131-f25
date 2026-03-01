package projects.patients;

public class PatientsList {

    private Patient[] patientsList;
    private final int MAX_PATIENTS = 10000;
    private int numPatients;
    private int indexOfIterator;
    private Patient nextPatient;
    private int firstAvailableIndex = 0;
    private int visitedIndex = 0;

    /**
     * Building the constructot for the PAtientsList Class
     * PatientList is an array of 10000 patients
     */

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
        if (pat == null || patientExists(pat.getIdentity()) || listIsFull()) {
            return false;
        }
        // handle the case when the patients list is full and cannot accommodate more
        // patients
        // this operation is not allowed and should throw false.

        // handle the case when the patient is added successfully to the list
        // this operation should return true
        // patientsList[nextAvailIdx++] = pat;
        return add_ordered(pat);
    }

    /**
     * This method returns the length of the patients list
     * 
     * @return the length - how many spaces are available?
     */
    public int getSize() {
        return patientsList.length;
    }

    /**
     * the method checks if a patient with the given identity already exists in the
     * 
     * @param id the patient identity to check for existence in the list
     * @return true if the patient exists in the list, false otherwise
     */
    public boolean patientExists(PatientIdentity id) {
        for (int i = 0; i < patientsList.length; i++) {
            if (patientsList[i] != null) {
                if (patientsList[i].getIdentity().match(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * the method returns the number of patients in the list
     * 
     * @return the number of patients in the list
     */
    public int getNumPatients() {
        numPatients = 0;
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
        return binarySearch(id);
    }

    /**
     * the method checks if the patients list is full and cannot accommodate more
     * patients, this operation is not allowed and should throw false.
     */
    private boolean listIsFull() {
        for (int i = 0; i < patientsList.length; i++) {
            if (patientsList[i] == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param pat the patient to be added to the list in an ordered manner based on
     *            their identity
     * @return true if the patient is added successfully to the list, false
     *         otherwise
     */
    private boolean add_ordered(Patient pat) {
        if (pat == null || patientExists(pat.getIdentity())) {
            return false;
        }

        // add from the end of the list
        // if the array is completely empty, add the patient at the first index
        if (patientsList[0] == null) {
            patientsList[0] = pat;
            return true;
        }

        // else if the array is not empty, add the patient at the first available index
        // add from the end of the list

        findFirstAvailableIndex(patientsList);

        while (firstAvailableIndex > 0
                && pat.getIdentity().isLessThan(patientsList[firstAvailableIndex - 1].getIdentity())) {
            patientsList[firstAvailableIndex] = patientsList[firstAvailableIndex - 1];
            firstAvailableIndex--;
        }
        patientsList[firstAvailableIndex] = pat;
        return true;

    }

    /**
     * This method helps find the first available index in the patients array.
     * 
     * @param pat - an array of patients
     * @return firstAvailableIndex - the first null index after the greatest object
     *         in the patients array
     */
    private int findFirstAvailableIndex(Patient[] pat) {

        // find the first available index
        for (int i = 0; i < patientsList.length; i++) {
            if (patientsList[i] == null) {
                firstAvailableIndex = i;
                i++;
                break;

            }
        }
        return firstAvailableIndex;
    }

    /**
     * This method helps set the iterator to start
     */

    private void setIteratorToStart() {
        indexOfIterator = -1;
    }

    /**
     * This method initializes the iterator to start
     */

    public void initIterator() {
        setIteratorToStart();
    }

    /**
     * This method provides the current index of the iteration
     * 
     * @return indexOfIterator - index where the iteration is at
     */
    public int indexOfIteration() {
        return indexOfIterator;
    }

    /**
     * 
     * This method allows to iterate over the patients list
     * looking at each item in order of placement and identifying the patient
     * being looked at a given moment
     * 
     * @return Patient - the current patient in the list
     */
    public Patient next() {
        Patient visitedPatient = null;

        nextPatient = patientsList[visitedIndex];
        indexOfIterator = visitedIndex;

        if (nextPatient != null) {
            visitedPatient = nextPatient;
            visitedIndex++;

        } else {
            initIterator();
            return null;
        }
        return visitedPatient;
    }

    /**
     * This methods searches over the list quickly
     * and helps find the patient with a given identity
     * using the upper end and lower end of the list
     * 
     * @param id - a given patient identity
     * @return - the patient matching that identity or null if such patient does not
     *         exist
     */
    private Patient binarySearch(PatientIdentity id) {
        if (id != null) {
            int left = 0;
            int right = findFirstAvailableIndex(patientsList) - 1;

            while (left <= right) {
                int mid = (left + right) / 2;
                if (patientsList[mid] != null) {

                    if (patientsList[mid].getIdentity().equals(id)) {
                        return patientsList[mid]; // Patient found
                    } else if (patientsList[mid].getIdentity().isLessThan(id)) {
                        left = mid + 1; // Search in the right half
                    } else {
                        right = mid - 1; // Search in the left half
                    }

                }
            }
        }
        return null; // Patient not found
    }

}
