package projects.hospital;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;

public class PatientTest {

    private PatientIdentity patient;

    @BeforeEach
    public void setUp() {
        Name name = new Name("John", "Doe");
        LocalDate dob = LocalDate.of(1990, 1, 1);
        patient = new PatientIdentity(name, dob);
    }

    @Test
    public void testConstructorWithValidInput() {
        assertNotNull(patient);
        assertEquals("John", patient.getName().getFirstName());
        assertEquals("Doe", patient.getName().getLastName());
        assertEquals("1990-01-01", patient.getFormattedDobString());
    }

    @Test
    public void testConstructorWithNullName() {
        LocalDate dob = LocalDate.of(1990, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> {
            new PatientIdentity(null, dob);
        });
    }

    @Test
    public void testConstructorWithNullDob() {
        Name name = new Name("John", "Doe");
        assertThrows(IllegalArgumentException.class, () -> {
            new PatientIdentity(name, null);
        });
    }

    @Test
    public void testGetIdentity() {
        Name expectedName = new Name("John", "Doe");
        Name actualName = patient.getName();
        assertEquals(expectedName.getFirstName(), actualName.getFirstName());
        assertEquals(expectedName.getLastName(), actualName.getLastName());

        LocalDate expectedDob = LocalDate.of(1990, 1, 1);
        LocalDate actualDob = patient.getDob();
        assertEquals(expectedDob, actualDob);

    }

    @Test
    public void testMatchWithMatchingPatient() {
        Name name = new Name("John", "Doe");
        LocalDate dob = LocalDate.of(1990, 1, 1);
        PatientIdentity otherPatient = new PatientIdentity(name, dob);
        assertEquals(patient.match(otherPatient), true);
    }

    @Test
    public void testMatchWithNonMatchingPatient() {
        Name name = new Name("Jane", "Smith");
        LocalDate dob = LocalDate.of(1995, 5, 15);
        PatientIdentity otherPatient = new PatientIdentity(name, dob);
        assertEquals(patient.match(otherPatient), false);
    }

    @Test
    public void testToString() {
        String expectedString = "name: John Doe dob: 1990-01-01";
        assertEquals(expectedString, patient.toString());

    }
}
