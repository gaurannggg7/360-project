package healthcare;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

import actors.*;

public class MessagingDashboard {

    public Scene createMessagingScene(Stage stage, User currentUser) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // User email field
        TextField userEmailField = new TextField();
        userEmailField.setPromptText("Enter user's email here...");

        // Message display area
        TextArea messagesArea = new TextArea();
        messagesArea.setEditable(false);
        messagesArea.setPrefHeight(200);

        // Message input field
        TextField messageInput = new TextField();
        messageInput.setPrefWidth(300);
        messageInput.setPromptText("Write a message here...");

        // Send button
        Button sendButton = new Button("Send");

        // Fetch user button
        Button fetchUserButton = new Button("Select Reciever");

        
        // Conversation list
        ListView<User> conversationList = new ListView<>();
        conversationList.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty || user == null ? null : user.getEmail());
            }
        });

        conversationList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displayMessagesForConversation(newValue, currentUser, messagesArea);
        });

        // button to fetch user you want to message
        fetchUserButton.setOnAction(event -> {
            String email = userEmailField.getText();
            if (email.isEmpty()) {
                System.out.println("Please enter an email address.");
                return;
            }
            try {
                User receiver = UserManagement.getUserDataByEmail(email);
                if (receiver != null) {
                    if (!conversationList.getItems().contains(receiver)) {
                        conversationList.getItems().add(receiver);
                    } else {
                        System.out.println("User already in the conversation list.");
                    }
                } else {
                    System.out.println("No user found with this email.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to fetch user data.");
            }
        });


        sendButton.setOnAction(event -> {
            String email = userEmailField.getText();
            String message = messageInput.getText();
            if (!email.isEmpty() && !message.isEmpty()) {
                try {
					sendMessageToUserByEmail(email, message, messagesArea, currentUser);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                messageInput.clear();
            }
        });
        
        
        // setting up the user interface 
        gridPane.add(new Label("User Email"), 0, 0);
        gridPane.add(userEmailField, 1, 0);
        gridPane.add(fetchUserButton, 2, 0);
        gridPane.add(new Label("Conversations"), 0, 1);
        gridPane.add(conversationList, 0, 2, 1, 1);
        gridPane.add(new Label("Messages"), 1, 1);
        gridPane.add(messagesArea, 1, 2, 2, 1);
        gridPane.add(messageInput, 1, 3);
        gridPane.add(sendButton, 2, 3);

        Scene scene = new Scene(gridPane, 600, 400);
        stage.setTitle("Messaging Dashboard");
        stage.setScene(scene);

        return scene;
    }
    
    // method to send a message to a user by the email you selected 
    private void sendMessageToUserByEmail(String email, String message, TextArea messagesArea, User currentUser) throws SQLException {
        User recipient = UserManagement.getUserDataByEmail(email);
        if (recipient != null) {
            MessagingManagement messageSession = new MessagingManagement();
            messageSession.sendMessage(currentUser.getUserID(), recipient.getUserID(), message);
            messagesArea.appendText("To " + email + ": " + message + "\n");
        } else {
            messagesArea.appendText("No user found with email: " + email + "\n");
        }
    }
    
    // method to display different conversations when looking at message
    public void displayMessagesForConversation(User recievingUser, User sendingUser, TextArea messagesArea) {
        if (recievingUser == null) return;
        
        // fetch reciever&sender users role so I can display it in their title like "Doctor Kadn"
        String recievingUserRole = UserManagement.getUserAuthority(recievingUser.getEmail());
        String sendingUserRole = UserManagement.getUserAuthority(sendingUser.getEmail());
        
        // create list and append messages from SQL table using getMessages method
        List<Message> messages = MessagingManagement.getMessages(sendingUser.getUserID(), recievingUser.getUserID());
		messagesArea.clear();
		for (Message message : messages) {
			
			if (sendingUser.getUserID() == message.getSenderId()) {
				messagesArea.appendText(String.format("Me: %s\n", message.getMessageContent()));
			} 
			
			else if (recievingUser.getUserID() == message.getSenderId()) {
				messagesArea.appendText(String.format("%s %s: %s\n", recievingUserRole, recievingUser.getLastName(), message.getMessageContent()));
			}
		}
    }

    
    // global method to display a view inbox button for all user dashboards so they can view message 
    static public GridPane setupInboxSection(User currentPatient) {
        GridPane inboxPane = new GridPane();
        inboxPane.setPadding(new Insets(20));
        inboxPane.setVgap(10);
        inboxPane.add(new Label("Inbox"), 0, 0);
        
        Button viewMessagesButton = new Button("View Messages");
        viewMessagesButton.setOnAction(event -> {
            Stage stage = new Stage();
            MessagingDashboard messagingDashboard = new MessagingDashboard();
            Scene messagingScene = messagingDashboard.createMessagingScene(stage, currentPatient);
            stage.setScene(messagingScene);
            stage.setTitle("Messaging Dashboard");
            stage.show();
        });
        
        
        inboxPane.add(viewMessagesButton, 0, 1);

        return inboxPane; // Correctly return the GridPane object
    }

}
