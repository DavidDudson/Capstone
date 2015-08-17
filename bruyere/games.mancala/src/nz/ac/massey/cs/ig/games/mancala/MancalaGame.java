	
package nz.ac.massey.cs.ig.games.mancala;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.core.game.SimpleGame;

/**
 * Mancala game implementation.
 * The game is represented by a list of ints as follows:
 * 
 *    	 /-------------------------------\
 *    	 |   | 0 | 1 | 2 | 3 | 4 | 5 |   |
 *    	 | 6 |-----------------------| 6 |
 *    	 |   | 5 | 4 | 3 | 2 | 1 | 0 |   |
 *    	 \-------------------------------/
 *    
 * Stones are moved clock-wise, the positions with an index 6 are the mancalas. 
 * Rules:
 * The score is the number of stones in the mancala of the player. 
 * If the last stone during a turn is placed in a mancala, then this player has another turn.
 * If the game ends (a player empties all of his/her pits 0..5), then the other player can add all stones left in his/her pits
 * to his/her mancala. 
 * If during the turn the last stone is placed in an empty pit, the player can move all stones from the opposite pit into his/her mancala. 
 * @author jens dietrich
 */
public class MancalaGame extends SimpleGame<Mancala, Integer> {
	
	// internal representation - used to serialise move details for visualisation on the client
	// a move that plays 3 stones at 1 and there are for stones in each pit is represented by the two lists 
	// positions: [1,2,3,4,5], values: [0,5,5,5,5] ,players: [1,1,1,1,1] (all pits that have changed belong to player 1)
	
	static class Move {
		
		//String playerId = null;
		boolean firstChangeRecorded = true;
		boolean player1StartedMove = false;
		int startPit = -1;
		int stonesPlayed = 0;
		
		int[] player1Pits = new int[7]; // includes mancala, @ 6
		int[] player2Pits = new int[7]; // includes mancala, @ 6
		
		List<Integer> player1PitsChanged = new ArrayList<>();
		List<Integer> player2PitsChanged = new ArrayList<>();
		
		// represents the initial state of the game
		static Move getMoveZero() {
			Move move = new Move();
			move.player1Pits = new int[]{4,4,4,4,4,4};
			move.player2Pits = new int[]{4,4,4,4,4,4};
			// nothing changed
			move.player1StartedMove = false;
			move.stonesPlayed = 0;
			return move;
		}
		void valueChanged(int position,boolean player1) {
			
			// System.out.println("Put stone into pit " + (player1?1:2) + " / " + position);
			if (firstChangeRecorded) {
				player1StartedMove = player1;
				startPit = position;
				firstChangeRecorded = false;
			}
			if (player1) player1PitsChanged.add(position);
			else player2PitsChanged.add(position);
		}
		
		
		// final state of game after move
		void setGameStateAfterMove(Mancala board) {
			for (int i=0;i<6;i++) player1Pits[i]=board.isPlayer1Playing()?board.getStonesInMyPit(i):board.getStonesInOtherPit(i);
			player1Pits[6]=board.isPlayer1Playing()?board.getStonesInMyMancala():board.getStonesInOtherMancala();
			for (int i=0;i<6;i++) player2Pits[i]=board.isPlayer2Playing()?board.getStonesInMyPit(i):board.getStonesInOtherPit(i);
			player2Pits[6]=board.isPlayer2Playing()?board.getStonesInMyMancala():board.getStonesInOtherMancala();
		}
	}
	private List<Move> moves = new ArrayList<Move>();
    
    // record history
    public static final String MOVE_SEPARATOR = "-";
    
    private StringBuilder historyInURLToken = new StringBuilder();
    
    private Mancala board = new Mancala();
    
    
    public MancalaGame(String uid, Bot<Mancala, Integer> player1, Bot<Mancala, Integer> player2) {
        super(uid,player1,player2);
        this.board.setPlayer1(player1);
        this.board.setPlayer2(player2);
        this.moves.add(Move.getMoveZero());
    }
 
	@Override
	protected void prepareMove(Bot<Mancala, Integer> player) {
		this.board.setActivePlayer(player);
	}

