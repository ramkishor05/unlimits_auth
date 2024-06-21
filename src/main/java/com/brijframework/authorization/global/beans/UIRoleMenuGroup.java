package com.brijframework.authorization.global.beans;

public class UIRoleMenuGroup {

	private Long id;

	private String idenNo;

	private UIUserRole userRole;

	private UIMenuGroup menuGroup;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdenNo() {
		return idenNo;
	}

	public void setIdenNo(String idenNo) {
		this.idenNo = idenNo;
	}

	public UIUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UIUserRole userRole) {
		this.userRole = userRole;
	}

	public UIMenuGroup getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(UIMenuGroup menuGroup) {
		this.menuGroup = menuGroup;
	}
}
