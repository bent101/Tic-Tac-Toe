package com.doogdoog.TicTacToe;

public class Game {
	public static enum Difficulty {
		EASY, MEDIUM, HARD, INSANE
	}
	public static int boardSize = 3;
	public static boolean isSingleplayer = false;
	public static boolean aiGoesFirst = false;
	public static char firstPlayer = 'X';
	
	private static final Difficulty aiDifficulty = Difficulty.INSANE;
	
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
		return "" + boardSize + "x" + boardSize + " " +
				(isSingleplayer ? "Singleplayer" : "Two Player") + " Tic Tac Toe";
	}
}
