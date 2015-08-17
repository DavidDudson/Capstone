
package test.nz.ac.massey.cs.ig.languages.java.compiler.defaults;



import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.nz.ac.massey.cs.ig.languages.java.compiler.JavaBuilderTests;
import test.nz.ac.massey.cs.ig.languages.java.compiler.SourceUtilTests;

/**
 * Super test suite.
 * @author jens dietrich
 */
@RunWith(Suite.class)
@SuiteClasses({
    JavaBuilderTests.class,
    DefaultStaticVerifierTests.class,
    SourceUtilTests.class
})
public class AllTests {
}
