import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) {
        int serverPort = 12345; // Server port number

        try {
            //try ()
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(serverPort); 
            System.out.println("Server listening on port " + serverPort);

            while (true) {
                // Accept client connections
                Socket clientSocket = serverSocket.accept();

                // Create input and output streams for client communication
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Receive client's message
                String message = in.readLine();
                System.out.println("Client message: " + message);

                // Send response to the client
                out.println("back at you");

                // Close the client socket
                clientSocket.close();
                out.close(); 
                in.close();

                //Check if the server needs to be stopped
                if (message.equalsIgnoreCase("stop"))
                    break;
            }

            // Close the server socket
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

