package nz.ac.massey.cs.ig.core.services.defaults;

import java.util.List;

import javax.tools.Diagnostic.Kind;

import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.services.BuildProblemCollector;
import nz.ac.massey.cs.ig.core.services.Builder;
import nz.ac.massey.cs.ig.core.services.BuilderException;
import nz.ac.massey.cs.ig.core.services.DynamicVerifier;
import nz.ac.massey.cs.ig.core.services.StaticASTVerifier;
import nz.ac.massey.cs.ig.core.services.StaticByteCodeVerifier;

/**
 * A default builder for Python code
 * 
 * @author Isaac Udy
 * @param <B>
 */
public class DefaultPythonBuilder<B extends Bot<?, ?>> implements Builder<B> {
	
	@Override
	public B build(final String botId, String src, StaticASTVerifier astverifier,StaticByteCodeVerifier sverifier, DynamicVerifier<B> dverifier, BuildProblemCollector dLog) throws BuilderException {
        try {
        	astverifier.verify(src,dLog);

        	final PythonInterpreter python = new PythonInterpreter();		
    		python.exec(src);
    		
    		// Create a PyObject, and turn it into an instance of the Bot class.
    		final PyObject nextMove = python.get("nextMove"); 
    		if(nextMove == null){
				dLog.report(new SimpleDiagnostics(Kind.ERROR, "The builder could not find 'nextMove'.", 0));
    			throw new BuilderException();
    		}
    		else{
    			try{
    				int args = nextMove.__getattr__("func_code").__getattr__("co_argcount").asInt();
    				// func_code provides information about the function.
    				// co_argcount says how many arguments the function takes. 
    				// nextMove takes a single argument in the Java class, therefore we need to throw an exception if the provided python function takes more or less arguments.
    				if(args != 1){
    					dLog.report(new SimpleDiagnostics(Kind.ERROR, "The function 'nextMove' is supposed to take one parameter. Your nextMove function takes "+args+" parameters.", 0));
        				throw new BuilderException();
    				}
    			}
    			catch(PyException pye){
    				// This exception will be thrown if the func_code attribute cannot be read. This would mean the 'nextMove' object is not a function.
					dLog.report(new SimpleDiagnostics(Kind.ERROR, "There was no function with the name 'nextMove' found by the compiler. Please make sure nextMove is a function, and if it is, check your spelling.", 0));
    				throw new BuilderException();
    			}
    		}
    	
    		Bot bot = new Bot(){
    			private String uid = botId;
    			
				@Override
				public String getId() {
					return uid;
				}

				@Override
				public Object nextMove(Object game) {
					return nextMove._jcall(new Object[]{game}).__tojava__(Object.class);
				}
    		};
    		
            return (B) bot;
        } 
        // maintain src code level 6 (no multi catch)
        // VerifierExceptions are throws as is
        catch (RuntimeException x ) {
            throw new BuilderException("Exception instantiating bot class for bot " + botId,x);
        } 
	}
	
}
