import java.util.List;
import java.util.List;

import nz.ac.massey.cs.ig.games.primegame.PGBot;

public class PrimeNumberBot extends PGBot {

	public PrimeNumberBot(String id) {
		super(id);
	}

	@Override
	public Integer nextMove(List<Integer> game) {
		for(int i=game.size()-1;i>=0;i--) {
			int x = game.get(i);
			if(isPrimeNumber(x)) {
				return x;
			}
		}
		return game.get(game.size() - 1);
	}
	
	private boolean isPrimeNumber(int x) {
		switch (x) {
		case 2:
		case 3:
		case 5:
		case 7:
		case 11:
		case 13:
		case 17:
		case 19:
		case 23:
		case 29:
		case 31:
		case 37:
		case 41:
		case 43:
		case 47:
		case 53:
		case 59:
		case 61:
		case 67:
		case 71:
		case 73:
		case 79:
		case 83:
		case 89:
		case 97:
			return true;
		default:
			return false;
		}
	}

}
