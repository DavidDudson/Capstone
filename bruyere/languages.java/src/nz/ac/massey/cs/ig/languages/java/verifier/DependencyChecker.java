package nz.ac.massey.cs.ig.languages.java.verifier;

import nz.ac.massey.cs.ig.core.services.build.BuildProblemCollector;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;

/**
 * Visits the byte code of a class and checks access to other classes.
 *
 * @author jens dietrich
 */
public class DependencyChecker extends ClassVisitor {

    private AbstractByteCodeVerifier verifier = null;
    private String thisClass = null;
    private BuildProblemCollector dLog = null;
    private int lineNo = -1;

    public DependencyChecker(AbstractByteCodeVerifier verifier,BuildProblemCollector dLog) {
        super(Opcodes.ASM4);
        this.verifier = verifier;
        this.dLog = dLog;
    }

    @Override
    public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
    	this.lineNo = -1;
        thisClass = name;
        
        int nestingLevel = countNestingLevel(name);
        verifier.checkClassNestingLevel(name, nestingLevel);

        if (superName != null) {
            checkTypeAccess(superName);
        }
        if (interfaces != null) {
            for (String i : interfaces) {
                checkTypeAccess(i);
            }
        }
        if (signature != null) {
            SignatureVisitor v = new SignatureDependencyVisitor();
            SignatureReader r = new SignatureReader(signature);
            r.accept(v);
        }
    }

    private int countNestingLevel(String name) {
    	int nestingLevel = 0;
    	while(name.indexOf("$") > -1) {
    		name = name.substring(name.indexOf("$") + 1);
    		nestingLevel++;
    	}
    	return nestingLevel;
	}

	@Override
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        checkDesc(desc);
        return new AnnotationDependencyVisitor();
    }

    @Override
    public FieldVisitor visitField(final int access, final String name, final String desc, final String signature, final Object value) {

    	this.lineNo = -1;
    	
    	verifier.checkFieldAccessModifier(access, name);
    	
        if (signature == null) {
            checkDesc(desc);
        } else {
            checkTypeSignature(signature);
        }
        if (value instanceof Type) {
            checkTypeAccess((Type) value);
        }
        return new FieldDependencyVisitor();
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
    	this.lineNo = -1;
    	
    	verifier.checkMethodAccessModifier(access, name);
    	
    	if (signature == null) {
            checkMethodDesc(desc);
        } else {
            // this is a method signature, not a type signature
            checkSignature(signature);
        }
        checkInternalNames(exceptions);
        return new MethodDependencyVisitor();
    }

    class AnnotationDependencyVisitor extends AnnotationVisitor {

        public AnnotationDependencyVisitor() {
            super(Opcodes.ASM4);
        }

        @Override
        public void visit(final String name, final Object value) {
            if (value instanceof Type) {
                checkTypeAccess((Type) value);
            }
        }

        @Override
        public void visitEnum(final String name, final String desc,
                final String value) {
            checkDesc(desc);
        }

        @Override
        public AnnotationVisitor visitAnnotation(final String name,
                final String desc) {
            checkDesc(desc);
            return this;
        }

        @Override
        public AnnotationVisitor visitArray(final String name) {
            return this;
        }
    }

    class FieldDependencyVisitor extends FieldVisitor {

        public FieldDependencyVisitor() {
            super(Opcodes.ASM4);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            checkDesc(desc);
            return new AnnotationDependencyVisitor();
        }
    }

    class MethodDependencyVisitor extends MethodVisitor {

        public MethodDependencyVisitor() {
            super(Opcodes.ASM4);
        }

        @Override
        public AnnotationVisitor visitAnnotationDefault() {
            return new AnnotationDependencyVisitor();
        }

        @Override
        public AnnotationVisitor visitAnnotation(final String desc,
                final boolean visible) {
            checkDesc(desc);
            return new AnnotationDependencyVisitor();
        }

        @Override
        public AnnotationVisitor visitParameterAnnotation(final int parameter,
                final String desc, final boolean visible) {
            checkDesc(desc);
            return new AnnotationDependencyVisitor();
        }

        @Override
        public void visitTypeInsn(final int opcode, final String type) {
            checkTypeAccess(Type.getObjectType(type));
        }

        @Override
        public void visitFieldInsn(final int opcode, final String owner,
                final String name, final String desc) {
            checkInternalName(owner);
        }

        @Override
        public void visitMethodInsn(final int opcode, final String owner,
                final String name, final String desc) {
            checkInternalName(owner);
        }

        @Override
        public void visitInvokeDynamicInsn(String name, String desc,
                Handle bsm, Object... bsmArgs) {
            checkMethodDesc(desc);
            checkConstant(bsm);
            for (int i = 0; i < bsmArgs.length; i++) {
                checkConstant(bsmArgs[i]);
            }
        }

        @Override
        public void visitLdcInsn(final Object cst) {
            checkConstant(cst);
        }

        @Override
        public void visitMultiANewArrayInsn(final String desc, final int dims) {
            checkDesc(desc);
        }

        @Override
        public void visitLocalVariable(final String name, final String desc,
                final String signature, final Label start, final Label end,
                final int index) {
            if (signature == null) {
                checkDesc(desc);
            } else {
                checkTypeSignature(signature);
            }
        }

        @Override
        public void visitTryCatchBlock(final Label start, final Label end,
                final Label handler, final String type) {
            if (type != null) {
                checkInternalName(type);
            }
        }

		@Override
		public void visitLineNumber(int no, Label arg1) {
			DependencyChecker.this.lineNo = no;
		}
        
    }

    class SignatureDependencyVisitor extends SignatureVisitor {

        String signatureClassName;

        public SignatureDependencyVisitor() {
            super(Opcodes.ASM4);
        }

        @Override
        public void visitClassType(final String name) {
            // System.out.println("visit class in signature: " + name);
            if (signatureClassName == null) {
                signatureClassName = name; // top level !
            }
            checkInternalName(name);
        }

        @Override
        public void visitInnerClassType(final String name) {
            signatureClassName = signatureClassName + "$" + name;
            checkInternalName(signatureClassName);
        }
    }

    void checkInternalName(final String name) {
        checkTypeAccess(Type.getObjectType(name));
    }

    private void checkInternalNames(final String[] names) {
        for (int i = 0; names != null && i < names.length; i++) {
            checkInternalName(names[i]);
        }
    }

    void checkDesc(final String desc) {
        checkTypeAccess(Type.getType(desc));
    }

    void checkMethodDesc(final String desc) {
        checkTypeAccess(Type.getReturnType(desc));
        Type[] types = Type.getArgumentTypes(desc);
        for (int i = 0; i < types.length; i++) {
            checkTypeAccess(types[i]);
        }
    }

    void checkTypeAccess(final Type t) {
        switch (t.getSort()) {
            case Type.ARRAY:
                checkTypeAccess(t.getElementType());
                break;
            case Type.OBJECT:
                checkTypeAccess(t.getInternalName());
                break;
            case Type.METHOD:
                checkMethodDesc(t.getDescriptor());
                break;
        }
    }

    private void checkSignature(final String signature) {
        if (signature != null) {
            new SignatureReader(signature)
                    .accept(new SignatureDependencyVisitor());
        }
    }

    void checkTypeSignature(final String signature) {
        if (signature != null) {
            try {
                new SignatureReader(signature)
                        .acceptType(new SignatureDependencyVisitor());
            } catch (Exception x) {
                System.err.println("Error visiting signature " + signature);
            }
        }
    }

    void checkConstant(final Object cst) {
        if (cst instanceof Type) {
            checkTypeAccess((Type) cst);
        } else if (cst instanceof Handle) {
            Handle h = (Handle) cst;
            checkInternalName(h.getOwner());
            checkMethodDesc(h.getDesc());
        }
    }

    private void checkTypeAccess(String type) throws SecurityException {
        if (!type.equals(thisClass)) {
            verifier.checkAccessToType(type, thisClass, dLog, lineNo);
        }
    }

}
