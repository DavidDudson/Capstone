package nz.ac.massey.cs.ig.games.othello;

import java.util.ArrayList;
import java.util.List;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.core.game.SimpleGame;

/**
 * Basic implementation of othello as a game
 * 
 * @author Johannes Tandler
 *
 */
public class OthelloGame extends SimpleGame<EasyOthelloBoard, OthelloPosition> {

	/**
	 * current board
	 */
	private EasyOthelloBoard currentBoard;

	/**
	 * validation helper of the game
	 */
	private OthelloValidator validator;

	/**
	 * history of moves
	 */
	private List<OthelloHistoryEntry> moves;

	@SuppressWarnings("unchecked")
	public OthelloGame(String uid, Bot<?, ?> player1, Bot<?, ?> player2) {
		super(uid, (Bot<EasyOthelloBoard, OthelloPosition>)player1, (Bot<EasyOthelloBoard, OthelloPosition>) player2);

		moves = new ArrayList<OthelloHistoryEntry>();
		currentBoard = new EasyOthelloBoard(player1, player2);
		validator = new OthelloValidator(currentBoard);
		currentBoard.setMe(player1);

		moves.add(new OthelloHistoryEntry(getPublicState(), new OthelloPosition(-1, -1), null));
	}

	/**
	 * Returns a copy of the board so nobody can change it.
	 */
	@Override
	public EasyOthelloBoard getPublicState() {
		return currentBoard.copy();
	}

	@Override
	protected void doMove(OthelloPosition move,
			Bot<EasyOthelloBoard, OthelloPosition> bot) {
		// make move
		currentBoard.setMe(bot);
		currentBoard.makeMove(move.getX(), move.getY());

		// if there are moves available for enemy switch current player and
		// state
		if (currentBoard.getAvailableMovesForEnemy().size() != 0) {
			currentBoard.switchPlayerPerspective();

			if (currentBoard.isMe(player1)) {
				this.state = GameState.WAITING_FOR_PLAYER_1;
			} else {
				this.state = GameState.WAITING_FOR_PLAYER_2;
			}
		}
	}

	@Override
	protected void registerMove(OthelloPosition move,
			Bot<EasyOthelloBoard, OthelloPosition> bot) {
		if(move == null) {
			return;
		}
		moves.add(new OthelloHistoryEntry(getPublicState(), move, bot.getId()));
	}

	/**
	 * Returns history of all moves
	 * 
	 * @return
	 */
	public List<OthelloHistoryEntry> getHistory() {
		return moves;
	}

	@Override
	protected void checkValidityOfMove(OthelloPosition move,
			Bot<EasyOthelloBoard, OthelloPosition> playerId)
			throws IllegalMoveException {
		if (!validator.isMoveValid(move, playerId)) {
			throw new IllegalMoveException("Player " + playerId.getId()
					+ " is not allowed to make move " + move
					+ ". <br/> Board was : <br/><pre>"
					+ currentBoard.toString() + "</pre>");
		}
	}

	@Override
	protected void checkGameTermination() {
		if (validator.getAvailableMoves(player1).size() == 0
				&& validator.getAvailableMoves(player2).size() == 0) {
			if (currentBoard.getScoreOfBlack() > currentBoard.getScoreOfWhite()) {
				this.state = GameState.PLAYER_1_WON;
			} else if (currentBoard.getScoreOfBlack() < currentBoard
					.getScoreOfWhite()) {
				this.state = GameState.PLAYER_2_WON;
			} else {
				this.state = GameState.TIE;
			}
		}
	}

}
