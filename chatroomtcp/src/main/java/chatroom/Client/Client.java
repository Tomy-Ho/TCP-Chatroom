package chatroom.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import chatroom.ChatData.ChatData;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Client extends VBox {
    BufferedReader clientIn;
    PrintWriter clientOut;
    BufferedReader inputReader;
    Socket serverConnection;
    ListView <String> chatBox = new ListView<>();; 
    TextField inputText = new TextField();
    Button sendMsgButton = new Button("Send");
    ClientOutput coHandler;
  
    public Client(Stage primaryStage){        
        this.getChildren().addAll(chatBox, inputText, sendMsgButton); 

        sendMsgButton.setOnAction(e -> coHandler.sendMessages(inputText, chatBox));
        inputText.setOnAction(e -> coHandler.sendMessages(inputText, chatBox));
        
        primaryStage.setOnCloseRequest(e -> {
            try {
                notifyDisconnection();
                serverConnection.close();
                Platform.exit();
                System.exit(0);
            } catch (IOException e1) {
            }            
        });
    }

    public void connectToServer(){
        try {
            serverConnection = new Socket("localhost", 1234);
            clientIn = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
            clientOut = new PrintWriter(serverConnection.getOutputStream(), true);
            chatBox.getItems().add("Succesfully connected to Server.");

            coHandler = new ClientOutput(clientOut);
            new Thread(() -> {
                ClientInput ciHandler = new ClientInput(clientIn);
                ciHandler.receiveMessages(chatBox);
            }).start();
        } catch (IOException e) {
            System.out.println("Failed to run client: " + e);
            System.exit(0);
        }
    }

    private void notifyDisconnection(){
        try {
            String note = "Disconnected.";
            ChatData data = new ChatData("Connection", "", "", note);
            ObjectMapper mapper = new ObjectMapper();
            String noteJson = mapper.writeValueAsString(data);
            clientOut.println(noteJson);
            System.out.println("sent");
        } catch (Exception e) {
        }
    }
}
