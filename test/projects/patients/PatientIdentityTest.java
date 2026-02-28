package projects.patients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertEquals;
import java.util.Calendar;
import java.util.Date;

public class PatientIdentityTest {

    private PatientIdentity patientIdentity;
    private Name name;
    private Date dob;
    private Calendar cal = Calendar.getInstance();

    @BeforeEach
    public void setUp() {
        name = new Name("John", "Doe");
        cal.set(1990, Calendar.JANUARY, 01); // Year, Month, Day
        dob = cal.getTime();
        patientIdentity = new PatientIdentity(name, dob);
    }

    /**
     * Test that the constructor creates a PatientIdentity
     * object with valid inputs.
     */
    @Test
    public void testConstructorIsValid() {
        // Test that the constructor creates a PatientIdentity object with valid inputs
        assertNotNull(patientIdentity);
    }

    /**
     * Test that the constructor throws an IllegalArgumentException
     * when the name or/and date of birth is null.
     */
    @Test
    public void testConstructorInvalidInputs() {
        // Test that the constructor throws an IllegalArgumentException
        // when the name is null
        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new PatientIdentity(null, dob);
                });
        assertEquals("Patient's name cannot be null.", e.getMessage());

        // Test that the constructor throws an IllegalArgumentException
        // when the date of birth is null
        e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new PatientIdentity(name, null);
                });
        assertEquals("Patient's date of birth cannot be null.", e.getMessage());

    }

    /**
     * Test that the getName method returns the correct name.
     */
    @Test
    public void testGetName() {
        Name expectedName = name;
        Name actualName = patientIdentity.getName();
        assertEquals(expectedName, actualName);
    }

    /**
     * Test that the getDob method returns the correct date of birth.
     */
    @Test
    public void testGetDob() {
        Date actualDate = patientIdentity.getDob();
        Date expectedDate = dob;

        assertEquals(expectedDate, actualDate);
    }

    /**
     * Test that the date format returns the date
     * in the correct format
     */
    @Test
    public void testDateFormatter() {
        String expectedResult = "1990-01-01";
        String actualResult = patientIdentity.dateFormatter();

        assertEquals(expectedResult, actualResult);
    }

    /**
     * Test that the match method throws an IllegalArgumentException
     * when the other PatientIdentity object is null.
     */
    @Test
    public void testMatchWithNullOther() {

        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    patientIdentity.match(null);
                });
        assertEquals("Other PatientIdentity cannot be null.", e.getMessage());
    }

    /**
     * Test that the match method returns true when the name and date of birth
     * of both PatientIdentity objects match, and false otherwise.
     */
    @Test
    public void testMatchReturnsTrue() {

        // identical parameters in 2 different objects
        PatientIdentity patientIdentity2 = new PatientIdentity(name, dob);

        boolean expectedResult = true;
        boolean actualResult = patientIdentity.match(patientIdentity2);
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Test that the match method returns false when the name and date of birth
     * of both PatientIdentity objects do not match.
     */
    @Test
    public void testMatchReturnsFalse() {
        // names are the same but date of birth differ
        Name name2 = new Name("Jane", "Doe");
        cal.set(1985, Calendar.APRIL, 01);
        Date dob2 = cal.getTime();
        PatientIdentity patientIdentity2 = new PatientIdentity(name2, dob2);
        boolean expectedResult = false;
        boolean actualResult = patientIdentity.match(patientIdentity2);
        assertEquals(expectedResult, actualResult);

        // names are different but date are the same
        name2 = new Name("Jane", "Anderson");
        cal.set(1990, Calendar.JANUARY, 01);
        dob2 = cal.getTime();
        patientIdentity2 = new PatientIdentity(name2, dob2);
        expectedResult = false;
        actualResult = patientIdentity.match(patientIdentity2);
        assertEquals(expectedResult, actualResult);

        // names and dates of birth are different
        name2 = new Name("Charlotte", "Anderson");
        cal.set(1995, Calendar.APRIL, 01);
        dob2 = cal.getTime();
        patientIdentity2 = new PatientIdentity(name2, dob2);
        expectedResult = false;
        actualResult = patientIdentity.match(patientIdentity2);
        assertEquals(expectedResult, actualResult);

    }

    /**
     * Test that the isLessThan method correctly compares two PatientIdentity
     * objects
     * based on their names and dates of birth, and returns true if the current
     * PatientIdentity object is less than the other PatientIdentity object, and
     * false
     * otherwise.
     */
    @Test
    public void testIsLessThanReturnsFalseWithMatchingNames() {
        // Test that the isLessThan correctly
        // returns false in the first case.
        // The name of patient2 is different from that of patient1,
        // and the date of birth is after that of patient1.

        Name name2 = new Name("Jane", "Doe");
        cal.set(2016, Calendar.MAY, 4);
        Date dob2 = cal.getTime();

        PatientIdentity patientIdentity2 = new PatientIdentity(name2, dob2);
        boolean expectedResult = false;
        boolean actualResult = patientIdentity.isLessThan(patientIdentity2);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testIsLessThanReturnsFalse() {
        // names and dates of birth are different
        Name name3 = new Name("John", "Anderson");
        cal.set(1988, Calendar.SEPTEMBER, 10);
        Date dob3 = cal.getTime();
        PatientIdentity patientIdentity3 = new PatientIdentity(name3, dob3);

        boolean expectedResult = false;
        boolean actualResult = patientIdentity.isLessThan(patientIdentity3);

        assertEquals(expectedResult, actualResult);

        // name are the same but date of birth are different
        name3 = new Name("John", "Doe");
        cal.set(1988, Calendar.SEPTEMBER, 10);
        dob3 = cal.getTime();
        expectedResult = false;
        actualResult = patientIdentity.isLessThan(patientIdentity3);
    }

    @Test
    public void testIsLessThanReturnsTrue() {

        // names and dates of birth differ
        Name name2 = new Name("John", "Martins");
        cal.set(1998, Calendar.SEPTEMBER, 10);
        Date dob2 = cal.getTime();
        PatientIdentity patientIdentity2 = new PatientIdentity(name2, dob2);

        boolean expectedResult = true;
        boolean actualResult = patientIdentity.isLessThan(patientIdentity2);
        assertEquals(expectedResult, actualResult);

        // names are different while dates of birth are the same
        name2 = new Name("John", "Martins");
        cal.set(1998, Calendar.SEPTEMBER, 10);
        dob2 = cal.getTime();
        patientIdentity2 = new PatientIdentity(name2, dob2);

        expectedResult = true;
        actualResult = patientIdentity.isLessThan(patientIdentity2);
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Test that the isLessThan method throws an IllegalArgumentException
     * when the other PatientIdentity object is null.
     */
    @Test
    public void testIsLessThanWithNulOther() {

        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    patientIdentity.isLessThan(null);
                });
        assertEquals("Other PatientIdentity cannot be null.", e.getMessage());
    }

    /**
     * Test that the toString method returns a string representation of the
     * PatientIdentity object that includes the name and date of birth in the
     * expected format.
     * 
     */
    @Test
    public void testToString() {
        // also serves as a test to the private method dateFormatter();
        String actualString = "name: " + name.toString() + " dob: " + patientIdentity.dateFormatter();
        String expectedString = "name: John Doe dob: 1990-01-01";
        assertEquals(expectedString, actualString);
    }

}