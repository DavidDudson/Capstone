package soGaCoGameTemplate;

import nz.ac.massey.cs.ig.core.game.Bot;

import java.util.List;

/**
 * Created by David J. Dudson on 4/08/15.
 *
 * Abstact class to get rid of the getID implementation
 */
public abstract class BattleshipBot implements Bot<List<Integer>,Integer>{

    private String uid = null;

    public BattleshipBot(String botId) {
        super();
        this.uid = botId;
    }

    @Override
    public String getId() {
        return this.uid;
    }
}
