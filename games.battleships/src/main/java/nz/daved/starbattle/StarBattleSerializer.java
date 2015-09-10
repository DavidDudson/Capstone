package nz.daved.starbattle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nz.ac.massey.cs.ig.core.game.Game;
import nz.ac.massey.cs.ig.core.services.Serializer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * Serialization Class of Battleships Game
 */
public class StarBattleSerializer implements Serializer {
    @Override
    public void encodeGame(Game<?, ?> _game, PrintWriter out) throws IOException {
        assert _game instanceof StarBattleGame;

        StarBattleGame game = (StarBattleGame) _game;
        List<StarBattleGameMove> history = game.getHistory();
        
        //generate json objects

        Gson gson = new GsonBuilder().create();
        JsonArray retJArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();

        //convert moves to json object

        history.stream().forEach(move -> retJArray.add(new Gson().toJsonTree(move)));

        //submit to printwriter

        jsonObject.add("moves", retJArray);
        gson.toJson(jsonObject, out);

        if (game.getError() != null) {
            Throwable error = game.getError();
            gson.toJson(error.getClass().getName(), out);
            gson.toJson(error.getMessage(), out);
            gson.toJson(error.getStackTrace(), out);
        }
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
