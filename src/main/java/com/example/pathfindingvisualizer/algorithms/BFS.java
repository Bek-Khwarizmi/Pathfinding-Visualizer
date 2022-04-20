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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class BFS {
    private Timeline timelineBFS;
    private ArrayList<View.Cell> path;
    private ArrayList<View.Cell> innerLayer;
    private View.Cell[][] board;
    private View.Cell start, target;
    private Queue<View.Cell> outerLayer;
    private View.Cell current;

    public BFS(View.Cell[][] board, View.Cell start, View.Cell target) {
        this.timelineBFS = new Timeline();
        this.path = new ArrayList<>();
        this.innerLayer = new ArrayList<>();
        this.outerLayer = new LinkedList<>();
        this.board = board;
        this.start = start;
        this.target = target;
        this.current = start;
        current.setDistance(0);
        outerLayer.offer(current);
    }

    public void animateBFS(){
        AtomicInteger countBFS = new AtomicInteger();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(10), event ->{
            countBFS.getAndIncrement();
            if(current!=target) {
                if(outerLayer.isEmpty()){
                    timelineBFS.stop();
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
                }else{
                    generateBFS();
                }
            }else{
                displayPath();
            }
        });
        timelineBFS.setCycleCount(Animation.INDEFINITE);
        timelineBFS.getKeyFrames().add(keyFrame);
        timelineBFS.play();
    }

    public void generateBFS(){
        current = outerLayer.poll();
        innerLayer.add(current);
        ArrayList<View.Cell> neighbours = current.getNeighbours(1);
        for (View.Cell neighbour : neighbours) {
            if (!neighbour.isWall() && neighbour.getDistance() == -1) {
                neighbour.setDistance(current.getDistance() + 1);
                neighbour.setParent(current);
                outerLayer.offer(neighbour);
            }
        }
        displayBFS();
    }

    public void displayBFS(){
        for (View.Cell item: outerLayer){
            item.setColor(Color.BLUE);
        }
        for (View.Cell item: innerLayer){
            item.setColor(Color.LIGHTGREEN);
        }
        start.setColor(Color.BLUE);
        target.setColor(Color.MAGENTA);
    }

    public void displayPath(){
        while (current != start) {
            current.setColor(Color.YELLOW);
            current = current.getParent();
        }
        timelineBFS.stop();
    }

    public void setTimelineBFSStop(){
        this.timelineBFS.stop();
    }

    public double getTimelineRate() {
        return timelineBFS.getRate();
    }

    public void setTimelineRate(double rate) {
        this.timelineBFS.setRate(rate);
    }
}
