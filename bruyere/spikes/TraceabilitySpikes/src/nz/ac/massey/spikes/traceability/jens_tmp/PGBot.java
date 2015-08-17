package nz.ac.massey.spikes.traceability.jens_tmp;

import java.util.List;

import nz.ac.massey.cs.ig.core.game.Bot;

public interface PGBot extends Bot<List<Integer>,Integer>{
	Integer move(List<Integer>  game);
}
