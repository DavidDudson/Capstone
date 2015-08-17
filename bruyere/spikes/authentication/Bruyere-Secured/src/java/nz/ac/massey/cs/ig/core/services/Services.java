
package nz.ac.massey.cs.ig.core.services;

import java.util.List;
import java.util.concurrent.ExecutorService;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.Game;

/**
 * Factory used to instantiate the various service classes.
 * If services classes are stateless, they can be cached here.
 * @author jens dietrich
 * @param <B> the bot type
 * @param <G> the game type 
 * @param <M> the move type
 * @param <PS> the type to encode the public game state
 */
public interface Services<B extends Bot<?,?>,G extends Game<M,PS>, M,PS> {
    Builder<B> getBuilder(String languageName);
    String[] getLanguages();
    StaticASTVerifier getASTVerifier();
    StaticByteCodeVerifier getStaticVerifier();
    DynamicVerifier<B> getDynamicVerifier();
    Storage getStorage();
    Serializer<G> getSerializer();
    UIDGenerator getUIDGenerator();
    GameFactory<G,B,M,PS> getGameFactory();
    ExecutorService getExecutorServiceForGames();
    TemplateFactory getTemplateFactory();
    
    // settings
    long getBotAgainstBotGameTimeouts();
    
    
}
