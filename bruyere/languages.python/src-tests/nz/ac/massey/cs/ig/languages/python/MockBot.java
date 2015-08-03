package nz.ac.massey.cs.ig.languages.python;

import nz.ac.massey.cs.ig.core.game.Bot;

/**
 * Dummy bot.
 * @author jens dietrich
 */
public class MockBot implements Bot<MockGame,MockMove> {

	@Override
	public String getId() {
		return null;
	}

	@Override
	public MockMove nextMove(MockGame game) {
		return null;
	}
	
	
	// these are methods used in test cases to test dynamic, junit-based, verification.
	public boolean returnTrue() {
		return true;
	}

	public void throwIllegalStateException() {
		throw new IllegalStateException();
	}
	
	public void triggerTimeout100ms() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
	}
	
}
