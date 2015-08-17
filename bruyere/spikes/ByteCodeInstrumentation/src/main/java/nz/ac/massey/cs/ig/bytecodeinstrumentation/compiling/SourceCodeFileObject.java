package nz.ac.massey.cs.ig.bytecodeinstrumentation.compiling;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class SourceCodeFileObject extends SimpleJavaFileObject {

	private String src;

	// check whether we can use the botid here, or whether this has to be the
	// actual class name
	public SourceCodeFileObject(String className, String src) {
		super(URI.create("string:///" + className.replace('.', '/')
				+ Kind.SOURCE.extension), Kind.SOURCE);
		this.src = src;
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return src;
	}

}
