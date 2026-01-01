package chatroom.Client.MainUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientUI extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Starting screen...");
        stage.setTitle("Menu");
        stage.setWidth(500);
        stage.setHeight(400);

        HomeScreen startWindow = new HomeScreen(stage);
        Scene scene = new Scene(startWindow);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

