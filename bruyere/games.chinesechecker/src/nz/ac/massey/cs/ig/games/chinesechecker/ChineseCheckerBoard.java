package nz.ac.massey.cs.ig.games.chinesechecker;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic implementation of an ChineseChecker board.
 * 
 * <h1>Board structure</h1> This is a board with X*X fields, where X is 8 if not
 * defined somehow else. Filed size must be even number and less than 12
 * 
 * board looks like :<br/>
 * 
 * <pre>
 * -01234567
 * 0++++----
 * 1+++-----
 * 2++------
 * 3+-------
 * 4-------o
 * 5------oo
 * 6-----ooo
 * 7----oooo
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
 * -01234567
 * 0++++----
 * 1+++-----
 * 2++---A--
 * 3+-------
 * 4-------o
 * 5------oo
 * 6-----ooo
 * 7----oooo
 * </pre>
 * 
 * @author Li Sui
 *
 */
public class ChineseCheckerBoard {
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
	 * current player
	 */
	protected Object currentPlayer;
	/**
	 * Default Constructor which creates a board for {@link #player1} as player
	 * 1 and {@link #player2} as player 2 and a bord size of 8.
	 * 
	 * @param player1
	 *            Principal for Player I
	 * @param player2
	 *            Principal for Player II
	 */
	public ChineseCheckerBoard(Object player1, Object player2) {
		this(player1, player2, 8);
	}

	/**
	 * See {@link #ChineseCheckerBoard(Object, Object)} for more details. This creates
	 * a board with a specified board size {@link #fieldSize}.
	 * 
	 * @param player1
	 *            Principal for Player I
	 * @param player2
	 *            Principal for Player II
	 * @param fieldSize
	 *            size of axis
	 */
	public ChineseCheckerBoard(Object player1, Object player2, int fieldSize) {
		if (fieldSize % 2 !=0 || fieldSize >12) {
			throw new UnsupportedOperationException(
					"Only field sizes must be even number and less than 12");
		}
		this.player1 = player1;
		this.player2 = player2;
		currentPlayer=player1;
		this.fieldSize = fieldSize;
		field = new Object[fieldSize][fieldSize];

		initField();
	}

	/**
	 * Creates a duplicate {@link ChineseCheckerBoard} of a given {@link ChineseCheckerBoard}
	 * 
	 * @param board
	 *            {@link ChineseCheckerBoard} which should be duplicated.
	 */
	public ChineseCheckerBoard(ChineseCheckerBoard board) {
		this.player1 = board.player1;
		this.player2 = board.player2;
		this.fieldSize = board.fieldSize;
		currentPlayer=player1;
		this.field = new Object[fieldSize][fieldSize];

		for (int y = 0; y < fieldSize; y++) {
			for (int x = 0; x < board.field[y].length; x++) {
				this.field[y][x] = board.field[y][x];
			}
		}
	}

	/**
	 * initialize field with default values.
	 */
	private void initField() {
		int mid =fieldSize/2;
		int counter =mid;
		for (int y = 0; y < mid; y++) {
			
			for (int x = 0; x < counter; x++) {
				field[y][x]=player1;
				field[fieldSize-1-y][fieldSize-1-x]=player2;
			}
			counter--;
		}
	}
	/**
	 * Sets the field to a specific value
	 * 
	 * @param x
	 *            x position starting by 0
	 * @param y
	 *            y position starting by 0
	 * @param val
	 *            Principal of field owner
	 */
	protected void setField(int x, int y) {
		if (currentPlayer != null && currentPlayer != player1 && currentPlayer != player2) {
			throw new UnsupportedOperationException(
					"Fields can be only set to null or one of players");
		}
		validatePosition(x,y);
		field[y][x] = currentPlayer;
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
					"field position must be in range of [0,0] and ["
							+ (fieldSize-1) + "," + (fieldSize-1) + "]");
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


	private boolean isOutside(int x, int y) {
		return x< 0 || y< 0 || x >= fieldSize || y >= fieldSize;
	}


	public ChineseCheckerBoard copy(){
		return this;
	}
	/**
	 * is a available position
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @return boolean
	 */
	protected boolean isFieldFree(int x, int y) {
		validatePosition(x,y);
		return field[y][x] == null;
	}
	
