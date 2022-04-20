package com.example.pathfindingvisualizer.algorithms;

import com.example.pathfindingvisualizer.util.View;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DFS {
    private Timeline timelineDFS;
    private Stack<View.Cell> path;
    private View.Cell[][] board;
    private View.Cell start, target;
    private View.Cell current;

    public DFS(View.Cell[][] board, View.Cell start, View.Cell target) {
        this.timelineDFS = new Timeline();
        this.path = new Stack<>();
        this.board = board;
        this.start = start;
        this.target = target;
        path.push(start);
        this.current = start;
        current.setDistance(0);
    }

    public void animateDFS(){
        AtomicInteger countDFS = new AtomicInteger();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(10), event ->{
            countDFS.getAndIncrement();
            if(current!=target) {
                generateDFS();
            }else{
                displayPath();
            }
        });
        timelineDFS.setCycleCount(Animation.INDEFINITE);
        timelineDFS.getKeyFrames().add(keyFrame);
        timelineDFS.play();
    }

    public void generateDFS(){
        ArrayList<View.Cell> neighbours = current.getNeighbours(1);
        if(!neighbours.isEmpty() && visitedCount(neighbours)){
            View.Cell next = current;
            for (View.Cell item: neighbours) {
                if (item.getDistance() != 0 && !item.isWall()){
                    next = item;
                    item.setDistance(0);
                    break;
                }
            }
            path.push(next);
            next.setParent(current);
            current = next;
        }else{
            while(neighbours.isEmpty() || !visitedCount(neighbours)){
                if(path.isEmpty()) {
                    timelineDFS.stop();
                    Pane warningPane = new Pane();
                    warningPane.setPrefHeight(150);
                    warningPane.setPrefWidth(440);
                    Stage warningStage = new Stage();
                    warningStage.setTitle("Warning");
                    Text text = new Text(170, 70, "Path not found");
                    text.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 17));
                    warningPane.getChildren().add(text);
                    warningStage.setScene(new Scene(warningPane,440,150));
                    warningStage.show();
                    break;
                }
                current = path.pop();
                neighbours = current.getNeighbours(1);
            }
            if(!path.isEmpty()) generateDFS();
        }
        displayDFS();
    }

    public boolean visitedCount(ArrayList<View.Cell> neighbours){
        int count = 0;
        for (View.Cell item: neighbours) if (item.getDistance()==0 || item.isWall()) count++;
        return count!=neighbours.size();
    }

    public void displayDFS(){
        for (View.Cell item: path){
            item.setColor(Color.LIGHTGREEN);
        }
        current.setColor(Color.BLUE);
        start.setColor(Color.BLUE);
        target.setColor(Color.MAGENTA);
    }

    public void displayPath(){
        while (current != start) {
            current.setColor(Color.YELLOW);
            current = current.getParent();
        }
        timelineDFS.stop();
    }

    public void setTimelineDFSStop(){
        this.timelineDFS.stop();
    }

    public double getTimelineRate() {
        return timelineDFS.getRate();
    }

    public void setTimelineRate(double rate) {
        this.timelineDFS.setRate(rate);
    }
}
