package projects.patients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.Calendar;

public class PatientTest {

    private Patient patient;
    private Calendar cal = Calendar.getInstance();
    private Name name;
    private Date dob;

    /***
     * 
     * Testing default values
     */

    @BeforeEach
    public void setUp() {
        name = new Name("John", "Doe");
        cal.set(1990, Calendar.JANUARY, 01); // Year, Month, Day
        dob = cal.getTime();

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
        assertEquals("1990-01-01", patient.getIdentity().dateFormatter());
    }

    /**
     * Test confirms that we cannot alllow a Patient with a null name
     * the constructor should throw an exception
     */
    @Test
    public void testConstructorWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PatientIdentity(null, dob);
        });
    }

    /**
     * Test confirms that we cannot allow a Patient with a null date of birth
     * the constructor should throw an exception
     */
    @Test
    public void testConstructorWithNullDob() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PatientIdentity(name, null);
        });
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
        String expectedString = "identity: name: John Doe dob: 1990-01-01";
        assertEquals(expectedString, patient.toString());
        ;

    }
}
