# Capstone
Capstone Project 2015 Group 1

# BuildScript

Mac/Linux: ./gradle SOME-TASK

Windows: gradlew.bat SOME-TASK

Fo the uninformed just run the task runDevServer and everything will work.

Eg:

Mac/Linux: ./gradle runDevServer

Windows: gradlew.bat runDevServer

If you choose to install gradle the command will always be (ragardless of platform)

gradle runDevServer

Tasks:
- runDevServer to run the Jetty Server building everything.
- jettyRunWar to run the Server without building
- buildAll to just build and not run the server
- assemble to build without running tests
- check to just run tests
- appStart to just start the application
- cloneSogaco to download the sources of SoGaCo

# Server

The server is set up very simply.

- localhost:8080/Capstone - The Index page.

# Configuration


