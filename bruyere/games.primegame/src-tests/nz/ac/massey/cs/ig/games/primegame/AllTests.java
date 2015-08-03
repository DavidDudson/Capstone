
package nz.ac.massey.cs.ig.games.primegame;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Super test suite.
 * @author jens dietrich
 */
@RunWith(Suite.class)
@SuiteClasses({
    BotAgainstBotTests.class,
    PrimeGameRuleTests.class
})
public class AllTests {
}
