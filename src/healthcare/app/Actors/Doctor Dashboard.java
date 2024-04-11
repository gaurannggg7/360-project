package healthcare.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Doctor Dashboard");

        BorderPane borderPane = new BorderPane();

        // Inbox Section
        GridPane inboxPane = new GridPane();
        inboxPane.setPadding(new Insets(20));
        inboxPane.setVgap(10);
        inboxPane.add(new Label("Inbox"), 0, 0);
        // Add inbox functionality here

        // Patient Records Section
        GridPane patientRecordsPane = new GridPane();
        patientRecordsPane.setPadding(new Insets(20));
        patientRecordsPane.setVgap(10);
        Label patientLabel = new Label("Search Patient:");
        TextField patientNameField = new TextField();
        TextField dobField = new TextField();
        Button searchButton = new Button("Search");
        patientRecordsPane.add(patientLabel, 0, 0);
        patientRecordsPane.add(new Label("Name:"), 0, 1);
        patientRecordsPane.add(patientNameField, 1, 1);
        patientRecordsPane.add(new Label("Date of Birth:"), 0, 2);
        patientRecordsPane.add(dobField, 1, 2);
        patientRecordsPane.add(searchButton, 1, 3);
     

        // Add patient records functionality here

        // Appointment Section
        Button appointmentButton = new Button("Enter Appointment");
        appointmentButton.setOnAction(event -> openAppointmentPortal( primaryStage));
        // Add appointment functionality here
        

        borderPane.setLeft(inboxPane);
        borderPane.setRight(patientRecordsPane);
        borderPane.setCenter(appointmentButton);

        Scene scene = new Scene(borderPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    

    private void openAppointmentPortal(Stage primaryStage) {
        AppointmentPortalUI appointmentPortal = new AppointmentPortalUI();
        appointmentPortal.start(new Stage());
    }


    public static void main(String[] args) {
        launch(args);
    }
}


