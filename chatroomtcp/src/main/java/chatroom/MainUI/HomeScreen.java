package chatroom.MainUI;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
;

public class HomeScreen extends VBox {
    
    public HomeScreen(){
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

    private EventHandler<javafx.event.ActionEvent> onCloseAppButton(){
        Platform.exit();
        System.exit(0);
        return null;
    }

    private EventHandler<javafx.event.ActionEvent> onEnterChatButton(){
        ChatUI chatWindow = new ChatUI();
        ClientUI.getInstance().getPrimaryStage().getScene().setRoot(chatWindow);
        return null;
    }
}
