package nz.ac.massey.cs.ig.core.services.event;

import nz.ac.massey.cs.ig.core.services.event.logging.BuildEvent;
import nz.ac.massey.cs.ig.core.services.event.logging.GameEvent;

public interface EventLogger {
	
	void logGameEvent(GameEvent event);
	
	void logBuildEvent(BuildEvent event);
	
	String ping();

}
