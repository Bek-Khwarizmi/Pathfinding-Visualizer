package com.example.pathfindingvisualizer.borderOrMaze;

import com.example.pathfindingvisualizer.util.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicInteger;

public class Border {
    private int borderCol;
    private int borderRow;
    private Timeline timelineBorder;
    private View.Cell[][] board;
    private View.Cell start, target;

    public Border(View.Cell[][] board, View.Cell start, View.Cell target){
        this.board = board;
        this.borderCol = 0;
        this.borderRow = 0;
        this.timelineBorder = new Timeline();
        this.start = start;
        this.target = target;
    }

    public void setTimelineBorderStop() {
        this.timelineBorder.stop();
    }

    public void setUpBorder(){
        for (int i=0; i<80;i++){
            for (int j=0;j<50;j++){
                board[i][j].setWall(false);
                if(board[i][j]!=start && board[i][j]!=target) {
                    board[i][j].setColor(Color.ALICEBLUE);
                }
            }
        }
        generateBorder();
    }

    public void generateBorder(){
        for (int i=0; i<80;i++){
            for (int j=0;j<50;j++){
                if(board[i][j]!=start && board[i][j]!=target && Math.random()<0.3) {
                    board[i][j].setWall(true);
                }
            }
        }
        animateBorder();
    }

    public void animateBorder(){
        AtomicInteger count1 = new AtomicInteger();
        KeyFrame keyFrameBorder = new KeyFrame(Duration.millis(1), event ->{
            count1.getAndIncrement();
            displayBorder();
        });
        timelineBorder.setCycleCount(Animation.INDEFINITE);
        timelineBorder.getKeyFrames().add(keyFrameBorder);
        timelineBorder.play();
    }

    public void displayBorder(){
        if(borderCol==79 && borderRow==49) timelineBorder.stop();
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
