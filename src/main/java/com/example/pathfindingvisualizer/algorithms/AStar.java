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
import java.util.concurrent.atomic.AtomicInteger;

public class AStar {
    private boolean targetIsReached;
    private Timeline timelineAStar;
    private ArrayList<View.Cell> minPathForTarget;
    private ArrayList<View.Cell> firstLayer, secondLayer;
    private View.Cell[][] board;
    private View.Cell start, target;

    public AStar(View.Cell[][] board, View.Cell start, View.Cell target) {
        this.targetIsReached = false;
        this.timelineAStar = new Timeline();
        this.minPathForTarget = new ArrayList<>();
        this.firstLayer = new ArrayList<>();
        this.secondLayer = new ArrayList<>();
        this.board = board;
        this.start = start;
        firstLayer.add(start);
        this.target = target;
    }

    public void animateAStar(){
        AtomicInteger count = new AtomicInteger();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(10), event ->{
            count.getAndIncrement();
            displayAStar();
        });
        timelineAStar.setCycleCount(Animation.INDEFINITE);
        timelineAStar.getKeyFrames().add(keyFrame);
        timelineAStar.play();
    }

    public void displayAStar(){
        View.Cell temp = null;
        if (!firstLayer.isEmpty()){
            int winner = 0;
            for (int i=0; i<firstLayer.size();i++){
                if (firstLayer.get(i).getF()<firstLayer.get(winner).getF()){
                    winner = i;
                }
            }

            View.Cell current = firstLayer.get(winner);
            temp = current;
            if (current == target){
                minPathForTarget.clear();
                minPathForTarget.add(temp);
                while (temp.getParent()!=null){
                    minPathForTarget.add(temp.getParent());
                    temp=temp.getParent();
                }
                targetIsReached = true;
            }

            firstLayer.remove(current);
            secondLayer.add(current);

            if (!targetIsReached){
                minPath(current);
            }
        }else{
            timelineAStar.stop();
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
        }
        if (temp!=null){
            for (View.Cell[] cells: board){
                for (View.Cell cell: cells){
                    if(!cell.isWall()) cell.setColor(Color.ALICEBLUE);
                }
            }

            for (View.Cell cell: firstLayer){
                cell.setColor(Color.BLUE);
            }

            for (View.Cell cell: secondLayer){
                cell.setColor(Color.LIGHTGREEN);
            }

            for (View.Cell cell: minPathForTarget){
                cell.setColor(Color.YELLOW);
            }

            temp.setColor(Color.BLUEVIOLET);
            start.setColor(Color.RED);
            target.setColor(Color.MAGENTA);
            if (targetIsReached) timelineAStar.stop();
        }
    }

    public void minPath(View.Cell current){
        for (View.Cell neighbour: current.getNeighbours(1)){
            if (secondLayer.contains(neighbour)||neighbour.isWall() ) continue;
            double tempG = current.getG()+1;
            boolean newPath = false;
            if (firstLayer.contains(neighbour)){
                if (tempG< neighbour.getG()){
                    neighbour.setG(tempG);
                    newPath = true;
                }
            }else{
                neighbour.setG(tempG);
                firstLayer.add(neighbour);
                newPath = true;
            }
            if (newPath){
                neighbour.setHx(distance(neighbour, target));
                neighbour.setF(neighbour.getG()+neighbour.getHx());
                neighbour.setParent(current);
            }
        }
    }

    public double distance(View.Cell from, View.Cell to){
        int x1 = from.getColumn();
        int y1 = from.getRow();
        int x2 = to.getColumn();
        int y2 =to.getRow();
        return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    }

    public void setTimelineAStarStop() {
        this.timelineAStar.stop();
    }

    public double getTimelineRate() {
        return timelineAStar.getRate();
    }

    public void setTimelineRate(double rate) {
        this.timelineAStar.setRate(rate);
    }
}
