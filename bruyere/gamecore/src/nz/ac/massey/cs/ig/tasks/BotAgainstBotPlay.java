package nz.ac.massey.cs.ig.tasks;

import java.io.Closeable;
import java.util.concurrent.Callable;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.game.GameState;

/**
 * Bot against bot game. Can be executed in a sandbox using futures (timeouts,
 * catch stack overflows).
 * 
 * @author jens dietrich
 * @param G
 *            the game class
 */
public class BotAgainstBotPlay implements Callable<Game<?, ?>> {

	private Game<?, ?> game = null;

	public BotAgainstBotPlay(Game<?, ?> game) {
		super();
		this.game = game;
	}

	@Override
	public Game<?, ?> call() throws Exception {

		try {
			GameState state = game.getState();
			assert state == GameState.NEW;

			while (!state.isFinished()) {
				state = game.move();
			}
			assert state.isFinished();

			return game;
		} catch (Exception e) {
			throw e;
		} finally {
			Bot<?, ?> bot = game.getPlayer1();
			if(bot instanceof Closeable) {
				((Closeable) bot).close();
			}
			bot = game.getPlayer2();
			if(bot instanceof Closeable) {
				((Closeable) bot).close();
			}
		}
	}

}
