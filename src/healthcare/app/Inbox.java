//change to your package name
package cse360hw4;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ScrollPane;

import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.ArrayList;

public class Inbox extends BorderPane
{
	
	private String owner;
	private String recipient;
	private ArrayList<String[]> messages;
	private ArrayList<String> contactList;
	private ArrayList<Label> texts;

	private TextField msgField;
	private Button sendButton;
	private Button backButton;
	private Button addContact;
	private Label inboxTitle;
	private TextField newContact;
	
	private VBox contactListButtons;
	private HBox back_title_labels;
	private VBox messagesPane;
	private ScrollPane container;
	private VBox allMessages;
	private HBox write_send_messages;
	private HBox main_tasks;
	private VBox bottom;
	
	private Main mainApp; 
	
	public Inbox(Main mainApp, String usr) 
    {
		//reset the text file message database
		//this.fresh_text_file("messages.txt");
		
		//initialize variables
		this.owner = usr;
		this.recipient = "";
		this.messages = new ArrayList<String[]>();
		this.contactList = new ArrayList<String>();
		this.texts = new ArrayList<Label>();
		
		//initialize javafx vars
		this.msgField = new TextField();
		this.sendButton = new Button("Send");
		this.backButton = new Button("Back");
		this.addContact = new Button("Add Contact");
		this.inboxTitle = new Label("\t\t"+usr+"'s Inbox");
		this.newContact = new TextField();
		
		this.contactListButtons = new VBox();
		this.back_title_labels = new HBox();
		this.messagesPane = new VBox();
		this.container = new ScrollPane();
		this.allMessages = new VBox();
		this.bottom = new VBox();
		
		this.write_send_messages = new HBox();
		this.main_tasks = new HBox();
		
		this.mainApp = mainApp;
		
		this.newContact.setPrefSize(25, 25);
		
		//build the top layout
		this.back_title_labels.getChildren().addAll(this.backButton,this.inboxTitle);
		
		this.contactListButtons.setPrefWidth(100);
		this.messagesPane.setPrefWidth(100);
		this.messagesPane.setPrefWidth(300);

		
		//this.sendMessage("messages.txt", "jenna|justin|hi");
		//build center layout
				//add all contacts to the contacts list, 
				//	create buttons for them, 
				//		add buttons to contact list buttons
		if(this.getAllMsgs_first("messages.txt")) {
			for(int i=0;i<this.messages.size();i++) {
				String temp1 = this.messages.get(i)[0];
				String temp2 = this.messages.get(i)[1];
	
				if(!temp1.equals(this.owner) && !this.contactList.contains(temp1))  {
					this.contactList.add(temp1);
					Button helper = new Button(temp1);
					this.contactListButtons.getChildren().add(helper);
					
					//connect the button to write in text field scroll pane
					helper.setOnAction(e->this.loadMessagesWith("messages.txt", temp1));
				}
				else if(!temp2.equals(this.owner) && !this.contactList.contains(temp2))  {
					this.contactList.add(temp2);
					Button helper = new Button(temp2);
					this.contactListButtons.getChildren().add(helper);
					
					//connect the button to write in text field scroll pane
					helper.setOnAction(e->this.loadMessagesWith("messages.txt", temp2));
				}
			}
		}
		
		this.write_send_messages.getChildren().addAll(this.msgField,this.sendButton);
		this.messagesPane.getChildren().addAll(this.container,this.write_send_messages);
		this.main_tasks.getChildren().addAll(this.contactListButtons,this.messagesPane);
		
		//bottom layout
		this.bottom.getChildren().addAll(this.newContact,this.addContact);
		
		//build the main layout
		back_title_labels.setPrefHeight(50);
		main_tasks.setPrefHeight(200);
		StackPane left = new StackPane();
		left.setPrefWidth(50);
		StackPane right = new StackPane();
		right.setPrefWidth(50);
		this.setLeft(left);
		this.setRight(right);
		
		this.setTop(back_title_labels);
		this.setCenter(main_tasks);
		this.setBottom(bottom);
		
		this.addContact.setOnAction(e->this.add_a_contact(this.newContact.getText()));
		this.sendButton.setOnAction(e->this.send_a_message("messages.txt",this.msgField.getText()));
		this.backButton.setOnAction(event -> mainApp.main_View());
    }
	
	
	public Boolean getAllMsgs(String file, String usr2) {
		this.messages.clear();
		try {
			//opens a new file reader object
			File thisFile = new File(file);
			Scanner myScanner = new Scanner(thisFile);
			while(myScanner.hasNextLine()) {
				//save line to messages and read next line
				/*lines are in the format:
				 * 		recipient|sender|message
				 * 
				 * */
				String line = myScanner.nextLine();
				//split the message by '|'
				//get all messages between owner and user2
				String[] someMessage = line.split("\\|");
				if(someMessage[0].equals(this.owner) && someMessage[1].equals(usr2)) {
					messages.add(someMessage);
				}
				else if(someMessage[1].equals(this.owner) && someMessage[0].equals(usr2)) 
					messages.add(someMessage);
			}
			
		}
		catch(IOException e){
			System.out.println("File open fail...");
			return false;
		}
		return true;
	}
	
