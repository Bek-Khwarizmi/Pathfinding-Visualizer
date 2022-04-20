package com.example.pathfindingvisualizer;

import com.example.pathfindingvisualizer.util.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        View viewPane = new View();
        BorderPane pane = viewPane.getPane();

        Scene scene = new Scene(pane, 1500, 750);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        pane.requestFocus();
    }

    public static void main(String[] args){
        launch(args);
    }
}