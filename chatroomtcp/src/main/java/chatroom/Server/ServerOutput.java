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
                msgData = new ChatData("Chat", clientname, sh.clientMsgData.chatMsg());
                msgJson = serverMapper.writeValueAsString(msgData);
                sh.getServerOUt().println(msgJson);
            }
        } catch (Exception e) {
            System.out.println("Failed to broadcast Message: " + e);
        }
    }
}
