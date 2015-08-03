package nz.ac.massey.cs.ig.core.services.event.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GameEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String userId;
	
	private String bot1Id;
	
	private String player1Id;
	
	private String bot2Id;
	
	private String player2Id;
	
	private String gameId;
	
	private int gameVersionMajor;
	private int gameVersionMinor;
	private int gameVersionRevision;
	
	private GameState gameResult;
	
	private String error;
	
	private Date timestamp;
	
	private String sogacoInstanceId;
	
	public GameEvent() {
		timestamp = new Date();
	}
	

	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getBot1Id() {
		return bot1Id;
	}


	public void setBot1Id(String bot1Id) {
		this.bot1Id = bot1Id;
	}


	public String getPlayer1Id() {
		return player1Id;
	}


	public void setPlayer1Id(String player1Id) {
		this.player1Id = player1Id;
	}


	public String getBot2Id() {
		return bot2Id;
	}


	public void setBot2Id(String bot2Id) {
		this.bot2Id = bot2Id;
	}


	public String getPlayer2Id() {
		return player2Id;
	}


	public void setPlayer2Id(String player2Id) {
		this.player2Id = player2Id;
	}


	public String getGameId() {
		return gameId;
	}


	public void setGameId(String gameId) {
		this.gameId = gameId;
	}


	public int getGameVersionMajor() {
		return gameVersionMajor;
	}


	public void setGameVersionMajor(int gameVersionMajor) {
		this.gameVersionMajor = gameVersionMajor;
	}


	public int getGameVersionMinor() {
		return gameVersionMinor;
	}


	public void setGameVersionMinor(int gameVersionMinor) {
		this.gameVersionMinor = gameVersionMinor;
	}


	public int getGameVersionRevision() {
		return gameVersionRevision;
	}


	public void setGameVersionRevision(int gameVersionRevision) {
		this.gameVersionRevision = gameVersionRevision;
	}


	public GameState getGameResult() {
		return gameResult;
	}


	public void setGameResult(GameState gameResult) {
		this.gameResult = gameResult;
	}


	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}


	public Date getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getSogacoInstanceId() {
		return sogacoInstanceId;
	}


	public void setSogacoInstanceId(String sogacoInstanceId) {
		this.sogacoInstanceId = sogacoInstanceId;
	}


	public enum GameState {
		PLAYER_1_WON,
		PLAYER_2_WON,
		TIE,
		OTHER
	}
}
