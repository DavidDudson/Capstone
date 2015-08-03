
package nz.ac.massey.cs.ig.games.mancala;

import nz.ac.massey.cs.ig.core.game.Bot;

/**
 * Mancala bot
 * @author jens dietrich
 */
public abstract class MancalaBot implements Bot<Mancala,Integer> {
    
    private String uid = null;
    
    public MancalaBot(String botId) {
        super();
        this.uid = botId;
    }
    
    @Override
    public String getId() {
        return this.uid;
    }

    
}
