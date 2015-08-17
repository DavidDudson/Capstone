package nz.ac.massey.cs.ig.bytecodeinstrumentation;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.SecureClassLoader;

import nz.ac.massey.cs.ig.bytecodeinstrumentation.adapter.AddInstrumentationAdapter;
import nz.ac.massey.cs.ig.bytecodeinstrumentation.compiling.CompileUtils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

public class Main {

	public static void main(String[] args) throws Exception {
		String bot1Name = "primegame/GreedyBuiltinBot";
		String name = "primegame/BlackMamba";

		final byte[] originalClassBytesPlayer1 = CompileUtils
				.compileAndGetByteCode(bot1Name);
		final byte[] originalClassBytes = CompileUtils
				.compileAndGetByteCode(name);

		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

		ClassVisitor adapter = new AddInstrumentationAdapter(cw);
		TraceClassVisitor tcv = new TraceClassVisitor(adapter, new ASMifier(),
				new PrintWriter(System.out));
		ClassReader cr = new ClassReader(originalClassBytes);
		cr.accept(tcv, 0);

		final byte[] modifiedClassBytes = cw.toByteArray();
		writeToFile(modifiedClassBytes, name);

		if (name.contains("/")) {
			name = name.substring(name.lastIndexOf("/") + 1);
		}
		if (bot1Name.contains("/")) {
			bot1Name = bot1Name.substring(bot1Name.lastIndexOf("/") + 1);
		}
		
		final Class<?> bot1Class = getClassloader(bot1Name, originalClassBytesPlayer1);
		final Class<?> bot2Class = getClassloader(name, modifiedClassBytes);

		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(new GameExecutor(bot1Class, bot2Class));
			t.start();
			while(t.isAlive()) {
				Thread.sleep(200);
			}
			//System.out.println("-");
		}
	}

	private static Class<?> getClassloader(String name, final byte[] bytes) {
		SecureClassLoader classLoader = new SecureClassLoader(
				Main.class.getClassLoader()) {
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

	private static void writeToFile(byte[] bytes, String name) {
		try {
			if (name.contains("/")) {
				name = name.substring(name.indexOf("/") + 1);
			}
			OutputStream stream = Files.newOutputStream(
					Paths.get(name + ".class"), StandardOpenOption.CREATE);
			stream.write(bytes);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
