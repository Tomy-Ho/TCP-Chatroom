package chatroom.MainUI;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatUI extends VBox {

    public ChatUI() {
        try {
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Chatroom");
            TextArea chatText = new TextArea();
            VBox vbox = new VBox(chatText);
            
            Scene scene = new Scene(vbox, 600, 800);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Failed to launch chat screen: " + e.getMessage());
        }
    }
}
