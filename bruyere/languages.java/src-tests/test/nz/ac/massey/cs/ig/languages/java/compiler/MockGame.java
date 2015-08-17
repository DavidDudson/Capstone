
package test.nz.ac.massey.cs.ig.languages.java.compiler;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;

/**
 * Dummy game
 * @author jens dietrich
 */
public class MockGame implements Game<MockMove,Object>{

    @Override
    public String getId() {
        return null;
    }

	@Override
	public MockMove getPublicState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bot<MockMove, Object> getPlayer1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bot<MockMove, Object> getPlayer2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameState move() throws IllegalMoveException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bot<MockMove, Object> getCurrentPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Throwable getError() {
		// TODO Auto-generated method stub
		return null;
	}
}
