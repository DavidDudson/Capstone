Bruyere Testing Environment Readme

Deploying the project is simple, simply load up the project in netbeans and hit the green play button to
automatically deploy the project on the built in apache server

The next task however is a little more fiddly, and that's setting up an LDAP server within apache directory studio
so that we have something to authenticate against.

Step 1: Load up apache directory studio

Step 2: In the bottom left corner click LDAP servers, and click new server

Step 3: Select ApacheDS 2.0.0  and give it a name if you want something different from the default, click finish

Step 4: Note this should automatically create the server, a default partition and set up the server on 10389

Step 5: We now need to add some user data to the server so that we have something to authenticate with

Step 6: Run the server we just created by right clicking the server and clicking run, then right click on our server and click create connection

Step 7: Click on the connections tab, we should see a connection with the same name as our server, right click and click open connection

Step 8: Click on file, then import, open the LDAP browser tab and click on LDIF into LDAP, then click next

Step 9: Under LDIF file navigate to the project root directory where this readme file is, and select LDAPExampleData.ldif

Step 10: Under Import into click browse, and select the server we have created and click OK, and click Finish

Step 11: Hopefully this should import without throughing any exceptions if so, everything should be ready to go.

Step 12: To check if the import works in the LDAP browser to the left, expand RootDSE, expand dc=example,dc=com and then after expanding ou=users you should see 2 testing users

Step 13: If there is data there it is readying for testing, with the server running and the project deployed on in netbeans attempt to navigate to a secure page

Step 14: Upon reaching the login screen use the following data to login Username=admin password=admin note is case sensitive

Step 15: now that you are logged in as admin you can add an account to the white list, if you want that account to have access to the admin page you must assign the role admin to that user, and example user that is inside the ldap server is the following, username=11002250 password=Test note this is case sensitive

Step 16: note the admin account is done seperatly from other users, in a way that with an empty white list it can still reach the admin tools, allowing administration to take place