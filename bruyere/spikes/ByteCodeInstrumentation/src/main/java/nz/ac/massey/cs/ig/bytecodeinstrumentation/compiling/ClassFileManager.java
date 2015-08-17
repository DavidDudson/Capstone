package nz.ac.massey.cs.ig.bytecodeinstrumentation.compiling;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

public class ClassFileManager extends
		ForwardingJavaFileManager<StandardJavaFileManager> {
	/**
	 * Instance of ByteCodeFileObject that will store the compiled bytecode of
	 * our class from http://www.javablogging.com/dynamic-in-memory-compilation/
	 */
	private Map<String, JavaClassObject> clazzes;

	public ClassFileManager(StandardJavaFileManager standardManager) {
		super(standardManager);
		clazzes = new HashMap<String, JavaClassObject>();
	}

	@Override
	public ClassLoader getClassLoader(Location location) {
		return null;
	}

	@Override
	public JavaFileObject getJavaFileForOutput(Location location,
			String className, Kind kind, FileObject sibling) throws IOException {
		if (!clazzes.containsKey(className)) {
			clazzes.put(className, new JavaClassObject(className, kind));
		}

		return clazzes.get(className);
	}

	public byte[] getByteCode() {
		throw new UnsupportedOperationException();
	}

	public byte[] getByteCode(String className) {
		if(className.contains("/")) {
			className = className.substring(className.indexOf("/") + 1);
		}
		if (clazzes.containsKey(className)) {
			return clazzes.get(className).getBytes();
		}
		return null;
	}
}