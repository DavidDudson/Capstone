package nz.daved.starbattle;

import nz.daved.starbattle.bots.FirstSquareBot;
        import nz.daved.starbattle.bots.LastSquareBot;
        import nz.daved.starbattle.bots.blocklyBot;

        import java.io.File;
        import java.io.IOException;
        import java.io.PrintWriter;

/**
 * Created by David J. Dudson on 13/08/15.
 * <p>
 * Main class to test bots
 */
public class BotTester {

    public static void main(String[] args) throws IOException {
        StarBattleGame game = new StarBattleGame("test", new blocklyBot("1"), new LastSquareBot("2"));
        game.runTestGame();
        game.getHistory().forEach(x -> System.out.println(x.toString()));
        StarBattleSerializer serializer = new StarBattleSerializer();
        serializer.encodeGame(game, new PrintWriter(new File("bob.tx")));

    }
}
