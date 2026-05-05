
import java.util.Date;

public class Prescription {

    private String medName;
    private Date issuedOn;
    private int medDosage;
    private String medPrescriber;
    public PatientIdentity ID = null;
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

    /**
     * This is the getetr method for the prescription's date.
     */
    public Date getDate() {
        return issuedOn;
    }

/**
 * this is the getter method for the medications's name
 */
    public String getName() {
        return medName;
    }

    /**
     * This is the getter method for the prescriber's name.
     */
    public String getPrescriber() {
        return medPrescriber;
    }

    /** this is the getetr method for the medication's dosage.
     * 
     */
    public int getDosage() {
        return medDosage;
    }

    /**
     * Method makes a new prescription from a line using token split
     * 
     * @param line - provided line input from the file coming in the format
     *             patient_name, patient_dob, medicine_name, date_of_issue, dosage,
     *             prescriber
     * @return a new Prescription object
     */

    public static Prescription makePrescription(String line, PatientsList patList) {
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

                // Create the Identity to be matched with the existing patient list
                PatientIdentity searchID = new PatientIdentity(name, patientDOB);

                // Look for that patient in the patients List
                Patient foundPatient = patList.find(searchID);

                if (foundPatient != null) {
                    pr.ID = foundPatient.getIdentity();
                } else {
                    // if not found, we simply keep that info
                    pr.ID = searchID;
                }

            }
            return pr;
        }
    }
/**
 * This method returns the Patient ID related to a specific prescription.
 */
    public PatientIdentity getPatientID(Prescription pr) {

        return pr.ID;

    }

    /*
     * this method returns a patient's Identity from its prescription
     * we added a code to avoid date formatting mismatch when referring to
     * the same patient so PR.ID will always point to the existing patient DOB.
     */
    public boolean matchPatientFromList(PatientsList list, Prescription pr) {
        if (list == null || this.ID == null) {
            return false;
        }
        // Use the list provided in the argument to find the ID
        return list.find(this.ID) != null;
    }
}
