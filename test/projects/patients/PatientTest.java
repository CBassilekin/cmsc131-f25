
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;

import java.util.Date;
import java.util.UUID;
import java.util.Calendar;

public class PatientTest {

    private Patient patient;
    private Calendar cal = Calendar.getInstance();
    private Name name;
    private Date dob;
    private UUID patientUUID;
    private PrescriptionList list;

    /***
     * 
     * Testing default values
     */

    @BeforeEach
    public void setUp() {
        name = new Name("John", "Doe");
        cal.set(1990, Calendar.JANUARY, 01); // Year, Month, Day
        dob = cal.getTime();
        list = new PrescriptionList();

        patient = new Patient(new PatientIdentity(name, dob));

    }

    /**
     * Test verifies that the Patient() object builds correctly
     * with an non null identity made up itself by
     * a name and a date of birth
     */
    @Test
    public void testConstructorWithValidInput() {
        assertNotNull(patient);
        assertEquals("John", patient.getIdentity().getName().getFirstName());
        assertEquals("Doe", patient.getIdentity().getName().getLastName());
        assertEquals("1990-01-01", patient.getIdentity().dateFormatter(dob));
    }

    /**
     * Test confirms that we cannot alllow a Patient with a null name
     * the constructor should throw an exception
     */
    @Test
    public void testConstructorWithNullName() {
        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Patient(new PatientIdentity(null, dob));
                });
        assertEquals("Patient's name cannot be null.", e.getMessage());

        e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Patient(new PatientIdentity(new Name("a", "b"), null));
                });
        assertEquals("Patient's date of birth cannot be null.", e.getMessage());

        e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Patient(new PatientIdentity(null, dob));
                });
        assertEquals("Patient's name cannot be null.", e.getMessage());
    }

    /**
     * Test confirms that a patient identity can be successfully returned
     */
    @Test
    public void testGetIdentity() {
        PatientIdentity actualResult = new PatientIdentity(name, dob);
        PatientIdentity expectedResult = patient.getIdentity();

        assertTrue(expectedResult.match(actualResult));
    }

    /**
     * Test confirms that the method returns a match
     * between 2 patients witht he same identity
     */
    @Test
    public void testMatchWithMatchingPatient() {
        Patient otherPatient = new Patient(new PatientIdentity(name, dob));
        assertEquals(patient.getIdentity().match(otherPatient.getIdentity()), true);
    }

    /**
     * Test confirms that non-match is returned when 2 patients
     * do not have the same identity
     */
    @Test
    public void testMatchWithNonMatchingPatient() {
        Name name2 = new Name("Jane", "Smith");
        Date dob2 = cal.getTime(); // different name, same dob
        Patient otherPatient = new Patient(new PatientIdentity(name2, dob2));
        boolean expectedResult = false;
        boolean actualResult = patient.getIdentity().match(otherPatient.getIdentity());
        assertEquals(expectedResult, actualResult);

        Name name3 = new Name("Jane", "Doe");
        Calendar cal3 = Calendar.getInstance();
        cal3.set(1980, Calendar.JANUARY, 01); // Year, Month, Day
        Date dob3 = cal3.getTime(); // same name, different dob
        otherPatient = new Patient(new PatientIdentity(name3, dob3));
        expectedResult = false;
        actualResult = patient.getIdentity().match(otherPatient.getIdentity());
        assertEquals(expectedResult, actualResult);

        Name name4 = new Name("Chris", "Bass");
        // different name, different dob
        otherPatient = new Patient(new PatientIdentity(name4, dob3));
        expectedResult = false;
        actualResult = patient.getIdentity().match(otherPatient.getIdentity());
        assertEquals(expectedResult, actualResult);

    }

    /**
     * Test comfirms that correct patient's identity
     * string is returned for a patient
     * 
     */
    @Test
    public void testToString() {

        // valid patient identity
        String expectedString = "Patient: name: John Doe dob: 1990-01-01";
        assertEquals(expectedString, patient.patientToString());
    }

    @Test
    public void testSetUUID() {
        // patient does not already have an UUID
        assertNull(patientUUID);

        // then patient is assigned one
        UUID actualResult = patient.setUUID();
        assertNotNull(actualResult);

        // the case where Patient already has an UUID
        UUID expectedResult = actualResult;
        UUID actualResult2 = patient.setUUID();
        assertEquals(expectedResult, actualResult2);

    }

    @Test
    public void testToCSV() {
        patientUUID = patient.setUUID();
        String actualResult = "John,Doe,1990-01-01," + patientUUID.toString();
        String expectedResult = patient.toCSV();
        assertEquals(expectedResult, actualResult);

    }

    /*
     * Test confirms that makePatient() method throws an exception
     * when a null argument string is passed.
     */
    @Test
    void testMakePatientThrowsOnInsufficientData() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Patient.makePatient("john,doe,1990-01-01");
                });
        assertEquals(
                "Insufficient information to create a patient",
                exception.getMessage());
    }

    @Test
    void testMakePatientThrowsOnInvalidDate() {

        /// date does not exist in the calendar
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Patient.makePatient("john,doe,1990-02-31,3c26d972-360a-436c-b1e8-8e5f2bde9490");
                });
        assertEquals(
                "Invalid date or UUID format",
                exception.getMessage());

        // date formatting is incorrect
        exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Patient.makePatient("john,doe,1990/03/31,3c26d972-360a-436c-b1e8-8e5f2bde9490");
                });
        assertEquals(
                "Invalid date or UUID format",
                exception.getMessage());
    }

    @Test
    void testMakePatientThrowsOnInvalidUUID() {

        // insufficient digit
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Patient.makePatient("john,doe,1990-02-31,3c26d972-360-436c-b1e8-8e5f2bde9490");
                });
        assertEquals(
                "Invalid date or UUID format",
                exception.getMessage());

        // incorrect alphanumeric format
        exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Patient.makePatient("john,doe,1990-02-31,30260972-360-4360-0108-805020009490");
                });
        assertEquals(
                "Invalid date or UUID format",
                exception.getMessage());
    }

    /*
     * Test confirms that make() method throws an exception
     * when a null argument string is passed.
     */
    @Test
    void testMakePatientThrowsOnNullInput() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Patient.makePatient(null);
                });
        assertEquals(
                "line must not be null.",
                exception.getMessage());
    }

    /*
     * Test confirms that make preserves a given account data during the factory.
     */
    @Test
    void testMakePatientPreservesData() {
        patientUUID = patient.setUUID();
        Patient madePatient = Patient.makePatient(
                "Doe,John,1990-01-01," + patientUUID.toString());
        assertEquals(patient.getIdentity().getName().getFirstName(),
                madePatient.getIdentity().getName().getFirstName());
        assertEquals(
                patient.getIdentity().getName().getLastName(),
                madePatient.getIdentity().getName().getLastName());

        assertEquals(
                patient.getIdentity().dateFormatter(dob),
                madePatient.getIdentity().dateFormatter(madePatient.getIdentity().getDob()));
        assertEquals(
                patientUUID,
                madePatient.setUUID());
    }

    @Test
    public void testStringToDate() {
        // valid String
        assertNotNull(Patient.stringToDate("1990-01-01"));

        // when a null string is entered
        Date actualResult = Patient.stringToDate(null);
        assertNull(actualResult);

        // invalid date (one that does not exist in the calendar)
        actualResult = Patient.stringToDate("2012-13-36");
        assertNull(actualResult);
    }

    @Test
    public void testValidUUID() {
        // when a null string is passed
        assertFalse(Patient.validUUID(null));

        // when a valid UUID is passed
        assertTrue(Patient.validUUID("3c26d972-360a-436c-b1e8-8e5f2bde9490"));

        // when an invalid UUID is passed
        assertFalse(Patient.validUUID("3c26d972-360-436c-b1e8-8e5f2bde9490"));

    }

    @Test
    public void testIsValidUUID() {
        // when a null string is passed
        assertFalse(Patient.isValidUUID(null));

        // when a valid UUID is passed
        assertTrue(Patient.isValidUUID("3c26d972-360a-436c-b1e8-8e5f2bde9490"));

        // when an invalid UUID is passed
        assertFalse(Patient.isValidUUID("3c26d972-360-436c-b1e8-8e5f2bde9490"));

    }

    @Test
    public void testGetList() {

        Calendar Cal = Calendar.getInstance();
        Cal.set(2024, Calendar.FEBRUARY, 11);
        Date issue = Cal.getTime();
        Prescription patPr = new Prescription("heptapone", issue, 50, "Agrawal");
        list.add(patPr);

        assertNotNull(list);
        list.init();

        assertEquals(1, list.getCount(list));
    }

}
