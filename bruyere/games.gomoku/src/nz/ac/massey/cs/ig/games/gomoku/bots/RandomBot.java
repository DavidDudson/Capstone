package nz.ac.massey.cs.ig.games.gomoku.bots;

import java.util.Random;

import nz.ac.massey.cs.ig.games.gomoku.EasyGomokuBoard;
import nz.ac.massey.cs.ig.games.gomoku.GomokuBot;
import nz.ac.massey.cs.ig.games.gomoku.GomokuPosition;

public class RandomBot extends GomokuBot {

	public RandomBot(String id) {
		super(id);
	}

	@Override
	public GomokuPosition nextMove(EasyGomokuBoard game) {
			
			Random rand =new Random();
			int x= rand.nextInt(8)+0;
			int y= rand.nextInt(8)+0;
			if(game.isFieldFree(x, y)){
				return new GomokuPosition(x,y);
			}else{
				return nextMove(game);
			}
	}

}