
package nz.ac.massey.cs.ig.games.primegame;

import java.util.List;
import nz.ac.massey.cs.ig.core.game.Bot;

/**
 * PG bot
 * @author jens dietrich
 */
public abstract class PGBot implements Bot<List<Integer>,Integer> {
    
    private String uid = null;
    
    public PGBot(String botId) {
        super();
        this.uid = botId;
    }
    
    @Override
    public String getId() {
        return this.uid;
    }

    
}
