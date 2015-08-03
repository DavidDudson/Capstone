package nz.ac.massey.cs.ig.games.othello;

/**
 * 
 * {@link OthelloBoard} which is aware of the current player. This means it is
 * aware of the current player (me) and the enemy.
 * 
 * @author Johannes Tandler
 *
 */
public class ContextAwareOthelloBoard extends OthelloBoard {

	/**
	 * current player
	 */
	protected Object currentPlayer;

	/**
	 * See {@link #OthelloBoard(Object, Object)} for more details. This creates
	 * a board with a specified board size {@link #fieldSize}.
	 * 
	 * Current or starting player is player 1
	 * 
	 * @param player1
	 *            Principal for Player I
	 * @param player2
	 *            Principal for Player II
	 * @param fieldSize
	 *            size of axis
	 */
	public ContextAwareOthelloBoard(Object player1, Object player2,
			int fieldSize) {
		super(player1, player2, fieldSize);
		// normally player 1 starts
		currentPlayer = player1;
	}

	/**
	 * Default Constructor which creates a board for {@link #player1} as player
	 * 1 and {@link #player2} as player 2 and a bord size of 8.
	 * 
	 * Current or starting player is player 1
	 * 
	 * @param player1
	 *            Principal for Player I
	 * @param player2
	 *            Principal for Player II
	 */
	public ContextAwareOthelloBoard(Object player1, Object player2) {
		super(player1, player2);
		currentPlayer = player1;
	}

	/**
	 * Creates a duplicate {@link ContextAwareOthelloBoard} of a given
	 * {@link OthelloBoard}.
	 * 
	 * If sourceBoard is {@link ContextAwareOthelloBoard} too, then current
	 * player is copied as well.
	 * 
	 * @param sourceBoard
	 *            {@link OthelloBoard} which should be duplicated.
	 */
	public ContextAwareOthelloBoard(OthelloBoard sourceBoard) {
		super(sourceBoard);
		currentPlayer = sourceBoard.player1;
		if (sourceBoard instanceof ContextAwareOthelloBoard) {
			currentPlayer = ((ContextAwareOthelloBoard) sourceBoard).currentPlayer;
		}
	}

	/**
	 * Changes current player.
	 */
	public void switchPlayerPerspective() {
		this.currentPlayer = getEnemy();
	}

	/**
	 * Sets the current player.
	 * 
	 * Throws an {@link UnsupportedOperationException} if parameter me is not
	 * player1 or player2
	 * 
	 * @param me
	 *            new current player
	 */
	public void setMe(Object me) {
		if (me != player1 && me != player2) {
			throw new UnsupportedOperationException(
					"Me can be only set one of players");
		}
		this.currentPlayer = me;
	}

	/**
	 * Returns true if the owner of field x y is the current player
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @return true if the owner of field x y is the current player
	 */
	public boolean isFieldMine(int x, int y) {
		validatePosition(x, y);
		return field[y - 1][x - 1] == currentPlayer;
	}

	/**
	 * Returns true if the owner of field x y is not the current player and the
	 * field is not free
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @return true if the owner of field x y is not the current player and the
	 * field is not free
	 */
	public boolean isFieldEnemy(int x, int y) {
		validatePosition(x, y);
		return field[y - 1][x - 1] == getEnemy();
	}

	/**
	 * Returns true if parameter bot is current player
	 * 
	 * @param bot principal of player to compare
	 * @return true if parameter bot is current player
	 */
	public boolean isMe(Object bot) {
		return currentPlayer == bot;
	}

	/**
	 * Makes a move from the perspective of the current player
	 * 
	 * See {@link OthelloBoard#setField(int, int, Object)} for more details.
	 * 
	 * ATTENTION: Does not automatically switch player perspective!!
	 * 
	 * @param x x position
	 * @param y y position
	 */
	public void makeMove(int x, int y) {
		setField(x, y, currentPlayer);
	}

	/**
	 * Returns score of current player
	 * 
	 * @return score of current player
	 */
	public int getScoreOfMe() {
		return countFieldsWithValue(currentPlayer);
	}

	/**
	 * Returns score of opposite of current player
	 * 
	 * @return score of opposite of current player
	 */
	public int getScoreOfEnemy() {
		return countFieldsWithValue(getEnemy());
	}

	/**
	 * returns the enemy
	 * 
	 * @return
	 */
	protected Object getEnemy() {
		return currentPlayer == player1 ? player2 : player1;
	}
}
