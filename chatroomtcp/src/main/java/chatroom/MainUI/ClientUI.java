package chatroom.MainUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientUI extends Application{
    static ClientUI clientUIinstance;
    private Stage primary;

    public static ClientUI getInstance(){
        if(clientUIinstance == null){
            clientUIinstance = new ClientUI();
        }
        return clientUIinstance;
    }

    @Override
    public void start(Stage stage) throws Exception {
        primary = stage;
        System.out.println("Starting screen...");
        primary.setTitle("Menu");
        primary.setWidth(500);
        primary.setHeight(400);

        HomeScreen startWindow = new HomeScreen();
        Scene scene = new Scene(startWindow);
        
        primary.setScene(scene);
        primary.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public Stage getPrimaryStage(){
        return primary;
    }
}

