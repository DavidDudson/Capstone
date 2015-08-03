package nz.ac.massey.cs.ig.core.services;

/**
 * MBean interface to monitor caching.
 * @author jens dietrich
 */
public interface BotCacheMXBean {
	
	// queries
	long getRequestCount();
    long getHitCount();
    double getHitRate();
    long getMissCount();
    double getMissRate();
    long getLoadCount();
    long getLoadSuccessCount();
    long getLoadExceptionCount();
    double getLoadExceptionRate();
    long getTotalLoadTime();
    double getAverageLoadPenalty();
    long getEvictionCount();
    long getSize();
    
    // actions
    void cleanUp();
    void invalidateAll();
}
