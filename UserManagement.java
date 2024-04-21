package healthcare;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import actors.*;

public class UserManagement {
	
	
	public static void insertUser(String authority, String firstName, 
			String lastName, String email, LocalDate dob, String password) {
		
	    String query = "INSERT INTO users (Authority, Firstname, Lastname, Email, DOB, Password) VALUES (?, ?, ?, ?, ?, ?)";
	
	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	    	
	    	stmt.setString(1, authority);
	        stmt.setString(2, firstName);
	        stmt.setString(3, lastName);
	        stmt.setString(4, email);
	        stmt.setDate(5, java.sql.Date.valueOf(dob)); // Convert LocalDate to sql.Date
	        stmt.setString(6, password); // Use the hashed password here
	
	        stmt.executeUpdate();
	        System.out.println("User registered successfully.");
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Not registered");
	        // Handle exceptions properly
	    }
	}
	
	public static boolean verifyUserPassword(String email, String password) {
		
        // User credentials
        String userEmail = email;
        String passwordToVerify = password;

        try {
            // Connect to the database
            Connection conn = DatabaseUtil.getConnection();
            
            // Query to find the user by email
            String query = "SELECT password FROM users WHERE email = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, userEmail);
            
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                // Get the stored hashed password from the database
                String storedHashedPassword = resultSet.getString("password");
                
                // Compare the provided password to the hashed password
                boolean doesMatch = BCrypt.checkpw(passwordToVerify, storedHashedPassword);
                
                if(doesMatch) {
                    System.out.println("Login successful.");         
                    return true;
                } 
                else {
                    System.out.println("Login failed: Incorrect password.");
                }
            } 
            else {
                System.out.println("Login failed: User not found.");
            }
            
            // Close connections
            resultSet.close();
            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            
        }
		return false;
    }
	
	public static String getUserAuthority(String email) {
	    String authority = null;

	    try {
	        Connection conn = DatabaseUtil.getConnection();
	        
	        String query = "SELECT authority FROM users WHERE email = ?";
	        PreparedStatement statement = conn.prepareStatement(query);
	        statement.setString(1, email);
	        
	        ResultSet resultSet = statement.executeQuery();
	        
	        // Check if the ResultSet has at least one row
	        if (resultSet.next()) {
	            authority = resultSet.getString("authority");
	        } else {
	            // Handle the case where no matching users are found
	            System.out.println("No user found with the email: " + email);
	        }
	        
	        resultSet.close();
	        statement.close();
	        conn.close(); // Don't forget to close the connection
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }

	    return authority;
	}
	

	public static User getUserDataByEmail(String email) throws SQLException {
		
	    String userAuthority = getUserAuthority(email); // Assume this method returns a string like "Doctor", "Patient", etc.
	    User user = null;

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement statement = conn.prepareStatement("SELECT * FROM users WHERE email = ?")) {

	        statement.setString(1, email);
	        
	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                String firstName = resultSet.getString("firstname");
	                String lastName = resultSet.getString("lastname");
	                java.sql.Date dbSqlDate = resultSet.getDate("dob");
	                LocalDate dob = null;
	                if (dbSqlDate != null) {
	                    dob = dbSqlDate.toLocalDate();
	                }
	                int userID = resultSet.getInt("id");

	                switch(userAuthority) {
	                    case "Patient":
	                        user = new Patient(firstName, lastName, email, dob, userID);
	                        break;
	                    case "Nurse":
	                        user = new Nurse(firstName, lastName, email, dob, userID);
	                        
	                    case "Doctor":
	                    	user = new Doctor(firstName, lastName, email, dob, userID); // Assuming Doctor has a similar constructor
	                        // Add more cases for other user types
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    }
	    return user;
	}
	
	public static void searchPatientRecords(String firstName, String lastName, String dobString) {
		
	    LocalDate dob;
	    try {
	        dob = LocalDate.parse(dobString, DateTimeFormatter.ISO_LOCAL_DATE);
	    } catch (DateTimeParseException e) {
	        System.out.println("Date format is incorrect. Please use YYYY-MM-DD.");
	        return;
	    }
	    
	    // Convert LocalDate to java.sql.Date
	    Date sqlDob = Date.valueOf(dob);
	    
	    String query = "SELECT * FROM users WHERE Firstname = ? AND Lastname = ? AND dob = ?";

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        
	        stmt.setString(1, firstName);
	        stmt.setString(2, lastName);
	        stmt.setDate(3, sqlDob);
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	            boolean found = false;
	            while (rs.next()) {
	                // Assuming your DB columns match these names
	                String patientInfo = rs.getString("Firstname") + " " +
	                                     rs.getString("Lastname") + ", " +
	                                     rs.getDate("dob");
	                System.out.println(patientInfo);
	                found = true;
	            }
	            if (!found) {
	                System.out.println("No records found.");
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	//System implementation for messaging 
	/*
   	
   	public static List<String> getStaffList() {
   		String dbUrl = "jdbc:mysql://localhost/CSE360?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
	    String dbUser = "root";
	    String dbPassword = "Football1?";
   		
	   	 List<String> emails = new ArrayList<>();
	     String sql = "SELECT email FROM users WHERE authority = 'nurse' OR authority = 'doctor'";
	
	     try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
	          PreparedStatement stmt = conn.prepareStatement(sql);
	          ResultSet rs = stmt.executeQuery()) {
	    	 
	         while (rs.next()) {
	             emails.add(rs.getString("email"));
	         }
	
	     } catch (Exception e) {
	         e.printStackTrace(); // Consider more robust error handling for your application
	     }
	     
	     return emails;
	 }
   	
   	public static List<String> fetchMessagesForPatient(String patientEmail) {
   		String dbUrl = "jdbc:mysql://localhost/CSE360?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
	    String dbUser = "root";
	    String dbPassword = "Football1?";
	    
        List<String> messages = new ArrayList<>();
        String sql = "SELECT sender_id, message_text, timestamp FROM messages WHERE receiver_id = ? ORDER BY timestamp DESC";
        
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, patientEmail);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String sender = rs.getString("sender");
                String messageText = rs.getString("message_text");
                String timestamp = rs.getTimestamp("timestamp").toString();
                String message = String.format("%s (%s): %s", sender, timestamp, messageText);
                messages.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return messages;
    }
   	
   	public static void sendMessage(String senderEmail, String recipientEmail, String messageText) {
   		String dbUrl = "jdbc:mysql://localhost/CSE360?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
	    String dbUser = "root";
	    String dbPassword = "Football1?";
	    
   	    // SQL statement to insert the new message into the database
   	    String sql = "INSERT INTO messages (sender_id, receiver_id, message_text) VALUES (?, ?, ?)";
   	    try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
   	         PreparedStatement stmt = conn.prepareStatement(sql)) {
   	        
   	        stmt.setString(1, senderEmail);
   	        stmt.setString(2, recipientEmail);
   	        stmt.setString(3, messageText);
   	        stmt.executeUpdate();
   	        
   	        System.out.println("Message sent and stored in database.");
   	    } catch (Exception e) {
   	        e.printStackTrace();
   	    }
   	}
   	
   	*/

 }


