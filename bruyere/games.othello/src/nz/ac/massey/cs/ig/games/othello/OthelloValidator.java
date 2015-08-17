package nz.ac.massey.cs.ig.games.othello;

import java.util.ArrayList;
import java.util.List;

import nz.ac.massey.cs.ig.core.game.Bot;

/**
 * 
 * Validator of moves in othello
 * 
 * @author Johannes Tandler
 *
 */
public class OthelloValidator {

	/**
	 * current othello board
	 */
	private OthelloBoard board;

	public OthelloValidator(OthelloBoard currentBoard) {
		this.board = currentBoard;
	}

	/**
	 * Returns all possible moves for a given player
	 * 
	 * @param player
	 * @return
	 */
	public List<OthelloPosition> getAvailableMoves(Object player) {
		List<OthelloPosition> possibleMoves = new ArrayList<OthelloPosition>();

		for (int y = 0; y < board.getFieldSize(); y++) {
			for (int x = 0; x < board.getFieldSize(); x++) {
				if (board.compareFieldTo(x + 1, y + 1, player) == 1) {
					addAvailableMovesFrom(x + 1, y + 1, player, possibleMoves);
				}
			}
		}
		return possibleMoves;
	}

	/**
	 * checks if a move from a given start point into a given direction is
	 * possible.
	 * 
	 * @param startX
	 * @param startY
	 * @param vX
	 * @param vY
	 * @param player
	 * @return Returns a {@link OthelloPosition} if move is possible, otherwise
	 *         null.
	 */
	private OthelloPosition checkDirection(int startX, int startY, int vX,
			int vY, Object player) {
		FieldEvaluation currentEvaluation = FieldEvaluation.UNKNOWN;
		int x = startX;
		int y = startY;

		int boarderX = vX > 0 ? board.getFieldSize() + 1 : 0;
		int boarderY = vY > 0 ? board.getFieldSize() + 1 : 0;

		while (x != boarderX && y != boarderY) {
			currentEvaluation = getFieldEvaluation(x, y, player,
					currentEvaluation);
			if (currentEvaluation == FieldEvaluation.NOT_POSSIBLE) {
				return null;
			} else if (currentEvaluation == FieldEvaluation.VALID_MOVE) {
				return new OthelloPosition(x, y);
			}

			x += vX;
			y += vY;
		}
		return null;
	}

	/**
	 * adds all available valid moves from a given start point into any
	 * direction to the given list
	 * 
	 * @param startX
	 * @param startY
	 * @param player
	 * @param possibleMoves
	 */
	private void addAvailableMovesFrom(int startX, int startY, Object player,
			List<OthelloPosition> possibleMoves) {
		int vX, vY;
		OthelloPosition move;

		// right
		vX = 1;
		vY = 0;
		move = checkDirection(startX + vX, startY + vY, vX, vY, player);
		if (move != null) {
			addMoveIfNecessary(possibleMoves, move);
		}
		move = null;

		// left
		vX = -1;
		vY = 0;
		move = checkDirection(startX + vX, startY + vY, vX, vY, player);
		if (move != null) {
			addMoveIfNecessary(possibleMoves, move);
		}
		move = null;

		// top
		vX = 0;
		vY = -1;
		move = checkDirection(startX + vX, startY + vY, vX, vY, player);
		if (move != null) {
			addMoveIfNecessary(possibleMoves, move);
		}
		move = null;

		// bottom
		vX = 0;
		vY = 1;
		move = checkDirection(startX + vX, startY + vY, vX, vY, player);
		if (move != null) {
			addMoveIfNecessary(possibleMoves, move);
		}
		move = null;

		// topRight
		vX = 1;
		vY = -1;
		move = checkDirection(startX + vX, startY + vY, vX, vY, player);
		if (move != null) {
			addMoveIfNecessary(possibleMoves, move);
		}
		move = null;

		// bottomRight
		vX = 1;
		vY = 1;
		move = checkDirection(startX + vX, startY + vY, vX, vY, player);
		if (move != null) {
			addMoveIfNecessary(possibleMoves, move);
		}
		move = null;

		// bottomLeft
		vX = -1;
		vY = 1;
		move = checkDirection(startX + vX, startY + vY, vX, vY, player);
		if (move != null) {
			addMoveIfNecessary(possibleMoves, move);
		}
		move = null;

		// topLeft
		vX = -1;
		vY = -1;
		move = checkDirection(startX + vX, startY + vY, vX, vY, player);
		if (move != null) {
			addMoveIfNecessary(possibleMoves, move);
		}
		move = null;
	}

	/**
	 * Small helper method
	 * 
	 * @param x
	 * @param y
	 * @param player
	 * @param currentState
	 * @return
	 */
	private FieldEvaluation getFieldEvaluation(int x, int y, Object player,
			FieldEvaluation currentState) {
		int fieldValue = board.compareFieldTo(x, y, player);

		if (fieldValue == 1) {
			return FieldEvaluation.NOT_POSSIBLE;
		} else if (fieldValue == 0) {
			if (currentState == FieldEvaluation.MAYBE) {
				return FieldEvaluation.VALID_MOVE;
			}
			return FieldEvaluation.NOT_POSSIBLE;
		} else {
			return FieldEvaluation.MAYBE;
		}
	}

	/**
	 * adds the specific move to the list only if it is not already contained by
	 * it.
	 * 
	 * @param possibleMoves
	 * @param othelloMove
	 */
	private void addMoveIfNecessary(List<OthelloPosition> possibleMoves,
			OthelloPosition othelloMove) {
		if (!possibleMoves.contains(othelloMove)) {
			possibleMoves.add(othelloMove);
		}
	}

	/**
	 * Returns true if move is one of the available moves.
	 * 
	 * @param move
	 * @param player
	 * @return
	 */
	public boolean isMoveValid(OthelloPosition move, Bot<?, ?> player) {
		return getAvailableMoves(player).contains(move);
	}

	private enum FieldEvaluation {
		UNKNOWN, NOT_POSSIBLE, MAYBE, VALID_MOVE
	}

}
