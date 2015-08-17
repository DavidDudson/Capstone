package test.nz.ac.massey.spikes.traceability.mockData;

import java.util.List;

import nz.ac.massey.cs.ig.games.primegame.PGBot;

public class InsturmentedPGBot1 extends PGBot {

    public InsturmentedPGBot1() {
        super(InsturmentedPGBot1.class.getName());
    }

	@Override
	public Integer nextMove(List<Integer> game) {
		nz.ac.massey.spikes.traceability.tracer.Tracer.increaseIterationCounter("pggame","1");
		nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("pggame","1",9, "game" ,game);
		nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("pggame","1",9, "this" ,this);
		assert game.size()>0;
		nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("pggame","1",10, "game" ,game);
		nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("pggame","1",10, "this" ,this);
		return game.get(0);
	}
}
