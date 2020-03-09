package com.doogdoog.TicTacToe;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

public class GraphicalTicTacToe extends Application {
	
	private int
		windowWidth = 1300,
		windowHeight = 800,
		margin = 50,
		boardSize;
	private double 
		tileSize,
		tileInsetFactor,
		strokeThickness;
	
	private boolean gameIsOver = false;
	
	private final Color
		xColor = Color.rgb(255, 130, 50),
		oColor = Color.rgb(50, 130, 255),
		xLightColor = Color.rgb(255, 200, 150),
		oLightColor = Color.rgb(150, 200, 255);
	
	private Board board;
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Group root = new Group();
		root.setLayoutX(margin);
		root.setLayoutY(margin);
		Scene scene = new Scene(root, windowWidth, windowHeight);
		
		board = new Board(Game.boardSize);
		
		// constants
		strokeThickness = 30.0 / (board.getSize() - 1);
		boardSize = 600;
		tileSize = (double) boardSize / board.getSize();
		tileInsetFactor = 0.7; // the X's and O's don't take up the entire square
		
		Rectangle background = new Rectangle(1300, 800, Color.rgb(0, 20, 40));
		background.setTranslateX(-50);
		background.setTranslateY(-50);
		
		Rectangle boardFrame = new Rectangle(
				0, 0, boardSize + 2 * margin, boardSize + 2 * margin);
		boardFrame.setFill(Color.rgb(30, 60, 90));
		boardFrame.setArcWidth(40);
		boardFrame.setArcHeight(40);
		
		Rectangle infoFrame = new Rectangle(
				boardSize + 3 * margin,
				0,
				windowWidth - boardSize - 5 * margin,
				windowHeight - 2 * margin);
		infoFrame.setFill(Color.rgb(30, 60, 90));
		infoFrame.setArcWidth(40);
		infoFrame.setArcHeight(40);
		
		Group boardNode = new Group();
		boardNode.setLayoutX(margin);
		boardNode.setLayoutY(margin);
		
		Group infoNode = new Group();
		infoNode.setLayoutX(infoFrame.getX() + margin);
		infoNode.setLayoutY(infoFrame.getY() + margin);
		
		// draws grid
		for(double i = tileSize; i < boardSize; i += tileSize) {
			Line vLine = new Line(i, 0, i, boardSize);
			vLine.setStrokeWidth(strokeThickness);
			vLine.setStroke(Color.rgb(200, 230, 250));
			vLine.setStrokeLineCap(StrokeLineCap.ROUND);
			
			boardNode.getChildren().add(vLine);
		}
		for(double i = tileSize; i < boardSize; i += tileSize) {
			Line hLine = new Line(0, i, boardSize, i);
			hLine.setStrokeWidth(strokeThickness);
			hLine.setStroke(Color.rgb(200, 230, 250));
			hLine.setStrokeLineCap(StrokeLineCap.ROUND);
			
			boardNode.getChildren().add(hLine);
		}
		
		root.getChildren().addAll(background, boardFrame, infoFrame, boardNode);
		
		// AI goes if it's singleplayer and AI goes first
		if(Game.isSingleplayer && Game.aiGoesFirst) {
			Move aiMove = board.getOptimalMove();
			
			Group symbol = board.getTurn() == 'X' ? getXNode() : getONode();
			symbol.setLayoutX(tileSize * aiMove.c + tileSize / 2);
			symbol.setLayoutY(tileSize * aiMove.r + tileSize / 2);
			boardNode.getChildren().add(symbol);
			
			board.takeTurn(aiMove);
		}
		
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(gameIsOver) return;
				
				int mouseX = (int) event.getX();
				int mouseY = (int) event.getY();

				int colClicked = (mouseX - 2 * margin) / (int) tileSize;
				int rowClicked = (mouseY - 2 * margin) / (int) tileSize;
				
				colClicked = Math.min(colClicked, board.getSize()-1);
				rowClicked = Math.min(rowClicked, board.getSize()-1);
				
				if(!board.isValidMove(rowClicked, colClicked))
					return;
				
				Group symbol = board.getTurn() == 'X' ? getXNode() : getONode();
				symbol.setLayoutX(tileSize * colClicked + tileSize / 2);
				symbol.setLayoutY(tileSize * rowClicked + tileSize / 2);
				boardNode.getChildren().add(symbol);
				
				board.takeTurn(rowClicked, colClicked);
				
				if(board.getWinner() != ' ') {
					boardNode.getChildren().add(getWinLine());
					gameIsOver = true;
					return;
				}
				
				// AI goes after you go if it's singleplayer
				if(Game.isSingleplayer) {
					Move aiMove = board.getOptimalMove();
					
					if(!board.isValidMove(aiMove))
						return;
					
					Group symbol2 = board.getTurn() == 'X' ? getXNode() : getONode();
					symbol2.setLayoutX(tileSize * aiMove.c + tileSize / 2);
					symbol2.setLayoutY(tileSize * aiMove.r + tileSize / 2);
					boardNode.getChildren().add(symbol2);
					
					board.takeTurn(aiMove);
					
					if(board.getWinner() != ' ') {
						boardNode.getChildren().add(getWinLine());
						gameIsOver = true;
						return;
					}
				}
			}
		});
		
		// display scene
		primaryStage.setScene(scene);
		primaryStage.setTitle(Game.getTitle());
		primaryStage.show();
	}
	
	private Group getXNode() {
		Line line1 = new Line(
				tileInsetFactor * tileSize / -2, tileInsetFactor * tileSize / -2,
				tileInsetFactor * tileSize / 2, tileInsetFactor * tileSize / 2);
		line1.setStrokeWidth(strokeThickness);
		line1.setStroke(xColor);
		line1.setStrokeLineCap(StrokeLineCap.ROUND);
		
		Line line2 = new Line(
				tileInsetFactor * tileSize / 2, tileInsetFactor * tileSize / -2,
				tileInsetFactor * tileSize / -2, tileInsetFactor * tileSize / 2);
		line2.setStrokeWidth(strokeThickness);
		line2.setStroke(xColor);
		line2.setStrokeLineCap(StrokeLineCap.ROUND);
		
		return new Group(line1, line2);
	}
	
	private Group getONode() {
		Ellipse ellipse = new Ellipse(
				tileInsetFactor * tileSize / 2,
				tileInsetFactor * tileSize / 2);
		ellipse.setStrokeWidth(strokeThickness);
		ellipse.setStroke(oColor);
		ellipse.setFill(Color.TRANSPARENT);
		
		return new Group(ellipse);
	}
	
	private Line getWinLine() {
		Move[] lineStartEnd = board.getWinLineStartEnd();
		Coordinate
			start = getTileCenter(lineStartEnd[0]),
			end = getTileCenter(lineStartEnd[1]);
		
		Line winLine = new Line(start.x, start.y, end.x, end.y);
		winLine.setStrokeWidth(strokeThickness);
		winLine.setStroke(board.getWinner() == 'X' ? xLightColor : oLightColor);
		winLine.setStrokeLineCap(StrokeLineCap.ROUND);
		return winLine;
		
	}
	
	private Coordinate getTileCenter(Move move) {
		return new Coordinate(
				tileSize * move.c + tileSize / 2,
				tileSize * move.r + tileSize / 2);
	}
	
	class Coordinate {
		public double x, y;
		public Coordinate(double a, double b) {
			x = a; y = b;
		}
	}
	
}
