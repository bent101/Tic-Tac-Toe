package com.doogdoog.TicTacToe;

import java.util.Arrays;

public class Board {
	private int size;
	private int numTurnsTaken;
	private char[][] board;
	private char turn;
	
	public Board(int size) {
		this.size = size;
		board = new char[size][size];
		for(char[] row : board) Arrays.fill(row, ' ');
		turn = Game.firstPlayer;
		numTurnsTaken = 0;
	}
	
	public Board(Board b) {
		size = b.size;
		board = new char[size][size];
		for(int r = 0; r < size; r++)
			for(int c = 0; c < size; c++)
				board[r][c] = b.board[r][c];
		turn = b.turn;
		numTurnsTaken = b.numTurnsTaken;
		
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
	
	public void takeTurn(Move move) {
		takeTurn(move.r, move.c);
		numTurnsTaken++;
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
				for(int i = 0; i < size - 1; i++)
					System.out.print("---+");
				System.out.print("---");
			}
			System.out.println();
		}
	}
	
	public char getWinner() {
		int numMovesOnBoard = 0;
		for(char[] row : board) for(char c : row)
			if(c != ' ') numMovesOnBoard++;
		
		// no one can have won if they didn't take enough turns
		if(numMovesOnBoard < 2*size - 1) return ' ';
		
		boolean isWin = true;
		
		// check rows
		for(int r = 0; r < size; r++) {
			isWin = true;
			for(int c = 1; c < size && isWin; c++)
				if(board[r][c] != board[r][0]) isWin = false;
			if(isWin && board[r][0] != ' ') return board[r][0];
		}
		
		// check columns
		for(int c = 0; c < size; c++) {
			isWin = true;
			for(int r = 1; r < size && isWin; r++)
				if(board[r][c] != board[0][c]) isWin = false;
			if(isWin && board[0][c] != ' ') return board[0][c];
		}
		
		// check diagonals
		
		// top-left to bottom-right
		isWin = true;
		for(int i = 1; i < size && isWin; i++)
			if(board[i][i] != board[0][0]) isWin = false;
		if(isWin && board[0][0] != ' ') return board[0][0];
		
		// top-right to bottom-left
		isWin = true;
		for(int i = 1; i < size && isWin; i++)
			if(board[i][size-1-i] != board[0][size-1]) isWin = false;
		if(isWin && board[0][size-1] != ' ') return board[0][size-1];
		
		return ' '; // nobody has won yet
	}
	
	public char getWinner(Move lastMove, int numMovesOnBoard) {
		// no one can have won if they didnt take enough turns
		if(numMovesOnBoard <= 2*size - 2) return ' ';
		
		char lastPlayer = board[lastMove.r][lastMove.c];
		
		// row
		boolean isWin = true;
		for(int c = 0; c < size && isWin; c++)
			if(board[lastMove.r][c] != lastPlayer)
				isWin = false;
		if(isWin) return lastPlayer;
		
		// column
		isWin = true;
		for(int r = 0; r < size && isWin; r++)
			if(board[r][lastMove.c] != lastPlayer)
				isWin = false;
		if(isWin) return lastPlayer;
		
		if(lastMove.r == lastMove.c) {
			// top-left to bottom right
			isWin = true;
			for(int i = 0; i < size && isWin; i++) {
				if(board[i][i] != lastPlayer)
					isWin = false;
			}
			if(isWin) return lastPlayer;
		}
		
		if(lastMove.r == size-1 - lastMove.c) {
			// top-right to bottom-left
			isWin = true;
			for(int i = 0; i < size && isWin; i++) {
				if(board[i][size-1-i] != lastPlayer)
					isWin = false;
			}
			if(isWin) return lastPlayer;
		}
		
		return ' '; // no one won
	}
	
	public boolean isValidMove(int r, int c) {
		// the indexes must be in bounds, and the selected tile must be blank
		return r >= 0 && r < size && c >= 0 && c < size &&
				board[r][c] == ' ';
	}
	
	public boolean isValidMove(Move move) {
		return isValidMove(move.r, move.c);
	}
	
	public boolean isFull() {
		for(char[] row : board) for(char c : row)
			if(c ==  ' ') return false;
		return true;
	}
	
	public double getStaticEval(int depth, Move lastMove) {
		int numMovesOnBoard = numTurnsTaken + Game.getAISearchDepth() - depth;
		
		char winner = getWinner(lastMove, numMovesOnBoard);
		if(winner == 'O') return 1; // O maximizes
		if(winner == 'X') return -1; // X minimizes
		if(isFull() || depth == 0) return 0;
		
		if(Game.isSingleplayer) {
			char aiTurn = Game.aiGoesFirst ^ (Game.firstPlayer == 'X') ? 'X' : 'O';
			if(aiTurn == turn) {
				// System.out.println("ai turn");
				Move optimalMove = getOptimalMove();
				board[optimalMove.r][optimalMove.c] = turn;
				turn = turn == 'X' ? 'O' : 'X';
				double staticEval = getStaticEval(depth-1, optimalMove);
				turn = turn == 'X' ? 'O' : 'X';
				board[optimalMove.r][optimalMove.c] = ' ';
				return staticEval;
			}
		}
		
		// System.out.println("not ai turn");
		
		double sum = 0;
		int count = 0;
		for(int r = 0; r < size; r++) {
			for(int c = 0; c < size; c++) {
				if(board[r][c] == ' ') {
					board[r][c] = turn;
					turn = turn == 'X' ? 'O' : 'X';
					double staticEval = getStaticEval(depth-1, new Move(r, c));
					sum += staticEval;
					count++;
					turn = turn == 'X' ? 'O' : 'X';
					board[r][c] = ' ';
				}
			}
		}
		return count == 0 ? 0 : sum / count;
	}
	
	public double getStaticEval(Move lastMove) {
		return getStaticEval(Game.getAISearchDepth(), lastMove);
	}
	
	public Move getOptimalMove() {
		int bestR = -1, bestC = -1;
		double best = turn == 'X' ? 2 : -2;
		for(int r = 0; r < size; r++) {
			for(int c = 0; c < size; c++) {
				if(board[r][c] != ' ') continue;
				board[r][c] = turn;
				double staticEval = getStaticEval(new Move(r, c));
				// System.out.println(staticEval);
				board[r][c] = ' ';
				boolean isBetter = turn == 'X' ? staticEval < best : staticEval > best;
				if(isBetter) {
					best = staticEval;
					bestR = r;
					bestC = c;
				}
			}
		}
		// System.out.println();
		
		return new Move(bestR, bestC);
	}
	
	public Move[] getWinLineStartEnd() {
		boolean isWin = true;
		
		// check rows
		for(int r = 0; r < size; r++) {
			isWin = true;
			for(int c = 1; c < size && isWin; c++)
				if(board[r][c] != board[r][0]) isWin = false;
			if(isWin && board[r][0] != ' ')
				return new Move[] { new Move(r, 0), new Move(r, size-1) };
		}
		
		// check columns
		for(int c = 0; c < size; c++) {
			isWin = true;
			for(int r = 1; r < size && isWin; r++)
				if(board[r][c] != board[0][c]) isWin = false;
			if(isWin && board[0][c] != ' ')
				return new Move[] { new Move(0, c), new Move(size-1, c) };
		}
		
		// check diagonals
		
		// top-left to bottom-right
		isWin = true;
		for(int i = 1; i < size && isWin; i++)
			if(board[i][i] != board[0][0]) isWin = false;
		if(isWin && board[0][0] != ' ')
			return new Move[] { new Move(0, 0), new Move(size-1, size-1) };
		
		// must be top-right to bottom-left; assume someone has won
		return new Move[] { new Move(0, size-1), new Move(size-1, 0) };	
	}
	
}
