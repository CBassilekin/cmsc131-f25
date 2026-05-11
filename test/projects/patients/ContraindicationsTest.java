import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import java.util.Calendar;

public class ContraindicationsTest {

        private Patient patient;
        private PatientsList paList;
        private Contraindications contra;
        private HashTable table = new HashTable(1009);

        private Calendar cal = Calendar.getInstance();; // using a random prime number for the size of the hash table
                                                        // to
                                                        // reduce collisions

        @Before
        public void setUp() {
                cal.set(1953, Calendar.JUNE, 16);

                patient = new Patient(new PatientIdentity(new Name(
                                "Maria", "Smith"), cal.getTime()));
                contra = new Contraindications(patient);
                table = new HashTable(19);

        }

        @Test
        public void testContructorIsValid() {
                assertNotNull(contra);
        }

        @Test
        public void testContructorThrowsOnInvalidInput() {
                // should throw an IllegalArgumentException
                // when the patient input is null
                Exception e = assertThrows(
                                IllegalArgumentException.class,
                                () -> {
                                        new Contraindications(null);
                                });
                assertEquals("The Patient's information is required.", e.getMessage());

                // should throw an IllegalArgumentException when the patient input is invalid

                // DOB is invalid
                Calendar cal = Calendar.getInstance();
                cal.set(1953, Calendar.JUNE, 16);// the dob is incorrect

                patient = new Patient(new PatientIdentity(new Name(
                                "Maria", "Smith"), cal.getTime()));

                // patient is not entered
                e = assertThrows(
                                IllegalArgumentException.class,
                                () -> {
                                        new Contraindications(null);
                                });
                assertEquals("The Patient's information is required.", e.getMessage());

        }

        @Test
        public void testLoadHashTableThrowsonNullInputs() {
                table = new HashTable(19);
                // inputs
                String contraPath = "data/testContra/csv";
                String prescriptionsPath = "data/testPrescriptions.csv";
                PatientsList paList = new PatientsList();
                paList.add(patient);

                // The patients file is null
                Exception e = assertThrows(
                                IllegalArgumentException.class,
                                () -> {
                                        contra.loadHashTable(null, contraPath, prescriptionsPath);
                                });
                assertEquals("The database value does not exist.", e.getMessage());

                // The contraindications file holding all contradictions is null
                e = assertThrows(
                                IllegalArgumentException.class,
                                () -> {
                                        contra.loadHashTable(paList, null, prescriptionsPath);
                                });
                assertEquals("The database value does not exist.", e.getMessage());

                // The prescriptions file is null
                e = assertThrows(
                                IllegalArgumentException.class,
                                () -> {
                                        contra.loadHashTable(paList, null, prescriptionsPath);
                                });
                assertEquals("The database value does not exist.", e.getMessage());

        }

        @Test
        public void loadHasTableReturnsFalse() {
                table = new HashTable(1009);
                // patient does not have any prescription
                Patient patNoPrescriptions = new Patient(new PatientIdentity(
                                new Name("John", "Doe"), cal.getTime()));
                Contraindications contraEmpty = new Contraindications(patNoPrescriptions);

                // adding the patient to our patient list
                paList = new PatientsList();
                paList.add(patNoPrescriptions);

                // this patient will not be included in the prescriptions file
                boolean result1 = contraEmpty.loadHashTable(paList,
                                "data/Contradictions.csv", "data/testPrescriptions.csv");
                assertFalse(result1);

                // patient does not have a matching contraindication to its prescriptions
                boolean result2 = contraEmpty.loadHashTable(paList,
                                "data/Contradictions.csv", "data/unmatchingPrescriptions.csv");
                assertFalse(result2);

                // the prescription file has errors that do not compile well
                paList = new PatientsList();// brand new patients database
                Patient patientWithErrorsInPrescriptions = new Patient(
                                new PatientIdentity(new Name("Maria", "Smith"), cal.getTime()));

                paList.add(patientWithErrorsInPrescriptions);

                boolean result3 = contraEmpty.loadHashTable(paList,
                                "data/Contradictions.csv", "data/testPrescriptionswithIncompletePrescriptions");
                assertFalse(result3);

                // patient does not exist in database
                paList = new PatientsList();// brand new patients database

                // we omit to aff him in the patients list, so he will not be found in the
                // prescriptions file

                boolean result4 = contraEmpty.loadHashTable(paList,
                                "data/Contradictions.csv", "data/testPrescriptions.csv");
                assertFalse(result4);
        }

