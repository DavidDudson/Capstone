
package nz.ac.massey.cs.ig.core.services;

import java.io.IOException;
import java.io.PrintWriter;
import nz.ac.massey.cs.ig.core.game.Game;

/**
 * Interface to describe services related to the encoding/decoding of games.
 * The purpose if communication with network clients.
 * @author jens dietrich
 */
public interface Serializer {
    
    /**
     * Encode the entire history of a game that has been played.
     * Use case: sent game state back to the client.
     * @param game
     * @param out
     * @throws IOException 
     */
    void encodeGame(Game<?, ?> game,PrintWriter out) throws IOException;
    
    
    /**
     * Get the content type indicating how the game history has been encoded,
     * e.g. application/json
     * @return 
     */
    String getGameContentType();
    
    /**
     * For simple games it might be possible to encode the entire game history
     * in one string to be used in a URL.
     * @param game 
     * @param out
     * @throws IOException 
     */
    String encodeGameCompact(Game<?, ?> game);
    
    /**
     * Whether history in URL encoding is supported
     * @return 
     */
    boolean supportsCompactGameEncoding();


}
