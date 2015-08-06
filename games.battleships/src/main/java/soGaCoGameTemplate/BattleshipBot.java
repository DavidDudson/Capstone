package soGaCoGameTemplate;

import nz.ac.massey.cs.ig.core.game.Bot;
import soGaCoGameTemplate.game.BotMap;
import soGaCoGameTemplate.game.Coordinate;
import soGaCoGameTemplate.game.GameBoard;

import java.util.List;

/**
 * Created by David J. Dudson on 4/08/15.
 *
 * Abstact class to get rid of the getID implementation
 */
public abstract class BattleshipBot implements Bot<BotMap,Coordinate>{

    private String uid = null;

    /**
     * Creates a bot
     * @param botId The uid of the bot
     */
    public BattleshipBot(String botId) {
        super();
        this.uid = botId;
    }

    /**
     * Get the uid
     * @return The uid
     */
    @Override
    public String getId() {
        return this.uid;
    }
}
