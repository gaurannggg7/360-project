package actors;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

public class Nurse extends User {

    public Nurse(String firstName, String lastName, String email, LocalDate dateOfBirth, int uniqueIdentifier) {
        super(firstName, lastName, email, dateOfBirth, uniqueIdentifier); // Initialize User part of Patient
        
    }
    
}