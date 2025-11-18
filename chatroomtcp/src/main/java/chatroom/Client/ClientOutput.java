package chatroom.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket; 

import com.fasterxml.jackson.databind.ObjectMapper;

import chatroom.ChatData.ChatData;

public class ClientOutput implements Runnable{
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

    private void sendMessages(){
        try {
            while (clientToSeverconnection.isConnected()) { 
                clientOutput = inputReader.readLine();
                clientOutputData = new ChatData("chat", "clientname", "clientname", clientOutput);
                clientOutputJson = clientOutputMapper.writeValueAsString(clientOutputData);

                if(clientOutput.isEmpty() || clientOutput.matches("[\\t]")){
                    System.out.println("Messages can't be empty!");
                } else {
                    clientOut.println(clientOutputJson);
                }   
            }
        } catch (IOException e) {
            System.out.println("Failed to send Client message: " + e);
        }
    }

    @Override
    public void run(){
        sendMessages();
    }
}
