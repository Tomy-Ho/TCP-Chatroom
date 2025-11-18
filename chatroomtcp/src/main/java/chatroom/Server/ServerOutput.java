package chatroom.Server;

import com.fasterxml.jackson.databind.ObjectMapper;

import chatroom.ChatData.ChatData;

public class ServerOutput {
    private ServerHandler shandler;
    ObjectMapper serverMapper = new ObjectMapper();
    ChatData msgData;
    String msgJson;
    
    public ServerOutput(ServerHandler sh){
        this.shandler = sh;
    }

    public void broadCastMsg(String clientMsgJson, String clientname){
        try {
            for(ServerHandler sh : shandler.getClientList().keySet()){
                sh.clientMsgData = serverMapper.readValue(clientMsgJson, ChatData.class);
                msgData = new ChatData("Chat", sh.getClientname(), clientname, sh.clientMsgData.chatMsg());
                msgJson = serverMapper.writeValueAsString(msgData);
                sh.getServerOUt().println(msgJson);
            }
        } catch (Exception e) {
            System.out.println("Failed to broadcast Message: " + e);
        }
    }

    public void castMsgToAll(String msgtype, String clientname, String sender, String message){
        try {
            for(ServerHandler sh : shandler.getClientList().keySet()){
                msgData = new ChatData(msgtype, clientname, sender, message);
                msgJson = serverMapper.writeValueAsString(msgData);
                sh.getServerOUt().println(msgJson);
            }
        } catch (Exception e) {
            System.out.println("Failed to sen message to all clients: " + e);
        }
    }

    public void castMsgtToClient(String msgtype, String clientname, String sender, String message){
        try {
            msgData = new ChatData(msgtype, clientname, sender, message);
            msgJson = serverMapper.writeValueAsString(msgData);
            shandler.getServerOUt().println(msgJson);
        } catch (Exception e) {
            System.out.println("Failed to cast message to client: " + e);
        }
    }
}
