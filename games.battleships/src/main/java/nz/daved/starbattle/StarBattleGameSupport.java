package nz.daved.starbattle;

import nz.ac.massey.cs.ig.core.game.GameFactory;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.Serializer;
import nz.ac.massey.cs.ig.core.services.defaults.DefaultGameSupport;
import nz.ac.massey.cs.ig.core.utils.ResourceUtils;
import nz.daved.starbattle.game.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StarBattleGameSupport extends DefaultGameSupport {


    public StarBattleGameSupport() {
        super("StarBattle");
    }

    @Override
    protected List<BotData> loadBuiltInBots() {
        String[] files = new String[]{"FirstSquareBot.src", "LastSquareBot.src"};
        List<BotData> builtInBots = new ArrayList<>();
        for (String file : files) {
            String id = file.substring(0, file.indexOf("."));

            BotData botData = new BotData(id);
            botData.setName(id);
            botData.setLanguage("JAVA");
            botData.setCompilable(true);
            botData.setTested(true);
            botData.setqName("builtinbots." + id);

            String src = ResourceUtils.loadFromClassPath(this, "resources/builtinbots/" + file);

            botData.setSrc(src);

            builtInBots.add(botData);
        }
        return builtInBots;
    }

    @Override
    public Collection<Class<?>> getWhitelistedClasses() {
        ArrayList<Class<?>> clazzes = new ArrayList<Class<?>>();
        clazzes.add(StarBattleGameSchematic.class);
        clazzes.add(StarBattleGameMove.class);
        clazzes.add(StarBattleGame.class);
        clazzes.add(StarBattleBot.class);
        clazzes.add(StarBattleSerializer.class);
        clazzes.add(ShipGameBoard.class);
        clazzes.add(BotGameBoard.class);
        clazzes.add(Coordinate.class);
        clazzes.add(GameBoard.class);
        clazzes.add(Ship.class);

        return clazzes;
    }

    @Override
    public Class<?> getTestClass() {
        return null;
    }

    /**
     * Generates an instance of the battleship game factory to create games with
     *
     * @return the created instance
     */
    @Override
    public GameFactory getGameFactory() {
        return StarBattleGame::new;
    }

    /**
     * Genrates a serializer instance for tranporting the complete game to the client
     *
     * @return The serializer instance
     */
    @Override
    public Serializer getSerializer() {
        return new StarBattleSerializer();
    }
}
