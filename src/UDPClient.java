import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Server IP address
        int serverPort = 12345; // Server port number

        try {
            // Create a UDP socket
            DatagramSocket socket = new DatagramSocket();
            
            int numMessages = 1000; // Number of messages to send
            long totalRTT = 0;
            long minRTT = Long.MAX_VALUE;
            long maxRTT = 0;

            for (int i = 0; i < numMessages; i++) {
                // Prepare the message to send
                byte[] sendData = "hello".getBytes();
                InetAddress serverInetAddress = InetAddress.getByName(serverAddress);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverInetAddress, serverPort);

                // Send the message to the server
                long startTime = System.nanoTime();
                socket.send(sendPacket);

                // Receive the response from the server
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                long endTime = System.nanoTime();
                long rtt = endTime - startTime;

                // Extract and print the response from the received packet
                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Server response: " + response);

                // Accumulate total RTT
                totalRTT += rtt;

                // Update min and max RTT
                if (rtt < minRTT) {
                    minRTT = rtt;
                }
                if (rtt > maxRTT) {
                    maxRTT = rtt;
                }
            }

            // Calculate average RTT
            long averageRTT = totalRTT / numMessages;
            
            // Print the statistics
            System.out.println("Number of messages sent: " + numMessages);
            System.out.println("Average RTT: " + averageRTT + " microseconds");
            System.out.println("Minimum RTT: " + minRTT + " microseconds");
            System.out.println("Maximum RTT: " + maxRTT + " microseconds");

            // Close the socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

