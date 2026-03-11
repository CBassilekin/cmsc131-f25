
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Patient {
    private PatientIdentity identity;
    public static UUID uuid;
    private static String first, last;
    private static String dobStr = null;
    private static Date date;

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

    public UUID setUUID() {
        UUID patientUUID = null;
        if (uuid == null) {
            patientUUID = UUID.randomUUID();
        } else {
            patientUUID = uuid;
        }
        return patientUUID;
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

    /**
     * This method takes a patient object paramters and turn them into a
     * line should be in this format "Smith, Henry, 1999-10-05,
     * // 6f577751-c766-401f-b733-04561431efc5"
     */
    public String toCSV() {

        String line = null;
        if (getIdentity().getName().getLastName() != null) {
            last = getIdentity().getName().getFirstName();

        }
        if (getIdentity().getName().getFirstName() != null) {
            first = getIdentity().getName().getLastName();
        }

        if (getIdentity().dateFormatter() != null) {
            dobStr = getIdentity().dateFormatter();

        }
        String uuidToString = setUUID().toString();

        line = last + "," + first + "," + dobStr + "," + uuidToString;
        return line;
    }

    /**
     * this method will take a line from a file and build a patient
     * object
     */
    public static Patient makePatient(String line) {
        String[] tokens = line.split(",\\s*"); // Handles optional spaces after commas

        if (tokens.length < 4) {
            throw new IllegalArgumentException("Insufficient information to create a patient");
        }

        // Array indices: 0=Last, 1=First, 2=Date, 3=UUID
        last = tokens[0].trim().toLowerCase();
        first = tokens[1].trim().toLowerCase();
        date = stringToDate(tokens[2].trim());

        // Validate UUID and Date before creating
        if (date != null && isValidUUID(tokens[3].trim())) {
            uuid = UUID.fromString(tokens[3].trim());
            // Assuming PatientIdentity takes (Name, Date) or (Name, Date, UUID)
            return new Patient(new PatientIdentity(new Name(first, last), date));
        }

        throw new IllegalArgumentException("Invalid date or UUID format");
    }

    public static Date stringToDate(String dateStr) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        Date date = null;
        try {
            date = formatter.parse(dateStr);

        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    /**
     * Method is the Regex-based validation for UUID Format.
     * 
     * @param uuidStr -- UUID in a string format
     * @return true if the UUID matches the correct fotmat hexadecimal pattern
     */
    public static boolean validUUID(String uuidStr) {
        if (uuidStr == null) {
            return false;
        }
        String uuidRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-"
                + "[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-"
                + "[0-9a-fA-F]{12}$";
        return uuidStr.matches(uuidRegex);
    }

    /**
     * Validates a UUID string using UUID.fromString().
     * This method ensures the string is in the correct UUID format.
     *
     * @param uuidStr the string to validate
     * @return true if valid UUID, false otherwise
     */
    public static boolean isValidUUID(String uuidStr) {
        if (uuidStr == null) {
            return false;
        }
        try {
            UUID uuid = UUID.fromString(uuidStr);
            // Ensure the string matches the canonical representation
            return uuid.toString().equals(uuidStr.toLowerCase());
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
