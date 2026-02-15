package projects.hospital;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;

public class PatientIdentityTest {

    private PatientIdentity patientIdentity;
    private Name name;
    private LocalDate dob;

    @BeforeEach
    public void setUp() {
        name = new Name("John", "Doe");
        dob = LocalDate.of(1990, 1, 1);
        patientIdentity = new PatientIdentity(name, dob);
    }

    /**
     * Test that the constructor creates a PatientIdentity
     * object with valid inputs.
     */
    @Test
    public void testConstructorIsValid() {
        // Test that the constructor creates a PatientIdentity object with valid inputs
        ;
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
        // Test that the constructor throws an IllegalArgumentException
        // when both the name and date of birth are null
        e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new PatientIdentity(null, null);
                });
        assertEquals("Patient's name and date of birth cannot be null.", e.getMessage());

    }

    /**
     * Test that the getName method returns the correct name.
     */
    @Test
    public void testGetName() {
        patientIdentity = new PatientIdentity(name, dob);

        Name expectedName = name;
        Name actualName = patientIdentity.getName();
        assertEquals(expectedName, actualName);
    }

    /**
     * Test that the getFormattedDobString method returns the correct date of birth
     * string in the expected format.
     */
    @Test
    public void testGetFormattedDobString() {
        patientIdentity = new PatientIdentity(name, dob);
        String expectedDobString = "1990-01-01";
        String actualDobString = patientIdentity.getFormattedDobString();
        assertEquals(expectedDobString, actualDobString);
    }

    /**
     * Test that the getDob method returns the correct date of birth.
     */
    @Test
    public void testGetDob() {
        patientIdentity = new PatientIdentity(name, dob);

        LocalDate expectedDob = dob;
        LocalDate actualDob = patientIdentity.getDob();
        assertEquals(expectedDob, actualDob);
    }

    /**
     * Test that the match method returns true when the name and date of birth
     * of both PatientIdentity objects match, and false otherwise.
     */
    @Test
    public void testMatchReturnsTrue() {
        PatientIdentity patient1 = new PatientIdentity(name, dob);
        PatientIdentity patient2 = new PatientIdentity(name, dob);

        boolean expectedResult = true;
        boolean actualResult = patient1.match(patient2);
        assertEquals(expectedResult, actualResult);

    }

    /**
     * Test that the match method returns false when the name and date of birth
     * of both PatientIdentity objects do not match.
     */
    @Test
    public void testMatchReturnsFalse() {
        PatientIdentity patient1 = new PatientIdentity(name, dob);
        Name name2 = new Name("Jane", "Doe");
        LocalDate dob2 = LocalDate.of(1995, 1, 1);
        PatientIdentity patient2 = new PatientIdentity(name2, dob2);

        boolean expectedResult = false;
        boolean actualResult = patient1.match(patient2);
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
    public void testIsLessThan() {

        // Test that the isLessThan correctly
        // returns false in the first case.
        // The name of patient2 is different from that of patient1,
        // and the date of birth is after that of patient1.
        PatientIdentity patient1 = new PatientIdentity(name, dob);
        Name name2 = new Name("Jane", "Doe");
        LocalDate dob2 = LocalDate.of(1995, 1, 1);
        PatientIdentity patient2 = new PatientIdentity(name2, dob2);

        boolean expectedResult = false;
        boolean actualResult = patient1.isLessThan(patient2);
        assertEquals(expectedResult, actualResult);

        // Test that the isLessThan correctly
        // returns false in the second case.
        // The name of patient3 is the same as that of patient1,
        // but the date of birth is before that of patient1.
        Name name3 = new Name("John", "Doe");
        LocalDate dob3 = LocalDate.of(1985, 1, 1);
        PatientIdentity patient3 = new PatientIdentity(name3, dob3);

        expectedResult = false;
        actualResult = patient1.isLessThan(patient3);
        assertEquals(expectedResult, actualResult);

        // Test that the isLessThan correctly
        // returns false in the third case.
        // The name of patient4 is the same as patient1,
        // and the date of birth is the same as that of patient1.
        Name name4 = new Name("John", "Doe");
        LocalDate dob4 = LocalDate.of(1990, 1, 1);
        PatientIdentity patient4 = new PatientIdentity(name4, dob4);

        expectedResult = false;
        actualResult = patient1.isLessThan(patient4);
        assertEquals(expectedResult, actualResult);

        // Test that the isLessThan correctly
        // returns true in the fourth case.
        // The name of patient5 is different from that of patient1,
        // but the date of birth is the same as that of patient1.
        Name name5 = new Name("John", "Vladimirovich");
        LocalDate dob5 = LocalDate.of(1990, 1, 1);
        PatientIdentity patient5 = new PatientIdentity(name5, dob5);

        expectedResult = true;
        actualResult = patient1.isLessThan(patient5);
        assertEquals(expectedResult, actualResult);

        // Test that the isLessThan correctly
        // returns true in the fifth case.
        // The name of patient6 is different from that of patient1,
        // but the date of birth is before that of patient1.
        Name name6 = new Name("Jane", "zukova");
        LocalDate dob6 = LocalDate.of(1985, 1, 1);
        PatientIdentity patient6 = new PatientIdentity(name6, dob6);

        expectedResult = true;
        actualResult = patient1.isLessThan(patient6);
        assertEquals(expectedResult, actualResult);

    }

    /**
     * Test that the isLessThan method throws an IllegalArgumentException
     * when the other PatientIdentity object is null.
     */
    @Test
    public void testIsLessThanWithNulOther() {
        PatientIdentity patient1 = new PatientIdentity(name, dob);
        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    patient1.isLessThan(null);
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
        PatientIdentity patient1 = new PatientIdentity(name, dob);
        String expectedString = "name: " + name.toString() + " dob: " + dob.toString();
        String actualString = patient1.toString();
        assertEquals(expectedString, actualString);
    }

}