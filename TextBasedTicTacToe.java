package com.doogdoog.TicTacToe;

import java.util.Scanner;

public class TextBasedTicTacToe {
	public static void main(String[] args) {
		Board board = new Board(Game.boardSize);
		
		Scanner in = new Scanner(System.in);
		
		char winner = ' ';
		
		System.out.println("======= Welcome to Tic Tac Toe! ========");
		
		if(Game.isSingleplayer && Game.aiGoesFirst) {
			board.takeTurn(board.getOptimalMove());
		}
		
		board.print();
		
		while((winner = board.getWinner()) == ' ' && !board.isFull()) {
			System.out.println("=============== " + board.getTurn() + "'s turn! ==============");
			
			int r = 0, c = 0;
			
			System.out.print("Enter your position (row column): ");
			r = in.nextInt(); c = in.nextInt();
			
			while(!board.isValidMove(r, c)) {
				System.out.print("That is an invalid position!\nPlease enter a valid position: ");
				r = in.nextInt(); c = in.nextInt();
			}
			
			board.takeTurn(r, c);
			
			// AI goes after you if it's singleplayer
			if(Game.isSingleplayer) {
				Move aiMove = board.getOptimalMove();
				if(board.isValidMove(aiMove))
					board.takeTurn(aiMove);
				else
					System.out.println("The AI tried to make an invalid move!");
			}
			
			board.print();
		}
		
		if(winner == ' ') {
			System.out.println("============= It's a tie!!! ============");
		} else {
			System.out.println("=============== " + winner + " wins!!! ==============");
		}
		
		in.close();
	}
	
}
