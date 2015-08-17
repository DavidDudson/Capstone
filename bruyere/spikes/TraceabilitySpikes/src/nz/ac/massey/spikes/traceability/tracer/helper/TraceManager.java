package nz.ac.massey.spikes.traceability.tracer.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import nz.ac.massey.spikes.traceability.tracer.Snapshot;

import com.google.common.collect.MapMaker;
/**
 * this class is used to manage bots' tracing information
 * @author Li Sui
 *
 */
public class TraceManager{

	/**
	 * map game Id to list of bots
	 * Map<String,<List<List<Snapshot>>> repersents bots
	 * List<List<Snapshot>> repersents moves
	 * List<Snapshot> repersents snapshots
	 */
	private ConcurrentMap<String, Map<String,List<List<Snapshot>>>> manager =new MapMaker().makeMap();
	/**
	 * get results for a given bot in JSON format.
	 * @return string
	 */
	public String getJSON(final String gameId,final String botId){
		StringBuilder json =new StringBuilder();
		
		List<List<Snapshot>> listOfMoves = manager.get(gameId).get(botId);
		json.append("{\"botID:"+botId+"\":[");
		
		for(int i=0;i<listOfMoves.size();i++){
			json.append("{"+"\"iteration: "+(i+1)+"\":[");
			if(i == listOfMoves.size()-1){	
				for(int j =0; j<listOfMoves.get(i).size();j++){
					if(j==listOfMoves.get(i).size()-1){
						json.append(listOfMoves.get(i).get(j).getJson());
					}else{
						json.append(listOfMoves.get(i).get(j).getJson()+",");
					}
				}
				json.append("]}");
			}else{
				for(int j =0; j<listOfMoves.get(i).size();j++){
					if(j==listOfMoves.get(i).size()-1){
						json.append(listOfMoves.get(i).get(j).getJson());
					}else{
						json.append(listOfMoves.get(i).get(j).getJson()+",");
					}
				}
				json.append("]},");
			}

		}
		json.append("]}");
		return json.toString();
	}
	/**
	 * add move to list of moves(outer list)
	 * @param snapshotsForMove
	 * @param botId
	 */
	public void addMoveSnapshot(final List<Snapshot> snapshotsForMove, final String botId,final String gameId){
		if(manager.containsKey(gameId)){
			if(manager.get(gameId).containsKey(botId)){
				manager.get(gameId).get(botId).add(snapshotsForMove);
			}else{
				List<List<Snapshot>> listOfMove =new ArrayList<>();
				listOfMove.add(snapshotsForMove);
				manager.get(gameId).put(botId, listOfMove);
			}
		}else{
			Map<String,List<List<Snapshot>>> bots =new HashMap<String,List<List<Snapshot>>>();
			manager.put(gameId, bots);
			List<List<Snapshot>> listOfMove =new ArrayList<>();
			listOfMove.add(snapshotsForMove);
			manager.get(gameId).put(botId, listOfMove);
		}
		
	}
	/**
	 * add snapshot to a move(inner list)
	 * @param botId
	 * @param snapshot
	 */

	public void addSnapshot(final String gameId,final String botId,final Snapshot snapshot){
		int iteration =manager.get(gameId).get(botId).size();
			//add to MoveSnapshot
		manager.get(gameId).get(botId).get(iteration-1).add(snapshot);
	}
	
	/**
	 * get a snapshot from list of moves
	 * @param botId
	 * @param lineNumber
	 * @return snapshot
	 */
	public Snapshot getSnapshot(final String gameId,final String botId,final int lineNumber){
		
		int iteration =manager.get(gameId).get(botId).size();
		for(Snapshot s :manager.get(gameId).get(botId).get(iteration-1)){
	    	if(s.getLineNumber() == lineNumber){
	    		return s;
	    	}
	    }
		return null;
	}
	
	/**
	 * get manager
	 * @return
	 */
	public ConcurrentMap<String, Map<String,List<List<Snapshot>>>> getManager() {
		return manager;
	}
}
