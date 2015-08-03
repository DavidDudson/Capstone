package nz.ac.massey.spikes.traceability.bytecode.instrumenter.analyser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nz.ac.massey.spikes.traceability.bytecode.instrumenter.Variable;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassInstrumentor extends ClassVisitor implements Opcodes{
	private Map<String,List<Variable>> methodMap;
	private String gameId;
	private String botId;
	private final String tracerPath="nz/ac/massey/spikes/traceability/tracer/Tracer";
	private final String addTraceParameter="(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/Object;)V";
    public ClassInstrumentor(ClassVisitor cv, Map<String, List<Variable>> methodMap,String gameId,String botId) {
        super(ASM5, cv);
        this.gameId =gameId;
        this.botId=botId;
        this.methodMap=methodMap;
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name,
            final String desc, final String signature, final String[] exceptions) {
    	   MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
    	if(!name.equals("<init>") && methodMap.get(name) != null){
    		if(name.equals("nextMove")){
    			//increaseIterationCounter(gameId,botId);
    			mv.visitLdcInsn(gameId);
    			mv.visitLdcInsn(botId);
    			mv.visitMethodInsn(Opcodes.INVOKESTATIC, tracerPath, "increaseIterationCounter", "(Ljava/lang/String;Ljava/lang/String;)V", false);
    		}
	       MethodAdapter method =new MethodAdapter(mv, methodMap.get(name));  

	        return method;
    	}else{
    		return mv;
    	}
         
    }

	@Override
	public void visitSource(String source, String debug) {
		
		super.visitSource(source, debug);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		
		return super.visitField(access, name, desc, signature, value);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public void visitEnd() {
		// TODO Auto-generated method stub
		super.visitEnd();
	}
	


	public class MethodAdapter extends MethodVisitor implements Opcodes {
		private int currentLine=0;
		private List<Variable> variables = new ArrayList<Variable>();
		private Variable currentVariable ;
	    public MethodAdapter(final MethodVisitor mv,final List<Variable> variables) {
	        super(ASM5, mv);
	        this.variables=variables;
	    }
	    

		@Override
		public void visitVarInsn(int opcode, int var) {
			super.visitVarInsn(opcode, var);
	
			for(int i=0;i<variables.size();i++){
				Variable v =variables.get(i);
				if(currentLine ==v.getLineNumber()){
					//permitive type store
					if(opcode ==ISTORE ){
						
						integerLoad(v);
						 variables.remove(i);
						 fieldLoad(v);
						
					    break;
					}
					//reference store
					if(opcode ==ASTORE){
						referenceLoad(v);
						variables.remove(i);
						fieldLoad(v);
					    break;
					}
					//long store
					if(opcode ==LSTORE){
						 longLoad(v);
						 variables.remove(i);
						 fieldLoad(v);
						 break;
					}
					// float store
					if(opcode == FSTORE){
						 floatLoad(v);
						 variables.remove(i);
						 fieldLoad(v);
						 break;
					}
					//double store
					if(opcode == DSTORE){
						 doubleLoad(v);
						 variables.remove(i);
						 fieldLoad(v);
						 break;
					}
					 currentVariable =v;
					
					 break;
				}
				}

		}
		
		@Override
		public void visitCode() {
			super.visitCode();
		}


		@Override
		public void visitEnd() {
			super.visitEnd();
		}


		@Override
		public void visitLineNumber(int line, Label start) {
			currentLine=line;
			super.visitLineNumber(line, start);
		}
		@Override
	    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
	        /* do call */
	        mv.visitMethodInsn(opcode, owner, name, desc, itf); 
	        if(opcode == INVOKEINTERFACE){
	        	if(currentVariable.getInstruction()==ALOAD){
	        		 referenceLoad(currentVariable);
					 variables.remove(currentVariable);//pop 
					 fieldLoad(currentVariable);
	        	}
	        }
	    }
		
		private Variable getVariable(String name){
			for(Variable v: variables){
				if(v.getName().equals(name)){
					return v;
				}
			}
			
			return null;
		}
		private void fieldLoad(Variable v){
			 Variable field =getVariable("this");
			 mv.visitLdcInsn(gameId);
    		 mv.visitLdcInsn(botId);
    		 mv.visitLdcInsn(v.getLineNumber());
			 mv.visitLdcInsn(field.getName());
			 mv.visitVarInsn(Opcodes.ALOAD, field.getIndex());
			
			 mv.visitMethodInsn(Opcodes.INVOKESTATIC, tracerPath, "addTrace", addTraceParameter, false);
		}
		private void referenceLoad(Variable v){
			 mv.visitLdcInsn(gameId);
    		 mv.visitLdcInsn(botId);
    		 mv.visitLdcInsn(v.getLineNumber());
			 mv.visitLdcInsn(v.getName());
			 mv.visitVarInsn(ALOAD, v.getIndex());
			 mv.visitMethodInsn(Opcodes.INVOKESTATIC, tracerPath, "addTrace", addTraceParameter, false);
		}
		
		private void floatLoad(Variable v){
			mv.visitLdcInsn(gameId);
   		 	mv.visitLdcInsn(botId);
   		 	mv.visitLdcInsn(v.getLineNumber());
			mv.visitLdcInsn(v.getName());		
			mv.visitVarInsn(Opcodes.FLOAD, v.getIndex());
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;",false);
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, tracerPath, "addTrace", addTraceParameter, false);
		}
		
		private void doubleLoad(Variable v)
		{
			mv.visitLdcInsn(gameId);
   		    mv.visitLdcInsn(botId);
   		    mv.visitLdcInsn(v.getLineNumber());
			mv.visitLdcInsn(v.getName());
			
		     mv.visitVarInsn(Opcodes.DLOAD, v.getIndex());
		     mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;",false);
			 mv.visitMethodInsn(Opcodes.INVOKESTATIC, tracerPath, "addTrace", addTraceParameter, false);
			
		}
		private void longLoad(Variable v){
			mv.visitLdcInsn(gameId);
			mv.visitLdcInsn(botId);
			mv.visitLdcInsn(v.getLineNumber());
			mv.visitLdcInsn(v.getName());
			mv.visitVarInsn(Opcodes.LLOAD, v.getIndex());
	        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;",false);
	        mv.visitMethodInsn(Opcodes.INVOKESTATIC, tracerPath, "addTrace", addTraceParameter, false);
		}
		
		private void integerLoad(Variable v){
			 mv.visitLdcInsn(gameId);
    		 mv.visitLdcInsn(botId);
    		 mv.visitLdcInsn(v.getLineNumber());
			 mv.visitLdcInsn(v.getName());
			
			 mv.visitVarInsn(ILOAD, v.getIndex());
			 
			 if(v.getDescriptor().equals("I")){
				mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf","(I)Ljava/lang/Integer;", false);
			 }
			 if(v.getDescriptor().equals("Z")){
				 mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;",false);
			 }
			 if(v.getDescriptor().equals("S")){
				 mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;",false);
			 }
			 if(v.getDescriptor().equals("C")){
				 mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;",false);
			 }
			 if(v.getDescriptor().equals("B")){
				 mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;",false);
			 }
			 mv.visitMethodInsn(Opcodes.INVOKESTATIC, tracerPath, "addTrace", addTraceParameter, false);
		}
		

	}

}


