package application;

public class Game {
	private double[] game;
	
	public Game() {
		game = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
	}
	
	
	public String toString() {
		String output = "";
		System.out.print("game = [");
		for(int i = 0; i < 9; i++) {
			System.out.print(game[i] + ", ");
		}
		System.out.print("]\n");
		for(int i = 0; i < 3; i++) {
			output += game[i*3] + " | " + game[i*3+1] +" | " + game[i*3+2] + "\n---------\n";
		}
		return output;
	}
	
	public double getSign(int pos) {
		return game[pos];
	}
	
	
	public double[] getGame() {
		return game;
	}
	
	public boolean isPlayable(int pos) {
		if(game[pos] == 0.0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if someone win the game
	 * @return No winner => 0 | Winner sign
	 */
	public double checkIfWin() {
		for(int i = 0; i < 3; i++)
		{
			// Check line
			System.out.println("Checking line : " + i*3 + " | " + (i*3+1) + " | " + (i*3+2) + " => " + game[i*3] + " | " + game[(i*3+1)] + " | " + game[(i*3+2)]);
			if(game[i*3] != 0.0 && game[i*3] == game[i*3+1] && game[i*3+2] == game[i*3]) {
				return getSign(i*3);
			}
			// Check Column
			if(game[i] != 0.0 && game[i] == game[i+3] && game[i+6] == game[i+3]) {
				return getSign(i);
			}
		}
		// First diag
		if(game[0] != 0.0 && game[0] == game[4] && game[4] == game[8]) {
			return getSign(0);
		}
		// Second diag
		if(game[2] != 0.0 && game[2] == game[4] && game[4] == game[6]) {
			return getSign(2);
		}
		return 0;
	}
	
	public int[] getWinPos() {
		for(int i = 0; i < 3; i++)
		{
			// Check line
			System.out.println("Checking line : " + i*3 + " | " + (i*3+1) + " | " + (i*3+2) + " => " + game[i*3] + " | " + game[(i*3+1)] + " | " + game[(i*3+2)]);
			if(game[i*3] != 0.0 && game[i*3] == game[i*3+1] && game[i*3+2] == game[i*3]) {
				int[] output = new int[]{i*3, i*3+1, i*3+2};
				return output;
			}
			// Check Column
			if(game[i] != 0.0 && game[i] == game[i+3] && game[i+6] == game[i+3]) {
				int[] output = new int[]{i, i+3, i+6};
				return output;
			}
		}
		// First diag
		if(game[0] != 0.0 && game[0] == game[4] && game[4] == game[8]) {
			int[] output = new int[]{0, 4, 8};
			return output;
		}
		// Second diag
		if(game[2] != 0.0 && game[2] == game[4] && game[4] == game[6]) {
			int[] output = new int[]{2, 4, 6};
			return output;
		}
		return null;
	}
	
	/**
	 * Play on the grid
	 * 
	 * @param pos position to play on the grid
	 * @param sign sign to play
	 * @return true if is played
	 */
	public boolean play(int pos, int sign) {
		if(game[pos] != 0) {
			return false;
		}
		game[pos] = sign;
		return true;
	}
}
