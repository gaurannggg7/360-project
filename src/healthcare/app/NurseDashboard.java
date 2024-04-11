package cse360.group;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class NurseDashboard extends Application {

    @Override
    public void start(Stage nurseStage) {
        nurseStage.setTitle("Nurse Dashboard");

        BorderPane borderPane = new BorderPane();

        //Inbox section on the left side of the nurse dashboard
        GridPane inbox = new GridPane();
        inbox.setAlignment(Pos.TOP_LEFT);
        inbox.setVgap(10);
        inbox.setHgap(10);
        inbox.setPadding(new Insets(10,10,10,10));

        //inbox label
        Label inboxLabel = new Label("Inbox");
        inbox.add(inboxLabel, 0, 7);

        //inbox buttons
        Button viewMsgBtn = new Button("View Messages");
        viewMsgBtn.setOnAction(actionEvent -> switch_to_inbox_view());
        Button sendMsgBtn = new Button ("Send Message");
        //sendMsgBtn.setOnAction(actionEvent -> sendMessage());
        inbox.add(viewMsgBtn, 0, 8);
        inbox.add(sendMsgBtn, 0, 9);

        //Patient records section on the right
        GridPane patientRecords = new GridPane();
        patientRecords.setAlignment(Pos.TOP_LEFT);
        patientRecords.setPadding(new Insets(10,10,10,10));
        patientRecords.setVgap(10);
        patientRecords.setHgap(10);

        //Patient records labels and text fields
        Label patientRecordsLabel = new Label("Patient Records");
        patientRecords.add(patientRecordsLabel, 0, 7);
        patientRecords.add(new Label("First:"), 0, 8);
        patientRecords.add(new TextField(), 1, 8);
        patientRecords.add(new Label("Last:"), 0, 9);
        patientRecords.add(new TextField(), 1, 9);
        patientRecords.add(new Label("Birthday:"), 0, 10);
        patientRecords.add(new TextField(), 1, 10);
        patientRecords.add(new Button("Search"), 1, 11);

        //Main grid, used to help with alignment of the two sections (inbox, patient records)
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.add(inbox, 0, 1);
        grid.add(patientRecords, 1, 1);

        //Used to get information from the text file
        String nurseID;
        //get nurse name from the first line from text file, the two lines below this comment are written to obtain the nurse's name from the first line of the text file containing the nurse information
        //List<String> nurseInfoLines = Files.readAllLines(Paths.get(nurseID+"NurseInfo.txt"));
        //String nurseName = nurseInfoLines.get(0).split(":")[1].trim();

        //label at top of dashboard
        Label dashboardLabel = new Label("Dashboard for Nurse " /* + nurseName */);
        dashboardLabel.setAlignment(Pos.CENTER);
        grid.add(dashboardLabel, 0,0);

        HBox headerBox = new HBox(dashboardLabel);
        headerBox.setAlignment(Pos.CENTER);

        borderPane.setTop(headerBox);
        borderPane.setCenter(grid);


        // Set the scene and stage
        Scene scene = new Scene(borderPane, 600, 400); // Adjust size as needed
        nurseStage.setScene(scene);
        nurseStage.show();
    }

    private void switch_to_inbox_view()
    {
        Inbox newInbox = new Inbox(this, nurseName);
        main_Layout.getChildren().clear();
        main_Layout.getChildren().add(newInbox);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
