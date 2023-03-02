import java.net.*;
import java.io.*;
import java.util.*;

public class ClientApp {
    public static void main(String[] args) {
        welcomeMessage();
        commands();
    }

    public static void connectServer(String input) {
        try (Socket socket = new Socket("localhost", 431)) {
            sendServerMessage(socket, input);
            receiveServerMessage(socket);
            commands(socket);
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
            System.exit(0);
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
            commands();
        }
    }

    public static void sendServerMessage(Socket socket, String input) throws IOException {
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println(input);
    }

    public static void receiveServerMessage(Socket socket) throws IOException {
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = reader.readLine();
        System.out.println(line);
    }

    public static void welcomeMessage() {
        System.out.println("Welcome to the Client Application");
        System.out.println();
        availableCommands();
    }

    public static void availableCommands() {
        System.out.println("Available commands :");
        System.out.println("Connect = Connect to the server");
        System.out.println("Get DOW = Ask server for current day of the week");
        System.out.println("Get Time = Ask server for current time");
        System.out.println("Get Date = Ask server for current date");
        System.out.println("Exit = Close the Client Application");
        System.out.println("Help = Display available commands");
    }

    public static String inputScanner() {
        Scanner inputScanner = new Scanner(System.in);
        String message = inputScanner.nextLine();
        return message;
    }

    // Commands for the application
    public static void commands() {
        System.out.println();
        String input = inputScanner().toUpperCase();
        String hello = "CONNECT";
        String dow = "GET DOW";
        String time = "GET TIME";
        String date = "GET DATE";
        String exit = "EXIT";
        String help = "HELP";
        // Help and About return all available commands
        if (input.toUpperCase().equals(help)) {
            System.out.println();
            availableCommands();
            commands();
        }
        // Verifies if string is hello and connects to server
        if (input.toUpperCase().equals(hello)) {
            connectServer(input);
            // Needed connection for these commands
        } else if (input.toUpperCase().equals(dow) || input.toUpperCase().equals(time)
                || input.toUpperCase().equals(date)) {
            System.out.println("REQUIRES SERVER CONNECTION");
            commands();
            // Exit closes client application
        } else if (input.toUpperCase().equals(exit)) {
            System.out.println("Closing Client Application.");
            // Disconnect from server and close application
            System.exit(0);
            // Invalid input, prompts again
        } else {
            System.out.println("INVALID COMMAND");
            commands();
        }
    }

    // Commands while connected to the server
    public static void commands(Socket socket) throws IOException {
        System.out.println();
        String input = inputScanner().toUpperCase();
        String hello = "CONNECT";
        String dow = "GET DOW";
        String time = "GET TIME";
        String date = "GET DATE";
        String exit = "EXIT";
        String help = "HELP";
        if (input.toUpperCase().equals(help)) {
            System.out.println();
            availableCommands();
            commands(socket);
        }
        // Command hello closes current connection and connects again
        if (input.toUpperCase().equals(hello)) {
            System.out.println("Already connected, disconnecting...");
            sendServerMessage(socket, "BYE");
            receiveServerMessage(socket);
            System.out.println("Disconnected.");
            System.out.println("Reconnecting...");
            connectServer(input);
            // Commands send message to server and wait for response
        } else if (input.toUpperCase().equals(dow)) {
            sendServerMessage(socket, "DOW");
            receiveServerMessage(socket);
            commands(socket);
        } else if (input.toUpperCase().equals(time)) {
            sendServerMessage(socket, "TIME");
            receiveServerMessage(socket);
            commands(socket);
        } else if (input.toUpperCase().equals(date)) {
            sendServerMessage(socket, "DATE");
            receiveServerMessage(socket);
            commands(socket);
            // Command disconnects from server
        } else if (input.toUpperCase().equals(exit)) {
            sendServerMessage(socket, "BYE");
            receiveServerMessage(socket);
            System.out.println();
            System.out.println("Closing Client Application...");
            System.exit(0);
        } else {
            System.out.println("INVALID COMMAND");
            commands(socket);
        }
    }
}