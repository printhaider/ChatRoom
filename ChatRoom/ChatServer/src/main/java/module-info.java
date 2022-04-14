module chatServer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    exports chatServer;
    opens chatServer to javafx.fxml;
}