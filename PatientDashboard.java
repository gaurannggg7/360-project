package healthcare.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;

import healthcare.app.Actors.*;

// Notice we're not extending Application anymore
public class PatientDashboard {

    // This method now accepts a Stage and configures it directly
    public Scene createPatientDashboardScene(Stage stage, String email) throws SQLException {
    	
    	User currentPatient = DBUtil.getUserDataByEmail(email);
    	
        // Configuration of the BorderPane, inbox, and patientRecords remains the same
        BorderPane borderPane = new BorderPane();
        
        GridPane inbox = setupInboxSection();
        borderPane.setLeft(inbox);
        
        // Assuming nurseName is fetched from elsewhere or set as a class variable
        String nurseName = "Nurse Name"; // Placeholder for dynamic nurse name fetching
        stage.setTitle("Dashboard for " + currentPatient.getFirstName());

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

}