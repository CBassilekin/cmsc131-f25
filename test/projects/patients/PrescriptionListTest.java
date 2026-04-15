import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PrescriptionListTest {

        private int iterator;
        private PrescriptionList test;
        private Prescription pr1;
        private Prescription pr2;
        private Prescription pr3;
        private Prescription pr4;
        private Calendar Cal = Calendar.getInstance();;
        private Date issue1;
        private Date issue2;
        private Date issue3;
        private Date issue4;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        @BeforeEach
        public void SetUp() {

                Cal.set(2006, Calendar.DECEMBER, 30);
                issue3 = Cal.getTime();
                pr3 = new Prescription(
                                "alphadril", issue3, 500, "Dior");

        }

        /**
         * this test confirms that a empty list is successfully created
         */
        @Test
        public void testConstructorValidity() {
                test = new PrescriptionList();

                assertNotNull(test);
        }

        @Test
        public void testAdd() {
                test = new PrescriptionList();

                Cal.set(2021, Calendar.JUNE, 3);
                issue1 = Cal.getTime();
                pr1 = new Prescription(
                                "phytochlor", issue1, 250, "Ali");

                // 1. the list is empty then get 1 item count after add()
                test.add(pr1);
                test.init();
                assertEquals(test.getCount(test), 1);

                // 2. the new record becomes the new head
                Cal.set(2023, Calendar.JULY, 3);
                issue2 = Cal.getTime();
                pr2 = new Prescription(
                                "cetaphime", issue2, 50, "Dior");

                // let's add it to the list
                test.add(pr2);
                test.init();
                assertEquals(test.getCount(test), 2);

                // checking if new Record matches head
                test.init(); // this puts the pointer to the head
                Prescription actualResult = test.next(); // head
                Prescription expectedResult = pr2; // most recently added record
                assertEquals(formatter.format(actualResult.getDate()), formatter.format(expectedResult.getDate()));
                assertEquals((actualResult.getName()), (expectedResult.getName()));
                assertEquals((actualResult.getPrescriber()), (expectedResult.getPrescriber()));
                assertEquals((actualResult.getDosage()), (expectedResult.getDosage()));

                // 3. the new Record is inserted between two records in the list
                Cal.set(2021, Calendar.DECEMBER, 30);
                issue3 = Cal.getTime();
                pr3 = new Prescription(
                                "alphadril", issue3, 500, "Dior");

                // let's add it to the list
                test.add(pr3);
                test.init();
                assertEquals(test.getCount(test), 3);

                // checking if new Record is in between head (pBefore) and pAfter
                test.init(); // this puts the pointer to the head
                test.next(); // should point to the head (pBefore)
                actualResult = test.next(); // should match pr3 which we will check
                test.next(); // should point to pAfter
                expectedResult = pr3;

                // checking that actualResult matches pr3
                assertEquals(formatter.format(actualResult.getDate()), formatter.format(expectedResult.getDate()));
                assertEquals((actualResult.getName()), (expectedResult.getName()));
                assertEquals((actualResult.getPrescriber()), (expectedResult.getPrescriber()));
                assertEquals((actualResult.getDosage()), (expectedResult.getDosage()));

                // 4. the new record is appended to the list
                Cal.set(2020, Calendar.DECEMBER, 30);
                issue4 = Cal.getTime();
                pr4 = new Prescription(
                                "alphadril", issue4, 500, "Dior");

                // let's add it to the list
                test.add(pr4);
                test.init();
                assertEquals(test.getCount(test), 4);

                // checking if new Record is at the end of the list before null
                test.init(); // this puts the pointer to the head
                test.next(); // should point to the head (pBefore)
                test.next();
                test.next();
                actualResult = test.next(); // should point to pr4.
                expectedResult = pr4;

                // the following record should be a null
                assertNull(test.next());

                // checking that actualResult matches pr4
                assertEquals(formatter.format(actualResult.getDate()), formatter.format(expectedResult.getDate()));
                assertEquals((actualResult.getName()), (expectedResult.getName()));
                assertEquals((actualResult.getPrescriber()), (expectedResult.getPrescriber()));
                assertEquals((actualResult.getDosage()), (expectedResult.getDosage()));
        }

        @Test
        public void testIteratorIndex() {
                test = new PrescriptionList();

                assertEquals(test.iteratorIndex(), 0); // should be 0 as the list is empty

                Cal.set(2021, Calendar.JUNE, 3);
                issue1 = Cal.getTime();
                pr1 = new Prescription(
                                "phytochlor", issue1, 250, "Ali");
                test.add(pr1);

                // checking that we can now iterate over at list with 1 record
                test.init();
                test.next();
                assertEquals(test.iteratorIndex(), 1);

                // the iteraror reinitializes at the end of the iteration
                test.next();
                assertEquals(test.iteratorIndex(), 0);
        }

        @Test
        public void testInit() {
                test = new PrescriptionList();

                assertEquals(test.iteratorIndex(), 0); // should be 0 as the list is empty

                Cal.set(2021, Calendar.JUNE, 3);
                issue1 = Cal.getTime();
                pr1 = new Prescription(
                                "phytochlor", issue1, 250, "Ali");
                test.add(pr1); // this record becomes the head of the list

                // before starting the iteration
                test.init();
                assertEquals(iterator, 0);

                Prescription expectedResult = pr1;
                Prescription actualResult = test.next();

                /// checking that nextRecord points to the head
                assertEquals(formatter.format(actualResult.getDate()), formatter.format(expectedResult.getDate()));
                assertEquals((actualResult.getName()), (expectedResult.getName()));
                assertEquals((actualResult.getPrescriber()), (expectedResult.getPrescriber()));
                assertEquals((actualResult.getDosage()), (expectedResult.getDosage()));

        }

        @Test
        public void testNextReturnsNull() {
                // we have used next() throughout our test units so far to iterate
                // over the list successfully. So this may be repetitive.
                test = new PrescriptionList();

                // for an empty list
                assertNull(test.next());

                // when the iteration gets to the end of the list
                Cal.set(2021, Calendar.JUNE, 3);
                issue1 = Cal.getTime();
                pr1 = new Prescription(
                                "phytochlor", issue1, 250, "Ali");
                test.add(pr1); // this record becomes the head of the list

                test.init();
                test.next();

                // at the end of the list, next() points to a null
                assertNull(test.next());
        }

        @Test
        public void testNextIsSuccessful() {

                // we have used next() throughout our test units so far to iterate
                // over the list successfully. So this may be repetitive.

                test = new PrescriptionList();

                // when the iteration gets to the end of the list
                Cal.set(2021, Calendar.JUNE, 3);
                issue1 = Cal.getTime();
                pr1 = new Prescription(
                                "phytochlor", issue1, 250, "Ali");
                Cal.set(2020, Calendar.DECEMBER, 30);
                issue4 = Cal.getTime();
                pr2 = new Prescription(
                                "alphadril", issue4, 500, "Dior");

                test.add(pr1);
                test.add(pr2);

                test.init();
                assertNotNull(test.next());
                assertNotNull(test.next());

        }

        @Test
        public void testfindPatientListReturnsANonEmptyList() {
                test = new PrescriptionList();

                // when the iteration gets to the end of the list
                Cal.set(2021, Calendar.JUNE, 3);
                issue1 = Cal.getTime();
                pr1 = new Prescription(
                                "phytochlor", issue1, 250, "Ali");

                Cal.set(2020, Calendar.DECEMBER, 30);
                issue4 = Cal.getTime();
                pr2 = new Prescription(
                                "alphadril", issue4, 500, "Dior");

                Cal.set(2001, Calendar.JUNE, 3);
                Date dob = Cal.getTime();
                // let's find this existing patient's prescription list
                Patient pat = new Patient(new PatientIdentity(
                                new Name("John", "Doe"), dob));

                //PatientsList patList = new PatientsList();
                Patient[] patArray = new Patient[] { new Patient(new PatientIdentity(
                                new Name("John", "Doe"), dob)) };

                // adding the patient to the patients list via file import
                test.readPrescriptions("data/findPatientListPrescriptions.csv", test, patArray);

                // Findind this patients'related prescriptions
                PrescriptionList assigned = test.findPatientList(pat, patArray);
                assigned.init();

                // getting the count
                assertEquals(assigned.getCount(assigned ), 2);
        }

        /**
         * this test verifies that a patient that may not exist in the list will
         * nopt have any prescription record
         */
        @Test
        public void testfindPatientListReturnsAnEmptyList() {
                test = new PrescriptionList();

                // when the iteration gets to the end of the list
                Cal.set(2021, Calendar.JUNE, 3);
                issue1 = Cal.getTime();
                pr1 = new Prescription(
                                "phytochlor", issue1, 250, "Ali");

                Cal.set(2020, Calendar.DECEMBER, 30);
                issue2 = Cal.getTime();
                pr2 = new Prescription(
                                "alphadril", issue2, 500, "Dior");

                Cal.set(2001, Calendar.JUNE, 3);
                Date dob = Cal.getTime();
                // let's find this non- existing patient's prescription list
                Patient pat = new Patient(new PatientIdentity(
                                new Name("Jasper", "Doe"), dob));

               // PatientsList patList = new PatientsList();
                Patient[] patArray = new Patient[] { new Patient(new PatientIdentity(
                                new Name("Jasper", "Doe"), dob)) };

                // adding the patient to the patients list via file import
                test.readPrescriptions("data/findPatientListPrescriptions.csv", test, patArray);

                // Findind this patients'related prescriptions
                PrescriptionList assigned = test.findPatientList(pat, patArray);
                assigned.init();

                // getting the count
                assertEquals(assigned.getCount(assigned ), 0);
        }

        /**
         * this method compares two prescriptions and should return true of the first
         * one is more
         * recent than the second one
         * a corner case may arise of they jhave the same date of issue. then
         * we will compare then aphabetically
         */
        @Test
        public void testComesAfter() {
                Cal.set(2021, Calendar.JUNE, 3);
                issue1 = Cal.getTime();
                pr1 = new Prescription(
                                "phytochlor", issue1, 250, "Ali");

                Cal.set(2020, Calendar.DECEMBER, 30);
                issue4 = Cal.getTime();
                pr2 = new Prescription(
                                "alphadril", issue4, 500, "Dior");

                // 2 prescriptions are clearly distant in time
                assertTrue(PrescriptionList.comesAfter(pr1, pr2));
                assertFalse(PrescriptionList.comesAfter(pr2, pr1));

                // 2pescriptions have the same issue date

                issue4 = Cal.getTime();
                pr2 = new Prescription(
                                "alphadril", issue1, 500, "Dior");
                assertFalse(PrescriptionList.comesAfter(pr1, pr2));
                assertTrue(PrescriptionList.comesAfter(pr2, pr1));
        }

        /**
         * this test verifies that the correct count of prescriptions is returned.
         */
        @Test
        public void testGetCount() {

                // we have used this method many times within the other test units so it works!
                // setting up a new list

                PrescriptionList list = new PrescriptionList();
                // An empty list should have a count of 0.
                assertEquals(list.getCount(list), 0);

                // setting up the list
                PatientsList patList = new PatientsList();
                Cal.set(2001, Calendar.JUNE, 3);
                Date dob = Cal.getTime();
                Patient[] patArray = new Patient[] { new Patient(new PatientIdentity(
                                new Name("John", "Doe"), dob)) };

                list.readPrescriptions("data/findPatientListPrescriptions.csv", list, patArray);

                // this patient has a 2 prescriptions in the list
                list.init();
                assertEquals(list.getCount(list), 2);

                // Now, working on a very long list with 1000 patients;
                list = new PrescriptionList();
                patList = new PatientsList();
                patArray = new Patient[1000];
                patList.importFromFile("data/testInput.csv", patArray);
                list.readPrescriptions("data/testPrescriptions.csv", list, patArray);

                // let's find out how many prescription there in the list

                list.init();
                assertEquals(list.getCount(list), 3695);

        }

        @Test
        public void testReadPrescriptionsReturnsTrue() {
                PrescriptionList list = new PrescriptionList();

                PatientsList newList = new PatientsList();
                Patient[] testList = new Patient[1000];

                // building our patients database
                assertTrue(newList.importFromFile("data/testInput.csv", testList));

                // file exists, a prescription can be created & it matches an existing patient
                assertTrue(list.readPrescriptions("data/testPrescriptions.csv", list, testList));
                list.init();
                assertEquals(list.getCount(list), 3695);
        }

        @Test
        public void testReadPrescriptionsReturnsFalse() {
                PrescriptionList list = new PrescriptionList();
               // PatientsList newList = new PatientsList();
                Patient[] testList = new Patient[1000];
                // file does not exist
                assertFalse(list.readPrescriptions(
                                "date/inexistentFilePath.csv", list, testList));

        }

        @Test
        public void testReadPrescriptionsReturnsDoesNotAddAPrescription() {
                PrescriptionList list = new PrescriptionList();
                PatientsList newList = new PatientsList();
                Patient[] testList = new Patient[1000];

                // building our patients database
                assertTrue(newList.importFromFile("data/testInput.csv", testList));

                // file exists, prescriptions can be created but linedoes not match any existing
                // patient, the method will still read, however no prescription will be added
                // to the precriptions list.

                assertTrue(list.readPrescriptions(
                                "data/testPrescriptionsUnmatch.csv", list, testList));
                list.init();
                assertEquals(list.getCount(list), 0);
        }

        @Test
        public void testReadPrecriptionsThrowsonNullPrescription() {

                PrescriptionList list = new PrescriptionList();
                //PatientsList newList = new PatientsList();
                Patient[] testList = new Patient[1000];

                // file exists, but prescriptions can't be created

                Exception e = assertThrows(
                                IllegalArgumentException.class,
                                () -> {
                                        list.readPrescriptions(
                                                        "data/testPrescriptionswithIncompletePrescriptions.csv", list,
                                                        testList);
                                });
                assertEquals("Insufficient information for a prescription line", e.getMessage());

        }
}