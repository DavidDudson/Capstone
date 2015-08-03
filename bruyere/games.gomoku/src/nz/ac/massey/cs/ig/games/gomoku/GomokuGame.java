package nz.ac.massey.cs.ig.games.gomoku;

import java.util.ArrayList;
import java.util.List;

import nz.ac.massey.cs.ig.core.game.Bot;
import nz.ac.massey.cs.ig.core.game.GameState;
import nz.ac.massey.cs.ig.core.game.IllegalMoveException;
import nz.ac.massey.cs.ig.core.game.SimpleGame;


public class GomokuGame extends SimpleGame<EasyGomokuBoard,GomokuPosition> {
	
	private EasyGomokuBoard currentBoard;

	private List<GomokuHistoryEntry> moves;
	private List<GomokuPosition> winningPieces=new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public GomokuGame(String uid, Bot<?, ?> player1, Bot<?, ?> player2) {
		super(uid, (Bot<EasyGomokuBoard, GomokuPosition>)player1, (Bot<EasyGomokuBoard, GomokuPosition>) player2);
		moves = new ArrayList<GomokuHistoryEntry>();
		currentBoard = new EasyGomokuBoard(player1, player2);
	

		moves.add(new GomokuHistoryEntry(getPublicState(), null, null));
	}

	/**
	 * Returns a copy of the board so nobody can change it.
	 */
	@Override
	public EasyGomokuBoard getPublicState() {
		return currentBoard.copy();
	}

	@Override
	protected void doMove(GomokuPosition move, Bot<EasyGomokuBoard, GomokuPosition> bot) {
		
		currentBoard.setMe(bot);
		currentBoard.makeMove(move);
		
		moves.add(new GomokuHistoryEntry(getPublicState(), move, bot.getId())); 
		if (currentBoard.isPlayer1(move.getX(), move.getY())) {
			this.state = GameState.WAITING_FOR_PLAYER_2;
		} else {
			this.state = GameState.WAITING_FOR_PLAYER_1;
		}
	}
	public List<GomokuHistoryEntry> getHistory(){
		return this.moves;
	}
	public List<GomokuPosition> getWinningPieces(){
		return winningPieces;
	}
	@Override
	protected void registerMove(GomokuPosition move,Bot<EasyGomokuBoard, GomokuPosition> bot) {
	   
	}

	@Override
	protected void checkValidityOfMove(GomokuPosition move,Bot<EasyGomokuBoard, GomokuPosition> playerId) throws IllegalMoveException {
		if (!currentBoard.isMoveValid(move)) {
			throw new IllegalMoveException("Player " + playerId.getId()
					+ " is not allowed to make move " + move
					+ ". <br/> Board was : <br/><pre>"
					+ currentBoard.toString() + "</pre>");
		}
	}

	@Override
	protected void checkGameTermination() {
			GomokuPosition lastPosition =moves.get(moves.size()-1).getPosition();
			if(lastPosition != null){
				if(isWin(lastPosition.getX(),lastPosition.getY())){
					if(currentBoard.isPlayer1(lastPosition.getX(),lastPosition.getY())){
						this.state=GameState.PLAYER_1_WON;
					}else if(currentBoard.isPlayer2(lastPosition.getX(),lastPosition.getY())){
								this.state=GameState.PLAYER_2_WON;
							}else{
								this.state=GameState.TIE;
							}
				}
			}
			currentBoard.switchPlayerPerspective();
	}
	
	 private boolean isWin(int xIndex, int yIndex){  
	        int continueCount=0; 
	        int ROWS =currentBoard.fieldSize;
	        int COLS =currentBoard.fieldSize;
	        List<GomokuPosition> temp=new ArrayList<>();
	
	       for(int x=xIndex;x>=0;x--){  
	          
	           if(currentBoard.isFieldMine(x, yIndex)){  
	        	   temp.add(new GomokuPosition(x,yIndex));
	               continueCount++;  
	           }else{
	        	   break;  
	           }     
	       }  
	      //search for right 
	       for(int x=xIndex;x<COLS;x++){  
	         
	          if(currentBoard.isFieldMine(x,yIndex)){
	        	  temp.add(new GomokuPosition(x,yIndex));
	             continueCount++;  
	          }else{  
	             break;
	          }
	       }  
	       if(continueCount>5){
	    	   	winningPieces=temp;
	             return true;  
	       }else { 
	    	   temp.clear();
	    	   continueCount=0; 
	       }
	         
	       //search up  
	       for(int y=yIndex;y>=0;y--){  
	          
	           if(currentBoard.isFieldMine(xIndex,y)){
	        	   temp.add(new GomokuPosition(xIndex,y));
	               continueCount++;  
	           }else{ 
	               break;
	           }
	       }  
	       //search down 
	       for(int y=yIndex;y<ROWS;y++){  
	          
	           if(currentBoard.isFieldMine(xIndex,y)){
	        	   temp.add(new GomokuPosition(xIndex,y));
	               continueCount++;  
	           }else{  
	              break;  
	           }
	       }  
	       if(continueCount>5){
	    	   winningPieces=temp;
	           return true;  
	       }else{ 
	    	   temp.clear();
	           continueCount=0;
	       }
	         
	         
	       //search diagonally 
	       //right top 
	       for(int x=xIndex,y=yIndex;y>=0&&x<COLS;x++,y--){  
	             
	           if(currentBoard.isFieldMine(x,y)){ 
	        	   temp.add(new GomokuPosition(x,y));
	               continueCount++;  
	           }else{
	        	   break;  
	           }
	       }  
	       //left down  
	       for(int x=xIndex,y=yIndex;x>=0&&y<ROWS;x--,y++){  
	           
	           if(currentBoard.isFieldMine(x,y)){
	        	  temp.add(new GomokuPosition(x,y));
	               continueCount++;  
	           }else{
	        	   break;  
	           }
	       }  
	       if(continueCount>5){
	    	   winningPieces=temp;
	           return true; 
	       } else {
	    	   temp.clear();
	    	   continueCount=0;  
	       }
	         
	           
	       //left up
	       for(int x=xIndex,y=yIndex;x>=0&&y>=0;x--,y--){  
	         
	           if(currentBoard.isFieldMine(x,y)){
	        	   temp.add(new GomokuPosition(x,y));
	               continueCount++;  
	           }else{
	        	   break;  
	           }
	       }  
	       //right down  
	       for(int x=xIndex,y=yIndex;x<COLS&&y<ROWS;x++,y++){  
	         
	           if(currentBoard.isFieldMine(x,y)){ 
	        	   temp.add(new GomokuPosition(x,y));
	               continueCount++;  
	           }else{
	        	   break;  
	           }
	       }  
	       if(continueCount>5){
	    	   winningPieces=temp;
	           return true;  
	       }else{
	    	   temp.clear();
	    	   continueCount=0;  
	       }
	         
	       return false;  
	     }  
}
