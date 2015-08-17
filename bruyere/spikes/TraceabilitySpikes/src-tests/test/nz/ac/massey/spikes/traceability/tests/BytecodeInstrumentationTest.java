package test.nz.ac.massey.spikes.traceability.tests;

import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import nz.ac.massey.spikes.traceability.bytecode.instrumenter.CompileUtils;
import nz.ac.massey.spikes.traceability.bytecode.instrumenter.analyser.ClassAdapter;
import nz.ac.massey.spikes.traceability.bytecode.instrumenter.analyser.ClassInstrumentor;
import nz.ac.massey.spikes.traceability.tracer.Snapshot;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

public class BytecodeInstrumentationTest {
	private String botName ="test/Bot";
	private String clazzName = botName.replace("/", ".");

	@Before
	public void init(){

	}
	
	@Test
	public void testAdapterVisitComplexType(){
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		TraceClassVisitor tcv = new TraceClassVisitor(cw, new Textifier(),
				new PrintWriter(System.out));
		ClassAdapter adapter =new ClassAdapter(cw);
		ClassReader cr = new ClassReader(compile());
		cr.accept(adapter, 0);
		//only two method been visited
		assertEquals(2,adapter.getMethodMap().size());
		//nextMove has 6 variables(include 2 this)
		assertEquals(6,adapter.getMethodMap().get("nextMove").size());
		//map variable should be at line 24
		assertEquals(24,adapter.getMethodMap().get("nextMove").get(4).getLineNumber());
		//map variable's descriptor
		assertEquals("Ljava/util/Map;",adapter.getMethodMap().get("nextMove").get(4).getDescriptor());
		//map variable's name
		assertEquals("map",adapter.getMethodMap().get("nextMove").get(4).getName());
		//map variable should be stored at 3 on stack
		assertEquals(3,adapter.getMethodMap().get("nextMove").get(4).getIndex());
		//map varaible's instruction ASTORE
		assertEquals(58,adapter.getMethodMap().get("nextMove").get(4).getInstruction());
	}
	
	@Test
	public void testAdapterVisitPrimitiveType(){
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		TraceClassVisitor tcv = new TraceClassVisitor(cw, new Textifier(),
				new PrintWriter(System.out));
		ClassAdapter adapter =new ClassAdapter(cw);
		ClassReader cr = new ClassReader(compile());
		cr.accept(adapter, 0);
		
		//only two method been visited
		assertEquals(2,adapter.getMethodMap().size());
		//primitive has 9 variables(include this)
		assertEquals(9,adapter.getMethodMap().get("primitive").size());
		//i variable should be at line 24
		assertEquals(31,adapter.getMethodMap().get("primitive").get(1).getLineNumber());
		//i variable's descriptor
		assertEquals("I",adapter.getMethodMap().get("primitive").get(1).getDescriptor());
		//i variable's name
		assertEquals("i",adapter.getMethodMap().get("primitive").get(1).getName());
		//i variable should be stored at 1 on stack
		assertEquals(1,adapter.getMethodMap().get("primitive").get(1).getIndex());
		//i varaible's instruction ISTORE
		assertEquals(54,adapter.getMethodMap().get("primitive").get(1).getInstruction());
		//name varaibles's instruction ASTORE
		assertEquals(58,adapter.getMethodMap().get("primitive").get(2).getInstruction());
		//d varaibles's instruction DSTORE
		assertEquals(57,adapter.getMethodMap().get("primitive").get(3).getInstruction());
		//f varaibles's instruction FSTORE
		assertEquals(56,adapter.getMethodMap().get("primitive").get(4).getInstruction());
		//s varaibles's instruction ASTORE
		assertEquals(54,adapter.getMethodMap().get("primitive").get(5).getInstruction());
		//l varaibles's instruction DSTORE
		assertEquals(55,adapter.getMethodMap().get("primitive").get(6).getInstruction());
		//c varaibles's instruction ASTORE
		assertEquals(54,adapter.getMethodMap().get("primitive").get(7).getInstruction());
		//b variables' instruction ASTORE
		assertEquals(54,adapter.getMethodMap().get("primitive").get(8).getInstruction());
	}
	
