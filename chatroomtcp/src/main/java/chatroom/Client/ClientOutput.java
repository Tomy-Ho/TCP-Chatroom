package chatroom.Client;

import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

import chatroom.ChatData.ChatData;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ClientOutput {
    private final PrintWriter clientOut;

    String clientOutput = "";
    String clientOutputJson = "";
    ChatData clientOutputData;
    ObjectMapper clientOutputMapper = new ObjectMapper();

    public ClientOutput(PrintWriter clientOut){
        this.clientOut = clientOut;
    }

    public void sendMessages(TextField inputText, ListView <String> chatBox){
        try {
            clientOutput = inputText.getText();
            if (clientOutput != null && !clientOutput.isEmpty()) { 
                
                clientOutputData = new ChatData("chat", "clientname", "clientname", clientOutput);
                clientOutputJson = clientOutputMapper.writeValueAsString(clientOutputData);

                clientOut.println(clientOutputJson);
                inputText.clear();
            }

            if(clientOutput.isEmpty() || clientOutput.matches("[\\t]")){
                chatBox.getItems().add("Messages can't be empty!");
                chatBox.scrollTo(chatBox.getItems().size() - 1);
            }
        } catch (IOException e) {
            System.out.println("Failed to send Client message: " + e);
        }
    }
}
