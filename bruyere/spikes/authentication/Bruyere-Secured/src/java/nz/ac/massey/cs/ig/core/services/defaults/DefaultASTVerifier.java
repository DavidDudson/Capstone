package nz.ac.massey.cs.ig.core.services.defaults;

import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic.Kind;

import org.python.antlr.PythonTree;
import org.python.antlr.ast.Attribute;
import org.python.antlr.ast.Call;
import org.python.antlr.ast.Exec;
import org.python.antlr.ast.Name;
import org.python.antlr.ast.VisitorBase;
import org.python.antlr.base.mod;
import org.python.core.CompileMode;
import org.python.core.CompilerFlags;
import org.python.core.ParserFacade;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PySyntaxError;

import java.lang.reflect.Field;

import nz.ac.massey.cs.ig.core.services.BuildProblemCollector;
import nz.ac.massey.cs.ig.core.services.StaticASTVerifier;
import nz.ac.massey.cs.ig.core.services.VerifierException;

/**
 * This is a default AST verifier for Python based input
 * @author Isaac Udy
 */
public class DefaultASTVerifier implements StaticASTVerifier {
	
	// TODO: Log verification problems.
	@Override
	public void verify(String source, BuildProblemCollector dLog) throws VerifierException {
		PythonSecurityVisitor visitor = new PythonSecurityVisitor(source, dLog);
		boolean isSourceSafe = visitor.verifySaftey();
		
		if(isSourceSafe){
			return;
		}
		else{
			throw new VerifierException();
		}
	}

	// TODO: In case of failure, log an error message explaining why exactly the source was not safe
	private static class PythonSecurityVisitor extends VisitorBase<Void> {
		
		private mod module;
		private BuildProblemCollector dLog;
		
		private List<String> disallowed = Arrays.asList(new String[]{
				"__subclasses__",
				"__builtins__",
				"__builtin__",
				"open",
				"eval",
				"popen",
				"sys"
		});
		
		private boolean safe = true;
		
		public PythonSecurityVisitor(String source, BuildProblemCollector dLog){
			this.dLog = dLog;
			try{
				this.module = ParserFacade.parse(source, CompileMode.exec,"<string>", new CompilerFlags());
			}
			catch(PySyntaxError x){
				Field linenoField;
				try {
					linenoField = PySyntaxError.class.getDeclaredField("lineno");
					linenoField.setAccessible(true);
					int lineno = linenoField.getInt(x);
					dLog.report(new SimpleDiagnostics(Kind.ERROR, x.toString(), lineno));
				} 
				//None of these exceptions should ever be thrown. PySyntaxError ALWAYS has a lineno field, and x will always be an instance of this class.
				  catch (NoSuchFieldException e) {
				} catch (SecurityException e) {
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				}
			}
		}
		
		public boolean verifySaftey(){
			try{
				module.traverse(this);
				return safe;
			}
			catch(Exception e){
				// Couldn't complete verification, therefore we assume the source is unsafe.
				return false;
			}
		}
		
		/*
		 * ==================================================================================
		 * 							Visit Methods below here
		 * ==================================================================================
		 */

		/**
	     * Visit each of the children one by one.
	     * @args node The node whose children will be visited.
	     */
		@Override
	    public void traverse(PythonTree node) throws Exception {
	        node.traverse(this);
	    }

	    public Void visit(PythonTree[] nodes) throws Exception {
	        for (int i = 0; i < nodes.length; i++) {
	        	visit(nodes[i]);
	        }
	        return null;
	    }
	    
	    public Void visit(PythonTree node) throws Exception {
	    	node.accept(this);
	        return null;
	    }

	    protected Void unhandled_node(PythonTree node) throws Exception {
	    	// If we are not handling a certain type of node, we assume that node is safe no matter it's value.
	    	// This means doing nothing
	    	return null;
	    }

	    @Override
	    public Void visitCall(Call node) throws Exception{
	    	PyObject func = node.getFunc();
	    	
			if(func.getClass() == Attribute.class){
				org.python.antlr.ast.Attribute attr = (org.python.antlr.ast.Attribute) func;
				String funcName = attr.getAttr().asStringOrNull();
				if(funcName == null){
				}
				else{
					if(this.disallowed.contains(funcName)){
			    		int lineNo = node.__getattr__("lineno").asInt();
						dLog.report(new SimpleDiagnostics(Kind.ERROR, "You called a disallowed function '"+funcName+"'", lineNo));
						
						this.safe = false;
					}
				}
			}
			
			traverse(node);
			return null;
	    }
	    
	    @Override
	    public Void visitExec(Exec node){
    		int lineNo = node.__getattr__("lineno").asInt();
			dLog.report(new SimpleDiagnostics(Kind.ERROR, "You are not allowed to use the 'exec' command.", lineNo));
	    	this.safe = false;
	    	return null;
	    }
	    
	    @Override
	    public Void visitName(Name node) throws Exception{
	    	if(this.disallowed.contains(node.getInternalId())){
	    		int lineNo = node.__getattr__("lineno").asInt();
	    		String name = node.__getattr__("id").asString();
				dLog.report(new SimpleDiagnostics(Kind.ERROR, "You attempted to access either a variable or a function which is disallowed, '"+name+"'", lineNo));				
	    		this.safe = false;
			}
	    	traverse(node);
	    	return null;
	    }
		
	}

}
