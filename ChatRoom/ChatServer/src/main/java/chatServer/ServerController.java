package chatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class ServerController implements Initializable {
    
    @FXML
    private TextArea textArea; //gets text area from FXML file
    
    private int clientNum = 0; // keeps track of the number of clients
    private Transcript transcript;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        transcript = new Transcript();

        textArea.setStyle("-fx-control-inner-background: rgb(173,216,230)"); // changes the rest of the background
        // to blue

        new Thread( () -> {
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);
        
        while (true) {
          // Listen for a new connection request
          Socket socket = serverSocket.accept();
    
          // Increment clientNum
          clientNum++;
          
          Platform.runLater( () -> {
            // Display the client number
            textArea.appendText("Starting thread for client " + clientNum +
              " at " + new Date() + '\n');
            });
          
          // Create and start a new thread for the connection
          new Thread(new HandleAClient(socket,transcript,textArea)).start();
        }
      }
      catch(IOException ex) {
        System.err.println(ex);
      }
    }).start();
    }    
    
}

class HandleAClient implements Runnable, ServerConstants {
    private Socket socket; // A connected socket
    private Transcript transcript; // Reference to shared transcript
    private TextArea textArea;
    private String handle;

    public HandleAClient(Socket socket,Transcript transcript,TextArea textArea) {
      this.socket = socket;
      this.transcript = transcript;
      this.textArea = textArea;
    }

    public void run() {
      try {
        // Create reading and writing streams
        BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter outputToClient = new PrintWriter(socket.getOutputStream());

        // Continuously serve the client
        while (true) {
          // Receive request code from the client
          int request = Integer.parseInt(inputFromClient.readLine());
          // Process request
          switch(request) {
              case SEND_HANDLE: {
                  handle = inputFromClient.readLine();
                  break;
              }
              case SEND_TEXT: {
                  String comment = inputFromClient.readLine();
                  transcript.addText(handle + ": " + comment);
                  break;
              }
              case GET_TEXT_COUNT: {
                  outputToClient.println(transcript.getSize());
                  outputToClient.flush();
                  break;
              }
              case GET_TEXT: {
                  int n = Integer.parseInt(inputFromClient.readLine());
                  outputToClient.println(transcript.getComment(n));
                  outputToClient.flush();
              }
          }
        }
      }
      catch(IOException ex) {
          Platform.runLater(()->textArea.appendText("Exception in client thread: "+ex.toString()+"\n"));
      }
    }
  }