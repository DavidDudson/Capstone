package nz.ac.massey.spikes.traceability.sourcecode.instrumenter;

import java.util.ArrayList;
import java.util.List;

/**
 * this class contains methodInfo. key:method name  value:list of variables in methodInfo
 * @author Li Sui
 *
 */
public class JavaClassInfo {
	private String name;
	private List<MethodInfo> methods = new ArrayList<MethodInfo>();
	
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public List<MethodInfo> getMethods() {
		return methods;
	}
	public void addMethod(final MethodInfo method) {
		this.methods.add(method);
	}
	public MethodInfo getCurrentVisitingMethod(){
		return methods.get(methods.size()-1);
	}

	
}
