# Capstone
Capstone Project 2015 Group 1

# BuildScript
Even more helpful if you add gradle to your PATH but...

Mac/Linux: gradle SOME-TASK

Windows: gradlew.bat SOME-TASK

If you want to clone & build SoGaCO
1. Go into build.gradle
2. Find the task cloneSogaco
3. Add in you bitbucket username and password.
4. Call gradle buildSogaco via commandLine
5. Wait... It can take 10-15mins to clone/build. Dont ask me why. I dont know, sogaco has 2000+ chunks to download
7. Call gradle farmRun to run the server

This may not be running perfectly on windows As I havnt tested. Please do not commit build.gradle after adding your username/password. I will be changing this to SSH in the future
