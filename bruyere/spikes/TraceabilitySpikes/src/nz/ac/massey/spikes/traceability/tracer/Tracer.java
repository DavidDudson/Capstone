package nz.ac.massey.spikes.traceability.tracer;

import java.util.ArrayList;
import java.util.List;

import nz.ac.massey.spikes.traceability.tracer.helper.TraceManager;
/**
 * registering a tracing event
 * @author Li Sui
 *
 */
public class Tracer {
	
	public static TraceManager manager =new TraceManager();
	/**
	 * register one variable info at a time for a snapshot
	 * @param gameId
	 * @param botId
	 * @param line
	 * @param varName
	 * @param value
	 */
    public static void addTrace(String gameId,String botId,int lineNumber,String varName,Object varValue){
    	Snapshot snapshot =manager.getSnapshot(gameId,botId, lineNumber);
    	if(snapshot==null){
    		//if the snapshot not exist in list. make a new one
    		snapshot =new Snapshot();
        	snapshot.setLineNumber(lineNumber);
        	snapshot.registerVariable(varName, varValue);
        	//add snapshot to current moveSnapshot
        	manager.addSnapshot(gameId,botId,snapshot);
        	
    	}else{
    		//otherwise register a new variable to current snapshot
    		snapshot.registerVariable(varName, varValue);
    	}
    }
    /**
     * close moveSnapshot list and add to list of moves
     * @param gameId
     * @param botId
     */
    public static void increaseIterationCounter(String gameId,String botId) {
    	List<Snapshot> snapshotsForMove =new ArrayList<>();
    	manager.addMoveSnapshot(snapshotsForMove, botId,gameId);
    }
}
