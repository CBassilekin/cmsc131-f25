
import java.lang.reflect.Method;
import java.util.Date;

public class Prescription {

    private String medName;
    private Date issuedOn;
    private int medDosage;
    private String medPrescriber;
    public PatientIdentity ID = null;
    private static PatientIdentity matchingPatient = null;

    public Prescription(String name, Date date, int dosage, String prescriber) {
        if (name != null && prescriber != null) {// we will allow the creation of prescriptions with missing date,
                                                 // dosage but not with missing medication name as it's the key for
                                                 // interactions mapping
            medName = name;
            issuedOn = date;
            medDosage = dosage;
            medPrescriber = prescriber;

        } else {
            throw new IllegalArgumentException("Medication's or prescriber's name cannot be null.");
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

    /**
     * this is the getetr method for the medication's dosage.
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

            String[] tokens = line.split("[,\\s]+"); // Handles optional spaces after commas

            if (tokens.length < 2) { // We need at least the medication name and prescriber to create a prescription
                throw new IllegalArgumentException(
                        "Insufficient information for a prescription line");
            }

            if (tokens.length == 2) { // If only medication name and prescriber are provided, we can still create a
                                      // prescription
                medecineName = tokens[0].trim();
                prescriber = tokens[1].trim();
                pr = new Prescription(medecineName, null, 0, prescriber);
                return pr;
            } else if (tokens.length >= 2) { // If there are more than 2 tokens, we have all the information of a
                                             // prescription

                // Array indices: 0=name, 1=date, 2=dosage, 3=prescriber
                if (tokens[0] != null) {
                    lastName = tokens[0].trim();
                }
                if (tokens[1] != null) {
                    firstName = tokens[1].trim();
                }
                if (tokens[2] != null) {
                    patientDOB = Patient.stringToDate(tokens[2].trim());
                }
                if (tokens[3] != null) {
                    medecineName = tokens[3].trim();
                }
                if (tokens[4] != null) {
                    dateOfIssue = Patient.stringToDate(tokens[4].trim());
                }
                if (tokens[5] != null) {
                    dosage = Integer.valueOf(tokens[5].trim());
                }
                if (tokens[6] != null) {
                    prescriber = tokens[6].trim();
                }
                if ((medecineName != null) && (prescriber != null)) { // allows the creation of prescriptions
                    // with missing date, dosage but not with missing medication name as it's the
                    // key for interactions mapping
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

    /**
     * Method makes a new prescription from a line using token split
     * 
     * @param line - provided line input from the file coming in the format
     *             patient_name, patient_dob, medicine_name, date_of_issue, dosage,
     *             prescriber
     * @return a new Prescription object
     */

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
