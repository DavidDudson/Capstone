package nz.ac.massey.cs.ig.games.chinesechecker;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;
import org.json.JSONWriter;

import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.core.services.Serializer;


public class ChineseCheckerSerializer implements Serializer {

	@Override
	public void encodeGame(Game<?, ?> g, PrintWriter out) throws IOException {
		assert g instanceof ChineseCheckerGame;
		ChineseCheckerGame game = (ChineseCheckerGame) g;
		List<ChineseCheckerHistoryEntry> moves = game.getHistory();
		
		JSONStringer json = new JSONStringer();

		try {
			json.object();
			boolean firstEntry = true;
			for (ChineseCheckerHistoryEntry move : moves) {
				
				if(firstEntry) {
					firstEntry = false;
					JSONWriter board = json.key("initialBoard").array();
					serializeBoard(move.getBoard(), board);
					board.endArray();
					
					json.key("moves").array();
					continue;
				}
				
				json.object();
				json.key("player").value(move.getPlayer());
				json.key("x").value(move.getPosition().getDestinationX());
				json.key("y").value(move.getPosition().getDestinationY());

				JSONWriter board = json.key("board").array();
				serializeBoard(move.getBoard(), board);
				board.endArray();
				json.endObject();
			}
			json.endArray();
			if(game.getError() != null) {
				Throwable error = game.getError();
				json.key("error").object();
	        	json.key("type").value(error.getClass().getName());
	        	if (error.getMessage()!=null) json.key("message").value(error.getMessage());
	        	if (error.getStackTrace()!=null) json.key("stacktrace").value(error.getStackTrace());
	        	// winner "by error"
	        	json.key("winner").value(game.getState()==GameState.PLAYER_1_WON?1:(game.getState()==GameState.PLAYER_2_WON?2:0));
	        	json.endObject();	
			}

			json.endObject();
			out.println(json.toString());
			System.out.println(json.toString());
		} catch (JSONException x) {
			System.out.println(x.getLocalizedMessage());
			for(int i =0;i<x.getStackTrace().length;i++){
				System.out.println(x.getStackTrace()[i]);
			}
			throw new IOException("Error encoding game state as JSON", x);
			
		}
	}

	private void serializeBoard(ChineseCheckerBoard board, JSONWriter writer) {
		for(int y = 0;y<board.getFieldSize();y++) {
			for(int x=0;x<board.getFieldSize();x++) {
				writer.object();
				writer.key("x").value(x);
				writer.key("y").value(y);
				int val = 0;
				if(board.isPlayer1(x, y)) {
					val = 1;
				} else if(board.isPlayer2(x, y)) {
					val = -1;
				}
				writer.key("value").value(val);
				writer.endObject();
			}
		}
		
	}

	@Override
	public String getGameContentType() {
		return "application/json";
	}

	@Override
	public String encodeGameCompact(Game<?, ?> game) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportsCompactGameEncoding() {
		// TODO Auto-generated method stub
		return false;
	}

}
