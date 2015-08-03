package nz.ac.massey.spikes.traceability.bytecode.instrumenter;

public class Variable {
	private String name;
	private String descriptor;
	private int index;
	private int lineNumber;
	private int instruction;


	public int getLineNumber() {
		return lineNumber;
	}



	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}



	public void setName(String name) {
		this.name = name;
	}



	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}



	public void setIndex(int index) {
		this.index = index;
	}



	public int getInstruction() {
		return instruction;
	}



	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}



	@Override
	public String toString() {
		
		return "name:"+name+"  descriptor:"+ descriptor +" index:"+index+" line:"+lineNumber;
	}

	public String getName() {
		return name;
	}

	public String getDescriptor() {
		return descriptor;
	}

	public int getIndex() {
		return index;
	}
	

	
}