	@Override
    protected void doMove(Integer position, Bot<Mancala, Integer>  player) {
    	
    	this.board.setActivePlayer(player);

    	Move move = new Move();
    	boolean player1Playing = this.board.isPlayer1Playing();
    	
    	int stillToPlace = board.getStonesInMyPit(position);
    	move.stonesPlayed = stillToPlace;
    	
        // empty pit
    	board.changeStonesInMyPit(position, -stillToPlace);
    	move.valueChanged(position,player1Playing);
    	
    	boolean bonusMove = false;
    	
    	while (stillToPlace>0) {
    		boolean isEmptyPit = false;
    		position = position+1;
    		position = position%14; // come around
    		if (position<6) {
    			isEmptyPit = board.getStonesInMyPit(position)==0;
    			board.changeStonesInMyPit(position,1);
    			move.valueChanged(position,player1Playing);
    		}
    		else if (position==6) {
    			board.addStonesToMyMancala(1);
    			move.valueChanged(position,player1Playing);
    		}
      		else if (position<13) {
      			board.changeStonesInOtherPit(position-7,1);
    			move.valueChanged(position-7,!player1Playing);
    		}
      		else if (position==13) {
      			// in most rule sets, stones are not placed into the mancala of the opposite player
//    			board.addStonesToOtherMancala(1);
//    			move.valueChanged(6,!player1Playing);
      			stillToPlace = stillToPlace+1; // increase to keep unchanged
    		}
    		stillToPlace = stillToPlace-1;
    		
    		// special rule: get stones in opposite pit if last pit was empty
    		if (isEmptyPit && stillToPlace==0 && position<6) {
    			int opposite = 5-position;
    			int stones = board.getStonesInOtherPit(opposite);
    			if (stones>0) {
    				board.changeStonesInOtherPit(opposite,stones);
    				move.valueChanged(opposite,!player1Playing);
    				board.addStonesToMyMancala(stones);
    				move.valueChanged(6,player1Playing);
    			}
    		}
    		
    		// special rule: if last position was own mancala, have a bonus move
    		bonusMove = position==6;
    	}
    	
    	move.setGameStateAfterMove(board);
    	moves.add(move);
    	
        if (player1Playing) {
        	this.state = bonusMove?GameState.WAITING_FOR_PLAYER_1:GameState.WAITING_FOR_PLAYER_2;
        }
        else {
        	this.state = bonusMove?GameState.WAITING_FOR_PLAYER_2:GameState.WAITING_FOR_PLAYER_1;
        }
    }
    

    
    
    @Override
    protected void registerMove(Integer v,Bot<Mancala, Integer>  playerId) {

        this.timeWhenLastSuccessfulMoveWasMade = System.currentTimeMillis();
        if (historyInURLToken.length()>0) historyInURLToken.append(MOVE_SEPARATOR);
        historyInURLToken.append(v);
    }

    @Override
    protected void checkValidityOfMove(Integer position, Bot<Mancala, Integer>  player) throws IllegalMoveException  {
    	this.board.setActivePlayer(player);
    	if (position<0 || position>5) {
    		throw new IllegalMoveException("The position of the pit must be between 0 and 5, but is " + position);
    	}
    	if (this.board.getStonesInMyPit(position)==0) {
    		throw new IllegalMoveException("Cannot play position " + position + ", this pit is empty.");
    	}
    }
    
