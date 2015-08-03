package nz.ac.massey.cs.ig.games.othello;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic implementation of an Othello board.
 * 
 * <h1>Board structure</h1> This is a board with X*X fields, where X is 8 if not
 * defined somehow else.
 * 
 * Per default the board looks therefore like :<br/>
 * 
 * <pre>
 * -12345678
 * 1--------
 * 2--------
 * 3--------
 * 4---+o---
 * 5---o+---
 * 6--------
 * 7--------
 * 8--------
 * </pre>
 * 
 * <h2>Field-Values</h2>
 * <ul>
 * <li>+ stands for fields of Player 1</li>
 * <li>o stands for fields of Player 2</li>
 * </ul>
 * 
 * <h2>Orientation</h2>
 * <ul>
 * <li><b>Y-Axis :</b> vertical axis
 * <li><b>X-Axis :</b> horizontal axis
 * </ul>
 * Example : Field A (Y=2;X=5) would be:
 * 
 * <pre>
 * -12345678
 * 1--------
 * 2----A---
 * 3--------
 * 4---+o---
 * 5---o+---
 * 6--------
 * 7--------
 * 8--------
 * </pre>
 * 
 * @author Johannes Tandler
 *
 */
public class OthelloBoard {

	/**
	 * Real fields
	 */
	protected Object[][] field;

	/**
	 * player1
	 */
	protected Object player1;

	/**
	 * player2
	 */
	protected Object player2;

	/**
	 * size of axis
	 */
	protected int fieldSize;

	/**
	 * Default Constructor which creates a board for {@link #player1} as player
	 * 1 and {@link #player2} as player 2 and a bord size of 8.
	 * 
	 * @param player1
	 *            Principal for Player I
	 * @param player2
	 *            Principal for Player II
	 */
	public OthelloBoard(Object player1, Object player2) {
		this(player1, player2, 8);
	}

	/**
	 * See {@link #OthelloBoard(Object, Object)} for more details. This creates
	 * a board with a specified board size {@link #fieldSize}.
	 * 
	 * @param player1
	 *            Principal for Player I
	 * @param player2
	 *            Principal for Player II
	 * @param fieldSize
	 *            size of axis
	 */
	public OthelloBoard(Object player1, Object player2, int fieldSize) {
		if (fieldSize % 2 != 0) {
			throw new UnsupportedOperationException(
					"Only field sizes with %2 == 0 and < 10 allowed.");
		}
		this.player1 = player1;
		this.player2 = player2;

		this.fieldSize = fieldSize;
		field = new Object[fieldSize][fieldSize];

		initField();
	}

	/**
	 * Creates a duplicate {@link OthelloBoard} of a given {@link OthelloBoard}
	 * 
	 * @param board
	 *            {@link OthelloBoard} which should be duplicated.
	 */
	public OthelloBoard(OthelloBoard board) {
		this.player1 = board.player1;
		this.player2 = board.player2;
		this.fieldSize = board.fieldSize;
		this.field = new Object[fieldSize][fieldSize];

		for (int y = 0; y < fieldSize; y++) {
			for (int x = 0; x < board.field[y].length; x++) {
				this.field[y][x] = board.field[y][x];
			}
		}
	}

	/**
	 * initialize field with default values. This includes default coins in the
	 * middle of the field.
	 */
	private void initField() {
		for (int i = 0; i < fieldSize; i++) {
			for (int b = 0; b < fieldSize; b++) {
				field[i][b] = null;
			}
		}

		int middle = fieldSize / 2;
		field[middle - 1][middle - 1] = player1;
		field[middle + 0][middle - 1] = player2;
		field[middle - 1][middle + 0] = player2;
		field[middle + 0][middle + 0] = player1;
	}

	/**
	 * Returns score of player 1
	 * 
	 * @return score of player 1
	 */
	public int getScoreOfBlack() {
		int score = countFieldsWithValue(player1);
		return score;
	}

	/**
	 * Counts fields with a specific value (Principal of player or null for free
	 * fields).
	 * 
	 * @param value
	 *            value of the field to be count
	 * @return Number of fields with this value
	 */
	protected int countFieldsWithValue(Object value) {
		int score = 0;
		for (int i = 0; i < fieldSize; i++) {
			for (int b = 0; b < fieldSize; b++) {
				if (field[i][b] == value) {
					score++;
				}
			}
		}
		return score;
	}

	/**
	 * Returns number of fields which are not owned by any player
	 * 
	 * @return number of fields which are not owned by any player
	 */
	public int getBlankFields() {
		return countFieldsWithValue(null);
	}

	/**
	 * Returns score of player 2
	 * 
	 * @return score of player 1
	 */
	public int getScoreOfWhite() {
		return countFieldsWithValue(player2);
	}

