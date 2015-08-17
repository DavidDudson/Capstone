package test.nz.ac.massey.spikes.traceability.tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nz.ac.massey.spikes.traceability.tracer.helper.JSONEncoder;

import org.junit.After;
import org.junit.Test;
/**
 * Test cases for json output
 * @author Li Sui
 *
 */
public class TestJSONEncoder {
	
	
	@Test
	public void testString(){
		JSONEncoder encoder =new JSONEncoder();
		assertEquals("\"2\"",encoder.stringify("2"));
	}
	
	@Test
	public void testPrimitive(){
		JSONEncoder encoder =new JSONEncoder();
		assertEquals("2",encoder.stringify(2));
		assertEquals("true",encoder.stringify(true));
		assertEquals("2.0",encoder.stringify(2.0));
	}
	
	@Test
	public void testPrimitiveArray(){
		JSONEncoder encoder =new JSONEncoder();
		int[] intArray =new int[2];
		intArray[0]=1;
		intArray[1]=2;
		assertEquals("[1,2]",encoder.stringify(intArray));
		
		String[] stringArray =new String[1];
		stringArray[0]="hello";
		assertEquals("[\"hello\"]",encoder.stringify(stringArray));
		
		double[] doubleArray =new double[1];
		doubleArray[0]=2.0;
		assertEquals("[2.0]",encoder.stringify(doubleArray));
		
		float[] floatArray=new float[1];
		floatArray[0]=(float) 1.456;
		assertEquals("[1.456]",encoder.stringify(floatArray));
		
		boolean[] booleanArray =new boolean[1];
		booleanArray[0]=true;
		assertEquals("[true]",encoder.stringify(booleanArray));
	}

	
	@Test
	public void testCollectons(){
		JSONEncoder encoder =new JSONEncoder();
		List<Integer> list1 =new ArrayList<>();
		list1.add(1);
		list1.add(2);
		assertEquals("{\"class\":\"java.util.ArrayList\",\"elements\":[1,2]}",encoder.stringify(list1));
	
		List<String> list2 =new ArrayList<>();
		list2.add("1");
		list2.add("2");
		assertEquals("{\"class\":\"java.util.ArrayList\",\"elements\":[\"1\",\"2\"]}",encoder.stringify(list2));
	}
	
	@Test
	public void testMap(){
		JSONEncoder encoder =new JSONEncoder();
		Map<Integer, String> map1 =new HashMap<>();
		map1.put(1, "1");
		map1.put(2, "2");
		assertEquals("{\"class\":\"java.util.HashMap\",\"elements\":{\"1\":\"1\",\"2\":\"2\"}}",encoder.stringify(map1));
		
		Map<String, Integer> map2 =new HashMap<>();
		map2.put("1", 1);
		map2.put("2", 2);
		assertEquals("{\"class\":\"java.util.HashMap\",\"elements\":{\"1\":1,\"2\":2}}",encoder.stringify(map2));
	}
	@Test
	public void testNestedDataType(){
		JSONEncoder encoder =new JSONEncoder();
		List<Map<String, Integer>> nest =new ArrayList<>();
		Map<String, Integer> map =new HashMap<>();
		map.put("1", 1);
		map.put("2", 2);
		nest.add(map);
		nest.add(map);

		assertEquals("{\"class\":\"java.util.ArrayList\",\"elements\":[{\"class\":\"java.util.HashMap\",\"elements\":{\"1\":1,\"2\":2}},{\"class\":\"java.util.HashMap\",\"elements\":{\"1\":1,\"2\":2}}]}",encoder.stringify(nest));
	}


	
	@Test
	public void testClassWithMaxDepth(){
		JSONEncoder encoder =new JSONEncoder();
		// depth =3 
		Person p =new Person();
		// set max depth is 2
		encoder.setMaxDepth(2);
		String expected = "{\"class\":\"Person\",\"a\":{\"class\":\"A\",\"name\":\"a\",\"b\":\"null\"}}";
		
		assertEquals(expected,encoder.stringify(p));
	}
	
	@Test
	public void testClassWithoutMaxDepth(){
		JSONEncoder encoder =new JSONEncoder();
		// depth =3 
		Person p =new Person();
		// the default maxDepth is 50;
		String expected = "{\"class\":\"Person\",\"a\":{\"class\":\"A\",\"name\":\"a\",\"b\":{\"class\":\"B\",\"name\":\"b\"}}}";
		
		assertEquals(expected,encoder.stringify(p));
	}
	
	@Test
	public void testCricularReference(){
		JSONEncoder encoder =new JSONEncoder();
		C c =new C();
		D d=new D();
		c.setD(d);
		d.setC(c);
		//set max depth is 4
		encoder.setMaxDepth(4);
		String result =encoder.stringify(d);
		String expected ="{\"class\":\"D\",\"name\":\"d\",\"c\":{\"class\":\"C\",\"name\":\"c\",\"d\":{\"class\":\"D\",\"name\":\"d\",\"c\":{\"class\":\"C\",\"name\":\"c\",\"d\":\"null\"}}}}";
		
		assertEquals(result,expected);
	}
	
	@Test
	public void testNestedClass(){
		JSONEncoder encoder =new JSONEncoder();
		University university =new University();
		encoder.setMaxDepth(1);
		String oneLevel= "{\"class\":\"University\",\"colleges\":\"null\"}";
		assertEquals(oneLevel, encoder.stringify(university));
		
		encoder.setMaxDepth(3);
		String threeLevel ="{\"class\":\"University\",\"colleges\":{\"class\":\"Colleges\",\"science\":{\"class\":\"Science\"},\"bbs\":{\"class\":\"Buiness\"},\"health\":{\"class\":\"Health\",\"s\":\"null\"},\"social\":{\"class\":\"Social\",\"s1\":\"null\",\"s2\":\"null\",\"s3\":\"null\",\"s4\":\"null\"}}}";
		assertEquals(threeLevel, encoder.stringify(university));
	}
	
	public class Person{
		A a =new A();
	}
	@SuppressWarnings("unused")
	public class A{
		private String name ="a";
		B b =new B();
	}
	@SuppressWarnings("unused")
	public class B{
		private String name ="b";
	}
	@SuppressWarnings("unused")
	public class C{
		private String name ="c";
		private D d;
		public void setD(D d){
			this.d =d;
		}
	}
	@SuppressWarnings("unused")
	public class D{
		private String name ="d";
		private C c;
		public void setC(C c){
			this.c=c;
		}
	}
	
	public class University{
	
		public Colleges colleges =new Colleges();
	}
	
	public class Colleges{
		Science science= new Science();
		Buiness bbs= new Buiness();
		Health health =new Health();
		Social social =new Social();
	}
	public class Science{
	
	}
	public class Buiness{
	
	}
	public class Health{
		
		Students s =new Students();
	}
	public class Social{

		Students s1=new Students();;
		Students s2=new Students();;
		Students s3=new Students();;
		Students s4=new Students();;
	}
	
	public class Students{
		Name name =new Name();
	}
	public class Name{
		String name ="this is a name";
	}
	
}
