package test.nz.ac.massey.spikes.traceability.mockData;

import nz.ac.massey.cs.ig.games.mancala.Mancala;
import nz.ac.massey.cs.ig.games.mancala.MancalaBot;

/**
 * Built-in bot.
 * @author jens dietrich
 */
public class InstrumentedMancalaBot2 extends MancalaBot {

    public InstrumentedMancalaBot2(String botId) {
        super(botId);
    }

	@Override
	public Integer nextMove(Mancala board) {
		nz.ac.massey.spikes.traceability.tracer.Tracer.increaseIterationCounter("mancala2","mancala2");
		nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala2",17, "board" ,board);
		nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala2",7, "this" ,this);
		for (int i=0;i<6;i++) {
			if (board.getStonesInMyPit(i)>0){
				nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala2",21, "board" ,board);
				nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala2",21, "this" ,this);
				return i;
			}
		}
		return 0;
	}
}
