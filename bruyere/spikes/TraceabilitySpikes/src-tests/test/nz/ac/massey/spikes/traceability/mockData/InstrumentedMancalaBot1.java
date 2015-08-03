package test.nz.ac.massey.spikes.traceability.mockData;

import nz.ac.massey.cs.ig.games.mancala.Mancala;
import nz.ac.massey.cs.ig.games.mancala.MancalaBot;

/**
 * This is the starting point to implement your own bot.
 * @author jens dietrich
 */

public class InstrumentedMancalaBot1 extends MancalaBot {

	public InstrumentedMancalaBot1(String botId) {
		super(botId);
	}

	/**
	 * Implement this method to pit. TODO: link to documentation of Mancala.
	 */
	@Override
	 public Integer nextMove(Mancala board) {
		nz.ac.massey.spikes.traceability.tracer.Tracer.increaseIterationCounter("mancala2","mancala1");
		nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala1",21, "board" ,board);
		nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala1",21, "this" ,this);
		// pick one that gives us another move
		for (int i=0;i<6;i++) {
			if (willGetBonusMove(board,i)) return i;
		}
		// pick one to get stones from opposite field
		for (int i=0;i<6;i++) {
			if (willGetOppositeStones(board,i)) return i;
		}
		// play last one possible
		for (int i=5;i>-1;i--) {
			if (board.getStonesInMyPit(i)>0) return i;
		}
	 	return 0; 	 
	 }
	 
	 private boolean willGetBonusMove(Mancala board,int move) {
	     // TODO: check http://127.0.0.1:8084/SoGaCo-Web/test.jspf move "comes around"
	     return move + board.getStonesInMyPit(move)==6;
	 }
	 
	 private boolean willGetOppositeStones(Mancala board,int move) {
			nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala1",47, "board" ,board);
			nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala1",47, "this" ,this);
			nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala1",47, "move" ,move);
			nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala1",47, "this" ,this);
	    int stones = board.getStonesInMyPit(move);
		nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala1",50, "stones" ,stones);
		nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala1",50, "this" ,this);
		nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala1",50, "board" ,board);
		nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala1",50, "this" ,this);
	    if (stones>0 && stones+move<6) {
	        int targetPit = stones+move;
			nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala1",55, "targetPit" ,targetPit);
			nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala1",55, "this" ,this);
	        // only now move will end in one of my pits
	        int opposite = 5-targetPit;
			nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala1",60, "opposite" ,opposite);
			nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala1",60, "this" ,this);
	        int otherStones = board.getStonesInOtherPit(opposite);
			nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala1",63, "otherStones" ,otherStones);
			nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("mancala2","mancala1",63, "this" ,this);
	        if (otherStones>0) return true;
	    } 
	    return false;
	 }
	 
};

