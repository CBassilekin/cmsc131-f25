import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

public class PatientsListTest {

    private PatientsList patientsList;
    private Patient patientTest1;
    private Patient patientTest2;
    private Patient patientTest3;

    private Calendar cal = Calendar.getInstance();
    private BinarySearchTree testList;

    /**
     * Testing will use all these values as defaukt
     * before each test
     */

    @Before
    public void setUp() {

        testList = new BinarySearchTree();
        patientsList = new PatientsList();

        cal.set(1990, Calendar.JANUARY, 01, 0, 0, 0); // Year, Month, Day
        cal.set(Calendar.MILLISECOND, 0);
        patientTest1 = new Patient(new PatientIdentity(
                new Name("John", "Doe"), cal.getTime()));
        cal.set(1970, Calendar.MARCH, 01, 0, 0, 0); // Year, Month, Day
        cal.set(Calendar.MILLISECOND, 0);
        patientTest2 = new Patient(new PatientIdentity(
                new Name("Jane", "Smith"), cal.getTime()));
        cal.set(2000, Calendar.MAY, 05, 0, 0, 0); // Year, Month, Day
        cal.set(Calendar.MILLISECOND, 0);
        patientTest3 = new Patient(new PatientIdentity(
                new Name("Alice", "Johnson"), cal.getTime()));

    }

    /**
     * Test confirms that the constuctor builds correctly
     * the tree should be empty at first and the list should be initialized
     * should not be null
     */
    @Test
    public void testValidConstructor() {
        assertEquals(0, patientsList.getNumPatients());
        assertNotNull(patientsList);
    }

    @Test
    public void testConstructorWithExistingList() {
        // creating a new list with an existing list of patients

        patientsList.add(patientTest1);
        patientsList.add(patientTest2);

        // checking that the new list has the same patients as the existing list
        assertTrue(patientsList.patientExists(patientTest1.getIdentity()));
        assertTrue(patientsList.patientExists(patientTest2.getIdentity()));
        assertEquals(2, patientsList.getNumPatients());
    }

    /**
     * Test confirms that valid patient are added
     * and check that the size of the list grows accordingly
     */
    @Test
    public void testAdd() {
        patientsList.add(patientTest1);
        assertTrue(patientsList.patientExists(patientTest1.getIdentity()));
        assertEquals(1, patientsList.getNumPatients());

        patientsList.add(patientTest2);
        assertTrue(patientsList.patientExists(patientTest2.getIdentity()));
        assertEquals(2, patientsList.getNumPatients());

        patientsList.add(patientTest3);
        assertTrue(patientsList.patientExists(patientTest3.getIdentity()));
        assertEquals(3, patientsList.getNumPatients());
    }

    /**
     * Test confirms that a null patient cannot be successfully added to the list
     */
    @Test
    public void testAddNullPatient() {
        patientsList.add(null);
        assertEquals(0, patientsList.getNumPatients());
    }

    /**
     * Test confirms that list cannot accept adding a
     * duplicate patient
     */

    @Test
    public void testAddDuplicatePatient() {
        patientsList.add(patientTest1);
        assertTrue(patientsList.patientExists(patientTest1.getIdentity()));
        assertEquals(1, patientsList.getNumPatients());

        // trying to add the same patient again
        patientsList.add(patientTest1);
        assertEquals(1, patientsList.getNumPatients());
    }

    /**
     * Test checks the total number of existing patients in the list.
     */
    @Test
    public void testGetNumPatients() {
        // initially the list should be empty
        assertEquals(0, patientsList.getNumPatients());

        // adding patients and checking the count
        patientsList.add(patientTest1);
        assertEquals(1, patientsList.getNumPatients());

        patientsList.add(patientTest2);
        assertEquals(2, patientsList.getNumPatients());

        patientsList.add(patientTest3);
        assertEquals(3, patientsList.getNumPatients());
    }

