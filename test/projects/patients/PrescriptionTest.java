
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.Assert.assertEquals;

public class PrescriptionTest {
    private Prescription pr = null;
    private Date issue;
    private String medName;
    private String prescriber;
    private int dosage;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private Patient[] list;

    /**
     * this method set up the test prescription for all tests
     */
    @BeforeEach
    void SetUP() {

        Calendar cal = Calendar.getInstance();
        cal.set(2022, Calendar.AUGUST, 04);
        issue = cal.getTime();
        medName = "benzozine";
        prescriber = "Miller";
        dosage = 50;

        pr = new Prescription(medName, issue, dosage, prescriber);
    }

    /*
     * This method verifies that the Precription Class throws successfully
     * when invalid input are passed onto it.
     */
    @Test
    void testConstructorThrowsOnInvalidInputs() {
        // should throw an IllegalArgumentException when the first name is null
        Exception e1 = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Prescription(null, issue, 50, "Miller");
                });
        assertEquals("Medication's name cannot be null", e1.getMessage());

        e1 = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Prescription("cetaphimine", null, 50, "Miller");
                });
        assertEquals("Medication's issue date cannot be null", e1.getMessage());

        e1 = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Prescription("cetaphimine", issue, 0, "Miller");
                });
        assertEquals("Dosage value is too small", e1.getMessage());

        e1 = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Prescription("cetaphimine", issue, 500, null);
                });
        assertEquals("Prescriber's name cannot be null", e1.getMessage());

    }

    /*
     * This test confirms that when valid values are passed to the constructor
     * it successfully creates a prescription.
     */
    @Test
    void testConstructorBuidsUpCorrectly() {
        assertNotNull(medName);
        assertNotNull(prescriber);
        assertNotNull(issue);
        assertTrue(dosage > 0, "Dosage should be greater than 0");
        assertNotNull(pr);
    }

    /**
     * test verfies that getDatee returns the correct value.
     */
    @Test
    void testgetDateReturnsCorrectOutput() {
        assertNotNull(pr.getDate());

        Date expectedResult = issue;
        Date actualResult = pr.getDate();
        assertEquals(expectedResult, actualResult);
    }

    /**
     * test verfies that getName() returns the correct value.
     */
    @Test
    void testgetNameReturnsCorrectOutput() {
        assertNotNull(pr.getName());

        String expectedResult = medName;
        String actualResult = pr.getName();
        assertEquals(expectedResult, actualResult);
    }

    /**
     * test verfies that getPrescriber() returns the correct value.
     */
    @Test
    void testgetPrescriberReturnsCorrectOutput() {
        assertNotNull(pr.getPrescriber());

        String expectedResult = prescriber;
        String actualResult = pr.getPrescriber();
        assertEquals(expectedResult, actualResult);
    }

    /**
     * test verfies that getDosage() returns the correct value.
     */
    @Test
    void testgetDosageReturnsCorrectOutput() {
        assertNotNull(pr.getDosage());

        int expectedResult = dosage;
        int actualResult = pr.getDosage();
        assertEquals(expectedResult, actualResult);
    }

    /**
     * This test verifies that the makePrescription() factory method preserves
     * the intergrity of a patient's data.
     */
    @Test
    void testMakePrescriptionPreservesData() {

        Calendar Cal = Calendar.getInstance();
        Cal.set(1953, Calendar.JUNE, 16);
        Date dob = Cal.getTime();
        list = new Patient[] { new Patient(new PatientIdentity(new Name("Maria", "Smith"), dob)) };

        Prescription justManufactured = Prescription.makePrescription(
                "Smith,Maria,1953-06-16,benzozine,2022-08-04,50,Miller", list);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        assertEquals(formatter.format(pr.getDate()),
                formatter.format(justManufactured.getDate()));
        assertEquals(pr.getName(),
                justManufactured.getName());

        assertEquals(
                pr.getPrescriber(),
                justManufactured.getPrescriber());
        assertEquals(
                pr.getDosage(),
                justManufactured.getDosage());
    }

    /**
     * This test verifies that an exception with an adequate message is
     * thrown when a null line is passed to makePrescription().
     */
    @Test
    void testMakePrescriptionThrowsOnNullLine() {
        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Prescription.makePrescription(null, list);
                });
        assertEquals("line must not be null.", e.getMessage());

    }

    /*
     * This method tests that makePrescriptions will throw an exception
     * when it is missing some information on the patient
     */
    @Test
    void testMakePrescriptionThrowsOnInsufficientInput() {

        // only 6 inputs are entered
        Exception e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Prescription.makePrescription(
                            "Smith,Maria,1953-06-16,2022-08-04,50,Miller", list);
                });
        assertEquals("Insufficient information for a prescription line", e.getMessage());

        // only 3 inputs are entered

        e = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Prescription.makePrescription(
                            "Smith,50,Miller", list);
                });
        assertEquals("Insufficient information for a prescription line", e.getMessage());

    }

    /**
     * This method confirm that getPatientFromPrescription returns the correct ID
     * for an individual and store it adequately.
     */
    @Test
    void testmatchPatientFromListRetrunsTrue() {
        Calendar Cal = Calendar.getInstance();
        Cal.set(1953, Calendar.JUNE, 16);
        Date dob = Cal.getTime();
        list = new Patient[] { new Patient(new PatientIdentity(new Name("Maria", "Smith"), dob)) };

        Prescription justManufactured = Prescription.makePrescription(
                "Smith,Maria,1953-06-16,benzozine,2022-08-04,50,Miller", list);

        assertTrue(justManufactured.matchPatientFromList(justManufactured, list));

    }

    @Test
    void testmatchPatientFromListRetrunsFalse() {
        Calendar Cal = Calendar.getInstance();
        Cal.set(1953, Calendar.JUNE, 16);
        Date dob = Cal.getTime();

        list = new Patient[] { new Patient(new PatientIdentity(new Name("Nicole", "Smith"), dob)) };
        Prescription justManufactured = Prescription.makePrescription(
                "Smith,Maria,1953-06-16,benzozine,2022-08-04,50,Miller", list);

        assertFalse(justManufactured.matchPatientFromList(justManufactured, list));

    }
}