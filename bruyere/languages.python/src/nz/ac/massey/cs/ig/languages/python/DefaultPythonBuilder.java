package nz.ac.massey.cs.ig.languages.python;

import java.io.ByteArrayInputStream;

import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.ProgrammingLanguageSupport;
import nz.ac.massey.cs.ig.core.services.build.BotFactory;
import nz.ac.massey.cs.ig.core.services.build.BuilderException;
import nz.ac.massey.cs.ig.core.services.defaults.DefaultBuilder;
import nz.ac.massey.cs.ig.core.utils.ResourceUtils;

import org.apache.logging.log4j.Logger;
import org.python.antlr.base.mod;
import org.python.core.CompileMode;
import org.python.core.CompilerFacade;
import org.python.core.ParserFacade;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

/**
 * A default builder for Python code
 * 
 * @author Isaac Udy
 * @author Johannes Tandler
 * @param <B>
 */
public class DefaultPythonBuilder extends DefaultBuilder {

	public DefaultPythonBuilder(ProgrammingLanguageSupport languageSupport,
			Logger logger) {
		super(languageSupport, logger);
		this.setInstrumenationSupport(true);
	}

	@Override
	protected Object compile(BotData data, ProgrammingLanguageSupport support)
			throws BuilderException {
		mod node = ParserFacade.parse(new ByteArrayInputStream(data.getSrc().getBytes()), CompileMode.exec, "", Py.getCompilerFlags());
		
		// Python internal script compilation
		PyCode code = CompilerFacade.compile(node, "Bot" + data.getId(), "", true, false, Py.getCompilerFlags());
		PythonInterpreter interpreter = new PythonInterpreter();
		interpreter.exec(code);
		PyObject method = interpreter.get("nextMove");
		interpreter.close();
		
		if(method != null && method instanceof PyFunction) {
			int args = method.__getattr__("func_code")
					.__getattr__("co_argcount").asInt();
			if(args != 1) {
				throw new BuilderException("nextMove method must have one parameter");
			}
		} else {
			throw new BuilderException("No 'nextMove' method found");
		}
		
		
		// create execution script
		String execScript = "__result = nextMove(game)";
		
		mod execAst = ParserFacade.parse(new ByteArrayInputStream(execScript.getBytes()), CompileMode.exec, "", Py.getCompilerFlags());
		
		// Python internal script compilation
		PyCode executor = CompilerFacade.compile(execAst, "BotExec" + data.getId(), "", true, false, Py.getCompilerFlags());

		return new PythonBuildArtefact(code, executor);
	}

	@Override
	protected Object instrumentByteCode(BotData data, Object buildArtefact,
			ProgrammingLanguageSupport support) {
		// create new execution script which invokes debugger
		String execScript = ResourceUtils.loadFromClassPath(this, "Debugger.py") + "\n\ntdb = Tdb()\n" +  "__result = tdb.runcall(nextMove, game)\n";
		
		mod nodeExec = ParserFacade.parse(new ByteArrayInputStream(execScript.getBytes()), CompileMode.exec, "", Py.getCompilerFlags());
		PyCode executor = CompilerFacade.compile(nodeExec, "BotExec" + data.getId(), "", true, false, Py.getCompilerFlags());
		
		// create PythonBuildArtefact
		PythonBuildArtefact artefact = new PythonBuildArtefact(((PythonBuildArtefact)buildArtefact).getSrc(),executor);
		artefact.setInstrumented(true);
		return artefact;
	}

	@Override
	protected BotFactory createBotFactory(BotData data, Object buildArtefact)
			throws BuilderException {
		return new PythonBotFactory(data.getId(),
				(PythonBuildArtefact) buildArtefact);
	}

	@Override
	public boolean isInstrumentationSupported() {
		return true;
	}
}
