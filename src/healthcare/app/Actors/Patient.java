package healthcare.app.Actors;

import java.util.HashMap;
import java.util.Map;

public class Patient {
    protected String firstName;
    protected String lastName;
    protected String dateOfBirth; 
    private String uniqueIdentifier;
    public PatientProfile patientProfile;
    private Map<Integer, Prescription> prescription;

    public Patient(String firstName, String lastName, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.uniqueIdentifier = uniqueIdentifier;
        this.patientProfile = new PatientProfile(); // Initialize with an empty profile
    }

    // Access the patient profile
    public PatientProfile accessProfile() {
        return patientProfile;
    }

    // Edit the patient profile
    public void editProfile(PatientProfile updatedProfile) {
        this.patientProfile = updatedProfile;
    }

    // Send a message (simplified version)
    public void sendMessage(String message) {
        // Implementation: This could be sending a message to the healthcare provider
        System.out.println("Message sent: " + message);
    }

    // Create a profile - might be similar or identical to editing a profile
    public void createProfile(PatientProfile profile) {
        this.patientProfile = profile;
    }

    // View patient history - for simplicity, just a print statement here
    public void viewHistory() {
        // Implementation: Display or return the patient's history
        System.out.println("Viewing patient history for " + this.firstName + " " + this.lastName);
    }

    public static class PatientProfile {
        // Example attributes
        private String medicalHistory;
        private String allergies;

    }

}
