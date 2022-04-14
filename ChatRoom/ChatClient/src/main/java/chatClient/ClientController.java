package chatClient;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ClientController implements Initializable {

    private ClientGateway gateway;
    @FXML
    private TextArea textArea; //gets text area from FXML file
    @FXML
    private TextField clientText; //gets client text from FXML file
    @FXML
    private Button sendButton; //gets send button from FXML file
    
    @FXML
    private void sendText(ActionEvent event) { // sends text to server
        String text = clientText.getText();
        gateway.sendText(text);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gateway = new ClientGateway(textArea);

        //rgb(173,216,230) <- The Colour Light Blue
        sendButton.setStyle("-fx-color: Black"); // changes button colour to black
        clientText.setStyle("-fx-background-color: Orange"); // changes user input bar to orange
        textArea.setStyle("-fx-control-inner-background: rgb(173,216,230)"); // changes the rest of the background
                                                                                // to blue

        // Put up a dialog to get a handle from the user
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Start Chat");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter a handle:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> gateway.sendHandle(name));

        // Start the transcript check thread
        new Thread(new TranscriptCheck(gateway,textArea)).start();
    }    
    
}

class TranscriptCheck implements Runnable, ClientConstants {
    private ClientGateway gateway; // Gateway to the server
    private TextArea textArea; // Where to display comments
    private int N; // How many texts we have read
    
    /** Construct a thread */
    public TranscriptCheck(ClientGateway gateway, TextArea textArea) {
      this.gateway = gateway;
      this.textArea = textArea;
      this.N = 0;
    }

    /** Run a thread */
    public void run() {
      while(true) {
          if(gateway.getTextCount() > N) {
              String newText = gateway.getText(N); // gets text
              Platform.runLater(()->textArea.appendText(newText + "\n")); // displays it for all clients
              N++;
          } else {
              try {
                  Thread.sleep(250);
              } catch(InterruptedException ex) {}
          }
      }
    }
  }