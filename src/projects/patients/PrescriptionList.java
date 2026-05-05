import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PrescriptionList {

    private ListRecord head;
    private int iterator;
    private ListRecord nextRecord = null;

    /**
     * This is the constructor for the PrescriptionList class. 
     * It initializes an empty list by setting the head of the list to null and the iterator index to zero.
     */
    public PrescriptionList() {
        head = null;
        iterator = 0;
    }

    /**
     * This is a private inner class that represents a record in the linked list of prescriptions.
     * Each ListRecord contains a Prescription object (data) and a reference to the next List
     * Record in the list (next). The constructor initializes the data with the given Prescription and sets the next reference to null.
     */
    private class ListRecord {
        public Prescription data;
        public ListRecord next;

        public ListRecord(Prescription pr) {
            data = pr;
            next = null;
        }
    }

    /**
     * This method adds a new prescription to the list in the correct order based on the date and name of the medication.
     * The method first checks if the new prescription is null and returns false if it is.
     * If the list is empty, the new prescription becomes the head of the list. If the new prescription should come before the current head, 
     * it is inserted at the beginning of the list. Otherwise, the method traverses the list to find the correct position for 
     * the new prescription and inserts it there.
     * @param pr - the Prescription to be added to the list
     * @return true if the prescription was successfully added, false if the input prescription is null 
     */
    public boolean add(Prescription pr) {
        if (pr == null)
            return false;
        ListRecord pNew = new ListRecord(pr);

        if (head == null) {
            head = pNew;
        } else if (comesAfter(pNew.data, head.data)) {
            pNew.next = head;
            head = pNew;
        } else {
            ListRecord pBefore = head;
            ListRecord pAfter = head.next;
            while ((pAfter != null) && (!comesAfter(pNew.data, pAfter.data))) {
                pBefore = pAfter;
                pAfter = pAfter.next;
            }
            pNew.next = pAfter;
            pBefore.next = pNew;
        }
        return true;
    }

    /**
     * This method initializes the iterator for the prescription list,
     *  setting it to the starting position (the head of the list) and resetting the iterator index to zero.
     * After calling this method, the next() method will return the first prescription in the list on its next call.
     */
    public void init() {
        iterator = 0;
        nextRecord = head;
    }

    /**
     * This method returns the current position of the iterator, 
     * which indicates how many prescriptions have been returned so far by the next() method.
     * @return the current index of the iterator, representing the number of prescriptions returned so far
     */
    public int iteratorIndex() {
        return iterator;
    }

/**
 * This method returns the next prescription in the list based on the current position of the iterator.
 * If there are no more prescriptions to return (i.e., the end of the list is
 * reached), it returns null. The method does not reset the iterator; 
 * it simply returns the next prescription and advances the iterator for the next call.  
 * @return the next Prescription in the list, or null if there are no more prescriptions to return
 */
    public Prescription next() {
        if (nextRecord == null) {
            return null; // Don't reset 'iterator' here; let init() do it.
        }
        Prescription currentData = nextRecord.data;
        nextRecord = nextRecord.next;
        iterator++;
        return currentData;
    }

    /**
     * This method counts the number of prescriptions in the list by traversing it 
     * from the head to the end, incrementing a counter for each record encountered
     *  It returns the total count of prescriptions in the list.
     * @return the total number of prescriptions in the list
     * 
     */
    public int getCount() {
        int count = 0;
        ListRecord current = head; // Use a local pointer to traverse the list
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    /**
     * This method returns a list of prescriptions assigned to a specific patient.
     * It iterates through the prescription list and checks if each prescription's patient ID 
     * matches the given patient's identity. If a match is found, the prescription is a
     * dded to the assigned list, which is returned at the end.
     * @param pat - the patient for whom we want to find the assigned prescriptions
     * @return a PrescriptionList containing all prescriptions assigned to the given patient
     * 
     */
    public PrescriptionList findPatientList(Patient pat) {
        PrescriptionList assigned = new PrescriptionList();
        if (pat == null)
            return assigned;

        ListRecord current = head;
        while (current != null) {
            if (current.data.getPatientID(current.data) != null &&
                    current.data.getPatientID(current.data).match(pat.getIdentity())) {
                assigned.add(current.data);
            }
            current = current.next;
        }
        return assigned;
    }

    /**
     * Determines the order of prescriptions based on their date and name.
     * Returns true if 'newRecord' should come after 'current' in the list, false otherwise.
     * If the dates are different, the one with the later date comes first. If the dates are the same, the one with the lexicographically smaller name comes first.
     * 
     * @param newRecord - the new prescription to be compared
     * @param current - the current prescription in the list to compare against
     * @return true if 'newRecord' should come after 'current', false otherwise
     * 
     */
    public static boolean comesAfter(Prescription newRecord, Prescription current) {
        if (newRecord.getDate() == null || current.getDate() == null)
            return false;

        if (!newRecord.getDate().equals(current.getDate())) {

            return (newRecord.getDate().after(current.getDate()));
        } else {
            return (newRecord.getName().compareTo(current.getName()) < 0);
        }
    }

    /**
     * This methods reads a prescriptions from a file
     * insert valid ones into a patientList which used a binary search tree structure
     * 
     * @throws IllegalArgumentException if the line doesn't contain enough information to create a prescription
     * 
     * the line should be in the format: "lastName, firstName, DOB, medicineName, dateOfIssue, dosage, prescriber"
     * DOB and dateOfIssue should be in the format "yyyy-MM-dd"
     * the method will ignore any line that doesn't match the expected format or contains invalid data, and it will continue processing the rest of the file.
     * 
     * @param filepath - the string name of the file to be read
     * @param paList - the list of patients to which the prescriptions will be added
     * 
     * @return true if the method succeed
     * false otherwise
     *
     */
    public boolean readPrescriptions(String filepath, PatientsList paList) {
        File file = new File(filepath);
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line != null && !line.trim().isEmpty()) {
                    Prescription pr = Prescription.makePrescription(line, paList);
                    if (pr != null)
                        this.add(pr);
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
