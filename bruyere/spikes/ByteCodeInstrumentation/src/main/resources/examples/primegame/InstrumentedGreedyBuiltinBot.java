import java.util.List;

import nz.ac.massey.cs.ig.bytecodeinstrumentation.instrumentation.Instrumentable;
import nz.ac.massey.cs.ig.bytecodeinstrumentation.instrumentation.Observer;
import nz.ac.massey.cs.ig.games.primegame.PGBot;

public class InstrumentedGreedyBuiltinBot extends PGBot implements Instrumentable {

	ThreadLocal<Observer> observerLocal = new ThreadLocal<Observer>();

    public InstrumentedGreedyBuiltinBot(String botId) {
        super(botId);
    }

	@Override
	public Integer nextMove(List<Integer> game) {
		observerLocal.get().invoke(0, "List", "size", "()L");
		observerLocal.get().invoke(0, "List", "get", "(L)L");
		return game.get(game.size()-1);
	}
	
	
	

	/* 
	 * ----------------------------------------------
	 * 
	 * GENERATED METHODS FOR INSTRUMENTABLE INTERFACE
	 * 
	 * ----------------------------------------------
	 */
	
	public void _initialize() {
		if(observerLocal == null) {
			observerLocal = new ThreadLocal<Observer>();
		}
		if(observerLocal.get() == null) {
			observerLocal.set(new Observer(this));
		}
	}
	
	public void _close() {
		if(observerLocal != null) {
			observerLocal.remove();
		}
	}


}
