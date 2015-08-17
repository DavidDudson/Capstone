package nz.ac.massey.cs.ig.games.othello;

import java.io.IOException;
import java.io.PrintWriter;

import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.services.Serializer;

/**
 * Serializes an Othelloboard by using its {@link OthelloBoard#toString()} method.
 * @author Jake
 *
 */
public class SimpleOthelloSerializer implements Serializer {

	@Override
	public void encodeGame(Game<?, ?> game, PrintWriter out) throws IOException {
		Object ps = game.getPublicState();
		if (!(ps instanceof OthelloBoard)) {
			return;
		}

		OthelloBoard board = (OthelloBoard) ps;
		out.write(board.toString());
	}

	@Override
	public String getGameContentType() {
		return "cmd";
	}

	/**
	 * deserialize a board
	 * @param s
	 * @param player1
	 * @param player2
	 * @return
	 */
	public OthelloBoard deserialize(String s, Object player1, Object player2) {
		String[] lines = s.split("\n");
		
		int fieldSize = 0;
		String currentLine = lines[0].substring(1);
		while(currentLine.length()>0) {
			fieldSize++;
			currentLine = currentLine.substring(String.valueOf(fieldSize).length());
		}
		
		CustomOthelloBoard board = new CustomOthelloBoard(player1, player2, fieldSize);
		
		for(int y=0;y<fieldSize;y++) {
			currentLine = lines[y+1];
			currentLine = currentLine.substring(String.valueOf(y+1).length());
			
			for(int x=0;x<currentLine.length();x++) {
				char field = currentLine.charAt(x);
				if(field == '-') {
					board.setFieldManual(x, y, null);
				} else if(field == '+') {
					board.setFieldManual(x, y, player1);
				} else if(field == 'o') {
					board.setFieldManual(x, y, player2);
				}
			}
		}
		
		return board;
	}

	@Override
	public String encodeGameCompact(Game<?, ?> game) {
		return null;
	}

	@Override
	public boolean supportsCompactGameEncoding() {
		return false;
	}
	
	
	private class CustomOthelloBoard extends EasyOthelloBoard {

		public CustomOthelloBoard(Object player1, Object player2, int fieldSize) {
			super(player1, player2, fieldSize);
		}

		/**
		 * can be used to set the value of a specific field if you dont want to
		 * spread game changes.
		 * 
		 * WARNING : Only use this to create specific games with a specific state
		 * 
		 * @param x
		 * @param y
		 * @param val
		 */
		public void setFieldManual(int x, int y, Object val) {
			field[y][x] = val;
		}
	}

}
