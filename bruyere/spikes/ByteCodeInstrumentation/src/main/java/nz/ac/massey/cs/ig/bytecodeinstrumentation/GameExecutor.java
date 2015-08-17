package nz.ac.massey.cs.ig.bytecodeinstrumentation;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import nz.ac.massey.cs.ig.bytecodeinstrumentation.instrumentation.Instrumentable;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.games.primegame.PGBot;
import nz.ac.massey.cs.ig.games.primegame.PGGame;

public class GameExecutor implements Runnable {

	private Class<?> bot1Class;
	private Class<?> bot2Class;
	
	public GameExecutor(Class<?> bot1Class, Class<?> bot2Class) {
		this.bot1Class = bot1Class;
		this.bot2Class = bot2Class;
	}
	
	public void run() {
		try {
			play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() throws Exception {
		Object player1 = bot1Class.getConstructors()[0]
				.newInstance("bot1");
		Object player2 = bot2Class.getConstructors()[0]
				.newInstance("bot2");
		if (player2 instanceof Instrumentable)
			((Instrumentable) player2)._initialize();

		PGGame game = new PGGame("-", (PGBot) player1,
				(PGBot) player2);

		GameState state = game.getState();
		long player1Duration = 0;
		long player2Duration = 0;
		long moveStartTime = 0;
		long moveDuration = 0;
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		while (!state.isFinished()) {
			boolean isPlayer1 = game.getCurrentPlayer().equals(
					game.getPlayer1());
			moveStartTime = bean.getCurrentThreadCpuTime();
			state = game.move();
			moveDuration = bean.getCurrentThreadCpuTime() - moveStartTime;
			if (isPlayer1) {
				player1Duration += moveDuration;
			} else {
				player2Duration += moveDuration;
			}
		}
		//System.out.println(game.getState());
		//System.out.println("Player 1 : " + player1Duration);
		System.out.println(player2Duration);
		if(player2 instanceof Instrumentable) {
			//System.out.println(((Instrumentable) player2).getObserver().getInstantiationCounter());
			//System.out.println(((Instrumentable) player2).getObserver().getAllocatedBytes());
		}
	}

}
