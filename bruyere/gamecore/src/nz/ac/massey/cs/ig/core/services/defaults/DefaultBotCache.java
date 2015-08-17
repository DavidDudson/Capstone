package nz.ac.massey.cs.ig.core.services.defaults;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import nz.ac.massey.cs.ig.core.services.BotCache;
import nz.ac.massey.cs.ig.core.services.BotCacheMXBean;
import nz.ac.massey.cs.ig.core.services.build.BotFactory;

public class DefaultBotCache implements BotCache {
	
	private Cache<String,BotFactory> CACHE = CacheBuilder.newBuilder()
		       .maximumSize(1000)
		       .expireAfterWrite(1, TimeUnit.HOURS)
		       .build();

	@Override
	public boolean isBotCached(String id) {
		return CACHE.getIfPresent(id) != null;
	}

	@Override
	public BotFactory getCachedBot(String id) {
		return CACHE.getIfPresent(id);
	}

	@Override
	public BotFactory cacheBot(BotFactory factory) {
		CACHE.put(factory.getBotId(), factory);
		return factory;
	}

	@Override
	public boolean releaseCachedBot(String id) {
		CACHE.invalidate(id);
		return true;
	}
	
	
	/*public static class CachedJavaBuilderMXBeanImpl implements BotCacheMXBean {
		 
		CachedJavaBuilderMXBeanImpl(String mbName) throws Exception {
	        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
	       
            String name = String.format("%s:name=%s",DefaultBotCache.class.getPackage().getName(), mbName);
            ObjectName mxBeanName = new ObjectName(name);
            if (!server.isRegistered(mxBeanName)) {
                server.registerMBean(this, new ObjectName(name));
            }
	    }

	    @Override public long getRequestCount() {
	        return CACHE.stats().requestCount();
	    }
	 
	    @Override public long getHitCount() {
	        return CACHE.stats().hitCount();
	    }
	 
	    @Override public double getHitRate() {
	        return CACHE.stats().hitRate();
	    }
	 
	    @Override public long getMissCount() {
	        return CACHE.stats().missCount();
	    }
	 
	    @Override public double getMissRate() {
	        return CACHE.stats().missRate();
	    }
	 
	    @Override public long getLoadCount() {
	        return CACHE.stats().loadCount();
	    }
	 
	    @Override public long getLoadSuccessCount() {
	        return CACHE.stats().loadSuccessCount();
	    }
	 
	    @Override public long getLoadExceptionCount() {
	        return CACHE.stats().loadExceptionCount();
	    }
	 
	    @Override public double getLoadExceptionRate() {
	        return CACHE.stats().loadExceptionRate();
	    }
	 
	    @Override public long getTotalLoadTime() {
	        return CACHE.stats().totalLoadTime();
	    }
	 
	    @Override public double getAverageLoadPenalty() {
	        return CACHE.stats().averageLoadPenalty();
	    }
	 
	    @Override public long getEvictionCount() {
	        return CACHE.stats().evictionCount();
	    }
	 
	    @Override public long getSize() {
	        return CACHE.size();
	    }
	 
	    // actions 
	    @Override public void cleanUp() {
	    	CACHE.cleanUp();
	    }
	 
	    @Override public void invalidateAll() {
	    	CACHE.invalidateAll();
	    }
	}
	
	static {
		try {
			new CachedJavaBuilderMXBeanImpl("BotCache");
		}
		catch (Exception x) {
			System.err.println("Error initialising bot cache in " + DefaultBotCache.class.getName());
			x.printStackTrace();
		}
	}*/

}
