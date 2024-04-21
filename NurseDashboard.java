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

// Notice we're not extending Application anymore
public class NurseDashboard {

    // This method now accepts a Stage and configures it directly
    public Scene createNurseDashboardScene(Stage stage, String email) throws SQLException {
    	
    	//Initialize user object based on their email
    	User currentNurse = UserManagement.getUserDataByEmail(email);
    	
        BorderPane borderPane = new BorderPane();
        
        // Inbox section setup
        GridPane inboxPane = MessagingDashboard.setupInboxSection(currentNurse);

        // Patient record section setup
        GridPane patientRecords = setupPatientRecordsSection();

        borderPane.setLeft(inboxPane);
        borderPane.setRight(patientRecords);

        // Assuming nurseName is fetched from elsewhere or set as a class variable
        String nurseName = "Nurse Name"; // Placeholder for dynamic nurse name fetching
        stage.setTitle("Dashboard for " + currentNurse.getFirstName());

        return new Scene(borderPane, 500, 500); // Adjust size as necessary
    }

    private GridPane setupInboxSection() {
    	GridPane inbox = new GridPane();
        inbox.setPadding(new Insets(10, 10, 10, 10));
        inbox.setVgap(10);
        inbox.setHgap(10);
        inbox.setAlignment(Pos.CENTER_LEFT);

        Button viewMsgBtn = new Button("View Messages");
        Button sendMsgBtn = new Button("Send Message");

        inbox.add(new Label("Inbox"), 0, 0);
        inbox.add(viewMsgBtn, 0, 1);
        inbox.add(sendMsgBtn, 0, 2);

        return inbox;
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