package test.nz.ac.massey.spikes.traceability.tests;

import static org.junit.Assert.*;

import java.util.List;

import nz.ac.massey.spikes.traceability.sourcecode.instrumenter.Instrumenter;
import nz.ac.massey.spikes.traceability.sourcecode.instrumenter.MethodInfo;

import org.junit.Test;
/**
 * Test visitors
 * @author Li Sui
 */
public class TestVistor {
	private final String classPath = "nz.ac.massey.spikes.testSourceCode.TestBot1";
	private final String gameId="game";
	@Test
	public void testVisitMethod() throws Exception{
		StringBuilder method =new StringBuilder(
				"package nz.ac.massey.spikes.testSourceCode;\n"
				+	"public class TestBot1{\n"
				+	"public void nextMove() {\n"
				+	"}\n"
				+	"}"
					);
		Instrumenter instrumenter=	new Instrumenter();
		instrumenter.instrument(method.toString(), classPath, "my first bot",gameId);
		List<MethodInfo> methods =instrumenter.getMethods();
		
		//contains nextMove method
		assertEquals("nextMove",methods.get(1).getName() );
		//has two methods. a constructor and "nextMove" 
		assertEquals(2,methods.size() );	
	}
	
	@Test
	public void testVisitVariable() throws Exception{
		StringBuilder variable =new StringBuilder(
				"package nz.ac.massey.spikes.testSourceCode;\n"
				+	"public class TestBot1{\n"
				+	"public void nextMove() {\n"
				+	"int i =0;\n"
				+	"}\n"
				+	"}"
					);
		Instrumenter instrumenter=	new Instrumenter();
		instrumenter.instrument(variable.toString(), classPath, "my first bot",gameId);
		List<MethodInfo> methods =instrumenter.getMethods();
		
		//only on variable been visited
		assertEquals(1,methods.get(1).getVariablesList().size());
		//variable should be in line 5
		assertEquals(4,methods.get(1).getVariablesList().get(0).getLineNumber());
		//variable name should be i
		assertEquals("i",methods.get(1).getVariablesList().get(0).getName() );
	}
	
	@Test
	public void testVisitMethodInvocation() throws Exception{
		StringBuilder variable =new StringBuilder(
				"package nz.ac.massey.spikes.testSourceCode;\n"
				+	"public class TestBot1{\n"
				+	"public void nextMove() {\n"
				+	"this.add();\n"
				+	"}\n"
				+	"public void add(){\n"
				+	"}\n"
				+	"}"
					);
		Instrumenter instrumenter=	new Instrumenter();
		instrumenter.instrument(variable.toString(), classPath, "my first bot",gameId);
		List<MethodInfo> methods =instrumenter.getMethods();
		
		//only on methodInvocation been visited
		assertEquals(1,methods.get(1).getVariablesList().size());
		
		//variable should be in line 5
		assertEquals(4,methods.get(1).getVariablesList().get(0).getLineNumber());
		//variable name should be this
		assertEquals("this",methods.get(1).getVariablesList().get(0).getName() );
	}
}
