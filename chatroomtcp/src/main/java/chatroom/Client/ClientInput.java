package chatroom.Client;

import java.io.BufferedReader;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import chatroom.ChatData.ChatData;
import javafx.application.Platform;
import javafx.scene.control.ListView;

public class ClientInput {
    private final BufferedReader clientIn;
    ObjectMapper clientInMapper = new ObjectMapper();
    String clientInJson = "";
    String clientInMsg = "";
    String printMsg = "";
    ChatData clientInData;

    public ClientInput(BufferedReader clientIn){
        this.clientIn = clientIn;
    }

    public void receiveMessages(ListView <String> chatBox){
        try {
            while ((clientInJson = clientIn.readLine()) != null){
                clientInData = clientInMapper.readValue(clientInJson, ChatData.class);
                clientInMsg = clientInData.chatMsg();
                if(clientInData.msgType().equalsIgnoreCase("chat")){
                    printMsg = clientInData.sender() + ": " + clientInMsg;
                } else if(clientInData.msgType().equalsIgnoreCase("Notification")){
                    printMsg = clientInData.sender() + " " + clientInMsg;
                } else {
                    printMsg = clientInData.sender() + " " + clientInMsg;
                }
                Platform.runLater(() -> {
                    chatBox.getItems().add(printMsg);
                    chatBox.scrollTo(chatBox.getItems().size() - 1);
                });
            } 
        } catch (IOException e) {
            System.out.println("Failed to receive Message: " + e);
        }
    }
}