	/**
	 * Sets the field to a specific value and changes other coins to keep the
	 * board in a valid state.
	 * 
	 * @param x
	 *            x position starting by 1
	 * @param y
	 *            y position starting by 1
	 * @param val
	 *            Principal of field owner
	 */
	public void setField(int x, int y, Object val) {
		if (val != null && val != player1 && val != player2) {
			throw new UnsupportedOperationException(
					"Fields can be only set to null or one of players");
		}
		validatePosition(x, y);
		y = y - 1;
		x = x - 1;

		field[y][x] = val;

		spreadValue(x, y, 1, 0, val); // right
		spreadValue(x, y, -1, 0, val); // left
		spreadValue(x, y, 0, -1, val); // top
		spreadValue(x, y, 0, 1, val); // bottom
		spreadValue(x, y, 1, -1, val); // topRight
		spreadValue(x, y, 1, 1, val); // bottomRight
		spreadValue(x, y, -1, -1, val); // bottomLeft
		spreadValue(x, y, -1, 1, val); // topLeft
	}

	/**
	 * Checks if the position is valid. Otherwise an
	 * {@link UnsupportedOperationException} is thrown.
	 * 
	 * @param x
	 *            x coordinate of position
	 * @param y
	 *            y coordinate of position
	 */
	protected void validatePosition(int x, int y) {
		if (isOutside(x, y)) {
			throw new UnsupportedOperationException(
					"field position must be in range of [1,1] and ["
							+ fieldSize + "," + fieldSize + "]");
		}
	}

	/**
	 * changes coins in a specific direction if necessary
	 * 
	 * @param startX
	 * @param startY
	 * @param vX
	 * @param vY
	 * @param value
	 */
	private void spreadValue(int startX, int startY, int vX, int vY,
			Object value) {
		int x = startX;
		int y = startY;

		int boarderX = vX > 0 ? fieldSize : -1;
		int boarderY = vY > 0 ? fieldSize : -1;

		x += vX;
		y += vY;

		List<int[]> fields = new ArrayList<int[]>();

		while (x != boarderX && y != boarderY) {
			if (field[y][x] == value) {
				for (int[] f : fields) {
					field[f[0]][f[1]] = value;
				}
				return;
			} else if (field[y][x] == null) {
				return;
			} else {
				fields.add(new int[] { y, x });
			}

			x += vX;
			y += vY;
		}
	}

	/**
	 * Returns length of game board axis
	 * 
	 * @return length of game board axis
	 */
	public int getFieldSize() {
		return fieldSize;
	}

	/**
	 * compares a specific field to a value and return 1 if equals, 0 if field
	 * not set and otherwise -1
	 * 
	 * @param x
	 *            x position starting by 1
	 * @param y
	 *            y position starting by 1
	 * @param player
	 *            FieldValue for compare
	 * @return1 if equals, 0 if field not set and otherwise -1
	 */
	public int compareFieldTo(int x, int y, Object player) {
		validatePosition(x, y);
		Object f = field[y - 1][x - 1];
		if (f == player) {
			return 1;
		} else if (f == null) {
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * Returns true if field is free
	 * 
	 * @param x
	 *            x position starting by 1
	 * @param y
	 *            y position starting by 1
	 * @return true if field is free, otherwise false.
	 */
	public boolean isFieldFree(int x, int y) {
		validatePosition(x, y);
		return field[y - 1][x - 1] == null;
	}

	/**
	 * Returns true if field is owned by player1
	 * 
	 * @param x
	 *            x position starting by 1
	 * @param y
	 *            y position starting by 1
	 * @return true if field is owned by player1
	 */
	public boolean isFieldBlack(int x, int y) {
		validatePosition(x, y);
		return field[y - 1][x - 1] == player1;
	}

	/**
	 * Returns true if field is owned by player2
	 * 
	 * @param x
	 *            x position starting by 1
	 * @param y
	 *            y position starting by 1
	 * @return true if field is owned by player2
	 */
	public boolean isFieldWhite(int x, int y) {
		validatePosition(x, y);
		return field[y - 1][x - 1] == player2;
	}

	/**
	 * Returns true if field is outside of board
	 * 
	 * @param x
	 *            x position starting by 1
	 * @param y
	 *            y position starting by 1
	 * @return true if field is outside of board
	 */
	public boolean isOutside(int x, int y) {
		return x == 0 || y == 0 || x > fieldSize || y > fieldSize;
	}

	/**
	 * Returns a copy of the current board.
	 * 
	 * @return a copy of the current board.
	 */
	public OthelloBoard copy() {
		return new OthelloBoard(this);
	}

	/**
	 * Returns easy string representation of an Othello board.
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("-");

		for (int i = 0; i < fieldSize; i++) {
			buffer.append((i + 1));
		}
		buffer.append("\n");

		for (int y = 0; y < fieldSize; y++) {
			buffer.append((y + 1));

			for (int x = 0; x < fieldSize; x++) {

				Object val = field[y][x];

				if (val == null) {
					buffer.append("-");
				} else if (val == player1) {
					buffer.append("+");
				} else {
					buffer.append("o");
				}
			}

			buffer.append("\n");
		}

		return buffer.toString();
	}
}
