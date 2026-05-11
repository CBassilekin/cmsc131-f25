
import java.util.Date;
import java.util.Calendar;

public class Main {

    private static PatientsList paList;
    private static PrescriptionList prList;
    private static String contra = "data/Contraindicatioons.csv"
    private static String pr = "data/prescriptions1000.csv"

    public static void main(String[] args) {
        // phase1(); // setting the Patient
        // phase2();
        // phase3();
        phase4();

    }

    public static void phase1() {
        Name name1 = new Name("John", "Smith");
        Name name2 = new Name("John", "Smith");
        Name name3 = new Name("Jane", "Smith");

        System.out.println(name1.match(name2)); // should print true
        System.out.println(name1.match(name3)); // should print false
        System.out.println(name1.isLessThan(name2));// should print false
        System.out.println(name1.isLessThan(name3)); // should print true
        System.out.println(name3.isLessThan(name1)); // should print false
        System.out.println(name1.toString()); // should print the patient's name information
        System.out.println(name2.toString()); // should print the patient's name information
        System.out.println(name3.toString()); // should print the patient's name information

        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1);
        Date dateOfBirth = calendar.getTime();
        PatientIdentity patientIdentity1 = new PatientIdentity(name1, dateOfBirth);
        PatientIdentity patientIdentity2 = new PatientIdentity(name2, dateOfBirth);
        PatientIdentity patientIdentity3 = new PatientIdentity(name3, dateOfBirth);
        System.out.println(patientIdentity1.match(patientIdentity2)); // should print true
        System.out.println(patientIdentity1.match(patientIdentity3)); // should print false
        System.out.println(patientIdentity1.isLessThan(patientIdentity3)); // should print true
        System.out.println(patientIdentity3.isLessThan(patientIdentity1)); // should print false
        System.out.println(patientIdentity1.toString()); // should print the patient's identity information
        System.out.println(patientIdentity2.toString()); // should print the patient's identity information
        System.out.println(patientIdentity3.toString()); // should print the patient's identity information

        Patient patient1 = new Patient(new PatientIdentity(name1, dateOfBirth));
        Patient patient2 = new Patient(new PatientIdentity(name2, dateOfBirth));
        Patient patient3 = new Patient(new PatientIdentity(name3, dateOfBirth));
        System.out.println(patient1.match(patient2)); // should print true
        System.out.println(patient1.match(patient3)); // should print false
        System.out.println(patient1.toString()); // should print the patient's identity information
        System.out.println(patient2.toString()); // should print the patient's identity information
        System.out.println(patient3.toString()); // should print the patient's identity information

    }

    // uploading the list of patients from the file and saving it to another file,
    // to check if the import and export methods are working properly
    public static void phase2() {

        paList = new PatientsList();

        System.out.println(paList.importFromFile("data/patients1000.csv"));
        System.out.println(paList.saveToFile("data/patients_out.csv"));
    }

    // uploading the list of prescriptions from the file and saving it to another
    // file,
    // to check if the import and export methods are working properly
    public static void phase3() {
        prList = new PrescriptionList();

        System.out.println(prList.readPrescriptions("data/prescriptions1000.csv", paList));

        Patient pat = paList.next();
        PrescriptionList patPr = prList.findPatientList(pat);
        System.out.println(patPr.getCount());

        patPr.init();
        System.out.println(patPr.next());
        System.out.println(patPr.next());
        System.out.println(patPr.next());
        System.out.println(patPr.next());
        System.out.println(patPr.next());
        System.out.println(patPr.next());

    }

    // Adding Stack + a tree Structure to the project.
    public static void phase4() {
        paList = new PatientsList();

        Calendar cal = Calendar.getInstance();
        cal.set(1990, Calendar.JANUARY, 1);
        Date dob1 = cal.getTime();
        Name name1 = new Name("John", "Doe");
        PatientIdentity patientIdentity = new PatientIdentity(name1, dob1);

        paList.add(new Patient(patientIdentity));

        // name of patient2 is different from that of patient1, but the date of birth is
        // the same as that of patient1.
        Name name2 = new Name("Jane", "Doe");
        cal.set(1990, Calendar.JANUARY, 1);
        Date dob2 = cal.getTime();
        PatientIdentity patientIdentity2 = new PatientIdentity(name2, dob2);

        paList.add(new Patient(patientIdentity2));

        // let's check what order they are in the list
        paList.initIterator();
        System.out.println(paList.next().toString());
        System.out.println(paList.next().toString());

        // lets find an existing patient in the list
        PatientIdentity searchIdentity = new PatientIdentity(new Name("John", "Doe"), cal.getTime());
        Patient foundPatient = paList.find(searchIdentity);
        System.out.println(foundPatient.toString());

        // lets find a non existing patient in the list
        PatientIdentity searchIdentity2 = new PatientIdentity(new Name("Alice", "Smith"), cal.getTime());
        Patient foundPatient2 = paList.find(searchIdentity2);
        System.out.println(foundPatient2);

        // lets count the patients in the list using the iterator
        paList.initIterator();
        System.out.println("Number of patients: " + paList.getNumPatients());

    }
}
