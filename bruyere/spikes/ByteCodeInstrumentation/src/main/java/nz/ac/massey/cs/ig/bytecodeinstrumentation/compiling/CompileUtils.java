package nz.ac.massey.cs.ig.bytecodeinstrumentation.compiling;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import nz.ac.massey.cs.ig.bytecodeinstrumentation.Main;

public class CompileUtils {

	public static byte[] compileAndGetByteCode(String exampleClass) {
		URL url = Main.class.getClassLoader().getResource("examples/" + exampleClass + ".java");
		String src = null;
		try {
			URI sourceFile = url.toURI();
			src = new String(Files.readAllBytes(Paths.get(sourceFile)), Charset.defaultCharset());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		JavaFileObject ob = new SourceCodeFileObject(exampleClass, src);
		
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, null, null);
	    ClassFileManager fileManager =  new ClassFileManager(stdFileManager);
		
		boolean success = compiler.getTask(null, fileManager, null, Arrays.asList("-g"), null, Arrays.asList(ob)).call();
		
		if(!success) {
			throw new UnsupportedOperationException();
		}
		
		return fileManager.getByteCode(exampleClass);
	}

	
}
