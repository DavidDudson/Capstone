
package test.nz.ac.massey.cs.ig.core.services;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import nz.ac.massey.cs.ig.core.services.UIDGenerator;

import org.junit.Test;

/**
 * Abstract tests for the UID generator.
 * To reuse these tests, subclass this class and instantiate uidGenerator
 * in @Before
 * @author jens dietrich
 */
public abstract class UIDGeneratorTests {
	public static final String USER = "dummyuser";
    protected static final int MAX_ITERATIONS = 100000;
    protected static UIDGenerator uidGenerator = null;
    
    public UIDGeneratorTests() {
    }
    
    
    @Test
    public void testUniqueness() {
        Set<String> ids = new HashSet<String>();
        for (int i=0;i<MAX_ITERATIONS;i++) {
            assertTrue(ids.add(uidGenerator.nextUID(USER)));
        }
    }
    
    @Test
    public void testValidJavaIdentifiers() {
        for (int i=0;i<MAX_ITERATIONS;i++) {
            String id = uidGenerator.nextUID(USER);
            assertTrue("Invalid start character in id " + id,Character.isJavaIdentifierStart(id.charAt(0)));
            for (int j=1;j<id.length();j++) {
                assertTrue("Invalid character at position " + j +  " in id " + id,Character.isJavaIdentifierPart(id.charAt(0)));
            }
        }
    }
    
    // this test is probably to strong, but it does ensure that ids can directly be used 
    // as file names
    @Test
    public void testDangerousChars() {
        for (int i=0;i<MAX_ITERATIONS;i++) {
            String id = uidGenerator.nextUID(USER);
            for (int j=0;j<id.length();j++) {
                char c = id.charAt(j);
                assertTrue("Invalid character at position " + j +  " in id " + id,Character.isLetterOrDigit(c) || c=='_' || c=='#');
            }
        }
    }
    
    // intention: 3 char strings like "com1","prn" are invalid file names on windows
    @Test
    public void testIdLengths() {
        for (int i=0;i<MAX_ITERATIONS;i++) {
            String id = uidGenerator.nextUID(USER);
            assertTrue("id to short " + id,id.length()>3);
        }
    }
    
    

}
