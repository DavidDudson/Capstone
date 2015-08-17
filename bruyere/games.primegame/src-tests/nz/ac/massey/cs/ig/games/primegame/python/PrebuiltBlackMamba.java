package nz.ac.massey.cs.ig.games.primegame.python;
import java.util.List;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import nz.ac.massey.cs.ig.games.primegame.PGBot;

public class PrebuiltBlackMamba  extends PGBot {

		public PrebuiltBlackMamba(String botId) {
	    	super(botId);
		}

		/**
		 * MIN_NR_OF_MOVES_TO_CONSIDER - we at least want to evaluate so many top
		 * moves (even if the percentage - e.g. 10% - as calculated from the
		 * availableNumbers is lower than this number)
		 */
		private final int MIN_NR_OF_MOVES_TO_CONSIDER = 10;

		/**
		 * convertIntegerSetToArray
		 * 
		 * @param set
		 * @return
		 */
		private int[] convertIntegerSetToArray(Set<Integer> set) {
			int[] array = new int[set.size()];
			int i = 0;
			for (int number : set)
				array[i++] = number;
			return array;
		}

		/**
		 * @param n
		 *            index within numbers
		 * @param numbers
		 *            available numbers
		 * @return
		 */
		private int sumFactors(int n, int[] numbers) {
			int sum = 0;
			for (int i = 0; i < n; i++) {
				if (numbers[n] % numbers[i] == 0) {
					sum += numbers[i];
				}
			}
			return sum;
		}

		/**
		 * Move: This structure holds information about a particular move and its
		 * local gain
		 * 
		 * @author <a href="mailto:jf@jensfendler.com">Jens Fendler</a>
		 */
		class Move implements Comparable<Move> {
			/**
			 * move - the Move that was made
			 */
			private int move;

			/**
			 * gain - the local payoff
			 */
			private int gain;

			/**
			 * Move Constructor
			 * 
			 * @param move
			 *            - the move
			 * @param gain
			 *            - the local payoff
			 */
			private Move(int move, int gain) {
				this.move = move;
				this.gain = gain;
			}

			/**
			 * @see java.lang.Comparable#compareTo(java.lang.Object)
			 */
			public int compareTo(Move o) {
				return o.gain - this.gain;
			}	
		}

		/**
		 * Implement this method to select a number from the list.
		 * You can assume that the list is not empty,
		 * and that the list is in natural order.
		 */
		@Override
		public Integer nextMove(List<Integer> game) {
		    Set<Integer> availableNumbers = new TreeSet<Integer>();
		    availableNumbers.addAll(game);

			// determine the size for the set of best moves to look into..
			// here we take MIN_NR_OF_MOVES_TO_CONSIDER, or 10% of the board size,
			// whichever is more
			int maxNrOfMoves = Math.max(MIN_NR_OF_MOVES_TO_CONSIDER, (int) (availableNumbers.size() * 0.1));

			// get set of best (locally optimised) moves
			TreeSet<Move> topMoves = getTopMoves(availableNumbers, maxNrOfMoves);

			// select the best overall move
			Integer move = selectMove(topMoves, availableNumbers, maxNrOfMoves);

			// return the move
			return move;
		}

		/**
		 * selectMove
		 * 
		 * @param moves
		 *            set of moves to consider by evaluating the next level
		 * @param availableNumbers
		 *            set of integer numbers still on the board
		 * @param maxNrOfLevel2Moves
		 *            max. number of the best next-level (counter-) moves to
		 *            evaluate
		 * @return an integer from the availableNumbers set which is the move we
		 *         make
		 */
		private Integer selectMove(TreeSet<Move> moves, Set<Integer> availableNumbers, int maxNrOfLevel2Moves) {
			Iterator<Move> it = moves.iterator();
			int level1MovesToEvaluate = 10;
			int bestScore = Integer.MIN_VALUE;
			Move bestMove = moves.iterator().next();
			for (int i = 0; it.hasNext() && i < level1MovesToEvaluate; i++) {
				Move m = it.next();
				Set<Integer> numbersLeft = computeFollowingBoard(availableNumbers, m.move);
				TreeSet<Move> counterMoves = getTopMoves(numbersLeft, maxNrOfLevel2Moves);
				// calculate the score over both levels (move and possible counter
				// move)
				if (counterMoves.size() > 0) {
					int level2Score = m.gain - counterMoves.iterator().next().gain;
					if (level2Score > bestScore) {
						bestMove = m;
						bestScore = level2Score;
					}
				}
			}
			return bestMove.move;
		}

		/**
		 * computeFollowingBoard - returns a Set of numbers which remain on the
		 * board after a given move was made on the given board
		 * 
		 * @param currentBoard
		 *            - numbers still available on the board before the move
		 * @param move
		 *            - the move to be made
		 * @return Set of numbers remaining on the board
		 */
		private Set<Integer> computeFollowingBoard(Set<Integer> currentBoard, int move) {
			Set<Integer> newNumbers = new TreeSet<Integer>(currentBoard);
			newNumbers.remove(move);
			for (Iterator<Integer> it = newNumbers.iterator(); it.hasNext();) {
				int n = it.next();
				if (n > Math.ceil(move / 2)) {
					break;
				}
				if (move % n == 0) {
					it.remove();
				}
			}
			return newNumbers;
		}

		/**
		 * getTopMoves - this method performs a optimisation by local payoff and
		 * returns the best up to maxNrOfMoves moves as a Set
		 * 
		 * @param availableNumbers
		 *            - the current board
		 * @param maxNrOfMoves
		 *            - max number of moves to return
		 * @return Set of Move objects representing the (locally) best moves
		 */
		private TreeSet<Move> getTopMoves(Set<Integer> availableNumbers, int maxNrOfMoves) {
			TreeSet<Move> topMoves = new TreeSet<Move>();
			int[] numbers = convertIntegerSetToArray(availableNumbers);
			for (int i = numbers.length - 1; i >= 0; i--) {
				int localScore = numbers[i] - sumFactors(i, numbers);
				Move move = new Move(numbers[i], localScore);
				// add the move to the queue (which remains sorted)
				topMoves.add(move);
				// only keep maxNrOfMoves in queue
				if (topMoves.size() > maxNrOfMoves) {
					topMoves.remove(topMoves.last());
				}
				// to improve performance, we stop searching when we reach the
				// low-end of the board which contains only numbers which are less
				// than or equal to the least gain found so far
				if (numbers[i] <= topMoves.last().gain) {
					break;
				}

				// TODO: determine distribution of taken numbers on board and opt to
				// evaluate only the high third of numbers left
			}
			return topMoves;
		}
	}
