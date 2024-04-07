package healthcare.app;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

// import user classes
import healthcare.app.Actors.*;

public class MainScreen extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    private Scene mainScene;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Doctor's Office");

        ToggleGroup group = new ToggleGroup();

        RadioButton rbPatient = new RadioButton("Patient");
        rbPatient.setUserData("Patient");
        rbPatient.setToggleGroup(group);
        rbPatient.setSelected(true);

        RadioButton rbNurse = new RadioButton("Nurse");
        rbNurse.setUserData("Nurse");
        rbNurse.setToggleGroup(group);

        RadioButton rbDoctor = new RadioButton("Doctor");
        rbDoctor.setUserData("Doctor");
        rbDoctor.setToggleGroup(group);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField staffIdField = new TextField();
        staffIdField.setPromptText("Staff ID");

        Button btnLogin = new Button("Sign In");
        // Add action for btnLogin depending on the selection
        Button btnCreateAcc = new Button("Create Account");
        
        btnCreateAcc.setOnAction(e -> showPatientRegistrationPage(primaryStage));

        VBox layout = new VBox(10, rbPatient, rbNurse, rbDoctor, usernameField, 
                passwordField, staffIdField, btnLogin, btnCreateAcc);
        layout.setAlignment(Pos.CENTER);

        // Initially hide staffIdField and show username/password fields
        staffIdField.setVisible(false);

        group.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
            if (newVal.getUserData().equals("Patient")) {
                usernameField.setVisible(true);
                passwordField.setVisible(true);
                staffIdField.setVisible(false);
                btnCreateAcc.setVisible(true);
            } else {
                usernameField.setVisible(false);
                passwordField.setVisible(false);
                staffIdField.setVisible(true);
                btnCreateAcc.setVisible(false);
            }
        });

        Scene scene = new Scene(layout, 300, 250);
        this.mainScene = scene;
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
    }
    
    private void showPatientRegistrationPage(Stage primaryStage) {
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Enter your first name");
        
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Enter your last name");
        
        Label dobLabel = new Label("Date of Birth:");
        TextField dobField = new TextField();
        
        Label passLabel = new Label("Password:");
        TextField passField = new TextField();
        passField.setPromptText("");
        
        Label passConfirmLabel = new Label("Confirm Password:");
        TextField passConfirmField = new TextField();
        passConfirmField.setPromptText("");
        
     
        Button btnSubmit = new Button("Submit");
        btnSubmit.setOnAction(e -> {
        	String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String dob = dobField.getText();
            String password = passField.getText();

            Patient patient = new Patient(firstName, lastName, dob);
            
        });
    }
        
        private void showPatientDashBoard(Stage primaryStage) {
            Label firstNameLabel = new Label("First Name:");
            TextField firstNameField = new TextField();
            firstNameField.setPromptText("Enter your first name");
            
            Label lastNameLabel = new Label("Last Name:");
            TextField lastNameField = new TextField();
            lastNameField.setPromptText("Enter your last name");
            
            Label dobLabel = new Label("Date of Birth:");
            TextField dobField = new TextField();
            
            Label passLabel = new Label("Password:");
            TextField passField = new TextField();
            passField.setPromptText("");
            
            Label passConfirmLabel = new Label("Confirm Password:");
            TextField passConfirmField = new TextField();
            passConfirmField.setPromptText("");
            
         
            Button btnSubmit = new Button("Submit");
            btnSubmit.setOnAction(e -> {
            	String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String dob = dobField.getText();
                String password = passField.getText();

                Patient patient = new Patient(firstName, lastName, dob);
                
            });
        
        
        
        
        
        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> primaryStage.setScene(mainScene)); // Go back to the main scene

        VBox registrationLayout = new VBox(10, firstNameLabel, firstNameField, lastNameLabel, lastNameField, 
        		dobLabel, dobField, passLabel, passField, passConfirmLabel, passConfirmField, btnSubmit, btnBack);
        registrationLayout.setAlignment(Pos.CENTER);
        
        Scene registrationScene = new Scene(registrationLayout, 300, 250);
        
        primaryStage.setScene(registrationScene);
    }

	private LocalDate LocalDate() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
