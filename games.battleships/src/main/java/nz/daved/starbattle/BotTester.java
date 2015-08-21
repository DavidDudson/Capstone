package nz.daved.starbattle;

import nz.daved.starbattle.bots.FirstSquareBot;

/**
 * Created by David J. Dudson on 13/08/15.
 * <p>
 * Main class to test bots
 */
public class BotTester {

    public static void main(String[] args) {
        StarBattleGame game = new StarBattleGame("test", new FirstSquareBot("1"), new FirstSquareBot("2"));
        game.runTestGame();
        game.getHistory().forEach(x -> System.out.println(x.toString()));
    }
}