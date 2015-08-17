
package test.nz.ac.massey.cs.ig.core.services.defaults;

import test.nz.ac.massey.cs.ig.core.services.UIDGeneratorTests;
import nz.ac.massey.cs.ig.core.services.defaults.DefaultUIDGenerator;
import org.junit.After;
import org.junit.Before;

/**
 * Tests the default UID generator.
 * @author jens dietrich
 */
public class DefaultUIDGeneratorTests extends UIDGeneratorTests {
    
    public DefaultUIDGeneratorTests() {
        super();
    }

    @Before
    public void setUp() {
        uidGenerator = new DefaultUIDGenerator();
    }
    
    @After
    public void tearDown() {
        uidGenerator = null;
    }
    
}