    /**
     * Test checks that a patient already exists in the database
     */
    @Test
    public void testPatientExistsReturnsTrue() {
        patientsList.add(patientTest1);
        patientsList.add(patientTest2);

        // checking if existing patient is found
        boolean actualResult = patientsList.patientExists(patientTest1.getIdentity());
        boolean expectedResult = true;
        assertEquals(expectedResult, actualResult);

        // checking if another existing patient is found
        actualResult = patientsList.patientExists(patientTest2.getIdentity());
        expectedResult = true;
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Test checks that a patient does not already exist
     * and can be added to the database.
     */
    @Test
    public void testPatientExistsReturnsFalse() {
        // checking if a non-existing patient is not found
        cal.set(1995, Calendar.MARCH, 20);
        PatientIdentity nonExistingId = new PatientIdentity(
                new Name("Non", "Existing"), cal.getTime());
        boolean actualResult = patientsList.patientExists(nonExistingId);
        boolean expectedResult = false;
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Test patientExists() with a null patient identity should return false
     */
    @Test
    public void testPatientExistsWithNullIdentity() {
        boolean actualResult = patientsList.patientExists(null);
        boolean expectedResult = false;
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Test checks that finding a null patient
     * returns a null patient
     */
    @Test
    public void testFindReturnsNull() {
        // trying to find a patient with a null identity
        Patient actualResult = patientsList.find(null);
        assertNull(actualResult);

        // trying to find a non-existing patient
        cal.set(1995, Calendar.MARCH, 20);
        PatientIdentity nonExistingId = new PatientIdentity(
                new Name("Non", "Existing"), cal.getTime());
        actualResult = patientsList.find(nonExistingId);
        assertNull(actualResult);

    }

    /**
     * Test confirms that finding an existing patient
     * can be easily returned using the find() method
     */
    @Test
    public void testFindPatientById() {
        patientsList.add(patientTest1);
        patientsList.add(patientTest2);

        // trying to find an existing patient
        Patient actualResult = patientsList.find(patientTest1.getIdentity());
        Patient expectedResult = patientTest1;
        assertEquals(expectedResult, actualResult);

        // trying to find another existing patient
        actualResult = patientsList.find(patientTest2.getIdentity());
        expectedResult = patientTest2;
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Test confirms that the iterator is correctly initialized at -1.
     */
    @Test
    public void testInitIterator() {

        // initializing the iterator
        patientsList.initIterator();

        // checking that the index of iteration is set to -1
        int expectedIndex = 0;
        int actualIndex = patientsList.indexOfIteration();
        assertEquals(expectedIndex, actualIndex);

    }

    /**
     * Test confirms that the index of Iteration is reported correctly each time
     */
    @Test
    public void testIndexOfIteration() {
        // adding patients to the list
        patientsList.add(patientTest1);
        patientsList.add(patientTest2);
        patientsList.add(patientTest3);

        // initializing the iterator
        patientsList.initIterator();

        // checking the index of iteration after each call to next()
        assertEquals(0, patientsList.indexOfIteration());
        patientsList.next();
        assertEquals(1, patientsList.indexOfIteration());
        patientsList.next();
        assertEquals(2, patientsList.indexOfIteration());
        patientsList.next();
        assertEquals(3, patientsList.indexOfIteration());

    }

    /**
     * Test confirms that an empty patients list will retuen nulll
     * when next() is called on it
     */
    @Test
    public void testNextAnEmptyArray() {
        // the list is empty
        patientsList.next();
        assertNull(patientsList.next());
        assertEquals(0, patientsList.indexOfIteration());

    }

    /**
     * For a Null patient in the list, test confirms that calling next()
     * will return a null patient
     * 
     */
    @Test
    public void testNextWhilePatientIsNull() {
        patientsList.add(null);
        patientsList.initIterator();
        assertNull(patientsList.next());
        assertEquals(0, patientsList.indexOfIteration());

    }

    /**
     * Test confirms that next() returns the correct patient
     * from the a non-empty database
     */
    @Test
    public void testNextAfterRepeatedCalls() {

        // adding patients to the list
        patientsList.add(patientTest1);
        patientsList.add(patientTest2);
        patientsList.add(patientTest3);

        // initializing the iterator
        patientsList.initIterator();

        // calling next() repeatedly and checking the returned patient each time
        Patient actualResult = patientsList.next();
        Patient expectedResult = patientTest1;
        assertEquals(expectedResult, actualResult);

        actualResult = patientsList.next();
        expectedResult = patientTest3;
        assertEquals(expectedResult, actualResult);

        actualResult = patientsList.next();
        expectedResult = patientTest2;
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Test confirms that importing from an inexistent file will fail and return
     * false
     * and the number of patients in the list should remain 0
     */
    @Test
    public void testImportFromAnInexistentFile() {

        // if the file is inexistent, method should return false
        assertFalse(patientsList.importFromFile("data/inexistentFile.csv"));
    }

    /**
     * Test confirms that importing from a correct and valid file will succeed
     * and the number of patients in the list should be updated
     */
    @Test
    public void testImportFromACorrectAndaValidFile() {

        // this is a perfect file with 1000 patients which should be import successfully
        assertTrue(patientsList.importFromFile("data/testInput.csv"));
        assertEquals(1000, patientsList.getNumPatients());

    }

    /**
     * Test confirms that importing from a file with an empty line will still be
     * imported
     * returning true since the file is still valid and the empty line will be
     * gracefully caught
     * and the number of patients in the list should remain 1000
     */
    @Test
    public void testImportFromAFileWithAnEmptyLine() {

        // this file has an empty line that our importFile() will
        // gracefully catch and the file will still be imported successfuly

        assertTrue(patientsList.importFromFile("data/testInputwithEmptyLine.csv"));
        assertEquals(999, patientsList.getNumPatients());
    }

    /**
     * Test confirms that saving to a correct and valid file will succeed
     */
    @Test
    public void testSaveToFileIsSuccessful() {
        // loading to list
        patientsList.importFromFile("data/testInput.csv");

        // save this version to a new file
        int lineNumber = 0;
        int emptyFileLineNumber = 0;

        patientsList.saveToFile("data/testOutput.csv");
        try {
            File file = new File("data/testOutput.csv");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                lineNumber++;
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to read the file: " + e.getMessage());

        }

        // Writing nothing to a file
        try {

            FileWriter writer = new FileWriter(new File("data/testOutputEmpty.csv"));
            Scanner scanner = new Scanner(new File("data/testOutputEmpty.csv"));
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                emptyFileLineNumber++;
            }
            scanner.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            fail("Failed to read the file: " + e.getMessage());

        }
        // While the first file should also have 1000 lines
        // representing each patient
        // the second file should have none.

        assertEquals(1000, lineNumber);
        assertTrue(patientsList.saveToFile("data/testOutput.csv"));
        assertEquals(0, emptyFileLineNumber);
        assertNotEquals(lineNumber, emptyFileLineNumber);

    }

    /**
     * Test confirms that saving to a null file or an inexistent file will fail and
     * return false
     */
    @Test
    public void testSaveToFileFailed() {

        // Loading up the data
        patientsList.importFromFile("data/testInput.csv");

        // Trying to save to a null file should fail
        assertFalse(patientsList.saveToFile(null));

        // Trying to save to an incorrect extension file should fail
        assertFalse(patientsList.saveToFile("/this_folder_does_not_exist/test.csv"));

    }

}