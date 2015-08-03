package nz.ac.massey.cs.ig.core.services.defaults;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import nz.ac.massey.cs.ig.core.game.BotMetaData;
import nz.ac.massey.cs.ig.core.services.Storage;
import nz.ac.massey.cs.ig.core.services.StorageException;

/**
 * Simple mechanism to store data directly in the file system. Mainly useful for
 * rapid prototyping. DOES NOT SUPPORT TRANSACTIONS !
 * 
 * @author jens dietrich
 */
public class SimpleFileStorage implements Storage {

	private File ROOT;
	private File BOTS;
	private File USERS;

	private static final Charset CHAR_SET = Charset.defaultCharset();

	public SimpleFileStorage(String rootFolder) {
		super();
		this.ROOT = new File(rootFolder);
		this.BOTS = new File(ROOT, "bots");
		this.USERS = new File(ROOT, "users");
	}

	// associates userIds with sets of botIds
	private LoadingCache<String, Set<String>> userBots = CacheBuilder
			.newBuilder().maximumSize(10000)
			.expireAfterWrite(2, TimeUnit.HOURS).concurrencyLevel(10)
			.softValues().build(new CacheLoader<String, Set<String>>() {
				public Set<String> load(String userId) throws IOException {
					Set<String> botIds = new HashSet<String>();
					File botList = getUserBotList(userId);
					if (botList.exists()) {
						botIds.addAll(Files.readLines(botList, CHAR_SET));
					}
					return botIds;
				}
			});

	// associates botIds with source code
	private LoadingCache<String, String> botSourceCode = CacheBuilder
			.newBuilder().maximumSize(1000).expireAfterWrite(2, TimeUnit.HOURS)
			.concurrencyLevel(10).build(new CacheLoader<String, String>() {
				public String load(String botId) throws IOException {
					File src = getBotSourceCodeFile(botId);
					return Files.toString(src, CHAR_SET);
				}
			});

	// associates botIds with metadata
	private LoadingCache<String, Properties> botMetadata = CacheBuilder
			.newBuilder().maximumSize(1000).expireAfterWrite(2, TimeUnit.HOURS)
			.concurrencyLevel(10).build(new CacheLoader<String, Properties>() {
				public Properties load(String botId) throws IOException {
					File f = getBotMetadataFile(botId);
					Properties metadata = new Properties();
					Reader reader = new FileReader(f);
					metadata.load(reader);
					reader.close();
					return metadata;
				}
			});

	private File getUserDir(String userId) throws IOException {
		File f = new File(USERS, userId);
		Files.createParentDirs(f);
		return f;
	}

	private File getBotDir(String botId) throws IOException {
		File f = new File(BOTS, botId);
		Files.createParentDirs(f);
		return f;
	}

	private File getBotSourceCodeFile(String botId) throws IOException {
		File f = new File(getBotDir(botId), botId + ".java");
		Files.createParentDirs(f);
		System.out.print(f.getAbsolutePath());
		return f;
	}

	private File getBotMetadataFile(String botId) throws IOException {
		File f = new File(getBotDir(botId), botId + ".properties");
		Files.createParentDirs(f);
		return f;
	}

	private File getUserBotList(String userId) throws IOException {
		File f = new File(getUserDir(userId), "bots.txt");
		Files.createParentDirs(f);
		return f;
	}

	private String getRootFolderName() {
		return ROOT.getAbsolutePath();
	}

	@Override
	public synchronized Set<String> getBotIds(String userId) throws StorageException {
		try {
			return userBots.get(userId);
		} catch (ExecutionException x) {
			throw new StorageException("Exception looking up bots of user "
					+ userId, x);
		}
	}

	@Override
	public synchronized String getBotSourceCode(String botId) throws StorageException {
		try {
			return botSourceCode.get(botId);
		} catch (ExecutionException x) {
			throw new StorageException("Exception looking up bot " + botId, x);
		}
	}

	@Override
	public synchronized Properties getBotMetadata(String botId) throws StorageException {
		try {
			return botMetadata.get(botId);
		} catch (ExecutionException x) {
			throw new StorageException("Exception looking up bot metadata " + botId, x);
		}
	}

