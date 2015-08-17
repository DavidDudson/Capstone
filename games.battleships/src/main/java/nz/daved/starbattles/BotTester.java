package nz.daved.starbattles;

import nz.daved.starbattles.bots.FirstSquareBot;

/**
 * Created by David J. Dudson on 13/08/15.
 * <p>
 * Main class to test bots
 */
public class BotTester {

    public static void main(String[] args) {
        StarBattleGame game = new StarBattleGame("test", new FirstSquareBot(), new FirstSquareBot());
        game.runTestGame();
        game.getHistory().forEach(x -> System.out.println(x.toString()));
    }
}
