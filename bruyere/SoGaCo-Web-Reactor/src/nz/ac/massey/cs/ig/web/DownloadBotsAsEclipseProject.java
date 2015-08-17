package nz.ac.massey.cs.ig.web;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.massey.cs.ig.api.Settings;
import nz.ac.massey.cs.ig.api.servlets.BasicBruyereServlet;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.Services;
import nz.ac.massey.cs.ig.core.utils.BotMetadataSerializer;

import com.google.common.io.Files;

/**
 * Downloads all bots as an Eclipse project for marking.
 * 
 * @author jens dietrich
 */
@WebServlet("/eclipse")
public class DownloadBotsAsEclipseProject extends BasicBruyereServlet {

	private static final long serialVersionUID = 6095548947350704594L;
	public static final String PROJECT_NAME = "_bots";
	public static final String BUILTIN_BOTS_USER = Settings.BUILTIN_BOTS_PSEUDO_USER;

	public DownloadBotsAsEclipseProject() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Services services = getServices();
		EntityManager db = services.createEntityManager();

		Collection<BotData> datas = null;
		Collection<String> generatedTestClasses = new ArrayList<String>();
		Collection<String> builtinBotClassNames = new TreeSet<String>();

		datas = db.createQuery("select a from BotData a", BotData.class)
				.getResultList();
		String packageName = toPackageName(BUILTIN_BOTS_USER);

		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "inline; filename=_bots.zip;");
		ServletOutputStream out = response.getOutputStream();
		ZipOutputStream zip = new ZipOutputStream(out);
		PrintStream pout = new PrintStream(zip);

		// write bots, metadata wil be added to source files,
		// also include buildtin bots
		String projectFolder = PROJECT_NAME + "/";
		for (BotData data : datas) {
			builtinBotClassNames.add(packageName + "."
					+ data.getqName());

			Properties metaData = BotMetadataSerializer.serialize(data);
			String src = data.getSrc();
			packageName = toPackageName(data.getId());

			// sources
			String scrFolder = projectFolder + "src/" + packageName + '/';
			String className = metaData.getProperty("qname");
			zip.putNextEntry(new ZipEntry(scrFolder + className + ".java"));
			// append package name - assuming students did not use it
			pout.println("// the package declaration has been inserted by EclipseProjectConverter");
			pout.print("package ");
			pout.print(packageName);
			pout.println(';');
			pout.println();

			pout.println("// bot meta data has been inserted by EclipseProjectConverter");
			pout.println("/*");
			for (Object key : metaData.keySet()) {
				String value = metaData.getProperty((String) key);
				pout.print(" * ");
				pout.print(key);
				pout.print(" : ");
				pout.print(value);
				pout.println();
			}
			pout.println("*/");

			pout.println();
			pout.println("// user code follows");
			pout.println();

			pout.println(src);

			// generate test cases
			String testClassName = "TEST_" + className;
			zip.putNextEntry(new ZipEntry(scrFolder + testClassName + ".java"));

			pout.println(generateAcceptanceTests(packageName, testClassName,
					className, builtinBotClassNames));
			generatedTestClasses.add(packageName + "." + testClassName);
		}

		// generate test suite (in root package)
		String scrFolder = projectFolder + "src/";
		zip.putNextEntry(new ZipEntry(scrFolder + "TestSuite.java"));
		pout.println(generateAcceptanceTestSuite(generatedTestClasses));

		// include libs
		String libPath = this.getServletContext().getRealPath("/WEB-INF/lib/");
		List<String> classpath = new ArrayList<String>();
		for (File jar : new File(libPath).listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		})) {

			if (includeLib(jar)) {
				zip.putNextEntry(new ZipEntry(projectFolder + "lib/"
						+ jar.getName()));
				Files.copy(jar, zip);
				classpath.add(jar.getName());
			}
		}

		// include (Eclipse) project meta-data
		zip.putNextEntry(new ZipEntry(projectFolder + ".project"));
		pout.println(generateEclipseDotProjectFile(PROJECT_NAME));
		zip.putNextEntry(new ZipEntry(projectFolder + ".classpath"));
		pout.println(generateEclipseDotClasspathFile(classpath));

		zip.flush();
		zip.close();
		out.flush();
	}

	private String generateAcceptanceTestSuite(
			Collection<String> generatedTestClasses) {
		StringBuilder b = new StringBuilder();

		b.append("// generated test suite, will test all bots\n").append("\n")
				.append("import org.junit.runners.Suite;\n")
				.append("import org.junit.runner.RunWith;\n").append("\n")
				.append("@RunWith(Suite.class)\n")
				.append("@Suite.SuiteClasses({\n");

		for (String className : generatedTestClasses) {
			b.append("	" + className + ".class,\n");
		}

		b.append("})\n").append("public class TestSuite {}\n");
		return b.toString();
	}

	private String generateAcceptanceTests(String packageName,
			String testClassName, String className,
			Collection<String> builtinBotClassNames) {
		StringBuilder b = new StringBuilder();
		b.append("package " + packageName + ";\n")
				.append("\n")
				.append("// generated test case to run bot against built-in bots")
				.append("\n")
				.append("import nz.ac.massey.cs.ig.core.game.GameState;\n")
				.append("import nz.ac.massey.cs.ig.examples.primegame.PGGame;\n")
				.append("import nz.ac.massey.cs.ig.tasks.BotAgainstBotPlay;\n")
				.append("import org.junit.Test;\n")
				.append("import static org.junit.Assert.*;\n").append("\n")
				.append("public class " + testClassName + "{\n");

		for (String builtinBotClass : builtinBotClassNames) {
			b.append(generateAcceptanceTest("test_" + className + "_against_"
					+ builtinBotClass.replace(".", "_") + "_1", "game-1",
					"builtin", "player", className, builtinBotClass, true));
			b.append(generateAcceptanceTest("test_" + className + "_against_"
					+ builtinBotClass.replace(".", "_") + "_2", "game-2",
					"player", "builtin", builtinBotClass, className, false));
		}
		b.append("}");

		return b.toString();
	}

	private String generateAcceptanceTest(String methodName, String gameId,
			String player1, String player2, String player1Class,
			String player2Class, boolean firstPlayerExpectedToWin) {
		return new StringBuilder()
				.append("	// generated acceptance test case\n")
				.append("	@Test\n")
				.append("	public void " + methodName
						+ "() throws Exception {\n")
				.append("		PGGame game = new PGGame(\"" + gameId + "\",\""
						+ player1 + "\",\"" + player2 + "\");\n")
				.append("		BotAgainstBotPlay play = new BotAgainstBotPlay(game,new "
						+ player1Class
						+ "(\""
						+ player1
						+ "\"),new "
						+ player2Class + "(\"" + player2 + "\"));\n")
				.append("		play.call();\n")
				.append("		System.out.println(game.getState());\n")
				.append("		// log game \n")
				.append("		int c = 0; \n")
				.append("		for (PGGame.Move move:game.getMoves()) { \n")
				.append("			System.out.println(\" \" + ++c + \"\t\" + move); \n")
				.append("		}\n")
				.append("\n")
				.append("		assertEquals(game.getState(),"
						+ (firstPlayerExpectedToWin ? "GameState.PLAYER_1_WON"
								: "GameState.PLAYER_2_WON") + ");\n")
				.append("	}\n\n").toString();
	}

	// whether to include a jar file
	private boolean includeLib(File jar) {
		return jar.getName().startsWith("ge-");
	}

	// convert a user name to a valid package name
	private String toPackageName(String userId) {
		char c = userId.charAt(0);
		return Character.isAlphabetic(c) ? userId : "p" + userId;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	private String generateEclipseDotProjectFile(String projectName) {
		return new StringBuilder()
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<projectDescription>\n")
				.append("	<name>" + projectName + "</name>\n")
				.append("	<comment></comment>\n")
				.append("	<projects></projects>\n")
				.append(" 	<buildSpec>\n")
				.append("		<buildCommand>")
				.append("			<name>org.eclipse.jdt.core.javabuilder</name>\n")
				.append("			<arguments></arguments>\n")
				.append("		</buildCommand>\n")
				.append("	</buildSpec>\n")
				.append("	<natures>\n")
				.append(" 		<nature>org.eclipse.jdt.core.javanature</nature>\n")
				.append("	</natures>\n").append("</projectDescription>")
				.toString();
	}

	public static final String generateEclipseDotClasspathFile(
			Collection<String> jars) {
		StringBuilder b = new StringBuilder();

		b.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
				.append("<classpath>\n")
				.append("	<classpathentry kind=\"src\" path=\"src\"/>\n")
				.append("	<classpathentry kind=\"con\" path=\"org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.8\"/>\n")
				.append("	<classpathentry kind=\"con\" path=\"org.eclipse.jdt.junit.JUNIT_CONTAINER/4\"/>")
				.append("	<classpathentry kind=\"output\" path=\"bin\"/>\n");

		for (String jar : jars) {
			b.append("	<classpathentry kind=\"lib\" path=\"lib/" + jar
					+ "\"/>\n");
		}

		b.append(" </classpath>");

		return b.toString();
	}

}
