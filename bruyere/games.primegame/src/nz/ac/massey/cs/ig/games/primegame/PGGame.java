package nz.ac.massey.cs.ig.games.primegame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.core.game.SimpleGame;

/**
 * TTT game implementation
 * 
 * @author jens dietrich
 */
public class PGGame extends SimpleGame<List<Integer>, Integer> {

	public class Move {
		private int player1ScorePlayed = 0;
		private int player2ScorePlayed = 0;
		private List<Integer> player1ScoreForced = new ArrayList<Integer>();
		private List<Integer> player2ScoreForced = new ArrayList<Integer>();

		public int getPlayer1ScorePlayed() {
			return player1ScorePlayed;
		}

		void setPlayer1ScorePlayed(int player1ScorePlayed) {
			this.player1ScorePlayed = player1ScorePlayed;
		}

		public int getPlayer2ScorePlayed() {
			return player2ScorePlayed;
		}

		void setPlayer2ScorePlayed(int player2ScorePlayed) {
			this.player2ScorePlayed = player2ScorePlayed;
		}

		public List<Integer> getPlayer1ScoreForced() {
			return (List<Integer>) Collections
					.unmodifiableList(player1ScoreForced);
		}

		void addToPlayer1ScoreForced(int v) {
			this.player1ScoreForced.add(v);
		}

		public List<Integer> getPlayer2ScoreForced() {
			return (List<Integer>) Collections
					.unmodifiableList(player2ScoreForced);
		}

		void addToPlayer2ScoreForced(int v) {
			this.player2ScoreForced.add(v);
		}

		@Override
		public String toString() {
			return "Move [player1ScorePlayed=" + player1ScorePlayed
					+ ", player2ScorePlayed=" + player2ScorePlayed
					+ ", player1ScoreForced=" + player1ScoreForced
					+ ", player2ScoreForced=" + player2ScoreForced + "]";
		}
	}

	private List<Move> moves = new ArrayList<Move>();

	// record history
	public static final String DIM_SEPARATOR = "_"; // FIXME - is this still
													// needed ?

	public static final String MOVE_SEPARATOR = "-";
	private int BOARD_SIZE = 100;

	private StringBuilder historyInURLToken = new StringBuilder();

	private Set<Integer> board = new HashSet<Integer>();
	private int player1score = 0;
	private int player2score = 0;

	/**
	 * Get the board size.
	 * 
	 * @return the size of the board
	 */
	public int getBoardSize() {
		return BOARD_SIZE;
	}

	/**
	 * Set the board size.
	 * 
	 * @param the
	 *            size of the board
	 */
	public void setBoardSize(int s) {
		if (this.state == GameState.NEW) {
			BOARD_SIZE = s;
			initBoard();
		} else
			throw new IllegalStateException(
					"Cannot change game size after game has started");
	}

	public PGGame(String uid, Bot<List<Integer>, Integer> player1,
			Bot<List<Integer>, Integer> player2) {
		super(uid, player1, player2);
		initBoard();
	}

	private void initBoard() {
		board.clear();
		for (int i = 1; i <= BOARD_SIZE; i++) {
			board.add(i);
		}
	}

	@Override
	protected void doMove(Integer v, Bot<List<Integer>, Integer> playerId) {
		/*
		 * System.out.println("player " + playerId + " plays " + v);
		 * System.out.println("player1 score before: " + this.player1score);
		 * System.out.println("player2 score before: " + this.player2score);
		 */

		boolean player1Moved = playerId.equals(this.getPlayer1());

		Move move = new Move();
		if (board.remove(v)) {
			if (player1Moved) {
				player1score = player1score + v;
				move.setPlayer1ScorePlayed(v);
			} else {
				player2score = player2score + v;
				move.setPlayer2ScorePlayed(v);
			}
		}

		for (int i = 1; i < v; i++) {
			if (v % i == 0 && board.remove(i)) {
				if (player1Moved) {
					player2score = player2score + i;
					move.addToPlayer2ScoreForced(i);
				} else {
					player1score = player1score + i;
					move.addToPlayer1ScoreForced(i);
				}
			}
		}
		moves.add(move);

		if (player1Moved) {
			this.state = GameState.WAITING_FOR_PLAYER_2;
		} else {
			this.state = GameState.WAITING_FOR_PLAYER_1;
		}
		/*
		 * System.out.println("player1 score after: " + this.player1score);
		 * System.out.println("player2 score after: " + this.player2score);
		 * System.out.println();
		 */
	}

	@Override
	protected void registerMove(Integer v, Bot<List<Integer>, Integer> playerId) {

		this.timeWhenLastSuccessfulMoveWasMade = System.currentTimeMillis();
		if (historyInURLToken.length() > 0)
			historyInURLToken.append(MOVE_SEPARATOR);
		historyInURLToken.append(v);
	}

	@Override
	protected void checkValidityOfMove(Integer v,
			Bot<List<Integer>, Integer> playerId) throws IllegalMoveException {

		if (v < 1) {
			throw new IllegalMoveException("The number played " + v
					+ " is not on the board, values must be 0 or greater");
		} else if (v > BOARD_SIZE) {
			throw new IllegalMoveException("The number played " + v
					+ " is not on the board, values must be less than "
					+ BOARD_SIZE);
		} else if (!board.contains(v)) {
			throw new IllegalMoveException("The number " + v + " played by "
					+ playerId.getId() + " has already been played");
		}
	}

	private String getPrintableBoard() {
		StringBuffer board = new StringBuffer();

		boolean first = true;
		for (int i : this.board) {
			if (!first) {
				board.append(", ");
			} else {
				first = false;
			}

			board.append(i);
		}

		return board.toString();
	}

	@Override
	protected void checkGameTermination() {
		if (board.isEmpty()) {
			if (player1score > player2score) {
				this.state = GameState.PLAYER_1_WON;
			} else if (player1score < player2score) {
				this.state = GameState.PLAYER_2_WON;
			} else {
				this.state = GameState.TIE;
			}
		}
	}

	@Override
	public List<Integer> getPublicState() {
		List<Integer> l = new ArrayList<Integer>();
		l.addAll(board);
		Collections.sort(l);
		return l;
	}

	public List<Move> getMoves() {
		return Collections.unmodifiableList(moves);
	}

	public int getPlayer1score() {
		return player1score;
	}

	public int getPlayer2score() {
		return player2score;
	}
}