	@Test
	public void testInstrumentation() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		TraceClassVisitor tcv = new TraceClassVisitor(cw, new Textifier(),
				new PrintWriter(System.out));
		ClassAdapter adapter =new ClassAdapter(cw);
		ClassReader cr = new ClassReader(compile());
		cr.accept(adapter, 0);
		
		ClassWriter cw2 = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassInstrumentor instrumentor =new ClassInstrumentor(cw2,adapter.getMethodMap(),"game","bot");
		cr.accept(instrumentor, 0);
		final byte[] instrumentedByteCode = cw2.toByteArray();
		Class<?> claz = CompileUtils.getClassloader(clazzName,instrumentedByteCode);
        
		Method thisMethod = claz.getMethod("nextMove", List.class);

		Constructor c =claz.getConstructor(String.class);
		Object instance =c.newInstance("bot");
	    thisMethod.invoke(instance,getListOfInt(1,2,3,4,5));
	   
	    List<List<Snapshot>> snapshots =nz.ac.massey.spikes.traceability.tracer.Tracer.manager.getManager().get("game").get("bot");
	    //list initialization and add() invocation(complex type)
	    assertEquals(22,snapshots.get(0).get(0).getLineNumber());
	    assertEquals("{\"class\":\"java.util.ArrayList\",\"elements\":[]}",snapshots.get(0).get(0).getValue("list"));
	    assertEquals(25,snapshots.get(0).get(2).getLineNumber());
	    assertEquals("{\"class\":\"java.util.ArrayList\",\"elements\":[1]}",snapshots.get(0).get(2).getValue("list"));
	    //map initialization and put() invocation(complex type)
	    assertEquals(24,snapshots.get(0).get(1).getLineNumber());
	    assertEquals("{\"class\":\"java.util.HashMap\",\"elements\":{}}",snapshots.get(0).get(1).getValue("map"));
	    assertEquals(26,snapshots.get(0).get(3).getLineNumber());
	    assertEquals("{\"class\":\"java.util.HashMap\",\"elements\":{\"1\":\"hello\"}}",snapshots.get(0).get(3).getValue("map"));
	    
	    //primitive type
	    assertEquals(31,snapshots.get(0).get(4).getLineNumber());
	    assertEquals("2",snapshots.get(0).get(4).getValue("i"));
	    assertEquals(32,snapshots.get(0).get(5).getLineNumber());
	    assertEquals("\"Jack\"",snapshots.get(0).get(5).getValue("name"));
	    assertEquals(33,snapshots.get(0).get(6).getLineNumber());
	    assertEquals("1.002",snapshots.get(0).get(6).getValue("d"));
	    assertEquals(34,snapshots.get(0).get(7).getLineNumber());
	    assertEquals("1234.0",snapshots.get(0).get(7).getValue("f"));
	    assertEquals(35,snapshots.get(0).get(8).getLineNumber());
	    assertEquals("1",snapshots.get(0).get(8).getValue("s"));
	    assertEquals(36,snapshots.get(0).get(9).getLineNumber());
	    assertEquals("1234",snapshots.get(0).get(9).getValue("l"));
	    assertEquals(37,snapshots.get(0).get(10).getLineNumber());
	    assertEquals("\"c\"",snapshots.get(0).get(10).getValue("c"));
	    assertEquals(38,snapshots.get(0).get(11).getLineNumber());
	    assertEquals("true",snapshots.get(0).get(11).getValue("b"));
	    
	}
	
	private byte[] compile(){
		return CompileUtils.compileAndGetByteCode(botName, clazzName);	
	}
	private static List<Integer> getListOfInt(int... numbers) {
		List<Integer> list = new ArrayList<>(numbers.length);
		for (int i:numbers) list.add(i);
		return list;
	}
}
