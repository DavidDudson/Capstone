package nz.ac.massey.cs.ig.bytecodeinstrumentation.adapter;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class AddMethodInstrumentationAdapter extends MethodVisitor {

	private String clazzName;
	private String observerClassName;

	private boolean running = false;
	
	private boolean parameters = true;

	public AddMethodInstrumentationAdapter(MethodVisitor mv, String clazz,
			String observerClassName) {
		super(ASM5, mv);
		this.clazzName = clazz;
		this.observerClassName = observerClassName;
	}

	private void invokeThreadLocal(int opcode, String owner, String name,
			String desc) {
		running = true;


		visitVarInsn(ALOAD, 0);
		visitFieldInsn(GETFIELD, clazzName,
				"observerLocal", "Ljava/lang/ThreadLocal;");
		visitMethodInsn(INVOKEVIRTUAL, "java/lang/ThreadLocal", "get",
				"()Ljava/lang/Object;", false);
		visitTypeInsn(CHECKCAST, observerClassName);
		if(parameters) {
			switch (opcode) {
			case INVOKEDYNAMIC:
				visitInsn(ICONST_1);
				break;
			case INVOKEINTERFACE:
				visitInsn(ICONST_2);
				break;
			case INVOKESTATIC:
				visitInsn(ICONST_3);
				break;
			case INVOKESPECIAL:
				visitInsn(ICONST_4);
				break;
			case INVOKEVIRTUAL:
				visitInsn(ICONST_5);
				break;

			default:
				visitInsn(ICONST_0);
				break;
			}
			visitLdcInsn(owner);
			visitLdcInsn(name);
			visitLdcInsn(desc);
		}
		visitMethodInsn(
				INVOKEVIRTUAL,
				observerClassName,
				"invoke",
				parameters ? "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V" : "()V",
				false);

		running = false;
	}
	
	@Override
	public void visitLocalVariable(String name, String desc, String signature,
			Label start, Label end, int index) {
		super.visitLocalVariable(name, desc, signature, start, end, index);
	}
	
	@Override
	public void visitCode() {
		super.visitCode();
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc, boolean itf) {
		super.visitMethodInsn(opcode, owner, name, desc, itf);
		if ((opcode >= 182 && opcode <= 186) || opcode == NEW) {
			if (!running) {
				invokeThreadLocal(opcode, owner, name, desc);
			}
		}
	}
}
