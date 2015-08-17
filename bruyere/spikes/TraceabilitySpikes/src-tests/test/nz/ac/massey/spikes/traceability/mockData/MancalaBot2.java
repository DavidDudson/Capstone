package test.nz.ac.massey.spikes.traceability.mockData;

import nz.ac.massey.cs.ig.games.mancala.Mancala;
import nz.ac.massey.cs.ig.games.mancala.MancalaBot;

/**
 * Built-in bot.
 * @author jens dietrich
 */
public class MancalaBot2 extends MancalaBot {

    public MancalaBot2(String botId) {
        super(botId);
    }

	@Override
	public Integer nextMove(Mancala board) {
		
		for (int i=0;i<6;i++) {
			if (board.getStonesInMyPit(i)>0) {
				return i;
			}
		}
		return 0;
	}
}
