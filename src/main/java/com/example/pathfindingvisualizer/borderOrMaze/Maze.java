package com.example.pathfindingvisualizer.borderOrMaze;

import com.example.pathfindingvisualizer.util.View;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class Maze {
    private int borderCol;
    private int borderRow;
    private Timeline timelineMaze;
    private View.Cell[][] board;
    private View.Cell start, target;

    public Maze(View.Cell[][] board, View.Cell start, View.Cell target){
        this.board = board;
        this.borderCol = 0;
        this.borderRow = 0;
        this.timelineMaze = new Timeline();
        this.start = start;
        this.target = target;
    }

    public void setTimelineMazeStop() {
            this.timelineMaze.stop();
    }

    public void setUpMaze(){
        for (int i=0; i<80;i++){
            for (int j=0;j<50;j++){
                board[i][j].setVisited(false);
                if(board[i][j]!=start && board[i][j]!=target) {
                    board[i][j].setColor(Color.ALICEBLUE);
                    board[i][j].setWall(true);
                }
            }
        }

        generateMaze();
    }

    public void generateMaze(){
        Stack<View.Cell> track = new Stack<>();
        track.push(board[3][3]);
        board[3][3].setVisited(true);
        int numOfVisitedCells = 1;
        View.Cell current;

        while(numOfVisitedCells<1000){
            current = track.peek();
            ArrayList<View.Cell> neighbours = current.getNeighbours(2);

            int visitedCount = 0;
            for (View.Cell item: neighbours){
                if (item.isVisited()) visitedCount++;
            }
            if (visitedCount == neighbours.size()){
                track.pop();
            }else{
                View.Cell next = neighbours.get((int)(Math.random()*(neighbours.size())));
                while(next.isVisited()){
                    next = neighbours.get((int)(Math.random()*(neighbours.size())));
                }
                current.removeWalls(next);
                next.setVisited(true);
                track.push(next);
                numOfVisitedCells++;
            }
        }
        animateMaze();
    }

    public void animateMaze(){
        AtomicInteger count2 = new AtomicInteger();
        KeyFrame keyFrameMaze = new KeyFrame(Duration.millis(2), event ->{
            count2.getAndIncrement();
            displayMaze();
        });
        timelineMaze.setCycleCount(Animation.INDEFINITE);
        timelineMaze.getKeyFrames().add(keyFrameMaze);
        timelineMaze.play();
    }

    public void displayMaze(){
        if(borderCol==79 && borderRow==49) timelineMaze.stop();
        for (int row = 0; row<borderRow-1; row++){
            for (int col = 0; col<80;col++){
                if(board[col][row].isWall()) board[col][row].setColor(Color.BLACK);
            }
        }
        for (int col = 0; col<=borderCol; col++){
            if(board[col][borderRow].isWall()) board[col][borderRow].setColor(Color.BLACK);
        }
        borderCol++;
        if(borderCol == 80) {
            borderRow++;
            borderCol=0;
        }
    }
}
