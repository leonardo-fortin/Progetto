import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Main {
    public static final int PORT = 1050; // porta al di fuori del range 1-1024 !

    public static void main(String[] args) throws IOException {
        ArrayList<Connessione> connessioni = new ArrayList<>();

        Gestore gestore = new Gestore(connessioni);
        gestore.start();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("EchoServer: started ");
            System.out.println("Server Socket: " + serverSocket);
            Socket clientSocket;
            while(true) {
                try {
                    // bloccante finchè non avviene una connessione
                    clientSocket = serverSocket.accept();
                    System.out.println("Connection accepted: " + clientSocket);
                    Connessione connessione = new Connessione(clientSocket);
                    connessione.start();
                    connessioni.add(connessione);

                } catch (IOException e) {
                    System.err.println("Accept failed");
                    System.exit(1);
                }
            }
        }
    }
}