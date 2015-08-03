package nz.ac.massey.spikes.traceability.sourcecode.instrumenter;

import static java.util.Collections.singleton;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

import nz.ac.massey.spikes.traceability.sourcecode.instrumenter.analyser.CodeAnalyserProcessor;

/**
 * @author Li Sui
 * this class is for instrumenting source code
 */
public class Instrumenter{
	private final String traceStatement = "nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace(";
	private final String increaseIterationCounterStatement="nz.ac.massey.spikes.traceability.tracer.Tracer.increaseIterationCounter(";
	private String botId;
	private String gameId;
	private List<MethodInfo> methods;
	
	/**
	 * instrumentation
	 * @param source
	 * @param qualifiedClassName
	 * @param botId
	 * @return
	 * @throws Exception
	 */
	public String instrument(final String source,final String qualifiedClassName,final String botId,final String gameId) throws Exception{
		this.botId =botId;
		this.gameId=gameId;
		
	    JavaCompiler compiler =ToolProvider.getSystemJavaCompiler();
        JavaCompiler.CompilationTask task = null;
        try {
            task = compiler.getTask(null, null, null, null, null,
                    Arrays.asList(new InMemoryJavaFileObject(qualifiedClassName, source)));
        } catch (Exception e) {
        	//TODO: Handle excetions
            e.printStackTrace();
        }
        // Set the code analyser processor to the compiler task
        CodeAnalyserProcessor processor= new CodeAnalyserProcessor();
        methods = processor.getClassInfo().getMethods();
        task.setProcessors(singleton(processor));
        task.call();  

		return generateTraceStatement(source);
	}
	/**
	 * insert instrumentation results to source code
	 * @param source
	 * @return
	 */
	private String generateTraceStatement(final String source){
		String[] lines = source.toString().split("\n");
		StringBuilder sourceCodeAfterInstru =new StringBuilder();
		int counter=0;
		boolean flag = false;
		for(String l: lines){
			sourceCodeAfterInstru.append(l+"\n"); 
			counter ++;
			for(MethodInfo method:methods){
				for(VariableInfo vi : method.getVariablesList()){
					
					if(vi.getLineNumber() ==counter){
						if(!method.getName().equals("<init>")){ // ingore construtor
							/*
							 * hard coded iteration counter. only add to if the variable is the last variable in nextMove method
							 */
							if(method.getName().equals("nextMove") && !flag){
								sourceCodeAfterInstru.append(increaseIterationCounterStatement+"\""+gameId+"\","+"\""+botId+"\""+");\n");
								flag=true;
							}	
							/*
							 * append trace statement
							 */
							sourceCodeAfterInstru.append(traceStatement +"\""+gameId+"\","+"\""+botId+"\"," +vi.getLineNumber()+ ",\"" + vi.getName() + "\" ,"+ vi.getName()+");\n");
							sourceCodeAfterInstru.append(traceStatement +"\""+gameId+"\","+"\""+botId+"\"," +vi.getLineNumber()+ ",\"" + "this" + "\" ,"+ "this"+");\n");
						}
					}
				}
			}
		}
		return sourceCodeAfterInstru.toString();
	}
	
	public List<MethodInfo> getMethods(){
		return methods;
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