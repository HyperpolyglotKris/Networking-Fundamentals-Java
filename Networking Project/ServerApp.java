// Kristiyan Stoilov

import java.net.*;
import java.io.*;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(431);
                System.out.println("HELLO CCCS431 SDTP Server written by Kristiyan Stoilov and Jad Charara... READY");
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("ALOHA << IP:" + socket.getRemoteSocketAddress() + ">>");
                    ServerThread serverThread = new ServerThread(socket);
                    new Thread(serverThread).start();
                }
            } catch (Exception x) {
                System.out.println("Exception : " + x.getMessage());
            }
        }
    }
}
