package com.doogdoog.TicTacToe;

public class Game {
	public static enum Difficulty {
		EASY, MEDIUM, HARD
	}
	public static int boardSize = 5;
	public static boolean isSingleplayer = true;
	public static boolean aiGoesFirst = false;
	public static char firstPlayer = 'O';
	
	private static final Difficulty aiDifficulty = Difficulty.HARD;
	
	public static int getAISearchDepth() {
		switch(aiDifficulty) {
			case EASY: return 2;
			case MEDIUM: return 3;
			case HARD: return 4;
		}
		return -1;
	}
	
	public static String getDifficultyStr() {
		switch(aiDifficulty) {
			case EASY: return "Easy";
			case MEDIUM: return "Medium";
			case HARD: return "Hard";
		}
		return null;
	}
}
