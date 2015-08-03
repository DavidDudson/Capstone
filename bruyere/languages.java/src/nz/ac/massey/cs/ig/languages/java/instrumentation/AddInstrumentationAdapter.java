package nz.ac.massey.cs.ig.languages.java.instrumentation;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_SYNTHETIC;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.ASM5;
import static org.objectweb.asm.Opcodes.ATHROW;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.IFNONNULL;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.PUTFIELD;
import static org.objectweb.asm.Opcodes.RETURN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nz.ac.massey.cs.ig.core.game.instrumentation.InstrumentedBot;
import nz.ac.massey.cs.ig.core.game.instrumentation.Observer;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Instruments a given class by adding the {@link InstrumentedBot} interface
 * 
 * @author Johannes Tandler
 *
 */
public class AddInstrumentationAdapter extends ClassVisitor {

	/**
	 * local field name of observer
	 */
	private static final String OBSERVER_FIELD_NAME = "__observer";

	/**
	 * Access modifier for generated private methods
	 */
	private static final int ACC_MOD_PRIVATE = ACC_PUBLIC;// + ACC_SYNTHETIC;

	/**
	 * Access modifier for generated public methods
	 */
	private static final int ACC_MOD_PUBLIC = ACC_PUBLIC;// + ACC_SYNTHETIC;


	/**
	 * interface name of observer
	 */
	private String observerClassName;

	/**
	 * interface name of Instrumentation... right now {@link InstrumentedBot}
	 */
	private String instrumentableInterface;

	/**
	 * name of adapted class
	 */
	private String owner;
	
	private String fieldOwnerClassName;

	private AddInstrumentationAdapter(ClassVisitor cv) {
		super(ASM5, cv);

		observerClassName = Type.getType(Observer.class).getInternalName();
		instrumentableInterface = Type.getType(InstrumentedBot.class)
				.getInternalName();
	}

	public AddInstrumentationAdapter(ClassVisitor cw, String fullClassName) {
		this(cw);
		fieldOwnerClassName = fullClassName.contains(".") ? fullClassName.replace(".", "/") : fullClassName;
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		this.owner = name;
		
		if(fieldOwnerClassName != null && owner.equals(fieldOwnerClassName)) {
			// add interface if necessary
			List<String> nInterfaces = new ArrayList<String>(
					Arrays.asList(interfaces));
			if (!nInterfaces.contains("")) 
				nInterfaces.add(instrumentableInterface);
			
			// visit class as normal
			super.visit(version, access, name, signature, superName, nInterfaces.toArray(new String[] {}));
	
			// add field for observer
			FieldVisitor fv = visitField(ACC_MOD_PRIVATE,
					OBSERVER_FIELD_NAME, "L" + observerClassName + ";", null, null);
			fv.visitEnd();
		} else {
			super.visit(version, access, name, signature, superName, interfaces);
		}
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor mv = super.visitMethod(access, name, desc, signature,
				exceptions);
		// add instrumentation to every non synthetic and not generated method.
		if ((access & ACC_SYNTHETIC) == 0) {
			if (!name.equals("__initialize") && !name.equals("__getObserver")
					&& !name.equals("<init>") && !name.equals("<clinit>")) {
				mv = new AddMethodInstrumentationAdapter(mv, owner, fieldOwnerClassName,
						observerClassName, OBSERVER_FIELD_NAME);
			}
		}
		return mv;
	}

	@Override
	public void visitEnd() {
		// create methods for InstrumentedBot interface
		if(fieldOwnerClassName != null && owner.equals(fieldOwnerClassName)) {
			createrInterfaceMethods();
		}
		super.visitEnd();
	}

	/**
	 * generates methods in class to fulfill {@link InstrumentedBot} interface
	 */
	private void createrInterfaceMethods() {
		MethodVisitor mv = visitMethod(ACC_MOD_PUBLIC, "__initialize",
				"(L" + observerClassName + ";)V", null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, owner, OBSERVER_FIELD_NAME, "L"
				+ observerClassName + ";");
		Label l1 = new Label();
		mv.visitJumpInsn(IFNONNULL, l1);
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitFieldInsn(PUTFIELD, owner, OBSERVER_FIELD_NAME, "L"
				+ observerClassName + ";");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, owner, OBSERVER_FIELD_NAME, "L"
				+ observerClassName + ";");
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKEINTERFACE, observerClassName, "setObservable",
				"(Ljava/lang/Object;)V", true);
		Label l3 = new Label();
		mv.visitJumpInsn(GOTO, l3);
		mv.visitLabel(l1);
		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		mv.visitTypeInsn(NEW, "java/lang/UnsupportedOperationException");
		mv.visitInsn(DUP);
		mv.visitMethodInsn(INVOKESPECIAL,
				"java/lang/UnsupportedOperationException", "<init>", "()V",
				false);
		mv.visitInsn(ATHROW);
		mv.visitLabel(l3);
		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		mv.visitInsn(RETURN);
		Label l4 = new Label();
		mv.visitLabel(l4);
		mv.visitLocalVariable("this", "L" + owner + ";", null, l0, l4, 0);
		mv.visitLocalVariable("observer", "L" + observerClassName + ";", null,
				l0, l4, 1);
		mv.visitMaxs(2, 2);
		mv.visitEnd();

		mv = visitMethod(ACC_PUBLIC, "__getObserver", "()L" + observerClassName
				+ ";", null, null);
		mv.visitCode();
		Label l1_0 = new Label();
		mv.visitLabel(l1_0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, owner, OBSERVER_FIELD_NAME, "L"
				+ observerClassName + ";");
		mv.visitInsn(ARETURN);
		Label l1_1 = new Label();
		mv.visitLabel(l1_1);
		mv.visitLocalVariable("this", "L" + owner + ";", null, l1_0, l1_1, 0);
		mv.visitMaxs(1, 1);
		mv.visitEnd();
	}
}
