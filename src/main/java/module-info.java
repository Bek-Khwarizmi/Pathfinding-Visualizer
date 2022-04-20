module com.example.pathfindingvisualizer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.pathfindingvisualizer to javafx.fxml;
    exports com.example.pathfindingvisualizer.util;
    opens com.example.pathfindingvisualizer.util to javafx.fxml;
    exports com.example.pathfindingvisualizer;
}