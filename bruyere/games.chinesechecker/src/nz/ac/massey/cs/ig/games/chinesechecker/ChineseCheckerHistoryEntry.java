package nz.ac.massey.cs.ig.games.chinesechecker;


public class ChineseCheckerHistoryEntry {
	/**
	 * move itself
	 */
	private ChineseCheckerPosition position;
	
	/**
	 * player of move
	 */
	private String player;
	
	/**
	 * board after move
	 */
	private ChineseCheckerBoard board;
	
	
	public ChineseCheckerHistoryEntry(ChineseCheckerBoard board, ChineseCheckerPosition pos, String player) {
		this.board = board;
		this.position =null;
		this.player = player;
	}

	/**
	 * move itself
	 * @return
	 */
	public ChineseCheckerPosition getPosition() {
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
	public ChineseCheckerBoard getBoard() {
		return board;
	}

}
