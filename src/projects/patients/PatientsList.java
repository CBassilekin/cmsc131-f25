
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PatientsList {

    private int indexOfIterator;
    private Scanner scanner;
    private BinarySearchTree patientsBST;

    /**
     * +
     * Building the constructor for the PatientsList Class
     */

    public PatientsList() {

        patientsBST = new BinarySearchTree();

    }

    /**
     * the method adds a new patient to the list
     * the array is supposed empty at first.
     * the method checks if the patient is null or already exists in the list, if so
     * it returns false.
     * if the patient is added successfully to the list, it returns true.
     * 
     */
    public void add(Patient pat) {

        /**
         * if (pat == null || patientExists(pat.getIdentity())) {
         * return;
         * }
         */
        patientsBST.add(pat);

    }

    public int getNumPatients() {
        return patientsBST.getSize();
    }

    /**
     * the method checks if a patient with the given identity already exists in the
     * 
     * @param id the patient identity to check for existence in the list
     * @return true if the patient exists in the list, false otherwise
     */
    public boolean patientExists(PatientIdentity id) {
        if (id != null) {
            return patientsBST.find(id) != null;
        }
        return false;
    }

    /**
     * the method finds a patient in the list based on the given patient identity
     * 
     * @param id the patient identity to search for in the list
     * @return the patient object if found, null otherwise
     * 
     */
    public Patient find(PatientIdentity id) {
        return (Patient) patientsBST.find(id);
    }

    /**
     * This method helps set the iterator to start
     */
    private void setIteratorToStart() {
        indexOfIterator = 0;
    }

    /**
     * This method initializes the iterator to start
     */

    public void initIterator() {
        patientsBST.init();
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
        IdentifiedObject data = patientsBST.nextData();

        if (data != null) {
            indexOfIterator++;
            return (Patient) data;
        }
        return null;
    }

    /**
     * this method read data from a CSV file with pre-populated patients information
     * each line of the file is in this format " last name, first name, date of
     * birth, uuid"
     * 
     * @return true if the data is read successfully and the patients are added to
     *         the list,
     *         false otherwise
     */
    public boolean importFromFile(String filepath) {
        File patientsFile = new File(filepath);
        // This syntax automatically closes the scanner even if it crashes
        try (Scanner scanner = new Scanner(patientsFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) {
                    add(Patient.makePatient(line));
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * this method saves the patients list to a CSV file, where each line of the
     * file is in the format " last name, first name, date of birth, uuid"
     * 
     * @param fileName the name of the file to save the patients list to
     * @param list     the array of patients to be saved to the file
     * @return true if the patients list is saved successfully to the file, false
     *         otherwise
     */
    public boolean saveToFile(String fileName) {
        if (fileName == null) {
            return false;
        }
        FileWriter writer = null;
        try {

            writer = new FileWriter(new File(fileName));

            int numPatients = 0;
            patientsBST.init();
            while (patientsBST.hasNext()) {
                Patient patient = (Patient) patientsBST.nextData();

                String line = patient.toCSV();
                if (line != null) {
                    writer.write(line + "\n");
                    numPatients++;
                }

            }
            writer.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
