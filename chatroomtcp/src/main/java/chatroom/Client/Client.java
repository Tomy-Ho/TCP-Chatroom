package chatroom.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client implements Runnable{
    BufferedReader clientIn;
    PrintWriter clientOut;
    BufferedReader inputReader;
    Socket serverConnection;

    @Override
    public void run(){
        try {
            serverConnection = new Socket("localhost", 1234);
            clientIn = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
            clientOut = new PrintWriter(serverConnection.getOutputStream(), true);
            inputReader = new BufferedReader(new InputStreamReader(System.in));
         
            System.out.println("Succesfully connected to Server.");
            ClientOutput coHandler = new ClientOutput(clientOut, inputReader, serverConnection);
            Thread coHandlerThread = new Thread(coHandler);
            coHandlerThread.start();

            ClientInput ciHandler = new ClientInput(clientIn);
            ciHandler.receiveMessages();

            if(serverConnection.isClosed()){
                System.exit(0);
            }
            
        } catch (IOException e) {
            System.out.println("Failed to run client: " + e);
            System.exit(0);
        } finally {
            System.out.println("Disconnected.");
            System.exit(0);
        }
    }
}
