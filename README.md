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
5. Wait... It can take 5-10 mins to clone/build. cloning takes around 5, building around 1-2.
7. Call gradle farmRun to run the server (I didn't make that task name, blame the plugin developer)

If you want to delete everything in the build folder for some reason call gradle clean

I suggest running buildSogaco with the -i argument (gradle buildSogaco -i),
this increases the amount of console logging and actually tells you whats happening.

This may not be running perfectly on windows As I havn't tested it yet. Please do not commit build.gradle after adding your username/password. I will be changing this to SSH in the future to avoid this.
