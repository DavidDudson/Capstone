package soGaCoGameTemplate;

import nz.ac.massey.cs.ig.core.game.GameFactory;
import nz.ac.massey.cs.ig.core.game.model.BotData;
import nz.ac.massey.cs.ig.core.services.Serializer;
import nz.ac.massey.cs.ig.core.services.defaults.DefaultGameSupport;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class BattleshipsGameSupport extends DefaultGameSupport {


    public BattleshipsGameSupport(String name) {
        super(name);
    }

    @Override
    protected List<BotData> loadBuiltInBots() {
        return null;
    }

    @Override
    public Collection<Class<?>> getWhitelistedClasses() {
        return null;
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
        return BattleshipGame::new;
    }

    /**
     * Genrates a serializer instance for tranporting the complete game to the client
     *
     * @return The serializer instance
     */
    @Override
    public Serializer getSerializer() {
        return new BattleshipsSerializer();
    }

    /**
     * Support only Java and the visual language
     *
     * @param languageId the language name to query
     * @return whether or not the language is supported
     */
    @Override
    public boolean isLanguageSupported(String languageId) {
        return Objects.equals(languageId, "java");
    }
}
