
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private PatientsList newList;
    private Patient testList[];
    private int MAXPATIENTS = 1000;
    private Patient patientTest1;
    private Patient patientTest2;
    private Patient patientTest3;

    private Calendar cal = Calendar.getInstance();

    /**
     * Testing will use all these values as defaukt
     * before each test
     */

    @BeforeEach
    public void setUp() {

        newList = new PatientsList();
        testList = new Patient[MAXPATIENTS];

        cal.set(1990, Calendar.JANUARY, 01); // Year, Month, Day
        patientTest1 = new Patient(new PatientIdentity(
                new Name("John", "Doe"), cal.getTime()));
        cal.set(1970, Calendar.MARCH, 01); // Year, Month, Day
        patientTest2 = new Patient(new PatientIdentity(
                new Name("Jane", "Smith"), cal.getTime()));
        cal.set(2000, Calendar.MAY, 05); // Year, Month, Day
        patientTest3 = new Patient(new PatientIdentity(
                new Name("Alice", "Johnson"), cal.getTime()));

    }

    /**
     * Test confirms that the constuctor builds correctly
     * each patient list should hold 1,000 spaces
     * should be all empty at the beginning
     * should not be null
     */
    @Test
    public void testValidConstructor() {
        assertEquals(0, newList.getNumPatients(testList));
        assertNotNull(testList);
    }

    /**
     * Test confirms that the list effectively holds
     * 1000 spaces
     */
    @Test
    public void testgetSize() {
        assertEquals(1000, newList.getSize(testList));
    }

    /**
     * Test confirms that adding patients is successfull
     * especially in an orderly way
     * we will not be checking the case where the list is full
     * because that is not part of this scope
     */
    @Test
    public void testAddPatientInOrder() {
        // adding patients in random order to be reordered automatically
        cal.set(1995, Calendar.MARCH, 20);
        Patient patientTest4 = new Patient(new PatientIdentity(
                new Name("Bob", "Brown"), cal.getTime()));
        newList.add(patientTest3, testList);
        newList.add(patientTest1, testList);
        newList.add(patientTest4, testList);
        newList.add(patientTest2, testList);

        // checking if the list has added . patients successfully
        int expectedNumPatients = 4;
        int actualNumPatients = newList.getNumPatients(testList);
        assertEquals(expectedNumPatients, actualNumPatients);

        // checking that the item ids are in the correct order
        Patient space0 = newList.next(testList);
        assertTrue(space0.getIdentity().isLessThan(patientTest1.getIdentity()));
        Patient space1 = newList.next(testList);
        assertTrue(space1.getIdentity().isLessThan(patientTest3.getIdentity()));
        Patient space2 = newList.next(testList);
        assertTrue(space2.getIdentity().isLessThan(patientTest2.getIdentity()));
    }

    /**
     * Test confirms that valid patient are added
     * and check that the size of the list grows accordingly
     */
    @Test
    public void testAddValidPatient() {
        boolean actualResult = newList.add(patientTest1, testList);
        assertTrue(actualResult);
        int actualNumPatients = newList.getNumPatients(testList);
        int expectedNumPatients = 1;
        assertEquals(expectedNumPatients, actualNumPatients);

        actualResult = newList.add(patientTest2, testList);
        assertTrue(actualResult);
        int actualNumPatients2 = newList.getNumPatients(testList);
        int expectedNumPatients2 = 2;
        assertEquals(expectedNumPatients2, actualNumPatients2);

        actualResult = newList.add(patientTest3, testList);
        assertTrue(actualResult);
        int actualNumPatients3 = newList.getNumPatients(testList);
        int expectedNumPatients3 = 3;
        assertEquals(expectedNumPatients3, actualNumPatients3);
    }

    /**
     * Test checks that a null patient cannot be successfully added to the list
     */
    @Test
    public void testAddNullPatient() {
        boolean actualResult = newList.add(null, testList);
        assertFalse(actualResult);

        int actualNumPatients = newList.getNumPatients(testList);
        int expectedNumPatients = 0;
        assertEquals(expectedNumPatients, actualNumPatients);
    }

    /**
     * Test confirms that list cannot accept adding a
     * duplicate patient
     */

    @Test
    public void testAddDuplicatePatient() {
        newList.add(patientTest1, testList);
        boolean actualResult = newList.add(patientTest1, testList);
        Boolean expectedResult = false;
        assertEquals(expectedResult, actualResult);
        assertEquals(1, newList.getNumPatients(testList));
    }

    /**
     * Test checks that a patient already exists in the database
     */
    @Test
    public void testPatientExistsReturnsTrue() {
        newList.add(patientTest1, testList);
        newList.add(patientTest2, testList);

        // checking if existing patient is found
        boolean actualResult = newList.patientExists(patientTest1.getIdentity(), testList);
        Boolean expectedResult = true;
        assertEquals(expectedResult, actualResult);

        // checking if another existing patient is found
        actualResult = newList.patientExists(patientTest2.getIdentity(), testList);
        expectedResult = true;
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Test checks that a patient does not already exist
     * and can be added to the database.
     */
    @Test
    public void testPatientExistsReturnsFalse() {
        newList.add(patientTest1, testList);

        // checking if non-existing patient is not found
        cal.set(1995, Calendar.MARCH, 20);
        Patient nonExistingId = new Patient(new PatientIdentity(
                new Name("Non", "Existing"), cal.getTime()));
        boolean actualResult = newList.patientExists(nonExistingId.getIdentity(), testList);
        boolean expectedResult = false;
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Test checks the total number of existing patients in the list.
     */
    @Test
    public void testNumberOfPatients() {
        // initially the list should be empty
        int actualNumPatients = newList.getNumPatients(testList);
        int expectedNumPatients = 0;
        assertEquals(expectedNumPatients, actualNumPatients);

        // adding patients and checking the count
        newList.add(patientTest1, testList);
        newList.add(patientTest2, testList);
        actualNumPatients = newList.getNumPatients(testList);
        expectedNumPatients = 2;
        assertEquals(expectedNumPatients, actualNumPatients);

        // adding another patient and checking the count again
        newList.add(patientTest3, testList);
        actualNumPatients = newList.getNumPatients(testList);
        expectedNumPatients = 3;
        assertEquals(expectedNumPatients, actualNumPatients);
    }

    /**
     * Test checks that finding a null patient
     * returns a null patient
     */
    @Test
    public void testFindReturnsNull() {
        // trying to find a patient with a null identity
        assertNull(newList.find(null, testList));

        // trying to find a non-existing patient by ID
        cal.set(1995, Calendar.MARCH, 20);
        PatientIdentity nonExistingId = new PatientIdentity(
                new Name("Non", "Existing"), cal.getTime());
        Patient actualPatient = newList.find(nonExistingId, testList);
        assertNull(actualPatient);
    }

    /**
     * Test confirm that finding an existing patient
     * can be easily returned using the find() method
     */
    @Test
    public void testFindPatientById() {
        newList.add(patientTest1, testList);
        newList.add(patientTest2, testList);

        // finding an existing patient by ID
        Patient actualPatient = newList.find(patientTest1.getIdentity(), testList);
        Patient expectedPatient = patientTest1;
        assertEquals(expectedPatient, actualPatient);

        // finding another existing patient by ID
        actualPatient = newList.find(patientTest2.getIdentity(), testList);
        expectedPatient = patientTest2;
        assertEquals(expectedPatient, actualPatient);

    }

    /**
     * Test confirm that the iterator is correctly initialized at -1.
     */
    @Test
    public void testInitIterator() {

        // checking if the iterator is set to the start at -1
        int expectedIndex = -1;
        newList.initIterator();
        int actualIndex = newList.indexOfIteration();
        assertEquals(expectedIndex, actualIndex);
    }

    /**
     * Test confirms that an empty array is initialized at -1
     * next() should return null
     */
    @Test
    public void testNextAnEmptyArray() {
        assertNull(newList.next(testList));
        assertEquals(-1, newList.indexOfIteration());
    }

    /**
     * For a Null patient in the lits,
     * next() returns a null patient
     */
    @Test
    public void testNextWhilePatientIsNull() {
        // the list is empty
        newList.next(testList);
        assertNull(newList.next(testList));

    }

    /**
     * Test confirms that next() returns the correct patient
     * from the a non-empty database
     */
    @Test
    public void testNextAfterRepeatedCalls() {

        // adding objects to the list
        newList.add(patientTest1, testList);
        newList.add(patientTest2, testList);

        // checking to see that the iterators change correctly
        Patient nextPatient1 = newList.next(testList);
        assertTrue(patientTest1.getIdentity().match(nextPatient1.getIdentity()));
        assertEquals(0, newList.indexOfIteration());

        Patient nextPatient2 = newList.next(testList);
        assertTrue(patientTest2.getIdentity().match(nextPatient2.getIdentity()));
        assertEquals(1, newList.indexOfIteration());

    }

    /**
     * Test confirms that the index of Iteration is reported correctly each time
     */
    @Test
    public void testIndexOfIteration() {

        // initializing the iterator
        newList.initIterator();

        // checking the index of the iterator at the start
        int expectedIndex = -1;
        int actualIndex = newList.indexOfIteration();
        assertEquals(expectedIndex, actualIndex);

        // Adding patients to the list
        newList.add(patientTest1, testList);
        newList.add(patientTest2, testList);
        newList.add(patientTest3, testList);

        // moving the iterator to the first patient and checking the index
        newList.next(testList);
        expectedIndex = 0;
        actualIndex = newList.indexOfIteration();
        assertEquals(expectedIndex, actualIndex);

        // moving the iterator to the next patient and checking the index again
        newList.next(testList);
        expectedIndex = 1;
        actualIndex = newList.indexOfIteration();
        assertEquals(expectedIndex, actualIndex);

        // moving the iterator to the next patient and checking the index again
        newList.next(testList);
        expectedIndex = 2;
        actualIndex = newList.indexOfIteration();
        assertEquals(expectedIndex, actualIndex);

        // moving the iterator beyond the last patient and checking the index resets to
        // -1

        newList.next(testList);
        expectedIndex = -1;
        actualIndex = newList.indexOfIteration();
        assertEquals(expectedIndex, actualIndex);
    }

    @Test
    public void testDirectAdd() {
        // a null patient is passed to the method
        assertFalse(newList.directAdd(null, testList));

        // a pattient add operation is successful
        assertTrue(newList.directAdd(patientTest1, testList));
        newList.next(testList);
        assertEquals(0, newList.indexOfIteration());

    }

    @Test
    public void testMergeSort() {

        // when passing a null list of patients to the method

        assertFalse(PatientsList.mergeSort(null));

        // when a list has 1 item
        Patient[] list = { patientTest1 };
        assertTrue(PatientsList.mergeSort(list));

        // lets add the other 2 patients and sort all
        list = new Patient[] { patientTest1, patientTest2, patientTest3 };

        assertTrue(PatientsList.mergeSort(list));
        assertEquals(patientTest1, list[0]);// First element should be patient1
        assertEquals(patientTest3, list[1]);// Second element should patient3
        assertEquals(patientTest2, list[2]);// last element should be patient2

        // now testing that mergeSort is maintained
        // lets try mergeSort again on an already sorted list
        // the result should be the same as above.

        assertTrue(PatientsList.mergeSort(list));
        assertEquals(patientTest1, list[0]);// First element should be patient1
        assertEquals(patientTest3, list[1]);// Second element should patient3
        assertEquals(patientTest2, list[2]);// last element should be patient2

    }

    @Test
    public void testImportFromAnInexistentFile() {

        // if the file is inexistent, method should return false
        assertFalse(newList.importFromFile("data/inexistent.csv", testList));
        assertEquals(0, newList.getNumPatients(testList));
    }

    @Test
    public void testImportFromACorrectAndaValidFile() {

        // this is a perfect file with 1000 patients which should be import successfully
        assertTrue(newList.importFromFile("data/testInput.csv", testList));
        assertEquals(1000, newList.getNumPatients(testList));

        // let's find() a known patient from the file in the list of patients
        // We will use line =
        // Barbara,Alvarez,1927-10-04,29eea220-dc3f-4a18-87b6-fba2b4d989b0

        Patient actualResult = newList.find(testList[0].getIdentity(), testList);
        Patient expectedResult = testList[0];
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testImportFromAFileWithAnEmptyLine() {

        // this file has an empty line that will not be imported
        // that particular line could not create a patient and
        // should throuw an error
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    newList.importFromFile("data/testInputwithEmptyLine.csv", testList);
                });
        assertEquals("Insufficient information to create a patient",
                exception.getMessage());

        // However the rest of the file should not proceed successfully
        // assertTrue(newList.importFromFile("data/testInputwithEmptyLine.csv"));
        assertFalse(newList.importFromFile("data/testInputwithEmptyLine", testList));
        assertEquals(0, newList.getNumPatients(testList));
    }

    @Test
    public void testSaveToFileIsSuccessful() {
        // loading to list
        newList.importFromFile("data/testInput.csv", testList);

        // save this version to a new file
        int lineNumber = 0;
        int emptyFileLineNumber = 0;

        newList.saveToFile("data/testOutput.csv", testList);
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
        assertTrue(newList.saveToFile("data/testOutput.csv", testList));
        assertEquals(0, emptyFileLineNumber);
        assertNotEquals(lineNumber, emptyFileLineNumber);

    }

    @Test
    public void testSaveToFileFailed() {

        // Loading up the data
        newList.importFromFile("data/testInput.csv", testList);

        // Trying to save to a null file should fail
        assertFalse(newList.saveToFile(null, testList));

        // Trying to save to an incorrect extension file should fail
        assertFalse(newList.saveToFile("date/inexistentFilepath.csv", testList));

    }

}
