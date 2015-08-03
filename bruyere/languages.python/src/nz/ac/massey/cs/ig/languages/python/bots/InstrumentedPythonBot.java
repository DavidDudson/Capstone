package nz.ac.massey.cs.ig.languages.python.bots;

import nz.ac.massey.cs.ig.core.game.instrumentation.InstrumentedBot;
import nz.ac.massey.cs.ig.core.game.instrumentation.Observer;
import nz.ac.massey.cs.ig.languages.python.PythonBuildArtefact;

/**
 * Extended {@link DefaultPythonBot} to support {@link InstrumentedBot}
 * 
 * @author Johannes Tandler
 *
 */
public class InstrumentedPythonBot extends DefaultPythonBot implements
		InstrumentedBot {
	/**
	 * used observer
	 */
	private Observer __observer;

	public InstrumentedPythonBot(PythonBuildArtefact artefact, String botId) {
		super(artefact, botId);
	}

	@Override
	public void __initialize(Observer observer) {
		this.__observer = observer;
		interpreter.set("__observer", observer);
	}

	@Override
	public Observer __getObserver() {
		return __observer;
	}

}