	public Boolean getAllMsgs_first(String file) {
		this.messages.clear();
		try {
			//opens a new file reader object
			File thisFile = new File(file);
			Scanner myScanner = new Scanner(thisFile);
			while(myScanner.hasNextLine()) {
				//save line to messages and read next line
				/*lines are in the format:
				 * 		recipient|sender|message
				 * 
				 * */
				String line = myScanner.nextLine();
				//split the message by '|'
				//get all messages between owner and user2
				String[] someMessage = line.split("\\|");
				if(someMessage[0].equals(this.owner) || someMessage[1].equals(this.owner)) 
					messages.add(someMessage);
				
			}
			
		}
		catch(IOException e){
			System.out.println("File open fail...");
			return false;
		}
		return true;
	}
	
	public Boolean sendMessage(String file,String message) {
		try {
			//open a new file writer
			FileWriter myWriter=new FileWriter(file,true);
            myWriter.write(message+"\n");
            myWriter.close();
			System.out.println("Wrote \"" + message + "\" to " + file);		
		}
		catch(IOException e) {
			System.out.println("File open fail...");
			return false;
		}
		return true;
	}
	public void loadMessagesWith(String file, String usr2) {
		this.recipient = usr2;
		
		if(this.getAllMsgs(file, this.recipient))
		{
			for(int i=0;i<this.messages.size();i++) {
				System.out.println(this.messages.get(i)[2]);
			}
			this.texts.clear();
			for(int i=0;i<this.messages.size();i++) {
				String thisText = this.messages.get(i)[1] + ":\n"+
						this.messages.get(i)[2]+"\n";
				texts.add(new Label(thisText));
			}
			this.allMessages.getChildren().clear();
			this.allMessages.getChildren().addAll(texts);
			this.container.setContent(this.allMessages);
		}
		else
			System.out.println("Failed to load messages");
	}
	public void send_a_message(String file,String content) {
		//check if recipient is set
		this.msgField.clear();
		if(this.contactList.contains(this.recipient))
		{
			//make the message
			String msg = this.recipient + "|" + this.owner + "|" + content;
			this.sendMessage(file, msg);
			this.loadMessagesWith(file, this.recipient);
		}
	}
	public void add_a_contact(String contactName) {
		this.newContact.clear();
		if(!this.contactList.contains(contactName) && !contactName.equals(this.owner))  {
			this.contactList.add(contactName);
			Button helper = new Button(contactName);
			this.contactListButtons.getChildren().add(helper);
			
			//connect the button to write in text field scroll pane
			helper.setOnAction(e->this.loadMessagesWith("messages.txt", contactName));
		}
		else
			System.out.println("Cannot add: "+contactName);
	}
	public void fresh_text_file(String file) {
		try {
		FileWriter myWriter=new FileWriter(file);
        myWriter.write("");
        myWriter.close();
		}
		catch(IOException e) {
			System.out.println("Cannot refresh the text file");
		}
	}
	
}
