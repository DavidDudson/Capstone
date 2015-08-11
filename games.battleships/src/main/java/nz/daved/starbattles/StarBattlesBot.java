package nz.daved.starbattles;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.daved.starbattles.game.BotGameBoard;
import nz.daved.starbattles.game.Coordinate;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * Abstact class to get rid of the getID implementation
 */
public abstract class StarBattlesBot implements Bot<BotGameBoard, Coordinate> {

    private String uid = null;

    /**
     * Creates a bot
     *
     * @param botId The uid of the bot
     */
    public StarBattlesBot(String botId) {
        super();
        this.uid = botId;
    }

    /**
     * Get the uid
     *
     * @return The uid
     */
    @Override
    public String getId() {
        return this.uid;
    }
}
