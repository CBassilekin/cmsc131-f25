
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PatientsList {

    private Patient[] patientsList;
    private final int MAX_PATIENTS = 1000;
    private int indexOfIterator;
    private Patient nextPatient;
    private int visitedIndex = 0;
    private int firstAvailableIndex = 0;
    private Scanner scanner;

    /**
     * Building the constructot for the PatientsList Class
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
    public boolean add(Patient pat, Patient[] list) {

        // handle the cases when the patient is null or already exists in the list
        // this operation is not allowed and should return false
        if (pat == null || patientExists(pat.getIdentity(), list) || listIsFull(list)) {
            return false;
        }
        // handle the case when the patients list is full and cannot accommodate more
        // patients
        // this operation is not allowed and should throw false.

        // handle the case when the patient is added successfully to the list
        // this operation should return true
        // list[nextAvailIdx++] = pat;
        return add_ordered(pat, list);
    }

    /**
     * This method returns the length of the patients list
     * 
     * @return the length - how many spaces are available?
     */
    public int getSize(Patient[] list) {
        return list.length;
    }

    /**
     * the method checks if a patient with the given identity already exists in the
     * 
     * @param id the patient identity to check for existence in the list
     * @return true if the patient exists in the list, false otherwise
     */
    public boolean patientExists(PatientIdentity id, Patient[] list) {
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                if (list[i].getIdentity().match(id)) {
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
    public int getNumPatients(Patient[] list) {
        int numPatients = 0;

        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i] != null) {
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
    public Patient find(PatientIdentity id, Patient[] list) {
        return binarySearch(id, list);
    }

    /**
     * the method checks if the patients list is full and cannot accommodate more
     * patients, this operation is not allowed and should throw false.
     */
    private boolean listIsFull(Patient[] list) {
        // Returns true if the index has reached or exceeded capacity.
        // If MAX_PATIENTS is 10, indices are 0-9. Index 10 is full.
        return firstAvailableIndex >= MAX_PATIENTS;
    }

    /**
     * @param pat the patient to be added to the list in an ordered manner based on
     *            their identity
     * @return true if the patient is added successfully to the list, false
     *         otherwise
     */
    private boolean add_ordered(Patient pat, Patient[] list) {
        if (pat == null || patientExists(pat.getIdentity(), list)) {
            return false;
        }

        // add from the end of the list
        // if the array is completely empty, add the patient at the first index
        if (list[0] == null) {
            list[0] = pat;
            return true;
        }

        // else if the array is not empty, add the patient at the first available index
        // add from the end of the list

        findFirstAvailableIndex(list);

        while (firstAvailableIndex > 0
                && pat.getIdentity().isLessThan(list[firstAvailableIndex - 1].getIdentity())) {
            list[firstAvailableIndex] = list[firstAvailableIndex - 1];
            (firstAvailableIndex)--;
        }
        list[firstAvailableIndex] = pat;
        return true;

    }

    /**
     * This method helps find the first available index in the patients array.
     * 
     * @param pat - an array of patients
     * @return firstAvailableIndex - the first null index after the greatest object
     *         in the patients array
     */
    private int findFirstAvailableIndex(Patient[] list) {
        // Check if the list itself is null to avoid NullPointerException
        if (list == null) {
            return -1;
        }

        // find the first available index (first null element)

        for (int i = 0; i < list.length; i++) {
            if (list[i] == null) {
                // Found a spot, return the index immediately
                firstAvailableIndex = i;
                return firstAvailableIndex;
            }
        }

        // If we finished the loop without returning, no spots are available
        return -1;
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
    public Patient next(Patient[] list) {
        Patient visitedPatient = null;

        nextPatient = list[visitedIndex];
        indexOfIterator = visitedIndex;

        if (nextPatient != null && indexOfIterator < list.length) {
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
    private Patient binarySearch(PatientIdentity id, Patient[] list) {
        if (id != null) {
            int left = 0;
            int right = 0;
            if (findFirstAvailableIndex(list) == -1) {
                right = list.length - 1;
            } else {
                right = findFirstAvailableIndex(list) - 1;
            }

            while (left <= right) {
                int mid = (left + right) / 2;
                if (list[mid] != null) {

                    if (list[mid].getIdentity().match(id)) {
                        return list[mid]; // Patient found

                    } else if (list[mid].getIdentity().isLessThan(id)) {
                        left = mid + 1; // Search in the right half
                    } else {
                        right = mid - 1; // Search in the left half
                    }

                }
            }
        }
        return null; // Patient not found
    }

    public boolean directAdd(Patient pat, Patient[] list) {

        if (pat == null) {
            return false;
        } else {

            for (int i = 0; i < list.length; i++) {
                if (list[i] == null) {
                    list[i] = pat;
                    break;

                }
            }
        }
        return true; // Always returns true when the operation is successful
    }

    public static boolean mergeSort(Patient[] list) {

        int i = 0;

        // an empty list should return false as there will be no data to mergeSort
        if (list == null) {
            return false;
        }

        // a 1-item list is automatically sorted and should return true
        if (list.length <= 1) {
            return true;
        }

        else {
            // finding the middle index of the array
            int mid = list.length / 2;
            int upper = (list.length - mid); // remaining spaces after mid

            // let us split the list into halves
            Patient[] lowerList = new Patient[mid];
            Patient[] upperList = new Patient[upper];

            // addind items from the unordered list into each splited list
            for (i = 0; i < mid; i++) {
                lowerList[i] = list[i];
            }
            for (i = mid; i < list.length; i++) {
                upperList[i - mid] = list[i];
            }

            // recursively sort both halves
            mergeSort(lowerList);
            mergeSort(upperList);

            // reset counters here
            i = 0;
            int j = 0;
            int k = 0;

            // Main comparison loop
            while (i < lowerList.length && j < upperList.length) {

                if (lowerList[i].getIdentity().isLessThan(upperList[j].getIdentity())) {
                    list[k++] = lowerList[i++];

                } else {
                    list[k++] = upperList[j++];
                }
            }

            // Copy remaining elements from lowerList (if any)
            while (i < lowerList.length) {
                list[k++] = lowerList[i++];
            }

            // Copy remaining elements from upperList (if any)
            while (j < upperList.length) {
                list[k++] = upperList[j++];
            }

        }

        return true;
    }

    /**
     * this method read data from a CSV file with pre-populated patients information
     * each line of the file is in this format " last name, first name, date of
     * birth, uuid"
     * 
     * @return
     */
    public boolean importFromFile(String filepath, Patient[] list) {
        File patientsFile = new File(filepath);
        Patient newPatientFromFile = null;
        try {
            scanner = new Scanner(patientsFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != null) {
                    newPatientFromFile = Patient.makePatient(line);
                    directAdd(newPatientFromFile, list);
                } else
                    throw new IllegalArgumentException(" line must not be null");
            }
            scanner.close();
            mergeSort(list);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean saveToFile(String fileName, Patient[] list) {
        if (fileName == null) {
            return false;
        }

        FileWriter writer = null;
        try {

            writer = new FileWriter(new File(fileName));
            while (indexOfIterator < list.length - 1) {
                String line = next(list).toCSV();
                if (line != null) {
                    writer.write(line + "\n");
                }

            }
            writer.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean readPrescriptions(String filepath, PrescriptionList list, Patient[] paList) {

        File prescriptionsFile = new File(filepath);
        Prescription pr = null;
        try {
            scanner = new Scanner(prescriptionsFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != null) {
                    // creating a new prescription
                    pr = Prescription.makePrescription(line, paList);
                    // matching a prescription to a patient, then add it to his/her prescription
                    // list
                    Patient pat = binarySearch(pr.getPatientID(pr), paList);
                    if (pat != null) {
                        list.add(pr);
                    }
                }
            }
            scanner.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
