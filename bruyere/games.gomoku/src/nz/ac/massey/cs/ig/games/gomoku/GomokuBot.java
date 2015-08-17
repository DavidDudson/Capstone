package nz.ac.massey.cs.ig.games.gomoku;

import nz.ac.massey.cs.ig.core.game.Bot;

public abstract class GomokuBot implements Bot<EasyGomokuBoard,GomokuPosition> {
	
	private String id;
	
	public GomokuBot(String id) {
		super();
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}

}
