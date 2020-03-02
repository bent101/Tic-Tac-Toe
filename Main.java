package com.doogdoog.TicTacToe;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static final int boardSize = 3;
	
	private static char[][] board = new char[boardSize][boardSize];
	private static char turn = 'X';
	
	public static void main(String[] args) {
		for(char[] row : board) Arrays.fill(row, ' ');
		
		Scanner in = new Scanner(System.in);
		
		char winner = ' ';
		
		System.out.println("Welcome to Tic Tac Toe! ================");
		printBoard();
		
		while((winner = getWinner()) == ' ') {
			System.out.println(turn + "'s turn!  =============================");
			
			int r = 0, c = 0;
			
			System.out.print("Enter your position (row column): ");
			r = in.nextInt(); c = in.nextInt();
			
			while(!isValidMove(r, c)) {
				System.out.println("That is an invalid position!\nPlease enter a valid position: ");
				r = in.nextInt(); c = in.nextInt();
			}
			
			board[r][c] = turn;
			turn = turn == 'X' ? 'O' : 'X';
			
			printBoard();
		}
		
		System.out.println(winner + " wins!!!");
	}	
	
	private static void printBoard() {
		System.out.println();
		
		// print each row with dividers between squares
		for(int r = 0; r < boardSize; r++) {
			for(int c = 0; c < boardSize-1; c++)
				System.out.print(" " + board[r][c] + " |");
			System.out.println(" " + board[r][boardSize-1]);
			
			// print horizontal line unless its the last row
			if(r < boardSize-1) {
				for(int i = 0; i < 4 * boardSize - 1; i++)
					System.out.print("-");
			}
			System.out.println();
		}
		
	}
	
	private static char getWinner() {
		// check rows
		for(int r = 0; r < boardSize; r++) {
			boolean isWin = true;
			for(int c = 1; c < boardSize && isWin; c++)
				if(board[r][c] != board[r][0]) isWin = false;
			if(isWin) return board[r][0];
		}
		
		// check columns
		for(int c = 0; c < boardSize; c++) {
			boolean isWin = true;
			for(int r = 1; r < boardSize && isWin; r++)
				if(board[r][c] != board[0][c]) isWin = false;
			if(isWin) return board[0][c];
		}
		
		// check diagonals
		
		// top-left to bottom-right
		boolean isWin = true;
		for(int i = 0; i < boardSize && isWin; i++)
			if(board[i][i] != board[0][0]) isWin = false;
		if(isWin) return board[0][0];
		
		// top-right to bottom-left
		isWin = true;
		for(int i = 0; i < boardSize && isWin; i++)
			if(board[i][boardSize-1-i] != board[i][boardSize-1]) isWin = false;
		if(isWin) return board[0][boardSize-1];
		
		return ' '; // nobody has won yet (the method might also return blank as the "winner")
	}
	
	private static boolean isValidMove(int r, int c) {
		// the indexes must be in bounds, and the selected tile must be blank
		return r >= 0 && r < boardSize && c >= 0 && c < boardSize &&
				board[r][c] == ' ';
	}
	
}
