package nz.ac.massey.spikes.traceability.bytecode.instrumenter;

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
		// TODO clean up

		// System.out.println(location);
		// ClassLoader parent;
		// try {
		// parent = new URLClassLoader(
		// new URL[] {
		// new
		// File("/Users/jbdietri/development/bruyere/services/WebContent/WEB-INF/lib/ge.jar").toURI().toURL(),
		// new
		// File("/Users/jbdietri/development/bruyere/services/WebContent/WEB-INF/lib/ge-examples.jar").toURI().toURL()
		// }
		// );
		// return parent;
		// } catch (MalformedURLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// return null;
		// }

		// ClassLoader classLoader = new
		// SecureClassLoader(ClassFileManager.this.getClass().getClassLoader())
		// {
		// // return new SecureClassLoader() {
		// @Override
		// protected Class<?> findClass(String name) throws
		// ClassNotFoundException {
		// byte[] b = jclassObject.getBytes();
		// return super.defineClass(name, jclassObject.getBytes(), 0, b.length);
		// }
		// };
		// System.out.println("Requested classloader " + classLoader);
		// System.out.println("Parent classloader is " +
		// classLoader.getParent());
		// return ClassFileManager.this.getClass().getClassLoader();
		// return Thread.currentThread().getContextClassLoader();
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
}
