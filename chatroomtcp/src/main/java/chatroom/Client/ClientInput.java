package chatroom.Client;

import java.io.BufferedReader;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import chatroom.ChatData.ChatData;

public class ClientInput {
    private final BufferedReader clientIn;
    ObjectMapper clientInMapper = new ObjectMapper();
    String clientInJson = "";
    String clientInMsg = "";
    ChatData clientInData;

    public ClientInput(BufferedReader clientIn){
        this.clientIn = clientIn;
    }

    public void receiveMessages(){
        try {
            while ((clientInJson = clientIn.readLine()) != null){
                clientInData = clientInMapper.readValue(clientInJson, ChatData.class);
                clientInMsg = clientInData.chatMsg();
                if(clientInData.msgType().equalsIgnoreCase("chat")){
                    System.out.println(clientInData.sender() + ": " + clientInMsg); 
                } else if(clientInData.msgType().equalsIgnoreCase("Notification")){
                    System.out.print(clientInData.sender() + " " + clientInMsg);
                } else {
                    System.out.println(clientInData.sender() + " " + clientInMsg);
                }
            } 
        } catch (IOException e) {
            System.out.println("Failed to receive Message: " + e);
        }
    }
}
