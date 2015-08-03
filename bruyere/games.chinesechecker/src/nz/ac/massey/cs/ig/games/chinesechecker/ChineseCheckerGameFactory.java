package nz.ac.massey.cs.ig.games.chinesechecker;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.GameFactory;


public class ChineseCheckerGameFactory implements GameFactory{

	@SuppressWarnings("unchecked")
	@Override
	public ChineseCheckerGame createGame(String id, Bot<?, ?> player1, Bot<?, ?> player2) {
		return new ChineseCheckerGame(id,(Bot<ChineseCheckerBoard,ChineseCheckerPosition>)player1,(Bot<ChineseCheckerBoard,ChineseCheckerPosition>)player2);
	}

}
