package test.nz.ac.massey.spikes.traceability.mockData;

import java.util.List;

import nz.ac.massey.cs.ig.games.othello.EasyOthelloBoard;
import nz.ac.massey.cs.ig.games.othello.OthelloBot;
import nz.ac.massey.cs.ig.games.othello.OthelloPosition;

public class InstrumentedOthelloBot1 extends OthelloBot {
	public InstrumentedOthelloBot1(String id) {
		super(id);
	}

	@Override
	public OthelloPosition nextMove(EasyOthelloBoard game) {
nz.ac.massey.spikes.traceability.tracer.Tracer.increaseIterationCounter("othello2","bot1");
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",15,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",15,"this" ,this);
		int maxRound = game.getFieldSize() * game.getFieldSize();
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",16,"maxRound" ,maxRound);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",16,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",16,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",16,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",16,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",16,"this" ,this);
		int currentRound = game.getBlankFields() - maxRound + 4;
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",17,"currentRound" ,currentRound);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",17,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",17,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",17,"this" ,this);

		if (currentRound < 2.0 * maxRound / 3.0) {
			return getBestMoveByEvaluation(game);
		} else {
			return getMoveWithHighestScoreDelta(game.getAvailableMovesForMe(),
					game);
		}
	}

	private OthelloPosition getBestMoveByEvaluation(EasyOthelloBoard game) {
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",27,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",27,"this" ,this);
		List<OthelloPosition> moves = game.getAvailableMovesForMe();
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",28,"moves" ,moves);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",28,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",28,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",28,"this" ,this);

		OthelloPosition bestMove = null;
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",30,"bestMove" ,bestMove);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",30,"this" ,this);
		int value = Integer.MIN_VALUE;
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",31,"value" ,value);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",31,"this" ,this);

		for (OthelloPosition move : moves) {
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",33,"move" ,move);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",33,"this" ,this);
			int val = calculateMoveValue(game, move);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",34,"val" ,val);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",34,"this" ,this);

			if (val > value) {
				bestMove = move;
				value = val;
			}
		}

		return bestMove;
	}

	private int calculateMoveValue(EasyOthelloBoard game, OthelloPosition move) {
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",45,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",45,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",45,"move" ,move);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",45,"this" ,this);
		int fieldSize = game.getFieldSize();
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",46,"fieldSize" ,fieldSize);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",46,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",46,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",46,"this" ,this);
		int maxPoints = fieldSize * fieldSize;
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",47,"maxPoints" ,maxPoints);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",47,"this" ,this);

		if (game.isOnEdge(move)) {
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",49,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",49,"this" ,this);
			return maxPoints;
		}

		if ((game.calcDistToBorder(move.getX()) == 2 && game.isOnBorder(move.getY()))

			|| (game.calcDistToBorder(move.getY()) == 2 && game.isOnBorder(move.getX()))) {

				return maxPoints / 2;
		}

		if (game.calcDistToBorder(move.getX()) == 2

				&& game.calcDistToBorder(move.getY()) == 2) {

			return maxPoints / 3;
		}

		return scoreDifference(game, move);
	}

	private int scoreDifference(EasyOthelloBoard game, OthelloPosition move) {
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",66,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",66,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",66,"move" ,move);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",66,"this" ,this);
		int score = game.getScoreOfMe();
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",67,"score" ,score);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",67,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",67,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",67,"this" ,this);
		
		EasyOthelloBoard cloned = game.copy();
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",69,"cloned" ,cloned);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",69,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",69,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",69,"this" ,this);
		cloned.makeMove(move);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",70,"cloned" ,cloned);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",70,"this" ,this);

		return cloned.getScoreOfMe() - score;
	}

	public OthelloPosition getMoveWithHighestScoreDelta(
			List<OthelloPosition> moves, EasyOthelloBoard game) {
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",76,"moves" ,moves);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",76,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",76,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",76,"this" ,this);
		OthelloPosition bestMove = moves.get(0);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",77,"bestMove" ,bestMove);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",77,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",77,"moves" ,moves);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",77,"this" ,this);
		int bestScore = 0;
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",78,"bestScore" ,bestScore);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",78,"this" ,this);

		for (OthelloPosition move : moves) {
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",80,"move" ,move);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",80,"this" ,this);
			int val = scoreDifference(game, move);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",81,"val" ,val);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot1",81,"this" ,this);
			
			if (val > bestScore) {
				bestMove = move;
				bestScore = val;
			}
		}

		return bestMove;
	}

}

