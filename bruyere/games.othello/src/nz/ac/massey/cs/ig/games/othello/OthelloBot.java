package nz.ac.massey.cs.ig.games.othello;

import nz.ac.massey.cs.ig.core.game.Bot;

/**
 * Basic implementation of an {@link OthelloBot} for {@link OthelloGame}.
 * Just takes the id.
 * @author Johannes Tandlers
 *
 */
public abstract class OthelloBot implements Bot<EasyOthelloBoard, OthelloPosition> {

	private String id;
	
	public OthelloBot(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}
}
