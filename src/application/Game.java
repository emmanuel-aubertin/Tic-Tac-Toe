package application;

public class Game {
	private int[] game;
	
	public Game() {
		game = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
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
	
	public int getSign(int pos) {
		return game[pos];
	}
	
	
	public int[] getGame() {
		return game;
	}
	/**
	 * Check if someone win the game
	 * @return No winner => 0 | Winner sign
	 */
	public int checkIfWin() {
		for(int i = 0; i < 3; i++)
		{
			// Check line
			System.out.println("Checking line : " + i*3 + " | " + (i*3+1) + " | " + (i*3+2) + " => " + game[i*3] + " | " + game[(i*3+1)] + " | " + game[(i*3+2)]);
			if(game[i*3] != 0 && game[i*3] == game[i*3+1] && game[i*3+2] == game[i*3]) {
				return getSign(i*3);
			}
			// Check Column
			if(game[i] != 0 && game[i] == game[i+3] && game[i+6] == game[i+3]) {
				return getSign(i);
			}
		}
		// First diag
		if(game[0] != 0 && game[0] == game[4] && game[4] == game[8]) {
			return getSign(0);
		}
		// Second diag
		if(game[2] != 0 && game[2] == game[4] && game[4] == game[6]) {
			return getSign(2);
		}
		return 0;
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
