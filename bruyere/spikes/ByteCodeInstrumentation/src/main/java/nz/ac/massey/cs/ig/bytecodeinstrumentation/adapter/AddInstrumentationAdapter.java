package nz.ac.massey.cs.ig.bytecodeinstrumentation.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class AddInstrumentationAdapter extends ClassVisitor {

	private String observerClassName = "nz/ac/massey/cs/ig/bytecodeinstrumentation/instrumentation/Observer";
	private String instrumentableInterface = "nz/ac/massey/cs/ig/bytecodeinstrumentation/instrumentation/Instrumentable";

	private String owner;

	private int accessModifierPrivate = ACC_PRIVATE + ACC_SYNTHETIC;
	private int accessModifierPublic = ACC_PUBLIC + ACC_SYNTHETIC;

	public AddInstrumentationAdapter(ClassVisitor cv) {
		super(ASM5, cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		this.owner = name;
		List<String> nInterfaces = new ArrayList<String>(
				Arrays.asList(interfaces));
		if (!nInterfaces.contains("")) {
			nInterfaces.add(instrumentableInterface);
		}
		super.visit(version, access, name, signature, superName,
				nInterfaces.toArray(new String[] {}));
		FieldVisitor fv = visitField(accessModifierPrivate, "observerLocal",
				"Ljava/lang/ThreadLocal;", "Ljava/lang/ThreadLocal<L"
						+ observerClassName + ";>;", null);
		fv.visitEnd();
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor mv = super.visitMethod(access, name, desc, signature,
				exceptions);
		if (!name.equals("_invokeObserver") && !name.equals("_initialize")
				&& !name.equals("_close") && !name.equals("<init>")) {
			mv = new AddMethodInstrumentationAdapter(mv, owner, observerClassName);
		}
		return mv;
	}

	@Override
	public void visitEnd() {
		createrInterfaceMethods();
		super.visitEnd();
	}

	private void createrInterfaceMethods() {
		MethodVisitor mv = visitMethod(accessModifierPublic , "_initialize", "()V", null,
				null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, owner, "observerLocal",
				"Ljava/lang/ThreadLocal;");
		Label l1 = new Label();
		mv.visitJumpInsn(IFNONNULL, l1);
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitTypeInsn(NEW, "java/lang/ThreadLocal");
		mv.visitInsn(DUP);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/ThreadLocal", "<init>",
				"()V", false);
		mv.visitFieldInsn(PUTFIELD, owner, "observerLocal",
				"Ljava/lang/ThreadLocal;");
		mv.visitLabel(l1);
		mv.visitFrame(F_SAME, 0, null, 0, null);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, owner, "observerLocal",
				"Ljava/lang/ThreadLocal;");
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/ThreadLocal", "get",
				"()Ljava/lang/Object;", false);
		Label l3 = new Label();
		mv.visitJumpInsn(IFNONNULL, l3);
		Label l4 = new Label();
		mv.visitLabel(l4);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, owner, "observerLocal",
				"Ljava/lang/ThreadLocal;");
		mv.visitTypeInsn(NEW, observerClassName);
		mv.visitInsn(DUP);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, observerClassName, "<init>",
				"(Ljava/lang/Object;)V", false);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/ThreadLocal", "set",
				"(Ljava/lang/Object;)V", false);
		mv.visitLabel(l3);
		mv.visitFrame(F_SAME, 0, null, 0, null);
		mv.visitInsn(RETURN);
		mv.visitMaxs(4, 1);
		mv.visitEnd();

		mv = visitMethod(accessModifierPublic, "_close", "()V", null, null);
		mv.visitCode();
		Label l1_0 = new Label();
		mv.visitLabel(l1_0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, owner, "observerLocal",
				"Ljava/lang/ThreadLocal;");
		Label l1_1 = new Label();
		mv.visitJumpInsn(IFNULL, l1_1);
		Label l1_2 = new Label();
		mv.visitLabel(l1_2);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, owner, "observerLocal",
				"Ljava/lang/ThreadLocal;");
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/ThreadLocal", "remove",
				"()V", false);
		mv.visitLabel(l1_1);
		mv.visitFrame(F_SAME, 0, null, 0, null);
		mv.visitInsn(RETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();
		
		mv = visitMethod(ACC_PUBLIC, "getObserver", "()Lnz/ac/massey/cs/ig/bytecodeinstrumentation/instrumentation/Observer;", null, null);
		mv.visitCode();
		Label l2_0 = new Label();
		mv.visitLabel(l2_0);
		mv.visitLineNumber(52, l2_0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, owner, "observerLocal", "Ljava/lang/ThreadLocal;");
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/ThreadLocal", "get", "()Ljava/lang/Object;", false);
		mv.visitTypeInsn(CHECKCAST, observerClassName);
		mv.visitInsn(ARETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();
	}
}
