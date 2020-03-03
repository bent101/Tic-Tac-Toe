package com.doogdoog.TicTacToe;

import java.util.Arrays;

public class Board {
	private int size;
	private char[][] board;
	private char turn;
	
	public Board(int size) {
		this.size = size;
		board = new char[size][size];
		for(char[] row : board) Arrays.fill(row, ' ');
		turn = 'X';
	}
	
	public Board() {
		this(3);
	}
	
	public char getTurn() {
		return turn;
	}
	
	public int getSize() {
		return size;
	}
	
	public char[][] getBoard() {
		return board;
	}
	
	public void takeTurn(int r, int c) {
		board[r][c] = turn;
		turn = turn == 'X' ? 'O' : 'X';
	}
	
	public void print() {
		System.out.println();
		
		// print each row with dividers between squares
		for(int r = 0; r < size; r++) {
			for(int c = 0; c < size-1; c++)
				System.out.print(" " + board[r][c] + " |");
			System.out.println(" " + board[r][size-1]);
			
			// print horizontal line unless its the last row
			if(r < size-1) {
				for(int i = 0; i < 4 * size - 1; i++)
					System.out.print("-");
			}
			System.out.println();
		}
	}
	
	public char getWinner() {
		// check rows
		for(int r = 0; r < size; r++) {
			boolean isWin = true;
			for(int c = 1; c < size && isWin; c++)
				if(board[r][c] != board[r][0]) isWin = false;
			if(isWin) return board[r][0];
		}
		
		// check columns
		for(int c = 0; c < size; c++) {
			boolean isWin = true;
			for(int r = 1; r < size && isWin; r++)
				if(board[r][c] != board[0][c]) isWin = false;
			if(isWin) return board[0][c];
		}
		
		// check diagonals
		
		// top-left to bottom-right
		boolean isWin = true;
		for(int i = 0; i < size && isWin; i++)
			if(board[i][i] != board[0][0]) isWin = false;
		if(isWin) return board[0][0];
		
		// top-right to bottom-left
		isWin = true;
		for(int i = 0; i < size && isWin; i++)
			if(board[i][size-1-i] != board[i][size-1]) isWin = false;
		if(isWin) return board[0][size-1];
		
		return ' '; // nobody has won yet (the method might also return blank as the "winner")
	}
	
	public boolean isValidMove(int r, int c) {
		// the indexes must be in bounds, and the selected tile must be blank
		return r >= 0 && r < size && c >= 0 && c < size &&
				board[r][c] == ' ';
	}
	
	public boolean isFull() {
		for(char[] row : board) for(char c : row)
			if(c ==  ' ') return false;
		return true;
	}
}
