package projects.hospital;

public class Name {
    private String firstName;
    private String lastName;

    /**
     * Constructor for Name class that takes in first and last name as parameters.
     * 
     * @param first - the first name of the patient
     * @param last  - the last name of the patient
     *              Any name that does not meet these criteria will result in an
     *              IllegalArgumentException being thrown.
     * @throws IllegalArgumentException if the first name is null or empty
     * @throws IllegalArgumentException if the last name is null or empty
     */
    public Name(String first, String last) {
        if (first == null || last == null) {

            throw new IllegalArgumentException(
                    "Any part of the name cannot be null or empty");
        } else {
            firstName = first;
            lastName = last;
        }

    }

    /**
     * Getter method for the first name of the patient.
     * 
     * @return the first name of the patient
     * 
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter method for the last name of the patient.
     * 
     * @return the last name of the patient
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method to compare two Name objects for equality, ignoring case sensitivity.
     * 
     * @param other - the Name object to compare with the current Name object
     * @return true if the first and last names of both Name objects match (ignoring
     *         case),
     *         false otherwise
     * 
     */
    public boolean match(Name other) {

        if (other != null) {

            String firstToLowercase = other.getFirstName().toLowerCase();
            String lastToLowercase = other.getLastName().toLowerCase();

            if (firstName.toLowerCase().equals(firstToLowercase)
                    && lastName.toLowerCase().equals(lastToLowercase)) {
                return true;
            } else {
                return false;
            }
        } else {
            throw new IllegalArgumentException("Cannot compare with null Name object.");
        }
    }

    /**
     * Method to compare two Name objects for ordering, ignoring case sensitivity.
     * The method first compares the last names of the two Name objects.
     * If the last name of the current Name object comes before the last name of the
     * other
     * Name object in alphabetical order (ignoring case), then the method returns
     * true.
     * If the last names are the same (ignoring case), then the method compares the
     * first names of the two Name objects.
     * If the first name of the current Name object comes before the first name of
     * the other Name object
     * in alphabetical order (ignoring case), then the method returns true. If both
     * the last names and first names
     * of the two Name objects are the same (ignoring case), then the method returns
     * false.
     * 
     * @param other - the Name object to compare with the current Name object
     * @return true if the current Name object comes before the other Name object in
     *         alphabetical order (ignoring case), false otherwise
     * @return false if both the last names and first names of the two Name objects
     *         are the same (ignoring case)
     * 
     */
    public boolean isLessThan(Name other) {
        if (other == null) {
            throw new IllegalArgumentException("Other name cannot be null");
        } else if (this.match(other)) {
            return false;
        } else {
            int lastNameComparison = lastName.toLowerCase().compareTo(other.lastName.toLowerCase());
            if (lastNameComparison < 0) {
                return true;
            } else if (lastNameComparison == 0) {
                return firstName.toLowerCase().compareTo(other.firstName.toLowerCase()) < 0;
            } else {
                return false;
            }
        }
    }

    /**
     * Method to return a string representation of the Name object in the format
     * "firstName lastName".
     * 
     * @return a string representation of the Name object
     *
     */
    public String toString() {
        return firstName + " " + lastName;
    }
}
