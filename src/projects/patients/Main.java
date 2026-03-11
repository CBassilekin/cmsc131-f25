
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        // phase1(); // setting the Patient
        phase2();

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

        PatientIdentity patientIdentity1 = new PatientIdentity(name1, new Date(1 - 1 - 2000));
        PatientIdentity patientIdentity2 = new PatientIdentity(name2, new Date(1 - 1 - 2000));
        PatientIdentity patientIdentity3 = new PatientIdentity(name3, new Date(1 - 1 - 2000));
        System.out.println(patientIdentity1.match(patientIdentity2)); // should print true
        System.out.println(patientIdentity1.match(patientIdentity3)); // should print false
        System.out.println(patientIdentity1.isLessThan(patientIdentity3)); // should print true
        System.out.println(patientIdentity3.isLessThan(patientIdentity1)); // should print false
        System.out.println(patientIdentity1.toString()); // should print the patient's identity information
        System.out.println(patientIdentity2.toString()); // should print the patient's identity information
        System.out.println(patientIdentity3.toString()); // should print the patient's identity information

        Patient patient1 = new Patient(new PatientIdentity(name1, new Date(1 - 1 - 2000)));
        Patient patient2 = new Patient(new PatientIdentity(name2, new Date(1 - 1 - 2000)));
        Patient patient3 = new Patient(new PatientIdentity(name3, new Date(1 - 1 - 2000)));
        System.out.println(patient1.match(patient2)); // should print true
        System.out.println(patient1.match(patient3)); // should print false
        System.out.println(patient1.toString()); // should print the patient's identity information
        System.out.println(patient2.toString()); // should print the patient's identity information
        System.out.println(patient3.toString()); // should print the patient's identity information

    }

    public static void phase2() {

        PatientsList list = new PatientsList();
        list.importFromFile();
        list.saveToFile("data/input.csv");
    }
}
