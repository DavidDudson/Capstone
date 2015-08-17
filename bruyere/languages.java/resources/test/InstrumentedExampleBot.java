package test;

import java.util.List;

import nz.ac.massey.cs.ig.core.game.instrumentation.InstrumentedBot;
import nz.ac.massey.cs.ig.core.game.instrumentation.Observer;
import nz.ac.massey.cs.ig.games.primegame.PGBot;

public class InstrumentedExampleBot extends PGBot implements InstrumentedBot {

	public Observer __observer;

	public InstrumentedExampleBot(String botId) {
		super(botId);
	}

	@Override
	public Integer nextMove(List<Integer> game) {
		__observer.invoke(1900000000, 0, "java/util/List", "size", null);
		__observer.invoke(19, 4, "Integer", "<init>", "(I)V");
		Integer index = new Integer(game.size() - 1);

		__observer.invoke(21, 0, "List", "get", "(I)Ljava/lang/Object;");
		return game.get(index);
	}

	private class Test {
		public int hallo() {
			InstrumentedExampleBot.this.__observer.invoke(31, 0, "Integer",
					"<init>", "(I)V");
			return new Integer(-1);
		}
	}

	/*
	 * ----------------------------------------------
	 * 
	 * GENERATED METHODS FOR INSTRUMENTED BOTS INTERFACE
	 * 
	 * ----------------------------------------------
	 */
	@Override
	public void __initialize(Observer observer) {
		if (__observer == null) {
			this.__observer = observer;
			this.__observer.setObservable(this);
		} else {
			throw new UnsupportedOperationException(); // We shouldn't switch
														// runtime observer
		}
	}

	@Override
	public Observer __getObserver() {
		return __observer;
	}

}
