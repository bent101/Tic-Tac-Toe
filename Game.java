package com.doogdoog.TicTacToe;

public class Game {
	public static enum Difficulty {
		EASY, MEDIUM, HARD, INSANE
	}
	public static int boardSize = 4;
	public static boolean isSingleplayer = true;
	public static boolean aiGoesFirst = false;
	public static char firstPlayer = 'X';
	
	private static final Difficulty aiDifficulty = Difficulty.HARD;
	
	public static int getAISearchDepth() {
		switch(aiDifficulty) {
			case EASY: return 3;
			case MEDIUM: return 4;
			case HARD: return 5;
			case INSANE: return 6;
		}
		return -1;
	}
	
	public static String getDifficultyStr() {
		switch(aiDifficulty) {
			case EASY: return "Easy";
			case MEDIUM: return "Medium";
			case HARD: return "Hard";
			case INSANE: return "Insane";
		}
		return null;
	}
	
	public static String getTitle() {
		return "" + boardSize + " x " + boardSize + " " +
				(isSingleplayer ? "Singleplayer" : "2 - Player") + " Tic Tac Toe";
	}
}
