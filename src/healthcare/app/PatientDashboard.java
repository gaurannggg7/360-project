package healthcare.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PatientDashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Patient's Dashboard");

        BorderPane borderPane = new BorderPane();

        // Top Section - Inbox
        GridPane inboxPane = new GridPane();
        inboxPane.setPadding(new Insets(20));
        inboxPane.setVgap(10);
        inboxPane.add(new Label("Inbox"), 0, 0);
        Button viewMessagesButton = new Button("View Messages");
        Button sendMessageButton = new Button("Send Message");
        inboxPane.add(viewMessagesButton, 0, 1);
        inboxPane.add(sendMessageButton, 1, 1);

        // Center Section - Most Recent Messages
        TabPane recentMessagesTabPane = new TabPane();
        Tab recentMessagesTab = new Tab("Most Recent Messages");
        recentMessagesTabPane.getTabs().add(recentMessagesTab);

        // Right Section - Contact Information
        Button contactInfoButton = new Button("Contact Information");
        BorderPane.setAlignment(contactInfoButton, javafx.geometry.Pos.BOTTOM_RIGHT);
        borderPane.setRight(contactInfoButton);

        // Bottom Section - Most Recent Visit
        GridPane recentVisitPane = new GridPane();
        recentVisitPane.setPadding(new Insets(20));
        recentVisitPane.setVgap(10);
        recentVisitPane.add(new Label("Most Recent Visit"), 0, 0);
        recentVisitPane.add(new Label("Date:"), 0, 1);
        recentVisitPane.add(new Label("10-04-2024"), 1, 1);
        recentVisitPane.add(new Label("Reason:"), 0, 2);
        recentVisitPane.add(new Label("broken foot"), 1, 2);
        recentVisitPane.add(new Label("Medications Prescribed:"), 0, 3);
        recentVisitPane.add(new Label("fentoxy"), 1, 3);
        recentVisitPane.add(new Label("Doctor's Summary:"), 0, 4);
        recentVisitPane.add(new Label("Patient kicked a bowling ball."), 1, 4);
        Button viewPastVisitsButton = new Button("View Past Visits");
        recentVisitPane.add(viewPastVisitsButton, 0, 5);

        // Adding Sections to BorderPane
        borderPane.setLeft(inboxPane);
       
        borderPane.setCenter(recentVisitPane);

        // Scene Setup
        Scene scene = new Scene(borderPane, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
