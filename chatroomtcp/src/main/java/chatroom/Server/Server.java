package chatroom.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private BufferedReader serverIn;
    private PrintWriter serverOut;
    private ServerSocket serverCon;
    private HashMap <ServerHandler, String> clientlist;

    ExecutorService clientpool;
    int socketNr = 1234;

    private void StartServer(){
        try {
             serverCon = new ServerSocket(socketNr);  
             System.out.println("New connection established!");
            

             while(!serverCon.isClosed()){
                Socket clientSocket = serverCon.accept();
                serverIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                serverOut = new PrintWriter(clientSocket.getOutputStream(), true);
                clientpool = Executors.newCachedThreadPool();

                if(clientSocket.isConnected()){
                    System.out.println("A Client has connected.");
                    ServerHandler sh = new ServerHandler(serverIn, serverOut, clientlist);
                    clientlist.put(sh, "");
                    clientpool.execute(sh);
                }

             }
             
        } catch (Exception e) {
            System.out.println("Failed to start Server: " + e);
            ShutDownServer();
        }
        
    }

    private void ShutDownServer(){
        try {
            serverIn.close();
            serverOut.close();
            clientpool.shutdown();
            serverCon.close();
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Failed to shut down Server: " + e);
        }
    }

    public static void main(String[] args) {
        new Server().StartServer();
    }
}