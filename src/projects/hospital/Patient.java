package projects.hospital;

public class Patient {
    private PatientIdentity identity;

    public Patient(PatientIdentity id) {
        if (id == null) {
            throw new IllegalArgumentException("Patient identity cannot be null");
        }
        identity = id;
    }

    /**
     * Getter method for the identity of the patient.
     * 
     * @return the PatientIdentity object representing the patient's identity.
     * 
     */
    public PatientIdentity getIdentity() {
        return identity;
    }

    /**
     * Method to compare the current Patient object with another Patient object for
     * a
     * match based on their identities.
     * 
     * @return a boolean value indicating whether the two Patient objects match
     *         based on their identities.
     * 
     */
    public boolean match(Patient other) {
        return identity.match(other.getIdentity());
    }

    /**
     * Method to return a string representation of the Patient object.
     * 
     * @return a string representation of the Patient object
     * 
     */
    public String toString() {
        return "identity: " + identity.toString();
    }

}
