package projects.patients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.Calendar;

public class PatientTest {

    private Patient patient;
    private Calendar cal = Calendar.getInstance();
    private Name name;
    private Date dob;

    @BeforeEach
    public void setUp() {
        name = new Name("John", "Doe");
        cal.set(1990, Calendar.JANUARY, 01); // Year, Month, Day
        dob = cal.getTime();

        patient = new Patient(new PatientIdentity(name, dob));
    }

    @Test
    public void testConstructorWithValidInput() {
        assertNotNull(patient);
        assertEquals("John", patient.getIdentity().getName().getFirstName());
        assertEquals("Doe", patient.getIdentity().getName().getLastName());
        assertEquals("1990-01-01", patient.getIdentity().dateFormatter());
    }

    @Test
    public void testConstructorWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PatientIdentity(null, dob);
        });
    }

    @Test
    public void testConstructorWithNullDob() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PatientIdentity(name, null);
        });
    }

    @Test
    public void testGetIdentity() {
        PatientIdentity actualResult = new PatientIdentity(name, dob);
        PatientIdentity expectedResult = patient.getIdentity();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testMatchWithMatchingPatient() {
        Patient otherPatient = new Patient(new PatientIdentity(name, dob));
        assertEquals(patient.getIdentity().match(otherPatient.getIdentity()), true);
    }

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

    @Test
    public void testToString() {
        String expectedString = "name: John Doe dob: 1990-01-01";
        assertEquals(expectedString, patient.toString());

    }
}
