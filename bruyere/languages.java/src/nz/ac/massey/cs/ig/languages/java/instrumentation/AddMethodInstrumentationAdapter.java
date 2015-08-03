package nz.ac.massey.cs.ig.languages.java.instrumentation;

import static org.objectweb.asm.Opcodes.ACONST_NULL;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ASM5;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.INVOKEDYNAMIC;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * InstrumentationAdapter to instrument specific methods
 * 
 * @author Johannes Tandler
 *
 */
public class AddMethodInstrumentationAdapter extends MethodVisitor {
	
	/**
	 * name of current class
	 */
	private String clazzName;

	/**
	 * name of observer class name
	 */
	private String observerClassName;
	
	private String fieldOwnerClassName;

	/**
	 * field name of observer
	 */
	private String fieldName;

	/**
	 * true if we are instrumenting right now. Prevents endless recursion by
	 * instrumenting instrumentation calls ;)
	 */
	private boolean running = false;

	/**
	 * Actual line number in class
	 */
	private int currentLineNumber = 0;

	/**
	 * Default constructor
	 * 
	 * @param mv
	 *            default {@link MethodVisitor}
	 * @param clazz
	 *            see {@link #clazzName}
	 * @param observerClassName
	 *            see {@link #observerClassName}
	 * @param fieldName
	 *            see {@link #fieldName}
	 */
	public AddMethodInstrumentationAdapter(MethodVisitor mv, String clazz, String fieldOwnerClassName,
			String observerClassName, String fieldName) {
		super(ASM5, mv);
		this.clazzName = clazz;
		this.observerClassName = observerClassName;
		this.fieldName = fieldName;
		this.fieldOwnerClassName = fieldOwnerClassName;
	}

	/**
	 * Adds invocation of observer with given parameters
	 * 
	 * @param opcode
	 *            {@link Opcodes} of current operation
	 * @param owner
	 *            owner of current operation
	 * @param name
	 *            name of current operation
	 * @param desc
	 *            description of current operation
	 */
	private void addObserverInvoke(int opcode, String owner, String name,
			String desc) {
		// if we are already instrumenting just skip
		if (running)
			return;
		running = true;

		// load observer
		visitVarInsn(ALOAD, 0);
		if(!clazzName.equals(fieldOwnerClassName)) {
			mv.visitFieldInsn(GETFIELD, clazzName, "this$0", "L" + fieldOwnerClassName + ";");
			mv.visitFieldInsn(GETFIELD, fieldOwnerClassName, fieldName, "L"
					+ observerClassName + ";");
		} else {
			mv.visitFieldInsn(GETFIELD, clazzName, fieldName, "L"
					+ observerClassName + ";");
		}

		// setup parameters
		mv.visitLdcInsn(currentLineNumber);
		switch (opcode) {
		case NEW:
			visitInsn(ICONST_1);
			break;
		case INVOKEDYNAMIC:
		case INVOKEINTERFACE:
		case INVOKESTATIC:
		case INVOKESPECIAL:
		case INVOKEVIRTUAL:
			visitInsn(ICONST_0);
			break;
		default:
			visitInsn(ICONST_1);
			break;
		}

		// set owner if available, null otherwise
		if (owner != null)
			visitLdcInsn(owner);
		else
			visitInsn(ACONST_NULL);

		// set name if available, null otherwise
		if (name != null)
			visitLdcInsn(name);
		else
			visitInsn(ACONST_NULL);

		// set description if available, null otherwise
		if (desc != null)
			visitLdcInsn(desc);
		else
			visitInsn(ACONST_NULL);

		// add method call
		visitMethodInsn(INVOKEINTERFACE, observerClassName, "invoke",
				"(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
				true);

		running = false;
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		// store current line number
		this.currentLineNumber = line;
		super.visitLineNumber(line, start);
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);
		// if new object is going to be created notify observer
		if (opcode == NEW) {
			addObserverInvoke(opcode, type, null, null);
		}
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc, boolean itf) {
		super.visitMethodInsn(opcode, owner, name, desc, itf);
		// notify observer about invocations
		if ((opcode >= 182 && opcode <= 186 && opcode != INVOKEDYNAMIC)) {
			addObserverInvoke(opcode, owner, name, desc);
		}
	}
}
