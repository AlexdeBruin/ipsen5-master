package nl.hsleiden.inf2b.groep4.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.hsleiden.inf2b.groep4.puzzle.block.Puzzle;
import org.glassfish.jersey.server.JSONP;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.security.auth.Subject;
import java.security.Principal;

@Entity
@Table(name = "account", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
@NamedQueries(value = {
		@NamedQuery(name = "nl.hsleiden.inf2b.groep4.account.Account.findAll",
				query = "Select a from Account a order by username"),
		@NamedQuery(name = "nl.hsleiden.inf2b.groep4.account.Account.findByUsername",
				query = ("Select a from Account a where a.username = :username")),
		@NamedQuery(name = "nl.hsleiden.inf2b.groep4.account.Account.getPuzzles",
				query = "Select a from Account a where puzzle_id is not null")
})
public class Account implements Principal {

	@Id
	@Column(name = "Account_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountId;

	@Column(name = "username")
	private String username;

	@JsonIgnore()
	@Column(name = "password")
	@Length(min = 8)
	private String password;

	@JoinColumn(name = "role_id")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Role accountRole;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Score score;

	@Column(name = "is_active")
	private boolean active;

	@Column(name = "require_password_change")
	private boolean requiresPasswordChange;

	@JoinColumn(name = "puzzle_id")
	@OneToOne(cascade = CascadeType.MERGE)
	private Puzzle puzzle;

	public Account() {
	}


	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isRequiresPasswordChange() {
		return requiresPasswordChange;
	}

	public void setRequiresPasswordChange(boolean requiresPasswordChange) {
		this.requiresPasswordChange = requiresPasswordChange;
	}

	public Puzzle getPuzzle() {
		return puzzle;
	}

	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}

	@Override
	@JsonIgnore
	public String getName() {
		return null;
	}

	@Override
	public boolean implies(Subject subject) {
		return false;
	}

	public Role getAccountRole() {
		return accountRole;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}
}
