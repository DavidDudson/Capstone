package nz.ac.massey.cs.ig.core.services;

import java.util.Properties;
import java.util.Set;

/**
 * Manage bot persistency. 
 * @author jens dietrich
 */
public interface Storage {
   
    /**
     * Get the bot ids for a particular user.
     * @param user
     * @return
     * @throws StorageException 
     */
    Set<String> getBotIds(String userId)  throws StorageException;
    /**
     * Get the source code of a bot.
     * @param userId
     * @return
     * @throws StorageException 
     */
    String getBotSourceCode (String botId) throws StorageException ;
  
    /**
     * Get the meta data of a bot.
     * @param botId
     * @return
     * @throws StorageException 
     */
    Properties getBotMetadata (String botId) throws StorageException ;
    
    /**
     * Save a bot.
     * @param botId
     * @param srcCode
     * @throws StorageException 
     */
    void saveBotSourceCode(String botId, String srcCode)  throws StorageException;
    
    /**
     * Save bot meta data.
     * @param botId
     * @param metadata
     * @throws StorageException 
     */
    void saveBotMetadata(String botId, Properties metadata)  throws StorageException;
    
    /**
     * Set the owner of a bot.
     * @param userId
     * @param botId
     * @throws StorageException 
     */
    void setOwner (String userId,String botId) throws StorageException ;
    
    /**
     * Check whether a user is the owner of a bot.  
     * @param userId
     * @param botId
     * @throws StorageException 
     */
    boolean isOwner (String userId,String botId) throws StorageException ;
    
   /**
    * Remove a bot from storage.
    * @param botId
    * @throws StorageException 
    */
    void deleteBot (String botId) throws StorageException ; 
    
    /**
     * Delete all bots owned by a certain user.
     * @param userId
     * @throws StorageException 
     */
    void deleteBotsOwnedBy (String userId) throws StorageException ; 
    
    /**
     * If the storage engine supports caching, this can be used to empty the cache.
     * This is manly used for testing.
     * @throws UnsupportedOperationException 
     */
    void clearCache () throws UnsupportedOperationException ;
    
    
}
