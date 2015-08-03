package nz.ac.massey.spikes.traceability.jens_tmp;

import java.util.Map;

/**
 * JSON Encoder. TODO: test
 * @author jens dietrich
 */
public class JSONEncoder implements Encoder {
	
	private int maxDepth = 5;
	private int maxNumberOfSnapshots = 100;
	private int maxNumberOfVariables = 20;
	
	
	@Override 
	public void stringify(Object o,Appendable out) {
		// TODO:
		// strings and primitives: use String.valueOf() - ignore i18n
		// for collections, iterate and use json array syntax , recursively  encode elements (but check depth)
		// for maps, iterate over all key-value pairs, and use json object syntax, recursively  encode value (but check depth)
		// for objects, iterate over all variables (use reflection - fields or ), and use json object syntax, recursively  encode value (but check depth)
		
		// example: assume we use a class Point with two fields x,y  and instance is created with new Point(1,2)
		// the result should be a map {"class":"Point","x":"1","y":"2"}  
		// issue: can we get with reflection to access private fields ? could also use java.beans.Introspector if private access does not work, or java.lang.invoke.MethodHandle (probably fastest)

	}
	@Override 
	public void stringifyAll(Map<String,String> map,Appendable out) {
		// TODO: JSON object

	}
	public int getMaxDepth() {
		return maxDepth;
	}
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}
	public int getMaxNumberOfSnapshots() {
		return maxNumberOfSnapshots;
	}
	public void setMaxNumberOfSnapshots(int maxNumberOfSnapshots) {
		this.maxNumberOfSnapshots = maxNumberOfSnapshots;
	}
	public int getMaxNumberOfVariables() {
		return maxNumberOfVariables;
	}
	public void setMaxNumberOfVariables(int maxNumberOfVariables) {
		this.maxNumberOfVariables = maxNumberOfVariables;
	}
}
