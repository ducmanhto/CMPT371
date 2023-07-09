import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Server IP address
        int serverPort = 12345; // Server port number

        try {
            // Connect to the server
            Socket socket = new Socket(serverAddress, serverPort);

            // Create input and output streams for socket communication
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            int numMessages = 1000; // Number of messages to send
            long totalRTT = 0;
            long minRTT = Long.MAX_VALUE;
            long maxRTT = 0;

            for (int i = 0; i < numMessages; i++) {
                // Send the "hello" message to the server
                long startTime = System.nanoTime();
                out.println("hello");

                try {
                // Receive and print the response from the server
                String response = in.readLine();
                long endTime = System.nanoTime();
                System.out.println("Server response: " + response);
                long rtt = (endTime - startTime) / 1000; // Convert nanoseconds to microseconds

                // Accumulate total RTT
                totalRTT += rtt;

                // Update min and max RTT
                if (rtt < minRTT) {
                    minRTT = rtt;
                }
                if (rtt > maxRTT) {
                    maxRTT = rtt;
                }
                } catch (IOException e) {
                    // Handle socket closure or network errors
                    System.err.println("Error receiving response from the server: " + e.getMessage());
                    break;
                }
            }

            // Calculate average RTT
            long averageRTT = totalRTT / numMessages;

            // Print the statistics
            System.out.println("Number of messages sent: " + numMessages);
            System.out.println("Average RTT: " + averageRTT + " microseconds");
            System.out.println("Minimum RTT: " + minRTT + " microseconds");
            System.out.println("Maximum RTT: " + maxRTT + " microseconds");

            // Close the connection
            out.close(); 
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
