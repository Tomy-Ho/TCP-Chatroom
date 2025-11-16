package chatroom.Server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import chatroom.ChatData.ChatData;;

public class ServerHandler implements Runnable {

    private BufferedReader serverIn;
    private PrintWriter serverOut;
    private HashMap <ServerHandler, String> clientlist;

    String clientInput = "";
    ObjectMapper clientMsgMapper = new ObjectMapper();
    String clientMsgJson = "";
    ChatData clientMsgData;
    String clientname = "";
    ServerOutput serverOutput = new ServerOutput(this);
    
    public ServerHandler(BufferedReader serverIn, PrintWriter serverOut, HashMap <ServerHandler, String> clientlist){
        this.serverIn = serverIn;
        this.serverOut = serverOut;
        this.clientlist = clientlist;
    }

    public ServerHandler(ServerHandler sh){
        sh = this;
    }

    public HashMap <ServerHandler, String> getClientList(){
        return this.clientlist;
    }

    public PrintWriter getServerOUt(){
        return this.serverOut;
    }

    public BufferedReader getServerIn(){
        return this.serverIn;
    }


    private void handleMessages(){
        try {
            while((clientInput = serverIn.readLine()) != null){
                clientMsgData = clientMsgMapper.readValue(clientInput, ChatData.class);
                serverOutput.broadCastMsg(clientInput, clientname);
            }
        } catch (Exception e) {
            System.out.println("Failed to handle Messages: " + e);
        }
    }

    public void run(){
        handleMessages();
    }
}
