package nz.ac.massey.cs.ig.core.services;

import nz.ac.massey.cs.ig.core.services.build.BotFactory;

public interface BotCache {

	public boolean isBotCached(String id);
	
	public BotFactory getCachedBot(String id);
	
	public BotFactory cacheBot(BotFactory factory);
	
	public boolean releaseCachedBot(String id);
	
}
