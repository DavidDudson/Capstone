package nz.ac.massey.cs.ig.core.game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Class to represent a user
 * 
 * @author Johannes Tandler
 *
 */
@Entity
public class UserData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4117236834731839545L;

	/**
	 * User id
	 */
	@Id
	private String id;

	/**
	 * user email
	 */
	private String email;

	/**
	 * name
	 */
	private String name;

	/**
	 * url of photo if available
	 */
	private String photoUrl;

	/**
	 * list of owned bots
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<BotData> bots;

	@ElementCollection
	private Set<UserRoles> roles;

	/**
	 * list of imported bots
	 */
	/*@OneToMany
	@JoinTable(name = "VISIBLE_BOTS")
	private List<BotData> visibleBots;*/

	public UserData() {
		bots = new ArrayList<BotData>();
		roles = EnumSet.of(UserRoles.STUDENT);
	}

	public UserData(String id) {
		this();
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getId() {
		return id;
	}

	public List<BotData> getBots() {
		return bots;
	}

	public boolean hasRole(String roleName) {
		roleName = roleName.toLowerCase().trim();
		for (UserRoles roles : roles) {
			if (roles.name().toLowerCase().equals(roleName))
				return true;
		}
		return false;
	}

	public void addRole(UserRoles role) {
		roles.add(role);
	}

	public Set<UserRoles> getRoles() {
		return roles;
	}

	@Override
	public String toString() {
		return id;
	}
}
