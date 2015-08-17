package test.nz.ac.massey.cs.ig.languages.java.compiler.defaults;

import java.util.Arrays;

import nz.ac.massey.cs.ig.languages.java.verifier.DefaultByteCodeVerifier;

import org.junit.After;
import org.junit.Before;

import test.nz.ac.massey.cs.ig.languages.java.compiler.MockGame;
import test.nz.ac.massey.cs.ig.languages.java.compiler.MockMove;
import test.nz.ac.massey.cs.ig.languages.java.compiler.StaticVerifierTests;

/**
 * Default tests for static verifier. Check whether references to some critical
 * APIs are prevented. Uses the default builder.
 * 
 * @author jens dietrich
 */
public class DefaultStaticVerifierTests extends StaticVerifierTests {

	public DefaultStaticVerifierTests() {
		super();
	}

	@Before
	public void setUp() {
		sverifier = new DefaultByteCodeVerifier(Arrays.asList(MockGame.class,
				MockMove.class));
		this.builder.setByteCodeVerifier(sverifier);
	}

	@After
	public void tearDown() {
		sverifier = null;
	}
}
