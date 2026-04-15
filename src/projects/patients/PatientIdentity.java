
import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientIdentity implements Identity {

    private Date dob; // date of birth in format YYYY-MM-DD
    private Name name; // full name in format "First Last"
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Constructor for PatientIdentity class that takes in a Name object
     * and a Date object as parameters.
     * 
     * @param patientName the Name object representing the patient's name
     * @param patientDob  the Date object representing the patient's date of birth
     * @throws IllegalArgumentException if the patient name is null
     * @throws IllegalArgumentException if the patient date of birth is null or
     *                                  before January 1, 1920
     * 
     */

    public PatientIdentity(Name patientName, Date patientDob) {
        if (patientName == null) {
            throw new IllegalArgumentException("Patient's name cannot be null.");
        } else if (patientDob == null) {
            throw new IllegalArgumentException(
                    "Patient's date of birth cannot be null.");
        }
        name = patientName;
        dob = patientDob;
    }

    /**
     * Getter method for the name of the patient.
     * 
     * @return the Name object representing the patient's name.
     */
    public Name getName() {
        name.getFirstName();
        name.getLastName();
        return name;
    }

    /**
     * Getter method for the date of birth of the patient.
     * 
     * @return the Date object representing the patient's date of birth.
     */
    public Date getDob() {
        return dob;
    }

    /**
     * method formats the date of birth (dob)
     * all dob should match this format yyyy-MM-dd
     * 
     * @return String dobOutput - formatted dob of the patient
     */
    public String dateFormatter(Date dob) {
        String dobOutput = formatter.format(dob);
        return dobOutput;
    }

    /**
     * Method to compare the current PatientIdentity object with another
     * PatientIdentity
     * object for a match based on name and date of birth.
     * 
     * @param other - the PatientIdentity object to compare with the current
     *              PatientIdentity object.
     * @return true if the name and date of birth of both PatientIdentity objects
     *         match, false otherwise.
     * 
     */
    public boolean match(Identity other) {
        if (other == null) {
            throw new IllegalArgumentException("Other PatientIdentity cannot be null.");
        }
        PatientIdentity otherPatient = (PatientIdentity) other;
        return (name.match(otherPatient.getName())
                && dateFormatter(dob).equals(dateFormatter((otherPatient.getDob()))));
    }

    /**
     * Method to compare the current PatientIdentity object
     * with another PatientIdentity object to determine
     * 
     * 
     * @param other - the PatientIdentity object to compare with the current
     *              PatientIdentity object.
     * @return true if the current PatientIdentity object is less than the other
     *         PatientIdentity object
     *         based on name and date of birth, false otherwise.
     * 
     */
    public boolean isLessThan(Identity other) {
        if (other == null) {
            throw new IllegalArgumentException("Other PatientIdentity cannot be null.");
        }
        PatientIdentity otherPatient = (PatientIdentity) other;
        // Returns true if name is less, OR if names match and DOB is earlier
        return (name.isLessThan(otherPatient.name)) || (name.match(otherPatient.name) && dob.before(otherPatient.dob));
    }

    public String identityToString() {

        return "name: " + name.toString() + " dob: " + dateFormatter(dob);
    }
}