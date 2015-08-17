package nz.ac.massey.cs.ig.games.gomoku;


public class GomokuHistoryEntry {
	/**
	 * move itself
	 */
	private GomokuPosition position;
	
	/**
	 * player of move
	 */
	private String player;
	
	/**
	 * board after move
	 */
	private GomokuBoard board;
	
	
	public GomokuHistoryEntry(GomokuBoard board, GomokuPosition pos, String player) {
		this.board = board;
		this.position = pos;
		this.player = player;
	}

	/**
	 * move itself
	 * @return
	 */
	public GomokuPosition getPosition() {
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
	public GomokuBoard getBoard() {
		return board;
	}

}
