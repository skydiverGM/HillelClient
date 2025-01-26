package ua.hillel;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class App {
    private static final String HOST = "localhost";
    private static final int PORT = 4999;

    public static void main(String[] args) {
        System.out.println("Connecting to server...");

        try(Socket socket = new Socket(HOST, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server. Enter your message.");

            new Thread(() -> {
                try{
                    String message;
                    while ((message = in.readLine()) != null){
                        System.out.println(message);
                    }
                }catch (Exception e){
                    System.err.println("Connection has been interrupted.");
                }
            }).start();

            String clientMessage;
            while ((clientMessage = console.readLine()) != null){
                out.println(clientMessage);
                if ("exit".equalsIgnoreCase(clientMessage)){
                    System.out.println("You have been disconnected.");
                    break;
                }
            }
        }catch (Exception e){
            System.err.println("Client's error: " + e.getMessage());
        }
    }
}
