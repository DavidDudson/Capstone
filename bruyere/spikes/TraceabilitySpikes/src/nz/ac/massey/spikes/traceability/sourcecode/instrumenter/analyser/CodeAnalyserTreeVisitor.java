package nz.ac.massey.spikes.traceability.sourcecode.instrumenter.analyser;

import com.sun.source.tree.*;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

import nz.ac.massey.spikes.traceability.sourcecode.instrumenter.JavaClassInfo;
import nz.ac.massey.spikes.traceability.sourcecode.instrumenter.MethodInfo;
import nz.ac.massey.spikes.traceability.sourcecode.instrumenter.VariableInfo;

/**
 * @author Li Sui
 * This class is for visiting source code
 */
@SuppressWarnings("restriction")
public class CodeAnalyserTreeVisitor extends TreePathScanner<Object, Trees> {

	private JavaClassInfo clazzInfo = new JavaClassInfo();
    @Override
    public Object visitClass(ClassTree classTree, Trees trees){ 
    	clazzInfo.setName(classTree.getSimpleName().toString());
    	return super.visitClass(classTree, trees);
    }
    
    @Override
    public Object visitMethod(MethodTree methodTree, Trees trees){
    	TreePath path = getCurrentPath();
    	MethodInfo method =new MethodInfo();
		String methodName = methodTree.getName().toString();
		long endPosition =trees.getSourcePositions().getEndPosition(path.getCompilationUnit(), methodTree);
        method.setLineNumber((int) path.getCompilationUnit().getLineMap().getLineNumber(endPosition));
		method.setName(methodName);	
		
		clazzInfo.addMethod(method);
    	return super.visitMethod(methodTree, trees);
    }
    
    @Override
    public Object visitVariable(VariableTree variableTree, Trees trees) {   		
        TreePath path = getCurrentPath();
		/**
		 * ignore fields
		 */
		if(variableTree.getModifiers().getFlags().isEmpty()){
			VariableInfo variable =new VariableInfo();
			String variableName =variableTree.getName().toString();
			variable.setName(variableName);
			long endPosition =trees.getSourcePositions().getEndPosition(path.getCompilationUnit(), variableTree);
			int lineNumber =(int) path.getCompilationUnit().getLineMap().getLineNumber(endPosition);
			variable.setLineNumber(lineNumber);	
			clazzInfo.getCurrentVisitingMethod().addVariable(variable);
		}	
   
        return super.visitVariable(variableTree, trees);
    }
    
    @Override
    public Object visitMethodInvocation(MethodInvocationTree methodInvocationTree, Trees trees) {
    	 TreePath path = getCurrentPath();
         String name = methodInvocationTree.getMethodSelect().toString();
 		VariableInfo variable =new VariableInfo();
 		System.out.println(name);
 		long endPosition = trees.getSourcePositions().getEndPosition(path.getCompilationUnit(), methodInvocationTree);
 		int lineNumber =(int) path.getCompilationUnit().getLineMap().getLineNumber(endPosition);
 		
 		String[] nameArray = name.split("\\.");
       //do not include direct calling method or print function
 		if (nameArray.length != 1 && !name.equals("System.out.println")) {
 			variable.setName(nameArray[0]);
 			variable.setLineNumber(lineNumber);
 			clazzInfo.getCurrentVisitingMethod().addVariable(variable);
 		}
    	 return super.visitMethodInvocation(methodInvocationTree, trees);
    }
	

	@Override
	public Object visitIf(IfTree node, Trees trees) {

		System.out.println(node.getCondition()+" ");
		return super.visitIf(node, trees);
	}
	
	@Override
	public Object visitAssignment(AssignmentTree node, Trees trees) {
        TreePath path = getCurrentPath();
		/**
		 * ignore fields
		 */
		VariableInfo variable =new VariableInfo();
		String variableName =node.getVariable().toString();
		variable.setName(variableName);
		long endPosition =trees.getSourcePositions().getEndPosition(path.getCompilationUnit(), node);
		int lineNumber =(int) path.getCompilationUnit().getLineMap().getLineNumber(endPosition);
		variable.setLineNumber(lineNumber);	
		clazzInfo.getCurrentVisitingMethod().addVariable(variable);
			
		return super.visitAssignment(node, trees);
	}
	
    @Override
	public Object visitEnhancedForLoop(EnhancedForLoopTree arg0, Trees arg1) {
		// TODO Auto-generated method stub
		return super.visitEnhancedForLoop(arg0, arg1);
	}

	@Override
	public Object visitForLoop(ForLoopTree node, Trees trees) {
//		TreePath path = getCurrentPath();
//		long endPosition = trees.getSourcePositions().getStartPosition(path.getCompilationUnit(), node);
// 		int lineNumber =(int) path.getCompilationUnit().getLineMap().getLineNumber(endPosition);
// 		System.out.println(node.getCondition()+"  "+lineNumber);
		return super.visitForLoop(node, trees);
	}

	/*
     * don't visit return
     * @see com.sun.source.util.TreeScanner#visitReturn(com.sun.source.tree.ReturnTree, java.lang.Object)
     */
    @Override
    public Object visitReturn(ReturnTree returnTree,Trees trees){
    	return null;
    }
    
	@Override
	public Object visitImport(ImportTree node, Trees trees) {
		//doesn't work
		return super.visitImport(node, trees);
	}
	

	@Override
	public Object visitAnnotation(AnnotationTree node, Trees trees) {

		return super.visitAnnotation(node, trees);
	}

	@Override
	public Object visitBlock(BlockTree node, Trees trees) {
	
		return super.visitBlock(node, trees);
	}
	@Override
	public Object visitExpressionStatement(ExpressionStatementTree node,
			Trees trees) {
		//overlap with variable assignment visiting
		return super.visitExpressionStatement(node, trees);
	}

	@Override
	public Object visitIdentifier(IdentifierTree node, Trees trees) {
		
		return super.visitIdentifier(node, trees);
	}


	@Override
	public Object visitMemberSelect(MemberSelectTree node, Trees trees) {
	
		return super.visitMemberSelect(node, trees);
	}
	
    /**
     * Returns the Java class model which holds the details of java source
     * @return clazzInfo Java class model 
     */
    protected JavaClassInfo getClassInfo() {
        return clazzInfo;
    }
}
