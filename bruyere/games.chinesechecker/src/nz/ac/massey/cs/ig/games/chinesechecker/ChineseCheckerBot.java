package nz.ac.massey.cs.ig.games.chinesechecker;

import nz.ac.massey.cs.ig.core.game.Bot;

public abstract class ChineseCheckerBot implements Bot<EasyChineseCheckerBoard,ChineseCheckerPosition> {
	
	private String id;
	
	public ChineseCheckerBot(String id) {
		super();
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}

}
