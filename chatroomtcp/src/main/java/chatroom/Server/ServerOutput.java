package chatroom.Server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import chatroom.ChatData.ChatData;

public class ServerOutput {
    private final ClientHandler shandler;
    ObjectMapper serverMapper = new ObjectMapper();
    ChatData msgData;
    String msgJson;
    
    public ServerOutput(ClientHandler sh){
        this.shandler = sh;
    }

    public void broadCastMsg(String clientMsgJson, String clientname){
        try {
            for(ClientHandler sh : shandler.getClientList().keySet()){
                sh.clientMsgData = serverMapper.readValue(clientMsgJson, ChatData.class);
                msgData = new ChatData("Chat", sh.getClientname(), clientname, sh.clientMsgData.chatMsg());
                msgJson = serverMapper.writeValueAsString(msgData);
                sh.getServerOUt().println(msgJson);
            }
        } catch (JsonProcessingException e) {
            System.out.println("Failed to broadcast Message: " + e);
        }
    }

    public void castMsgToAll(String msgtype, String clientname, String sender, String message){
        try {
            for(ClientHandler sh : shandler.getClientList().keySet()){
                msgData = new ChatData(msgtype, clientname, sender, message);
                msgJson = serverMapper.writeValueAsString(msgData);
                sh.getServerOUt().println(msgJson);
            }
        } catch (JsonProcessingException e) {
            System.out.println("Failed to sen message to all clients: " + e);
        }
    }

    public void castMsgtToClient(String msgtype, String clientname, String sender, String message){
        try {
            msgData = new ChatData(msgtype, clientname, sender, message);
            msgJson = serverMapper.writeValueAsString(msgData);
            shandler.getServerOUt().println(msgJson);
        } catch (JsonProcessingException e) {
            System.out.println("Failed to cast message to client: " + e);
        }
    }

    public void directMessage(String messageJson){
        try {
            msgData = serverMapper.readValue(messageJson, ChatData.class);
            String msgSenderTemp = msgData.sender();
            String[] splitMsg = msgData.chatMsg().split(" ");
            String receiverName;
            String senderName = shandler.getClientname();

            if(splitMsg.length < 2){
                msgData = new ChatData("Notification", senderName, "[Server]", "Please enter a username after /whisper.\n");
                msgJson = serverMapper.writeValueAsString(msgData);
                shandler.getServerOUt().println(msgJson);
                return;
            } else {
                receiverName = splitMsg[1];
            }

            String whisperSender = senderName + " whispers:";
            String whisperMsg = " ";
            String returnMsgSender = "You whispered to " + receiverName;
            String returnMsg = " ";

            if(splitMsg.length == 2){
                msgData = new ChatData("Notification", senderName, "[Server]", "Messages can't be empty!\n");
                msgJson = serverMapper.writeValueAsString(msgData);
                shandler.getServerOUt().println(msgJson);
                return;
            }

            if(!shandler.getClientList().containsValue(splitMsg[1])){
                msgData = new ChatData("Notification", senderName, "[Server]", "Username does not exist in this Server!\n");
                msgJson = serverMapper.writeValueAsString(msgData);
                shandler.getServerOUt().println(msgJson);
                return;
            }

            if(senderName.equals(receiverName)){
                msgData = new ChatData("Notification", senderName, "[Server]", "You can't whisper to yourself!\n");
                msgJson = serverMapper.writeValueAsString(msgData);
                shandler.getServerOUt().println(msgJson);
                return;
            }

            for(int i = 2; i < splitMsg.length; i++){
                whisperMsg += splitMsg[i];
                returnMsg += splitMsg[i];
            }

            for(ClientHandler sh : shandler.getClientList().keySet()){
                if(sh.getClientname().equalsIgnoreCase(receiverName)){
                    msgData = new ChatData("DM", senderName, whisperSender, whisperMsg);
                    msgJson = serverMapper.writeValueAsString(msgData);
                    sh.getServerOUt().println(msgJson);
                    break;
                }
            }

            //resend whisper message to Sender back: "You whispered to [username]: Hello world."
            msgData = new ChatData("DM-back", senderName, returnMsgSender, returnMsg);
            msgJson = serverMapper.writeValueAsString(msgData);
            shandler.getServerOUt().println(msgJson);

        } catch (JsonProcessingException e) {
            System.out.println("Failed to send DM: " + e);
        }
    }
}
