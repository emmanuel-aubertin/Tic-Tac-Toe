package application;

public class Game {
	private int[] game;
	
	public Game() {
		game = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
	}
	
	
	public int getSign(int pos) {
		return game[pos];
	}
	
	public int checkIfWin() {
		for(int i = 0; i < 3; i++)
		{
			// Check line
			if(game[i] == game[i+1] && game[i+2] == game[i+1]) {
				return getSign(i);
			}
			// Check Column
			if(game[i] == game[i+3] && game[i+6] == game[i+3]) {
				return getSign(i);
			}
		}
		// First diag
		if(game[0] == game[4] && game[4] == game[8]) {
			return getSign(0);
		}
		// Second diag
		if(game[2] == game[4] && game[4] == game[6]) {
			return getSign(2);
		}
		return 0;
	}
	
	
	public boolean play(int pos, int sign) {
		if(game[pos] != 0) {
			return false;
		}
		game[pos] = sign;
		return true;
	}
}
