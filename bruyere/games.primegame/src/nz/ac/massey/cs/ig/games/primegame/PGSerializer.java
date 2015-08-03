package nz.ac.massey.cs.ig.games.primegame;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;

import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.core.services.Serializer;
import nz.ac.massey.cs.ig.games.primegame.PGGame.Move;


/**
 * Encodes and decodes PG games and moves.
 * @author jens dietrich
 */
public class PGSerializer implements Serializer {

	@Override
	public void encodeGame(Game<?, ?> g, PrintWriter out) throws IOException {
		assert g instanceof PGGame;
		
		PGGame game = (PGGame)g;
		
		List<Move> moves = game.getMoves();
        JSONStringer json = new JSONStringer();
        
        try {
        	Throwable error = game.getError();
	        json.object().key("moves").array();
	        
	        for (Move move:moves) {
	        	json.object();
	        	json.key("player1.played").value(move.getPlayer1ScorePlayed());
	        	json.key("player2.played").value(move.getPlayer2ScorePlayed());
	        	json.key("player1.forced").array();
	        		for (Integer v:move.getPlayer1ScoreForced()) {
	        			json.value(v);
	        		}
	        		json.endArray();
		        json.key("player2.forced").array();
	        		for (Integer v:move.getPlayer2ScoreForced()) {
	        			json.value(v);
	        		}
	        		json.endArray();
	        	json.endObject();
	        }
	
	        json.endArray();
	        
	        if (error!=null) {
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
        }
        catch (JSONException x) {
        	throw new IOException("Error encoding game state as JSON",x);
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
