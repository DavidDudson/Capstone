package soGaCoGameTemplate;

import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.services.Serializer;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by David J. Dudson on 4/08/15.
 *
 * Serialization Class of Battleships Game
 */
public class BattleshipsSerializer implements Serializer {
    @Override
    public void encodeGame(Game<?, ?> game, PrintWriter printWriter) throws IOException {

    }

    @Override
    public String getGameContentType() {
        return "application/json";
    }

    @Override
    public String encodeGameCompact(Game<?, ?> game) {
        return null;
    }

    @Override
    public boolean supportsCompactGameEncoding() {
        return false;
    }
}
