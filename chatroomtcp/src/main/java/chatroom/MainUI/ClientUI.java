package chatroom.MainUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientUI extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        /*new Thread(() -> {
            Client client = new Client();
            client.run();
        }).start();*/
        System.out.println("Starting screen...");
        stage.setWidth(200);
        stage.setHeight(400);

        HomeScreen startWindow = new HomeScreen();
        Scene scene = new Scene(startWindow);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

