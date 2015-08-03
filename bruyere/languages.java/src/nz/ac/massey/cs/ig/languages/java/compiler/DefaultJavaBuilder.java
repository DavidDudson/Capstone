package nz.ac.massey.cs.ig.languages.java.compiler;

import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.ProgrammingLanguageSupport;
import nz.ac.massey.cs.ig.core.services.build.BotFactory;
import nz.ac.massey.cs.ig.core.services.build.BuilderException;
import nz.ac.massey.cs.ig.core.services.defaults.DefaultBuilder;
import nz.ac.massey.cs.ig.languages.java.instrumentation.AddInstrumentationAdapter;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * Simple compiler that wraps the compiler in javax.tools. Requires the JDK (not
 * just the JRE).
 * 
 * @author jens dietrich
 */
public class DefaultJavaBuilder extends DefaultBuilder {

	private String fullClassName = null;

	public DefaultJavaBuilder(ProgrammingLanguageSupport languageSupport,
			Logger logger) {
		super(languageSupport, logger);
		// activate instrumentation per default
		this.setInstrumenationSupport(true);
	}

	private final static List<String> COMPILER_OPTIONS = Arrays.asList(
			"-classpath", CompilationClassPathUtil.getCompilationClassPath());

	@Override
	protected Object compile(BotData data, ProgrammingLanguageSupport support) throws BuilderException {
		String src = data.getSrc();

		fullClassName = new SourceUtils().extractFullClassName(src);
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		final ClassFileManager fileManager = new ClassFileManager(
				compiler.getStandardFileManager(null, null, null));

		List<JavaFileObject> jfiles = new ArrayList<JavaFileObject>();
		jfiles.add(new SourceCodeFileObject(fullClassName, src));
		compiler.getTask(null, fileManager, dLog, COMPILER_OPTIONS, null,
				jfiles).call();
		logVerifier(data.getId(), "<built-in java compiler>", dLog, logger);

		return fileManager.getByteCodes();
	}

	@Override
	protected Object instrumentByteCode(BotData data, Object buildArtefact,
			ProgrammingLanguageSupport support) {
		@SuppressWarnings("unchecked")
		Map<String, byte[]> tmpByteCodes = (Map<String, byte[]>) buildArtefact;

		Map<String, byte[]> instrumentedClasses = new HashMap<String, byte[]>();
		for (String clazz : tmpByteCodes.keySet()) {
			byte[] tmpByteCode = tmpByteCodes.get(clazz);

			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			ClassVisitor adapter = new AddInstrumentationAdapter(cw, fullClassName);
			
			ClassReader cr = new ClassReader(tmpByteCode);
			cr.accept(adapter, 0);

			byte[] instrumented = cw.toByteArray();
			instrumentedClasses.put(clazz, instrumented);
		}

		return instrumentedClasses;
	}

	@Override
	protected BotFactory createBotFactory(BotData data, Object buildArtefact)
			throws BuilderException {
		@SuppressWarnings("unchecked")
		Map<String, byte[]> byteCodes = (Map<String, byte[]>) buildArtefact;

		String fullName = new SourceUtils().extractFullClassName(data.getSrc());

		SecureClassLoader classLoader = new CustomSecureClassLoader(byteCodes);

		Class<?> clazz;
		try {
			clazz = classLoader.loadClass(fullName);
		} catch (ClassNotFoundException e) {
			throw new BuilderException("Exception loading bot class for bot "
					+ data.getId(), e);
		}

		return new JavaBotFactory(data.getId(), clazz);
	}

	private class CustomSecureClassLoader extends SecureClassLoader {

		public CustomSecureClassLoader(Map<String, byte[]> additionalClasses) {
			super(DefaultBuilder.class.getClassLoader());

			for (String clazz : additionalClasses.keySet()) {
				byte[] bytes = additionalClasses.get(clazz);
				defineClass(clazz, bytes, 0, bytes.length);
			}
		}
	}

	@Override
	public boolean isInstrumentationSupported() {
		return true;
	}
}
