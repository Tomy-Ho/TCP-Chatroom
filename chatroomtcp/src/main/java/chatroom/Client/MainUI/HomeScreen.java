package chatroom.Client.MainUI;

import chatroom.Client.Client;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
;

public class HomeScreen extends VBox {
    Stage primaryStage;

    public HomeScreen(Stage primary){
        primaryStage = primary;
        this.setAlignment(Pos.CENTER);
        this.setId("vbox");

        Label homeTitle = new Label("Chatroom");
        homeTitle.setId("title");
        
        Button enterChatButton = new Button("Enter Chatroom");
        Button closeApplication = new Button("Exit to Desktop");

        this.getChildren().addAll(homeTitle, enterChatButton, closeApplication);

        closeApplication.setOnAction(e -> onCloseAppButton());
        enterChatButton.setOnAction(e -> onEnterChatButton());
    }

    private void onCloseAppButton(){
        Platform.exit();
        System.exit(0);
    }

    private void onEnterChatButton(){
        try {
            Client client = new Client();

            client.connectToServer();
        
            Scene clientScene = new Scene(client, 600, 700);
            primaryStage.setScene(clientScene);
            
        } catch (Exception e) {
        }
    }
}