	// note that transactions are not supported
	@Override
	public synchronized void saveBotSourceCode(String botId, String srcCode) throws StorageException {
		try {
			Files.write(srcCode, this.getBotSourceCodeFile(botId), CHAR_SET);
			// cache
			this.botSourceCode.put(botId, srcCode);
		} catch (IOException x) {
			throw new StorageException("Exception storing source code for bot "
					+ botId, x);
		}
	}
	
	// note that transactions are not supported
	@Override
	public synchronized void saveBotMetadata(String botId,Properties metadata) throws StorageException {
		try {
			File f = this.getBotMetadataFile(botId);
			Writer out = new FileWriter(f);
			metadata.store(out, "saved by " + this.getClass().getName());
			out.close();
			// cache
			this.botMetadata.put(botId, metadata);
		} catch (IOException x) {
			throw new StorageException("Exception storing metadata for bot "
					+ botId, x);
		}
	}

	@Override
	public synchronized void setOwner(String userId, String botId) throws StorageException {
		Properties metadata = getBotMetadata(botId);		
		Set<String> bots;
		try {
			bots = this.userBots.get(userId);
			if (!bots.contains(botId)) {
				File botList = getUserBotList(userId);
				PrintWriter out = new PrintWriter(new FileWriter(botList));
				// existing bots
				for (String b : bots) {
					out.println(b);
				}
				// add new bot
				out.println(botId);
				out.close();
				// cache
				bots.add(botId);
				metadata.setProperty(BotMetaData.OWNER,userId);
			}
		} catch (Exception x) {
			throw new StorageException("Exception setting ownership of bot " + botId + " to user " + userId, x);
		}

	}

	@Override
	public synchronized boolean isOwner(String userId, String botId) throws StorageException {
		try {
			return this.userBots.get(userId).contains(botId);
		} catch (ExecutionException x) {
			throw new StorageException("Exception checking ownership of bot "
					+ botId + " by user " + userId, x);
		}
	}

	@Override
	public synchronized void deleteBot(String botId) throws StorageException {
		
		Properties metadata = getBotMetadata(botId);
		
		if (metadata==null) throw new StorageException("Cannot delete bot - cannot find metadata for bot " + botId);
		
		String userId = metadata.getProperty(BotMetaData.OWNER);
		if (userId==null) throw new StorageException("Cannot delete bot - cannot find owner in metadata of bot " + botId);
		
		Set<String> botIds = null;
		File botList = null;
		try {
			botList = getUserBotList(userId);
			botIds = new HashSet<>(); 
			if (botList.exists()) {
				botIds.addAll(Files.readLines(botList, CHAR_SET));
			}
		} catch (IOException x) {
			throw new StorageException("Cannot delete bot " + botId,x);
		}
		
		// delete bot
		if (!botIds.remove(botId)) {
			throw new StorageException("Cannot delete bot - inconsistent user information for bot " + botId + " and user " + userId);
		}
		
		// write new ownership info
		PrintWriter out;
		try {
			out = new PrintWriter(new FileWriter(botList));
			// existing bots
			for (String b : botIds) {
				out.println(b);
			}
			out.close();
		} catch (IOException x) {
			throw new StorageException("Cannot delete bot - exception updating ownership info " + botId );
		}
		
		
		// clear cache
		this.botSourceCode.invalidate(botId);
		this.botMetadata.invalidate(botId);
		this.userBots.invalidate(userId);

		// clear files
		try {
			recursivelyDelete(this.getBotDir(botId));
		} catch (IOException x) {
			throw new StorageException("Exception deleting bot " + botId, x);
		}
	}

	@Override
	public synchronized void deleteBotsOwnedBy(String userId) throws StorageException {
		Set<String> bots;
		try {
			bots = this.userBots.get(userId);
			for (String botId : bots) {
				deleteBot(botId);
			}
		} catch (ExecutionException x) {
			throw new StorageException("Exception deleting all bots owned by "
					+ userId, x);
		}
	}

	@Override
	public synchronized void clearCache() {
		this.botMetadata.invalidateAll();
		this.botSourceCode.invalidateAll();
		this.userBots.invalidateAll();
	}

	private void recursivelyDelete(File f) {
		if (f.isDirectory()) {
			for (File c : f.listFiles()) {
				recursivelyDelete(c);
			}
		}
		f.delete();
	}

}
