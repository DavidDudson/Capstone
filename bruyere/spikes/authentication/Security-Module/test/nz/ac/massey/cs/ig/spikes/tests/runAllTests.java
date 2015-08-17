/*
 * SoGaCo Authentication System
 * Created by Marcel Kroll
 */
package nz.ac.massey.cs.ig.spikes.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//Simple class to run all the tests defined within the testing package
//Note without some form of testing LDAP server 1 of the tests will fail
@RunWith(Suite.class)
@Suite.SuiteClasses({
    whiteListHandlerTest.class,
    userStorageHandlerTest.class,
    LDAPAuthenticationHandlerTest.class,
})

//empty method - not required
public class runAllTests {}
