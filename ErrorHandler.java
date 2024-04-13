package healthcare;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ErrorHandler {

    public static void showErrorDialog(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR); // Create an Alert of type ERROR
        alert.setTitle("Error");
        alert.setHeaderText("Error Message");
        alert.setContentText(errorMessage); // Set the text to display
        alert.showAndWait(); // Show the dialog and wait for the user to close it
    }
    
    public static void showAlertDialog(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION); // Create an Alert of type ERROR
        alert.setTitle("Hello");
        alert.setHeaderText("confirmation Message");
        alert.setContentText(message); // Set the text to display
        alert.showAndWait(); // Show the dialog and wait for the user to close it
    }
}