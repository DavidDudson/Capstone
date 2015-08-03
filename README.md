# Capstone
Capstone Project 2015 Group 1

# BuildScript
Even more helpful if you add gradle to your PATH but...

Mac/Linux: gradle SOME-TASK

Windows: gradlew.bat SOME-TASK

Tasks:
- runDevServer to run the Jetty Server.
- buildAll to just build and not run the server
- assemble to build without running tests
- check to just run tests
- appStart to just start the application

# Server

The server is set up very simply.

- localhost:8080/SoGaCo-Web-Home/ -> Main Sogaco Home page
- localhost:8080/SoGaCo-Web-Battleships(VersionTag)/gameState.jsp -> Our current test jsp page
- localhost:8080/SoGaCo-Web-Battleships(VersionTag)/static/demo.html -> Current Blockly demos

Ill fix routing when we have something worth actually fixing.
