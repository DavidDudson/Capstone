package nz.ac.massey.cs.ig.games.chinesechecker.bots;

import java.util.Random;

import nz.ac.massey.cs.ig.games.chinesechecker.ChineseCheckerBot;
import nz.ac.massey.cs.ig.games.chinesechecker.ChineseCheckerPosition;
import nz.ac.massey.cs.ig.games.chinesechecker.EasyChineseCheckerBoard;

public class RandomBot extends ChineseCheckerBot {

	public RandomBot(String id) {
		super(id);
	}

	@Override
	public  ChineseCheckerPosition nextMove(EasyChineseCheckerBoard game) {
		Random rand =new Random();
		int x= rand.nextInt(8)+0;
		int y= rand.nextInt(8)+0;
		
		return null;
	}

}
