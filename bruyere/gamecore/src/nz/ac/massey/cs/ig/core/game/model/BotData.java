package nz.ac.massey.cs.ig.core.game.model;

import java.util.function.Function;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import nz.ac.massey.cs.ig.core.utils.Utils;

/**
 * Class which represents all static data of a bot. These are meta data like id,
 * name and moreover its source code.
 * 
 * @author Johannes Tandler
 *
 */
@Entity
public class BotData {

	/**
	 * id of a bot
	 */
	@Id
	private String id;

	/**
	 * Source code of the bot
	 */
	@Lob
	private String src;

	/**
	 * last modified data
	 */
	private String lastModified;

	/**
	 * date when this bot was created
	 */
	private String created;

	/**
	 * identifier of used programming language
	 */
	private String language;

	/**
	 * name of bots
	 */
	private String name;

	/**
	 * qualified name of bot
	 */
	private String qName;

	/**
	 * Owner of this bot
	 */
    @ManyToOne
    @JoinColumn(name="owner_id", nullable = false)
	private UserData owner;

	/**
	 * True if bot can be compiled
	 */
	private boolean compilable;

	/**
	 * True if bot can be tested
	 */
	private boolean tested;

	/**
	 * True if bot can be shared
	 */
	private boolean shared;

	/**
	 * True if bot is starred. Starred bots are automatically used in
	 * tournaments.
	 */
	private boolean starred;

	public BotData() {
		super();
		this.created = Utils.getTimeStamp();

		language = "";
		name = null;
		qName = "";
		owner = null;
		compilable = false;
		tested = false;
		shared = false;
		starred = false;

		this.lastModified = this.created;
	}

	public BotData(String id) {
		this();
		this.id = id;
	}

	/**
	 * {@link #src}
	 * 
	 * @return
	 */
	public String getSrc() {
		return src;
	}

	/**
	 * {@link #src}
	 * 
	 * @param src
	 */
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * {@link #id}
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * {@link #lastModified}
	 * 
	 * @return
	 */
	public String getLastModified() {
		return lastModified;
	}

	/**
	 * {@link #lastModified}
	 * 
	 * @param lastModified
	 */
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * {@link #created}
	 * 
	 * @return
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * {@link #created}
	 * 
	 * @param created
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * {@link #language}
	 * 
	 * @return
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * {@link #language}
	 * 
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * {@link #name}
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@link #name}
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * {@link #qName}
	 * 
	 * @return
	 */
	public String getqName() {
		return qName;
	}

	/**
	 * {@link #qName}
	 * 
	 * @param qName
	 */
	public void setqName(String qName) {
		this.qName = qName;
	}

	/**
	 * {@link #owner}
	 * 
	 * @return
	 */
	public UserData getOwner() {
		return owner;
	}

	/**
	 * {@link #owner}
	 * 
	 * @param owner
	 */
	public void setOwner(UserData owner) {
		this.owner = owner;
	}

	/**
	 * {@link #compilable}
	 * 
	 * @return
	 */
	public boolean isCompilable() {
		return compilable;
	}

	/**
	 * {@link #compilable}
	 * 
	 * @param compilable
	 */
	public void setCompilable(boolean compilable) {
		this.compilable = compilable;
	}

	/**
	 * {@link #tested}
	 * 
	 * @return
	 */
	public boolean isTested() {
		return tested;
	}

	/**
	 * {@link #tested}
	 * 
	 * @param tested
	 */
	public void setTested(boolean tested) {
		this.tested = tested;
	}

	/**
	 * {@link #shared}
	 * 
	 * @return
	 */
	public boolean isShared() {
		return shared;
	}

	/**
	 * {@link #shared}
	 * 
	 * @param shared
	 */
	public void setShared(boolean shared) {
		this.shared = shared;
	}

	/**
	 * {@link #starred}
	 * 
	 * @return
	 */
	public boolean isStarred() {
		return starred;
	}

	/**
	 * {@link #starred}
	 * 
	 * @param starred
	 */
	public void setStarred(boolean starred) {
		this.starred = starred;
	}

	/**
	 * Helper enum
	 * 
	 * @author Jake
	 *
	 */
	public enum BotMetaDataProperties {

		//@formatter:off
		LAST_MODIFIED("lastmodified", (BotData b) -> b.getLastModified()), 
		CREATED("created", (BotData b) -> b.getCreated()), 
		ID("id", (BotData b) -> b.getId()), 
		LANGUAGE("language", (BotData b) -> b.getLanguage()), 
		NAME("name", (BotData b) -> b.getName()), 
		QNAME("qname", (BotData b) -> b.getqName()), 
		OWNER("owner", (BotData b) -> b.getOwner().getId()),
		OWNER_NAME("owner_name", (BotData b) -> b.getOwner().getName() != null ? b.getOwner().getName() : b.getOwner().getId()),
		COMPILABLE("compilable", (BotData b) -> b.isCompilable() ? "true" : "false"), 
		SHARED("shared", (BotData b) -> b.isShared() ? "true" : "false"), 
		TESTED("tested", (BotData b) -> b.isTested() ? "true" : "false");
		//@formatter:on

		private final String name;

		private final Function<BotData, String> botData;

		private BotMetaDataProperties(String name, Function<BotData, String> op) {
			this.name = name;
			this.botData = op;
		}

		public String getName() {
			return name;
		}

		public String getValue(BotData data) {
			return botData.apply(data);
		}
	}

}
