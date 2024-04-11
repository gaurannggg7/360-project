


package healthcare.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AppointmentPortalUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Appointment Portal");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // Labels
        grid.add(new Label("Name:"), 0, 0);
        grid.add(new Label("Age:"), 0, 1);
        grid.add(new Label("Height:"), 0, 2);
        grid.add(new Label("Weight:"), 0, 3);
        grid.add(new Label("Temperature:"), 0, 4);
        grid.add(new Label("Pulse:"), 0, 5);
        grid.add(new Label("Blood Pressure:"), 0, 6);
        grid.add(new Label("Respiratory Rate:"), 0, 7);
        grid.add(new Label("Concerns:"), 0, 8);
        grid.add(new Label("Allergies:"), 0, 9);
        grid.add(new Label("Previous Health Issues:"), 0, 10);
        grid.add(new Label("Medications Prescribed:"), 0, 11);
        grid.add(new Label("Immunizations:"), 0, 12);
        grid.add(new Label("Treatment Results:"), 0, 13);
        grid.add(new Label("Prescriptions Remaining:"), 0, 14);
        grid.add(new Label("General Summary:"), 0, 15);

        // TextFields
        for (int i = 0; i < 15; i++) {
            grid.add(new TextField(), 1, i);
        }

        // TextArea for General Summary
        TextArea generalSummaryTextArea = new TextArea();
        grid.add(generalSummaryTextArea, 1, 15);

        // Button
        Button submitButton = new Button("Submit");
        grid.add(submitButton, 1, 16);

        Scene scene = new Scene(grid, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
