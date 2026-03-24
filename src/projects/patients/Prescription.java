
import java.util.Date;

public class Prescription {

    private String medName;
    private Date issuedOn;
    private int medDosage;
    private String medPrescriber;
    private PatientIdentity ID = null;
    private static PatientIdentity matchingPatient = null;

    public Prescription(String name, Date date, int dosage, String prescriber) {
        if (name != null) {
            medName = name;
        } else {
            throw new IllegalArgumentException("Medication's name cannot be null");
        }
        if (date != null) {
            issuedOn = date;
        } else {
            throw new IllegalArgumentException("Medication's issue date cannot be null");
        }
        if (dosage > 0) {
            medDosage = dosage;
        } else {
            throw new IllegalArgumentException("Dosage value is too small");
        }
        if (prescriber != null) {
            medPrescriber = prescriber;
        } else {
            throw new IllegalArgumentException("Prescriber's name cannot be null");
        }
    }

    public Date getDate() {
        return issuedOn;
    }

    public String getName() {
        return medName;
    }

    public String getPrescriber() {
        return medPrescriber;
    }

    public int getDosage() {
        return medDosage;
    }

    /**
     * Method make a new prescription from a line using token split
     * 
     * @param line - provided line input from the file coming in the format
     *             patient_name, patient_dob, medicine_name, date_of_issue, dosage,
     *             prescriber
     * @return a new Prescription object
     */

    public static Prescription makePrescription(String line, Patient[] patArray) {
        String lastName = null;
        String firstName = null;
        Date patientDOB = null;
        String medecineName = null;
        Date dateOfIssue = null;
        int dosage = 0;
        String prescriber = null;
        Prescription pr = null;

        if (line == null) {
            throw new IllegalArgumentException("line must not be null.");
        } else {

            String[] tokens = line.split(",\\s*"); // Handles optional spaces after commas

            if (tokens.length < 7) {
                throw new IllegalArgumentException(
                        "Insufficient information for a prescription line");
            }

            // Array indices: 0=name, 1=date, 2=dosage, 3=prescriber
            lastName = tokens[0].trim();
            firstName = tokens[1].trim();
            patientDOB = Patient.stringToDate(tokens[2].trim());
            medecineName = tokens[3].trim();
            dateOfIssue = Patient.stringToDate(tokens[4].trim());
            dosage = Integer.valueOf(tokens[5].trim());
            prescriber = tokens[6].trim();
            if ((lastName != null) && (firstName != null) && (patientDOB != null) &&
                    (medecineName != null) && (dosage != 0) & (dateOfIssue != null)) {
                // only valid old year old or less would be created, ignoring older
                // prescriptions
                pr = new Prescription(medecineName, dateOfIssue, dosage, prescriber);
                Name name = new Name(firstName, lastName);

                // ID = new PatientIdentity(name, patientDOB);
                pr.ID = new PatientIdentity(name, patientDOB);
                if (pr.matchPatientFromList(pr, patArray)) {
                    pr.ID = matchingPatient;
                }

            }
            return pr;
        }
    }

    /*
     * this method returns a patient's Identity from its prescription
     * we added a code to avoid date formatting mismatch when referring to
     * the same patient so PR.ID will always point to the existing patient DOB.
     */
    public boolean matchPatientFromList(Prescription pr, Patient[] listArray) {

        for (int i = 0; i < listArray.length; i++) {
            matchingPatient = listArray[i].getIdentity();
            if (pr.ID.match(matchingPatient)) {
                pr.ID = matchingPatient;
                return true;
            }
        }
        return false;
    }

    public PatientIdentity getPatientID(Prescription pr) {
        return pr.ID;
    }

}
