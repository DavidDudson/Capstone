package nz.ac.massey.spikes.traceability.jens_tmp;

import java.util.Map;

/**
 * The encoder is used by the tracer to stringify objects. 
 * @author jens dietrich
 */
public interface Encoder {
	
	/**
	 * Set the max encoding depth.
	 * @param i
	 */
	public void setMaxDepth(int i) ;
	
	/**
	 * Set the max number of snapshots recorded per move.
	 * @param i
	 */
	public void setMaxNumberOfSnapshots(int i) ;
	
	/**
	 * Set the max number of variables recorded per snapshot.
	 * @param i
	 */
	public void setMaxNumberOfVariables(int i) ;
	
	
	void stringify(Object o,Appendable out);
	void stringifyAll(Map<String,String> map,Appendable out);
}
