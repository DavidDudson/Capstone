package nz.ac.massey.cs.ig.languages.python.bots;

import java.io.Closeable;
import java.io.IOException;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.languages.python.PythonBuildArtefact;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

/**
 * Default implementation of an PythonBot
 * @author Jake
 *
 */
public class DefaultPythonBot implements Bot<Object, Object>, Closeable {
	
	/**
	 * bot id
	 */
	private String botId;
	
	protected PythonInterpreter interpreter;
	
	private PythonBuildArtefact artefact;
	
	/**
	 * Default constructor
	 * @param artefact
	 * @param botId
	 */
	public DefaultPythonBot(PythonBuildArtefact artefact, String botId) {
		this.botId = botId;
		this.artefact = artefact;
		
		interpreter = new PythonInterpreter();
		interpreter.exec(artefact.getSrc());
	}

	@Override
	public String getId() {
		return botId;
	}

	@Override
	public Object nextMove(Object game) {
		// set game variable
		interpreter.set("game", game);
		interpreter.exec(artefact.getExecScript());
		// pull result
		PyObject ob = interpreter.get("__result");
		return ob.__tojava__(Object.class);
	}

	@Override
	public void close() throws IOException {
		interpreter.close();
	}
}
