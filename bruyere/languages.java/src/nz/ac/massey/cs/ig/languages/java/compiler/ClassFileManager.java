package nz.ac.massey.cs.ig.languages.java.compiler;

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
	private Map<String, ByteCodeFileObject> clazzes;

	public ClassFileManager(StandardJavaFileManager standardManager) {
		super(standardManager);
		clazzes = new HashMap<String, ByteCodeFileObject>();
	}

	@Override
	public ClassLoader getClassLoader(Location location) {
		return null;
	}

	@Override
	public JavaFileObject getJavaFileForOutput(Location location,
			String className, Kind kind, FileObject sibling) throws IOException {
		if (!clazzes.containsKey(className)) {
			clazzes.put(className, new ByteCodeFileObject(className, kind));
		}

		return clazzes.get(className);
	}

	public byte[] getByteCode() {
		throw new UnsupportedOperationException();
	}

	public byte[] getByteCode(String className) {
		if (clazzes.containsKey(className)) {
			return clazzes.get(className).getBytes();
		}
		return null;
	}
	
	public Map<String, byte[]> getByteCodes() {
		Map<String, byte[]> byteCodes = new HashMap<String, byte[]>();
		for(String clazz : clazzes.keySet()) {
			byteCodes.put(clazz, clazzes.get(clazz).getBytes());
		}
		return byteCodes;
	}
}
