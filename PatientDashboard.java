package healthcare;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import actors.*;

public class PatientDashboard {

    public Scene createPatientDashboardScene(Stage stage, String email) throws SQLException {
        User currentPatient = UserManagement.getUserDataByEmail(email);
        
        BorderPane borderPane = new BorderPane();
        
        // Logout button
        GridPane logOutUserPane = MainScreen.logoutUserButton(stage);
        
        // Left Section - Inbox
        GridPane inboxPane = MessagingDashboard.setupInboxSection(currentPatient);
        
        // Center Section - Most Recent Visit
        GridPane recentVisitPane = setupRecentVisitSection();
        
        // Right Section - Contact Information
        VBox contactInfoBox = setupContactInfoSection();
        
        // Top Section - Recent Messages TabPane
        TabPane recentMessagesTabPane = setupRecentMessagesSection();

        borderPane.setLeft(inboxPane);
        borderPane.setCenter(recentVisitPane);
        borderPane.setRight(contactInfoBox);
        borderPane.setTop(recentMessagesTabPane);
        borderPane.setBottom(logOutUserPane);

        stage.setTitle("Dashboard for " + currentPatient.getFirstName());

        return new Scene(borderPane, 1000, 600); // Adjust size as necessary
    }

    private GridPane setupRecentVisitSection() {
        GridPane recentVisitPane = new GridPane();
        recentVisitPane.setPadding(new Insets(20));
        recentVisitPane.setVgap(10);
        recentVisitPane.add(new Label("Most Recent Visit"), 0, 0);
        recentVisitPane.add(new Label("Date:"), 0, 1);
        recentVisitPane.add(new Label("10-04-2024"), 1, 1);
        recentVisitPane.add(new Label("Reason:"), 0, 2);
        recentVisitPane.add(new Label("Broken Foot"), 1, 2);
        recentVisitPane.add(new Label("Medications Prescribed:"), 0, 3);
        recentVisitPane.add(new Label("Fentoxy"), 1, 3);
        recentVisitPane.add(new Label("Doctor's Summary:"), 0, 4);
        recentVisitPane.add(new Label("Patient kicked a bowling ball."), 1, 4);
        Button viewPastVisitsButton = new Button("View Past Visits");
        recentVisitPane.add(viewPastVisitsButton, 0, 5);

        return recentVisitPane;
    }

    private VBox setupContactInfoSection() {
        VBox contactInfoBox = new VBox(10);
        contactInfoBox.setPadding(new Insets(20));
        contactInfoBox.getChildren().add(new Button("Contact Information"));

        return contactInfoBox;
    }

    private TabPane setupRecentMessagesSection() {
        TabPane recentMessagesTabPane = new TabPane();
        Tab recentMessagesTab = new Tab("Most Recent Messages");
        // Ideally, you would set the content of this tab as needed
        recentMessagesTabPane.getTabs().add(recentMessagesTab);
        recentMessagesTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        return recentMessagesTabPane;
    }
}