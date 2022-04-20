package com.example.pathfindingvisualizer.util;

import com.example.pathfindingvisualizer.Main;
import com.example.pathfindingvisualizer.algorithms.AStar;
import com.example.pathfindingvisualizer.algorithms.BFS;
import com.example.pathfindingvisualizer.algorithms.DFS;
import com.example.pathfindingvisualizer.algorithms.Dijkstra;
import com.example.pathfindingvisualizer.borderOrMaze.Border;
import com.example.pathfindingvisualizer.borderOrMaze.Maze;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class View extends Main{
    CheckBox chkN, chkNE, chkE, chkSE, chkS, chkSW, chkW, chkNW;
    BorderPane borderPane = new BorderPane();
    Pane pane = new Pane();
    Cell[][] board = new Cell[80][50];
    Cell start, target;

    Border border;
    Maze maze;
    AStar astar;
    Dijkstra dijkstra;
    BFS bfs;
    DFS dfs;

    public BorderPane getPane(){
//------------------------------UI--------------------------------
        RadioButton btnForDijkstra, btnForBFS, btnForDFS, btnForAStar;
        RadioButton btnBRM, btnForRD;
        Button btnForGeneratingMaze, btnVisualize, btnReset;

        VBox boxForControls;{
            boxForControls = new VBox();
            boxForControls.setPadding(new Insets(10, 0, 0, 0));
            boxForControls.setSpacing(10);
            boxForControls.setPrefWidth(300);
            boxForControls.setPrefHeight(800);
            boxForControls.setStyle("-fx-background-color: cadetblue");
        }
        Font font, fontForInfo; Color fontColor;{
            font = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 14);
            fontForInfo = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 17);
            fontColor = Color.DARKBLUE;
        }
        //Adding directions:
        BorderPane paneForDirections; Label lbForDirections;{
            paneForDirections = new BorderPane();
            paneForDirections.setPadding(new Insets(0,5,5,70));
            lbForDirections = new Label("\t\tChoose directions:", paneForDirections);
            lbForDirections.setContentDisplay(ContentDisplay.BOTTOM);
            lbForDirections.setFont(fontForInfo);
            lbForDirections.setTextFill(fontColor);
            ImageView image = new ImageView("C:\\PathfindingVisualizer\\src\\main\\java\\com\\example\\pathfindingvisualizer\\images\\directions.gif");
            image.setFitWidth(100);
            image.setFitHeight(100);
            HBox boxTop = new HBox();
            HBox boxBottom = new HBox();
            chkNW = new CheckBox();
            chkN = new CheckBox();
            chkNE = new CheckBox();
            chkE = new CheckBox();
            chkSE = new CheckBox();
            chkS = new CheckBox();
            chkSW = new CheckBox();
            chkW = new CheckBox();
            BorderPane.setAlignment(chkW, Pos.CENTER);
            BorderPane.setAlignment(chkE, Pos.CENTER);
            boxTop.getChildren().addAll(chkNW, chkN, chkNE);
            boxBottom.getChildren().addAll(chkSW,chkS, chkSE);
            paneForDirections.setTop(boxTop);
            paneForDirections.setBottom(boxBottom);
            paneForDirections.setLeft(chkW);
            paneForDirections.setRight(chkE);
            boxTop.setSpacing(40);
            boxTop.setPadding(new Insets(0,0,3,0));
            boxBottom.setSpacing(40);
            boxBottom.setPadding(new Insets(3,0,0,0));
            paneForDirections.setCenter(image);
            boxForControls.getChildren().add(lbForDirections);
        }
        //Label for picking an algorithm:
        VBox boxForAlgorithms; Label lbForAlgorithms; {
            boxForAlgorithms = new VBox();
            lbForAlgorithms = new Label("Pick an Algorithm:", boxForAlgorithms);
            boxForAlgorithms.setSpacing(5);
            boxForAlgorithms.setPadding(new Insets(0,5,5,15));

            btnForDijkstra = new RadioButton("Minimum path finder");
            btnForDijkstra.setPrefWidth(250);
            btnForDijkstra.setFont(font);
            btnForDijkstra.setTextFill(fontColor);;
            btnForBFS = new RadioButton("Breadth-first Search");
            btnForBFS.setPrefWidth(250);
            btnForBFS.setFont(font);
            btnForBFS.setTextFill(fontColor);
            btnForDFS = new RadioButton("Depth-first Search");
            btnForDFS.setPrefWidth(250);
            btnForDFS.setFont(font);
            btnForDFS.setTextFill(fontColor);;
            btnForAStar = new RadioButton("A* Search");
            btnForAStar.setPrefWidth(250);
            btnForAStar.setFont(font);
            btnForAStar.setTextFill(fontColor);
            boxForAlgorithms.getChildren()
                    .addAll(btnForDijkstra, btnForBFS,
                            btnForDFS, btnForAStar);
            ToggleGroup groupForAlgorithms = new ToggleGroup();
            btnForDijkstra.setToggleGroup(groupForAlgorithms);
            btnForBFS.setToggleGroup(groupForAlgorithms);
            btnForDFS.setToggleGroup(groupForAlgorithms);
            btnForAStar.setToggleGroup(groupForAlgorithms);

            lbForAlgorithms.setFont(fontForInfo);
            lbForAlgorithms.setTextFill(fontColor);
            lbForAlgorithms.setContentDisplay(ContentDisplay.BOTTOM);
        }
        //Label for picking a maze/border creating algorithm:
        VBox boxForMazes; Label lbForMazes; {
            boxForMazes = new VBox();
            lbForMazes = new Label("Add a maze/border:", boxForMazes);
            boxForMazes.setPadding(new Insets(0,5,5,15));
            boxForMazes.setSpacing(5);

            btnBRM = new RadioButton("Randomly generated border");
            btnBRM.setPrefWidth(250);
            btnBRM.setFont(font);
            btnBRM.setTextFill(fontColor);
            btnForRD = new RadioButton("Recursive generated maze");
            btnForRD.setPrefWidth(250);
            btnForRD.setFont(font);
            btnForRD.setTextFill(fontColor);
            btnForGeneratingMaze = new Button("Generate");
            btnForGeneratingMaze.setPrefWidth(250);
            btnForGeneratingMaze.setFont(font);
            btnForGeneratingMaze.setTextFill(fontColor);

            boxForMazes.getChildren()
                    .addAll(btnBRM, btnForRD, btnForGeneratingMaze);

            ToggleGroup groupForMazes = new ToggleGroup();
            btnBRM.setToggleGroup(groupForMazes);
            btnForRD.setToggleGroup(groupForMazes);

            lbForMazes.setContentDisplay(ContentDisplay.BOTTOM);
            lbForMazes.setFont(fontForInfo);
            lbForMazes.setTextFill(fontColor);
            lbForMazes.setStyle("-fx-background-color: lightgray");
            lbForMazes.setPrefWidth(280);
        }
        //Adding control Buttons:
        HBox boxForVisualizeAndResetButtons; {
            boxForVisualizeAndResetButtons = new HBox();
            boxForVisualizeAndResetButtons.setSpacing(10);
            boxForVisualizeAndResetButtons.setPadding(new Insets(0,0,0,15));

            btnVisualize = new Button("Visualize");
            btnVisualize.setPrefWidth(120);
            btnVisualize.setFont(font);
            btnVisualize.setTextFill(fontColor);
            btnReset = new Button("Reset");
            btnReset.setPrefWidth(120);
            btnReset.setFont(font);
            btnReset.setTextFill(fontColor);

            boxForVisualizeAndResetButtons.getChildren().addAll(btnVisualize, btnReset);
        }
        //Pane for upper part:
        {
            VBox boxForFirstPart = new VBox();
            boxForFirstPart.setPadding(new Insets(0,0,0,10));
            boxForFirstPart.setSpacing(10);

            boxForFirstPart.getChildren().add(lbForAlgorithms);
            boxForFirstPart.getChildren().add(lbForMazes);
            boxForFirstPart.getChildren().add(boxForVisualizeAndResetButtons);

            boxForControls.getChildren().add(boxForFirstPart);
        }
        //Information:
        VBox boxForInformation; Label lbForInformation; {
            boxForInformation = new VBox();
            lbForInformation = new Label("\t\t\tInformation", boxForInformation);
            boxForInformation.setPadding(new Insets(2,5,5,20));
            boxForInformation.setSpacing(4);

            Rectangle startNode = new Rectangle(0,0,24,24);
            startNode.setStroke(Color.BLACK);
            startNode.setFill(Color.BLUE);
            Label lbForStartNode = new Label("Start Node", startNode);
            lbForStartNode.setFont(font);

            Rectangle targetNode = new Rectangle(0,0,24,24);
            targetNode.setStroke(Color.BLACK);
            targetNode.setFill(Color.MAGENTA);
            Label lbForTargetNode = new Label("Target Node", targetNode);
            lbForTargetNode.setFont(font);

            Rectangle unvisitedNode = new Rectangle(0,0,24,24);
            unvisitedNode.setStroke(Color.BLACK);
            unvisitedNode.setFill(Color.ALICEBLUE);
            Label lbForUnvisitedNode = new Label("Unvisited Node", unvisitedNode);
            lbForUnvisitedNode.setFont(font);

            Rectangle lastVisitedNode = new Rectangle(0,0,24,24);
            lastVisitedNode.setStroke(Color.BLACK);
            lastVisitedNode.setFill(Color.LIGHTGREEN);
            Label lbForLastVisitedNode = new Label("Visited Node", lastVisitedNode);
            lbForLastVisitedNode.setFont(font);

            Rectangle shortestPathNode = new Rectangle(0,0,24,24);
            shortestPathNode.setStroke(Color.BLACK);
            shortestPathNode.setFill(Color.YELLOW);
            Label lbForShortestPathNode = new Label("Path Node", shortestPathNode);
            lbForShortestPathNode.setFont(font);

            Rectangle wallNode = new Rectangle(0,0,24,24);
            wallNode.setStroke(Color.BLACK);
            wallNode.setFill(Color.BLACK);
            Label lbForWallNode = new Label("Wall Node", wallNode);
            lbForWallNode.setFont(font);


            BorderPane paneW = new BorderPane();
            paneW.setPrefWidth(25);
            paneW.setPrefHeight(25);
            paneW.setStyle("-fx-border-color: black");
            Label lblForWW = new Label("W");
            lblForWW.setFont(fontForInfo);
            paneW.setCenter(lblForWW);
            Label lblForW = new Label(" - key to speed up", paneW);
            lbForTargetNode.setFont(font);
            lblForW.setFont(font);

            BorderPane paneS = new BorderPane();
            paneS.setPrefWidth(25);
            paneS.setPrefHeight(25);
            paneS.setStyle("-fx-border-color: black");
            Label lblForSS = new Label("S");
            lblForSS.setFont(fontForInfo);
            paneS.setCenter(lblForSS);
            Label lblForS = new Label(" - key to speed down", paneS);
            lbForTargetNode.setFont(font);
            lblForS.setFont(font);

            boxForInformation.getChildren()
                    .addAll(lbForStartNode, lbForTargetNode, lbForUnvisitedNode,
                            lbForLastVisitedNode,lbForShortestPathNode, lbForWallNode,
                            lblForW, lblForS);


            lbForInformation.setContentDisplay(ContentDisplay.BOTTOM);
            lbForInformation.setFont(fontForInfo);
            lbForInformation.setStyle("-fx-background-color: azure");
            lbForInformation.setPrefWidth(300);
            lbForInformation.setPrefHeight(270);
            lbForInformation.setPadding(new Insets(3,0,0,0));

            boxForControls.getChildren().add(lbForInformation);
        }

