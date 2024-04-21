package actors;

import java.time.LocalDate;

public class User {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected LocalDate dateOfBirth;
    protected int uniqueIdentifier;

    public User(String firstName, String lastName, String email, LocalDate dateOfBirth, int uniqueIdentifier) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.uniqueIdentifier = uniqueIdentifier;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public int getUserID() {
        return this.uniqueIdentifier;
    }
    
}