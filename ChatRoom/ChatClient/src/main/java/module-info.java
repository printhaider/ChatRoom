module chatClient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens chatClient to javafx.fxml;
    exports chatClient;
}