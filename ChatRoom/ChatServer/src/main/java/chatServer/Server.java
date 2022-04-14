package chatServer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.application.Platform;

/** Represents an employee.
 * @author Vrund Patel
 * @author Vishwaajeeth Kamalakkannan
 * @author Haider Bajwa
 * @author Fahad Fauzan
 * @version 1.0

This multi-threaded server enables multiple clients to communicate with each other.
 */

public class Server extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480); // Loads FXML file
        stage.setScene(scene);
        stage.setTitle("Server");
        stage.setOnCloseRequest(e->{Platform.exit();System.exit(0);});
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml)); // Loads FXML and sets it as scene root
    }

    private static Parent loadFXML(String fxml) throws IOException { // Loads FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(Server.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}