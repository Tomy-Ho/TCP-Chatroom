package chatroom.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Client extends VBox {
    BufferedReader clientIn;
    PrintWriter clientOut;
    BufferedReader inputReader;
    Socket serverConnection;
    ListView <String> chatBox = new ListView<>();; 
    TextField inputText = new TextField();
    Button sendMsgButton = new Button("Send");
    ClientOutput coHandler;

    public Client(){        
        HBox inputTextBox = new HBox(10, inputText, sendMsgButton);
        this.getChildren().addAll(chatBox, inputText, sendMsgButton); 

        
        sendMsgButton.setOnAction(e -> coHandler.sendMessages(inputText));
        inputText.setOnAction(e -> coHandler.sendMessages(inputText));
        
    }

    public void connectToServer(){
        try {
            serverConnection = new Socket("localhost", 1234);
            clientIn = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
            clientOut = new PrintWriter(serverConnection.getOutputStream(), true);
            System.out.println("Succesfully connected to Server.");

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
}
