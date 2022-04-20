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

public class Dijkstra {
    private Timeline timelineDijkstra;
    private ArrayList<View.Cell> path;
    private ArrayList<View.Cell> innerLayer;
    private View.Cell[][] board;
    private View.Cell start, target;
    private Queue<View.Cell> outerLayer;
    private View.Cell current;

    public Dijkstra(View.Cell[][] board, View.Cell start, View.Cell target) {
        this.timelineDijkstra = new Timeline();
        this.path = new ArrayList<>();
        this.innerLayer = new ArrayList<>();
        this.outerLayer = new PriorityQueue<View.Cell>(new CellDistanceFromGoalComparator());
        this.board = board;
        this.start = start;
        this.target = target;
        this.current = start;
        current.setDistance(0);
        outerLayer.offer(current);
    }

    public void animateDijkstra(){
        AtomicInteger countDijkstra = new AtomicInteger();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(10), event ->{
            countDijkstra.getAndIncrement();
            if(current!=target) {
                if(outerLayer.isEmpty()){
                    timelineDijkstra.stop();
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
                    generateDijkstra();
                }
            }else{
                displayPath();
            }
        });
        timelineDijkstra.setCycleCount(Animation.INDEFINITE);
        timelineDijkstra.getKeyFrames().add(keyFrame);
        timelineDijkstra.play();
    }

    public void generateDijkstra(){
        current = outerLayer.poll();
        innerLayer.add(current);
        List<View.Cell> neighbours = current.getNeighbours(1);
        for (View.Cell neighbour : neighbours) {
            if (!neighbour.isWall() && neighbour.getDistance() == -1) {
                neighbour.setDistance(current.getDistance() + 1);
                neighbour.setParent(current);
                outerLayer.offer(neighbour);
            }
        }
        displayDijkstra();
    }

    public void displayDijkstra(){
        for (View.Cell item: outerLayer){
            item.setColor(Color.BLUE);
        }
        for (View.Cell item: innerLayer){
            item.setColor(Color.LIGHTGREEN);
        }
        current.setColor(Color.YELLOW);
        start.setColor(Color.BLUE);
        target.setColor(Color.MAGENTA);
    }

    public void displayPath(){
        while (current != start) {
            current.setColor(Color.YELLOW);
            current = current.getParent();
        }
        timelineDijkstra.stop();
    }

    public void setTimelineDijkstraStop(){
        this.timelineDijkstra.stop();
    }

    public double getTimelineRate() {
        return timelineDijkstra.getRate();
    }

    public void setTimelineRate(double rate) {
        this.timelineDijkstra.setRate(rate);
    }

    private class CellDistanceFromGoalComparator implements Comparator<View.Cell> {
        public double distanceToGoal(View.Cell from){
            return Math.hypot(from.getColumn() - target.getColumn(), from.getRow() - target.getRow());
        }
        @Override
        public int compare(View.Cell cell1, View.Cell cell2) {
            if (distanceToGoal(cell1) > distanceToGoal(cell2)) {
                return 1;
            } else {
                return distanceToGoal(cell1) < distanceToGoal(cell2) ? -1 : 0;
            }
        }
    }
}
