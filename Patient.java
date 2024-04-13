package actors;

import java.util.Map;
import java.time.LocalDate;

public class Patient extends User {

    public Patient(String firstName, String lastName, String email, LocalDate dateOfBirth, String uniqueIdentifier) {
        super(firstName, lastName, email, dateOfBirth, uniqueIdentifier); // Initialize User part of Patient
       
    }
       
}

