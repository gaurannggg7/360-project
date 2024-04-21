package healthcare;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;

// Assuming DBUtil and User are defined elsewhere in your package
import actors.*;

public class DoctorDashboard {
    
    public Scene createDoctorDashboardScene(Stage stage, String email) throws SQLException {
        
    	//Initialize user object based on their email
        User currentDoctor = UserManagement.getUserDataByEmail(email);

        BorderPane borderPane = new BorderPane();
        
        // Logout button
        GridPane logOutUserPane = MainScreen.logoutUserButton(stage);

        // Set up sections
        GridPane inboxPane = MessagingDashboard.setupInboxSection(currentDoctor);
        
        GridPane patientRecordsPane = setupPatientRecordsSection(stage);
        
        Button appointmentButton = new Button("Enter Appointment Portal");
        // Assuming you have a method to open the appointment portal
        appointmentButton.setOnAction(event -> showAppointmentPortalScene(stage));

        // Layout configuration
        borderPane.setLeft(inboxPane);
        borderPane.setRight(patientRecordsPane);
        borderPane.setBottom(appointmentButton);
        borderPane.setBottom(logOutUserPane);

        stage.setTitle("Dashboard for Doctor " + currentDoctor.getFirstName());

        return new Scene(borderPane, 600, 400);
    }

    private GridPane setupInboxSection() {
        GridPane inboxPane = new GridPane();
        inboxPane.setPadding(new Insets(20));
        inboxPane.setVgap(10);
        // Add inbox functionality here
        inboxPane.add(new Label("Inbox"), 0, 0);
        // Continue setting up your inbox pane...
        
        return inboxPane;
    }

    private GridPane setupPatientRecordsSection(Stage stage) {
        GridPane patientRecordsPane = new GridPane();
        patientRecordsPane.setPadding(new Insets(20));
        patientRecordsPane.setVgap(10);
        // Set up patient search functionality here
        patientRecordsPane.add(new Label("Search Patient:"), 0, 0);
        // Add more components to patientRecordsPane...

        // Add a button for searching or listing patient records
        Button searchButton = new Button("Search");
        // You need to implement the action for searching patient records
        patientRecordsPane.add(searchButton, 1, 3);
        
        return patientRecordsPane;
    }
    
    
    private static void showAppointmentPortalScene(Stage stage) {
        Scene appointmentPortalScene = createAppointmentPortalScene();
        stage.setScene(appointmentPortalScene);
    }
    
    private static Scene createAppointmentPortalScene() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // Labels and TextFields
        String[] labels = {
            "Name:", "Age:", "Height:", "Weight:", "Temperature:", "Pulse:",
            "Blood Pressure:", "Respiratory Rate:", "Concerns:", "Allergies:",
            "Previous Health Issues:", "Medications Prescribed:", "Immunizations:",
            "Treatment Results:", "Prescriptions Remaining:", "General Summary:"
        };

        for (int i = 0; i < labels.length; i++) {
            grid.add(new Label(labels[i]), 0, i);
            if (i < labels.length - 1) { // For all but "General Summary:"
                grid.add(new TextField(), 1, i);
            }
        }

        // TextArea for General Summary
        TextArea generalSummaryTextArea = new TextArea();
        grid.add(generalSummaryTextArea, 1, labels.length - 1); // Add at the "General Summary" row

        // Button
        Button submitButton = new Button("Submit");
        grid.add(submitButton, 1, labels.length); // Add below the last label

        // Create and return the Scene
        return new Scene(grid, 400, 600);
    }
    // main method removed if not extending Application
}