        @Test
        public void loadHashTableReturnsTrue() {

                table = new HashTable(1009);

                // inputs
                String contraPath = "data/testContra.csv";
                String prescriptionsPath = "data/testPrescriptions.csv";

                paList = new PatientsList();
                paList.add(patient);

                // should work if the table was loaded correctly here.
                assertTrue(contra.loadHashTable(paList, contraPath, prescriptionsPath));
                assertNotNull(contra.getPatientAllInteractions());

        }

        @Test
        public void testGetMedNameThrowsIfNoMedNameAdded() {
                // inputs
                String contraPath = "data/testContra/csv";
                String prescriptionsPath = "data/testPrescriptions.csv";
                paList = new PatientsList();

                // return a null string if loading the table fails due to the patient not being
                // found in the prescriptions file
                Patient patientWillNotLoadHashTable = new Patient(
                                new PatientIdentity(new Name("John", "Doe"), cal.getTime()));
                paList.add(patientWillNotLoadHashTable);
                Exception e = assertThrows(
                                IllegalStateException.class,
                                () -> {
                                        contra.getMedName();
                                });
                assertEquals("No medication name has been added to the contraindications list.", e.getMessage());
                contra = new Contraindications(patient);

                // return a null string if loading the table fails due to unmatching patient
                // information
                // here the patient is found
                // but the last medecine is not added since its not in his chart
                Patient patientWithUnmatchingInfo = new Patient(
                                new PatientIdentity(new Name("Maria", "Smith"), cal.getTime()));
                paList.add(patientWithUnmatchingInfo);
                boolean success = contra.loadHashTable(paList, contraPath, prescriptionsPath);
                assertFalse(success);
                Exception e2 = assertThrows(
                                IllegalStateException.class,
                                () -> {
                                        contra.getMedName();
                                });

                assertEquals("No medication name has been added to the contraindications list.", e2.getMessage());

                // Latly, getMedName() returns a null string if loading the table fails due to
                // the patient not having
                // any prescriptions
                Patient patNoPrescriptions = new Patient(new PatientIdentity(
                                new Name("John", "Doe"), cal.getTime()));
                paList.add(patNoPrescriptions);
                Contraindications contraEmpty = new Contraindications(patNoPrescriptions);
                Exception e3 = assertThrows(
                                IllegalStateException.class,
                                () -> {
                                        contraEmpty.getMedName();
                                });

                assertEquals("No medication name has been added to the contraindications list.", e2.getMessage());

        }

        @Test
        public void testGetNameReturnsMedName() {
                // inputs
                String contraPath = "data/aspirin_warfarin_contra.csv";
                String prescriptionsPath = "data/patient_aspirin.csv";

                paList = new PatientsList();
                paList.add(patient);

                // return the name of the last medecine added to the hash table if the table was
                // loaded successfully
                boolean success = contra.loadHashTable(paList, contraPath, prescriptionsPath);
                assertEquals("aspirin", contra.getMedName());

        }

        @Test
        public void testgetPatientInteraction() {
                // SCENARIO 1: Successful Interaction Storage
                // Setup: Patient takes "Aspirin", Contra file says "Aspirin" interacts with
                // "Warfarin"
                // adding patient to the list
                paList = new PatientsList();
                paList.add(patient);

                // Act
                contra.loadHashTable(paList, "data/aspirin_warfarin_contra.csv", "data/patient_aspirin.csv");

                HashTable resultTable = contra.getPatientAllInteractions();

                // Assert
                assertNotNull(resultTable);
                assertTrue(resultTable.find("aspirin"));
                assertEquals(2, contra.getCount());

                // SCENARIO 2: No Interactions Found
                // Setup: Patient takes many prescriptions, no contraindications exist for it
                Contraindications safeContra = new Contraindications(patient);
                safeContra.loadHashTable(paList, "data/contraEmptyFile.csv", "data/testPrescriptions.csv");

                assertEquals(0, safeContra.getCount());
        }
}
