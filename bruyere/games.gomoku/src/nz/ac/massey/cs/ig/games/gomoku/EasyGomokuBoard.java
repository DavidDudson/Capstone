package nz.ac.massey.cs.ig.games.gomoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Easy {@link GomokuBoard} implementation.It handles requests for
 * {@link GomokuPosition} and provides currently available moves for both
 * players.
 * 
 * @author Li Sui
 *
 */
public class EasyGomokuBoard extends GomokuBoard {

	public EasyGomokuBoard(Object player1, Object player2, int fieldSize) {
		super(player1, player2, fieldSize);
	}

	public EasyGomokuBoard(Object player1, Object player2) {
		super(player1, player2);
	}

	public EasyGomokuBoard(GomokuBoard board) {
		super(board);
	}

	/**
	 * Makes a move for the current player
	 * 
	 * @param move
	 */
	protected void makeMove(GomokuPosition move) {
		setField(move.getX(), move.getY());
	}

	/**
	 * Returns list of available moves for the current player
	 * 
	 * @return List<GomokuPosition>
	 */
	public List<GomokuPosition> getAvailableMovesForMe() {
		List<GomokuPosition> possibleMoves = new ArrayList<GomokuPosition>();
		for(int y = 0;y<getFieldSize();y++) {
			for(int x=0;x<getFieldSize();x++) {
				if (isFieldFree(x, y)) {
					possibleMoves.add((new GomokuPosition(x, y)));
				}
			}
		}
		return possibleMoves;
	}
	/**
	 * Return list moves that been played
	 * @return List<GomokuPosition>
	 */
	public List<GomokuPosition> getHistoryListMoves(){
		List<GomokuPosition> historyMoves = new ArrayList<GomokuPosition>();
		for(int y = 0;y<getFieldSize();y++) {
			for(int x=0;x<getFieldSize();x++) {
				if (!isFieldFree(x, y)) {
					historyMoves.add((new GomokuPosition(x, y)));
				}
			}
		}
		return historyMoves;
	}
	
	/**
	 * see {@link #getCurrentBoard()} for more information.
	 * @return String[][]
	 */
	public String[][] getCurrentBoard(){
		return super.getCurrentBoard();
	}
	
	@Override
	public EasyGomokuBoard copy() {
		return new EasyGomokuBoard(this);
	}

	/**
	 * see {@link #isFieldFree(int, int)} for more information.
	 * 
	 * @param pos
	 * @return boolean
	 */
	public boolean isFieldFree(GomokuPosition pos) {
		return super.isFieldFree(pos.getX(), pos.getY());
	}
	/**
	 * see {@link #isFieldFree(int, int)} for more information.
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @return boolean
	 */
	public boolean isFieldFree(int x,int y) {
		return super.isFieldFree(x, y);
	}
	
	/**
	 * see {@link #isFieldMine(int, int)} for more information.
	 * 
	 * @param pos
	 * @return boolean
	 */
	public boolean isFieldMine(GomokuPosition pos){
		return super.isFieldMine(pos.getX(), pos.getY());
	}
	
	/**
	 * see {@link #isFieldMine(int, int)} for more information.
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @return boolean
	 */
	public boolean isFieldMine(int x,int y){
		return super.isFieldMine(x, y);
	}
	
	/**
	 * check move is in available moves for current user
	 * 
	 * @param GomokuPosition
	 * @return boolean
	 */
	protected boolean isMoveValid(GomokuPosition move) {
		return getAvailableMovesForMe().contains(move);
	}
	
	

}
