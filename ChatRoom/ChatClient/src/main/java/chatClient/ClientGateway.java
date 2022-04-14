package chatClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class ClientGateway implements ClientConstants {

    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
    private TextArea textArea;
    // Establish the connection to the server.
    public ClientGateway(TextArea textArea) {
        this.textArea = textArea;
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);

            // Create an output stream to send data to the server
            outputToServer = new PrintWriter(socket.getOutputStream());

            // Create an input stream to read data from the server
            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Exception in gateway constructor: " + ex.toString() + "\n"));
        }
    }

    // Start the chat by sending in the user's handle.
    public synchronized void sendHandle(String handle) {
        outputToServer.println(SEND_HANDLE);
        outputToServer.println(handle);
        outputToServer.flush();
    }

    // Send a new comment to the server.
    public synchronized void sendText(String comment) {
        outputToServer.println(SEND_TEXT);
        outputToServer.println(comment);
        outputToServer.flush();
    }

    // Ask the server to send us a count of how many comments are
    // currently in the transcript.
    public synchronized int getTextCount() {
        outputToServer.println(GET_TEXT_COUNT);
        outputToServer.flush();
        int count = 0;
        try {
            count = Integer.parseInt(inputFromServer.readLine());
        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Error in getTextCount: " + ex.toString() + "\n"));
        }
        return count;
    }

    // Fetch comment n of the transcript from the server.
    public synchronized String getText(int n) {
        outputToServer.println(GET_TEXT);
        outputToServer.println(n);
        outputToServer.flush();
        String comment = "";
        try {
            comment = inputFromServer.readLine();
        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Error in getText: " + ex.toString() + "\n"));
        }
        return comment;
    }
}
