package nz.ac.massey.cs.ig.games.chinesechecker;

import java.util.ArrayList;
import java.util.List;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.core.game.SimpleGame;


public class ChineseCheckerGame extends SimpleGame<EasyChineseCheckerBoard,ChineseCheckerPosition> {
	
	private EasyChineseCheckerBoard currentBoard;

	private List<ChineseCheckerHistoryEntry> moves;
	
	@SuppressWarnings("unchecked")
	public ChineseCheckerGame(String uid, Bot<?, ?> player1, Bot<?, ?> player2) {
		super(uid, (Bot<EasyChineseCheckerBoard, ChineseCheckerPosition>)player1, (Bot<EasyChineseCheckerBoard, ChineseCheckerPosition>) player2);
		moves = new ArrayList<ChineseCheckerHistoryEntry>();
		currentBoard = new EasyChineseCheckerBoard(player1, player2);
	

		moves.add(new ChineseCheckerHistoryEntry(getPublicState(), null, null));
	}

	/**
	 * Returns a copy of the board so nobody can change it.
	 */
	@Override
	public EasyChineseCheckerBoard getPublicState() {
		return currentBoard.copy();
	}

	@Override
	protected void doMove(ChineseCheckerPosition move, Bot<EasyChineseCheckerBoard, ChineseCheckerPosition> bot) {
		currentBoard.setMe(bot);
		currentBoard.makeMove(move);	
		currentBoard.removePiece(move);

		currentBoard.switchPlayerPerspective();
			
		if (currentBoard.isMe(player1)) {
			this.state = GameState.WAITING_FOR_PLAYER_1;
		} else {
			this.state = GameState.WAITING_FOR_PLAYER_2;
		}
		
	}
	public List<ChineseCheckerHistoryEntry> getHistory(){
		return this.moves;
	}
	@Override
	protected void registerMove(ChineseCheckerPosition move,Bot<EasyChineseCheckerBoard, ChineseCheckerPosition> bot) {
	    moves.add(new ChineseCheckerHistoryEntry(getPublicState(), move, bot.getId()));  
	}

	@Override
	protected void checkValidityOfMove(ChineseCheckerPosition move,Bot<EasyChineseCheckerBoard, ChineseCheckerPosition> playerId) throws IllegalMoveException {
		
	}

	@Override
	protected void checkGameTermination() {
		
		if(currentBoard.isPlayer1Won()){
			this.state=GameState.PLAYER_1_WON;
		}
		if(currentBoard.isPlayer2Won()){
			this.state=GameState.PLAYER_2_WON;
		}
	} 
}
