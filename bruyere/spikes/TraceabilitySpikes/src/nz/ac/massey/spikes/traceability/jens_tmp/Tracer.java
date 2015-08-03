package nz.ac.massey.spikes.traceability.jens_tmp;

import java.util.List;

// could be a singleton, or static
public interface Tracer {
	
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
	
	/**
	 * Register a trace event.
	 * @param botId - note that multiple bots (in diff threads) generated traces at the same time
	 * @param identifier the variable identifier (incl this)
	 * @param line
	 * @param iteration
	 * @param value
	 */
	public void trace(String botId,int line,int iteration,String identifier,Object value);
	
	/**
	 * Access the traces. The outer list contains traces for different moves (= invocations of the move method).
	 * The inner list represents the snapshots taken during a single execution of move.
	 * Note that the invocation should remove references to the respective data. if no data can be found for this botId,
	 * throw an IllegalStateException. 
	 * @param botId
	 * @return
	 */
	public List<List<Snapshot>> getTraces(String botId);
	
}
