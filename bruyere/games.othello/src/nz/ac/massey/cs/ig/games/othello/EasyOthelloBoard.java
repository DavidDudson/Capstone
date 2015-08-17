package nz.ac.massey.cs.ig.games.othello;

import java.util.List;

/**
 * Easy {@link OthelloBoard} implementation which is context aware (
 * {@link ContextAwareOthelloBoard}). This means it is aware of the current
 * player (me) and the enemy. Additionally handles requests for
 * {@link OthelloPosition} and provides currently available moves for both
 * players.
 * 
 * @author Johannes Tandler
 *
 */
public class EasyOthelloBoard extends ContextAwareOthelloBoard {

	public EasyOthelloBoard(Object player1, Object player2, int fieldSize) {
		super(player1, player2, fieldSize);
	}

	public EasyOthelloBoard(Object player1, Object player2) {
		super(player1, player2);
	}

	public EasyOthelloBoard(OthelloBoard board) {
		super(board);
	}

	/**
	 * Makes a move for the current player
	 * 
	 * @param move
	 */
	public void makeMove(OthelloPosition move) {
		makeMove(move.getX(), move.getY());
	}

	/**
	 * Returns list of available moves for the current player
	 * 
	 * @return
	 */
	public List<OthelloPosition> getAvailableMovesForMe() {
		return new OthelloValidator(this).getAvailableMoves(currentPlayer);
	}

	/**
	 * Returns list of available moves for the opposite of current player
	 * 
	 * @return
	 */
	public List<OthelloPosition> getAvailableMovesForEnemy() {
		return new OthelloValidator(this).getAvailableMoves(getEnemy());
	}

	@Override
	public EasyOthelloBoard copy() {
		return new EasyOthelloBoard(this);
	}

	/**
	 * Checks if field is mine. See {@link #isFieldMine(int, int)} for more
	 * information
	 * 
	 * @param pos
	 * @return
	 */
	public boolean isFieldMine(OthelloPosition pos) {
		return super.isFieldMine(pos.getX(), pos.getY());
	}

	/**
	 * See {@link #isFieldEnemy(int, int)} for more information.
	 * 
	 * @param pos
	 * @return
	 */
	public boolean isFieldEnemy(OthelloPosition pos) {
		return super.isFieldEnemy(pos.getX(), pos.getY());
	}

	/**
	 * see {@link #isFieldFree(int, int)} for more information.
	 * 
	 * @param pos
	 * @return
	 */
	public boolean isFieldFree(OthelloPosition pos) {
		return super.isFieldFree(pos.getX(), pos.getY());
	}

	/**
	 * Returns true if field is on one of the 4 edges.
	 * 
	 * @param pos
	 * @return
	 */
	public boolean isOnEdge(OthelloPosition pos) {
		return (pos.getX() == 1 || pos.getX() == getFieldSize())
				&& (pos.getY() == 1 || pos.getY() == getFieldSize());
	}

	/**
	 * Returns true if value is on board of game board
	 * 
	 * @param val
	 * @return
	 */
	public boolean isOnBorder(int val) {
		return calcDistToBorder(val) == 0;
	}

	/**
	 * Returns distance to closes boarder
	 * 
	 * @param val
	 * @return
	 */
	public int calcDistToBorder(int val) {
		return Math.min(Math.abs(val - fieldSize), Math.abs(val - 1));
	}
}
