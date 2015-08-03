
package nz.ac.massey.cs.ig.games.primegame;

import static org.junit.Assert.assertEquals;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.games.primegame.bots.GreedyBuiltinBot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests the TTT game rules.
 * @author jens dietrich
 */
@SuppressWarnings("unchecked")
public class PrimeGameRuleTests {
    
    private PGGame game = null;
    
    private PGBot bot1;
    private PGBot bot2;
    
    public PrimeGameRuleTests() {
        super();
    }
    
    @Before
    public void setUp() {
    	bot1 = Mockito.mock(PGBot.class);
    	bot2 = new GreedyBuiltinBot();
        game = new PGGame("game1", bot1, bot2);
        game.setBoardSize(10);
    }
    
    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void testInitStatus() {
        assertEquals(game.getState(),GameState.NEW);
    }

	@Test(expected=IllegalMoveException.class)
    public void testIllegalStartWrongPosition1() throws IllegalMoveException {
    	Mockito.when(bot1.nextMove(Mockito.anyList())).thenReturn(-1);
        game.move();
    }
    
	@Test(expected=IllegalMoveException.class)
    public void testIllegalStartWrongPosition2() throws IllegalMoveException {
    	Mockito.when(bot1.nextMove(Mockito.anyList())).thenReturn(42);
        game.move();
    }
    
    @Test(expected=IllegalStateException.class)
    public void testChangeSizeAfterGameHasStarted() throws IllegalMoveException {
    	Mockito.when(bot1.nextMove(Mockito.anyList())).thenReturn(1);
    	game.move();
        game.setBoardSize(30);
    }

    public void testIllegalStartWrongPosition3() throws IllegalMoveException {
    	game.setTerminatingOnError(true);
    	Mockito.when(bot1.nextMove(Mockito.anyList())).thenReturn(42);
        game.move();
        assertEquals(GameState.PLAYER_2_WON,game.getState());
    }
    
    
    @Test(expected=IllegalMoveException.class)
    public void testIllegalPosition1() throws IllegalMoveException {
    	bot2 = Mockito.mock(PGBot.class);
    	game = new PGGame("", bot1, bot2);
    	

    	Mockito.when(bot1.nextMove(Mockito.anyList())).thenReturn(2);
    	Mockito.when(bot2.nextMove(Mockito.anyList())).thenReturn(2);
    	
    	game.move();
    	game.move();
    }
    
    public void testIllegalPosition2() throws IllegalMoveException {
    	bot2 = Mockito.mock(PGBot.class);
    	game = new PGGame("", bot1, bot2);
    	game.setTerminatingOnError(true);
    	

    	Mockito.when(bot1.nextMove(Mockito.anyList())).thenReturn(2);
    	Mockito.when(bot2.nextMove(Mockito.anyList())).thenReturn(3);
    	
    	game.move();
    	game.move();
        assertEquals(GameState.PLAYER_1_WON,game.getState());
    }
    
    @Test
    public void testPlayer1Wins() throws IllegalMoveException {
    	bot2 = Mockito.mock(PGBot.class);
    	game = new PGGame("", bot1, bot2);
    	game.setBoardSize(10);
    	
    	Mockito.when(bot1.nextMove(Mockito.anyList())).thenReturn(7);
    	game.move();
    	
        // player1: 7 , player2: 1
        assertEquals(GameState.WAITING_FOR_PLAYER_2,game.getState());
        assertEquals(7,game.getPlayer1score());
        assertEquals(1,game.getPlayer2score());
        
    	Mockito.when(bot2.nextMove(Mockito.anyList())).thenReturn(10);
    	game.move();
    	
        // player1: 7+(2+5)=14 , player2: 1+(10)=11
        assertEquals(GameState.WAITING_FOR_PLAYER_1,game.getState());
        assertEquals(14,game.getPlayer1score());
        assertEquals(11,game.getPlayer2score());
        
    	Mockito.when(bot1.nextMove(Mockito.anyList())).thenReturn(9);
    	game.move();
        // player1: 14+(9)=23 , player2: 11+(3)=14
        assertEquals(GameState.WAITING_FOR_PLAYER_2,game.getState());
        assertEquals(23,game.getPlayer1score());
        assertEquals(14,game.getPlayer2score());
        
    	Mockito.when(bot2.nextMove(Mockito.anyList())).thenReturn(4);
    	game.move();
        // player1: 23+(0)=23 , player2: 14+(4)=18
        assertEquals(GameState.WAITING_FOR_PLAYER_1,game.getState());
        assertEquals(23,game.getPlayer1score());
        assertEquals(18,game.getPlayer2score());
           
    	Mockito.when(bot1.nextMove(Mockito.anyList())).thenReturn(8);
    	game.move();
        // player1: 23+(8)=31 , player2: 18+(0)=18
        assertEquals(GameState.WAITING_FOR_PLAYER_2,game.getState());
        assertEquals(31,game.getPlayer1score());
        assertEquals(18,game.getPlayer2score());
        
        Mockito.when(bot2.nextMove(Mockito.anyList())).thenReturn(6);
    	game.move();
        // player1: 31+(0)=31 , player2: 18+(6)=24
        assertEquals(GameState.PLAYER_1_WON,game.getState());
        assertEquals(31,game.getPlayer1score());
        assertEquals(24,game.getPlayer2score());
    }
    
    
}
