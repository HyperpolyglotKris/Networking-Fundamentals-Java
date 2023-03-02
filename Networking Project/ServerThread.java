// Kristiyan Stoilov

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ServerThread implements Runnable {
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            System.out.println(receiveMessage(socket));
            sendHello(socket);
            clientInterraction(socket);
            socket.close();
            return;
        } catch (Exception x) {
            System.out.println("Exception : " + x.getMessage());
        }
    }

    public void sendHello(Socket socket) throws IOException {
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println("ALOHA << IP:" + socket.getRemoteSocketAddress() + " >>");
    }

    public void sendMessage(Socket socket, String information) throws IOException {
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(information);
    }

    public String receiveMessage(Socket socket) {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine();
            return line;
        } catch (IOException x) {
            return "LOST";
        }
    }

    public void clientInterraction(Socket socket) throws IOException {
        try {
            String message = receiveMessage(socket);
            System.out.println(message);
            String hello = "HELLO";
            String dow = "DOW";
            String time = "TIME";
            String date = "DATE";
            String bye = "BYE";
            String lost = "LOST";
            if (message.toUpperCase().equals(hello)) {
                clientInterraction(socket);
            }
            if (message.toUpperCase().equals(dow)) {
                LocalDate localDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EE");
                String information = localDate.format(formatter);
                System.out.println("Server responds with the day of the week");
                sendMessage(socket, information);
                clientInterraction(socket);
            } else if (message.toUpperCase().equals(time)) {
                LocalTime localTime = LocalTime.now();
                String information = "" + localTime;
                System.out.println("Server responds with the time of the day");
                sendMessage(socket, information);
                clientInterraction(socket);
            } else if (message.toUpperCase().equals(date)) {
                LocalDate localDate = LocalDate.now();
                String information = "" + localDate;
                System.out.println("Server responds with the current date");
                sendMessage(socket, information);
                clientInterraction(socket);
            } else if (message.toUpperCase().equals(bye)) {
                String byeMessage = "BYE << IP:" + socket.getRemoteSocketAddress() + " >>";
                sendMessage(socket, byeMessage);
                System.out.println(byeMessage);
                return;
            } else if (message.toUpperCase().equals(lost)) {
                String byeMessage = "CLIENT << IP:" + socket.getRemoteSocketAddress() + " >> LOST CONNECTION";
                System.out.println(byeMessage);
                return;
            }
        } catch (Exception x) {
            System.out.println("Exception: " + x.getMessage());
            return;
        }
    }
}