package test.nz.ac.massey.spikes.traceability.instrumenter.compiler;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

/**
 * 
 * @author Li Sui
 *
 */
public class Compiler {
	
	private String errorMessage;
	
	public Class<?> compile(String classPath, String sourceCode){
		  JavaCompiler compiler =ToolProvider.getSystemJavaCompiler();
		  DynamicClassLoader cl = new DynamicClassLoader(ClassLoader.getSystemClassLoader());
	      try {
	          CompiledCode  compiledCode = new CompiledCode(classPath);
	          ExtendedStandardJavaFileManager fileManager = new ExtendedStandardJavaFileManager(
	              compiler.getStandardFileManager(null, null, null), compiledCode, cl);
	          MyDiagnosticListener diagnosticListener=new MyDiagnosticListener();
	          JavaCompiler.CompilationTask task = compiler.getTask(null,
	                  fileManager, diagnosticListener, null, null,
	                  Arrays.asList(new InMemoryJavaFileObject(classPath, sourceCode)));
	          if(task.call()){
	              errorMessage="no errors";
	              return cl.loadClass(classPath);

	          }else{
	              errorMessage =diagnosticListener.getDiagnosticReport();
	              return null;
	          }

	      } catch (Exception e) {
	          e.printStackTrace();
	      }
			return null;
	}
	
	public String getErrorMessage(){
		return errorMessage;
	}
	/**
	 * @author Li Sui
	 */
	public class InMemoryJavaFileObject extends SimpleJavaFileObject {
	    private String contents = null;

	    public InMemoryJavaFileObject(String className, String contents) throws Exception {
	        super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
	        this.contents = contents;
	    }

	    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
	        return contents;
	    }
	}


}
