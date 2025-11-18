package chatroom.Client;

import java.io.BufferedReader;

import com.fasterxml.jackson.databind.ObjectMapper;

import chatroom.ChatData.ChatData;

public class ClientInput {
    private BufferedReader clientIn;
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
                } else {
                    System.out.print(clientInData.sender() + " " + clientInMsg);
                }
            } 
        } catch (Exception e) {
            System.out.println("Failed to receive Message: " + e);
        }
    }
}
