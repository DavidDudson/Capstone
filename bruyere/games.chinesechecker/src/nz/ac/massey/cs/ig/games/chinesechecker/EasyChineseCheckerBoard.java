package nz.ac.massey.cs.ig.games.chinesechecker;

import java.util.ArrayList;
import java.util.List;

/**
 * Easy {@link ChineseCheckerBoard} implementation.It handles requests for
 * {@link ChineseCheckerMove} and provides currently available moves for both
 * players.
 * 
 * @author Li Sui
 *
 */
public class EasyChineseCheckerBoard extends ChineseCheckerBoard {

	public EasyChineseCheckerBoard(Object player1, Object player2, int fieldSize) {
		super(player1, player2, fieldSize);
	}

	public EasyChineseCheckerBoard(Object player1, Object player2) {
		super(player1, player2);
	}

	public EasyChineseCheckerBoard(ChineseCheckerBoard board) {
		super(board);
	}

	/**
	 * Makes a move for the current player
	 * 
	 * @param move
	 */
	public void makeMove(ChineseCheckerPosition move) {
		setField(move.getDestinationX(), move.getDestinationY());
	}
	/**
	 * Returns list of available moves for the current player
	 * 
	 * @return List<GomokuPosition>
	 */
	public List<ChineseCheckerPosition> getAvailablePiecesForMe() {
		List<ChineseCheckerPosition> possible = new ArrayList<ChineseCheckerPosition>();
		for(int y = 0;y<getFieldSize();y++) {
			for(int x=0;x<getFieldSize();x++) {
				if (isFieldMine(x,y) && isAvaliablePiece(x,y) ) {
					ChineseCheckerPosition p=new ChineseCheckerPosition();
					p.setPiecePosition(x, y);
					possible.add(p);
				}
			}
		}
		return possible;
	}
	
	public List<CCPossibleMove> getAvailableMovesForAPiece(ChineseCheckerPosition piece){
		return null;
	}
	
	/**
	 * see {@link #getCurrentBoard()} for more information.
	 * @return String[][]
	 */
	public String[][] getCurrentBoard(){
		return super.getCurrentBoard();
	}
	
	@Override
	public EasyChineseCheckerBoard copy() {
		return new EasyChineseCheckerBoard(this);
	}

	/**
	 * see {@link #isFieldFree(int, int)} for more information.
	 * 
	 * @param pos
	 * @return boolean
	 */
	public boolean isFieldFree(ChineseCheckerPosition pos) {
		return super.isFieldFree(pos.getDestinationX(), pos.getDestinationY());
	}
	/**
	 * see {@link #isFieldFree(int, int)} for more information.
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @return boolean
	 */
	public boolean isFieldFree(int x,int y) {
		return super.isFieldFree(x, y);
	}
	
	/**
	 * see {@link #isFieldMine(int, int)} for more information.
	 * 
	 * @param pos
	 * @return boolean
	 */
	public boolean isPieceMine(ChineseCheckerPosition pos){
		return super.isFieldMine(pos.getPieceX(), pos.getPieceY());
	}
	
	/**
	 * see {@link #isFieldMine(int, int)} for more information.
	 * 
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @return boolean
	 */
	public boolean isFieldMine(int x,int y){
		return super.isFieldMine(x, y);
	}

	public void removePiece(ChineseCheckerPosition move) {
		super.remove(move.getPieceX(),move.getPieceY());
	}
}
