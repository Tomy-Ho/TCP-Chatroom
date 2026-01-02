package chatroom.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import chatroom.ChatData.ChatData;;

public class ClientHandler implements Runnable {

    private final BufferedReader serverIn;
    private final PrintWriter serverOut;
    private String clientname = "";
    private final HashMap <ClientHandler, String> clientlist;
    private final Socket clientSocket;

    String clientInput = "";
    ObjectMapper clientMsgMapper = new ObjectMapper();
    ChatData clientMsgData;
    ServerOutput serverOutput = new ServerOutput(this);

    public ClientHandler(BufferedReader serverIn, PrintWriter serverOut, Socket clientSocket, HashMap <ClientHandler, String> clientlist){
            this.serverIn = serverIn;
            this.serverOut = serverOut;
            this.clientSocket = clientSocket;
            this.clientlist = clientlist;
    }

    public HashMap<ClientHandler, String> getClientList(){
        return this.clientlist;
    }
    
    public PrintWriter getServerOUt(){
        return this.serverOut;
    }

    public BufferedReader getServerIn(){
        return this.serverIn;
    }

    public String getClientname(){
        return this.clientname;
    }

    private void loginClientToServer(){
        try {
            System.out.println("A Client has connected to the server.");
            serverOutput.castMsgtToClient("Notification", clientname, "[Server]","Please enter a username: ");
            
            clientInput = serverIn.readLine();
            clientMsgData = clientMsgMapper.readValue(clientInput, ChatData.class);
            String desiredUsername = clientMsgData.chatMsg();

            while(!checkValidUserName(desiredUsername)){
                clientInput = serverIn.readLine();
                clientMsgData = clientMsgMapper.readValue(clientInput, ChatData.class);
                desiredUsername = clientMsgData.chatMsg();
            }

            setUsername(desiredUsername);
            serverOutput.castMsgtToClient("Notification", clientname, "[Server]", "Welcome to the chatroom, " + clientname + "!" + "\n");
            System.out.println(clientlist);
        } catch (IOException e) {
            System.out.println("Failed to login Client: " + e);
        }
    }

    private void logoutClient(){
        try {
            System.out.println("siuu");
            if(clientMsgData.chatMsg().equals("Disconnected.") 
                && clientMsgData.msgType().equals("Connection")){
                serverOutput.castMsgtToClient("logout", clientname, "[Server]", "You left the chatroom." + "\n");
                clientSocket.close();
                serverOutput.castMsgToAll("Notification", clientname, "[Server]", clientname + " left the chatroom." + "\n");
                clientlist.remove(this);
            }
        } catch (Exception e) {
            System.out.println("Faile to logout client: " + e);
        }
    }

    private void setUsername(String username){
        clientlist.put(this, username);
        this.clientname = username;
        serverOutput.castMsgtToClient("login", clientname, "[Server]", "Your username: " + this.clientname );
    }

    private boolean checkValidUserName(String username){
        boolean namevalid = false;
        if(!username.matches(".\\p{L}.*") || username.isEmpty() || username.equalsIgnoreCase("/bye")){
            serverOutput.castMsgtToClient("Notification", clientname, "[Server]", "Invalid username, please try again: ");
            return namevalid;
        } else  if (clientlist.containsValue(username)) {
            serverOutput.castMsgtToClient("Notification", clientname, "[Server]", "Username already in use, please try again: ");
            return namevalid;
        } else {
            namevalid = true;
            return namevalid;
        }
    }

    private void handleMessages(){
        try {
            while((clientInput = serverIn.readLine()) != null){
                clientMsgData = clientMsgMapper.readValue(clientInput, ChatData.class);
                logoutClient();

                if(clientMsgData.chatMsg().contains("/whisper")){
                    serverOutput.directMessage(clientInput);
                } else {
                    serverOutput.broadCastMsg(clientInput, clientname); 
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run(){
        loginClientToServer();
        handleMessages();
    }
}
