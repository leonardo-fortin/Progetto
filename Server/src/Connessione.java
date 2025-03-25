import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Connessione extends Thread {

    private Socket clientSocket;

    private BufferedReader in = null;
    private PrintWriter out = null;

    public Connessione(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            String str = "";
            while(!str.equals("END")) {
                try {
                    // creazione stream di input da clientSocket
                    InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
                    in = new BufferedReader(isr);
                    // creazione stream di output su clientSocket
                    OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
                    BufferedWriter bw = new BufferedWriter(osw);
                    out = new PrintWriter(bw, true);
                    //ciclo di ricezione dal client e invio di risposta
                    out.print("Hello (END to end connection): ");
                    out.flush();
                    while (true) {
                        str = in.readLine();
                        if (str.equals("END")) break;
                        System.out.println("Echoing: " + str.toUpperCase());
                        out.println(str.toUpperCase());
                    }
                } catch (IOException e) {
                    System.err.println("Accept failed");
                    System.exit(1);
                }
            }
            // chiusura di stream e socket
            System.out.println("EchoServer: closing...");
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void chiudi(){
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
