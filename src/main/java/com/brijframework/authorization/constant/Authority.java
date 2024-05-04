package com.brijframework.authorization.constant;

public enum Authority {

	DEVELOPER("Dev", "DEVELOPER", 1, "GLOBAL"),

	ADMIN("Admin", "ADMIN", 2, "GLOBAL"),

	USER("User", "USER", 3, "APP");

	String roleName;
	String roleId;
	int position;
	String roleType;

	private Authority(String roleName, String roleId, int position, String roleType) {
		this.roleName = roleName;
		this.roleId = roleId;
		this.position = position;
		this.roleType = roleType;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getRoleId() {
		return roleId;
	}

	public int getPosition() {
		return position;
	}

	public String getRoleType() {
		return roleType;
	}

	Authority forRoleId(int userRoleId) {
		for (Authority userRole : values()) {
			if (userRole.getPosition() == userRoleId) {
				return userRole;
			}
		}
		return null;
	}

}