    @Override
    protected void checkGameTermination() {

    	int stonesInMyPits = 0;
    	int stonesInOtherPits = 0;
    	for (int i=0;i<6;i++) stonesInMyPits = stonesInMyPits+board.getStonesInMyPit(i);
    	for (int i=0;i<6;i++) stonesInOtherPits = stonesInOtherPits+board.getStonesInOtherPit(i);
    	
    	if (stonesInMyPits==0 || stonesInOtherPits==0) {
    		
    		// place all remaining stones of other player into its mancala
    		Move finalMove = new Move();
    		if (stonesInMyPits==0) {
    			for (int i=0;i<6;i++) {
    				int s = board.getStonesInOtherPit(i);
    				if (s>0) {
    					board.changeStonesInOtherPit(i,-s);
    					finalMove.valueChanged(i,!board.isPlayer1Playing());
    					board.addStonesToOtherMancala(s);
    					finalMove.valueChanged(6,!board.isPlayer1Playing());
    				}
    			}
    		}
    		else if (stonesInOtherPits==0) {
    			for (int i=0;i<6;i++) {
    				int s = board.getStonesInMyPit(i);
    				if (s>0) {
    					board.changeStonesInMyPit(i,-s);
    					finalMove.valueChanged(i,board.isPlayer1Playing());
    					board.addStonesToOtherMancala(s);
    					finalMove.valueChanged(6,board.isPlayer1Playing());
    				}
    			}
    		}
    		finalMove.setGameStateAfterMove(board);
    		this.moves.add(finalMove);
    		
        	int player1Score = this.board.getStonesInMyMancala();
        	int player2Score = this.board.getStonesInOtherMancala();

        	
        	// the current player is not necessarily player 1 - swap if necessary
        	if (board.isPlayer2Playing()) {
        		int tmp = player1Score;
        		player1Score = player2Score;
        		player2Score = tmp;
        	}
        	
    		if (player1Score>player2Score) {
    			this.state = GameState.PLAYER_1_WON;
    		}
    		else if (player1Score<player2Score) {
    			this.state = GameState.PLAYER_2_WON;
    		}
    		else {
    			this.state = GameState.TIE;
    		}
    	}

    }

	@Override
	public Mancala getPublicState() {
		// TODO check
		return board;
	}

	public List<Move> getMoves() {
		return Collections.unmodifiableList(moves);
	}
	
	// =============== METHODS INTENDED FOR TESTING ONLY 
	
	// for testing only
    public MancalaGame(String uid, Bot<Mancala, Integer> player1, Bot<Mancala, Integer> player2,int[] pits1,int mancala1,int[] pits2,int mancala2) {
        super(uid,player1,player2);
        board = new Mancala(pits1,mancala1,pits2,mancala2);
        this.board.setPlayer1(player1);
        this.board.setPlayer2(player2);
        
    }

    // for testing only
    private String boardToHTMLString() {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	PrintStream ps = new PrintStream(baos);
    	_printBoard(ps,"");
    	try {
			return baos.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "<cannot print board>";
		} 
    }
    // for testing only
    public void _printBoard() {
    	_printBoard(System.out,"");
    }
    public void _printBoard(PrintStream out,String eol) {
    	out.print("MANCALA BOARD (player 2 on top)");
    	out.println(eol);
    	out.print("/------------------------------\\");
    	out.println(eol);
    	
    	// ---
    	
    	out.print("|   |");
    	for (int i=0;i<6;i++) {
    		if (board.isPlayer1Playing()) {
    			_printValue(out,board.getStonesInOtherPit(i));
    		}
    		else {
    			_printValue(out,board.getStonesInMyPit(i));
    		}
    	}
    	out.print("   |");
    	out.println(eol);
    	
    	// --- 
    	
    	out.print("|");
    	if (board.isPlayer1Playing()) {
			_printValue(out,board.getStonesInMyMancala());
		}
		else {
			_printValue(out,board.getStonesInOtherMancala());
		}
    	out.print("=======================");
    	out.print("|");
    	if (board.isPlayer1Playing()) {
			_printValue(out,board.getStonesInOtherMancala());
		}
		else {
			_printValue(out,board.getStonesInMyMancala());
		}
    	out.println(eol);
    	
    	// --- 
    	
    	out.print("|   |");
    	for (int i=5;i>-1;i--) {
    		if (board.isPlayer1Playing()) {
    			_printValue(out,board.getStonesInMyPit(i));
    		}
    		else {
    			_printValue(out,board.getStonesInOtherPit(i));
    		}
    	}
    	out.print("   |");
    	out.println(eol);
    	
    	// --- 
    	
    	out.print("\\------------------------------/");
    	out.println(eol);
    	out.println(eol);
    	
    }

    private void _printValue(PrintStream out, int i) {
		out.print(' ');
		out.print(i);
		if (i<10) out.print(' '); // fill space
		out.print('|');
	}
    
    public int _getStonesInMancala1() {
		return this.board.isPlayer1Playing()?this.board.getStonesInMyMancala():this.board.getStonesInOtherMancala();
    }

    public int _getStonesInMancala2() {
		return this.board.isPlayer1Playing()?this.board.getStonesInOtherMancala():this.board.getStonesInMyMancala();
    }
}
