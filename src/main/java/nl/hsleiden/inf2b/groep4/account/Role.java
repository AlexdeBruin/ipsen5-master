package nl.hsleiden.inf2b.groep4.account;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

	@Id
	@Column(name = "role_id")
	private int roleId;
	private String roleName;

	public Role() {
	}

	public Role(int roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public Role(int roleId) throws Exception {
		this.roleId = roleId;
		if (roleId == 1) {
			this.roleName = "GROUP";
		} else if (roleId == 3) {
			this.roleName = "ADMIN";
		} else {
			throw new Exception("Invalid role id");
		}
}

	public int getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}
}
