from locust import HttpLocust, TaskSet, task

import string
import random

def id_generator(size=6, chars=string.ascii_uppercase + string.digits):
    return ''.join(random.choice(chars) for _ in range(size))

def login(l):
    l.client.post("/login", {"username":id_generator(), "password":"education"})

def index(l):
    l.client.get("/editor.jsp")

def submitBot(l):
    l.client.post("/build/JAVA", """package nz.ac.massey.cs.ig.games.othello.botss;
        import nz.ac.massey.cs.ig.games.othello.EasyOthelloBoard;
        import nz.ac.massey.cs.ig.games.othello.OthelloBot;
        import nz.ac.massey.cs.ig.games.othello.OthelloPosition;
        public class DumbBot extends OthelloBot {
            public DumbBot(String id) {
                super(id);
            }
            @Override
            public OthelloPosition nextMove(EasyOthelloBoard game) {
                return game.getAvailableMovesForMe().get(0);//""" + id_generator() + """
            }
        }""")

def submitWrongBotSyntax(l):
    l.client.post("/build/JAVA", """package nz.ac.massey.cs.ig.games.othello.botss;
        import nz.ac.massey.cs.ig.games.othello.EasyOthelloBoard;
        import nz.ac.massey.cs.ig.games.othello.OthelloBot;
        import nz.ac.massey.cs.ig.games.othello.OthelloPosition;
        public class DumbBot extends OthelloBot {
            public DumbBot(String id) {
                super(id);
            }
            @Override
            public OthelloPosition nextMove(EasyOthelloBoard game) {
                return game.getAvailableMovesForMe().get(0);""" + id_generator() + """
            }
        }""")

def submitWrongBotSem(l):
    l.client.post("/build/JAVA", """package nz.ac.massey.cs.ig.games.othello.botss;
        import nz.ac.massey.cs.ig.games.othello.EasyOthelloBoard;
        import nz.ac.massey.cs.ig.games.othello.OthelloBot;
        import nz.ac.massey.cs.ig.games.othello.OthelloPosition;
        public class DumbBot extends OthelloBot {
            public DumbBot(String id) {
                super(id);
            }
            @Override
            public OthelloPosition nextMove(EasyOthelloBoard game) {
                return game.getAvailableMovesForMe().get(1);//""" + id_generator() + """
            }
        }""")

def playBot(l):
    resp = l.client.post("/build/JAVA", (
        "package nz.ac.massey.cs.ig.games.othello.botss;\n"
        "import nz.ac.massey.cs.ig.games.othello.EasyOthelloBoard;\n"
        "import nz.ac.massey.cs.ig.games.othello.OthelloBot;\n"
        "import nz.ac.massey.cs.ig.games.othello.OthelloPosition;\n"
        "public class DumbBot extends OthelloBot {\n"
        "   public DumbBot(String id) {\n"
        "        super(id);\n"
        "    }\n"
        "    @Override\n"
        "    public OthelloPosition nextMove(EasyOthelloBoard game) {\n"
        "        return game.getAvailableMovesForMe().get(0);//" 
        + "-\n" + 
        "}\n}\n"))
    botId = resp.headers['BotId']
    l.client.post("/creategame_b2b" , "SmartBot\n" + botId + "\n")    


class UserBehavior(TaskSet):

    @task
    class SubTaskSet(TaskSet):
        
        @task(10)
        def my_task(self):
            submitBot(self)
        
        @task(10)
        def my_task3(self):
            submitWrongBotSyntax(self)

        @task(10)
        def my_task4(self):
            submitWrongBotSem(self)

        @task(5)
        def my_task2(self):
            playBot(self)

    def on_start(self):
        login(self)

class WebsiteUser(HttpLocust):
    task_set = UserBehavior
    min_wait=500
    max_wait=1500