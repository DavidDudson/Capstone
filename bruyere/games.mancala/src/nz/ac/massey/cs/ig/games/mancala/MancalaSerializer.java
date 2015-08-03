package nz.ac.massey.cs.ig.games.mancala;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONException;
import org.json.JSONStringer;

import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.core.services.Serializer;

/**
 * Encodes and decodes mancala games and moves.
 * @author jens dietrich
 */
public class MancalaSerializer implements Serializer {

	@Override
	public void encodeGame(Game<?, ?> g, PrintWriter out) throws IOException {
		assert g instanceof MancalaGame;
		
		MancalaGame game = (MancalaGame)g;
		
		List<MancalaGame.Move> moves = game.getMoves();
        JSONStringer json = new JSONStringer();
        
        
        try {
        	Throwable error = game.getError();
	        json.object().key("moves").array();
	        for (MancalaGame.Move move:moves) {
	        	json.object(); // start move
	        	
	        	// game state
	        	json.key("player1state").array();
	        	for (int i:move.player1Pits) json.value(i);
	        	json.endArray();
	        	
	        	json.key("player2state").array();
	        	for (int i:move.player2Pits) json.value(i);
	        	json.endArray();
	        	
	        	// changed
	        	json.key("player1changed").array();
	        	for (int i:move.player1PitsChanged) json.value(i);
	        	json.endArray();
	        	
	        	json.key("player2changed").array();
	        	for (int i:move.player2PitsChanged) json.value(i);
	        	json.endArray();

	        	// start
	        	json.key("playerStarted").value(move.player1StartedMove?1:2);
	        	json.key("startPit").value(move.startPit);
	        	json.key("stonesPlayed").value(move.stonesPlayed);
	        	
	        	json.endObject(); // end move
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
		return null;
	}

	@Override
	public boolean supportsCompactGameEncoding() {
		return false;
	}
    
}
