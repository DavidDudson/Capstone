package test.nz.ac.massey.cs.ig.core.services.defaults.testtests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for the junit-based verifier.
 * Note that this are non-standard test cases that can only be run with the special test runner used in the junit-based dynamic verifier ! 
 * @author jens dietrich
 */
public class TestTestUnexpectedException {
	private MockBot bot = null;
	public TestTestUnexpectedException(MockBot bot) {
		super();
		this.bot = bot;
	}
	
	@Before public void setUp() {
		this.bot = new MockBot();
	}
	@After public void tearDown() {
		this.bot = null;
	}
			
	@Test
	public void testUnexpectedException () {
		bot.throwIllegalStateException();
	}
}