//----------------------------Set up --------------------------------

        pane.setPrefWidth(1200);
        pane.setPrefHeight(750);
        for (int i=0; i<80;i++){
            for (int j=0;j<50;j++){
                board[i][j] = new Cell(i,j);
            }
        }
        start = board[23][25];
        start.setColor(Color.BLUE);
        target = board[57][25];
        target.setColor(Color.MAGENTA);

//---------------------------Controls--------------------------------

        btnForGeneratingMaze.setOnMouseClicked(event ->{
            if(btnBRM.isSelected()){
                lbForMazes.setStyle("-fx-background-color: cadetblue");
                border = new Border(board, start, target);
                border.setUpBorder();
            }
            if(btnForRD.isSelected()){
                lbForMazes.setStyle("-fx-background-color: cadetblue");
                maze = new Maze(board, start, target);
                maze.setUpMaze();
            }
        });

        btnVisualize.setOnMouseClicked(event -> {
            if(!btnForDijkstra.isSelected() && !btnForBFS.isSelected() && !btnForDFS.isSelected() && !btnForAStar.isSelected()){
                Pane warningPane = new Pane();
                warningPane.setPrefHeight(150);
                warningPane.setPrefWidth(440);
                Stage warningStage = new Stage();
                warningStage.setTitle("Warning");
                Text text = new Text(100, 70, "You need to choose an algorithm");
                text.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 17));
                warningPane.getChildren().add(text);
                warningStage.setScene(new Scene(warningPane,440,150));
                warningStage.show();
            }
            else if(btnForDijkstra.isSelected()){
                dijkstra = new Dijkstra(board, start, target);
                dijkstra.animateDijkstra();
            }
            else if(btnForBFS.isSelected()){
                bfs = new BFS(board, start, target);
                bfs.animateBFS();
            }
            else if (btnForDFS.isSelected()){
                dfs = new DFS(board, start, target);
                dfs.animateDFS();
            }
            else if(btnForAStar.isSelected()){
                astar = new AStar(board, start, target);
                astar.animateAStar();
            }
        });

        btnReset.setOnMouseClicked(event -> {
            lbForMazes.setStyle("-fx-background-color: lightgray");
            btnBRM.setSelected(false);
            btnForRD.setSelected(false);
            btnForDijkstra.setSelected(false);
            btnForBFS.setSelected(false);
            btnForDFS.setSelected(false);
            btnForAStar.setSelected(false);
            reset();
        });

        borderPane.setOnKeyPressed(event ->{
            if(btnForDijkstra.isSelected()){
                double rate = dijkstra.getTimelineRate();
                if (event.getCode() == KeyCode.W){
                    dijkstra.setTimelineRate(rate*2);
                }else if(event.getCode() == KeyCode.S){
                    dijkstra.setTimelineRate(rate/2);
                }
            }
            if(btnForBFS.isSelected()){
                double rate = bfs.getTimelineRate();
                if (event.getCode() == KeyCode.W){
                    bfs.setTimelineRate(rate*2);
                }else if(event.getCode() == KeyCode.S){
                    bfs.setTimelineRate(rate/2);
                }
            }
            if (btnForDFS.isSelected()){
                double rate = dfs.getTimelineRate();
                if (event.getCode() == KeyCode.W){
                    dfs.setTimelineRate(rate*2);
                }else if(event.getCode() == KeyCode.S){
                    dfs.setTimelineRate(rate/2);
                }
            }
            if(btnForAStar.isSelected()){
                double rate = astar.getTimelineRate();
                if (event.getCode() == KeyCode.W){
                    astar.setTimelineRate(rate*2);
                }else if(event.getCode() == KeyCode.S){
                    astar.setTimelineRate(rate/2);
                }
            }
        });

        pane.setOnMouseDragged(event ->{
            int col = (int)event.getX()/15;
            int row = (int)event.getY()/15;
            if(col>=0 && col<80 && row>=0 && row<50 && board[col][row] != start && board[col][row] != target){
                board[col][row].setWall(true);
                board[col][row].setColor(Color.BLACK);
            }
        });

        pane.setOnMouseClicked(event ->{
            if(!btnVisualize.isFocused() && event.getClickCount() == 2) {
                if(event.getButton() == MouseButton.PRIMARY){
                    board[start.getColumn()][start.getRow()] = new Cell(start.getColumn(), start.getRow());
                    board[start.getColumn()][start.getRow()].setColor(Color.ALICEBLUE);
                    start = board[(int)(event.getX()/15)][(int)(event.getY()/15)];
                    start.setWall(false);
                    start.setColor(Color.GREEN);
                }else if(event.getButton() == MouseButton.SECONDARY){
                    board[target.getColumn()][target.getRow()] = new Cell(target.getColumn(), target.getRow());
                    board[target.getColumn()][target.getRow()].setColor(Color.ALICEBLUE);
                    target = board[(int)(event.getX()/15)][(int)(event.getY()/15)];
                    target.setWall(false);
                    target.setColor(Color.RED);
                }
            }
        });

        borderPane.setLeft(boxForControls);
        borderPane.setRight(pane);
        return borderPane;
    }

    public void reset(){
        if (border!=null) border.setTimelineBorderStop();
        if (maze!=null) maze.setTimelineMazeStop();
        if (dijkstra!=null) dijkstra.setTimelineDijkstraStop();
        if(bfs!=null) bfs.setTimelineBFSStop();
        if(dfs!=null) dfs.setTimelineDFSStop();
        if(astar!=null) astar.setTimelineAStarStop();

        for (int i=0; i<80;i++){
            for (int j=0;j<50;j++){
                board[i][j] = new Cell(i,j);
            }
        }
        start = board[23][25];
        start.setColor(Color.BLUE);
        target = board[57][25];
        target.setColor(Color.MAGENTA);
    }

    public class Cell{
        private int column;
        private int row;
        private ArrayList<Cell> neighbours;
        private boolean wall;
        private boolean visited;

        private Cell parent;
        private double f, g, hx;
        private double distance;

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        private Rectangle cell;

        Cell(int col, int row){
            this.column = col;
            this.row = row;
            this.wall = false;
            this.visited = false;

            this.f = 0;
            this.g = 0;
            this.hx = 0;
            this.distance = -1;

            cell = new Rectangle(col*15,row*15,15,15);
            cell.setStroke(Color.LIGHTSKYBLUE);
            cell.setFill(Color.ALICEBLUE);
            pane.getChildren().add(cell);
        }

        public int getColumn() {
            return column;
        }

        public int getRow() {
            return row;
        }

        public void addNeighbours(int num){
            this.neighbours = new ArrayList<>();
            if(num==1){
                if (chkN.isSelected() && row>num-1){
                    this.neighbours.add(board[column][row-num]);
                }
                if(chkNE.isSelected() && row>num-1 && column<80-num){
                    this.neighbours.add(board[column+num][row-num]);
                }
                if (chkE.isSelected() && column<80-num){
                    this.neighbours.add(board[column+num][row]);
                }
                if(chkSE.isSelected() && row<50-num && column<80-num){
                    this.neighbours.add(board[column+num][row+num]);
                }
                if (chkS.isSelected() && row<50-num){
                    this.neighbours.add(board[column][row+num]);
                }
                if(chkSW.isSelected() && column>num-1 && row<50-num){
                    this.neighbours.add(board[column-num][row+num]);
                }
                if(chkW.isSelected() && column>num-1){
                    this.neighbours.add(board[column-num][row]);
                }
                if(chkNW.isSelected() && row>num-1 && column>num-1){
                    this.neighbours.add(board[column-num][row-num]);
                }
            }else{
                if (row>num-1){
                    this.neighbours.add(board[column][row-num]);
                }
                if (column<80-num){
                    this.neighbours.add(board[column+num][row]);
                }
                if (row<50-num){
                    this.neighbours.add(board[column][row+num]);
                }
                if(column>num-1){
                    this.neighbours.add(board[column-num][row]);
                }
            }
        }

        public ArrayList<Cell> getNeighbours(int num) {
            this.addNeighbours(num);
            return neighbours;
        }

        public boolean isWall() {
            return wall;
        }

        public void setWall(boolean wall) {
            this.wall = wall;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public void setColor(Color color) {
            if(this == start){
                cell.setFill(Color.BLUE);
            }else if(this == target){
                cell.setFill(Color.MAGENTA);
            }else{
                cell.setFill(color);
            }
        }

        public Cell getParent() {
            return parent;
        }

        public void setParent(Cell parent) {
            this.parent = parent;
        }

        public double getF() {
            return f;
        }

        public void setF(double f) {
            this.f = f;
        }

        public double getG() {
            return g;
        }

        public void setG(double g) {
            this.g = g;
        }

        public double getHx() {
            return hx;
        }

        public void setHx(double hx) {
            this.hx = hx;
        }

        public void removeWalls(Cell next){
            int fromCol = this.getColumn();
            int fromRow = this.getRow();
            int toCol = next.getColumn();
            int toRow = next.getRow();
            board[fromCol][fromRow].setWall(false);
            board[toCol][toRow].setWall(false);

            if(fromCol-toCol==0 && fromRow-toRow==2) {
                board[fromCol][fromRow-1].setWall(false);
            }
            if(fromCol-toCol==-2 && fromRow-toRow==0) {
                board[fromCol+1][fromRow].setWall(false);
            }
            if(fromCol-toCol==0 && fromRow-toRow==-2) {
                board[fromCol][fromRow+1].setWall(false);
            }
            if(fromCol-toCol==2 && fromRow-toRow==0) {
                board[fromCol-1][fromRow].setWall(false);
            }
        }
    }
}

