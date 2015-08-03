package nz.ac.massey.cs.ig.core.services.build.verifier.junit;

import java.util.List;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.services.build.BotFactory;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * Special junit runner. Test cases must have a constructor TestCase(Bot b) to "inject" the bot
 * that is being tested.
 * @author jens dietrich
 * @param <B>
 */

final class EmbeddedJUnitTestRunner extends BlockJUnit4ClassRunner {

	private BotFactory botFactory;
	private Bot<?, ?> botUnderTest;

	public EmbeddedJUnitTestRunner(BotFactory botFactory,Class<?> testClass) throws InitializationError {
		super(testClass);
		this.botFactory = botFactory;
	}
	
    /**
     * Returns a new fixture for running a test. Default implementation executes
     * the test class's no-argument constructor (validation should have ensured
     * one exists).
     */
	@Override
    protected Object createTest() throws Exception {
		botUnderTest = botFactory.createBot();
        return getTestClass().getOnlyConstructor().newInstance(botUnderTest);
    }
	
	public Bot<?, ?> getBotUnderTest() {
		return botUnderTest;
	}
	
    /**
     * Adds to {@code errors} if the test class has more than one constructor,
     * or if the constructor takes parameters. Overriden for specific
     * different validation rules.
     */
	@Override
    protected void validateConstructor(List<Throwable> errors) {
        validateOnlyOneConstructor(errors);
        validateOneArgConstructor(errors);
    }
	
    /**
     * Adds to {@code errors} if the test class's single constructor takes
     * parameters (do not override)
     */
    protected void validateOneArgConstructor(List<Throwable> errors) {
        if (!getTestClass().isANonStaticInnerClass()
                && hasOneConstructor2()
                && (getTestClass().getOnlyConstructor().getParameterTypes().length != 1)) {
            String gripe = "Test class should have exactly one public one-argument constructor (the parameter is the bot instance to be tested)";
            errors.add(new Exception(gripe));
        }
    }
    
    /**
     * This is a copy of the private method hasOneConstructor() from the superclass,
     * renamed to avoid ambiguity.
     * @return
     */
    private boolean hasOneConstructor2() {
        return getTestClass().getJavaClass().getConstructors().length == 1;
    }
}
