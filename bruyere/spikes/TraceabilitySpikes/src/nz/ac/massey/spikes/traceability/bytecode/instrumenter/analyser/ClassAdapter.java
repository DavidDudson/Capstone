package nz.ac.massey.spikes.traceability.bytecode.instrumenter.analyser;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import nz.ac.massey.spikes.traceability.bytecode.instrumenter.Variable;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class ClassAdapter extends ClassVisitor implements Opcodes {

	private Map<String,List<Variable>> methodMap = new HashMap<>();
    public ClassAdapter(ClassVisitor cv) {
        super(ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name,
            final String desc, final String signature, final String[] exceptions) {
    	 MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
    	if(!name.equals("<init>")){
	       
	        MethodAdapter method =new MethodAdapter(mv);
	        if(methodMap.containsKey(name)){
	        	methodMap.get(name).addAll(method.getVaraibles());
	        }else{
	        	methodMap.put(name, method.getVaraibles());
	        }
	        return method;
    	}else{
    		return mv;
    	}     
    }
    
	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		// TODO Auto-generated method stub
		return super.visitField(access, name, desc, signature, value);
	}
	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public void visitEnd() {
		
		super.visitEnd();
	}
	

	public Map<String,List<Variable>> getMethodMap(){
		return methodMap;
	}


	public class MethodAdapter extends MethodVisitor implements Opcodes {
		private List<Variable> variables = new ArrayList<Variable>();
		private List<Variable> temp =new ArrayList<Variable>();
		private int currentLine=0;
	
	    public MethodAdapter(final MethodVisitor mv) {
	        super(ASM5, mv);
	    }


		@Override
		public AnnotationVisitor visitTypeAnnotation(int typeRef,
				TypePath typePath, String desc, boolean visible) {
			// TODO Auto-generated method stub
			return super.visitTypeAnnotation(typeRef, typePath, desc, visible);
		}


		@Override
		public void visitTypeInsn(int opcode, String type) {
			// TODO Auto-generated method stub
			super.visitTypeInsn(opcode, type);
		}


		@Override
		public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
			for(int i=0;i<temp.size();i++){
				Variable v =temp.get(i);
				if(index == v.getIndex()){
					v.setDescriptor(desc);
					v.setName(name);
					variables.add(v);
				}
			}
			if(name.equals("this")){
				Variable v =new Variable();
				v.setDescriptor(desc);
				v.setIndex(index);
				v.setName(name);
				if(!temp.contains(v)){
					variables.add(v);
				}
			}
			super.visitLocalVariable(name, desc, signature, start, end, index);
		}


		@Override
		public void visitVarInsn(int opcode, int var) {
				
					Variable v =new Variable();
					v.setIndex(var);
					v.setLineNumber(currentLine);
					v.setInstruction(opcode);
					temp.add(v);
			
			super.visitVarInsn(opcode, var);
			
		}

		@Override
		public void visitLineNumber(int line, Label start) {
			currentLine=line;
			super.visitLineNumber(line, start);
		}

		@Override
	    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {  
			
	        mv.visitMethodInsn(opcode, owner, name, desc, itf);			
	    }
	    
	    public List<Variable> getVaraibles(){
	    	return variables;
	    }
	
	}

}

