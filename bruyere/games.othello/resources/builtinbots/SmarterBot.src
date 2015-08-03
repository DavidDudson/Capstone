package nz.ac.massey.cs.ig.games.othello.bots;

import java.util.List;

import nz.ac.massey.cs.ig.games.othello.EasyOthelloBoard;
import nz.ac.massey.cs.ig.games.othello.OthelloBot;
import nz.ac.massey.cs.ig.games.othello.OthelloPosition;

public class SmarterBot extends OthelloBot {
	
	public SmarterBot(String id) {
		super(id);
	}
	
	@Override
	public OthelloPosition nextMove(EasyOthelloBoard game) {
		int maxRound = game.getFieldSize() * game.getFieldSize();
		int currentRound = game.getBlankFields() - maxRound + 4;

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
		List<OthelloPosition> moves = game.getAvailableMovesForMe();

		OthelloPosition bestMove = null;
		int value = Integer.MIN_VALUE;
		
		for (OthelloPosition move : moves) {
			int val = calculateMoveValue(game, move);
			
			// subtract best enemy move
			EasyOthelloBoard board = game.copy();
			board.makeMove(move);
			board.switchPlayerPerspective();
			int bestEnemyValue = Integer.MIN_VALUE;
			for(OthelloPosition enemyMove : board.getAvailableMovesForMe()) {
				int enemyValue = calculateMoveValue(board, enemyMove);
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
		int fieldSize = game.getFieldSize();
		int maxPoints = fieldSize * fieldSize;

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
		int score = game.getScoreOfMe();
		
		EasyOthelloBoard cloned = game.copy();
		cloned.makeMove(move);

		return cloned.getScoreOfMe() - score;
	}

	public OthelloPosition getMoveWithHighestScoreDelta(
			List<OthelloPosition> moves, EasyOthelloBoard game) {
		OthelloPosition bestMove = moves.get(0);
		int bestScore = 0;

		for (OthelloPosition move : moves) {
			int val = scoreDifference(game, move);
			
			if (val > bestScore) {
				bestMove = move;
				bestScore = val;
			}
		}

		return bestMove;
	}
	

}