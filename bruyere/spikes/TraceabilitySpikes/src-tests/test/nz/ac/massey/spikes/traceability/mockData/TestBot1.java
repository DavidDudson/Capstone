package test.nz.ac.massey.spikes.traceability.mockData;
import java.util.List;


/**
 * @auther:Li Sui
 * a example class for instrumentation. test purpose only
 */
public class TestBot1{
	
	private String botid;
	
	public TestBot1(String botId) {	
		this.botid=botId;
	}
	public Integer nextMove(List<Integer> game) {
		nz.ac.massey.spikes.traceability.tracer.Tracer.increaseIterationCounter("game",botid);
		nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("game",botid,9, "game" ,game);
		nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("game",botid,9, "this" ,this);

		int size =
		game.size();
		nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("game",botid,10, "size" ,size);
		nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("game",botid,10, "this" ,this);

		return game.get(size-1);
	}
}
