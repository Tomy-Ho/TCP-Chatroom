package chatroom.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket; 

import com.fasterxml.jackson.databind.ObjectMapper;

import chatroom.ChatData.ChatData;
import javafx.scene.control.TextField;

public class ClientOutput {
    private final PrintWriter clientOut;
    private final BufferedReader inputReader;
    private final Socket clientToSeverconnection;

    String clientOutput = "";
    String clientOutputJson = "";
    ChatData clientOutputData;
    ObjectMapper clientOutputMapper = new ObjectMapper();

    public ClientOutput(PrintWriter clientOut, BufferedReader inputReader, Socket clientToServerconnection){
        this.inputReader = inputReader;
        this.clientOut = clientOut;
        this.clientToSeverconnection = clientToServerconnection;
    }

    public void sendMessages(TextField inputText){
        try {
            clientOutput = inputReader.readLine();
            if (clientOutput != null && !clientOutput.isEmpty()) { 
                
                clientOutputData = new ChatData("chat", "clientname", "clientname", clientOutput);
                clientOutputJson = clientOutputMapper.writeValueAsString(clientOutputData);

                clientOut.println(clientOutputJson);
                inputText.clear();
            }

            if(clientOutput.isEmpty() || clientOutput.matches("[\\t]")){
                System.out.println("Messages can't be empty!");
            }
        } catch (IOException e) {
            System.out.println("Failed to send Client message: " + e);
        }
    }
}
