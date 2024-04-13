package healthcare;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
//import bcrypt library to hash/salt passwords
import java.time.LocalDate;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public class MainScreen extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    private Scene mainScene;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Doctor's Office");

        TextField userEmailField = new TextField();
        userEmailField.setPromptText("Email");

        PasswordField userPasswordField = new PasswordField();
        userPasswordField.setPromptText("Password");

        TextField staffIdField = new TextField();
        staffIdField.setPromptText("Staff ID");
        
        
        //Logic for patient logging in 
        Button btnLogin = new Button("Login");
        btnLogin.setOnAction(e -> {
        	String userEmail = userEmailField.getText();
        	
            // Check if log in password matches email when the button is clicked
            if (UserManagement.verifyUserPassword(userEmail, userPasswordField.getText())) {
            	
            	/* String sessionId = SessionManager.createSession(userEmailField.getText());
            	 * 	 
            	System.out.print(sessionId);
            	*/
             // Retrieve user authority after successful login
                String userRole = UserManagement.getUserAuthority(userEmail);
                
                // Decide which dashboard to show based on user authority
                switch (userRole) {
                
                
                    case "Patient":
                    try {
                    	PatientDashboard patientDashboard = new PatientDashboard();
			            Scene patientScene = patientDashboard.createPatientDashboardScene(primaryStage, userEmail); // Pass the primary stage to the method
			            primaryStage.setScene(patientScene);
			            primaryStage.show(); // Make sure to show the primaryStage if it's not already visible
                    } catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
					}
						break;
                        
                        
                    case "Doctor":
                	try {
						DoctorDashboard doctorDashboard = new DoctorDashboard();
			            Scene doctorScene = doctorDashboard.createDoctorDashboardScene(primaryStage, userEmail); // Pass the primary stage to the method
			            primaryStage.setScene(doctorScene);
			            primaryStage.show(); // Make sure to show the primaryStage if it's not already visible
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
                		break;
                        
                        
                    case "Nurse":
					try {
						NurseDashboard nurseDashboard = new NurseDashboard();
			            Scene nurseScene = nurseDashboard.createNurseDashboardScene(primaryStage, userEmail); // Pass the primary stage to the method
			            primaryStage.setScene(nurseScene);
			            primaryStage.show(); // Make sure to show the primaryStage if it's not already visible
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                        break;
                    default:
                        // Handle unknown role or show error
                        System.out.println("Unknown role: " + userRole);
                        break;
                }
            } 
            else {
            	ErrorHandler.showErrorDialog("Error, review information");
            }
        });
       
        
        //Logic for patient registering 
        Button btnCreateAcc = new Button("Create Account");
        btnCreateAcc.setOnAction(e -> showPatientRegistrationPage(primaryStage));

        
        VBox layout = new VBox(10, userEmailField, 
                userPasswordField, staffIdField, btnLogin, btnCreateAcc);
        layout.setAlignment(Pos.CENTER);

  
        // Initially hide staffIdField and show username/password fields
        staffIdField.setVisible(false);

    
        Scene scene = new Scene(layout, 300, 250);
        this.mainScene = scene;
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    private void showPatientRegistrationPage(Stage primaryStage) {
        // Labels and fields for the registration form
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Enter your first name");

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Enter your last name");
        
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Insert email@domain:");

        Label dobLabel = new Label("Date of Birth (YYYY-MM-DD):"); // Improved label to include format
        TextField dobField = new TextField();
        dobField.setPromptText("YYYY-MM-DD");

        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField(); // Changed to PasswordField for security
        passField.setPromptText("Password");

        Label passConfirmLabel = new Label("Confirm Password:");
        PasswordField passConfirmField = new PasswordField(); // Changed to PasswordField
        passConfirmField.setPromptText("Confirm Password");
        
        
        Button btnSubmit = new Button("Submit");
        btnSubmit.setOnAction(e -> {
        	//check if the pass matches the confirmed pass
        	if (passField.getText().equals(passConfirmField.getText())) {
        		
	        	//hash and salt pass before storing in DB if passwords match
	            String hashedPassword = BCrypt.hashpw(passField.getText(), BCrypt.gensalt(12));
	            		
        		
        		ErrorHandler.showAlertDialog("Thank you! Your registration was successful.");
	            
	        	//insert user with insert user function from 
	        	UserManagement.insertUser("Patient", firstNameField.getText(), lastNameField.getText(),
	        			emailField.getText(), LocalDate.parse(dobField.getText()), hashedPassword); 
        	}
        	else {
        		ErrorHandler.showErrorDialog("Error, review information");
        	}
        });

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> primaryStage.setScene(mainScene)); // Go back to the main scene

        VBox registrationLayout = new VBox(10, firstNameLabel, firstNameField, lastNameLabel, lastNameField, 
        		emailLabel, emailField, dobLabel, dobField, passLabel, passField, passConfirmLabel, 
        		passConfirmField, btnSubmit, btnBack);
        
        registrationLayout.setAlignment(Pos.CENTER);

        Scene registrationScene = new Scene(registrationLayout, 600, 600); // Adjusted size to fit all elements
        
        primaryStage.setScene(registrationScene);
    }
    
    
    // Test dashboards
    /* 
    
    
	} 
	
	*/
	
	//Messaging system implementation, PROBLEM: needs to take User objects id not email to register sent message
	
	/*
	 
	
	
	*/
}
