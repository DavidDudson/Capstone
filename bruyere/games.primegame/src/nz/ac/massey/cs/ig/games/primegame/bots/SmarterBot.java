package nz.ac.massey.cs.ig.games.primegame.bots;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nz.ac.massey.cs.ig.games.primegame.PGBot;

public class SmarterBot extends PGBot {

	public SmarterBot() {
		super(SmarterBot.class.getName());
	}

	@Override
	public Integer nextMove(List<Integer> game) {
		// copy to set for faster lookup
		Set<Integer> numbers = new HashSet<Integer>();
		numbers.addAll(game);
		
		int selection = -1;
		int maxGain = Integer.MIN_VALUE;
		
		for (int n:game) {
			int gain = n;
			for (int i=1;2*i<n;i++) {
				if(n%i==0 && numbers.contains(i)) {
					// is factor
					gain = gain - i;
				}
			}
			if (gain>maxGain) {
				selection = n;
				maxGain = gain;
			}
		}

		return selection;
	}

}
