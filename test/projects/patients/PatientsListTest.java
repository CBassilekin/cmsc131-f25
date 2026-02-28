
package projects.patients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertEquals;
import java.util.Calendar;

public class PatientsListTest {

    private PatientsList newList;
    private Patient patientTest1;
    private Patient patientTest2;
    private Patient patientTest3;

    private Calendar cal = Calendar.getInstance();

    @BeforeEach
    public void setUp() {

        newList = new PatientsList();

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

    @Test
    public void testValidConstructor() {
        assertEquals(0, newList.getNumPatients());
        assertEquals(10000, newList.getSize());
    }

    @Test
    public void testAddPatientInOrder() {
        // adding patients in random order to be reordered automatically
        cal.set(1995, Calendar.MARCH, 20);
        Patient patientTest4 = new Patient(new PatientIdentity(
                new Name("Bob", "Brown"), cal.getTime()));
        newList.add(patientTest3);
        newList.add(patientTest1);
        newList.add(patientTest4);
        newList.add(patientTest2);

        // checking if the list has added . patients successfully
        int expectedNumPatients = 4;
        int actualNumPatients = newList.getNumPatients();
        assertEquals(expectedNumPatients, actualNumPatients);

        // checking that the item ids are in the correct order
        Patient space0 = newList.next();
        assertFalse(space0.getIdentity().isLessThan(patientTest1.getIdentity()));
        Patient space1 = newList.next();
        assertFalse(space1.getIdentity().isLessThan(patientTest3.getIdentity()));
        Patient space2 = newList.next();
        assertFalse(space2.getIdentity().isLessThan(patientTest2.getIdentity()));
    }

    @Test
    public void testAddValidPatient() {
        boolean actualResult = newList.add(patientTest1);
        assertTrue(actualResult);
        int actualNumPatients = newList.getNumPatients();
        int expectedNumPatients = 1;
        assertEquals(expectedNumPatients, actualNumPatients);

        actualResult = newList.add(patientTest2);
        assertTrue(actualResult);
        int actualNumPatients2 = newList.getNumPatients();
        int expectedNumPatients2 = 2;
        assertEquals(expectedNumPatients2, actualNumPatients2);

        actualResult = newList.add(patientTest3);
        assertTrue(actualResult);
        int actualNumPatients3 = newList.getNumPatients();
        int expectedNumPatients3 = 3;
        assertEquals(expectedNumPatients3, actualNumPatients3);
    }

    @Test
    public void testAddNullPatient() {
        boolean actualResult = newList.add(null);
        assertFalse(actualResult);

        int actualNumPatients = newList.getNumPatients();
        int expectedNumPatients = 0;
        assertEquals(expectedNumPatients, actualNumPatients);
    }

    @Test
    public void testAddDuplicatePatient() {
        newList.add(patientTest1);
        boolean actualResult = newList.add(patientTest1);
        Boolean expectedResult = false;
        assertEquals(expectedResult, actualResult);
        assertEquals(1, newList.getNumPatients());
    }

    @Test
    public void testAddPatientWhenFull() {

        newList.add(patientTest1);
        newList.add(patientTest2);
        newList.add(patientTest3);

        // trying to add another patient when the list is already full

        cal.set(1995, Calendar.MARCH, 20);
        Patient patientTest4 = new Patient(new PatientIdentity(
                new Name("Adam", "Smith"), cal.getTime()));
        assertTrue(newList.add(patientTest4));
    }

    @Test
    public void testPatientExistsReturnsTrue() {
        newList.add(patientTest1);
        newList.add(patientTest2);

        // checking if existing patient is found
        boolean actualResult = newList.patientExists(patientTest1.getIdentity());
        Boolean expectedResult = true;
        assertEquals(expectedResult, actualResult);

        // checking if another existing patient is found
        actualResult = newList.patientExists(patientTest2.getIdentity());
        expectedResult = true;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testPatientExistsReturnsFalse() {
        newList.add(patientTest1);

        // checking if non-existing patient is not found
        cal.set(1995, Calendar.MARCH, 20);
        Patient nonExistingId = new Patient(new PatientIdentity(
                new Name("Non", "Existing"), cal.getTime()));
        boolean actualResult = newList.patientExists(nonExistingId.getIdentity());
        boolean expectedResult = false;
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testNumberOfPatients() {
        // initially the list should be empty
        int actualNumPatients = newList.getNumPatients();
        int expectedNumPatients = 0;
        assertEquals(expectedNumPatients, actualNumPatients);

        // adding patients and checking the count
        newList.add(patientTest1);
        newList.add(patientTest2);
        actualNumPatients = newList.getNumPatients();
        expectedNumPatients = 2;
        assertEquals(expectedNumPatients, actualNumPatients);

        // adding another patient and checking the count again
        newList.add(patientTest3);
        actualNumPatients = newList.getNumPatients();
        expectedNumPatients = 3;
        assertEquals(expectedNumPatients, actualNumPatients);
    }

    @Test
    public void testFindReturnsNull() {
        assertNull(newList.find(null));
    }

    @Test
    public void testFindPatientById() {
        newList.add(patientTest1);
        newList.add(patientTest2);

        // finding an existing patient by ID
        Patient actualPatient = newList.find(patientTest1.getIdentity());
        Patient expectedPatient = patientTest1;
        assertEquals(expectedPatient, actualPatient);

        // finding another existing patient by ID
        actualPatient = newList.find(patientTest2.getIdentity());
        expectedPatient = patientTest2;
        assertEquals(expectedPatient, actualPatient);

        // trying to find a non-existing patient by ID
        cal.set(1995, Calendar.MARCH, 20);
        PatientIdentity nonExistingId = new PatientIdentity(
                new Name("Non", "Existing"), cal.getTime());
        actualPatient = newList.find(nonExistingId);
        assertNull(actualPatient);

    }

    @Test
    public void testInitIterator() {

        // checking if the iterator is set to the start at -1
        int expectedIndex = -1;
        newList.initIterator();
        int actualIndex = newList.indexOfIteration();
        assertEquals(expectedIndex, actualIndex);
    }

    @Test
    public void testNextAnEmptyArray() {
        assertNull(newList.next());
        assertEquals(-1, newList.indexOfIteration());
    }

    @Test
    public void testNextWhilePatientIsNull() {
        // the list is empty
        newList.next();
        assertNull(newList.next());

    }

    @Test
    public void testNextAfterRepeatedCalls() {

        // when list is empty
        newList.next();
        assertEquals(-1, newList.indexOfIteration());

        // adding objects to the list
        newList.add(patientTest1);
        newList.add(patientTest2);

        // checking to see that the iterators change correctly
        Patient nextPatient1 = newList.next();
        assertEquals(patientTest1, nextPatient1);
        assertEquals(0, newList.indexOfIteration());

        Patient nextPatient2 = newList.next();
        assertEquals(patientTest2, nextPatient2);
        assertEquals(1, newList.indexOfIteration());

    }

    @Test
    public void testIndexOfIteration() {

        // initializing the iterator
        newList.initIterator();

        // checking the index of the iterator at the start
        int expectedIndex = -1;
        int actualIndex = newList.indexOfIteration();
        assertEquals(expectedIndex, actualIndex);

        // Adding patients to the list
        newList.add(patientTest1);
        newList.add(patientTest2);
        newList.add(patientTest3);

        // moving the iterator to the next patient and checking the index
        newList.next();
        expectedIndex = 0;
        actualIndex = newList.indexOfIteration();
        assertEquals(expectedIndex, actualIndex);

        // moving the iterator to the next patient and checking the index again
        newList.next();
        expectedIndex = 1;
        actualIndex = newList.indexOfIteration();
        assertEquals(expectedIndex, actualIndex);

        // moving the iterator to the next patient and checking the index again
        newList.next();
        expectedIndex = 2;
        actualIndex = newList.indexOfIteration();
        assertEquals(expectedIndex, actualIndex);

        // moving the iterator beyond the last patient and checking the index resets to
        // -1
        newList.next();
        expectedIndex = -1;
        actualIndex = newList.indexOfIteration();
        assertEquals(expectedIndex, actualIndex);
    }

}
