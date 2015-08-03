package nz.ac.massey.cs.ig.core.game;

/**
 * Defines the keys for bot meta data.
 * Note that these values should not be changed as they are used in persistent data.
 * @author jens dietrich
 */
public interface BotMetaData {
	public static final String LAST_MODIFIED = "lastmodified";
	public static final String CREATED = "created";
	public static final String ID = "id";
	public static final String LANGUAGE = "language"; // programming language bot is defined in
	public static final String NAME = "name"; // local class name
	public static final String QNAME = "qname"; // full name incl package name
	public static final String OWNER = "owner"; // full name incl package name
}
