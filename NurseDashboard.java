package healthcare;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;

import actors.*;

public class NurseDashboard {

    public Scene createNurseDashboardScene(Stage stage, String email) throws SQLException {
    	
    	//Initialize user object based on their email
    	User currentNurse = UserManagement.getUserDataByEmail(email);
    	
        BorderPane borderPane = new BorderPane();
        
        // Logout button
        GridPane logOutUserPane = MainScreen.logoutUserButton(stage);
        
        // Gridpane section setup for user interface
        GridPane inboxPane = MessagingDashboard.setupInboxSection(currentNurse);
        GridPane patientRecords = setupPatientRecordsSection();

        borderPane.setLeft(inboxPane);
        borderPane.setRight(patientRecords);
        borderPane.setBottom(logOutUserPane);
        
        stage.setTitle("Dashboard for " + currentNurse.getFirstName());

        return new Scene(borderPane, 500, 500); // Adjust size as necessary
    }
    
    private GridPane setupPatientRecordsSection() {
    	GridPane patientRecords = new GridPane();
        patientRecords.setPadding(new Insets(10, 10, 10, 10));
        patientRecords.setVgap(10);
        patientRecords.setHgap(10);
        patientRecords.setAlignment(Pos.CENTER_RIGHT);

        TextField firstNameField = new TextField();
        patientRecords.add(new Label("Patient Records"), 0, 0);
        patientRecords.add(new Label("First:"), 0, 1);
        patientRecords.add(firstNameField, 1, 1); // Add this line to actually add firstNameField to the GridPane

        TextField lastNameField = new TextField();
        patientRecords.add(new Label("Last:"), 0, 2);
        patientRecords.add(lastNameField, 1, 2);

        TextField dobField = new TextField();
        patientRecords.add(new Label("Date of Birth (YYYY-MM-DD):"), 0, 3);
        patientRecords.add(dobField, 1, 3); // Make sure this is dobField
        

        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> UserManagement.searchPatientRecords(firstNameField.getText(), 
        		lastNameField.getText(), dobField.getText()));
        
        patientRecords.add(searchButton, 1, 4);

        return patientRecords;
    }

}