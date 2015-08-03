package nz.ac.massey.cs.ig.core.services.event.logging;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import nz.ac.massey.cs.ig.core.services.build.BuildResult.BuildStatus;


@Entity
public class BuildEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String userId;
	
	private String botId;
	
	private String gameId;
	
	private int gameVersionMajor;
	private int gameVersionMinor;
	private int gameVersionRevision;
	
	private String language;
	
	@Lob
	private String src;

	@Lob
	private String stacktrace;
	
	private BuildStatus status;
	
	private Date timestamp;
	
	private String sogacoInstanceId;


	public BuildEvent() {
		timestamp = new Date();
	}
	
	
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getBotId() {
		return botId;
	}


	public void setBotId(String botId) {
		this.botId = botId;
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


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getSrc() {
		return src;
	}


	public void setSrc(String src) {
		this.src = src;
	}


	public String getStacktrace() {
		return stacktrace;
	}


	public void setStacktrace(String stacktrace) {
		this.stacktrace = stacktrace;
	}


	public BuildStatus getStatus() {
		return status;
	}


	public void setStatus(BuildStatus status) {
		this.status = status;
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
	
}
