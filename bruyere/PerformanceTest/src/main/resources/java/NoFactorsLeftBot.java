import java.util.List;
import java.util.List;

import nz.ac.massey.cs.ig.games.primegame.PGBot;

public class NoFactorsLeftBot extends PGBot {

	public NoFactorsLeftBot(String id) {
		super(id);
	}

	@Override
	public Integer nextMove(List<Integer> game) {
		for(int i=game.size()-1;i>=0;i--) {
			int x = game.get(i);
			if(!factorsLeft(x)) {
				return x;
			}
		}
		return game.get(game.size() - 1);
	}
	
	private boolean factorsLeft(int x) {
		for(int i=2;i<x;i++) {
			if(x%i == 0) {
				return true;
			}
		}
		return false;
	}
}
