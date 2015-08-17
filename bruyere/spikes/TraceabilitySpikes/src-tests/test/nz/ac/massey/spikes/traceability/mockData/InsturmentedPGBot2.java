package test.nz.ac.massey.spikes.traceability.mockData;

import java.util.List;

import nz.ac.massey.cs.ig.games.primegame.PGBot;

public class InsturmentedPGBot2 extends PGBot {

		    public InsturmentedPGBot2() {
		        super(InsturmentedPGBot2.class.getName());
		    }

			@Override
			public Integer nextMove(List<Integer> game) {
				nz.ac.massey.spikes.traceability.tracer.Tracer.increaseIterationCounter("pggame","2");
				nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("pggame","2",9, "game" ,game);
				nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("pggame","2",9, "this" ,this);
				assert game.size()>0;
				nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("pggame","2",10, "game" ,game);
				nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("pggame","2",10, "this" ,this);
				return game.get(game.size()-1);
			}
}
