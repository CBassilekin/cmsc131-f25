package projects.patients;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PatientIdentity {

    private Date dob; // date of birth in format YYYY-MM-DD
    private Name name; // full name in format "First Last"

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

    public PatientIdentity(Name patientName, LocalDate patientDob) {
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
     * Date of birth of the patient in format YYYY-MM-DD.
     * 
     * @return the LocalDate object representing the patient's date of birth.
     */
    public String getFormattedDobString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dob.format(formatter);
    }

    /**
     * Getter method for the date of birth of the patient.
     * 
     * @return the Date object representing the patient's date of birth.
     */
    public Date getDob() {
        dob = Date.getFormattedDobString();
        return dob;
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
    public boolean match(PatientIdentity other) {
        if (other == null) {
            throw new IllegalArgumentException("Other PatientIdentity cannot be null.");
        }
        return (name.match(other.getName()) && dob.equals(other.getDob()));
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
    public boolean isLessThan(PatientIdentity other) {
        if (other == null) {
            throw new IllegalArgumentException("Other PatientIdentity cannot be null.");
        }
        // Returns true if name is less, OR if names match and DOB is earlier
        return (name.isLessThan(other.name)) || (name.match(other.name) && dob.isBefore(other.dob));
    }

    public String toString() {
        return "name: " + name.toString() + " dob: " + dob.toString();
    }
}
