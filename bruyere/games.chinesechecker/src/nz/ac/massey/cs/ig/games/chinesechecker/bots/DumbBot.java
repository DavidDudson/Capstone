package nz.ac.massey.cs.ig.games.chinesechecker.bots;

import nz.ac.massey.cs.ig.games.chinesechecker.ChineseCheckerPosition;
import nz.ac.massey.cs.ig.games.chinesechecker.EasyChineseCheckerBoard;
import nz.ac.massey.cs.ig.games.chinesechecker.ChineseCheckerBot;

public class DumbBot extends ChineseCheckerBot{
	public DumbBot(String id) {
		super(id);
	}

	@Override
	public ChineseCheckerPosition nextMove(EasyChineseCheckerBoard game) {
		ChineseCheckerPosition piece =game.getAvailablePiecesForMe().get(0);
		piece.setDestination(4,0);
		return piece;
	}
}