package test;

import java.util.List;

import nz.ac.massey.cs.ig.games.primegame.PGBot;


public class ExampleBot extends PGBot {
	
    public ExampleBot(String botId) {
        super(botId);
    }

	@Override
	public Integer nextMove(List<Integer> game) {
		Object index = new Integer(game.size() - 1);
		
		return game.get((int)index);
	}


	private class Test {
		public String hallo() {
			return ExampleBot.this.getId();
		}
	}
}
