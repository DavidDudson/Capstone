package test.nz.ac.massey.spikes.traceability.mockData;

import java.util.List;

import nz.ac.massey.cs.ig.games.othello.EasyOthelloBoard;
import nz.ac.massey.cs.ig.games.othello.OthelloBot;
import nz.ac.massey.cs.ig.games.othello.OthelloPosition;

public class InstrumentedOthelloBot2 extends OthelloBot {
	
	public InstrumentedOthelloBot2(String id) {
		super(id);
	}
	
	@Override
	public OthelloPosition nextMove(EasyOthelloBoard game) {
nz.ac.massey.spikes.traceability.tracer.Tracer.increaseIterationCounter("othello2","bot2");
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",16,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",16,"this" ,this);
		int maxRound = game.getFieldSize() * game.getFieldSize();
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",17,"maxRound" ,maxRound);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",17,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",17,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",17,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",17,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",17,"this" ,this);
		int currentRound = game.getBlankFields() - maxRound + 4;
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",18,"currentRound" ,currentRound);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",18,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",18,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",18,"this" ,this);

		// calculate best move by evaluation of all possible moves
		// for 66 % of the game
		if (currentRound < 2.0 * maxRound / 3.0) {
			return getBestMoveByEvaluation(game);
		} else {
			// just get best move after it
			return getMoveWithHighestScoreDelta(game.getAvailableMovesForMe(), game);
		}
	}
	
	private OthelloPosition getBestMoveByEvaluation(EasyOthelloBoard game) {
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",30,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",30,"this" ,this);
		List<OthelloPosition> moves = game.getAvailableMovesForMe();
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",31,"moves" ,moves);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",31,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",31,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",31,"this" ,this);

		OthelloPosition bestMove = null;
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",33,"bestMove" ,bestMove);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",33,"this" ,this);
		int value = Integer.MIN_VALUE;
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",34,"value" ,value);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",34,"this" ,this);
		
		for (OthelloPosition move : moves) {
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",36,"move" ,move);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",36,"this" ,this);
			int val = calculateMoveValue(game, move);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",37,"val" ,val);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",37,"this" ,this);
			
			// subtract best enemy move
			EasyOthelloBoard board = game.copy();
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",40,"board" ,board);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",40,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",40,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",40,"this" ,this);
			board.makeMove(move);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",41,"board" ,board);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",41,"this" ,this);
			board.switchPlayerPerspective();
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",42,"board" ,board);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",42,"this" ,this);
			int bestEnemyValue = Integer.MIN_VALUE;
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",43,"bestEnemyValue" ,bestEnemyValue);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",43,"this" ,this);
			for(OthelloPosition enemyMove : board.getAvailableMovesForMe()) {
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",44,"enemyMove" ,enemyMove);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",44,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",44,"board" ,board);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",44,"this" ,this);
				int enemyValue = calculateMoveValue(board, enemyMove);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",45,"enemyValue" ,enemyValue);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",45,"this" ,this);
				if(enemyValue > bestEnemyValue) {
					bestEnemyValue = enemyValue;
				}
			}			
			val = val - bestEnemyValue;
			
			// if move is better than current remember it
			if(val>value) {
				bestMove = move;
				value = val;
			}
		}
		
		// return best found move
		return bestMove;
	}
	
	private int calculateMoveValue(EasyOthelloBoard game, OthelloPosition move) {
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",63,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",63,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",63,"move" ,move);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",63,"this" ,this);
		int fieldSize = game.getFieldSize();
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",64,"fieldSize" ,fieldSize);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",64,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",64,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",64,"this" ,this);
		int maxPoints = fieldSize * fieldSize;
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",65,"maxPoints" ,maxPoints);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",65,"this" ,this);

		if (game.isOnEdge(move)) {

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
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",84,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",84,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",84,"move" ,move);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",84,"this" ,this);
		int score = game.getScoreOfMe();
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",85,"score" ,score);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",85,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",85,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",85,"this" ,this);
		
		EasyOthelloBoard cloned = game.copy();
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",87,"cloned" ,cloned);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",87,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",87,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",87,"this" ,this);
		cloned.makeMove(move);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",88,"cloned" ,cloned);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",88,"this" ,this);

		return cloned.getScoreOfMe() - score;
	}

	public OthelloPosition getMoveWithHighestScoreDelta(
			List<OthelloPosition> moves, EasyOthelloBoard game) {
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",94,"moves" ,moves);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",94,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",94,"othello2" ,game);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",94,"this" ,this);
		OthelloPosition bestMove = moves.get(0);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",95,"bestMove" ,bestMove);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",95,"this" ,this);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",95,"moves" ,moves);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",95,"this" ,this);
		int bestScore = 0;
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",96,"bestScore" ,bestScore);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",96,"this" ,this);

		for (OthelloPosition move : moves) {
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",98,"move" ,move);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",98,"this" ,this);
			int val = scoreDifference(game, move);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",99,"val" ,val);
nz.ac.massey.spikes.traceability.tracer.Tracer.addTrace("othello2","bot2",99,"this" ,this);
			
			if (val > bestScore) {
				bestMove = move;
				bestScore = val;
			}
		}

		return bestMove;
	}
	

}

