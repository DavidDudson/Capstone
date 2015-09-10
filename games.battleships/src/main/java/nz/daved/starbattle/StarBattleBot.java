package nz.daved.starbattle;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.daved.starbattle.game.BotGameBoard;
import nz.daved.starbattle.game.Coordinate;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * Abstact class to get rid of the getID implementation
 */
public abstract class StarBattleBot implements Bot<BotGameBoard, Coordinate> {

    private String uid;

    public StarBattleBot(String botId) {
        this.uid = botId;
    }

    @Override
    public String getId() {
        return this.uid;
    }
}
