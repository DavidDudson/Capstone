package nz.ac.massey.cs.ig.core.services.defaults.compiler;

import java.io.File;

/**
 * Utility to infer the classpath to be used by the compiler.
 * This will find out whether the code is running in a web app, or within Eclipse.
 * Otherwise, this will fail !
 * @author jens dietrich
 */
public class CompilationClassPathUtil {
	public static String getCompilationClassPath() {
		// try to figure out whether this is a web application
		String path = CompilationClassPathUtil.class.getResource("").getPath();
		if (path.contains("WEB-INF/lib")) {
			System.out.println("running in a web container");
			System.out.println(path);
			
			// fine-tuning path
			if (path.startsWith("file:")) {
				path = path.substring(5);
			}
			int p = path.indexOf("WEB-INF/lib");
			path = path.substring(0, p+"WEB-INF/lib".length());
			System.out.println("fixed path: " + path);
			// build path from libs
			StringBuilder b = new StringBuilder();
			String SEP = System.getProperty("path.separator");
			for (File f:new File(path).listFiles()) {
				String n = f.getAbsolutePath();
				if (n.endsWith(".jar")) {	
					if (b.length()>0) b.append(SEP);
					b.append(n);
				}
			}
			String pth = b.toString();
			System.out.println("class path: " + pth);
			return pth;

		}
		
		// otherwise, if this is an eclipse project, use defaults
		if (new File("./.project").exists()) {
			System.out.println("running inside eclipse");
			return "bin";
		}
		
		System.out.println("Cannot infer out compilation classpath, currently only eclipse and web apps are supported");
		return "";
	}
}
