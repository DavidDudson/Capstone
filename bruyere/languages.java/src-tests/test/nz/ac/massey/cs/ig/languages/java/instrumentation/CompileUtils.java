package test.nz.ac.massey.cs.ig.languages.java.instrumentation;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureClassLoader;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import nz.ac.massey.cs.ig.languages.java.compiler.ClassFileManager;
import nz.ac.massey.cs.ig.languages.java.compiler.SourceCodeFileObject;


public class CompileUtils {

	public static byte[] compileAndGetByteCode(String exampleClass, String className) {
		URL url = CompileUtils.class.getClassLoader().getResource("resources/" + exampleClass + ".java");
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
		return fileManager.getByteCode(className);
	}
	
	public static Class<?> getClassloader(String name, final byte[] bytes) {
		SecureClassLoader classLoader = new SecureClassLoader(
				CompileUtils.class.getClassLoader()) {
			@Override
			protected Class<?> findClass(String name)
					throws ClassNotFoundException {
				byte[] b = bytes;
				return super.defineClass(name, b, 0, b.length);
			}
		};
		try {
			return classLoader.loadClass(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


	
}
