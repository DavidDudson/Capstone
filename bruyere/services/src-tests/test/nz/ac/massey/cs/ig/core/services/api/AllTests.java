
package test.nz.ac.massey.cs.ig.core.services.api;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Super test suite.
 * @author jens dietrich
 */
@RunWith(Suite.class)
@SuiteClasses({
	PlayGameTests.class,
	ServiceTests.class
})
public class AllTests {
}
