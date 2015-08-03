package nz.ac.massey.cs.ig.games.mancala;

import com.google.common.base.Preconditions;
import nz.ac.massey.cs.ig.core.game.Bot;

/**
 * This is the data structure representing the mancala board.
 * This is the API users program against.
 * @author jens dietrich
 *
 */
public final class Mancala {
	
	/**
	 * Make constructor not public - users cannot directly instantiate this. 
	 */
	Mancala() {
		super();
	}
	
	// for testing only - inject state !
	Mancala(int[] pits1,int mancala1,int[] pits2,int mancala2) {
		this();
		Preconditions.checkArgument(pits1.length==6);
		Preconditions.checkArgument(pits2.length==6);
		this.pits1 = pits1;
		this.pits2 = pits2;
		this.mancala1 = mancala1;
		this.mancala2 = mancala2;
	}
	
	
	private Bot<Mancala, Integer> player1 = null;
	private Bot<Mancala, Integer> player2 = null;
	private Bot<Mancala, Integer> activePlayer = null; 
	
	private int[] pits1 = {4,4,4,4,4,4};
	private int[] pits2 = {4,4,4,4,4,4};
	
	private int mancala1 = 0;
	private int mancala2 = 0;
	
	// public API for user
	
	/**
	 * Get the stones in one of my pits.
	 * @param position the position of the pit, a value between 0 (first pit) and 5 (last pit)
	 * @return the number of stones in this pit
	 */
	public int getStonesInMyPit(int position) {
		Preconditions.checkElementIndex(position, 6);
		int[] pits = this.activePlayer==player1 ? this.pits1 : this.pits2;
		return pits[position];
	}
	
	/**
	 * Get the stones in one of the other players pits.
	 * @param position the position of the pit, a value between 0 (first pit) and 5 (last pit)
	 * @return the number of stones in this pit
	 */
	public int getStonesInOtherPit(int position) {
		Preconditions.checkElementIndex(position, 6);
		int[] pits = this.activePlayer==player1 ? this.pits2 : this.pits1;
		return pits[position];
	}
	/**
	 * Get the stones in my mancala.
	 * @return the number of stones in my mancala
	 */
	public int getStonesInMyMancala() {
		return this.activePlayer==player1 ? mancala1 : mancala2;
	}
	/**
	 * Get the stones in the other players mancala.
	 * @return the number of stones in the other mancala
	 */
	public int getStonesInOtherMancala() {
		return this.activePlayer==player1 ? mancala2 : mancala1;
	}
	
	// non - public methods to be used within this package

	void setPlayer1 (Bot<Mancala, Integer> player1) {
		Preconditions.checkState(this.player1==null,"player 1 can only be set once");
		this.player1 = player1;
	}
	void setPlayer2 (Bot<Mancala, Integer> player2) {
		Preconditions.checkState(this.player2==null,"player 2 can only be set once");
		this.player2 = player2;
	}
	void setActivePlayer (Bot<Mancala, Integer> activePlayer) {
		Preconditions.checkState(this.player1!=null,"player 1 must be set");
		Preconditions.checkState(this.player2!=null,"player 2 must be set");
		Preconditions.checkState(this.player1==activePlayer || this.player2==activePlayer,"the active player must either be player 1 or player 2");
		this.activePlayer = activePlayer;
	}
	boolean isPlayer1Playing() {
		return this.activePlayer==player1;
	}
	boolean isPlayer2Playing() {
		return this.activePlayer==player2;
	}
	void changeStonesInMyPit(int position, int diff) {
		Preconditions.checkElementIndex(position, 6);
		Preconditions.checkState(this.activePlayer!=null);
		int[] pits = this.activePlayer==player1 ? this.pits1 : this.pits2;
		pits[position] = pits[position] + diff;
	}
	void changeStonesInOtherPit(int position, int diff) {
		Preconditions.checkElementIndex(position, 6);
		Preconditions.checkState(this.activePlayer!=null);
		int[] pits = this.activePlayer==this.player1 ? this.pits2 : this.pits1;
		pits[position] = pits[position] + diff;
	}
	

	void addStonesToMyMancala(int count) {
		Preconditions.checkArgument(count>0);
		Preconditions.checkState(this.activePlayer!=null);
		if (this.activePlayer==player1) {
			this.mancala1 = mancala1 + count;
		}
		else {
			this.mancala2 = mancala2 + count;
		}
	}

	void addStonesToOtherMancala(int count) {
		Preconditions.checkArgument(count>0);
		Preconditions.checkState(this.activePlayer!=null);
		if (this.activePlayer==player1) {
			this.mancala2 = mancala2 + count;
		}
		else {
			this.mancala1 = mancala1 + count;
		}
	}

}
