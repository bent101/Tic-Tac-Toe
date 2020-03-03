package com.doogdoog.TicTacToe;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.shape.Line;


public class GraphicalTicTacToe extends Application {
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Group root = new Group();
		Scene scene = new Scene(root, 1300, 800);
		
		Board board = new Board();
		
		final int
			strokeThickness = 30 / (board.getSize() - 1),
			boardWidth = 600,
			boardHeight = 600,
			boardOffsetX = 100,
			boardOffsetY = 100;
		final double
			tileWidth = (double) boardWidth / board.getSize(),
			tileHeight = (double) boardHeight / board.getSize();
		
		Rectangle background = new Rectangle(1300, 800, Color.rgb(0, 20, 40));
		
		Rectangle boardFrame = new Rectangle(50, 50, 700, 700);
		boardFrame.setFill(Color.rgb(30, 60, 90));
		boardFrame.setArcWidth(40);
		boardFrame.setArcHeight(40);
		
		Rectangle infoFrame = new Rectangle(800, 50, 450, 700);
		infoFrame.setFill(Color.rgb(30, 60, 90));
		infoFrame.setArcWidth(40);
		infoFrame.setArcHeight(40);
		
		Group boardNode = new Group();
		boardNode.setLayoutX(boardOffsetX);
		boardNode.setLayoutY(boardOffsetY);
		
		for(double i = tileWidth; i < boardWidth; i += tileWidth) {
			Line hLine = new Line(0, i, boardWidth, i);
			hLine.setStrokeWidth(strokeThickness);
			hLine.setStroke(Color.rgb(200, 230, 250));
			
			Line vLine = new Line(i, 0, i, boardHeight);
			vLine.setStrokeWidth(strokeThickness);
			vLine.setStroke(Color.rgb(200, 230, 250));
			
			boardNode.getChildren().addAll(hLine, vLine);
		}
		
		
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int mouseX = (int) event.getX();
				int mouseY = (int) event.getY();

				int colClicked = (mouseX - boardOffsetX) / (int) tileWidth;
				int rowClicked = (mouseY - boardOffsetY) / (int) tileHeight;
				
				colClicked = Math.min(colClicked, board.getSize()-1);
				rowClicked = Math.min(rowClicked, board.getSize()-1);
				
				System.out.println(rowClicked + " " + colClicked);
			}
		});
		
		
		root.getChildren().addAll(background, boardFrame, infoFrame, boardNode);
		
		// display scene
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}