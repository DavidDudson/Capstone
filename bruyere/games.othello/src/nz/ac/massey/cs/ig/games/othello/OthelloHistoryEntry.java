package nz.ac.massey.cs.ig.games.othello;

import com.google.common.base.Preconditions;

/**
 * History entry of a move in othello
 * @author Johannes Tandler
 *
 */
public class OthelloHistoryEntry {
	
	/**
	 * move itself
	 */
	private OthelloPosition position;
	
	/**
	 * player of move
	 */
	private String player;
	
	/**
	 * board after move
	 */
	private EasyOthelloBoard board;
	
	
	public OthelloHistoryEntry(EasyOthelloBoard board, OthelloPosition pos, String player) {
		Preconditions.checkNotNull(pos, "OthelloPosition must not be null");
		this.board = board;
		this.position = pos;
		this.player = player;
	}

	/**
	 * move itself
	 * @return
	 */
	public OthelloPosition getPosition() {
		return position;
	}

	/**
	 * player of move
	 * @return
	 */
	public String getPlayer() {
		return player;
	}

	/**
	 * board after move
	 * @return
	 */
	public EasyOthelloBoard getBoard() {
		return board;
	}

}
