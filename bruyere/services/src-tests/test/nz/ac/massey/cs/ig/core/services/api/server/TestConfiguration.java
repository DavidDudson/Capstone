package test.nz.ac.massey.cs.ig.core.services.api.server;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;

import nz.ac.massey.cs.ig.core.services.Configuration;

public class TestConfiguration implements Configuration {

	private String rootPath = "";
	
	public TestConfiguration(String rootPath) {
		this.rootPath = rootPath;
	}
	
	@Override
	public boolean isDebugMode() {
		return true;
	}

	@Override
	public List<String> getAdminUserIds() {
		return null;
	}

	@Override
	public void save() {
		
	}

	@Override
	public String getLogLevel() {
		return Level.ALL.name();
	}

	@Override
	public void setLogLevel(String newLogLevel) {
	}

	@Override
	public String getJDBCConnectionURL() {
		return "jdbc:derby:target/database;create=true";
	}

	@Override
	public Map<String, String> getPredefinedUsers() {
		return null;
	}

	@Override
	public String getEventLoggerBaseURL() {
		return null;
	}

	@Override
	public String getEventLoggerClass() {
		return null;
	}

	@Override
	public String getRootPath() {
		return rootPath;
	}

}