	/**
	 * the position is player1
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @return boolean
	 */
	protected boolean isPlayer1(int x, int y){
		validatePosition(x,y);
		return field[y][x] == player1;
	}
	/**
	 * the position is player2
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @return boolean
	 */
	protected boolean isPlayer2(int x, int y) {
		validatePosition(x,y);
		return field[y][x] == player2;
	}
	/**
	 * Changes current player.
	 */
	protected void switchPlayerPerspective() {
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
	protected boolean isFieldMine(int x, int y) {
		validatePosition(x,y);
		return field[y][x] == currentPlayer;
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
	protected boolean isFieldEnemy(int x, int y) {
		validatePosition(x, y);
		return field[y][x] == getEnemy();
	}

	/**
	 * Returns true if parameter bot is current player
	 * 
	 * @param bot principal of player to compare
	 * @return true if parameter bot is current player
	 */
	protected boolean isMe(Object bot) {
		return currentPlayer == bot;
	}

	/**
	 * returns the enemy
	 * 
	 * @return
	 */
	protected Object getEnemy() {
		return currentPlayer == player1 ? player2 : player1;
	}
	/**
	 * get current game board represented in String[][]. eg.
	 * <br/>
	 *<pre>
	 *    0     1     2     3     4     5     6     7     8
	 * 0empty empty empty empty empty empty empty empty empty
	 * 1empty empty   me  empty empty empty empty empty empty
	 * 2empty empty   me  enemy empty empty empty empty empty
	 * 3empty   me  empty enemy empty empty empty empty empty
	 * 4empty empty empty enemy empty empty empty empty empty
	 * 5empty empty empty empty empty empty empty empty empty
	 * 6empty empty empty empty empty empty empty empty empty
	 * 7empty empty empty empty empty empty empty empty empty
	 * 8empty empty empty empty empty empty empty empty empty
	 * </pre>
	 * @return String[][]
	 */
	protected String[][] getCurrentBoard(){
		String [][] boardCopy =new String [fieldSize][fieldSize];
		for(int y=0;y<fieldSize;y++){
			for(int x=0;x<fieldSize;x++){
				if(isFieldMine(x,y)){
					boardCopy[y][x]="me";
				}else if(isFieldEnemy(x,y)){
					boardCopy[y][x]="enemy";
				}else{
					boardCopy[y][x]="empty";
				}
			}
		}
		return boardCopy;
	}
	
	public boolean isPlayer2Won(){

		int mid =fieldSize/2;
		int counter =mid;
		for (int y = 0; y < mid; y++) {
			for (int x = 0; x < counter; x++) {
				if(field[y][x]==player1){
					return false;
				}
			}
			counter--;
		}
		return true;
	}
	
	protected void remove(int x,int y){
		field[y][x] = null;
	}
	
	public boolean isPlayer1Won(){

		int mid =fieldSize/2;
		int counter =mid;
		for (int y = 0; y < mid; y++) {
			for (int x = 0; x < counter; x++) {
				if(field[fieldSize-1-y][fieldSize-1-x]==player2){
					return false;
				}
			}
			counter--;
		}
		return true;
	}
	/**
	 * TODO:complete all avaliableMove
	 * @param x
	 * @param y
	 * @return
	 */
	protected boolean isAvaliablePiece(int x,int y){
		if(checkUp(x,y) || checkDown(x,y) || checkLeft(x,y) || checkDown(x,y)){
			return true;
		}
		return false;
	}
	protected boolean checkUp(int x, int y){

		if(y-1 >=0 ){
			if(field[y-1][x] == null ){
				return true;
			}
		}
		if(y-2>=0){
			if(field[y-2][x] == null && field[y-1][x] !=null){
				return true;
			}
		}
		return false;
		
	}
	protected boolean checkRight(int x,int y){
		
		if(x+1 <fieldSize ){
			if(field[y][x+1] == null ){
				return true;
			}
		}
		if(x+2<fieldSize){
			if(field[y][x+2] == null && field[y][x+1] != null ){
				return true;
			}
		}
		return false;
	}
	protected boolean checkLeft(int x,int y){
		
		if(x-1 >=0 ){
			if(field[y][x-1] == null ){
				return true;
			}
		}
		if(x-2>=0){
			if(field[y][x-2] == null && field[y][x-1] != null){
				return true;
			}
		}
		return false;
	}
	protected boolean checkDown(int x,int y){
		
		if(y+1 <fieldSize ){
			if(field[y+1][x] == null ){
				return true;
			}
		}
		if(y+2<fieldSize){
			if(field[y+2][x] == null &&field[y+1][x] != null){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns easy string representation of an ChineseChecker board.
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("-");

		for (int i = 0; i < fieldSize; i++) {
			buffer.append(i);
		}
		buffer.append("\n");

		for (int y = 0; y < fieldSize; y++) {
			buffer.append(y);

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
