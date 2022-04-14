package chatClient;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.application.Platform;

/** Represents an employee.
 * @author Vrund Patel
 * @author Vishwaajeeth Kamalakkannan
 * @author Haider Bajwa
 * @author Fahad Fauzan
 * @version 1.0

 This chat client communicates with other clients through a multi-threaded server.
 */

public class Client extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480); // Loads FXML file
        scene.getRoot().setStyle("-fx-background-color: orange"); // formats the scene for text box
        stage.setScene(scene);

        stage.setTitle("Client Chat");
        stage.setOnCloseRequest(e->{Platform.exit();System.exit(0);});
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml)); // Loads FXML and sets it as scene root
    }

    private static Parent loadFXML(String fxml) throws IOException { // Loads FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}