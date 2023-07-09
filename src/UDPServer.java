import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public static void main(String[] args) {
        int serverPort = 12345; // Server port number

        try {
            try (
            // Create a UDP socket
            DatagramSocket socket = new DatagramSocket(serverPort)) {
                System.out.println("Server listening on port " + serverPort);

                while (true) {
                    // Receive client's message
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(receivePacket);
                    String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("Client message: " + message);

                    // Prepare the response to send
                    byte[] sendData = "back at you".getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                            receivePacket.getAddress(), receivePacket.getPort());

                    // Send the response to the client
                    socket.send(sendPacket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
