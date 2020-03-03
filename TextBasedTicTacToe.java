package com.doogdoog.TicTacToe;

import java.util.Scanner;

public class TextBasedTicTacToe {
	public static void main(String[] args) {
		Board board = new Board();
		
		Scanner in = new Scanner(System.in);
		
		char winner = ' ';
		
		System.out.println("======= Welcome to Tic Tac Toe! ========");
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